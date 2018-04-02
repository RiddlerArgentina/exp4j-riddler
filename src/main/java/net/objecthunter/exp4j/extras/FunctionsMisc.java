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
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

/**
 * This class contains a small set of useful functions that don't really fit in the
 * other categories.
 *
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public final class FunctionsMisc {
    public static final Function EQUAL;
    public static final Function IF;
    public static final Function SINC;

    /**
     * This is the threshold used to consider values equal, that is, if two values {@code a} and
     * {@code b} are separated by less than this threshold they will be considered to be equal, it
     * has a default value of {@value}
     */
    public static final double EQUALITY_THRESHOLD = Operator.BOOLEAN_THRESHOLD;

    private static final Function[] FUNCTIONS = new Function[3];

    static {
        int i = 0;
        EQUAL = new Function("equal", 2) {
            @Override
            public double apply(double... args) {
                final double  a = args[0];
                final double  b = args[1];
                return Math.abs(a - b) < EQUALITY_THRESHOLD ? 1 : 0;
            }
        };
        FUNCTIONS[i++] = EQUAL;
        IF = new Function("if", 3) {
            @Override
            public double apply(double... args) {
                final boolean a = args[0] >= EQUALITY_THRESHOLD;
                final double  t = args[1];
                final double  f = args[2];
                return a ? t : f;
            }
        };
        FUNCTIONS[i++] = IF;
        SINC = new Function("sinc", 1) {
            @Override
            public double apply(double... args) {
                final double a = args[0];
                return a == 0.0 ? 1 : Math.sin(a) / a;
            }
        };
        FUNCTIONS[i++] = SINC;
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
            case "equal": return EQUAL;
            case "if"   : return IF;
            case "sinc" : return SINC;
            default:      return null;
        }
    }

    private FunctionsMisc() {
        // Don't let anyone initialize this class
    }
}
