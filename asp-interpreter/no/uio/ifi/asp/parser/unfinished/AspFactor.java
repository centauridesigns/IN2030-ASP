package no.uio.ifi.asp.parser.unfinished;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.parser.*;
import java.util.ArrayList;

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
	    //-- Must be changed in part 2:
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
