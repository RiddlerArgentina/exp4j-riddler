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
import net.objecthunter.exp4j.extras.FunctionsMisc;
import net.objecthunter.exp4j.extras.OperatorsComparison;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
                .operator(OperatorsComparison.getOperators())
                .build();

        assertEquals(1, e1.evaluate(), 0d);
    }

    @Test
    public void testFuncIf2() {
        String expr1 = "if(false(), 1, 0)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsMisc.getFunctions())
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
                .build();

        assertEquals(0, e1.evaluate(), 0d);
    }

    @Test
    public void testFuncIf3() {
        String expr1 = "if(4 < 5 & 3 > 2, 1, 0)";

        Expression e1 = new ExpressionBuilder(expr1)
                .functions(FunctionsMisc.getFunctions())
                .functions(FunctionsBoolean.getFunctions())
                .operator(OperatorsComparison.getOperators())
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
}
