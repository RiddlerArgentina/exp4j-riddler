/* 
* Copyright 2016 Federico Vera
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
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Federico Vera {@literal <dktcoding [at] gmail>}
 */
public class FunctionBooleanTest {
    
    @Test
    public void testNot1() {
        String expr = "not(0)";
        boolean expected = true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNot2() {
        String expr = "not(1)";
        boolean expected = false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNot3() {
        String expr = "not(4.12)";
        boolean expected = false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNot4() {
        String expr = "not()";
        boolean expected = false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNot5() {
        String expr = "not(1, 5)";
        boolean expected = false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNot6() {
        String expr = "not(true())";
        boolean expected = false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNot7() {
        String expr = "not(false())";
        boolean expected = true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testAnd1() {
        String expr = "and(0, 0)";
        boolean expected = false & false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testAnd2() {
        String expr = "and(0, 1)";
        boolean expected = false & true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testAnd3() {
        String expr = "and(1, 0)";
        boolean expected = true & false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testAnd4() {
        String expr = "and(1, 1)";
        boolean expected = true & true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnd5() {
        String expr = "and(1, 1, 7)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnd6() {
        String expr = "and(1)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test
    public void testNand1() {
        String expr = "nand(0, 0)";
        boolean expected = false & false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNand2() {
        String expr = "nand(0, 1)";
        boolean expected = false & true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNand3() {
        String expr = "nand(1, 0)";
        boolean expected = true & false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNand4() {
        String expr = "nand(1, 1)";
        boolean expected = true & true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNand5() {
        String expr = "nand(1, 1, 7)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNand6() {
        String expr = "nand(1)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test
    public void testOr1() {
        String expr = "or(0, 0)";
        boolean expected = false | false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testOr2() {
        String expr = "or(0, 1)";
        boolean expected = false | true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testOr3() {
        String expr = "or(1, 0)";
        boolean expected = true | false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testOr4() {
        String expr = "or(1, 1)";
        boolean expected = true | true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOr5() {
        String expr = "or(1, 1, 7)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOr6() {
        String expr = "or(1)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test
    public void testNor1() {
        String expr = "nor(0, 0)";
        boolean expected = false | false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNor2() {
        String expr = "nor(0, 1)";
        boolean expected = false | true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNor3() {
        String expr = "nor(1, 0)";
        boolean expected = true | false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testNor4() {
        String expr = "nor(1, 1)";
        boolean expected = true | true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNor5() {
        String expr = "nor(1, 1, 7)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNor6() {
        String expr = "nor(1)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test
    public void testXor1() {
        String expr = "xor(0, 0)";
        boolean expected = false ^ false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testXor2() {
        String expr = "xor(0, 1)";
        boolean expected = false ^ true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testXor3() {
        String expr = "xor(1, 0)";
        boolean expected = true ^ false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testXor4() {
        String expr = "xor(1, 1)";
        boolean expected = true ^ true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXor5() {
        String expr = "xor(1, 1, 7)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXor6() {
        String expr = "xor(1)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test
    public void testXnor1() {
        String expr = "xnor(0, 0)";
        boolean expected = false ^ false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testXnor2() {
        String expr = "xnor(0, 1)";
        boolean expected = false ^ true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testXnor3() {
        String expr = "xnor(1, 0)";
        boolean expected = true ^ false;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test
    public void testXnor4() {
        String expr = "xnor(1, 1)";
        boolean expected = true ^ true;
        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();
        assertEquals(!expected ? 1 : 0, e.evaluate(), 0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXnor5() {
        String expr = "xnor(1, 1, 7)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXnor6() {
        String expr = "xnor(1)";
        new ExpressionBuilder(expr).functions(FunctionsBoolean.getFunctions())
                .build()
                .evaluate();
    }

    @Test
    public void testDeMorgan1() {
        String expr1 = "not(and(a,b))";
        String expr2 = "or(not(a), not(b))";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .variables("a", "b")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .functions(FunctionsBoolean.getFunctions())
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                e2.setVariable("a", a);
                e2.setVariable("b", b);
                assertEquals(e1.evaluate(), e2.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testDeMorgan2() {
        String expr1 = "not(or(a,b))";
        String expr2 = "and(not(a), not(b))";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .variables("a", "b")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .functions(FunctionsBoolean.getFunctions())
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                e2.setVariable("a", a);
                e2.setVariable("b", b);
                assertEquals(e1.evaluate(), e2.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testDeMorgan3() {
        String expr1 = "not(or(a,b))";
        String expr2 = "and(not(a), not(b))";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .variables("a", "b")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .functions(FunctionsBoolean.getFunctions())
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                e2.setVariable("a", a);
                e2.setVariable("b", b);
                assertEquals(e1.evaluate(), e2.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testOpNot4() {
        String expr = "¬¬false()";

        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();

        assertEquals(0, e.evaluate(), 0d);
    }

    @Test
    public void testOpNot5() {
        String expr = "¬¬true()";

        Expression e = new ExpressionBuilder(expr)
                .functions(FunctionsBoolean.getFunctions())
                .build();

        assertEquals(1, e.evaluate(), 0d);
    }
    @Test
    public void testSanityAnd1() {
        String expr1 = "a & b";
        String expr2 = "and(a, b)";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .functions(FunctionsBoolean.getFunctions())
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                e2.setVariable("a", a);
                e2.setVariable("b", b);
                assertEquals(e1.evaluate(), e2.evaluate(), 0d);
            }
        }
    }
    
    public void testOpAnd2() {
        String expr = "a && b";

        ValidationResult vr = new ExpressionBuilder(expr)
                .variables("a", "b")
                .build()
                .validate();
        assertFalse(vr.isValid());
        assertTrue(vr.getErrors().contains("Too many operators"));
    }

    @Test
    public void testSanityOr() {
        String expr1 = "a | b";
        String expr2 = "or(a, b)";

        Expression e1 = new ExpressionBuilder(expr1)
                .variables("a", "b")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .functions(FunctionsBoolean.getFunctions())
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                e2.setVariable("a", a);
                e2.setVariable("b", b);
                assertEquals(e1.evaluate(), e2.evaluate(), 0d);
            }
        }
    }
    
    private static final int[] VALUES = {0, 1};
}
