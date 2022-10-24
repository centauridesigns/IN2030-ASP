package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

// ABSTRACT
public abstract class AspCompoundStmt extends AspStmt{
    TokenKind kind;
    public AspCompoundStmt(int n){
        super(n);
    }

    static AspCompoundStmt parse(Scanner s){
        enterParser("compound stmt");
        
        AspCompoundStmt acs = null;
        
        switch (s.curToken().kind){
            case forToken:
                acs = AspForStmt.parse(s); break;
            case ifToken:
                acs = AspIfStmt.parse(s); break;
            case whileToken:
                acs = AspWhileStmt.parse(s); break;
            case defToken:
                acs = AspFuncDef.parse(s); break;
            default:
        }

        leaveParser("compound stmt");
        return acs;
    }

    abstract void prettyPrint();
    abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}
