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
	    //-- Must be changed in part 2:
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
