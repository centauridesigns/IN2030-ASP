package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspArguments extends AspPrimarySuffix {
    ArrayList<AspExpr> expressions = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }

    static AspArguments parse(Scanner s) {
        enterParser("arguments");

        AspArguments aa = new AspArguments(s.curLineNum());
        skip(s, leftParToken);

        if (s.curToken().kind == rightParToken) {
            skip(s, rightParToken);
        }

        else {
            while (true) {
                aa.expressions.add(AspExpr.parse(s));
                if (s.curToken().kind != commaToken) break;
                skip(s, commaToken);
            }

            skip(s, rightParToken);
        }

        leaveParser("arguments");
        return aa;
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
