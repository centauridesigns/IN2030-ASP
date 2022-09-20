package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspSyntax;

public abstract class AspAndTest extends AspSyntax{
    ArrayList<AspNotTest> notTests = new ArrayList<>();
    
    AspAndTest(int n) {
        super(n);
    }

    static AspAndTest parse(Scanner s) {
        AspAndTest aat = new AspAndTest(s.curLineNum());
        while (true) {
            aat.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != andToken) break;
            skip(s, andToken);
        }

        return aat;
    }
}
