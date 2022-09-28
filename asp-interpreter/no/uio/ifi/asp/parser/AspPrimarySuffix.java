package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;

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
                aps = AspArguments.parse(s);
            case leftBracketToken:
                aps = AspSubscription.parse(s);
        }

        leaveParser("primary suffix");

        return aps;
    }
}
