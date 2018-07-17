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

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

/**
 * This class contains a small set of useful functions that don't really fit in the
 * other categories.
 *
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public final class FunctionsMisc {
    /**
     * Equality function.
     *
     * The test is performed using a threshold given by
     * {@link Operator#BOOLEAN_THRESHOLD}.
     *
     * @see Operator#BOOLEAN_THRESHOLD
     */
    public static final Function EQUAL = new Equals();

    /**
     * Branching function.
     *
     * @see Operator#BOOLEAN_THRESHOLD
     * @see OperatorsComparison#OP_EQU
     * @see OperatorsComparison#OP_GOE
     * @see OperatorsComparison#OP_GT
     * @see OperatorsComparison#OP_LOE
     * @see OperatorsComparison#OP_LT
     * @see OperatorsComparison#OP_NEQ
     */
    public static final Function IF    = new If();

    /**
     * Cardinal Sin (non-normalized).
     */
    public static final Function SINC  = new Sinc();

    /**
     * Retrieves the value of {@link Double#POSITIVE_INFINITY}.
     */
    public static final Function INFINITY  = new Infinity();

    /**
     * Tells if a number is {@link Double#NaN}.
     */
    public static final Function IS_NAN  = new IsNaN();

    /**
     * This is the threshold used to consider values equal, that is, if two values {@code a} and
     * {@code b} are separated by less than this threshold they will be considered to be equal, it
     * has a default value of {@value}
     */
    public static final double EQUALITY_THRESHOLD = Operator.BOOLEAN_THRESHOLD;

    /**
     * Array with all the available functions
     *
     * @return {@link Function} array
     * @see FunctionsMisc#getFunction(java.lang.String)
     */
    public static Function[] getFunctions() {
        return new Function[]{EQUAL, IF, SINC, INFINITY, IS_NAN};
    }

    /**
     * Get the function for a given name
     * @param name the name of the function
     * @return a Function instance
     * @see FunctionsMisc#getFunctions()
     */
    public static Function getFunction(final String name) {
        switch (name) {
            case "equal": return EQUAL;
            case "if"   : return IF;
            case "sinc" : return SINC;
            case "inf"  : return INFINITY;
            case "isnan": return IS_NAN;
            default:      return null;
        }
    }

    private FunctionsMisc() {
        // Don't let anyone initialize this class
    }

    private static final class Equals extends Function {
        private static final long serialVersionUID = 2388827649030518290L;
        Equals() { super("equal", 2); }
        @Override
        public double apply(double... args) {
            final double  a = args[0];
            final double  b = args[1];
            return Math.abs(a - b) < EQUALITY_THRESHOLD ? 1 : 0;
        }
    }

    private static final class If extends Function {
        private static final long serialVersionUID = 3865326455639650003L;
        If() { super("if", 3); }
        @Override
        public double apply(double... args) {
            final boolean a = args[0] >= EQUALITY_THRESHOLD;
            final double  t = args[1];
            final double  f = args[2];
            return a ? t : f;
        }
    }

    private static final class Sinc extends Function {
        private static final long serialVersionUID = -3749047550580483555L;
        Sinc() { super("sinc", 1); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            return a == 0.0 ? 1 : Math.sin(a) / a;
        }
    }

    private static final class Infinity extends Function {
        private static final long serialVersionUID = -3749047550580483555L;
        Infinity() { super("inf", 0); }
        @Override
        public double apply(double... args) {
            return Double.POSITIVE_INFINITY;
        }
    }

    private static final class IsNaN extends Function {
        private static final long serialVersionUID = -3749047550580483555L;
        IsNaN() { super("isnan", 1); }
        @Override
        public double apply(double... args) {
            final double val = args[0];
            return Double.isNaN(val) ? 1.0 : 0.0;
        }
    }
}
