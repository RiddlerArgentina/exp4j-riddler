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
package net.objecthunter.exp4j.operator;

import java.util.Arrays;

import static net.objecthunter.exp4j.operator.Operator.*;

/**
 * This class implements all of the built-in operators both arithmetic and boolean.
 * Boolean values will be treated as follows:<ol>
 * <li>if the absolute value is less than 1e-12 it will be considered {@code false}. About this...
 * I know is not the ideal approach, but we have a LOT of arithmetic "hacks" all around some
 * projects and this behavior is actually expected.</li>
 * <li>if the absolute value is bigger or equal than 1e-12 it will be considered {@code true}</li>
 * <li>the boolean results will <b>always</b> be {@code 1.0} for {@code true} and {@code 0.0} for
 * {@code false}</li>
 * <li>boolean operations will always have lower precedence than arithmetic operations (i.e. they
 * will be evaluated last)</li>
 * <li>The precedence (order) of the boolean operators will always be ¬ &amp; | (in accordance to
 * the analogy with arithmetic operators), so that:<pre>
 *             a &amp; b| b &amp; ¬c -&gt; ((a &amp; b) | (b &amp; (¬c)))</pre></li>
 * </ol>
 */
public final class Operators {
    private static final int INDEX_ADDITION = 0;
    private static final int INDEX_SUBTRACTION = 1;
    private static final int INDEX_MUTLIPLICATION = 2;
    private static final int INDEX_DIVISION = 3;
    private static final int INDEX_POWER = 4;
    private static final int INDEX_MODULO = 5;
    private static final int INDEX_UNARYMINUS = 6;
    private static final int INDEX_UNARYPLUS = 7;

    private static final int INDEX_OP_AND = 8;
    private static final int INDEX_OP_OR  = 9;
    private static final int INDEX_OP_NOT = 10;

    private static final int INDEX_FACTORIAL = 11;

    private static final Operator[] BUILTIN = new Operator[12];

    static {
        BUILTIN[INDEX_ADDITION]= new Operator("+", 2, true, PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] + args[1];
            }
        };
        BUILTIN[INDEX_SUBTRACTION]= new Operator("-", 2, true, PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] - args[1];
            }
        };
        BUILTIN[INDEX_UNARYMINUS]= new Operator("-", 1, false, PRECEDENCE_UNARY_MINUS) {
            @Override
            public double apply(final double... args) {
                return -args[0];
            }
        };
        BUILTIN[INDEX_UNARYPLUS]= new Operator("+", 1, false, PRECEDENCE_UNARY_PLUS) {
            @Override
            public double apply(final double... args) {
                return args[0];
            }
        };
        BUILTIN[INDEX_MUTLIPLICATION]= new Operator("*", 2, true, PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
                return args[0] * args[1];
            }
        };
        BUILTIN[INDEX_DIVISION]= new Operator("/", 2, true, PRECEDENCE_DIVISION) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return args[0] / args[1];
            }
        };
        BUILTIN[INDEX_POWER]= new Operator("^", 2, false, PRECEDENCE_POWER) {
            @Override
            public double apply(final double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        BUILTIN[INDEX_MODULO]= new Operator("%", 2, true, PRECEDENCE_MODULO) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return args[0] % args[1];
            }
        };
        BUILTIN[INDEX_OP_AND]= new Operator("&", 2, true, PRECEDENCE_AND) {
            @Override
            public double apply(final double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a & b) ? 1 : 0;
            }
        };
        BUILTIN[INDEX_OP_OR]= new Operator("|", 2, true, PRECEDENCE_OR) {
            @Override
            public double apply(final double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a | b) ? 1 : 0;
            }
        };
        BUILTIN[INDEX_OP_NOT]= new Operator("¬", 1, false, PRECEDENCE_NOT) {
            @Override
            public double apply(final double... args) {
                return (Math.abs(args[0]) < BOOLEAN_THRESHOLD) ? 1 : 0;
            }
        };
        BUILTIN[INDEX_FACTORIAL]= new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    String msg = "Operand for factorial has to be an integer";
                    throw new IllegalArgumentException(msg);
                }
                if (arg < 0) {
                    String msg = "The operand of the factorial can not be less than zero";
                    throw new IllegalArgumentException(msg);
                }
                if (arg > 171) {
                    String msg = "The operand of the factorial can not be more than 171";
                    throw new IllegalArgumentException(msg);
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };
    }

    public static Operator[] getOperators() {
        return Arrays.copyOf(BUILTIN, BUILTIN.length);
    }

    public static Operator getBuiltinOperator(final char symbol, final int numArguments) {
        switch(symbol) {
            case '+':
                if (numArguments != 1) {
                    return BUILTIN[INDEX_ADDITION];
                } else{
                    return BUILTIN[INDEX_UNARYPLUS];
                }
            case '-':
                if (numArguments != 1) {
                    return BUILTIN[INDEX_SUBTRACTION];
                } else{
                    return BUILTIN[INDEX_UNARYMINUS];
                }
            case '*': return BUILTIN[INDEX_MUTLIPLICATION];
            case '/': return BUILTIN[INDEX_DIVISION];
            case '^': return BUILTIN[INDEX_POWER];
            case '%': return BUILTIN[INDEX_MODULO];
            case '&': return BUILTIN[INDEX_OP_AND];
            case '|': return BUILTIN[INDEX_OP_OR];
            case '¬': return BUILTIN[INDEX_OP_NOT];
            case '!':
                if (numArguments != 1) {
                    return BUILTIN[INDEX_FACTORIAL];
                }
            default:
                return null;
        }
    }

    private Operators() {
        // Don't let anyone initialize this class
    }

}
