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

import net.objecthunter.exp4j.extras.FunctionsBoolean;
import net.objecthunter.exp4j.extras.FunctionsMisc;
import net.objecthunter.exp4j.extras.OperatorsComparison;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 * @author Federico Vera {@literal <dktcoding [at] gmail>}
 */
public class FunctionsMiscTest {

    @Test
    public void testFuncIf1() {
        String expr1 = "if(true(), 1, 0)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsMisc.getFunctions())
                .functions(FunctionsBoolean.getFunctions())
                .operators(OperatorsComparison.getOperators())
                .build();

        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testFuncIf2() {
        String expr1 = "if(false(), 1, 0)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsMisc.getFunctions())
                .functions(FunctionsBoolean.getFunctions())
                .operators(OperatorsComparison.getOperators())
                .build();

        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testFuncIf3() {
        String expr1 = "if(4 < 5 & 3 > 2, 1, 0)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsMisc.getFunctions())
                .functions(FunctionsBoolean.getFunctions())
                .operators(OperatorsComparison.getOperators())
                .build();

        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpEqual2() {
        String expr1 = "equal(1, 2)";
        Expression e1 = new ExpressionBuilder(expr1)
                .function(FunctionsMisc.getFunction("equal")).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpEqual3() {
        String expr1 = "equal(0, 0)";
        Expression e1 = new ExpressionBuilder(expr1)
                .function(FunctionsMisc.getFunction("equal")).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpEqual4() {
        String expr1 = "equal(154 * 54 + 4, 154 * 54 + 4)";
        Expression e1 = new ExpressionBuilder(expr1)
                .function(FunctionsMisc.getFunction("equal")).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpEqual5() {
        String expr1 = "equal(a, a)";

        Expression e1 = new ExpressionBuilder(expr1)
                .function(FunctionsMisc.getFunction("equal"))
                .variables("a")
                .build();

        assertEquals(1, e1.setVariable("a", 1).evaluate(), 0d);
    }

    @Test
    public void testSinc() {
        Expression sinc = new ExpressionBuilder("sinc(x)")
                             .functions(FunctionsMisc.getFunctions())
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
    public void testInf() {
        Expression inf = new ExpressionBuilder("inf()")
                             .functions(FunctionsMisc.INFINITY)
                             .build();

        ValidationResult vr = inf.validate();
        assertTrue(vr.isValid());
        assertEquals(Double.POSITIVE_INFINITY, inf.evaluate(), 0d);
    }

    @Test
    public void testInf2() {
        Expression inf = new ExpressionBuilder("inf()")
                             .functions(FunctionsMisc.getFunctions())
                             .build(true);

        ValidationResult vr = inf.validate();
        assertTrue(vr.isValid());
        assertTrue(Double.isInfinite(inf.evaluate()));
        assertEquals(Double.POSITIVE_INFINITY, inf.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInf3() {
        Expression inf = new ExpressionBuilder("inf(1)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();
        ValidationResult vr = inf.validate();
        assertFalse(vr.isValid());
        inf.evaluate();
    }

    @Test
    public void testInf4() {
        Expression inf = new ExpressionBuilder("-inf()")
                             .functions(FunctionsMisc.getFunctions())
                             .build(true);

        ValidationResult vr = inf.validate();
        assertTrue(vr.isValid());
        assertTrue(Double.isInfinite(inf.evaluate()));
        assertEquals(Double.NEGATIVE_INFINITY, inf.evaluate(), 0d);
    }

    @Test
    public void testIsNaN() {
        Expression inf = new ExpressionBuilder("isnan(inf()/inf())")
                             .functions(FunctionsMisc.getFunctions())
                             .build(true);

        ValidationResult vr = inf.validate();
        assertTrue(vr.isValid());
        assertEquals(1.0, inf.evaluate(), 0d);
    }

    @Test
    public void testIsNaN2() {
        Expression inf = new ExpressionBuilder("inf()/inf()")
                             .functions(FunctionsMisc.getFunctions())
                             .build(true);

        ValidationResult vr = inf.validate();
        assertTrue(vr.isValid());
        assertTrue(Double.isNaN(inf.evaluate()));
    }

    @Test
    public void testIsNaN3() {
        Expression inf = new ExpressionBuilder("isnan(1/inf())")
                             .functions(FunctionsMisc.getFunctions())
                             .build(true);

        ValidationResult vr = inf.validate();
        assertTrue(vr.isValid());
        assertEquals(0.0, inf.evaluate(), 0d);
    }

    @Test
    public void testMin() {
        Expression min = new ExpressionBuilder("min(-10, 2)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = min.validate();
        assertTrue(vr.isValid());
        assertEquals(-10.0, min.evaluate(), 0d);
    }

    @Test
    public void testMin2() {
        Expression min = new ExpressionBuilder("min(10, 2)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = min.validate();
        assertTrue(vr.isValid());
        assertEquals(2.0, min.evaluate(), 0d);
    }

    @Test
    public void testMin3() {
        Expression min = new ExpressionBuilder("min(10, min(1, 20))")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = min.validate();
        assertTrue(vr.isValid());
        assertEquals(1.0, min.evaluate(), 0d);
    }

    @Test
    public void testGetFunctionNonExistent() {
        assertNull(FunctionsMisc.getFunction("foo"));
    }

    @Test(expected = NullPointerException.class)
    public void testGetFunctionNull() {
        assertNull(FunctionsMisc.getFunction(null));
    }
}
