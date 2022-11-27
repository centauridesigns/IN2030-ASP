// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
        assign("float", new RuntimeFunc("float"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "float", where);
                return new RuntimeFloatValue(actualParams.get(0).getFloatValue("float", where));
            }
        });

        assign("input", new RuntimeFunc("input"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                String print = actualParams.get(0).getStringValue("input", where);
                System.out.println(print);
                return new RuntimeStringValue(keyboard.nextLine());
            }
        });

        assign("int", new RuntimeFunc("int"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
                checkNumParams(actualParams, 1, "int", where);
                return new RuntimeIntegerValue(actualParams.get(0).getIntValue("int", where));
            }
        });

        assign("len", new RuntimeFunc("len") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,AspSyntax where) {
                checkNumParams(actualParams, 1, "len", where);
                return actualParams.get(0).evalLen(where);
            }
        });

        assign("print", new RuntimeFunc("print") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,AspSyntax where) {
                for (RuntimeValue actualParam : actualParams) {
                    System.out.print(actualParam.toString());
                }

                System.out.println();
                return new RuntimeNoneValue();
            }
        });

        assign("range", new RuntimeFunc("range") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,AspSyntax where) {
                checkNumParams(actualParams, 2, "range", where);
                long rangeStart = actualParams.get(0).getIntValue("range", where);
                long rangeEnd = actualParams.get(1).getIntValue("range", where);
        
                if (rangeStart > rangeEnd) {
                    runtimeError("Range start exceeds end", where);;
                }

                RuntimeListValue rangeList = new RuntimeListValue(new ArrayList<RuntimeValue>());

                for (long i = rangeStart; i <= rangeEnd; i++) {
                    rangeList.addElem(new RuntimeIntegerValue(i));
                }

                return rangeList;
            }
        });

        assign("str", new RuntimeFunc("str") {
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams,AspSyntax where) {
                checkNumParams(actualParams, 1, "str", where);
                return new RuntimeStringValue(actualParams.get(0).toString());
            }
        });
    }

    private void checkNumParams(ArrayList<RuntimeValue> actArgs, int nCorrect, String id, AspSyntax where) {
        if (actArgs.size() != nCorrect)
            RuntimeValue.runtimeError("Wrong number of parameters to " + id + "!", where);
    }
}
