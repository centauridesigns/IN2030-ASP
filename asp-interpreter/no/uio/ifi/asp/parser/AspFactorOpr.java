package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspFactorOpr extends AspSyntax {
    AspFactorOpr(int n) {
	    super(n);
    }

    public static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");

        AspFactorOpr afo = new AspFactorOpr(s.curLineNum());

        switch(s.curToken().kind) {
            case astToken:
                skip(s, astToken); break;
            case slashToken:
                skip(s, slashToken); break;
            case percentToken:
                skip(s, percentToken); break;
            case doubleSlashToken:
                skip(s, doubleSlashToken); break;
            default:

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
