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
public final class OperatorsComparison {
    public static final int PRECEDENCE_COMPARISON = Operator.PRECEDENCE_ADDITION - 50;
    public static final int PRECEDENCE_EQUAL = Operator.PRECEDENCE_OR - 50;

    public static final Operator OP_GT  = new OpGT();
    public static final Operator OP_GOE = new OpGOE();
    public static final Operator OP_LT  = new OpLT();
    public static final Operator OP_LOE = new OpLOE();
    public static final Operator OP_EQU = new OpEqu();
    public static final Operator OP_NEQ = new OpNeq();

    /**
     * This is the threshold used to consider values equal, that is, if two values {@code a} and
     * {@code b} are separated by less than this threshold they will be considered to be equal, it
     * has a default value of {@value}
     */
    public static final double EQUALITY_THRESHOLD = Operator.BOOLEAN_THRESHOLD;

    public static Operator[] getOperators() {
        return new Operator[]{OP_GT, OP_GOE, OP_LT, OP_LOE, OP_EQU, OP_NEQ};
    }

    public static Operator getOperator(final String symbol) {
        switch(symbol) {
            case ">":  return OP_GT;
            case ">=": return OP_GOE;
            case "<":  return OP_LT;
            case "<=": return OP_LOE;
            case "==": return OP_EQU;
            case "!=": return OP_NEQ;
            default:
                return null;
        }
    }

    private OperatorsComparison() {
        // Don't let anyone initialize this class
    }

    private static final class OpGT extends Operator {
        private static final long serialVersionUID = -3577940809939988953L;
        OpGT() { super(">", 2, true, PRECEDENCE_COMPARISON); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            final double b = args[1];
            return (a > b) ? 1 : 0;
        }
    }

    private static final class OpGOE extends Operator {
        private static final long serialVersionUID = 4458035942461875803L;
        OpGOE() { super(">=", 2, true, PRECEDENCE_COMPARISON); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            final double b = args[1];
            return (a >= b) ? 1 : 0;
        }
    }

    private static final class OpLT extends Operator {
        private static final long serialVersionUID = -1309870683874217267L;
        OpLT() { super("<", 2, false, PRECEDENCE_COMPARISON); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            final double b = args[1];
            return (a < b) ? 1 : 0;
        }
    }

    private static final class OpLOE extends Operator {
        private static final long serialVersionUID = 7679772268080021230L;
        OpLOE() { super("<=", 2, false, PRECEDENCE_COMPARISON); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            final double b = args[1];
            return (a <= b) ? 1 : 0;
        }
    }

    private static final class OpEqu extends Operator {
        private static final long serialVersionUID = 7987791934260015206L;
        OpEqu() { super("==", 2, true, PRECEDENCE_EQUAL); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            final double b = args[1];
            return Math.abs(a - b) < EQUALITY_THRESHOLD ? 1 : 0;
        }
    }

    private static final class OpNeq extends Operator {
        private static final long serialVersionUID = -6219775221131013725L;
        OpNeq() { super("!=", 2, true, PRECEDENCE_EQUAL); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            final double b = args[1];
            return Math.abs(a - b) >= EQUALITY_THRESHOLD ? 1 : 0;
        }
    }
}
