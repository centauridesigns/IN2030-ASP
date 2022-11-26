package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspReturnStmt extends AspSmallStmt {
    AspExpr statement;

    AspReturnStmt(int n) {
        super(n);
    }

    static AspReturnStmt parse(Scanner s) {
        enterParser("return stmt");

        AspReturnStmt ars = new AspReturnStmt(s.curLineNum());
        
        skip(s, returnToken);
        ars.statement = AspExpr.parse(s);
        
        leaveParser("return stmt");
        return ars;
    }

    @Override
    public void prettyPrint() {
	    prettyWrite("return ");
        statement.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = statement.eval(curScope);
        trace("return " + value.showInfo());
        throw new RuntimeReturnValue(value, lineNum);
    }
}
