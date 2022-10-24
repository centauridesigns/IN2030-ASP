package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import no.uio.ifi.asp.runtime.*;

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
	    if (isNot) prettyWrite("not ");
        comparison.prettyPrint();
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue value = comparison.eval(curScope);

        if (isNot) {
            value = value.evalNot(this);
        }

        return value;
    }
}

