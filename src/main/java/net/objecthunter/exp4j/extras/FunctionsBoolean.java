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
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public final class FunctionsBoolean {
    public static final Function NOT   = new Not();
    public static final Function AND   = new And();
    public static final Function OR    = new Or();
    public static final Function XOR   = new Xor();
    public static final Function NAND  = new Nand();
    public static final Function NOR   = new Nor();
    public static final Function XNOR  = new Xnor();
    public static final Function FALSE = new False();
    public static final Function TRUE  = new True();

    public static Function[] getFunctions() {
        return new Function[] {NOT, AND, OR, XOR, NAND, NOR, XNOR, FALSE, TRUE};
    }

    /**
     * Get the function for a given name
     * @param name the name of the function
     * @return a Function instance
     */
    public static Function getFunction(final String name) {
        switch (name) {
            case "not"  : return NOT;
            case "and"  : return AND;
            case "or"   : return OR;
            case "xor"  : return XOR;
            case "nand" : return NAND;
            case "nor"  : return NOR;
            case "xnor" : return XNOR;
            case "false": return FALSE;
            case "true" : return TRUE;
            default:      return null;
        }
    }

    private FunctionsBoolean() {
        // Don't let anyone initialize this class
    }

    private static final class Not extends Function {
        private static final long serialVersionUID = 5548754689040328198L;
        Not() { super("not", 1); }
        @Override
        public double apply(double... args) {
            return (Math.abs(args[0]) >= BOOLEAN_THRESHOLD) ? 0 : 1;
        }
    }

    private static final class And extends Function {
        private static final long serialVersionUID = 610825426695578129L;
        And() { super("and", 2); }
        @Override
        public double apply(double... args) {
            final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
            final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
            return (a & b) ? 1 : 0;
        }
    }

    private static final class Or extends Function {
        private static final long serialVersionUID = -6688682627798315082L;
        Or() { super("or", 2); }
        @Override
        public double apply(double... args) {
            final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
            final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
            return (a | b) ? 1 : 0;
        }
    }

    private static final class Xor extends Function {
        private static final long serialVersionUID = 9162299011098408477L;
        Xor() { super("xor", 2); }
        @Override
        public double apply(double... args) {
            final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
            final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
            return (a ^ b) ? 1 : 0;
        }
    }

    private static final class Nand extends Function {
        private static final long serialVersionUID = -5696690796736889084L;
        Nand() { super("nand", 2); }
        @Override
        public double apply(double... args) {
            final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
            final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
            return (a & b) ? 0 : 1;
        }
    }

    private static final class Nor extends Function {
        private static final long serialVersionUID = 7443039549525626711L;
        Nor() { super("nor", 2); }
        @Override
        public double apply(double... args) {
            final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
            final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
            return (a | b) ? 0 : 1;
        }
    }

    private static final class Xnor extends Function {
        private static final long serialVersionUID = -7602049045810375102L;
        Xnor() { super("xnor", 2); }
        @Override
        public double apply(double... args) {
            final boolean a = Math.abs(args[0]) >= BOOLEAN_THRESHOLD;
            final boolean b = Math.abs(args[1]) >= BOOLEAN_THRESHOLD;
            return (a ^ b) ? 0 : 1;
        }
    }

    private static final class False extends Function {
        private static final long serialVersionUID = 8888653636237556280L;
        False() { super("false", 0); }
        @Override
        public double apply(double... args) {
            return 0;
        }
    }

    private static final class True extends Function {
        private static final long serialVersionUID = -5934349523714582684L;
        True() { super("true", 0); }
        @Override
        public double apply(double... args) {
            return 1;
        }
    }
}
