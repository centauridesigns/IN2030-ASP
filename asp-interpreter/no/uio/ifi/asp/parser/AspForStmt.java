package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt {
    AspName name;
    AspExpr expression;
    AspSuite suite;

    AspForStmt(int n) {
        super(n);
    }

    static AspForSmt parse(Scanner s) {
        enterParser("for stmt");

        AspForStmt afs = new AspForStmt(s.curLineNum());
        skip(s, forToken);
        afs.name = AspName.parse(s);

        skip(s, inToken);
        afs.expression = AspExpr.parse(s);

        skip(s, colonToken);
        afs.suite = AspSuite.parse(s);

        leaveParser("for stmt");
        return afs;
    }
}
