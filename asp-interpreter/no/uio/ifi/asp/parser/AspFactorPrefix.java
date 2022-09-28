package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

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
