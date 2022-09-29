package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspNoneLiteral extends AspAtom {
    AspNoneLiteral(int n){
        super(n);
    }

    static AspNoneLiteral parse(Scanner s){
        enterParser("none");
        AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
        skip(s, noneToken);
        leaveParser("none");
        return anl;
    }

    @Override
    public void prettyPrint() {
	    prettyWrite("None ");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}