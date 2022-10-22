package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public class AspTerm extends AspSyntax {
    ArrayList<AspFactor> aspFactors = new ArrayList<>();
    ArrayList<AspTermOpr> aspTerms = new ArrayList<>();

    AspTerm(int n) {
	    super(n);
    }

    public static AspTerm parse(Scanner s) {
        enterParser("term");

        AspTerm at = new AspTerm(s.curLineNum());

        while(true){
            at.aspFactors.add(AspFactor.parse(s));
            if (!s.isTermOpr()) break;
            at.aspTerms.add(AspTermOpr.parse(s));
        }

        leaveParser("term");
        return at;
    }

    @Override
    public void prettyPrint() {
	    int nPrinted = 0;

        for (AspFactor af: aspFactors){
            af.prettyPrint();
            if (nPrinted < aspTerms.size()){
                aspTerms.get(nPrinted).prettyPrint();
            }
            nPrinted++;
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = aspFactors.get(0).eval(curScope);
        RuntimeValue operator;
        TokenKind kind;

        for (int i = 1; i < aspFactors.size(); i++) {
            kind = aspTerms.get(i - 1).kind;
            operator = aspTerms.get(i).eval(curScope);

            switch(kind) {
                case plusToken:
                    value = value.evalAdd(operator, this);
                case minusToken:
                    value = value.evalAdd(operator, this);
                default:
                    Main.panic("Illegal term operator: " + kind + "!");
            }
        }
        
        return value;
    }
}
