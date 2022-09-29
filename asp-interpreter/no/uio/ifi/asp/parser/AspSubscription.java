package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspSubscription extends AspPrimarySuffix {
    AspExpr expression;
    AspSubscription(int n){
        super(n);
    }

    public static AspSubscription parse(Scanner s){
        enterParser("subscription");
        AspSubscription as = new AspSubscription(s.curLineNum());

        skip(s, leftBracketToken);
        as.expression = AspExpr.parse(s);
        skip(s, rightBracketToken);
 
        leaveParser("subscription");
        return as;
    }

    @Override
    public void prettyPrint() {
	    prettyWrite("[");
        expression.prettyPrint();
        prettyWrite("]");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
