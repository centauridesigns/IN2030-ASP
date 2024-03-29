package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.*;
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
        return toString();
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

    public void addElem(RuntimeValue element) {
        listObject.add(element);
    }

    public RuntimeValue getElem(int index) {
        return listObject.get(index);
    }

    public ArrayList<RuntimeValue> getList(AspSyntax where) {
        return this.listObject;
    }


    @Override
    public String getStringValue(String what, AspSyntax where){
        return this.toString();
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return listObject.size();
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
            if (this.listObject.equals(((RuntimeListValue) v).getList(where))) {
                return new RuntimeBoolValue(true);
            }

            return new RuntimeBoolValue(false);
        }
        else if (v.toString() == "None"){
            return new RuntimeBoolValue(false);
        }

        runtimeError("'==' is undefined for values " + typeName() + " and " + v.typeName() + ".", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!getBoolValue("!", where));
    }

    // Not needed.
    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeListValue) {
            if (this.listObject.equals(((RuntimeListValue) v).getList(where))) {
                return new RuntimeBoolValue(false);
            }

            return new RuntimeBoolValue(true);
        }
        else if (v.toString() == "None"){
            return new RuntimeBoolValue(false);
        }

        runtimeError("'!=' is undefined for values " + typeName() + " and " + v.typeName() + ".", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            ArrayList<RuntimeValue> newList = listObject;
            int size = listObject.size();

            for (int i = (int) v.getIntValue("* operand", where)-1; i > 0; i--) {
                for (int j = 0; j < size; j++) {
                    listObject.add(listObject.get(j));
                }
            }

            return new RuntimeListValue(newList);
        }

        runtimeError("'*' is undefined for values " + typeName() + "and" + v.typeName() + ".", where);
        return null;
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue v, AspSyntax where) {
        Integer index = (int) inx.getIntValue("element assignment", where);

        if (index >= 0 && index < listObject.size()) {
            listObject.set(index, v);
        }

        else {
            runtimeError("Index " + index + " out of bounds for " + typeName() + " of size " + listObject.size() + ".", where);
        }
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        Integer index = (int) v.getIntValue("element assignment", where);

        if (v instanceof RuntimeIntegerValue) {
            if (index >= 0 && index <= listObject.size() - 1) {
                return listObject.get(index);
            }

            runtimeError("Index " + index + " out of bounds for " + typeName() + " of size " + listObject.size() + ".", where);
            return null;
        }

        runtimeError("Subscription [...] is undefined for values " + v.typeName() + ".", where);

        return null;
    }
}
