package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;


public class AspStringLiteral extends AspAtom {
    String characters;

    AspStringLiteral(int n){
        super(n);
    }

    static AspStringLiteral parse(Scanner s){
        enterParser("string literal");
        AspStringLiteral asl = new AspStringLiteral(s.curLineNum());

        asl.characters = s.curToken().stringLit;

        skip(s, stringToken);
        leaveParser("string literal");
        return asl;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("\"");
	    prettyWrite(characters);
        prettyWrite("\"");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeStringValue(characters);
    }
}