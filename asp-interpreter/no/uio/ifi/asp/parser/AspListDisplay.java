package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> exprs = new ArrayList<>();
    
    AspListDisplay(int n){
        super(n);
    }

    static AspListDisplay parse(Scanner s){
        enterParser("subscription");
        AspListDisplay ald = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);

        while(true){
            if (s.curToken().kind == rightBracketToken) break;
            if (s.curToken().kind == commaToken) skip(s, commaToken);
            ald.exprs.add(AspExpr.parse(s));
        }
        skip(s, rightBracketToken);
 
        leaveParser("subscription");
        return ald;
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