package no.uio.ifi.asp.parser.unfinished;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspGlobalStmt extends AspSmallStmt {
    AspName variable;
    ArrayList<AspName> variables = new ArrayList<>();

    AspGlobalStmt(int n) {
        super(n);
    }

    static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");
        AspGlobalStmt ags = new AspGlobalStmt(s.curLineNum());

        while (true) {
            ags.variables.add(AspName.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }

        leaveParser("global stmt");
        return ags;
    }

}