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

import net.objecthunter.exp4j.extras.FunctionExpresion;
import net.objecthunter.exp4j.extras.FunctionsMisc;
import net.objecthunter.exp4j.extras.OperatorsComparison;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class FunctionExpresionTest {
    @Test
    public void testFunctionExpression() {
        FunctionExpresion fe = new FunctionExpresion("foo", 2, "a + b");
        Expression e = new ExpressionBuilder("foo(1, 2)")
                .function(fe)
                .build();
        Assertions.assertEquals(3, e.evaluate(), 0d);
    }

    @Test
    public void testFunctionExpression2() {
        FunctionExpresion fe = new FunctionExpresion("foo", 2, "a + b");
        Expression e = new ExpressionBuilder("foo(1, 2)")
                .function(fe)
                .build(true);
        Assertions.assertEquals(3, e.evaluate(), 0d);
    }

    @Test
    public void testFunctionExpression3() {
        FunctionExpresion fe = new FunctionExpresion(
                "foo", 3, true,
                "if((a < b) & (b < c), 2 * b, c)",
                FunctionsMisc.getFunctions(),
                OperatorsComparison.getOperators()
        );
        Expression e = new ExpressionBuilder("foo(1, 2, 3)")
                .function(fe)
                .build();
        Assertions.assertEquals(4, e.evaluate(), 0d);
    }

    @Test
    public void testFunctionExpression4() {
        FunctionExpresion fe = new FunctionExpresion(
                "foo", 3, true,
                "if((a < b) & (b < c), 2 * b, c)",
                FunctionsMisc.getFunctions(),
                OperatorsComparison.getOperators()
        );
        Expression e = new ExpressionBuilder("foo(3, 2, 1)")
                .function(fe)
                .build();
        Assertions.assertEquals(1, e.evaluate(), 0d);
    }

    @Test
    public void testFunctionExpression5() {
        FunctionExpresion fe = new FunctionExpresion(
                "foo", 3, true,
                "a + b - c",
                FunctionsMisc.getFunctions(),
                OperatorsComparison.getOperators()
        );
        Expression e = new ExpressionBuilder("foo(3, 2, foo(3, 2, 1)) * foo(3, 2, 1)")
                .function(fe)
                .build();
        Assertions.assertEquals(4, e.evaluate(), 0d);
    }

    @Test
    public void testFunctionExpression6() {
        FunctionExpresion fe = new FunctionExpresion(
                "foo", 3, true,
                "a + b - c",
                FunctionsMisc.getFunctions(),
                OperatorsComparison.getOperators()
        );
        Expression e = new ExpressionBuilder("foo(3, 2, foo(3, 2, 1)) * foo(3, 2, 1)")
                .function(fe)
                .build(true);
        Assertions.assertEquals(4, e.evaluate(), 0d);
    }

    @Test
    public void testFunctionExpression7() {
        FunctionExpresion fe = new FunctionExpresion(
                "foo", 2,
                "a * lcm(a, b)",
                FunctionsMisc.getFunctions()
        );
        Expression e = new ExpressionBuilder("foo(3, 5)")
                .function(fe)
                .build(true);
        Assertions.assertEquals(45, e.evaluate(), 0d);
    }

    @Test
    public void testFunctionExpression8() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            FunctionExpresion fe = new FunctionExpresion(
                    "foo", 27,
                    "a * lcm(a, b)",
                    FunctionsMisc.getFunctions()
            );
        });
    }
    @Test
    public void testFunctionExpression9() {
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> {
            FunctionExpresion fe = new FunctionExpresion(
                    "foo", 0,
                    "a * lcm(a, b)",
                    FunctionsMisc.getFunctions()
            );
        });
    }
}
