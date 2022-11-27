package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspFuncDef extends AspCompoundStmt {
    AspName defName;
    ArrayList<AspName> args = new ArrayList<>();
    AspSuite suite;

    AspFuncDef(int n) {
	    super(n);
    }

    @Override
    public String toString() {
        return defName.name;
    }

    public static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef afd = new AspFuncDef(s.curLineNum());

        skip(s, defToken);
        afd.defName = AspName.parse(s);
        skip(s, leftParToken);

        while(true){
            if(s.curToken().kind == nameToken){
                afd.args.add(AspName.parse(s));
            }
            if(s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        afd.suite = AspSuite.parse(s);

        leaveParser("func def");
        return afd;
    }

    @Override
    public void prettyPrint() {
        int nPrinted = 0;

	    prettyWrite("def ");
        defName.prettyPrint();
        prettyWrite("(");

        for (AspName an: args){
            if (nPrinted > 0) prettyWrite(", ");
            an.prettyPrint(); ++nPrinted;
        }
        prettyWrite(")");
        prettyWrite(":");
        suite.prettyPrint();

    }

    public AspSuite getSuite() {
        return this.suite;
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        ArrayList<RuntimeValue> argsEval = new ArrayList<>();
        RuntimeStringValue nameEval = new RuntimeStringValue(defName.name);

        for (AspName param : args) {
            RuntimeStringValue p = new RuntimeStringValue(param.name);
            argsEval.add(p);
        }

        RuntimeFunc funcEval = new RuntimeFunc(nameEval, argsEval, curScope, this);
        curScope.assign(defName.name, funcEval);

        trace("defining "+ funcEval.toString());
        return null;
    }
}
