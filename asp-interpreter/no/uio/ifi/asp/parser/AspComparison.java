package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> terms = new ArrayList<>();
    ArrayList<AspCompOpr> compOprs = new ArrayList<>();

    AspComparison(int n){
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());
        
        while (true){
            ac.terms.add(AspTerm.parse(s));
            if (!s.isCompOpr()) break;
            ac.compOprs.add(AspCompOpr.parse(s));
        }

        leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        int nPrinted = 0;

        for (AspTerm at: terms){
            at.prettyPrint();
            if (nPrinted < compOprs.size()){
                compOprs.get(nPrinted).prettyPrint();
            }
            nPrinted++;
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = terms.get(0).eval(curScope);
        RuntimeValue operand;
        TokenKind kind;

        for (int i = 1; i < terms.size(); i++) {
            value = terms.get(i - 1).eval(curScope);
            operand = terms.get(i).eval(curScope);
            kind = compOprs.get(i - 1).kind;
            
            switch (kind) {
                case lessToken:
                    value = value.evalLess(operand, this);
                case lessEqualToken:
                    value = value.evalLessEqual(operand, this);
                case greaterToken:
                    value = value.evalGreater(operand, this);
                case greaterEqualToken:
                    value = value.evalGreaterEqual(operand, this);
                case doubleEqualToken:
                    value = value.evalEqual(operand, this);
                case notEqualToken:
                    value = value.evalNotEqual(operand, this);
                default:
                    Main.panic("Illegal comparison operator: " + kind + "!");
            }
        }

        return value;
    }

}