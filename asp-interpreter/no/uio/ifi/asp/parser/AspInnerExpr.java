package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspInnerExpr extends AspAtom {
    AspExpr expression;

    AspInnerExpr(int n) {
        super(n);
    }

    static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());

        skip(s, leftParToken);
        aie.expression = AspExpr.parse(s);
        skip(s, rightParToken);

        leaveParser("inner expr");
        return aie;
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