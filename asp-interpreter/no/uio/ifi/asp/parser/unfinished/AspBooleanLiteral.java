package no.uio.ifi.asp.parser.unfinished;

import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspBooleanLiteral extends AspAtom {
    AspBooleanLiteral(int n) {
        super(n);
    }

    static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());

        if (s.curToken().kind == trueToken) skip(s, trueToken);
        else skip(s, falseToken);

        leaveParser("boolean literal");
        return abl;
    }
}