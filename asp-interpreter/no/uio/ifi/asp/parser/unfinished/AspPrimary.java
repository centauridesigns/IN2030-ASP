package no.uio.ifi.asp.parser.unfinished;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;

public class AspPrimary extends AspSyntax{
    AspAtom atom;
    ArrayList<AspPrimarySuffix> apss = new ArrayList<>();

    AspPrimary(int n) {
	    super(n);
    }

    public static AspPrimary parse(Scanner s) {
        enterParser("primary");

        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom = AspAtom.parse(s)

        while (s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken ) {
            ap.apss.add(AspPrimarySuffix.parse(s));
        }

        leaveParser("primary");
        return ap;
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
