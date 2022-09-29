package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspBooleanLiteral extends AspAtom {
    TokenKind kind;
    AspBooleanLiteral(int n) {
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());

        if (s.curToken().kind == trueToken){
            abl.kind = trueToken; 
            skip(s, trueToken);
        }else{
            abl.kind = falseToken; 
            skip(s, falseToken);
        } 

        leaveParser("boolean literal");
        return abl;
    }

    @Override
    public void prettyPrint() {
	    if(kind == trueToken) prettyWrite(" True ");
        else prettyWrite(" False ");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}