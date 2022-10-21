package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

public class RuntimeListValue extends RuntimeValue {
    ArrayList<RuntimeValue> listObject;

    public RuntimeListValue(ArrayList<RuntimeValue> list) {
        listObject = list;
    }

    @Override
    String typeName() {
        return "list";
    }

    @Override
    public String showInfo() {
    }

    @Override
    public String toString() {
        String listString = "[";

        for (int i = 0; i < listObject.size(); i++) {
            listString += listObject.get(i);

            if (i != listObject.size() - 1) {
                listString += ",";
            }
        }

        listString += "]";

        return listString;
    }

    public RuntimeValue getElement(int index) {
        return listObject.get(index);
    }
    
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !listObject.isEmpty();
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue((long) listObject.size());
    }

    // Not needed.
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeListValue) {
            if (this.listObject.equals(v.listObject)) {
                return new RuntimeBoolValue(true);
            }

            return new RuntimeBoolValue(false);
        }

        runtimeError("'==' is undefined for values " + typeName() + "and" + v.typeName() + ".", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeIntegerValue(listObject.size() == 0);
    }

    // Not needed.
    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeListValue) {
            if (this.listObject.equals(v.listObject)) {
                return new RuntimeBoolValue(false);
            }

            return new RuntimeBoolValue(true);
        }

        runtimeError("'!=' is undefined for values " + typeName() + "and" + v.typeName() + ".", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            ArrayList<RuntimeValue> newList = listObject;

            for (int i = v.getIntValue("* operand", where); i > 0; i--) {
                for (int j = 0; j < listObject.size(); j++) {
                    listObject.add(listObject.get(j));
                }
            }

            return new RuntimeListValue(newList);
        }

        runtimeError("'*' is undefined for values " + typeName() + "and" + v.typeName() + ".", where);
        return null;
    }

    @Override
    public void evalAssignElem(RuntimeValue i, RuntimeValue v, AspSyntax where) {
        Integer index = i.getIntValue("element assignment", where);

        if (index < listObject.size()) {
            listObject.remove(index);
            listObject.add(index, v);
        }

        else {
            runtimeError("Index " + index + "out of bounds for " + typeName() + " of size " + listObject.size() + ".", where);
        }
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        Integer index = v.getIntValue("element assignment", where);

        if (v instanceof RuntimeIntegerValue) {
            if (index < listObject.size() - 1) {
                return listObject.get(index);
            }
        }

        runtimeError("Index " + index + "out of bounds for " + typeName() + " of size " + listObject.size() + ".", where);
        return null;
    }
}
