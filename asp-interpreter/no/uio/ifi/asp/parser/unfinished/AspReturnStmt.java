package no.uio.ifi.asp.parser.unfinished;

import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.scanner.*;

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
    }
}
