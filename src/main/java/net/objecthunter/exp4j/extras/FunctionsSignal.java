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
 * @since 0.8-riddler
 */
public class FunctionsSignal {

    /**
     * Cardinal Sin (non-normalized).
     * This function has one argument {@code sinc(t)} where:
     * <ul>
     * <li><code><b>t</b></code>: Current point</li>
     * </ul>
     * @since 0.7-riddler
     */
    public static final Function SINC  = new Sinc();

    /**
     * Heavyside Step Function.
     * This function has one argument {@code heavyside(t)} where:
     * <ul>
     * <li><code><b>t</b></code>: Current point</li>
     * </ul>
     * @since 0.8-riddler
     */
    public static final Function HEAVYSIDE  = new HeavySide();

    /**
     * Rectangular Function.
     * This function has three arguments {@code rectangle(t, X, Y)} where:
     * <ul>
     * <li><code><b>t</b></code>: Current point</li>
     * <li><code><b>X</b></code>: Center of the rectangle</li>
     * <li><code><b>Y</b></code>: Length of the rectangle</li>
     * </ul>
     * @since 0.8-riddler
     */
    public static final Function RECTANGULAR  = new Rectangular();

    /**
     * Sawtooth Wave.
     * This function has three arguments {@code sawtooth(t)} where:
     * <ul>
     * <li><code><b>t</b></code>: Current point</li>
     * </ul>
     * @since 0.9-riddler
     */
    public static final Function SAWTOOTH  = new Sawtooth();

    /**
     * Array with all the available functions
     *
     * @return {@link Function} array
     * @see FunctionsSignal#getFunction(String)
     * @see FunctionsSignal#HEAVYSIDE
     * @see FunctionsSignal#RECTANGULAR
     * @see FunctionsSignal#SINC
     * @see FunctionsSignal#SAWTOOTH
     */
    public static Function[] getFunctions() {
        return new Function[]{
            SINC, HEAVYSIDE, RECTANGULAR, SAWTOOTH
        };
    }

    /**
     * Get the function for a given name.
     *
     * @param name the name of the function
     * @return a Function instance
     * @see FunctionsSignal#getFunctions()
     * @see FunctionsSignal#HEAVYSIDE
     * @see FunctionsSignal#RECTANGULAR
     * @see FunctionsSignal#SINC
     * @see FunctionsSignal#SAWTOOTH
     */
    public static Function getFunction(final String name) {
        switch (name) {
            case "sinc"      : return SINC;
            case "rectangle" : return RECTANGULAR;
            case "heavyside" : return HEAVYSIDE;
            case "sawtooth"  : return SAWTOOTH;
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
        private static final long serialVersionUID = 5866054076148826759L;
        HeavySide() { super("heavyside", 1); }
        @Override
        public double apply(double... args) {
            final double a = args[0];
            return a < 0 ? 0.0 : 1.0;
        }
    }

    private static final class Rectangular extends Function {
        private static final long serialVersionUID = 555335310381844968L;
        Rectangular() { super("rectangle", 3); }
        @Override
        public double apply(double... args) {
            final double t = args[0];
            final double X = args[1];
            final double Y = args[2];
            return u(t - X + Y / 2) - u(t - X - Y / 2);
        }

        private static int u(double t) {
            return t < 0 ? 0 : 1;
        }
    }

    private static final class Sawtooth extends Function {
        private static final long serialVersionUID = 555335310381844968L;
        Sawtooth() { super("sawtooth", 1); }
        @Override
        public double apply(double... args) {
            final double x = args[0];
            return x >= 0 ? x % 1 : 1 + (x % 1);
        }
    }
}
