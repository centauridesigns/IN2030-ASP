package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorPrefix extends AspSyntax {
    Token token;

    AspFactorPrefix(int n) {
        super(n);
    }

    public static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());

        afp.token = s.curToken();
        skip(s, s.curToken().kind);

        leaveParser("factor prefix");
        return afp;
    }
}
