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
        prettyWrite("in ");
        expression.prettyPrint();
        prettyWrite(":");
        suite.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
