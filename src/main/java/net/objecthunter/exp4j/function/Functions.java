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
    private static final int INDEX_SIN = 0;
    private static final int INDEX_COS = 1;
    private static final int INDEX_TAN = 2;
    private static final int INDEX_LOG = 3;
    private static final int INDEX_LOG1P = 4;
    private static final int INDEX_ABS = 5;
    private static final int INDEX_ACOS = 6;
    private static final int INDEX_ASIN = 7;
    private static final int INDEX_ATAN = 8;
    private static final int INDEX_CBRT = 9;
    private static final int INDEX_CEIL = 10;
    private static final int INDEX_FLOOR = 11;
    private static final int INDEX_SINH = 12;
    private static final int INDEX_SQRT = 13;
    private static final int INDEX_TANH = 14;
    private static final int INDEX_COSH = 15;
    private static final int INDEX_POW = 16;
    private static final int INDEX_EXP = 17;
    private static final int INDEX_EXPM1 = 18;
    private static final int INDEX_LOG10 = 19;
    private static final int INDEX_LOG2 = 20;
    private static final int INDEX_SGN = 21;
    private static final int INDEX_PI = 22;
    private static final int INDEX_E = 23;

    private static final Function[] BUILTIN = new Function[24];

    static {
        BUILTIN[INDEX_SIN] = new Function("sin") {
            @Override
            public double apply(double... args) {
                return Math.sin(args[0]);
            }
        };
        BUILTIN[INDEX_COS] = new Function("cos") {
            @Override
            public double apply(double... args) {
                return Math.cos(args[0]);
            }
        };
        BUILTIN[INDEX_TAN] = new Function("tan") {
            @Override
            public double apply(double... args) {
                return Math.tan(args[0]);
            }
        };
        BUILTIN[INDEX_LOG] = new Function("log") {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]);
            }
        };
        BUILTIN[INDEX_LOG2] = new Function("log2") {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(2d);
            }
        };
        BUILTIN[INDEX_LOG10] = new Function("log10") {
            @Override
            public double apply(double... args) {
                return Math.log10(args[0]);
            }
        };
        BUILTIN[INDEX_LOG1P] = new Function("log1p") {
            @Override
            public double apply(double... args) {
                return Math.log1p(args[0]);
            }
        };
        BUILTIN[INDEX_ABS] = new Function("abs") {
            @Override
            public double apply(double... args) {
                return Math.abs(args[0]);
            }
        };
        BUILTIN[INDEX_ACOS] = new Function("acos") {
            @Override
            public double apply(double... args) {
                return Math.acos(args[0]);
            }
        };
        BUILTIN[INDEX_ASIN] = new Function("asin") {
            @Override
            public double apply(double... args) {
                return Math.asin(args[0]);
            }
        };
        BUILTIN[INDEX_ATAN] = new Function("atan") {
            @Override
            public double apply(double... args) {
                return Math.atan(args[0]);
            }
        };
        BUILTIN[INDEX_CBRT] = new Function("cbrt") {
            @Override
            public double apply(double... args) {
                return Math.cbrt(args[0]);
            }
        };
        BUILTIN[INDEX_FLOOR] = new Function("floor") {
            @Override
            public double apply(double... args) {
                return Math.floor(args[0]);
            }
        };
        BUILTIN[INDEX_SINH] = new Function("sinh") {
            @Override
            public double apply(double... args) {
                return Math.sinh(args[0]);
            }
        };
        BUILTIN[INDEX_SQRT] = new Function("sqrt") {
            @Override
            public double apply(double... args) {
                return Math.sqrt(args[0]);
            }
        };
        BUILTIN[INDEX_TANH] = new Function("tanh") {
            @Override
            public double apply(double... args) {
                return Math.tanh(args[0]);
            }
        };
        BUILTIN[INDEX_COSH] = new Function("cosh") {
            @Override
            public double apply(double... args) {
                return Math.cosh(args[0]);
            }
        };
        BUILTIN[INDEX_CEIL] = new Function("ceil") {
            @Override
            public double apply(double... args) {
                return Math.ceil(args[0]);
            }
        };
        BUILTIN[INDEX_POW] = new Function("pow", 2) {
            @Override
            public double apply(double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        BUILTIN[INDEX_EXP] = new Function("exp", 1) {
            @Override
            public double apply(double... args) {
                return Math.exp(args[0]);
            }
        };
        BUILTIN[INDEX_EXPM1] = new Function("expm1", 1) {
            @Override
            public double apply(double... args) {
                return Math.expm1(args[0]);
            }
        };
        BUILTIN[INDEX_SGN] = new Function("signum", 1) {
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
        BUILTIN[INDEX_PI] = new Function("pi", 0) {
            @Override
            public double apply(double... args) {
                return Math.PI;
            }
        };
        BUILTIN[INDEX_E] = new Function("e", 0) {
            @Override
            public double apply(double... args) {
                return Math.E;
            }
        };
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
            case "sin":   return BUILTIN[INDEX_SIN];
            case "cos":   return BUILTIN[INDEX_COS];
            case "tan":   return BUILTIN[INDEX_TAN];
            case "asin":  return BUILTIN[INDEX_ASIN];
            case "acos":  return BUILTIN[INDEX_ACOS];
            case "atan":  return BUILTIN[INDEX_ATAN];
            case "sinh":  return BUILTIN[INDEX_SINH];
            case "cosh":  return BUILTIN[INDEX_COSH];
            case "tanh":  return BUILTIN[INDEX_TANH];
            case "abs":   return BUILTIN[INDEX_ABS];
            case "log":   return BUILTIN[INDEX_LOG];
            case "log10": return BUILTIN[INDEX_LOG10];
            case "log2":  return BUILTIN[INDEX_LOG2];
            case "log1p": return BUILTIN[INDEX_LOG1P];
            case "ceil":  return BUILTIN[INDEX_CEIL];
            case "floor": return BUILTIN[INDEX_FLOOR];
            case "sqrt":  return BUILTIN[INDEX_SQRT];
            case "cbrt":  return BUILTIN[INDEX_CBRT];
            case "pow":   return BUILTIN[INDEX_POW];
            case "exp":   return BUILTIN[INDEX_EXP];
            case "expm1": return BUILTIN[INDEX_EXPM1];
            case "signum":return BUILTIN[INDEX_SGN];
            case "pi":    return BUILTIN[INDEX_PI];
            case "e":     return BUILTIN[INDEX_E];
            default:
                return null;
        }
    }
}
