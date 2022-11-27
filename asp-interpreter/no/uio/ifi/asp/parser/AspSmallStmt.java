package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public abstract class AspSmallStmt extends AspSyntax {
    public AspSmallStmt(int n){
        super(n);
    }

    static AspSmallStmt parse(Scanner s){
        enterParser("small stmt");
        AspSmallStmt ass = null;

        switch(s.curToken().kind) {
            case nameToken:
                if (s.anyEqualToken()) {
                    ass = AspAssignment.parse(s); break;
                }
                ass = AspExprStmt.parse(s); break;
            case globalToken:
                ass = AspGlobalStmt.parse(s); break;
            case passToken:
                ass = AspPassStmt.parse(s); break;
            case returnToken:
                ass = AspReturnStmt.parse(s); break;
            default:
        }

        leaveParser("small stmt");
        return ass;
    }

    @Override
    public void prettyPrint() {}

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        return null;
    }
}
