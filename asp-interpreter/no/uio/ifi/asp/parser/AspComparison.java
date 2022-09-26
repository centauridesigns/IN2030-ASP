package no.uio.ifi.asp.parser;

public class AspComparison extends AspSyntax{
    AspTerm term;
    ArrayList<AspCompOpr> compOprs = new ArrayList<>();

    AspComparison(int n){
        super(n);
    }

    static AspComparison parse(Scanner s) {
        AspComparison ac = new AspComparison(s.curLineNum());
        
        while (true){
            term = AspTerm.parse(s);
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