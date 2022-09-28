package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.runtime.*;

public class AspCompOpr extends AspSyntax{
    AspCompOpr(int n){
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        enterParser("comp opr");
        AspCompOpr aco = new AspCompOpr(s.curLineNum());
        leaveParser("comp opr");
        return aco;
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
