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
package net.objecthunter.exp4j;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionsTest {
    @Test
    public void testFunctionNameNull() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Function(null) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        });
    }

    @Test
    public void testFunctionNameEmpty() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Function("") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        });
    }

    @Test
    public void testFunctionNameZeroArgs() {
        Function f = new Function("foo", 0) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        };
        Assertions.assertEquals(0f, f.apply(), 0f);
    }

    @Test
    public void testFunctionNameNegativeArgs() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Function("foo", -1) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        });
    }

    @Test
    public void testIllegalFunctionName1() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Function("1foo") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        });
    }

    @Test
    public void testIllegalFunctionName2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Function("_&oo") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        });
    }

    @Test
    public void testIllegalFunctionName3() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () ->
                new Function("o+o") {
                    @Override
                    public double apply(double... args) {
                        return 0;
                    }
                });
    }

    @Test
    public void testSignum() {
        Assertions.assertEquals(1, new ExpressionBuilder("signum(1e4)").build().evaluate(), 0);
        Assertions.assertEquals(-1, new ExpressionBuilder("signum(-1E4)").build().evaluate(), 0);
        Assertions.assertEquals(0, new ExpressionBuilder("signum(0)").build().evaluate(), 0);
    }

    @Test
    public void testCheckFunctionNames() {
        Assertions.assertTrue(Function.isValidFunctionName("log"));
        Assertions.assertTrue(Function.isValidFunctionName("sin"));
        Assertions.assertTrue(Function.isValidFunctionName("abz"));
        Assertions.assertTrue(Function.isValidFunctionName("alongfunctionnamecanhappen"));
        Assertions.assertTrue(Function.isValidFunctionName("_log"));
        Assertions.assertTrue(Function.isValidFunctionName("__blah"));
        Assertions.assertTrue(Function.isValidFunctionName("foox"));
        Assertions.assertTrue(Function.isValidFunctionName("aZ"));
        Assertions.assertTrue(Function.isValidFunctionName("Za"));
        Assertions.assertTrue(Function.isValidFunctionName("ZZaa"));
        Assertions.assertTrue(Function.isValidFunctionName("_"));
        Assertions.assertTrue(Function.isValidFunctionName("log2"));
        Assertions.assertTrue(Function.isValidFunctionName("lo32g2"));
        Assertions.assertTrue(Function.isValidFunctionName("_o45g2"));

        Assertions.assertFalse(Function.isValidFunctionName("&"));
        Assertions.assertFalse(Function.isValidFunctionName("_+log"));
        Assertions.assertFalse(Function.isValidFunctionName("_k&l"));
        Assertions.assertFalse(Function.isValidFunctionName("k&l"));
        Assertions.assertFalse(Function.isValidFunctionName("+log"));
        Assertions.assertFalse(Function.isValidFunctionName("fo-o"));
        Assertions.assertFalse(Function.isValidFunctionName("log+"));
        Assertions.assertFalse(Function.isValidFunctionName("perc%"));
        Assertions.assertFalse(Function.isValidFunctionName("del$a"));
    }

    @Test
    public void testGetFunctionNonExistent() {
        Assertions.assertNull(Functions.getBuiltinFunction("foo"));
    }

    @Test
    public void testGetFunctionNull() {
        Assertions.assertThrowsExactly(NullPointerException.class, () ->
                Functions.getBuiltinFunction(null)
        );
    }
}
