package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmts = new ArrayList<>();

    AspSmallStmtList(int n) {
	    super(n);
    }

    public static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");

        AspSmallStmtList assl = new AspSmallStmtList(s.curLineNum());

        while(true){
            assl.smallStmts.add(AspSmallStmt.parse(s));
            if(s.curToken().kind != semicolonToken) break;
            skip(s, semicolonToken);
        }

        if(s.curToken().kind == semicolonToken) skip(s, semicolonToken); 
        skip(s, newLineToken);
        
        leaveParser("small stmt list");
        return assl;
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
