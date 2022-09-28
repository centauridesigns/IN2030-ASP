package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.*;

public class AspFuncDef extends AspCompoundStmt {
    AspName defName;
    ArrayList<AspName> args = new ArrayList<>();
    AspSuite suite;

    AspFuncDef(int n) {
	    super(n);
    }

    public static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef afd = new AspFuncDef(s.curLineNum());

        skip(s, defToken);
        afd.defName = AspName.parse(s);
        skip(s, leftParToken);

        while(true){
            if(s.curToken().kind == nameToken){
                afd.args.add(AspName.parse(s));
            }
            if(s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        afd.suite = AspSuite.parse(s);

        leaveParser("func def");
        return afd;
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
