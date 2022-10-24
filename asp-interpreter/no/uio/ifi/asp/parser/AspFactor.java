package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.scanner.*;
import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> factorPrefixes = new ArrayList<>();
    ArrayList<AspPrimary> primaries = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();

    AspFactor(int n) {
	    super(n);
    }

    public static AspFactor parse(Scanner s) {
        enterParser("factor");

        AspFactor af = new AspFactor(s.curLineNum());

        while (true) {
            if (s.isFactorPrefix()){
                af.factorPrefixes.add(AspFactorPrefix.parse(s));
            }
            af.primaries.add(AspPrimary.parse(s));
            if (!s.isFactorOpr()) break;
            af.factorOprs.add(AspFactorOpr.parse(s));
        }

        leaveParser("factor");
        return af;
    }

    @Override
    public void prettyPrint() {
	    int nPrinted = 0;

        for (AspPrimary ap : primaries){
            if (nPrinted < factorPrefixes.size()){
                factorPrefixes.get(nPrinted).prettyPrint();
            }
            ap.prettyPrint();
            if (nPrinted < factorOprs.size()){
                factorOprs.get(nPrinted).prettyPrint();
            }
            nPrinted++; 

        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue runtimePrimary = primaries.get(0).eval(curScope);

        if (!factorPrefixes.isEmpty()){
            if (factorPrefixes.get(0) != null){
                TokenKind kind = factorPrefixes.get(0).kind;

                switch (kind){
                    case plusToken:
                        runtimePrimary = runtimePrimary.evalPositive(this); break;
                    case minusToken:
                        runtimePrimary = runtimePrimary.evalNegate(this); break;
                    default:
                        Main.panic("Illegal factor prefix" + kind);
                }
            }
        }

        for (int i = 1; i < primaries.size(); i++){
            TokenKind kindOfOpr = factorOprs.get(i-1).kind;
            RuntimeValue runtimeNextPrimary = primaries.get(i).eval(curScope);
            
            if (factorPrefixes.get(i) != null){
                TokenKind prefix = factorPrefixes.get(i).kind;
                switch (prefix){
                    case plusToken:
                        runtimeNextPrimary = runtimeNextPrimary.evalPositive(this); break;
                    case minusToken:
                        runtimeNextPrimary = runtimeNextPrimary.evalNegate(this); break;
                    default:
                        Main.panic("Illegal factor prefix" + prefix);
                }
            }
            switch (kindOfOpr){
                case astToken:
                    runtimePrimary = runtimePrimary.evalMultiply(runtimeNextPrimary, this); break;
                case slashToken:
                    runtimePrimary = runtimePrimary.evalDivide(runtimeNextPrimary, this); break;
                case percentToken:
                    runtimePrimary = runtimePrimary.evalModulo(runtimeNextPrimary, this); break;
                case doubleSlashToken:
                    runtimePrimary = runtimePrimary.evalIntDivide(runtimeNextPrimary, this); break;
                default:
                    Main.panic("Illegal factor operator" + kindOfOpr);
            }
        }
        return runtimePrimary;
    }
}
