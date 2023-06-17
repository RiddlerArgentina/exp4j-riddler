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
package net.objecthunter.exp4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class OperatorBooleanTest {
    private static final int[] VALUES = {0, 1};

    @Test
    public void testAnd() {

        String expr1 = "a & b";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                Assertions.assertEquals(((a == 1) && (b == 1)) ? 1 : 0, e1.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testOr() {
        String expr1 = "a | b";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                Assertions.assertEquals(((a == 1) || (b == 1)) ? 1 : 0, e1.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testDeMorgan1() {
        String expr1 = "¬(a & b)";
        String expr2 = "¬a | ¬b";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                e2.setVariable("a", a);
                e2.setVariable("b", b);
                Assertions.assertEquals(e1.evaluate(), e2.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testDeMorgan2() {
        String expr1 = "¬(a | b)";
        String expr2 = "¬a & ¬b";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                e2.setVariable("a", a);
                e2.setVariable("b", b);
                Assertions.assertEquals(e1.evaluate(), e2.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testOpNot1() {
        String expr = "¬a";

        Expression e = new ExpressionBuilder(expr)
                .variables("a")
                .build();

        for (int a : VALUES) {
            e.setVariable("a", a);
            Assertions.assertEquals(a == 0 ? 1 : 0, e.evaluate(), 0d);
        }
    }

    @Test
    public void testOpNot2() {
        String expr = "¬¬¬a";

        Expression e = new ExpressionBuilder(expr)
                .variables("a")
                .build();

        for (int a : VALUES) {
            e.setVariable("a", a);
            Assertions.assertEquals(a == 0 ? 1 : 0, e.evaluate(), 0d);
        }
    }

    @Test
    public void testOpNot3() {
        String expr = "¬¬a";

        Expression e = new ExpressionBuilder(expr)
                .variables("a")
                .build();

        for (int a : VALUES) {
            e.setVariable("a", a);
            Assertions.assertEquals(a != 0 ? 1 : 0, e.evaluate(), 0d);
        }
    }

    @Test
    public void testOpPrecedence1() {
        String expr1 = "¬a | ¬b";
        String expr2 = "((¬a) | (¬b))";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                e2.setVariable("a", a);
                e2.setVariable("b", b);
                Assertions.assertEquals(e1.evaluate(), e2.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testOpPrecedence2() {
        String expr1 = "a | b & c";
        String expr2 = "(a | (b & c))";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b", "c")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .variables("a", "b", "c")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                for (int c : VALUES) {
                    e1.setVariable("a", a);
                    e1.setVariable("b", b);
                    e1.setVariable("c", c);
                    e2.setVariable("a", a);
                    e2.setVariable("b", b);
                    e2.setVariable("c", c);
                    Assertions.assertEquals(e1.evaluate(), e2.evaluate(), 0d);
                }
            }
        }
    }

    @Test
    public void testOpPrecedence3() {
        String expr1 = "a & ¬b | c";
        String expr2 = "((a & (¬b)) | c)";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b", "c")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .variables("a", "b", "c")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                for (int c : VALUES) {
                    e1.setVariable("a", a);
                    e1.setVariable("b", b);
                    e1.setVariable("c", c);
                    e2.setVariable("a", a);
                    e2.setVariable("b", b);
                    e2.setVariable("c", c);
                    Assertions.assertEquals(e1.evaluate(), e2.evaluate(), 0d);
                }
            }
        }
    }

    @Test
    public void testOpPrecedence4() {
        String expr1 = "a & b | ¬c & d";
        String expr2 = "((a & b) | ((¬c) & d))";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b", "c", "d")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .variables("a", "b", "c", "d")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                for (int c : VALUES) {
                    for (int d : VALUES) {
                        e1.setVariable("a", a);
                        e1.setVariable("b", b);
                        e1.setVariable("c", c);
                        e1.setVariable("d", d);
                        e2.setVariable("a", a);
                        e2.setVariable("b", b);
                        e2.setVariable("c", c);
                        e2.setVariable("d", d);
                        Assertions.assertEquals(e1.evaluate(), e2.evaluate(), 0d);
                    }
                }
            }
        }
    }

    @Test
    public void testOpPrecedence5() {
        String expr1 = "a & b & c | d";
        String expr2 = "(((a & b) & c) | d)";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b", "c", "d")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .variables("a", "b", "c", "d")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                for (int c : VALUES) {
                    for (int d : VALUES) {
                        e1.setVariable("a", a);
                        e1.setVariable("b", b);
                        e1.setVariable("c", c);
                        e1.setVariable("d", d);
                        e2.setVariable("a", a);
                        e2.setVariable("b", b);
                        e2.setVariable("c", c);
                        e2.setVariable("d", d);
                        Assertions.assertEquals(e1.evaluate(), e2.evaluate(), 0d);
                    }
                }
            }
        }
    }

    @Test
    public void testOpPrecedence6() {
        String expr1 = "0 + 2 & 3 + 4";
        //(0 + 2) & (3 + 4) = true() <- correct
        //0 + (2 & 3) + 4   = 5      <- incorrect

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b", "c", "d")
                .build();

        Assertions.assertEquals(1, e1.evaluate(), 0D);
    }
}
