package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspIntegerLiteral extends AspAtom {
    Long number;

    AspIntegerLiteral(int n){
        super(n);
    }

    static AspIntegerLiteral parse(Scanner s){
        enterParser("integer literal");
        AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());

        ail.number = s.curToken().integerLit;

        skip(s, integerToken);
        leaveParser("integer literal");
        return ail;
    }

    @Override
    public void prettyPrint() {
	    String numberStreng = String.valueOf(number);
	    prettyWrite(numberStreng);
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}