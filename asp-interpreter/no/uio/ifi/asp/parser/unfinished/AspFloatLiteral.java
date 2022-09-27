package no.uio.ifi.asp.parser.unfinished;

import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFloatLiteral extends AspAtom {
    AspIntegerLiteral integer;

    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");

        AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
        afl.integer = AspIntegerLiteral.parse(s);

        // What the hell?!
    }
}