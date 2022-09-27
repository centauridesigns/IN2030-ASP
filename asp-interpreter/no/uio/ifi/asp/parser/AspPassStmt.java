package no.uio.ifi.asp.parser.unfinished;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPassStmt extends AspSmallStmt {
    AspPassStmt(int n) {
        super(n);
    }

    static AspPassStmt parse(Scanner s) {
        enterParser("pass stmt");

        AspPassStmt statement = new AspPassStmt(s.curLineNum());
        skip(s, passToken);

        leaveParser("pass stmt");
        return statement;
    }
}
