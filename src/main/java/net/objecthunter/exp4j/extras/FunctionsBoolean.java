/* 
* Copyright 2016 Federico Vera
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
package net.objecthunter.exp4j.extras;

import java.util.Arrays;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import static net.objecthunter.exp4j.operator.Operator.*;

/**
 * This class contains a set of commonly used boolean functions and constants.
 * The boolean value rules are the same the we use in the operators:<ol>
 * <li>if the absolute value is less than {@link Operator#BOOLEAN_THRESHOLD} it will be considered 
 * {@code false}.</li>
 * <li>if the absolute value is bigger or equal than {@link Operator#BOOLEAN_THRESHOLD} it will be
 * considered {@code true}</li>
 * <li>the recommended approach is to use functions or operators that return boolean values, like
 * the comparison operators or the {@code true()} and {@code flase} constants</li>
 * <li>the {@code true()} constant will always return 1.0 and {@code false()} will always return 
 * 0.0
 * </ol>
 * @see Operator#BOOLEAN_THRESHOLD
 * @see OperatorsComparison
 * @author Federico Vera {@literal <dktcoding [at] gmail>}
 */
public class FunctionsBoolean {
    private static final int INDEX_NOT   = 0;
    private static final int INDEX_AND   = 1;
    private static final int INDEX_OR    = 2;
    private static final int INDEX_XOR   = 3;
    private static final int INDEX_NAND  = 4;
    private static final int INDEX_NOR   = 5;
    private static final int INDEX_XNOR  = 6;
    private static final int INDEX_FALSE = 7;
    private static final int INDEX_TRUE  = 8;

    private static final Function[] FUNCTIONS = new Function[9];
    
    static {
        FUNCTIONS[INDEX_NOT] = new Function("not") {
            @Override
            public double apply(double... args) {
                return (Math.abs(args[0]) >= BOOLEAN_THRESHOLD) ? 0 : 1;
            }
        };
        FUNCTIONS[INDEX_AND] = new Function("and", 2) {
            @Override
            public double apply(double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a & b) ? 1 : 0;
            }
        };
        FUNCTIONS[INDEX_OR] = new Function("or", 2) {
            @Override
            public double apply(double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a | b) ? 1 : 0;
            }
        };
        FUNCTIONS[INDEX_XOR] = new Function("xor", 2) {
            @Override
            public double apply(double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a ^ b) ? 1 : 0;
            }
        };
        FUNCTIONS[INDEX_NAND] = new Function("nand", 2) {
            @Override
            public double apply(double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a & b) ? 0 : 1;
            }
        };
        FUNCTIONS[INDEX_NOR] = new Function("nor", 2) {
            @Override
            public double apply(double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a | b) ? 0 : 1;
            }
        };
        FUNCTIONS[INDEX_XNOR] = new Function("xnor", 2) {
            @Override
            public double apply(double... args) {
                final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
                final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
                return (a ^ b) ? 0 : 1;
            }
        };
        FUNCTIONS[INDEX_FALSE] = new Function("false", 0) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        };
        FUNCTIONS[INDEX_TRUE] = new Function("true", 0) {
            @Override
            public double apply(double... args) {
                return 1;
            }
        };
    }
    
    public static Function[] getFunctions() {
        return Arrays.copyOf(FUNCTIONS, FUNCTIONS.length);
    }

    /**
     * Get the function for a given name
     * @param name the name of the function
     * @return a Function instance
     */
    public static Function getFunction(final String name) {
        switch (name) {
            case "not"  : return FUNCTIONS[INDEX_NOT  ];
            case "and"  : return FUNCTIONS[INDEX_AND  ];
            case "or"   : return FUNCTIONS[INDEX_OR   ];
            case "xor"  : return FUNCTIONS[INDEX_XOR  ];
            case "nand" : return FUNCTIONS[INDEX_NAND ];
            case "nor"  : return FUNCTIONS[INDEX_NOR  ];
            case "xnor" : return FUNCTIONS[INDEX_XNOR ];
            case "false": return FUNCTIONS[INDEX_FALSE];
            case "true" : return FUNCTIONS[INDEX_TRUE ];
            default:      return null;
        }
    }
}
