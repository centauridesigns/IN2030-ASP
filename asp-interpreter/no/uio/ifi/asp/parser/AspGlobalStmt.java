package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspGlobalStmt extends AspSmallStmt {
    ArrayList<AspName> variables = new ArrayList<>();

    AspGlobalStmt(int n) {
        super(n);
    }

    static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");
        AspGlobalStmt ags = new AspGlobalStmt(s.curLineNum());
        skip(s, globalToken);

        while (true) {
            ags.variables.add(AspName.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }

        leaveParser("global stmt");
        return ags;
    }

    @Override
    public void prettyPrint() {
        int nPrinted = 0;

	    prettyWrite("global ");

        for (AspName an: variables){
            if (nPrinted > 0) prettyWrite(", ");
            an.prettyPrint(); ++nPrinted;
        }
        
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        for (AspName global : variables) {
            curScope.registerGlobalName(global.name);
        }

        return null;
    }

}