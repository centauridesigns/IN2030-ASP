package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> factorPrefixes = new ArrayList<>();
    ArrayList<AspPrimary> primaries = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();

    AspFactor(int n) {
	    super(n);
    }

    public static AspFactor parse(Scanner s) {
        enterParser("factor");

        AspFactor af = new AspFactor(s.curLineNum());

        while (true) {
            if (s.isFactorPrefix()){
                af.factorPrefixes.add(AspFactorPrefix.parse(s));
            }
            af.primaries.add(AspPrimary.parse(s));
            if (!s.isFactorOpr()) break;
            af.factorOprs.add(AspFactorOpr.parse(s));
        }

        leaveParser("factor");
        return af;
    }

    @Override
    public void prettyPrint() {
	    int nPrinted = 0;

        for (AspFactorPrefix afp: factorPrefixes){
            afp.prettyPrint();
            primaries.get(nPrinted).prettyPrint();
            if (nPrinted < factorOprs.size()){
                factorOprs.get(nPrinted).prettyPrint();
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
