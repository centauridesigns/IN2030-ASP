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
	    name.prettyPrint();

        for (AspSubscription as: subs){
            as.prettyPrint();
        }
        prettyWrite(" = ");

        expr.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeName = name.eval(curScope);
        RuntimeValue runtimeExpr = expr.eval(curScope);

        if (subs.isEmpty()){
            curScope.assign(runtimeName.getStringValue("assignment", this), runtimeExpr);
        }
        else {
            for (AspSubscription sub : subs){
                RuntimeValue runtimeSub = sub.eval(curScope);
                runtimeName = runtimeName.evalSubscription(runtimeSub, this);
            }
            RuntimeValue inx = new RuntimeIntegerValue(subs.size()-1);
            runtimeName.evalAssignElem(inx, runtimeExpr, this);
        }
        return null;
    }
}