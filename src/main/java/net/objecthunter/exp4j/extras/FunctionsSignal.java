/*
* Copyright 2018 Federico Vera
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

/**
 * This class contains some additional functions related with signal processing.
 *
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class FunctionsSignal {

    /**
     * Cardinal Sin (non-normalized).
     */
    public static final Function SINC  = new Sinc();

    /**
     * Heavyside Step Function.
     */
    public static final Function HEAVYSIDE  = new HeavySide();

    /**
     * Array with all the available functions
     *
     * @return {@link Function} array
     * @see FunctionsSignal#getFunction(java.lang.String)
     */
    public static Function[] getFunctions() {
        return new Function[]{
            SINC, HEAVYSIDE
        };
    }

    /**
     * Get the function for a given name
     * @param name the name of the function
     * @return a Function instance
     * @see FunctionsSignal#getFunctions()
     */
    public static Function getFunction(final String name) {
        switch (name) {
            case "sinc"     : return SINC;
            case "heavyside": return HEAVYSIDE;
            default:      return null;
        }
    }

    private FunctionsSignal() {
        // Don't let anyone initialize this class
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

    private static final class HeavySide extends Function {
        private static final long serialVersionUID = -3749047550580483555L;
        HeavySide() { super("heavyside", 1); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            return a < 0 ? 0.0 : 1.0;
        }
    }
}
