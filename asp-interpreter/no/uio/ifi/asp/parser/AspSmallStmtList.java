package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmts = new ArrayList<>();
    Boolean semiColon;

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

        if(s.curToken().kind == semicolonToken){
            assl.semiColon = true;
            skip(s, semicolonToken); 
        }

        skip(s, newLineToken);
        
        leaveParser("small stmt list");
        return assl;
    }

    @Override
    public void prettyPrint() {
	    int nPrinted = 0;

        for (AspSmallStmt ass: smallStmts){
            if (nPrinted > 0) prettyWrite("; ");
            ass.prettyPrint(); ++nPrinted;
        }
        if (semiColon != null) prettyWrite("; ");
        prettyWrite("NEWLINE ");

    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}
