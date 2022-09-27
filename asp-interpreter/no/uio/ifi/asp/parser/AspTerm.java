package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspTerm extends AspSyntax {
    ArrayList<AspFactor> aspFactors = new ArrayList<>();
    ArrayList<AspTerm> aspTerms = new ArrayList<>();

    AspTerm(int n) {
	    super(n);
    }

    public static AspTerm parse(Scanner s) {
        enterParser("term");

        AspTerm at = new AspTerm(s.curLineNum());

        while(s.isFactorOpr() || s.isTermOpr()){
            at.aspFactors.add(AspFactor.parse(s));
            if (!s.isTermOpr()) break;
            at.aspTerms.add(AspCompOpr.parse(s));
        }

        leaveParser("term");
        return at;
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
