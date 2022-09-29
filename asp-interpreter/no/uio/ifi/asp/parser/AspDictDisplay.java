package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspDictDisplay extends AspAtom {
    ArrayList<AspExpr> exprs = new ArrayList<>();
    ArrayList<AspStringLiteral> strings = new ArrayList<>();

    AspDictDisplay(int n){
        super(n);
    }

    static AspDictDisplay parse(Scanner s){
        enterParser("dict display");
        AspDictDisplay add = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);
        while(true){
            if (s.curToken().kind == rightBraceToken) break;
            if (s.curToken().kind == commaToken) skip(s, commaToken);
            add.strings.add(AspStringLiteral.parse(s));
            skip(s, colonToken);
            add.exprs.add(AspExpr.parse(s));
        }
        skip(s, rightBraceToken);

        leaveParser("dict display");
        return add;
    }

    @Override
    public void prettyPrint() {
	    int nPrinted = 0;

        prettyWrite("{");

        for (AspStringLiteral asl: strings){
            if (nPrinted > 0) prettyWrite(", ");
            asl.prettyPrint();
            prettyWrite(":");
            exprs.get(nPrinted).prettyPrint();    
            nPrinted++;        

        }

        prettyWrite("}");
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}