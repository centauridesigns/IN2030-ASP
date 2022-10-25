package no.uio.ifi.asp.runtime;
import no.uio.ifi.asp.parser.*;

public class RuntimeIntegerValue extends RuntimeValue {
    long integerValue;

    public RuntimeIntegerValue(long i) {
        integerValue = i;
    }

    @Override
    String typeName() {
        return "Integer";
    }

    @Override
    public String showInfo() {
        String newString = "'" + String.valueOf(integerValue) + "'";
        return newString;
    }

    @Override
    public String toString() {
        return String.valueOf(integerValue);
    }
    
    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (integerValue == 0){
            return false;
        }return true;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return integerValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        double d = (double) integerValue;
        return d;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntegerValue(String.valueOf(integerValue).length());
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (integerValue == v.getIntValue("==", where)) {
                return new RuntimeBoolValue(true);
            }
            return new RuntimeBoolValue(false);

        }else if (v instanceof RuntimeFloatValue){
            if ( (double) integerValue == v.getFloatValue("==", where)) {
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
        return new RuntimeBoolValue(integerValue == 0);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (integerValue == v.getIntValue("!=", where)) {
                return new RuntimeBoolValue(false);
            }
            return new RuntimeBoolValue(true);

        }else if (v instanceof RuntimeFloatValue){
            if ( (double) integerValue == v.getFloatValue("!=", where)) {
                return new RuntimeBoolValue(false);
            }
            return new RuntimeBoolValue(true);
        }else if (v instanceof RuntimeNoneValue){
            return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for !=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(integerValue + v.getIntValue("+", where));

        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue((double)integerValue + v.getFloatValue("+", where));
        }
        //System.out.println("This value: " + Integer.toString((int)integerValue) + "second value: " + Integer.toString((int) v.getIntValue("+", where)));
        runtimeError("Type error for +.", where);
        return null;
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(integerValue - v.getIntValue("-", where));

        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue((double)integerValue - v.getFloatValue("-", where));
        }

        runtimeError("Type error for -.", where);
        return null;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(integerValue * v.getIntValue("*", where));

        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue((double)integerValue * v.getFloatValue("*", where));
        }

        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue((double) integerValue / v.getIntValue("/", where));

        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue((double)integerValue / v.getFloatValue("/", where));
        }

        runtimeError("Type error for /.", where);
        return null;
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(Math.floorDiv(integerValue, v.getIntValue("//", where)));
        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue((double)Math.floor(integerValue / v.getFloatValue("//", where)));
        }

        runtimeError("Type error for //.", where);
        return null;
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(Math.floorMod(integerValue, v.getIntValue("%", where)));
        }else if (v instanceof RuntimeFloatValue){
            return new RuntimeFloatValue( ((double)integerValue - v.getFloatValue("%", where)) * Math.floor((double)integerValue / v.getFloatValue("//", where)));
        }

        runtimeError("Type error for %.", where);
        return null;
    }


    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (integerValue > v.getIntValue(">", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeFloatValue) {
            if (integerValue > v.getFloatValue(">", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for >.", where);
        return null;
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (integerValue >= v.getIntValue(">=", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeFloatValue) {
            if (integerValue > v.getFloatValue(">=", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for >=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (integerValue < v.getIntValue("<", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeFloatValue) {
            if (integerValue > v.getFloatValue("<", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for <.", where);
        return null;
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (integerValue <= v.getIntValue("<=", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }else if (v instanceof RuntimeFloatValue) {
            if (integerValue > v.getFloatValue("<=", where)) {
                return new RuntimeBoolValue(true);
            }return new RuntimeBoolValue(false);
        }

        runtimeError("Type error for <=.", where);
        return null;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntegerValue(-integerValue);
    }
    
    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntegerValue(integerValue);
    }
}
