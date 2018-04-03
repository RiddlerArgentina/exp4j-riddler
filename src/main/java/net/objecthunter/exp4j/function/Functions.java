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

import java.util.Arrays;

/**
 * Class representing the builtin functions available for use in expressions
 */
public final class Functions {
    public static final Function SIN;
    public static final Function COS;
    public static final Function TAN;
    public static final Function LOG;
    public static final Function LOG1P;
    public static final Function ABS;
    public static final Function ACOS;
    public static final Function ASIN;
    public static final Function ATAN;
    public static final Function CBRT;
    public static final Function CEIL;
    public static final Function FLOOR;
    public static final Function SINH;
    public static final Function SQRT;
    public static final Function TANH;
    public static final Function COSH ;
    public static final Function POW;
    public static final Function EXP;
    public static final Function EXPM1;
    public static final Function LOG10;
    public static final Function LOG2;
    public static final Function SGN;
    public static final Function PI;
    public static final Function E;

    private static final Function[] BUILTIN = new Function[24];

    static {
        int i = 0;
        SIN = new Function("sin") {
            @Override
            public double apply(double... args) {
                return Math.sin(args[0]);
            }
        };
        BUILTIN[i++] = SIN;
        COS = new Function("cos") {
            @Override
            public double apply(double... args) {
                return Math.cos(args[0]);
            }
        };
        BUILTIN[i++] = COS;
        TAN = new Function("tan") {
            @Override
            public double apply(double... args) {
                return Math.tan(args[0]);
            }
        };
        BUILTIN[i++] = TAN;
        LOG = new Function("log") {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]);
            }
        };
        BUILTIN[i++] = LOG;
        LOG2 = new Function("log2") {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(2d);
            }
        };
        BUILTIN[i++] = LOG2;
        LOG10 = new Function("log10") {
            @Override
            public double apply(double... args) {
                return Math.log10(args[0]);
            }
        };
        BUILTIN[i++] = LOG10;
        LOG1P = new Function("log1p") {
            @Override
            public double apply(double... args) {
                return Math.log1p(args[0]);
            }
        };
        BUILTIN[i++] = LOG1P;
        ABS = new Function("abs") {
            @Override
            public double apply(double... args) {
                return Math.abs(args[0]);
            }
        };
        BUILTIN[i++] = ABS;
        ACOS = new Function("acos") {
            @Override
            public double apply(double... args) {
                return Math.acos(args[0]);
            }
        };
        BUILTIN[i++] = ACOS;
        ASIN = new Function("asin") {
            @Override
            public double apply(double... args) {
                return Math.asin(args[0]);
            }
        };
        BUILTIN[i++] = ASIN;
        ATAN = new Function("atan") {
            @Override
            public double apply(double... args) {
                return Math.atan(args[0]);
            }
        };
        BUILTIN[i++] = ATAN;
        CBRT = new Function("cbrt") {
            @Override
            public double apply(double... args) {
                return Math.cbrt(args[0]);
            }
        };
        BUILTIN[i++] = CBRT;
        FLOOR = new Function("floor") {
            @Override
            public double apply(double... args) {
                return Math.floor(args[0]);
            }
        };
        BUILTIN[i++] = FLOOR;
        SINH = new Function("sinh") {
            @Override
            public double apply(double... args) {
                return Math.sinh(args[0]);
            }
        };
        BUILTIN[i++] = SINH;
        SQRT = new Function("sqrt") {
            @Override
            public double apply(double... args) {
                return Math.sqrt(args[0]);
            }
        };
        BUILTIN[i++] = SQRT;
        TANH = new Function("tanh") {
            @Override
            public double apply(double... args) {
                return Math.tanh(args[0]);
            }
        };
        BUILTIN[i++] = TANH;
        COSH = new Function("cosh") {
            @Override
            public double apply(double... args) {
                return Math.cosh(args[0]);
            }
        };
        BUILTIN[i++] = COSH;
        CEIL = new Function("ceil") {
            @Override
            public double apply(double... args) {
                return Math.ceil(args[0]);
            }
        };
        BUILTIN[i++] = CEIL;
        POW = new Function("pow", 2) {
            @Override
            public double apply(double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        BUILTIN[i++] = POW;
        EXP = new Function("exp", 1) {
            @Override
            public double apply(double... args) {
                return Math.exp(args[0]);
            }
        };
        BUILTIN[i++] = EXP;
        EXPM1 = new Function("expm1", 1) {
            @Override
            public double apply(double... args) {
                return Math.expm1(args[0]);
            }
        };
        BUILTIN[i++] = EXPM1;
        SGN = new Function("signum", 1) {
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
        };
        BUILTIN[i++] = SGN;
        PI = new Function("pi", 0) {
            @Override
            public double apply(double... args) {
                return Math.PI;
            }
        };
        BUILTIN[i++] = PI;
        E = new Function("e", 0) {
            @Override
            public double apply(double... args) {
                return Math.E;
            }
        };
        BUILTIN[i++] = E;
    }

    private Functions() {
        // Don't let anyone initialize this class
    }

    /**
     * Retrieves all the functions in this class
     *
     * @return function list
     */
    public static Function[] getFunctions() {
        return Arrays.copyOf(BUILTIN, BUILTIN.length);
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
}
