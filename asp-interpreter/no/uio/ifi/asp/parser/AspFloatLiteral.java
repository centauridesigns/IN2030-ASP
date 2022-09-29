package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspFloatLiteral extends AspAtom {
    double floaten;

    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");

        AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
        afl.floaten = s.curToken().floatLit;

        skip(s, floatToken);
        leaveParser("float literal");
        return afl;
    }

    @Override
    public void prettyPrint() {
        String doubleStreng = String.valueOf(floaten);
	    prettyWrite(" " + doubleStreng + " ");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}