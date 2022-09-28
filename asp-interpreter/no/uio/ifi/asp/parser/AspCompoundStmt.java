package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

// ABSTRACT
public abstract class AspCompoundStmt extends AspStmt{
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
        }

        leaveParser("compound stmt");
        return acs;
    }
}
