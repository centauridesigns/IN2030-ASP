package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.*;

public class RuntimeFloatValue extends RuntimeValue {
    double doubleValue;

    public RuntimeFloatValue(double d) {
        doubleValue = d;
    }

    @Override
    String typeName() {
        return "float";
    }

    @Override
    public String showInfo() {
        String newString = "'" + String.valueOf(doubleValue) + "'";
        return newString;
    }

    @Override
    public String toString() {
        return String.valueOf(doubleValue);
    }
    
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (doubleValue == 0.0){
            return false;
        }return true;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return Math.round(doubleValue);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return doubleValue;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue(String.valueOf(doubleValue).length());
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (doubleValue == v.getFloatValue("==", where)) {
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);

        }else if (v instanceof RuntimeFloatValue){
            if (doubleValue == v.getFloatValue("==", where)) {
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);
        }
        
        runtimeError("Type error for ==.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(doubleValue == 0.0);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (doubleValue != v.getFloatValue("!=", where)) {
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);

        }else if (v instanceof RuntimeFloatValue){
            if (doubleValue != v.getFloatValue("!=", where)) {
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for !=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        //refaktorer etter testing
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(doubleValue + v.getFloatValue("+", where));

        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(doubleValue + v.getFloatValue("+", where));
        }

        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        //refaktorer etter testing
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(doubleValue - v.getFloatValue("-", where));

        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(doubleValue - v.getFloatValue("-", where));
        }

        runtimeError("Type error for -.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        //refaktorer etter testing
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(doubleValue * v.getFloatValue("*", where));

        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(doubleValue * v.getFloatValue("*", where));
        }

        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        //refaktorer etter testing
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(doubleValue / v.getFloatValue("/", where));

        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(doubleValue / v.getFloatValue("/", where));
        }

        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        //refaktorer etter testing
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(Math.floor(doubleValue / v.getFloatValue("//", where)));
        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue(Math.floor(doubleValue / v.getFloatValue("//", where)));
        }

        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        //refaktorer etter testing
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue( (doubleValue - v.getFloatValue("%", where)) * Math.floor(doubleValue / v.getFloatValue("//", where)));
        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue( (doubleValue - v.getFloatValue("%", where)) * Math.floor(doubleValue / v.getFloatValue("//", where)));
        }

        runtimeError("Type error for %.", where);
        return null;
    }


    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (doubleValue > v.getIntValue(">", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeFloatValue) {
            if (doubleValue > v.getFloatValue(">", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for >.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (doubleValue >= v.getIntValue(">=", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeFloatValue) {
            if (doubleValue > v.getFloatValue(">=", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (doubleValue < v.getIntValue("<", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeFloatValue) {
            if (doubleValue > v.getFloatValue(">", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for <.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (doubleValue <= v.getIntValue("<=", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeFloatValue) {
            if (doubleValue > v.getFloatValue(">", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for <=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(-doubleValue);
    }
    
    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(doubleValue);
    }
}
