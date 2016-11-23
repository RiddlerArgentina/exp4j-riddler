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
public abstract class Operators {
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
    
    private static final Operator[] builtinOperators = new Operator[11];

    static {
        builtinOperators[INDEX_ADDITION]= new Operator("+", 2, true, PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] + args[1];
            }
        };
        builtinOperators[INDEX_SUBTRACTION]= new Operator("-", 2, true, PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] - args[1];
            }
        };
        builtinOperators[INDEX_UNARYMINUS]= new Operator("-", 1, false, PRECEDENCE_UNARY_MINUS) {
            @Override
            public double apply(final double... args) {
                return -args[0];
            }
        };
        builtinOperators[INDEX_UNARYPLUS]= new Operator("+", 1, false, PRECEDENCE_UNARY_PLUS) {
            @Override
            public double apply(final double... args) {
                return args[0];
            }
        };
        builtinOperators[INDEX_MUTLIPLICATION]= new Operator("*", 2, true, PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
                return args[0] * args[1];
            }
        };
        builtinOperators[INDEX_DIVISION]= new Operator("/", 2, true, PRECEDENCE_DIVISION) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return args[0] / args[1];
            }
        };
        builtinOperators[INDEX_POWER]= new Operator("^", 2, false, PRECEDENCE_POWER) {
            @Override
            public double apply(final double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        builtinOperators[INDEX_MODULO]= new Operator("%", 2, true, PRECEDENCE_MODULO) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return args[0] % args[1];
            }
        };
        builtinOperators[INDEX_OP_AND]= new Operator("&", 2, true, PRECEDENCE_AND) {
            @Override
            public double apply(final double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a & b) ? 1 : 0;
            }
        };
        builtinOperators[INDEX_OP_OR]= new Operator("|", 2, true, PRECEDENCE_OR) {
            @Override
            public double apply(final double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a | b) ? 1 : 0;
            }
        };
        builtinOperators[INDEX_OP_NOT]= new Operator("¬", 1, false, PRECEDENCE_NOT) {
            @Override
            public double apply(final double... args) {
                return (Math.abs(args[0]) < BOOLEAN_THRESHOLD) ? 1 : 0;
            }
        };
    }

    public static Operator getBuiltinOperator(final char symbol, final int numArguments) {
        switch(symbol) {
            case '+':
                if (numArguments != 1) {
                    return builtinOperators[INDEX_ADDITION];
                } else{
                    return builtinOperators[INDEX_UNARYPLUS];
                }
            case '-':
                if (numArguments != 1) {
                    return builtinOperators[INDEX_SUBTRACTION];
                } else{
                    return builtinOperators[INDEX_UNARYMINUS];
                }
            case '*': return builtinOperators[INDEX_MUTLIPLICATION];
            case '/': return builtinOperators[INDEX_DIVISION];
            case '^': return builtinOperators[INDEX_POWER];
            case '%': return builtinOperators[INDEX_MODULO];
            case '&': return builtinOperators[INDEX_OP_AND];
            case '|': return builtinOperators[INDEX_OP_OR];
            case '¬': return builtinOperators[INDEX_OP_NOT];
            default:
                return null;
        }
    }

}
