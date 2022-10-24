package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

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
        int nPrinted = 0;
	    prettyWrite("[");

        for (AspExpr ae: exprs){
            if (nPrinted > 0) prettyWrite(", ");
            ae.prettyPrint(); ++nPrinted;
        }

        prettyWrite("]");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeListValue list = new RuntimeListValue(new ArrayList<RuntimeValue>());

        RuntimeValue element;

        for (AspExpr expr : exprs) {
            element = expr.eval(curScope);
            list.addElem(element);
        }

        return list;
    }
}