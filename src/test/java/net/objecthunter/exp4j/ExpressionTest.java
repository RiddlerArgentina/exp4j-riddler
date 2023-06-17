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
import net.objecthunter.exp4j.operator.Operators;
import net.objecthunter.exp4j.tokenizer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionTest {
    @Test
    public void testExpression1() {
        Token[] tokens = new Token[]{
                new NumberToken(3d),
                new NumberToken(2d),
                new OperatorToken(Operators.getBuiltinOperator('+', 2))
        };
        Expression exp = new Expression(tokens, new String[0]);
        Assertions.assertEquals(5d, exp.evaluate(), 0d);
    }

    @Test
    public void testExpression2() {
        Token[] tokens = new Token[]{
                new NumberToken(1d),
                new FunctionToken(Functions.getBuiltinFunction("log")),
        };
        Expression exp = new Expression(tokens, new String[0]);
        Assertions.assertEquals(0d, exp.evaluate(), 0d);
    }

    @Test
    public void testGetVariableNames1() {
        Token[] tokens = new Token[]{
                new VariableToken("a"),
                new VariableToken("b"),
                new OperatorToken(Operators.getBuiltinOperator('+', 2))
        };
        Expression exp = new Expression(tokens, new String[0]);

        Assertions.assertEquals(2, exp.getVariableNames().size());
    }

    @Test
    public void testToString() {
        Expression exp = new ExpressionBuilder("1 + 1").build();
        Assertions.assertEquals("1.0 1.0 +", exp.toString());
        exp = new ExpressionBuilder("1 + 1").build(true);
        Assertions.assertEquals("2.0", exp.toString());
        exp = new ExpressionBuilder("2x + 1").variable("x").build(true);
        Assertions.assertEquals("2.0 x * 1.0 +", exp.toString());
        exp = new ExpressionBuilder("2 ^ sin(pi())").build(true);
        Assertions.assertEquals("1.0", exp.toString());
        exp = new ExpressionBuilder("2 ^ sin(pi())").build(false);
        Assertions.assertEquals("2.0 pi sin ^", exp.toString());
    }

    @Test
    public void testToTokenString() {
        Expression exp = new ExpressionBuilder("1 + 1").build();
        Assertions.assertEquals("NUMBER[1.0] NUMBER[1.0] OPERATOR[+]", exp.toTokenString());
        exp = new ExpressionBuilder("1 + 1").build(true);
        Assertions.assertEquals("NUMBER[2.0]", exp.toTokenString());
        exp = new ExpressionBuilder("2x + 1").variable("x").build(true);
        Assertions.assertEquals("NUMBER[2.0] VARIABLE[x] OPERATOR[*] NUMBER[1.0] OPERATOR[+]", exp.toTokenString());
        exp = new ExpressionBuilder("2 ^ sin(pi())").build(true);
        Assertions.assertEquals("NUMBER[1.0]", exp.toTokenString());
        exp = new ExpressionBuilder("2 ^ sin(pi())").build(false);
        Assertions.assertEquals("NUMBER[2.0] FUNCTION[pi] FUNCTION[sin] OPERATOR[^]", exp.toTokenString());
    }

    @Test
    public void testCopy() {
        Expression exp1 = new ExpressionBuilder("1 + 3").build();
        Expression exp2 = exp1.copy();
        Assertions.assertEquals(exp1.evaluate(), exp2.evaluate(), 1e-12);
    }

    @Test
    public void testCopy1() {
        Expression exp1 = new ExpressionBuilder("1 + x").variable("x").build();
        Expression exp2 = exp1.copy();
        exp1.setVariable("x", 0);
        exp2.setVariable("x", 0);
        Assertions.assertEquals(exp1.evaluate(), exp2.evaluate(), 1e-12);
        exp1.setVariable("x", 1);
        exp2.setVariable("x", 2);
        Assertions.assertNotEquals(exp1.evaluate(), exp2.evaluate(), 1e-12);
    }

    @Test
    public void testCopy2() {
        Function myFunc = new Function("myFunc") {
            @Override
            public double apply(double... args) {
                return Math.sin(args[0]);
            }
        };
        Expression exp1 = new ExpressionBuilder("1 + myFunc(x)")
                .function(myFunc).variable("x").build();
        Expression exp2 = exp1.copy();
        exp1.setVariable("x", 0);
        exp2.setVariable("x", 0);
        Assertions.assertEquals(exp1.evaluate(), exp2.evaluate(), 1e-12);
        exp1.setVariable("x", 1);
        exp2.setVariable("x", 2);
        Assertions.assertNotEquals(exp1.evaluate(), exp2.evaluate(), 1e-12);
    }

    @Test
    public void testCopy3() {
        //There's an issue with multiple variables not being set after copy()
        Function myFunc = new Function("myFunc") {
            @Override
            public double apply(double... args) {
                return Math.sin(args[0]);
            }
        };
        Expression exp1 = new ExpressionBuilder("1 + myFunc(x) + y")
                .function(myFunc).variables("x", "y").build();
        exp1.setVariable("x", 3);
        exp1.setVariable("y", 1);
        Expression exp2 = exp1.copy();
        Assertions.assertEquals(exp1.evaluate(), exp2.evaluate(), 1e-12);
        exp1.setVariable("x", 0);
        exp2.setVariable("x", 0);
        Assertions.assertEquals(exp1.evaluate(), exp2.evaluate(), 1e-12);
        exp1.setVariable("x", 1);
        exp2.setVariable("x", 2);
        Assertions.assertNotEquals(exp1.evaluate(), exp2.evaluate(), 1e-12);
    }

    @Test
    public void testCheckVariable() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder("sin(sin)").build().setVariable("sin", 0));
    }

    @Test
    public void testCheckVariable2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Function foo = new Function("foo") {
                @Override
                public double apply(double... args) {
                    return args[0];
                }
            };
            new ExpressionBuilder("sin(foo)").function(foo).build().setVariable("foo", 0);
        });
    }

    @Test
    public void testContainsVariable() {
        Expression exp = new ExpressionBuilder("sin(foo)").variable("foo").build();
        Assertions.assertTrue(exp.containsVariable("foo"));
        Assertions.assertFalse(exp.containsVariable("bar"));
    }

    @Test
    public void testEvaluateWrongNumberOfArguments() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("sin()").variable("foo").build();
            exp.evaluate();
        });
    }

    @Test
    public void testToStringNoValues() {
        Expression exp = new ExpressionBuilder("sin(1 * foo)").variable("foo").build();
        Assertions.assertEquals("1.0 foo * sin", exp.toString());
    }

    @Test
    public void testToStringWithValues() {
        Expression exp = new ExpressionBuilder("sin(1 * foo)").variable("foo").build();
        exp.setVariable("foo", 0);
        Assertions.assertEquals("1.0 foo(0.0) * sin", exp.toString());
    }

    @Test
    public void testEvaluateWrongArgsForOperator() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("3 * ").variable("foo").build();
            exp.evaluate();
        });
    }

    @Test
    public void testEvaluateWrongArgsForOperatorSimplify() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("3 * ").variable("foo").build(true);
            exp.evaluate();
        });
    }

    @Test
    public void testExpressions1() {
        Expression res = new ExpressionBuilder("xxx")
                .variable("x").build(true);
        for (int i = 0; i < 10; i++) {
            double x = Math.random() * 20 - 10;
            res.setVariable("x", x);
            Assertions.assertEquals(x * x * x, res.evaluate(), 0d);
        }
        Assertions.assertTrue(res.isCachingResult());
    }

    @Test
    public void testExpressions2() {
        Expression res = new ExpressionBuilder("xyxy")
                .variables("x", "y").build(true);
        for (int i = 0; i < 10; i++) {
            double x = Math.random() * 20 - 10;
            double y = Math.random() * 20 - 10;
            res.setVariable("x", x);
            res.setVariable("y", y);
            Assertions.assertEquals(x * y * x * y, res.evaluate(), 0d);
        }
        Assertions.assertTrue(res.isCachingResult());
    }

    @Test
    public void testCacheOn() {
        Expression e1 = new ExpressionBuilder("xyxy").variables("xy").build();
        Expression e2 = new ExpressionBuilder("xyxy").variables("xy").build(true);
        Expression e3 = new ExpressionBuilder("xyxy").variables("x", "y").build();
        Expression e4 = new ExpressionBuilder("xyxy").variables("x", "y").build(true);
        Expression e5 = new ExpressionBuilder("sin(2)").build();
        Expression e6 = new ExpressionBuilder("sin(x)").variables("x", "y").build();
        Assertions.assertTrue(e1.isCachingResult());
        Assertions.assertTrue(e2.isCachingResult());
        Assertions.assertTrue(e3.isCachingResult());
        Assertions.assertTrue(e4.isCachingResult());
        Assertions.assertTrue(e5.isCachingResult());
        Assertions.assertTrue(e6.isCachingResult());
    }

    @Test
    public void testCacheOff() {
        Expression e1 = new ExpressionBuilder("xyxy()").function(new Function("xyxy", 0) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).build();
        Expression e2 = new ExpressionBuilder("xyxy()").function(new Function("xyxy", 0, false) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).build();
        Expression e3 = new ExpressionBuilder("xyxy()").function(new Function("xyxy", 0) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).build(true);
        Expression e4 = new ExpressionBuilder("xyxy()").function(new Function("xyxy", 0, false) {
            @Override
            public double apply(double... args) {
                return 0;
            }
        }).build(true);
        Assertions.assertTrue(e1.isCachingResult());
        Assertions.assertFalse(e2.isCachingResult());
        Assertions.assertTrue(e3.isCachingResult());
        Assertions.assertFalse(e4.isCachingResult());
    }
}
