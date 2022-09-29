package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public class AspFactorOpr extends AspSyntax {
    TokenKind kind;

    AspFactorOpr(int n) {
	    super(n);
    }

    public static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");
        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());

        afo.kind = s.curToken().kind;
        skip(s, s.curToken().kind);

        leaveParser("factor opr");
        return afo;
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
