package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.*;

public class RuntimeStringValue extends RuntimeValue {
    String stringLiteral;

    public RuntimeStringValue(String s) {
        stringLiteral = s;
    }

    @Override
    String typeName() {
        return "String";
    }

    @Override
    public String showInfo() {
        if (stringLiteral.indexOf('\'') >= 0) {
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
    public String getStringValue(String what, AspSyntax where) {
        return stringLiteral;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return !stringLiteral.isEmpty();
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (long) Integer.parseInt(stringLiteral);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return Double.parseDouble(stringLiteral);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue(stringLiteral.length());
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            if (stringLiteral == v.toString()) {
                return new RuntimeBoolValue(true);
            }

            return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
        }
        
        runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(stringLiteral == "");
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(!stringLiteral.equals(v.toString()));
        }
        
        else if (v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
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

            for (long i = v.getIntValue("*", where) - 1; i > 0; i--) {
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
            if (v.getIntValue("[]", where) <= stringLiteral.length() - 1) {
                int i = (int) v.getIntValue("[]", where);
                String character = Character.toString(stringLiteral.charAt(i));
                return new RuntimeStringValue(character);
            }

            runtimeError("Out of bounds.", where);
        }

        runtimeError("Type error for [].", where);
        return new RuntimeStringValue(Character.toString(this.stringLiteral.charAt((int)v.getIntValue("integer", where))));
    }
}