package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

// ABSTRACT
public abstract class AspCompoundStmt extends AspStmt{
    public AspCompoundStmt(int n){
        super(n);
    }

    static AspCompoundStmt parse(Scanner s){
        return ;
    }
}
