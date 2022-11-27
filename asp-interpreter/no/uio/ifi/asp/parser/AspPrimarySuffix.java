package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

// ABSTRACT
public abstract class AspPrimarySuffix extends AspSyntax {

    public AspPrimarySuffix(int n){
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s){
        enterParser("primary suffix");
        AspPrimarySuffix aps = null;

        switch (s.curToken().kind) {
            case leftParToken:
                aps = AspArguments.parse(s); break;
            case leftBracketToken:
                aps = AspSubscription.parse(s); break;
            default:
        }

        leaveParser("primary suffix");

        return aps;
    }

    @Override
    public void prettyPrint() {}


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
