package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspForStmt extends AspCompoundStmt {
    AspName name;
    AspExpr expression;
    AspSuite suite;

    AspForStmt(int n) {
        super(n);
    }

    static AspForStmt parse(Scanner s) {
        enterParser("for stmt");

        AspForStmt afs = new AspForStmt(s.curLineNum());
        skip(s, forToken);
        afs.name = AspName.parse(s);

        skip(s, inToken);
        afs.expression = AspExpr.parse(s);

        skip(s, colonToken);
        afs.suite = AspSuite.parse(s);

        leaveParser("for stmt");
        return afs;
    }

    @Override
    public void prettyPrint() {
	    prettyWrite("for ");
        name.prettyPrint();
        prettyWrite(" ");
        prettyWrite("in ");
        expression.prettyPrint();
        prettyWrite(":");
        suite.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeExpr = expression.eval(curScope);

        if (runtimeExpr instanceof RuntimeListValue) {
            RuntimeListValue runtimeList = (RuntimeListValue) runtimeExpr;
            trace("for " + name.name.toString() + " in " + runtimeExpr.toString()) ;

            for (int i = 0; i < runtimeExpr.getIntValue("for-loop", this) - 1; i++) {
                curScope.assign(name.name.toString(), runtimeList.getElem(i));
                trace("assigning " + runtimeList.getElem(i).toString() + " to " + name.name);
                suite.eval(curScope);
            }
        }

        return runtimeExpr;
    }
}
