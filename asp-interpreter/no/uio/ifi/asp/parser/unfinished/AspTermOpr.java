package no.uio.ifi.asp.parser.unfinished;

import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.scanner.*;

public class AspTermOpr extends AspSyntax {
    AspTermOpr(int n) {
        super(n);
    }

    static AspTermOpr parse(Scanner s) {
        enterParser("term opr");
        AspTermOpr ato = new AspTermOpr(s.curLineNum());

        if (s.curToken().kind == minusToken) skip(s, minusToken);
        else skip(s, plusToken);

        leaveParser("term opr");
        return ato;
    }
}
