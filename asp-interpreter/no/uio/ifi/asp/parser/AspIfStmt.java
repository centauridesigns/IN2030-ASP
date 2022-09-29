package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspExpr> exprs = new ArrayList<>();
    ArrayList<AspSuite> ifSuites = new ArrayList<>();
    AspSuite suite;
    int elifs = 0;

    AspIfStmt(int n) {
	    super(n);
    }

    public static AspIfStmt parse(Scanner s) {
        enterParser("if stmt");

        AspIfStmt ais = new AspIfStmt(s.curLineNum());

        skip(s, ifToken);

        while(true){
            ais.exprs.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.ifSuites.add(AspSuite.parse(s));
            if(s.curToken().kind != elifToken) break;
            ais.elifs++;
            skip(s, elifToken);
        }

        if(s.curToken().kind == elseToken){
            skip(s, elseToken);
            skip(s, colonToken);
            ais.suite = AspSuite.parse(s);
        }
        
        leaveParser("if stmt");
        return ais;
    }

    @Override
    public void prettyPrint() {
        int nPrinted = 0;
	    prettyWrite("if ");

        for (AspExpr ae: exprs){
            ae.prettyPrint();
            prettyWrite(":");
            ifSuites.get(nPrinted).prettyPrint();
            if (nPrinted < elifs){
                prettyWrite("elif ");
            }
            nPrinted++;
        }

        if (suite != null){
            prettyWrite("else ");
            prettyWrite(":");
            suite.prettyPrint();
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
