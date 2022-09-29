package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public class AspExprStmt extends AspSmallStmt {
    AspExpr statement;

    AspExprStmt(int n) {
        super(n);
    }

    static AspExprStmt parse(Scanner s) {
        enterParser("expr stmt");

        AspExprStmt aes = new AspExprStmt(s.curLineNum());
        aes.statement = AspExpr.parse(s);

        leaveParser("expr smt");
        return aes;
    }

    @Override
    public void prettyPrint() {
	    statement.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
