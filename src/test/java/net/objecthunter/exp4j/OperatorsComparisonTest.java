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

import java.util.HashMap;
import net.objecthunter.exp4j.extras.FunctionsBoolean;
import net.objecthunter.exp4j.extras.OperatorsComparison;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Federico Vera {@literal <dktcoding [at] gmail>}
 */
public class OperatorsComparisonTest {
    
    @Test
    public void testPrecedence1() {
        String expr1 = "1 + 2 < 3 + 1 & 8 > 4";
        String expr2 = "(((1 + 2) < (3 + 1)) & (8 > 4))";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .build();

        Expression e2 = new ExpressionBuilder(expr2)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .build();
        
        assertEquals(1, e1.evaluate(), 0D);
        assertEquals(e2.evaluate(), e1.evaluate(), 0D);
    }
    
    @Test
    public void testPrecedence2() {
        String expr1 = "1 + 2 < 3 + 1 & 8 > 4 == (((1 + 2) < (3 + 1)) & (8 > 4))";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .build();
        
        assertEquals(1, e1.evaluate(), 0D);
    }
    
    @Test
    public void testPrecedence3() {
        String expr1 = "true() & false() == and(1, 0)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .build();
        
        assertEquals(1, e1.evaluate(), 0D);
    }
    
    @Test
    public void testPrecedence4() {
        String expr1 = "true() & false() == and(1, 0)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .build();
        
        assertEquals(1, e1.evaluate(), 0D);
    }

    @Test
    public void testSanityOpFunc1() {
        String expr1 = "a & b == and(a, b)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                assertEquals(1, e1.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testSanityOpFunc2() {
        String expr1 = "a | b == or(a, b)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                assertEquals(1, e1.evaluate(), 0d);
            }
        }
    }

    @Test
    public void testSanityOpFunc3() {
        String expr1 = "¬a == not(a)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .variables("a")
                .build();

        for (int a : VALUES) {
            e1.setVariable("a", a);
            assertEquals(1, e1.evaluate(), 0d);
        }
    }

    @Test
    public void testOpNotEqual() {
        String expr1 = "a & b != and(a, b)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .variables("a", "b")
                .build();

        for (int a : VALUES) {
            for (int b : VALUES) {
                e1.setVariable("a", a);
                e1.setVariable("b", b);
                assertEquals(0, e1.evaluate(), 0d);
            }
        }
    }
    
    @Test
    public void testOpGreaterThan1() {
        String expr1 = "1 < 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpGreaterThan2() {
        String expr1 = "10 < 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpGreaterThan3() {
        String expr1 = "2 < 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpGreaterOrEqualThan1() {
        String expr1 = "1 <= 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpGreaterOrEqualThan2() {
        String expr1 = "10 <= 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpGreaterOrEqualThan3() {
        String expr1 = "2 <= 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpLessThan1() {
        String expr1 = "1 > 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpLessThan2() {
        String expr1 = "10 > 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpLessThan3() {
        String expr1 = "2 > 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpLessOrEqualThan1() {
        String expr1 = "1 >= 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpLessOrEqualThan2() {
        String expr1 = "10 >= 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpLessOrEqualThan3() {
        String expr1 = "2 >= 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpEqual2() {
        String expr1 = "1 == 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpEqual3() {
        String expr1 = "0 == 0";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpEqual4() {
        String expr1 = "154 * 54 + 4 == 154 * 54 + 4";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpEqual5() {
        String expr1 = "a == a";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .variables("a")
                .build();

        assertEquals(1, e1.setVariable("a", 1).evaluate(), 0d);
    }
    
    @Test
    public void testOpNotEqual1() {
        String expr1 = "1 != 2";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testOpNotEqual2() {
        String expr1 = "0 != 0";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpNotEqual3() {
        String expr1 = "154 * 54 + 4 != 154 * 54 + 4";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpNotEqualThreshold1() {
        String expr1 = "1 != 1.0000000000001";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testOpNotEqualThreshold2() {
        String expr1 = "1 != 1.000000000001";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators()).build();
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testDocExample() {
        String expr1 = "a + b * c > d / e & f < g == (((a + (b * c)) > (d / e)) & (f < g))";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators())
                .variables("a", "b", "c", "d", "e", "f", "g")
                .build();
        e1.setVariable("a", Math.random());
        e1.setVariable("b", Math.random());
        e1.setVariable("c", Math.random());
        e1.setVariable("d", Math.random());
        e1.setVariable("e", Math.random());
        e1.setVariable("f", Math.random());
        e1.setVariable("g", Math.random());
        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testDocExample2() {
        String expr1 = "a + b * c > d / e & f < g == (((a + (b * c)) > (d / e)) & (f < g))";
        String expr2 = "((a + b * c > d / e & f < g) == (((a + (b * c)) > (d / e)) & (f < g)))";
        String expr3 = "a + b * c > d / e & f < g == a + b * c > d / e & f < g";
        Expression e1 = new ExpressionBuilder(expr1)
                .operator(OperatorsComparison.getOperators())
                .variables("a", "b", "c", "d", "e", "f", "g")
                .build();
        Expression e2 = new ExpressionBuilder(expr2)
                .operator(OperatorsComparison.getOperators())
                .variables("a", "b", "c", "d", "e", "f", "g")
                .build();
        Expression e3 = new ExpressionBuilder(expr3)
                .operator(OperatorsComparison.getOperators())
                .variables("a", "b", "c", "d", "e", "f", "g")
                .build();
        HashMap<String, Double> vars = new HashMap<>(7);
        vars.put("a", Math.random());
        vars.put("b", Math.random());
        vars.put("c", Math.random());
        vars.put("d", Math.random());
        vars.put("e", Math.random());
        vars.put("f", Math.random());
        vars.put("g", Math.random());
        e1.setVariables(vars);
        e2.setVariables(vars);
        e3.setVariables(vars);
        assertEquals(e1.evaluate(), e2.evaluate(), 0d);
        assertEquals(e2.evaluate(), e3.evaluate(), 0d);
    }

    private static final int[] VALUES = {0, 1};
}
