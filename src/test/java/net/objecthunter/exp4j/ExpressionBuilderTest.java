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
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Math.*;

public class ExpressionBuilderTest {

    @Test
    public void testExpressionBuilder1() {
        double result = new ExpressionBuilder("2+1")
                .build()
                .evaluate();
        Assertions.assertEquals(3d, result, 0d);
    }

    @Test
    public void testExpressionBuilder2() {
        double result = new ExpressionBuilder("cos(x)")
                .variables("x")
                .build()
                .setVariable("x", PI)
                .evaluate();
        double expected = cos(PI);
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder3() {
        double x = PI;
        double result = new ExpressionBuilder("sin(x)-log(3*x/4)")
                .variables("x")
                .build()
                .setVariable("x", x)
                .evaluate();

        double expected = sin(x) - log(3 * x / 4);
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder4() {
        Function log2 = new Function("log2", 1) {

            @Override
            public double apply(double... args) {
                return log(args[0]) / log(2);
            }
        };
        double result = new ExpressionBuilder("log2(4)")
                .function(log2)
                .build()
                .evaluate();

        double expected = 2;
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder5() {
        Function avg = new Function("avg", 4) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        double result = new ExpressionBuilder("avg(1,2,3,4)")
                .function(avg)
                .build()
                .evaluate();

        double expected = 2.5d;
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder6() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        double result = new ExpressionBuilder("3!")
                .operator(factorial)
                .build()
                .evaluate();

        double expected = 6d;
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpressionBuilder7() {
        ValidationResult res = new ExpressionBuilder("x")
                .variables("x")
                .build()
                .validate();
        Assertions.assertFalse(res.isValid());
        Assertions.assertEquals(res.getErrors().size(), 1);
    }

    @Test
    public void testExpressionBuilder8() {
        ValidationResult res = new ExpressionBuilder("x*y*z")
                .variables("x", "y", "z")
                .build()
                .validate();
        Assertions.assertFalse(res.isValid());
        Assertions.assertEquals(res.getErrors().size(), 3);
    }

    @Test
    public void testExpressionBuilder9() {
        ValidationResult res = new ExpressionBuilder("x")
                .variables("x")
                .build()
                .setVariable("x", 1d)
                .validate();
        Assertions.assertTrue(res.isValid());
    }

    @Test
    public void testValidationDocExample() {
        Expression e = new ExpressionBuilder("x")
                .variables("x")
                .build();
        ValidationResult res = e.validate();
        Assertions.assertFalse(res.isValid());
        Assertions.assertEquals(1, res.getErrors().size());

        e.setVariable("x", 1d);
        res = e.validate();
        Assertions.assertTrue(res.isValid());
    }

    @Test
    public void testExpressionBuilder10() {
        double result = new ExpressionBuilder("1e1")
                .build()
                .evaluate();
        Assertions.assertEquals(10d, result, 0d);
    }

    @Test
    public void testExpressionBuilder11() {
        double result = new ExpressionBuilder("1.11e-1")
                .build()
                .evaluate();
        Assertions.assertEquals(0.111d, result, 0d);
    }

    @Test
    public void testExpressionBuilder12() {
        double result = new ExpressionBuilder("1.11e+1")
                .build()
                .evaluate();
        Assertions.assertEquals(11.1d, result, 0d);
    }

    @Test
    public void testExpressionBuilder13() {
        double result = new ExpressionBuilder("-3^2")
                .build()
                .evaluate();
        Assertions.assertEquals(-9d, result, 0d);
    }

    @Test
    public void testExpressionBuilder14() {
        double result = new ExpressionBuilder("(-3)^2")
                .build()
                .evaluate();
        Assertions.assertEquals(9d, result, 0d);
    }

    @Test
    public void testExpressionBuilder15() {
        Assertions.assertThrowsExactly(ArithmeticException.class, () -> new ExpressionBuilder("-3/0")
                .build()
                .evaluate());
    }

    @Test
    public void testExpressionBuilder16() {
        double res = new ExpressionBuilder("log(x) - y * (sqrt(x^cos(y)))")
                .variables("x", "y")
                .build()
                .setVariable("x", 1d)
                .setVariable("y", 2d)
                .evaluate();
        Assertions.assertEquals(-2, res, 1e-9);
    }

    @Test
    public void testExpressionBuilder17() {
        Expression e = new ExpressionBuilder("x-y*")
                .variables("x", "y")
                .build();
        ValidationResult res = e.validate(false);
        Assertions.assertFalse(res.isValid());
        Assertions.assertEquals(1, res.getErrors().size());
        Assertions.assertEquals("Too many operators", res.getErrors().get(0));
    }

    @Test
    public void testExpressionBuilder18() {
        Expression e = new ExpressionBuilder("log(x) - y *")
                .variables("x", "y")
                .build();
        ValidationResult res = e.validate(false);
        Assertions.assertFalse(res.isValid());
        Assertions.assertEquals(1, res.getErrors().size());
        Assertions.assertEquals("Too many operators", res.getErrors().get(0));
    }

    @Test
    public void testExpressionBuilder19() {
        Expression e = new ExpressionBuilder("x - y *")
                .variables("x", "y")
                .build();
        ValidationResult res = e.validate(false);
        Assertions.assertFalse(res.isValid());
        Assertions.assertEquals(1, res.getErrors().size());
        Assertions.assertEquals("Too many operators", res.getErrors().get(0));
    }

    /* legacy tests from earlier exp4j versions */

    @Test
    public void testFunction1() {
        Function custom = new Function("timespi") {

            @Override
            public double apply(double... values) {
                return values[0] * PI;
            }
        };
        Expression e = new ExpressionBuilder("timespi(x)")
                .function(custom)
                .variables("x")
                .build()
                .setVariable("x", 1);
        double result = e.evaluate();
        Assertions.assertEquals(PI, result);
        Assertions.assertEquals(PI, result, 0d);
    }

    @Test
    public void testFunction2() {
        Function custom = new Function("loglog") {

            @Override
            public double apply(double... values) {
                return log(log(values[0]));
            }
        };
        Expression e = new ExpressionBuilder("loglog(x)")
                .variables("x")
                .function(custom)
                .build()
                .setVariable("x", 1);
        double result = e.evaluate();
        Assertions.assertEquals(log(log(1)), result, 0d);
    }

    @Test
    public void testFunction3() {
        Function custom1 = new Function("foo") {

            @Override
            public double apply(double... values) {
                return values[0] * E;
            }
        };
        Function custom2 = new Function("bar") {

            @Override
            public double apply(double... values) {
                return values[0] * PI;
            }
        };
        Expression e = new ExpressionBuilder("foo(bar(x))")
                .function(custom1)
                .function(custom2)
                .variables("x")
                .build()
                .setVariable("x", 1);
        double result = e.evaluate();
        Assertions.assertEquals(1 * E * PI, result, 0d);
    }

    @Test
    public void testFunction4() {
        Function custom1 = new Function("foo") {

            @Override
            public double apply(double... values) {
                return values[0] * E;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("foo(log(x))")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        Assertions.assertEquals(log(varX) * E, result, 0d);
    }

    @Test
    public void testFunction5() {
        Function custom1 = new Function("foo") {

            @Override
            public double apply(double... values) {
                return values[0] * E;
            }
        };
        Function custom2 = new Function("bar") {

            @Override
            public double apply(double... values) {
                return values[0] * PI;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("bar(foo(log(x)))")
                .variables("x")
                .function(custom1)
                .function(custom2)
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        Assertions.assertEquals(log(varX) * E * PI, result, 0d);
    }

    @Test
    public void testFunction6() {
        Function custom1 = new Function("foo") {

            @Override
            public double apply(double... values) {
                return values[0] * E;
            }
        };
        Function custom2 = new Function("bar") {

            @Override
            public double apply(double... values) {
                return values[0] * PI;
            }
        };
        double varX = 32.24979131d;
        Expression e = new ExpressionBuilder("bar(foo(log(x)))")
                .variables("x")
                .functions(custom1, custom2)
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        Assertions.assertEquals(log(varX) * E * PI, result, 0d);
    }

    @Test
    public void testFunction7() {
        Function custom1 = new Function("half") {

            @Override
            public double apply(double... values) {
                return values[0] / 2;
            }
        };
        Expression e = new ExpressionBuilder("half(x)")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", 1d);
        Assertions.assertEquals(0.5d, e.evaluate(), 0d);
    }

    @Test
    public void testFunction10() {
        Function custom1 = new Function("max", 2) {
            @Override
            public double apply(double... values) {
                return values[0] < values[1] ? values[1] : values[0];
            }
        };
        Expression e =
                new ExpressionBuilder("max(x,y)")
                        .variables("x", "y")
                        .function(custom1)
                        .build()
                        .setVariable("x", 1d)
                        .setVariable("y", 2d);
        Assertions.assertEquals(2, e.evaluate(), 0d);
    }

    @Test
    public void testFunction11() {
        Function custom1 = new Function("power", 2) {

            @Override
            public double apply(double... values) {
                return pow(values[0], values[1]);
            }
        };
        Expression e =
                new ExpressionBuilder("power(x,y)")
                        .variables("x", "y")
                        .function(custom1)
                        .build()
                        .setVariable("x", 2d)
                        .setVariable("y", 4d);
        Assertions.assertEquals(pow(2, 4), e.evaluate(), 0d);
    }

    @Test
    public void testFunction12() {
        Function custom1 = new Function("max", 5) {

            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < getNumArguments(); i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        Expression e = new ExpressionBuilder("max(1,2.43311,51.13,43,12)")
                .function(custom1)
                .build();
        Assertions.assertEquals(51.13d, e.evaluate(), 0d);
    }

    @Test
    public void testFunction13() {
        Function custom1 = new Function("max", 3) {

            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < getNumArguments(); i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        double varX = E;
        Expression e = new ExpressionBuilder("max(log(x),sin(x),x)")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        Assertions.assertEquals(varX, e.evaluate(), 0d);
    }

    @Test
    public void testFunction14() {
        Function custom1 = new Function("multiply", 2) {

            @Override
            public double apply(double... values) {
                return values[0] * values[1];
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("multiply(sin(x),x+1)")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double expected = sin(varX) * (varX + 1);
        double actual = e.evaluate();
        Assertions.assertEquals(expected, actual, 0d);
    }

    @Test
    public void testFunction15() {
        Function custom1 = new Function("timesPi") {

            @Override
            public double apply(double... values) {
                return values[0] * PI;
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("timesPi(x^2)")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double expected = varX * PI;
        double actual = e.evaluate();
        Assertions.assertEquals(expected, actual, 0d);
    }

    @Test
    public void testFunction16() {
        Function custom1 = new Function("multiply", 3) {

            @Override
            public double apply(double... values) {
                return values[0] * values[1] * values[2];
            }
        };
        double varX = 1;
        Expression e = new ExpressionBuilder("multiply(sin(x),x+1^(-2),log(x))")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double expected = sin(varX) * pow((varX + 1), -2) * log(varX);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testFunction17() {
        Function custom1 = new Function("timesPi") {

            @Override
            public double apply(double... values) {
                return values[0] * PI;
            }
        };
        double varX = E;
        Expression e = new ExpressionBuilder("timesPi(log(x^(2+1)))")
                .variables("x")
                .function(custom1)
                .build()
                .setVariable("x", varX);
        double expected = log(pow(varX, 3)) * PI;
        Assertions.assertEquals(expected, e.evaluate(), 0.0);
    }

    // thanks to Marcin Domanski who issued
    // http://jira.congrace.de/jira/browse/EXP-11
    // i have this test, which fails in 0.2.9
    @Test
    public void testFunction18() {
        Function minFunction = new Function("min", 2) {

            @Override
            public double apply(double[] values) {
                double currentMin = Double.POSITIVE_INFINITY;
                for (double value : values) {
                    currentMin = min(currentMin, value);
                }
                return currentMin;
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("-min(5, 0) + 10")
                .function(minFunction);
        double calculated = b.build().evaluate();
        Assertions.assertEquals(10, calculated, 0.0);
    }

    // thanks to Sylvain Machefert who issued
    // http://jira.congrace.de/jira/browse/EXP-11
    // i have this test, which fails in 0.3.2
    @Test
    public void testFunction19() {
        Function minFunction = new Function("power", 2) {

            @Override
            public double apply(double[] values) {
                return pow(values[0], values[1]);
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("power(2,3)")
                .function(minFunction);
        double calculated = b.build().evaluate();
        Assertions.assertEquals(pow(2, 3), calculated, 0d);
    }

    // thanks to Narendra Harmwal who noticed that getArgumentCount was not
    // implemented
    // this test has been added in 0.3.5
    @Test
    public void testFunction20() {
        Function maxFunction = new Function("max", 3) {

            @Override
            public double apply(double... values) {
                double max = values[0];
                for (int i = 1; i < getNumArguments(); i++) {
                    if (values[i] > max) {
                        max = values[i];
                    }
                }
                return max;
            }
        };
        ExpressionBuilder b = new ExpressionBuilder("max(1,2,3)")
                .function(maxFunction);
        double calculated = b.build().evaluate();
        Assertions.assertEquals(3, maxFunction.getNumArguments());
        Assertions.assertEquals(3, calculated, 0d);
    }

    @Test
    public void testOperators1() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        Expression e = new ExpressionBuilder("1!").operator(factorial)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
        e = new ExpressionBuilder("2!").operator(factorial)
                .build();
        Assertions.assertEquals(2d, e.evaluate(), 0d);
        e = new ExpressionBuilder("3!").operator(factorial)
                .build();
        Assertions.assertEquals(6d, e.evaluate(), 0d);
        e = new ExpressionBuilder("4!").operator(factorial)
                .build();
        Assertions.assertEquals(24d, e.evaluate(), 0d);
        e = new ExpressionBuilder("5!").operator(factorial)
                .build();
        Assertions.assertEquals(120d, e.evaluate(), 0d);
        e = new ExpressionBuilder("11!").operator(factorial)
                .build();
        Assertions.assertEquals(39916800d, e.evaluate(), 0d);
    }

    @Test
    public void testOperators2() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };
        Expression e = new ExpressionBuilder("2^3!").operator(factorial)
                .build();
        Assertions.assertEquals(64d, e.evaluate(), 0d);
        e = new ExpressionBuilder("3!^2").operator(factorial)
                .build();
        Assertions.assertEquals(36d, e.evaluate(), 0d);
        e = new ExpressionBuilder("-(3!)^-1").operator(factorial)
                .build();
        double actual = e.evaluate();
        Assertions.assertEquals(pow(-6d, -1), actual, 0d);
    }

    @Test
    public void testOperators3() {
        Operator gteq = new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Expression e = new ExpressionBuilder("1>=2").operator(gteq)
                .build();
        Assertions.assertEquals(0d, e.evaluate(), 0d);
        e = new ExpressionBuilder("2>=1").operator(gteq)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
        e = new ExpressionBuilder("-2>=1").operator(gteq)
                .build();
        Assertions.assertEquals(0d, e.evaluate(), 0d);
        e = new ExpressionBuilder("-2>=-1").operator(gteq)
                .build();
        Assertions.assertEquals(0d, e.evaluate(), 0d);
    }

    @Test
    public void testModulo1() {
        double result = new ExpressionBuilder("33%(20/2)%2")
                .build().evaluate();
        Assertions.assertEquals(1d, result, 0d);
    }

    @Test
    public void testOperators4() {
        Operator greaterEq = new Operator(">=", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Operator greater = new Operator(">", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                if (values[0] > values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };
        Operator newPlus = new Operator(">=>", 2, true, 4) {

            @Override
            public double apply(double[] values) {
                return values[0] + values[1];
            }
        };
        Expression e = new ExpressionBuilder("1>2").operator(greater)
                .build();
        Assertions.assertEquals(0d, e.evaluate(), 0d);
        e = new ExpressionBuilder("2>=2").operator(greaterEq)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
        e = new ExpressionBuilder("1>=>2").operator(newPlus)
                .build();
        Assertions.assertEquals(3d, e.evaluate(), 0d);
        e = new ExpressionBuilder("1>=>2>2").operator(greater).operator(newPlus)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
        e = new ExpressionBuilder("1>=>2>2>=1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
        e = new ExpressionBuilder("1 >=> 2 > 2 >= 1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
        e = new ExpressionBuilder("1 >=> 2 >= 2 > 1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        Assertions.assertEquals(0d, e.evaluate(), 0d);
        e = new ExpressionBuilder("1 >=> 2 >= 2 > 0").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
        e = new ExpressionBuilder("1 >=> 2 >= 2 >= 1").operator(greater).operator(newPlus)
                .operator(greaterEq)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
    }

    @Test
    public void testInvalidOperator1() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Operator fail = new Operator("2", 2, true, 1) {

                @Override
                public double apply(double[] values) {
                    return 0;
                }
            };
            new ExpressionBuilder("1").operator(fail)
                    .build();
        });
    }

    @Test
    public void testInvalidFunction1() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Function("1gd") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        });
    }

    @Test
    public void testInvalidFunction2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new Function("+1gd") {
            @Override
            public double apply(double... args) {
                return 0;
            }
        });
    }

    @Test
    public void testExpressionBuilder01() {
        Expression e = new ExpressionBuilder("7*x + 3*y")
                .variables("x", "y")
                .build()
                .setVariable("x", 1)
                .setVariable("y", 2);
        double result = e.evaluate();
        Assertions.assertEquals(13d, result, 0d);
    }

    @Test
    public void testExpressionBuilder02() {
        Expression e = new ExpressionBuilder("7*x + 3*y")
                .variables("x", "y")
                .build()
                .setVariable("x", 1)
                .setVariable("y", 2);
        double result = e.evaluate();
        Assertions.assertEquals(13d, result, 0d);
    }

    @Test
    public void testExpressionBuilder03() {
        double varX = 1.3d;
        double varY = 4.22d;
        Expression e = new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y")
                .variables("x", "y")
                .build()
                .setVariable("x", varX)
                .setVariable("y",
                        varY);
        double result = e.evaluate();
        Assertions.assertEquals(7 * varX + 3 * varY - pow(log(varY / varX * 12), varY), result, 0d);
    }

    @Test
    public void testExpressionBuilder04() {
        double varX = 1.3d;
        double varY = 4.22d;
        Expression e =
                new ExpressionBuilder("7*x + 3*y - log(y/x*12)^y")
                        .variables("x", "y")
                        .build()
                        .setVariable("x", varX)
                        .setVariable("y", varY);
        double result = e.evaluate();
        Assertions.assertEquals(7 * varX + 3 * varY - pow(log(varY / varX * 12), varY), result, 0d);
        varX = 1.79854d;
        varY = 9281.123d;
        e.setVariable("x", varX);
        e.setVariable("y", varY);
        result = e.evaluate();
        Assertions.assertEquals(7 * varX + 3 * varY - pow(log(varY / varX * 12), varY), result, 0d);
    }

    @Test
    public void testExpressionBuilder05() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            double varX = 1.3d;
            double varY = 4.22d;
            Expression e = new ExpressionBuilder("3*y")
                    .variables("y")
                    .build()
                    .setVariable("x", varX)
                    .setVariable("y", varY);
            double result = e.evaluate();
            Assertions.assertEquals(3 * varY, result, 0d);
        });
    }

    @Test
    public void testExpressionBuilder06() {
        double varX = 1.3d;
        double varY = 4.22d;
        double varZ = 4.22d;
        Expression e = new ExpressionBuilder("x * y * z")
                .variables("x", "y", "z")
                .build();
        e.setVariable("x", varX);
        e.setVariable("y", varY);
        e.setVariable("z", varZ);
        double result = e.evaluate();
        Assertions.assertEquals(varX * varY * varZ, result, 0d);
    }

    @Test
    public void testExpressionBuilder07() {
        double varX = 1.3d;
        Expression e = new ExpressionBuilder("log(sin(x))")
                .variables("x")
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        Assertions.assertEquals(log(sin(varX)), result, 0d);
    }

    @Test
    public void testExpressionBuilder08() {
        double varX = 1.3d;
        Expression e = new ExpressionBuilder("log(sin(x))")
                .variables("x")
                .build()
                .setVariable("x", varX);
        double result = e.evaluate();
        Assertions.assertEquals(log(sin(varX)), result, 0d);
    }

    @Test
    public void testSameName() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Function custom = new Function("bar") {

                @Override
                public double apply(double... values) {
                    return values[0] / 2;
                }
            };
            double varBar = 1.3d;
            Expression e = new ExpressionBuilder("bar(bar)")
                    .variables("bar")
                    .function(custom)
                    .build()
                    .setVariable("bar", varBar);
            ValidationResult res = e.validate();
            Assertions.assertFalse(res.isValid());
            Assertions.assertEquals(1, res.getErrors().size());
        });
    }

    @Test
    public void testInvalidFunction() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            double varY = 4.22d;
            Expression e = new ExpressionBuilder("3*invalid_function(y)")
                    .variables("<")
                    .build()
                    .setVariable("y", varY);
            e.evaluate();
        });
    }

    @Test
    public void testMissingVar() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            double varY = 4.22d;
            Expression e = new ExpressionBuilder("3*y*z")
                    .variables("y", "z")
                    .build()
                    .setVariable("y", varY);
            e.evaluate();
        });
    }

    @Test
    public void testUnaryMinusPowerPrecedence() {
        Expression e = new ExpressionBuilder("-1^2")
                .build();
        Assertions.assertEquals(-1d, e.evaluate(), 0d);
    }

    @Test
    public void testUnaryMinus() {
        Expression e = new ExpressionBuilder("-1")
                .build();
        Assertions.assertEquals(-1d, e.evaluate(), 0d);
    }

    @Test
    public void testExpression1() {
        String expr;
        double expected;
        expr = "2 + 4";
        expected = 6d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression10() {
        String expr;
        double expected;
        expr = "1 * 1.5 + 1";
        expected = 1 * 1.5 + 1;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression11() {
        double x = 1d;
        double y = 2d;
        String expr = "log(x) ^ sin(y)";
        double expected = pow(log(x), sin(y));
        Expression e = new ExpressionBuilder(expr)
                .variables("x", "y")
                .build()
                .setVariable("x", x)
                .setVariable("y", y);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression12() {
        String expr = "log(2.5333333333)^(0-1)";
        double expected = pow(log(2.5333333333d), -1);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression13() {
        String expr = "2.5333333333^(0-1)";
        double expected = pow(2.5333333333d, -1);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression14() {
        String expr = "2 * 17.41 + (12*2)^(0-1)";
        double expected = 2 * 17.41d + pow((12 * 2), -1);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression15() {
        String expr = "2.5333333333 * 17.41 + (12*2)^log(2.764)";
        double expected = 2.5333333333d * 17.41d + pow((12 * 2), log(2.764d));
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression16() {
        String expr = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
        double expected = 2.5333333333d / 2 * 17.41d + pow((12 * 2), log(2.764d) - sin(5.6664d));
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression17() {
        String expr = "x^2 - 2 * y";
        double x = E;
        double y = PI;
        double expected = x * x - 2 * y;
        Expression e = new ExpressionBuilder(expr)
                .variables("x", "y")
                .build()
                .setVariable("x", x)
                .setVariable("y", y);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression18() {
        String expr = "-3";
        double expected = -3;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression19() {
        String expr = "-3 * -24.23";
        double expected = -3 * -24.23d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression2() {
        String expr;
        double expected;
        expr = "2+3*4-12";
        expected = 2 + 3 * 4 - 12;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression20() {
        String expr = "-2 * 24/log(2) -2";
        double expected = -2 * 24 / log(2) - 2;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression21() {
        String expr = "-2 *33.34/log(x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / pow(log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build()
                .setVariable("x", x);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpressionPower() {
        String expr = "2^-2";
        double expected = pow(2, -2);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpressionMultiplication() {
        String expr = "2*-2";
        double expected = -4d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression22() {
        String expr = "-2 *33.34/log(x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / pow(log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build()
                .setVariable("x", x);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression23() {
        String expr = "-2 *33.34/(log(foo)^-2 + 14 *6) - sin(foo)";
        double x = 1.334d;
        double expected = -2 * 33.34 / (pow(log(x), -2) + 14 * 6) - sin(x);
        Expression e = new ExpressionBuilder(expr)
                .variables("foo")
                .build()
                .setVariable("foo", x);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression24() {
        String expr = "3+4-log(23.2)^(2-1) * -1";
        double expected = 3 + 4 - pow(log(23.2), (2 - 1)) * -1;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression25() {
        String expr = "+3+4-+log(23.2)^(2-1) * + 1";
        double expected = 3 + 4 - log(23.2d);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression26() {
        String expr = "14 + -(1 / 2.22^3)";
        double expected = 14 + -(1d / pow(2.22d, 3d));
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression27() {
        String expr = "12^-+-+-+-+-+-+---2";
        double expected = pow(12, -2);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression28() {
        String expr = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323) ";
        double expected = pow(12, -2) * -14 / pow(2, -log(2.22323));
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression29() {
        String expr = "24.3343 % 3";
        double expected = 24.3343 % 3;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testVarname1() {
        String expr = "12.23 * foo.bar";
        Expression e = new ExpressionBuilder(expr)
                .variables("foo.bar")
                .build()
                .setVariable("foo.bar", 1d);
        Assertions.assertEquals(12.23d, e.evaluate(), 0d);
    }

    @Test
    public void testMisplacedSeparator() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr = "12.23 * ,foo";
            Expression e = new ExpressionBuilder(expr)
                    .build()
                    .setVariable(",foo", 1d);
            Assertions.assertEquals(12.23, e.evaluate(), 0d);
        });
    }

    @Test
    public void testInvalidVarname() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr = "12.23 * @foo";
            Expression e = new ExpressionBuilder(expr)
                    .build()
                    .setVariable("@foo", 1d);
            Assertions.assertEquals(12.23d, e.evaluate(), 0d);
        });
    }

    @Test
    public void testVarMap() {
        String expr = "12.23 * foo - bar";
        Map<String, Double> variables = new HashMap<>();
        variables.put("foo", 2d);
        variables.put("bar", 3.3d);
        Expression e = new ExpressionBuilder(expr)
                .variables(variables.keySet())
                .build()
                .setVariables(variables);
        Assertions.assertEquals(12.23d * 2d - 3.3d, e.evaluate(), 0d);
    }

    @Test
    public void testInvalidNumberofArguments1() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr = "log(2,2)";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testInvalidNumberOfArguments2() {
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> {
            new Function("avg", 4) {
                @Override
                public double apply(double... args) {
                    double sum = 0;
                    for (double arg : args) {
                        sum += arg;
                    }
                    return sum / args.length;
                }
            };
            String expr = "avg(2,2)";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testExpression3() {
        String expr;
        double expected;
        expr = "2+4*5";
        expected = 2 + 4 * 5;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression30() {
        String expr = "24.3343 % 3 * 20 ^ -(2.334 % log(2 / 14))";
        double expected = 24.3343d % 3 * pow(20, -(2.334 % log(2d / 14d)));
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression31() {
        String expr = "-2 *33.34/log(y_x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / pow(log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("y_x")
                .build()
                .setVariable("y_x", x);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression32() {
        String expr = "-2 *33.34/log(y_2x)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / pow(log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("y_2x")
                .build()
                .setVariable("y_2x", x);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression33() {
        String expr = "-2 *33.34/log(_y)^-2 + 14 *6";
        double x = 1.334d;
        double expected = -2 * 33.34 / pow(log(x), -2) + 14 * 6;
        Expression e = new ExpressionBuilder(expr)
                .variables("_y")
                .build()
                .setVariable("_y", x);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression34() {
        String expr = "-2 + + (+4) +(4)";
        double expected = -2 + 4 + 4;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression40() {
        String expr = "1e1";
        double expected = 10d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression41() {
        String expr = "1e-1";
        double expected = 0.1d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    /*
     * Added tests for expressions with scientific notation see http://jira.congrace.de/jira/browse/EXP-17
     */
    @Test
    public void testExpression42() {
        String expr = "7.2973525698e-3";
        double expected = 7.2973525698e-3d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression43() {
        String expr = "6.02214E23";
        double expected = 6.02214e23d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        double result = e.evaluate();
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testExpression44() {
        String expr = "6.02214E23";
        double expected = 6.02214e23d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression45() {
        Assertions.assertThrowsExactly(NumberFormatException.class, () -> {
            String expr = "6.02214E2E3";
            new ExpressionBuilder(expr)
                    .build();
        });
    }

    @Test
    public void testExpression46() {
        Assertions.assertThrowsExactly(NumberFormatException.class, () -> {
            String expr = "6.02214e2E3";
            new ExpressionBuilder(expr)
                    .build();
        });
    }

    // tests for EXP-20: No exception is thrown for unmatched parenthesis in
    // build
    // Thanks go out to maheshkurmi for reporting
    @Test
    public void testExpression48() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr = "(1*2";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testExpression49() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr = "{1*2";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testExpression50() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr = "[1*2";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testExpression51() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr = "(1*{2+[3}";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testExpression52() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr = "(1*(2+(3";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testExpression53() {
        String expr = "14 * 2x";
        Expression exp = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        exp.setVariable("x", 1.5d);
        Assertions.assertTrue(exp.validate().isValid());
        Assertions.assertEquals(14d * 2d * 1.5d, exp.evaluate(), 0d);
    }

    @Test
    public void testExpression54() {
        String expr = "2 ((-(x)))";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        e.setVariable("x", 1.5d);
        Assertions.assertEquals(-3d, e.evaluate(), 0d);
    }

    @Test
    public void testExpression55() {
        String expr = "2 sin(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        e.setVariable("x", 2d);
        Assertions.assertEquals(sin(2d) * 2, e.evaluate(), 0d);
    }

    @Test
    public void testExpression56() {
        String expr = "2 sin(3x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        e.setVariable("x", 2d);
        Assertions.assertEquals(sin(6d) * 2d, e.evaluate(), 0d);
    }

    @Test
    public void testDocumentationExample1() {
        Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        double result = e.evaluate();
        double expected = 3 * sin(3.14d) - 2d / (2.3d - 2d);
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testDocumentationExample2() throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(1);
        Expression e = new ExpressionBuilder("3log(y)/(x+1)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        Future<Double> result = e.evaluateAsync(exec);
        double expected = 3 * log(3.14d) / (3.3);
        Assertions.assertEquals(expected, result.get(), 0d);
    }

    @Test
    public void testDocumentationExample3() {
        double result = new ExpressionBuilder("2cos(xy)")
                .variables("x", "y")
                .build()
                .setVariable("x", 0.5d)
                .setVariable("y", 0.25d)
                .evaluate();
        Assertions.assertEquals(2d * cos(0.5d * 0.25d), result, 0d);
    }

    @Test
    public void testDocumentationExample5() {
        String expr = "7.2973525698e-3";
        double expected = Double.parseDouble(expr);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }


    @Test
    public void testDocumentationExample6() {
        Function logb = new Function("logb", 2) {
            @Override
            public double apply(double... args) {
                return log(args[0]) / log(args[1]);
            }
        };
        double result = new ExpressionBuilder("logb(8, 2)")
                .function(logb)
                .build()
                .evaluate();
        double expected = 3;
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testDocumentationExample7() {
        Function avg = new Function("avg", 4) {

            @Override
            public double apply(double... args) {
                double sum = 0;
                for (double arg : args) {
                    sum += arg;
                }
                return sum / args.length;
            }
        };
        double result = new ExpressionBuilder("avg(1,2,3,4)")
                .function(avg)
                .build()
                .evaluate();

        double expected = 2.5d;
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testDocumentationExample8() {
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        double result = new ExpressionBuilder("3!")
                .operator(factorial)
                .build()
                .evaluate();

        double expected = 6d;
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testDocumentationExample9() {
        Operator gteq = new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

            @Override
            public double apply(double[] values) {
                if (values[0] >= values[1]) {
                    return 1d;
                } else {
                    return 0d;
                }
            }
        };

        Expression e = new ExpressionBuilder("1>=2").operator(gteq)
                .build();
        Assertions.assertEquals(0d, e.evaluate(), 0d);
        e = new ExpressionBuilder("2>=1").operator(gteq)
                .build();
        Assertions.assertEquals(1d, e.evaluate(), 0d);
    }

    @Test
    public void testDocumentationExample10() {
        Assertions.assertThrowsExactly(ArithmeticException.class, () -> {
            Operator reciprocal = new Operator("$", 1, true, Operator.PRECEDENCE_DIVISION) {
                @Override
                public double apply(final double... args) {
                    if (args[0] == 0d) {
                        throw new ArithmeticException("Division by zero!");
                    }
                    return 1d / args[0];
                }
            };
            Expression e = new ExpressionBuilder("0$").operator(reciprocal).build();
            e.evaluate();
        });
    }

    @Test
    public void testDocumentationExample11() {
        Expression e = new ExpressionBuilder("x")
                .variable("x")
                .build();

        ValidationResult res = e.validate();
        Assertions.assertFalse(res.isValid());
        Assertions.assertEquals(1, res.getErrors().size());

        e.setVariable("x", 1d);
        res = e.validate();
        Assertions.assertTrue(res.isValid());
    }

    @Test
    public void testDocumentationExample12() {
        Expression e = new ExpressionBuilder("x")
                .variable("x")
                .build();

        ValidationResult res = e.validate(false);
        Assertions.assertTrue(res.isValid());
        Assertions.assertEquals(Collections.EMPTY_LIST, res.getErrors());
    }

    // Thanks go out to Johan Björk for reporting the division by zero problem EXP-22
    // https://www.objecthunter.net/jira/browse/EXP-22
    @Test
    public void testExpression57() {
        Assertions.assertThrowsExactly(ArithmeticException.class, () -> {
            String expr = "1 / 0";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            Assertions.assertEquals(Double.POSITIVE_INFINITY, e.evaluate(), 0d);
        });
    }

    @Test
    public void testExpression58() {
        String expr = "17 * sqrt(-1) * 12";
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertTrue(Double.isNaN(e.evaluate()));
    }

    // Thanks go out to Alex Dolinsky for reporting the missing exception when an empty
    // expression is passed as in new ExpressionBuilder("")
    @Test
    public void testExpression59() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder("")
                .build());
    }

    @Test
    public void testExpression60() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression e = new ExpressionBuilder("   ")
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testExpression61() {
        Expression e = new ExpressionBuilder("14 % 0").build();
        Assertions.assertThrowsExactly(ArithmeticException.class, e::evaluate);
    }

    // https://www.objecthunter.net/jira/browse/EXP-24
    // thanks go out to Rémi for the issue report
    @Test
    public void testExpression62() {
        Expression e = new ExpressionBuilder("x*1.0e5+5")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(E * 1.0 * pow(10, 5) + 5, e.evaluate(), 0d);
    }

    @Test
    public void testExpression63() {
        Expression e = new ExpressionBuilder("log10(5)").build();
        Assertions.assertEquals(log10(5), e.evaluate(), 0d);
    }

    @Test
    public void testExpression64() {
        Expression e = new ExpressionBuilder("log2(5)").build();
        Assertions.assertEquals(log(5) / log(2), e.evaluate(), 0d);
    }

    @Test
    public void testExpression65() {
        Expression e = new ExpressionBuilder("2log(e())").build();
        Assertions.assertEquals(2d, e.evaluate(), 0d);
    }

    @Test
    public void testExpression66() {
        Expression e = new ExpressionBuilder("log(e())2").build();
        Assertions.assertEquals(2d, e.evaluate(), 0d);
    }

    @Test
    public void testExpression67() {
        Expression e = new ExpressionBuilder("2e()sin(pi()/2)").build();
        Assertions.assertEquals(2 * E * sin(PI / 2d), e.evaluate(), 0d);
    }

    @Test
    public void testExpression68() {
        Expression e = new ExpressionBuilder("2x")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(2 * E, e.evaluate(), 0d);
    }

    @Test
    public void testExpression69() {
        Expression e = new ExpressionBuilder("2x2")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(4 * E, e.evaluate(), 0d);
    }

    @Test
    public void testExpression70() {
        Expression e = new ExpressionBuilder("2xx")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(2 * E * E, e.evaluate(), 0d);
    }

    @Test
    public void testExpression71() {
        Expression e = new ExpressionBuilder("x2x")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(2 * E * E, e.evaluate(), 0d);
    }

    @Test
    public void testExpression72() {
        Expression e = new ExpressionBuilder("2cos(x)")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(2 * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression73() {
        Expression e = new ExpressionBuilder("cos(x)2")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(2 * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression74() {
        Expression e = new ExpressionBuilder("cos(x)(-2)")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(-2d * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression75() {
        Expression e = new ExpressionBuilder("(-2)cos(x)")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(-2d * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression76() {
        Expression e = new ExpressionBuilder("(-x)cos(x)")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(-E * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression77() {
        Expression e = new ExpressionBuilder("(-xx)cos(x)")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(-E * E * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression78() {
        Expression e = new ExpressionBuilder("(xx)cos(x)")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(E * E * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression79() {
        Expression e = new ExpressionBuilder("cos(x)(xx)")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(E * E * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression80() {
        Expression e = new ExpressionBuilder("cos(x)(xy)")
                .variables("x", "y")
                .build()
                .setVariable("x", E)
                .setVariable("y", sqrt(2));
        Assertions.assertEquals(sqrt(2) * E * cos(E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression81() {
        Expression e = new ExpressionBuilder("cos(xy)")
                .variables("x", "y")
                .build()
                .setVariable("x", E)
                .setVariable("y", sqrt(2));
        Assertions.assertEquals(cos(sqrt(2) * E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression82() {
        Expression e = new ExpressionBuilder("cos(2x)")
                .variables("x")
                .build()
                .setVariable("x", E);
        Assertions.assertEquals(cos(2 * E), e.evaluate(), 0d);
    }

    @Test
    public void testExpression83() {
        Expression e = new ExpressionBuilder("cos(xlog(xy))")
                .variables("x", "y")
                .build()
                .setVariable("x", E)
                .setVariable("y", sqrt(2));
        Assertions.assertEquals(cos(E * log(E * sqrt(2))), e.evaluate(), 0d);
    }

    @Test
    public void testExpression84() {
        Expression e = new ExpressionBuilder("3x_1")
                .variables("x_1")
                .build()
                .setVariable("x_1", E);
        Assertions.assertEquals(3d * E, e.evaluate(), 0d);
    }

    @Test
    public void testExpression85() {
        Expression e = new ExpressionBuilder("1/2x")
                .variables("x")
                .build()
                .setVariable("x", 6);
        Assertions.assertEquals(3d, e.evaluate(), 0d);
    }

    // thanks got out to David Sills
    @Test
    public void testSpaceBetweenNumbers() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder("1 1")
                .build());
    }

    // thanks go out to Janny for providing the tests and the bug report
    @Test
    public void testUnaryMinusInParenthesisSpace() {
        ExpressionBuilder b = new ExpressionBuilder("( -1)^2");
        double calculated = b.build().evaluate();
        Assertions.assertEquals(1, calculated, 0d);
    }

    @Test
    public void testUnaryMinusSpace() {
        ExpressionBuilder b = new ExpressionBuilder(" -1 + 2");
        double calculated = b.build().evaluate();
        Assertions.assertEquals(1, calculated, 0d);
    }

    @Test
    public void testUnaryMinusSpaces() {
        ExpressionBuilder b = new ExpressionBuilder(" -1 + + 2 +   -   1");
        double calculated = b.build().evaluate();
        Assertions.assertEquals(0, calculated, 0d);
    }

    @Test
    public void testUnaryMinusSpace1() {
        ExpressionBuilder b = new ExpressionBuilder("-1");
        double calculated = b.build().evaluate();
        Assertions.assertEquals(-1, calculated, 0d);
    }

    @Test
    public void testExpression4() {
        String expr;
        double expected;
        expr = "2+4 * 5";
        expected = 2 + 4 * 5;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression5() {
        String expr;
        double expected;
        expr = "(2+4)*5";
        expected = (2 + 4) * 5;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression6() {
        String expr;
        double expected;
        expr = "(2+4)*5 + 2.5*2";
        expected = (2 + 4) * 5 + 2.5 * 2;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression7() {
        String expr;
        double expected;
        expr = "(2+4)*5 + 10/2";
        expected = (2 + 4) * 5 + 10 / 2.;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression8() {
        String expr;
        double expected;
        expr = "(2 * 3 +4)*5 + 10/2";
        expected = (2 * 3 + 4) * 5 + 10 / 2.;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testExpression9() {
        String expr;
        double expected;
        expr = "(2 * 3 +4)*5 +4 + 10/2";
        expected = (2 * 3 + 4) * 5 + 4 + 10 / 2.;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testFailUnknownFunction1() {
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> {
            String expr;
            expr = "lig(1)";
            Expression e = new ExpressionBuilder(expr)
                    .build();
            e.evaluate();
        });
    }

    @Test
    public void testFailUnknownFunction2() {
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> {
            String expr;
            expr = "galength(1)";
            new ExpressionBuilder(expr)
                    .build().evaluate();
        });
    }

    @Test
    public void testFailUnknownFunction3() {
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> {
            String expr;
            expr = "tcos(1)";
            Expression exp = new ExpressionBuilder(expr)
                    .build();
            double result = exp.evaluate();
            System.out.println(result);
        });
    }

    @Test
    public void testFunction22() {
        String expr;
        expr = "cos(cos_1)";
        Expression e = new ExpressionBuilder(expr)
                .variables("cos_1")
                .build()
                .setVariable("cos_1", 1d);
        Assertions.assertEquals(cos(1d), e.evaluate(), 0d);
    }

    @Test
    public void testFunction23() {
        String expr;
        expr = "log1p(1)";
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(log1p(1d), e.evaluate(), 0d);
    }

    @Test
    public void testFunction24() {
        String expr;
        expr = "pow(3,3)";
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(27d, e.evaluate(), 0d);
    }

    @Test
    public void testPostfix1() {
        String expr;
        double expected;
        expr = "2.2232^0.1";
        expected = pow(2.2232d, 0.1d);
        double actual = new ExpressionBuilder(expr)
                .build().evaluate();
        Assertions.assertEquals(expected, actual, 0d);
    }

    @Test
    public void testPostfixEverything() {
        String expr;
        double expected;
        expr = "(sin(12) + log(34)) * 3.42 - cos(2.234-log(2))";
        expected = (sin(12) + log(34)) * 3.42 - cos(2.234 - log(2));
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixExponentation1() {
        String expr;
        double expected;
        expr = "2^3";
        expected = pow(2, 3);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixExponentation2() {
        String expr;
        double expected;
        expr = "24 + 4 * 2^3";
        expected = 24 + 4 * pow(2, 3);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixExponentation3() {
        String expr;
        double expected;
        double x = 4.334d;
        expr = "24 + 4 * 2^x";
        expected = 24 + 4 * pow(2, x);
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build()
                .setVariable("x", x);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixExponentation4() {
        String expr;
        double expected;
        double x = 4.334d;
        expr = "(24 + 4) * 2^log(x)";
        expected = (24 + 4) * pow(2, log(x));
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build()
                .setVariable("x", x);
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixFunction1() {
        String expr;
        double expected;
        expr = "log(1) * sin(0)";
        expected = log(1) * sin(0);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixFunction10() {
        String expr;
        double expected;
        expr = "cbrt(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = cbrt(x);
            Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
        }
    }

    @Test
    public void testPostfixFunction11() {
        String expr;
        double expected;
        expr = "cos(x) - (1/cbrt(x))";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            if (x == 0d) continue;
            expected = cos(x) - (1 / cbrt(x));
            Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
        }
    }

    @Test
    public void testPostfixFunction12() {
        String expr;
        double expected;
        expr = "acos(x) * expm1(asin(x)) - exp(atan(x)) + floor(x) + cosh(x) - sinh(cbrt(x))";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected =
                    acos(x) * expm1(asin(x)) - exp(atan(x)) + floor(x) + cosh(x)
                            - sinh(cbrt(x));
            if (Double.isNaN(expected)) {
                Assertions.assertTrue(Double.isNaN(e.setVariable("x", x).evaluate()));
            } else {
                Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
            }
        }
    }

    @Test
    public void testPostfixFunction13() {
        String expr;
        double expected;
        expr = "acos(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = acos(x);
            if (Double.isNaN(expected)) {
                Assertions.assertTrue(Double.isNaN(e.setVariable("x", x).evaluate()));
            } else {
                Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
            }
        }
    }

    @Test
    public void testPostfixFunction14() {
        String expr;
        double expected;
        expr = " expm1(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = expm1(x);
            if (Double.isNaN(expected)) {
                Assertions.assertTrue(Double.isNaN(e.setVariable("x", x).evaluate()));
            } else {
                Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
            }
        }
    }

    @Test
    public void testPostfixFunction15() {
        String expr;
        double expected;
        expr = "asin(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = asin(x);
            if (Double.isNaN(expected)) {
                Assertions.assertTrue(Double.isNaN(e.setVariable("x", x).evaluate()));
            } else {
                Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
            }
        }
    }

    @Test
    public void testPostfixFunction16() {
        String expr;
        double expected;
        expr = " exp(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = exp(x);
            Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
        }
    }

    @Test
    public void testPostfixFunction17() {
        String expr;
        double expected;
        expr = "floor(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = floor(x);
            Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
        }
    }

    @Test
    public void testPostfixFunction18() {
        String expr;
        double expected;
        expr = " cosh(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = cosh(x);
            Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
        }
    }

    @Test
    public void testPostfixFunction19() {
        String expr;
        double expected;
        expr = "sinh(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = sinh(x);
            Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
        }
    }

    @Test
    public void testPostfixFunction20() {
        String expr;
        double expected;
        expr = "cbrt(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = cbrt(x);
            Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
        }
    }

    @Test
    public void testPostfixFunction21() {
        String expr;
        double expected;
        expr = "tanh(x)";
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        for (double x = -10; x < 10; x = x + 0.5d) {
            expected = tanh(x);
            Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
        }
    }

    @Test
    public void testPostfixFunction2() {
        String expr;
        double expected;
        expr = "log(1)";
        expected = 0d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixFunction3() {
        String expr;
        double expected;
        expr = "sin(0)";
        expected = 0d;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixFunction5() {
        String expr;
        double expected;
        expr = "ceil(2.3) +1";
        expected = ceil(2.3) + 1;
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixFunction6() {
        String expr;
        double expected;
        double x = 1.565d;
        double y = 2.1323d;
        expr = "ceil(x) + 1 / y * abs(1.4)";
        expected = ceil(x) + 1 / y * abs(1.4);
        Expression e = new ExpressionBuilder(expr)
                .variables("x", "y")
                .build();
        Assertions.assertEquals(expected, e.setVariable("x", x).setVariable("y", y).evaluate(), 0d);
    }

    @Test
    public void testPostfixFunction7() {
        String expr;
        double expected;
        double x = E;
        expr = "tan(x)";
        expected = tan(x);
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
    }

    @Test
    public void testPostfixFunction8() {
        String expr;
        double expected;
        expr = "2^3.4223232 + tan(e())";
        expected = pow(2, 3.4223232d) + tan(E);
        Expression e = new ExpressionBuilder(expr).build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixFunction9() {
        String expr;
        double expected;
        double x = E;
        expr = "cbrt(x)";
        expected = cbrt(x);
        Expression e = new ExpressionBuilder(expr)
                .variables("x")
                .build();
        Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
    }

    @Test
    public void testPostfixInvalidVariableName() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            String expr;
            double expected;
            double x = 4.5334332d;
            double log = PI;
            expr = "x * pi";
            expected = x * log;
            Expression e = new ExpressionBuilder(expr)
                    .variables("x", "pi")
                    .build();
            Assertions.assertEquals(expected, e.setVariable("x", x).setVariable("log", log).evaluate(), 0d);
        });
    }

    @Test
    public void testPostfixParanthesis() {
        String expr;
        double expected;
        expr = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
        expected = (3 + 3 * 14) * (2 * (24 - 17) - 14.) / ((34) - 2.);
        Expression e = new ExpressionBuilder(expr)
                .build();
        Assertions.assertEquals(expected, e.evaluate(), 0d);
    }

    @Test
    public void testPostfixVariables() {
        String expr;
        double expected;
        double x = 4.5334332d;
        expr = "x * pi()";
        expected = x * PI;
        Expression e = new ExpressionBuilder(expr).variable("x").build();
        Assertions.assertEquals(expected, e.setVariable("x", x).evaluate(), 0d);
    }

    @Test
    public void testUnicodeVariable1() {
        Expression e = new ExpressionBuilder("λ")
                .variable("λ")
                .build()
                .setVariable("λ", E);
        Assertions.assertEquals(E, e.evaluate(), 0d);
    }

    @Test
    public void testUnicodeVariable2() {
        Expression e = new ExpressionBuilder("log(3ε+1)")
                .variable("ε")
                .build()
                .setVariable("ε", E);
        Assertions.assertEquals(log(3 * E + 1), e.evaluate(), 0d);
    }

    @Test
    public void testUnicodeVariable3() {
        Function log = new Function("λωγ", 1) {

            @Override
            public double apply(double... args) {
                return log(args[0]);
            }
        };

        Expression e = new ExpressionBuilder("λωγ(π)")
                .variable("π")
                .function(log)
                .build()
                .setVariable("π", PI);
        Assertions.assertEquals(log(PI), e.evaluate(), 0d);
    }

    @Test
    public void testUnicodeVariable4() {
        Function log = new Function("λ_ωγ", 1) {

            @Override
            public double apply(double... args) {
                return log(args[0]);
            }
        };

        Expression e = new ExpressionBuilder("3λ_ωγ(πε6)")
                .variables("π", "ε")
                .function(log)
                .build()
                .setVariable("π", PI)
                .setVariable("ε", E);
        Assertions.assertEquals(3 * log(PI * E * 6), e.evaluate(), 0d);
    }

    // thanks go out to vandanagopal for reporting the issue
    // https://github.com/fasseg/exp4j/issues/23
    @Test
    public void testSecondArgumentNegative() {
        Function round = new Function("MULTIPLY", 2) {
            @Override
            public double apply(double... args) {
                return round(args[0] * args[1]);
            }
        };
        double result = new ExpressionBuilder("MULTIPLY(2,-1)")
                .function(round)
                .build()
                .evaluate();
        Assertions.assertEquals(-2d, result, 0d);
    }

    // Test for https://github.com/fasseg/exp4j/issues/65
    @Test
    public void testVariableWithDot() {
        double result = new ExpressionBuilder("2*SALARY.Basic")
                .variable("SALARY.Basic")
                .build()
                .setVariable("SALARY.Basic", 1.5d)
                .evaluate();
        Assertions.assertEquals(3d, result, 0d);
    }

    @Test
    public void testTwoAdjacentOperators() {
        final Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("Operand for factorial has to be an integer");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("The operand of the factorial can not be less than zero");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        double result = new ExpressionBuilder("3!+2")
                .operator(factorial)
                .build()
                .evaluate();

        double expected = 8d;
        Assertions.assertEquals(expected, result, 0d);
    }

    @Test
    public void testGetVariableNames1() {
        Expression e = new ExpressionBuilder("b*a-9.24c")
                .variables("b", "a", "c")
                .build();
        Set<String> variableNames = e.getVariableNames();
        Assertions.assertTrue(variableNames.contains("a"));
        Assertions.assertTrue(variableNames.contains("b"));
        Assertions.assertTrue(variableNames.contains("c"));
    }

    @Test
    public void testGetVariableNames2() {
        Expression e = new ExpressionBuilder("log(bar)-FOO.s/9.24c")
                .variables("bar", "FOO.s", "c")
                .build();
        Set<String> variableNames = e.getVariableNames();
        Assertions.assertTrue(variableNames.contains("bar"));
        Assertions.assertTrue(variableNames.contains("FOO.s"));
        Assertions.assertTrue(variableNames.contains("c"));
    }

    @Test
    public void testSameVariableAndBuiltinFunctionName() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder("log10(log10)")
                .variables("log10")
                .build());
    }

    @Test
    public void testSameVariableAndUserFunctionName() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder("2*tr+tr(2)")
                .variables("tr")
                .function(new Function("tr") {
                    @Override
                    public double apply(double... args) {
                        return 0;
                    }
                })
                .build());
    }

    @Test
    public void testSignum() {
        Expression e = new ExpressionBuilder("signum(1)")
                .build();
        Assertions.assertEquals(1, e.evaluate(), 0d);

        e = new ExpressionBuilder("signum(-1)")
                .build();
        Assertions.assertEquals(-1, e.evaluate(), 0d);

        e = new ExpressionBuilder("signum(--1)")
                .build();
        Assertions.assertEquals(1, e.evaluate(), 0d);

        e = new ExpressionBuilder("signum(+-1)")
                .build();
        Assertions.assertEquals(-1, e.evaluate(), 0d);

        e = new ExpressionBuilder("-+1")
                .build();
        Assertions.assertEquals(-1, e.evaluate(), 0d);

        e = new ExpressionBuilder("signum(-+1)")
                .build();
        Assertions.assertEquals(-1, e.evaluate(), 0d);
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("-12", new ExpressionBuilder("-12").toString());
        Assertions.assertEquals("(x) + pi()", new ExpressionBuilder("(x) + pi()").toString());
        Assertions.assertEquals("Blah blah blah", new ExpressionBuilder("Blah blah blah").toString());
    }

    @Test
    public void testOperatorFactorial() {
        Expression exp = new ExpressionBuilder("3!").build();
        Assertions.assertEquals(6, exp.evaluate(), 0);
        exp = new ExpressionBuilder("3!!").build();
        Assertions.assertEquals(720, exp.evaluate(), 0);
        Assertions.assertTrue(exp.validate().isValid());
        exp = new ExpressionBuilder("4 + 3!").build();
        Assertions.assertEquals(10, exp.evaluate(), 0);
        Assertions.assertTrue(exp.validate().isValid());
        exp = new ExpressionBuilder("3! * 2").build();
        Assertions.assertEquals(12, exp.evaluate(), 0);
        Assertions.assertTrue(exp.validate().isValid());
        exp = new ExpressionBuilder("2 * 3!").build();
        Assertions.assertEquals(12, exp.evaluate(), 0);
        Assertions.assertTrue(exp.validate().isValid());
        exp = new ExpressionBuilder("4 + (3!)").build();
        Assertions.assertEquals(10, exp.evaluate(), 0);
        Assertions.assertTrue(exp.validate().isValid());
        exp = new ExpressionBuilder("4 + 3! + 2 * 6").build();
        Assertions.assertEquals(22, exp.evaluate(), 0);
        Assertions.assertTrue(exp.validate().isValid());
    }

    @Test
    public void testOperatorFactorial2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder("!3").build());
    }

    @Test
    public void testOperatorFactorial3() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder("!!3").build());
    }

    @Test
    public void testOperatorFactorial4() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("1.5!").build();
            exp.evaluate();
        });
    }

    @Test
    public void testOperatorFactorial5() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("(-1)!").build();
            exp.evaluate();
        });
    }

    @Test
    public void testOperatorFactorial6() {
        Expression exp = new ExpressionBuilder("sin(3!)").build();
        Assertions.assertEquals(sin(6), exp.evaluate(), 1e-12);
    }

    @Test
    public void testOperatorFactorial7() {
        Expression exp = new ExpressionBuilder("sin(3!-2!)").build();
        Assertions.assertEquals(sin(4), exp.evaluate(), 1e-12);
    }

    @Test
    public void testOperatorFactorial8() {
        Expression exp = new ExpressionBuilder("sin(3!-2!) * 4 + 1").build();
        Assertions.assertEquals(sin(4) * 4 + 1, exp.evaluate(), 1e-12);
    }

    @Test
    public void testOperatorFactorial9() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("3 + !(-1)").build();
            exp.evaluate();
        });
    }

    @Test
    public void testOperatorFactorial10() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("sin(!3)").build();
            exp.evaluate();
        });
    }

    @Test
    public void testOperatorFactorial11() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("3 + !sin(3!)").build();
            exp.evaluate();
        });
    }

    @Test
    public void testOperatorFactorial12() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            Expression exp = new ExpressionBuilder("172!").build();
            exp.evaluate();
        });
    }

    @Test
    public void testFactorialIssue75() {
        Expression exp = new ExpressionBuilder("3!-2!").build();
        Assertions.assertEquals(4, exp.evaluate(), 1e-12);
    }

    @Test
    public void testEmptyExpression() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder("").build());
    }

    @Test
    public void testEmptyExpression2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ExpressionBuilder(null).build());
    }

    @Test
    public void testFunctionsList() {
        ExpressionBuilder builder = new ExpressionBuilder("foo(bar(1))");
        Function foo = new Function("foo") {
            @Override
            public double apply(double... args) {
                return args[0];
            }
        };
        Function bar = new Function("bar") {
            @Override
            public double apply(double... args) {
                return args[0];
            }
        };
        Function[] funcs = new Function[]{foo, bar};
        Expression exp = builder.functions(Arrays.asList(funcs)).build();
        Assertions.assertEquals(1, exp.evaluate(), 0);
    }

    @Test
    public void testFunctionsArray() {
        ExpressionBuilder builder = new ExpressionBuilder("foo(bar(1))");
        Function foo = new Function("foo") {
            @Override
            public double apply(double... args) {
                return args[0];
            }
        };
        Function bar = new Function("bar") {
            @Override
            public double apply(double... args) {
                return args[0];
            }
        };
        Expression exp = builder.functions(foo, bar).build();
        Assertions.assertEquals(1, exp.evaluate(), 0);
    }

    @Test
    public void testFunctionsArrayEmpty() {
        ExpressionBuilder builder = new ExpressionBuilder("1");
        Expression e = builder.functions().build();
        Assertions.assertEquals(1, e.evaluate(), 1e-9);
    }

    @Test
    public void testOperatorsList() {
        ExpressionBuilder builder = new ExpressionBuilder("(1|1)&0");
        Operator foo = new Operator("&", 2, true, 0) {
            @Override
            public double apply(double... args) {
                return args[0] * args[1];
            }
        };
        Operator bar = new Operator("|", 2, true, 0) {
            @Override
            public double apply(double... args) {
                return args[0] + args[1];
            }
        };
        Operator[] ops = new Operator[]{foo, bar};
        Expression exp = builder.operators(Arrays.asList(ops)).build();
        Assertions.assertEquals(0, exp.evaluate(), 0);
    }

    @Test
    public void testOperatorsArray() {
        ExpressionBuilder builder = new ExpressionBuilder("(1|1)&0");
        Operator foo = new Operator("&", 2, true, 0) {
            @Override
            public double apply(double... args) {
                return args[0] * args[1];
            }
        };
        Operator bar = new Operator("|", 2, true, 0) {
            @Override
            public double apply(double... args) {
                return args[0] + args[1];
            }
        };

        Expression exp = builder.operators(foo, bar).build();
        Assertions.assertEquals(0, exp.evaluate(), 0);
    }

    @Test
    public void testOperatorsArrayEmpty() {
        ExpressionBuilder builder = new ExpressionBuilder("1");
        Expression e = builder.functions().build();
        Assertions.assertEquals(1, e.evaluate(), 1e-9);
    }

    @Test
    public void testIssue79() {
        //https://github.com/fasseg/exp4j/issues/79
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> new ExpressionBuilder("field-plus+field-minus")
                .variables("field-plus", "field-minus")
                .build());
    }

    @Test
    public void testIssue79_2() {
        //https://github.com/fasseg/exp4j/issues/79
        Assertions.assertThrowsExactly(UnknownFunctionOrVariableException.class, () -> new ExpressionBuilder("hello world * 3")
                .variable("hello world")
                .build());
    }

    @Test
    public void testIssue79_3() {
        //https://github.com/fasseg/exp4j/issues/79
        Expression e = new ExpressionBuilder("hello world * 3")
                .variables("hello", "world")
                .build();
        Assertions.assertNotNull(e);
    }

}
