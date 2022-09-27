package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspComparison extends AspSyntax {
    AspTerm term;
    ArrayList<AspCompOpr> compOprs = new ArrayList<>();

    AspComparison(int n){
        super(n);
    }

    static AspComparison parse(Scanner s) {
        AspComparison ac = new AspComparison(s.curLineNum());
        
        while (true){
            ac.term = AspTerm.parse(s);
            if (!s.isCompOpr()) break;
            ac.compOprs.add(AspCompOpr.parse(s));
        }
    }

    @Override
    void prettyPrint() {

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        
    }

}