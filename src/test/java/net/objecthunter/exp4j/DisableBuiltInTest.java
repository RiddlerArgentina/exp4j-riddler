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

import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class DisableBuiltInTest {
    @Test(expected = UnknownFunctionOrVariableException.class)
    public void testDisableBuiltInFunctions() {
        new ExpressionBuilder("sin(x)")
                .disableBuiltInFunctions()
                .variable("x")
                .build();
    }

    @Test
    public void testDisableBuiltInFunctions2() {
        Expression e = new ExpressionBuilder("sin(x)")
                .disableBuiltInFunctions()
                .function(Functions.getBuiltinFunction("sin"))
                .variable("x")
                .build();
        e.setVariable("x", Math.PI);
        assertEquals(Math.sin(Math.PI), e.evaluate(), 1e-9);
    }

    @Test
    public void testImplicitMulitplicationOnNumber() throws Exception {
        Expression e = new ExpressionBuilder("var_12")
                .variable("var_1")
                .build()
                .setVariable("var_1", 2);
        assertEquals(4d, e.evaluate(), 0d);
    }

    @Test
    public void testImplicitMulitplicationOnVariable() throws Exception {
        Expression e = new ExpressionBuilder("var_1var_1")
                .variable("var_1")
                .build()
                .setVariable("var_1",2);
        assertEquals(4d, e.evaluate(), 0d);
    }

    @Test
    public void testImplicitMulitplicationOnParantheses() throws Exception {
        Expression e = new ExpressionBuilder("var_1(2)")
                .variable("var_1")
                .build()
                .setVariable("var_1",2);
        assertEquals(4d, e.evaluate(), 0d);
    }

    @Test
    public void testImplicitMulitplicationOnParantheses2() throws Exception {
        Expression e = new ExpressionBuilder("(2)var_1")
                .variable("var_1")
                .build()
                .setVariable("var_1",2);
        assertEquals(4d, e.evaluate(), 0d);
    }

    @Test
    public void testImplicitMulitplicationOnFunction() throws Exception {
        Expression e = new ExpressionBuilder("var_1log(2)")
                .variable("var_1")
                .build()
                .setVariable("var_1",2);
        assertEquals(2*Math.log(2), e.evaluate(), 0d);
    }

}
