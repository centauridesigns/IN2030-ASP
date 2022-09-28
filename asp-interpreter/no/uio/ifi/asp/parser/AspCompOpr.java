package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspCompOpr extends AspSyntax{
    AspCompOpr(int n){
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        enterParser("comp opr");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());

        switch(s.curToken().kind) {
            case lessToken:
                skip(s, lessToken); break;
            case lessEqualToken:
                skip(s, lessEqualToken); break;
            case greaterToken:
                skip(s, greaterToken); break;
            case greaterEqualToken:
                skip(s, greaterEqualToken); break;
            case doubleEqualToken:
                skip(s, doubleEqualToken); break;
            case notEqualToken:
                skip(s, notEqualToken); break;
            default:

        }
        leaveParser("comp opr");
        return aco;
    }

    @Override
    public void prettyPrint() {
	    //-- Must be changed in part 2:
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
