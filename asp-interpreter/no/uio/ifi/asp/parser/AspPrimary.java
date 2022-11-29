package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspPrimary extends AspSyntax{
    AspAtom atom;
    ArrayList<AspPrimarySuffix> apss = new ArrayList<>();

    AspPrimary(int n) {
	    super(n);
    }

    public static AspPrimary parse(Scanner s) {
        enterParser("primary");

        AspPrimary ap = new AspPrimary(s.curLineNum());

        ap.atom = AspAtom.parse(s);

        while (s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken) {
            ap.apss.add(AspPrimarySuffix.parse(s));
        }

        leaveParser("primary");
        return ap;
    }

    @Override
    public void prettyPrint() {
	    atom.prettyPrint();

        for (AspPrimarySuffix ass: apss){
            ass.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = atom.eval(curScope);
        ArrayList<RuntimeValue> arguments;

        for (AspPrimarySuffix suffix : apss) {
            if (value instanceof RuntimeStringValue || value instanceof RuntimeDictValue || value instanceof RuntimeListValue) {
                value = value.evalSubscription(suffix.eval(curScope), this);
            }
    
            else if (suffix instanceof AspArguments) {
                String funcTrace = curScope.find(value.toString(), this).toString();
                RuntimeListValue listObject = (RuntimeListValue) suffix.eval(curScope);
                arguments = listObject.getList(this);

                trace("Call function " + funcTrace + " with params " + arguments);
                value = value.evalFuncCall(arguments, this);
            }
        }

        return value;
    }
}
