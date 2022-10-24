package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspFloatLiteral extends AspAtom {
    double floatValue;

    AspFloatLiteral(int n) {
        super(n);
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");

        AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
        afl.floatValue = s.curToken().floatLit;

        skip(s, floatToken);
        leaveParser("float literal");
        return afl;
    }

    @Override
    public void prettyPrint() {
        String doubleStreng = String.valueOf(floatValue);
	    prettyWrite(doubleStreng + " ");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return new RuntimeFloatValue(floatValue);
    }
}