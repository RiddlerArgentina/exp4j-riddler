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

import java.util.HashMap;
import java.util.Map;
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
    public void testMax() {
        Expression min = new ExpressionBuilder("max(-10, 2)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = min.validate();
        assertTrue(vr.isValid());
        assertEquals(2.0, min.evaluate(), 0d);
    }

    @Test
    public void testMax2() {
        Expression min = new ExpressionBuilder("max(10, 2)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = min.validate();
        assertTrue(vr.isValid());
        assertEquals(10.0, min.evaluate(), 0d);
    }

    @Test
    public void testMax3() {
        Expression min = new ExpressionBuilder("max(10, max(1, 20))")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = min.validate();
        assertTrue(vr.isValid());
        assertEquals(20.0, min.evaluate(), 0d);
    }

    @Test
    public void testGcd() {
        Expression gcd = new ExpressionBuilder("gcd(-10, 2)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = gcd.validate();
        assertTrue(vr.isValid());
        assertEquals(2.0, gcd.evaluate(), 0d);
    }

    @Test
    public void testGcd2() {
        Expression gcd = new ExpressionBuilder("gcd(10, 2)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = gcd.validate();
        assertTrue(vr.isValid());
        assertEquals(2.0, gcd.evaluate(), 0d);
    }

    @Test
    public void testGcd3() {
        Expression gcd = new ExpressionBuilder("gcd(10, gcd(1, 20))")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = gcd.validate();
        assertTrue(vr.isValid());
        assertEquals(1.0, gcd.evaluate(), 0d);
    }

    @Test
    public void testGcd4() {
        Expression gcd = new ExpressionBuilder("gcd(10.2, 17.124)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = gcd.validate();
        assertTrue(vr.isValid());
        assertEquals(1.0, gcd.evaluate(), 0d);
    }

    @Test
    public void testGcd5() {
        Expression gcd = new ExpressionBuilder("gcd(10.2, 17 * 10)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = gcd.validate();
        assertTrue(vr.isValid());
        assertEquals(10.0, gcd.evaluate(), 0d);
    }

    @Test
    public void testLcm() {
        Expression lcm= new ExpressionBuilder("lcm(-10, 2)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = lcm.validate();
        assertTrue(vr.isValid());
        assertEquals(10.0, lcm.evaluate(), 0d);
    }

    @Test
    public void testLcm2() {
        Expression lcm = new ExpressionBuilder("lcm(10, 2)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = lcm.validate();
        assertTrue(vr.isValid());
        assertEquals(10.0, lcm.evaluate(), 0d);
    }

    @Test
    public void testLcm3() {
        Expression lcm = new ExpressionBuilder("lcm(10, lcm(1, 20))")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = lcm.validate();
        assertTrue(vr.isValid());
        assertEquals(20.0, lcm.evaluate(), 0d);
    }

    @Test
    public void testLcm4() {
        Expression lcm = new ExpressionBuilder("lcm(4, 6)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        ValidationResult vr = lcm.validate();
        assertTrue(vr.isValid());
        assertEquals(12.0, lcm.evaluate(), 0d);
    }

    @Test
    public void testAbsortion() {
        Expression e1 = new ExpressionBuilder("lcm(x, gcd(x, y))")
                             .functions(FunctionsMisc.getFunctions())
                             .variables("x", "y")
                             .build();
        Expression e2 = new ExpressionBuilder("gcd(x, lcm(x, y))")
                             .functions(FunctionsMisc.getFunctions())
                             .variables("x", "y")
                             .build();

        for (int i = 0; i < 10; i++) {
            Map<String, Double> vars = new HashMap<>(3);
            vars.put("x", Math.random() * 70);
            vars.put("y", Math.random() * 30);
            double v1 = e1.setVariables(vars).evaluate();
            double v2 = e2.setVariables(vars).evaluate();
            assertEquals(v1, Math.round(vars.get("x")), 0d);
            assertEquals(v2, Math.round(vars.get("x")), 0d);
        }
    }

    @Test
    public void testDistribution() {
        Expression e1 = new ExpressionBuilder("lcm(x, gcd(y, z))")
                             .functions(FunctionsMisc.getFunctions())
                             .variables("x", "y", "z")
                             .build();
        Expression e2 = new ExpressionBuilder("gcd(lcm(x, y), lcm(x, z))")
                             .functions(FunctionsMisc.getFunctions())
                             .variables("x", "y", "z")
                             .build();

        for (int i = 0; i < 10; i++) {
            Map<String, Double> vars = new HashMap<>(3);
            vars.put("x", Math.random() * 70);
            vars.put("y", Math.random() * 30);
            vars.put("z", Math.random() * 30);
            double v1 = e1.setVariables(vars).evaluate();
            double v2 = e2.setVariables(vars).evaluate();
            assertEquals(v1, v2, 0d);
        }
    }

    @Test
    public void testDistribution2() {
        Expression e1 = new ExpressionBuilder("gcd(x, lcm(y, z))")
                             .functions(FunctionsMisc.getFunctions())
                             .variables("x", "y", "z")
                             .build();
        Expression e2 = new ExpressionBuilder("lcm(gcd(x, y), gcd(x, z))")
                             .functions(FunctionsMisc.getFunctions())
                             .variables("x", "y", "z")
                             .build();

        for (int i = 0; i < 10; i++) {
            Map<String, Double> vars = new HashMap<>(3);
            vars.put("x", Math.random() * 70);
            vars.put("y", Math.random() * 30);
            vars.put("z", Math.random() * 30);
            double v1 = e1.setVariables(vars).evaluate();
            double v2 = e2.setVariables(vars).evaluate();
            assertEquals(v1, v2, 0d);
        }
    }

    @Test
    public void testRound() {
        Expression e1 = new ExpressionBuilder("round(1)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        assertEquals(1.0, e1.evaluate(), 0d);
    }

    @Test
    public void testRound2() {
        Expression e1 = new ExpressionBuilder("round(1.12452)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        assertEquals(1.0, e1.evaluate(), 0d);
    }

    @Test
    public void testRound3() {
        Expression e1 = new ExpressionBuilder("round(1.99991274)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        assertEquals(2.0, e1.evaluate(), 0d);
    }

    @Test
    public void testRound4() {
        Expression e1 = new ExpressionBuilder("round(-1.99991274)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        assertEquals(-2.0, e1.evaluate(), 0d);
    }

    @Test
    public void testd2r() {
        Expression e1 = new ExpressionBuilder("degtorad(0)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        assertEquals(0.0, e1.evaluate(), 0d);
    }

    @Test
    public void testd2r2() {
        Expression e1 = new ExpressionBuilder("degtorad(90)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        assertEquals(Math.PI / 2, e1.evaluate(), 0d);
    }

    @Test
    public void testr2d() {
        Expression e1 = new ExpressionBuilder("radtodeg(0)")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        assertEquals(0.0, e1.evaluate(), 0d);
    }

    @Test
    public void testr2d2() {
        Expression e1 = new ExpressionBuilder("radtodeg(pi())")
                             .functions(FunctionsMisc.getFunctions())
                             .build();

        assertEquals(180.0, e1.evaluate(), 0d);
    }

    @Test
    public void testr2d2r() {
        Expression e1 = new ExpressionBuilder("degtorad(radtodeg(x))")
                             .functions(FunctionsMisc.getFunctions())
                             .variable("x")
                             .build();

        for (int i = 0; i < 50; i++) {
            double x = Math.random() * 20 - 10;
            assertEquals(x, e1.setVariable("x", x).evaluate(), 1e-8d);
        }
    }

    @Test
    public void testr2d2r2() {
        Expression e1 = new ExpressionBuilder("radtodeg(degtorad(x))")
                             .functions(FunctionsMisc.getFunctions())
                             .variable("x")
                             .build();

        for (int i = 0; i < 50; i++) {
            double x = Math.random() * 20 - 10;
            assertEquals(x, e1.setVariable("x", x).evaluate(), 1e-8d);
        }
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
