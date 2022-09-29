package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
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
        //-- Must be changed in part 3:
        return null;
    }

}