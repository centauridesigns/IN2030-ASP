package no.uio.ifi.asp.runtime;

public class RuntimeStringValue extends RuntimeValue {
    String stringLiteral;

    public RuntimeBoolValue(String s) {
        stringLiteral = s;
    }

    @Override
    String typeName() {
        return "String";
    }

    @Override
    public String showInfo() {
        if (stringLiteral.indexOf('\'' >= 0)) {
            return '"' + stringLiteral + '"';
        }

        else {
            return "'" + stringLiteral + "'";
        }
    }

    @Override
    public String toString() {
        return stringLiteral;
    }
    
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !stringLiteral.isEmpty();
    }

    @Override
    public getIntValue(String what, AspSyntax where) {
        return Integer.parseInt(stringLiteral);
    }

    @Override
    public getFloatValue(String what, AspSyntax where) {
        return Float.parseFloat(stringLiteral);
    }

    @Override
    public boolean evalLen(AspSyntax where) {
        return RuntimeIntegerValue(stringLiteral.length());
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringLiteral == v.toString()) {
                return RunTimeBoolValue(true);
            }

            return RunTimeBoolValue(false);
        }
        
        runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(string == "");
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringLiteral == v.toString()) {
                return RunTimeBoolValue(false);
            }

            return RunTimeBoolValue(true)
        }

        runtimeError("Type error for !=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            String newString = stringLiteral + v.toString();
            return new RuntimeStringValue(newString);
        }

        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            String newString = stringLiteral;

            for (int i = v.integerValue; i > 0; i--) {
                newString = newString + stringLiteral;
            }

            return new RuntimeStringValue(newString);
        }

        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringLiteral.length() > v.toString().length()) {
                return new RuntimeBoolValue(true);
            }

            return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for >.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringLiteral.length() >= v.toString().length()) {
                return new RuntimeBoolValue(true);
            }

            return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringLiteral.length() < v.toString().length()) {
                return new RuntimeBoolValue(true);
            }

            return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for <.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringLiteral.length() <= v.toString().length()) {
                return new RuntimeBoolValue(true);
            }

            return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for <=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue)  {
            if (v.integerValue < stringLiteral.length() - 1) {
                String character = stringLiteral.charAt(v.integerValue);
                return new RuntimeStringValue(character);
            }

            runtimeError("Out of bounds.");
        }

        runtimeError("Type error for [].");
        return null;
    }
}