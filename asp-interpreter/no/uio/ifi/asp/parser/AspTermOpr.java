package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspTermOpr extends AspSyntax {
    AspTermOpr(int n) {
        super(n);
    }

    static AspTermOpr parse(Scanner s) {
        enterParser("term opr");

        AspTermOpr ato = new AspTermOpr(s.curLineNum());

        switch(s.curToken().kind) {
            case plusToken:
                skip(s, plusToken);
            case minusToken:
                skip(s, minusToken);
        }

        leaveParser("term opr");
        return ato;
    }

    @Override
    public void prettyPrint() {
	    //-- Must be changed in part 2:
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}