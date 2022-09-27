package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

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
}
