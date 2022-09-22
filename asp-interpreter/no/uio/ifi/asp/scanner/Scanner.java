// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
		curFileName = fileName;
		indents.push(0);

		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
					new FileInputStream(fileName),
					"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
    }


    private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
    }

    public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}

		return curLineTokens.get(0);
    }

    public void readNextToken() {
		if (! curLineTokens.isEmpty())
			curLineTokens.remove(0);
    }

    private void readNextLine() {
		boolean isInFloat = false;
		boolean isInDoubleChars = false;
		curLineTokens.clear();

		// Read the next line:
		String line = null;

		try {
			line = sourceFile.readLine();

			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}

		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

		// If there are no more lines to read, add an EOF token.
		if (sourceFile == null) {
			// Handle detents.
			if (indents.peek() > 4 - (indents.peek() % 4)) {
				while (indents.peek() > 4 - indents.peek() % 4) {
					indents.pop();
					curLineTokens.add(new Token(dedentToken, curLineNum()));
				}
			}

			curLineTokens.add(new Token(eofToken, curLineNum()));

			for (Token t: curLineTokens) {
				Main.log.noteToken(t);
			}
		}

		// If there are lines to read, continue reading.
		else {
	
			// Expand the leading tabs, converting them onto spaces.
			line = expandLeadingTabs(line);
			if (line == "ERROR") {
				return;
			}
	
			// Create indent and dedent tokens.
			createIndents(line);

			// Loop through every character in the sentence.
			for (int i = 0; i < line.length(); i++) {
				//System.out.println("char er: " + line.charAt(i));
				//System.out.println(line);

				if (!isDigit(line.charAt(i)) && isInFloat) {
					isInFloat = false;
				}

				// If the line contains a space, do nothing. 
				if (line.charAt(i) == ' ');

				// If the sentence contains a #, regard the whole as a comment. This must be rewritten to allow for post-code comments.
				else if (line.charAt(i) == '#') {
					// Terminate line. A token of this kind should not be created when mid-string.
					curLineTokens.add(new Token(newLineToken,curLineNum()));

					for (Token t: curLineTokens) {
						Main.log.noteToken(t);
					}

					return;
				}
				
				// If the line contains a quotation mark, read until the next quotation mark (unless it is an ending quotation mark).
				// Does not take into consideration NEWLINE mid-string, which must be corrected.
				else if (line.charAt(i) == '"') {
					int start = i + 1;
					int end = start;

					while (end < line.length() - 1 && line.charAt(end) != '\"') {
						i++;
						end++;
					}

					if(line.charAt(end) != '\"'){
						scannerError("Encountered unended string literal.");
					}

					String sToken = line.substring(start, end);

					Token tempToken = new Token(stringToken, curLineNum());
					tempToken.stringLit = sToken;

					curLineTokens.add(tempToken);

					i++;
				}

				// If the line contains a quotation mark, read until the next quotation mark (unless it is an ending quotation mark).
				// Does not take into consideration NEWLINE mid-string, which must be corrected.
				else if (line.charAt(i) == '\'') {
					int start = i + 1;
					int end = start;

					// Continue iterating through the string until a quotation mark is read. The for-loop skips past the string, ensuring it is not read again.
					while (end < line.length() - 1 && line.charAt(end) != '\'') {
						i++;
						end++;
					}

					// If a NEWLINE does not occur at the end of the line, the string is unended.
					if(line.charAt(end) != '\''){
						scannerError("Encountered unended string literal.");
					}

					String sToken = line.substring(start, end);

					Token tempToken = new Token(stringToken, curLineNum());
					tempToken.stringLit = sToken;

					curLineTokens.add(tempToken);

					i++;
				}
	
				// If the letter read is a character, continue reading until the characters end - unless it is contained within quotes, in which case it is a string.
				else if (isLetterAZ(line.charAt(i))) {
					int start = i;

					while(i < line.length() - 1 && (isLetterAZ(line.charAt(i + 1)) || (isDigit(line.charAt(i + 1))))){
						i++;
					}

					// Set end to I, the substring does not take into account the end-index.
					int end = i;

					String nToken = line.substring(start, end + 1);
					Token tempToken = new Token(nameToken, curLineNum());
					tempToken.name = nToken;

					tempToken.checkResWords();
	
					curLineTokens.add(tempToken);
				}
	
				// If a digit is read, continue reading until no digits persist. Create a TOKEN.
				else if (isDigit(line.charAt(i))) {
					if (!isInFloat) {
						int start = i;
	
						if (i + 1 < line.length()) {
							// The encountered integer is more than 1 digit long.
							while (i + 1 < line.length() && isDigit(line.charAt(i + 1))) {
								i++;
							}
	
							// The encountered digit contains a decimal point.
							if (i + 1 < line.length() - 1 && line.charAt(i + 1) == '.') {
								boolean completeFloat = false;
								isInFloat = true;
								i = i+2;
								
	
								if (line.charAt(i-1) == '.' && line.length() == i) {
									isInFloat = false;
									scannerError("Encountered unended or incorrectly formatted float.");
								}
	
								// Read the remaining decimals following the decimal point.
								while (i < line.length() - 1 && isDigit(line.charAt(i))) {
									completeFloat = true;
									i++;
								}
								
								// Set the float to complete whence the last digit is not a decimal point.
								if (!completeFloat && isDigit(line.charAt(i))) {
									completeFloat = true;
								}
								
								// If a decimal point is found at the last digit, throw an error.
								if (!completeFloat) {
									isInFloat = false;
									scannerError("Encountered unended or incorrectly formatted float.");
								}
							
								int end = i;
								
								try {
									Float ftToken = Float.parseFloat(line.substring(start, end + 1));
									Token tempToken = new Token(floatToken, curLineNum());
									tempToken.floatLit = ftToken;
									curLineTokens.add(tempToken);
								} catch(NumberFormatException e) {
									scannerError("List index can not be a float.");
								}
							}
		
							// The encountered multi-digit integer does not contain a decimal point.
							else {
								int end = i;
			
								int intToken = Integer.valueOf(line.substring(start, end + 1));
								Token tempToken = new Token(integerToken, curLineNum());
								tempToken.integerLit = intToken;
			
								curLineTokens.add(tempToken);
							}
						}
	
						// The encountered single digit integer does not contain a decimal point.
						else {
							int end = i;
				
							int intToken = Integer.valueOf(line.substring(start, end + 1));
							Token tempToken = new Token(integerToken, curLineNum());
							tempToken.integerLit = intToken;
		
							curLineTokens.add(tempToken);
						}
					}

				}

				// If none of the above match the encountered symbol, check to see which. We check double-digit symbols separately, and loop through the token kinds if it is a single-digit symbol.
				else {
					if (line.charAt(i) == '='){
						if (line.charAt(i+1) == '='){
							isInDoubleChars = true;
							curLineTokens.add(new Token(doubleEqualToken, curLineNum()));
						} 
						
						else{
							if(isInDoubleChars){
								isInDoubleChars = false;
							}else{
								curLineTokens.add(new Token(equalToken, curLineNum()));
							}
						}
					} 
					
					else if (line.charAt(i) == '<'){
						if (line.charAt(i+1) == '='){
							isInDoubleChars = true;
							curLineTokens.add(new Token(lessEqualToken, curLineNum()));
						}
						
						else {
							if(isInDoubleChars){
								isInDoubleChars = false;
							}else{
								curLineTokens.add(new Token(lessToken, curLineNum()));
							}
						}
					}
					
					else if (line.charAt(i) == '>'){
						if (line.charAt(i+1) == '='){
							isInDoubleChars = true;
							curLineTokens.add(new Token(greaterEqualToken, curLineNum()));
						}
						
						else {
							if(isInDoubleChars){
								isInDoubleChars = false;
							}else{
								curLineTokens.add(new Token(greaterToken, curLineNum()));
							}
						}
					}
					
					else if (line.charAt(i) == '/'){
						if (line.charAt(i+1) == '/'){
							isInDoubleChars = true;
							curLineTokens.add(new Token(doubleSlashToken, curLineNum()));
						}
						
						else {
							if(isInDoubleChars){
								isInDoubleChars = false;
							}else{
								curLineTokens.add(new Token(slashToken, curLineNum()));
							}
						}
					}
					
					else if (line.charAt(i) == '!'){
						if (line.charAt(i+1) == '='){
							curLineTokens.add(new Token(notEqualToken, curLineNum()));
						}
					}
					
					// If no special-case symbol has been found, cross-reference it with the list of tokens.
					else {
						int tokenCount = 0;
						for (TokenKind t : TokenKind.values()){
							if (t.image.charAt(0) == line.charAt(i)) {
								tokenCount++;
								curLineTokens.add(new Token(t, curLineNum()));
							}
						}

						// If the symbol is not in the list of known symbols, it cannot be handled and is illegal.
						if(tokenCount == 0){
							if (line.charAt(i) == '	') {

							}

							else {
								scannerError("Encountered unknown symbol.");
							}
						}
					}
				}
			}

			// Terminate line. A token of this kind should not be created when mid-string.
			curLineTokens.add(new Token(newLineToken,curLineNum()));
	
			for (Token t: curLineTokens) {
				Main.log.noteToken(t);
			}
		}
    }

    public int curLineNum() {
		return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;

		while (indent < s.length() && s.charAt(indent) == ' ') indent++;
		return indent;
    }

    private String expandLeadingTabs(String s) {
		//System.out.println("Lengde på s i metoden: " + s.length());
		int n = 0;
		int m = 0;
		boolean tabOrSpace = true;
		String tmp = "";

		while (tabOrSpace) {
			if (s.length() == 0) {
				return "ERROR";
			}

			else if (s.charAt(m) == ' ') n++;

			else if (s.charAt(m) == '	') {
				n += 4 - (n % 4);
			}

			else {
				tabOrSpace = false;
			}

			if (m >= s.length() - 1) {
				tabOrSpace = false;
			}

			m++;
		}

		// Legger til riktig antall spaces og resten av stringen
		for (int i = 0; i < n; i++) {
			tmp += ' ';
		}

		//System.out.println(s.substring(m - 1));

		tmp += s.substring(m - 1);
		return tmp;
    }

	private void createIndents(String s){
		int indentCount = 0;
		//sjekker om linje bare inneholder blanke 
		for (int i = 0;  i < s.length(); i++){
			if (s.charAt(i) != ' ');

			else if (s.charAt(i) == '\n') {
				return;
			}

			else {
				continue;
			}
		}

		//Teller innledende blanke 
		indentCount = findIndent(s);

		//Pusher og popper fra stack
		if (indentCount > indents.peek()) {
			indents.push(indentCount);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		}

		else if (indentCount < indents.peek()) {
			while (indentCount < indents.peek()) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
		}

		else if (indentCount == indents.peek());

		if (indentCount != indents.peek()){
			scannerError("Encountered incorrect indentation level.");
		}
	}


    private boolean isLetterAZ(char c) {
		return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
		return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		
		if(k == lessToken || k == lessEqualToken || k == greaterToken || k == greaterEqualToken || k == doubleEqualToken || k == notEqualToken){
			return true;
		} 
		return false;
    }


    public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }
	

    public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean anyEqualToken() {
		for (Token t: curLineTokens) {
			if (t.kind == equalToken) return true;
			if (t.kind == semicolonToken) return false;
		}

		return false;
    }

	ArrayList<String> keywords = new ArrayList<>(Arrays.asList("and", "def", "elif", "else", "for", "global", "if", "in", "None", "not", "or", "pass", "return", "True", "False", "while"));
}
