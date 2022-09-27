package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

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
