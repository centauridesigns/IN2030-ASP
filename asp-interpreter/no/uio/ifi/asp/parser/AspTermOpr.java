package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public class AspTermOpr extends AspSyntax {
    TokenKind kind;

    AspTermOpr(int n) {
        super(n);
    }

    static AspTermOpr parse(Scanner s) {
        enterParser("term opr");

        AspTermOpr ato = new AspTermOpr(s.curLineNum());

        ato.kind = s.curToken().kind;
        skip(s, s.curToken().kind);

        leaveParser("term opr");
        return ato;
    }

    @Override
    public void prettyPrint() {
	    prettyWrite(" " + kind.toString() + " ");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
