/*
* Copyright 2014 Frank Asseg
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.objecthunter.exp4j.function;

import java.io.Serializable;

/**
 * Class representing the builtin functions available for use in expressions
 */
public final class Functions {
    public static final Function SIN   = new Sin();
    public static final Function COS   = new Cos();
    public static final Function TAN   = new Tan();
    public static final Function LOG   = new Log();
    public static final Function LOG1P = new Log1p();
    public static final Function LOG10 = new Log10();
    public static final Function LOG2  = new Log2();
    public static final Function ABS   = new Abs();
    public static final Function ACOS  = new ACos();
    public static final Function ASIN  = new ASin();
    public static final Function ATAN  = new ATan();
    public static final Function CBRT  = new CBRT();
    public static final Function CEIL  = new Ceil();
    public static final Function FLOOR = new Floor();
    public static final Function SINH  = new Sinh();
    public static final Function SQRT  = new Sqrt();
    public static final Function TANH  = new Tanh();
    public static final Function COSH  = new Cosh();
    public static final Function POW   = new Pow();
    public static final Function EXP   = new Exp();
    public static final Function EXPM1 = new Expm1();
    public static final Function SGN   = new Signum();
    public static final Function PI    = new Pi();
    public static final Function E     = new E();
    
    private Functions() {
        // Don't let anyone initialize this class
    }

    /**
     * Get the builtin function for a given name
     * @param name the name of the function
     * @return a Function instance
     */
    public static Function getBuiltinFunction(final String name) {
        switch (name) {
            case "sin":   return SIN;
            case "cos":   return COS;
            case "tan":   return TAN;
            case "asin":  return ASIN;
            case "acos":  return ACOS;
            case "atan":  return ATAN;
            case "sinh":  return SINH;
            case "cosh":  return COSH;
            case "tanh":  return TANH;
            case "abs":   return ABS;
            case "log":   return LOG;
            case "log10": return LOG10;
            case "log2":  return LOG2;
            case "log1p": return LOG1P;
            case "ceil":  return CEIL;
            case "floor": return FLOOR;
            case "sqrt":  return SQRT;
            case "cbrt":  return CBRT;
            case "pow":   return POW;
            case "exp":   return EXP;
            case "expm1": return EXPM1;
            case "signum":return SGN;
            case "pi":    return PI;
            case "e":     return E;
            default:
                return null;
        }
    }

    private static final class Sin extends Function implements Serializable {
        Sin() { super("sin"); }
        @Override
        public double apply(double... args) {
            return Math.sin(args[0]);
        }
    }

    private static final class Cos extends Function implements Serializable {
        Cos() { super("cos"); }
        @Override
        public double apply(double... args) {
            return Math.cos(args[0]);
        }
    }

    private static final class Tan extends Function implements Serializable {
        Tan() { super("tan"); }
        @Override
        public double apply(double... args) {
            return Math.tan(args[0]);
        }
    }

    private static final class Log extends Function implements Serializable {
        Log() { super("log"); }
        @Override
        public double apply(double... args) {
            return Math.log(args[0]);
        }
    }

    private static final class Log2 extends Function implements Serializable {
        Log2() { super("log2"); }
        @Override
        public double apply(double... args) {
            return Math.log(args[0]) / Math.log(2d);
        }
    }

    private static final class Log10 extends Function implements Serializable {
        Log10() { super("log10"); }
        @Override
        public double apply(double... args) {
            return Math.log10(args[0]);
        }
    }

    private static final class Log1p extends Function implements Serializable {
        Log1p() { super("log1p"); }
        @Override
        public double apply(double... args) {
            return Math.log1p(args[0]);
        }
    }

    private static final class Abs extends Function implements Serializable {
        Abs() { super("abs"); }
        @Override
        public double apply(double... args) {
            return Math.abs(args[0]);
        }
    }

    private static final class ACos extends Function implements Serializable {
        ACos() { super("acos"); }
        @Override
        public double apply(double... args) {
            return Math.acos(args[0]);
        }
    }

    private static final class ASin extends Function implements Serializable {
        ASin() { super("asin"); }
        @Override
        public double apply(double... args) {
            return Math.asin(args[0]);
        }
    }

    private static final class ATan extends Function implements Serializable {
        ATan() { super("atan"); }
        @Override
        public double apply(double... args) {
            return Math.atan(args[0]);
        }
    }

    private static final class CBRT extends Function implements Serializable {
        CBRT() { super("cbrt"); }
        @Override
        public double apply(double... args) {
            return Math.cbrt(args[0]);
        }
    }

    private static final class Floor extends Function implements Serializable {
        Floor() { super("floor"); }
        @Override
        public double apply(double... args) {
            return Math.floor(args[0]);
        }
    }

    private static final class Sinh extends Function implements Serializable {
        Sinh() { super("sinh"); }
        @Override
        public double apply(double... args) {
            return Math.sinh(args[0]);
        }
    }

    private static final class Tanh extends Function implements Serializable {
        Tanh() { super("tanh"); }
        @Override
        public double apply(double... args) {
            return Math.tanh(args[0]);
        }
    }

    private static final class Cosh extends Function implements Serializable {
        Cosh() { super("cosh"); }
        @Override
        public double apply(double... args) {
            return Math.cosh(args[0]);
        }
    }

    private static final class Ceil extends Function implements Serializable {
        Ceil() { super("ceil"); }
        @Override
        public double apply(double... args) {
            return Math.ceil(args[0]);
        }
    }

    private static final class Sqrt extends Function implements Serializable {
        Sqrt() { super("sqrt"); }
        @Override
        public double apply(double... args) {
            return Math.sqrt(args[0]);
        }
    }

    private static final class Pow extends Function implements Serializable {
        Pow() { super("pow", 2); }
        @Override
        public double apply(double... args) {
            return Math.pow(args[0], args[1]);
        }
    }

    private static final class Exp extends Function implements Serializable {
        Exp() { super("exp"); }
        @Override
        public double apply(double... args) {
            return Math.exp(args[0]);
        }
    }

    private static final class Expm1 extends Function implements Serializable {
        Expm1() { super("expm1"); }
        @Override
        public double apply(double... args) {
            return Math.expm1(args[0]);
        }
    }

    private static final class Signum extends Function implements Serializable {
        Signum() { super("signum"); }
        @Override
        public double apply(double... args) {
            if (args[0] > 0) {
                return 1;
            } else if (args[0] < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private static final class Pi extends Function implements Serializable {
        Pi () { super("pi", 0); }
        @Override
        public double apply(double... args) {
            return Math.PI;
        }
    }

    private static final class E extends Function implements Serializable {
        E() { super("e", 0); }
        @Override
        public double apply(double... args) {
            return Math.E;
        }
    }
}