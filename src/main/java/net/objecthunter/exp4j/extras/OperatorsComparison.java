/*
* Copyright 2016-2018 Federico Vera
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
import net.objecthunter.exp4j.operator.Operator;

/**
 * This class contains the implementation of comparison and equality operators.
 * The returned values will always be 1.0 for {@code true} and 0.0 for {@code false}.
 * The precedence of this operators is as follows:<ul>
 * <li>All comparison operators have the same precedence</li>
 * <li>Comparison operators have higher precedence than boolean operators</li>
 * <li>Comparison operators have lower precedence than arithmetic operators</li>
 * <li>Equality operators have the lowest precedence (they should always be the last ones to be
 * evaluated)</li>
 * <li>Equality operators will consider numbers closer than {@link
 * OperatorsComparison#EQUALITY_THRESHOLD} to be equal (and viceversa)</li>
 * </ul>
 * <pre> To clarify the full evaluation order is:
 *   FIRST-&gt;   * / %  - +  &gt; &gt;= &lt; &lt;=  ¬  &amp;  |  == !=   &lt;-LAST
 *             -----  ---  ---------  -  -  -  -----
 *             ^^^ The dashes indicate the ones with the same precedence
 * So:
 * a + b * c &gt; d / e &amp; f &lt; g == (((a + (b * c)) &gt; (d / e)) &amp; (f &lt; g))
 *
 * </pre>
 *
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class OperatorsComparison {
    public static final int PRECEDENCE_COMPARISON = Operator.PRECEDENCE_ADDITION - 50;
    public static final int PRECEDENCE_EQUAL = Operator.PRECEDENCE_OR - 50;

    private static final int INDEX_OP_GT  = 0;
    private static final int INDEX_OP_GOE = 1;
    private static final int INDEX_OP_LT  = 2;
    private static final int INDEX_OP_LOE = 3;
    private static final int INDEX_OP_EQU = 4;
    private static final int INDEX_OP_NEQ = 5;

    /**
     * This is the threshold used to consider values equal, that is, if two values {@code a} and
     * {@code b} are separated by less than this threshold they will be considered to be equal, it
     * has a default value of {@value}
     */
    public static final double EQUALITY_THRESHOLD = Operator.BOOLEAN_THRESHOLD;

    private static final Operator[] OPERATORS = new Operator[6];

    static {
        OPERATORS[INDEX_OP_GT]= new Operator(">", 2, true, PRECEDENCE_COMPARISON) {
            @Override
            public double apply(final double... args) {
                final double a = args[0];
                final double b = args[1];
                return (a > b) ? 1 : 0;
            }
        };
        OPERATORS[INDEX_OP_GOE]= new Operator(">=", 2, true, PRECEDENCE_COMPARISON) {
            @Override
            public double apply(final double... args) {
                final double a = args[0];
                final double b = args[1];
                return (a >= b) ? 1 : 0;
            }
        };
        OPERATORS[INDEX_OP_LT]= new Operator("<", 2, false, PRECEDENCE_COMPARISON) {
            @Override
            public double apply(final double... args) {
                final double a = args[0];
                final double b = args[1];
                return (a < b) ? 1 : 0;
            }
        };
        OPERATORS[INDEX_OP_LOE]= new Operator("<=", 2, false, PRECEDENCE_COMPARISON) {
            @Override
            public double apply(final double... args) {
                final double a = args[0];
                final double b = args[1];
                return (a <= b) ? 1 : 0;
            }
        };
        OPERATORS[INDEX_OP_EQU]= new Operator("==", 2, true, PRECEDENCE_EQUAL) {
            @Override
            public double apply(final double... args) {
                final double a = args[0];
                final double b = args[1];
                return Math.abs(a - b) < EQUALITY_THRESHOLD ? 1 : 0;
            }
        };
        OPERATORS[INDEX_OP_NEQ]= new Operator("!=", 2, true, PRECEDENCE_EQUAL) {
            @Override
            public double apply(final double... args) {
                final double a = args[0];
                final double b = args[1];
                return Math.abs(a - b) >= EQUALITY_THRESHOLD ? 1 : 0;
            }
        };
    }

    public static Operator[] getOperators() {
        return Arrays.copyOf(OPERATORS, OPERATORS.length);
    }

    public static Operator getOperator(final String symbol) {
        switch(symbol) {
            case ">":  return OPERATORS[INDEX_OP_GT];
            case ">=": return OPERATORS[INDEX_OP_GOE];
            case "<":  return OPERATORS[INDEX_OP_LT];
            case "<=": return OPERATORS[INDEX_OP_LOE];
            case "==": return OPERATORS[INDEX_OP_EQU];
            case "!=": return OPERATORS[INDEX_OP_NEQ];
            default:
                return null;
        }
    }

    private OperatorsComparison() {
        // Don't let anyone initialize this class
    }

}
