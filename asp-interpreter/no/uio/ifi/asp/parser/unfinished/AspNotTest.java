package no.uio.ifi.asp.parser.unfinished;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspNotTest extends AspSyntax {
    AspComparison comparison;
    Boolean isNot = false;

    AspNotTest(int n){
        super(n);
    }

    static AspNotTest parse(Scanner s){
        enterParser("not test");
        AspNotTest ant = new AspNotTest(s.curLineNum());

        if(s.curToken().kind == notToken){
            ant.isNot = true;
            skip(s, notToken);
        }
        
        ant.comparison = AspComparison.parse(s);

        leaveParser("not test");
        return ant;
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

