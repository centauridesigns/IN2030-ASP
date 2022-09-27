package no.uio.ifi.asp.parser;

public class AspCompOpr extends AspSyntax{
    AspCompOpr(int n){
        super(n);
    }

    static AspCompOpr parse(Scanner s){
        AspCompOpr aco = new AspCompOpr(s.curLineNum());
        return aco;
    }

    @Override
    void prettyPrint(){

    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue{
        
    }
}
