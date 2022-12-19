package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import no.uio.ifi.asp.main.Main;
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


    // @Override
    // public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    //     RuntimeValue runtimeExpr = expr.eval(curScope);
    //     RuntimeScope gs = Main.globalScope;

    //     if (subs.isEmpty()){
    //         if (gs.hasGlobalName(name.name)) {
    //             gs.assign(name.name, runtimeExpr);
    //             trace(name.name + "=" + runtimeExpr.toString());
    //         }

    //         else {
    //             curScope.assign(name.name, runtimeExpr);
    //             trace(name.name + "=" + runtimeExpr.toString());
    //         }
    //     }

    //     else {
    //         RuntimeValue runtimeName = name.eval(curScope);
            
    //         for (AspSubscription sub : subs){
    //             RuntimeValue runtimeSub = sub.eval(curScope);
    //             runtimeName = runtimeName.evalSubscription(runtimeSub, this);
    //         }
            
    //         AspSubscription sub = subs.get(subs.size() - 1);
    //         RuntimeValue inx = sub.eval(curScope);

    //         runtimeName.evalAssignElem(inx, runtimeExpr, this);
    //         trace(runtimeName.toString() + "[" + inx.toString() +"] = " + expr.toString());
    //     }

    //     return null;
    // }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimeExpr = expr.eval(curScope);
        RuntimeScope gs = Main.globalScope;

        if (subs.isEmpty()){
            if (gs.hasGlobalName(name.name)) {
                gs.assign(name.name, runtimeExpr);
                trace(name.name + "=" + runtimeExpr.toString());
            }

            else {
                curScope.assign(name.name, runtimeExpr);
                trace(name.name + "=" + runtimeExpr.toString());
            }
        }

        else {
            String runtimeName = name.name;
            RuntimeValue inx = subs.get(0).eval(curScope);

            RuntimeValue list = curScope.find(name.name, this);
            
            for (int i = 0; i < subs.size() - 1; i++){
                inx = subs.get(i + 1).eval(curScope);
                list = list.evalSubscription(subs.get(i).eval(curScope), this);
            }

            list.evalAssignElem(inx, runtimeExpr, this);
            trace(runtimeName + "[" + inx.toString() +"] = " + runtimeExpr.toString());
        }

        return null;
    }
}