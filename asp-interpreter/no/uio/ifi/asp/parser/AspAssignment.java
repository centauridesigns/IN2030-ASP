package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

public class AspAssignment extends AspSmallStmt {
    ArrayList<AspSubscription> subs = new ArrayList<>();
    AspName name;
    AspExpr expr;

    AspAssignment(int n){
        super(n);
    }

    static AspAssignment parse(Scanner s){
        enterParser("assignment");
        AspAssignment aa = new AspAssignment(s.curLineNum());

        aa.name = AspName.parse(s);

        while(s.curToken().kind != equalToken){
            aa.subs.add(AspSubscription.parse(s));
        }

        skip(s, equalToken);
        aa.expr = AspExpr.parse(s);
 
        leaveParser("assignment");
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