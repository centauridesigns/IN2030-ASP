package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

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
}
