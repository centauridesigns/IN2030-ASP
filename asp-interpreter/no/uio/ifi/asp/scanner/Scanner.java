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

		//-- Must be changed in part 1:
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ');
			else if (line.charAt(i) == '#') return;
			/*
			else if (line.charAt(i) == '\n') {
				//OPPRETT NEWLINE TOKEN 
				curLineTokens.add(new Token(newLineToken, curLineNum()));
				return;
			}*/

			else if (line.charAt(i) == '"') {
				// LES TIL NESTE " : opprett string token
				int start = i;
				while(line.charAt(i+1) != '"'){
					i++;
				}
				int end = i+1;
				String sToken = line.substring(start, end+1);
				curLineTokens.add(new Token(stringToken, curLineNum()));
				//må vel pæse med selve stringen også??

			}

			else if (isLetterAZ(line.charAt(i))) {
				// LES TIL INGEN FLERE CHARS OPPRETT NAVN TOKEN
				int start = i;
				while(isLetterAZ(line.charAt(i+1))){
					i++;
				}
				//setter end til i, substring tar ikke med endindex
				int end = i;
				String nToken = line.substring(start, end);

				if(!keywords.contains(nToken)){
					curLineTokens.add(new Token(nameToken, curLineNum()));
					//må vel pæse med selve stringen også??
				}else{
					//opprettelse av keyword-tokens
					for(Token t : TokenKind.values()){
						if(t.name.equals(nToken)){
							curLineTokens.add(new Token(t, curLineNum()));
						}
					}
				}
				
			}

			else if (isDigit(line.charAt(i))) {
				// SJEKK OM NESTE VERDIER OGSÅ ER TALL, HVIS IKKE OPPRETT TALL TOKEN
				int start = i;
				while(isDigit(line.charAt(i+1))){
					i++;
				}
				//setter end til i, substring tar ikke med endindex
				int end = i;
				int intToken = Integer.valueOf(substring(start, end));
				curLineTokens.add(new Token(integerToken, curLineNum()));
				//må vel pæse med selve inten også??
			}
			// SYMBOLER, ARITMETIKK, EOF
			else{
				if(line.charAt(i) == '='){
					if(line.charAt(i+1) == '='){
						curLineTokens.add(new Token(doubleEqualToken, curLineNum()));
					}else{
						curLineTokens.add(new Token(equalToken, curLineNum()));
					}
				}else if (line.charAt(i) == '<'){
					if(line.charAt(i+1) == '='){
						curLineTokens.add(new Token(lessEqualToken, curLineNum()));
					}else{
						curLineTokens.add(new Token(lessToken, curLineNum()));
					}
				}else if (line.charAt(i) == '>'){
					if(line.charAt(i+1) == '='){
						curLineTokens.add(new Token(greaterEqualToken, curLineNum()));
					}else{
						curLineTokens.add(new Token(greaterToken, curLineNum()));
					}
				}else if (line.charAt(i) == '/'){
					if(line.charAt(i+1) == '/'){
						curLineTokens.add(new Token(doubleSlashToken, curLineNum()));
					}else{
						curLineTokens.add(new Token(slashToken, curLineNum()));
					}
				}else if (line.charAt(i) == '!'){
					if(line.charAt(i+1) == '='){
						curLineTokens.add(new Token(notEqualToken, curLineNum()));
					}
				}else{
					for(Token t : TokenKind.values()){
						if(t.name.equals(line.charAt(i))){
							curLineTokens.add(new Token(t, curLineNum()));
						}
					}
				}
			}

		}

		// Terminate line:
		curLineTokens.add(new Token(newLineToken,curLineNum()));

		for (Token t: curLineTokens) 
			Main.log.noteToken(t);
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
			if (s.charAt(m) == ' ') n++;

			else if (s.charAt(m) == '	') {
				n += 4 - (n % 4);
			}

			if(m >= s.length()-1) {
				tabOrSpace = false;
			}
			System.out.println(m);
			m++;
		}

		//legger til riktig antall spaces og resten av stringen
		for (int i = 0; i < n; i++) {
			tmp += ' ';
		}

		tmp += s.substring(m);
		
		return tmp;
    }

	private void createIndents(String s){
		String tmp;
		int indentCount = 0;
		//sjekker om linje bare inneholder blanke 
		for (int i = 0;  i < s.length(); i++){
			if(s.charAt(i) != ' '){
				return;
			}
		}

		//omformer TAB-er til blanke 
		tmp = expandLeadingTabs(s);

		//teller innledende blanke 
		indentCount = findIndent(tmp);

		//pusher og popper fra stack
		if (indentCount > indents.peek()) {
			indents.push(indentCount);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		}

		else {
			indents.pop();
			curLineTokens.add(new Token(dedentToken, curLineNum()));
		}

		if (indentCount != indents.peek()){
			// Indenteringsfeil.
			System.out.println("Indenteringsfeil.");
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
		//-- Must be changed in part 2:
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
