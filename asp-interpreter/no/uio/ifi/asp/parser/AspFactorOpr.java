package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFactorOpr extends AspSyntax {
    AspFactorOpr(int n) {
	    super(n);
    }

    public static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");

        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());

        switch(s.curToken().kind) {
            case astToken:
                skip(s, astToken);
            case slashToken:
                skip(s, slashToken);
            case percentToken:
                skip(s, percentToken);
            case doubleSlashToken:
                skip(s, doubleSlashToken);

        }

        leaveParser("factor opr");
        return afo;
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
