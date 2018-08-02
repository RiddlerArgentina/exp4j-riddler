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
package net.objecthunter.exp4j;

import net.objecthunter.exp4j.extras.FunctionsSignal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Federico Vera {@literal <fede [at] riddler.com.ar>}
 */
public class FunctionsSignalTest {

    @Test
    public void testSinc() {
        Expression sinc = new ExpressionBuilder("sinc(x)")
                             .functions(FunctionsSignal.getFunctions())
                             .variable("x")
                             .build();

        assertEquals(1, sinc.setVariable("x", 0).evaluate(), 0d);
        assertEquals(0, sinc.setVariable("x", Math.PI).evaluate(), 1e-12);
        assertEquals(0, sinc.setVariable("x", -Math.PI).evaluate(), 1e-12);
        assertEquals(0, sinc.setVariable("x", -2 * Math.PI).evaluate(), 1e-12);
        assertEquals(0, sinc.setVariable("x",  2 * Math.PI).evaluate(), 1e-12);

        double x = Math.random();
        assertEquals(Math.sin(x)/x, sinc.setVariable("x",  x).evaluate(), 1e-12);
    }

    @Test
    public void testHeavySide() {
        Expression e1 = new ExpressionBuilder("heavyside(10)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(1.0, e1.evaluate(), 0d);
    }

    @Test
    public void testHeavySide2() {
        Expression e1 = new ExpressionBuilder("heavyside(0)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(1.0, e1.evaluate(), 0d);
    }

    @Test
    public void testHeavySide3() {
        Expression e1 = new ExpressionBuilder("heavyside(-23)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(0.0, e1.evaluate(), 0d);
    }

    @Test
    public void testRectangle() {
        Expression e1 = new ExpressionBuilder("rectangle(0, 0, 1)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(1.0, e1.evaluate(), 0d);
    }

    @Test
    public void testRectangle2() {
        Expression e1 = new ExpressionBuilder("rectangle(10, 10, 1)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(1.0, e1.evaluate(), 0d);
    }

    @Test
    public void testRectangle3() {
        Expression e1 = new ExpressionBuilder("rectangle(0, 10, 5)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(0.0, e1.evaluate(), 0d);
    }

    @Test
    public void testRectangle4() {
        Expression e1 = new ExpressionBuilder("rectangle(13, 10, 5)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(0.0, e1.evaluate(), 0d);
    }

    @Test
    public void testSawtooth() {
        Expression e1 = new ExpressionBuilder("sawtooth(1)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(0.0, e1.evaluate(), 0d);
    }

    @Test
    public void testSawtooth1() {
        Expression e1 = new ExpressionBuilder("sawtooth(0)")
                             .functions(FunctionsSignal.getFunctions())
                             .build();

        assertEquals(0.0, e1.evaluate(), 0d);
    }

    @Test
    public void testSawtooth2() {
        Expression e1 = new ExpressionBuilder("sawtooth(x)")
                             .functions(FunctionsSignal.getFunctions())
                             .variable("x")
                             .build();

        for (double x = -10; x < 10; x += 0.02) {
            double res = e1.setVariable("x", x).evaluate();
            assertTrue(res <= 1);
            assertTrue(res >= 0);
        }
    }

    @Test
    public void testSawtooth3() {
        Expression e1 = new ExpressionBuilder("sawtooth(x)")
                             .functions(FunctionsSignal.getFunctions())
                             .variable("x")
                             .build();
        double x = Math.random() * 5 - 10;
        double r = e1.setVariable("x", x).evaluate();
        for (; x < 10; x += 1) {
            double res = e1.setVariable("x", x).evaluate();
            assertEquals(r, res, 1e-12);
        }
    }

    @Test
    public void testTriangle1() {
        Expression e1 = new ExpressionBuilder("triangle(x)")
                             .functions(FunctionsSignal.getFunctions())
                             .variable("x")
                             .build();
        for (double x = -10; x < 10; x += 0.01) {
            double res = e1.setVariable("x", x).evaluate();
            assertTrue(res <=  1);
            assertTrue(res >= -1);
        }
    }

    @Test
    public void testTriangle2() {
        Expression e1 = new ExpressionBuilder("triangle(x)")
                             .functions(FunctionsSignal.getFunctions())
                             .variable("x")
                             .build();
        double x = Math.random() * 5 - 10;
        double r = e1.setVariable("x", x).evaluate();
        for (; x < 10; x += 1) {
            double res = e1.setVariable("x", x).evaluate();
            assertEquals(r, res, 1e-12);
        }
    }
}
