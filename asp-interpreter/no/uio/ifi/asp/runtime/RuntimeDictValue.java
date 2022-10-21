package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import java.util.HashMap;
import no.uio.ifi.asp.parser.*;

public class RuntimeDictValue extends RuntimeValue {
    HashMap<RuntimeStringValue, RuntimeValue> dictObject;
    
    public RuntimeDictValue(HashMap<RuntimeStringValue, RuntimeValue> hash) {
        this.dictObject = hash;
    }

    @Override
    String typeName() {
        return "dictionary";
    }

    @Override
    public String showInfo() {
        return toString();
    }

    @Override
    public String toString() {
        String newString = "{";
        int i = 0;

        for (RuntimeStringValue key : dictObject.keySet()) {
            newString += key;
            newString += ":";
            newString += dictObject.get(key);

            if (i < dictObject.size() - 1) {
                newString += ", ";
            }

            i++;
        }

        newString += "}";
        return newString;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !dictObject.isEmpty();
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue((long) dictObject.size());
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!getBoolValue("!", where));
    }

    @Override
    public void evalAssignElem(RuntimeValue k, RuntimeValue v, AspSyntax where) {
        if (k instanceof RuntimeStringValue) {
            if (dictObject.containsKey(k)) {
                dictObject.put((RuntimeStringValue) k, v);
            }

            else {
                runtimeError("Key " + k.toString() + " not found", where);
            }
        }

        else {
            runtimeError("Key " + k.toString() + " is not a string.", where);
        }
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue k, AspSyntax where) {
        if (k instanceof RuntimeStringValue) {
            if (dictObject.get(k) != null) {
                return dictObject.get(k);
            }

            runtimeError("Key " + k.toString() + " not found", where);
            return null;
        }

        runtimeError("Key " + k.toString() + " is not a string.", where);
        return null;
    }
}
