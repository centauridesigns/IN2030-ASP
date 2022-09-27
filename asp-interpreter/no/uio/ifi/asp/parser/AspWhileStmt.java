package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.parser.unfinished.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspCompoundStmt {
    AspExpr test;
    AspSuite body;

    AspWhileStmt(int n) {
        super(n);
    }

    static AspWhileStmt parse(Scanner s) {
        enterParser("whileStmt");

        AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
        skip(s, whileToken); 
        aws.test = AspExpr.parse(s);

        skip(s, colonToken);
        aws.body = AspSuite.parse(s);

        leaveParser("andTest");
        return aws;
    }
}

