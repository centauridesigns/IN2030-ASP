package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public class AspComparison extends AspSyntax {
    AspTerm term;
    ArrayList<AspCompOpr> compOprs = new ArrayList<>();

    AspComparison(int n){
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("aspcomparison");
        AspComparison ac = new AspComparison(s.curLineNum());
        
        while (true){
            ac.term = AspTerm.parse(s);
            if (!s.isCompOpr()) break;
            ac.compOprs.add(AspCompOpr.parse(s));
        }

        leaveParser("aspcomparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        //-- Must be changed in part 2:
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }

}