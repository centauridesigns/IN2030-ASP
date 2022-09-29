package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public class AspCompOpr extends AspSyntax{
    TokenKind kind;
    
    AspCompOpr(int n){
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        enterParser("comp opr");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());

        aco.kind = s.curToken().kind;
        skip(s, s.curToken().kind);

        leaveParser("comp opr");
        return aco;
    }

    @Override
    public void prettyPrint() {
	    prettyWrite(" " + kind.toString() + " ");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
