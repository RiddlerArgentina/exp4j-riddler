/* 
 * Copyright 2015-2018 Federico Vera
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
package net.objecthunter.exp4j.shuntingyard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import net.objecthunter.exp4j.extras.FunctionsMisc;
import net.objecthunter.exp4j.extras.OperatorsComparison;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.Token;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class SimplifierTest {

    @Test
    public void testSimplifierFlag() {
        final String expression = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = ShuntingYard.convertToRPN(
                true,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        assertEquals(1, stokens.length);
        assertTrue(tokens.length > stokens.length);
        assertEquals(Simplifier.simplify(tokens).length, stokens.length);
    }
    
    @Test
    public void testResult1() {
        Expression e = new ExpressionBuilder("2^3 + 4 / 2").build();
        final double expected = e.evaluate();

        Expression e2 = new ExpressionBuilder("2^3 + 4 / 2").build(true);
        final double actual = e2.evaluate();
        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testResult2() {
        Expression e = new ExpressionBuilder("2^3!").operator(FACT).build();
        final double expected = e.evaluate();

        Expression e2 = new ExpressionBuilder("2^3!").operator(FACT).build(true);
        final double actual = e2.evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testResult3() {
        Expression e = new ExpressionBuilder("-(3!)^-1").operator(FACT).build();
        final double expected = e.evaluate();

        Expression e2 = new ExpressionBuilder("-(3!)^-1").operator(FACT).build(true);
        final double actual = e2.evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testResult4() {
        final double varX = 1.3d;
        final double varY = 4.22d;
        Expression e = new ExpressionBuilder("7*x + 3*4.22 - log(1.3/4.22*12)^y")
                .variables("x", "y")
                .build()
                .setVariable("x", varX)
                .setVariable("y", varY);
        final double expected = e.evaluate();

        Expression e2 = new ExpressionBuilder("7*x + 3*4.22 - log(1.3/4.22*12)^y")
                .variables("x", "y")
                .build(true)
                .setVariable("x", varX)
                .setVariable("y", varY);
        final double actual = e2.evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testResult5() {
        final double x = 1.334d;
        Expression e = new ExpressionBuilder("-2 *33.34/log(x)^-2 + 14 *6")
                .variables("x")
                .build()
                .setVariable("x", x);
        final double expected = e.evaluate();

        Expression e2 = new ExpressionBuilder("-2 *33.34/log(x)^-2 + 14 *6")
                .variables("x")
                .build(true)
                .setVariable("x", x);
        final double actual = e2.evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testValidation1() {
        Function custom = new Function("bar") {
            @Override
            public double apply(double... values) {
                return values[0] / 2;
            }
        };
        
        Expression e = new ExpressionBuilder("bar(a)")
                .variables("a")
                .function(custom)
                .build(true);
        
        ValidationResult res = e.validate();
        assertFalse(res.isValid());
        assertEquals(1, res.getErrors().size());
        assertEquals("The setVariable 'a' has not been set", res.getErrors().get(0));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValidation2() {
        Function custom = new Function("bar") {
            @Override
            public double apply(double... values) {
                return values[0] / 2;
            }
        };
        
        final double varBar = 1.3d;
        new ExpressionBuilder("bar(bar)")
                .variables("bar")
                .function(custom)
                .build(true)
                .setVariable("bar", varBar);
    }

    @Test
    public void testOptimization1() {
        final String expression = "2.5333333333/2 * 17.41 + (12*2)^(log(2.764) - sin(5.6664))";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(1, stokens.length);
        assertTrue(tokens.length > stokens.length);

        final double expected = new ExpressionBuilder(expression).build().evaluate();
        final double actual   = new ExpressionBuilder(expression).build(true).evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testOptimization2() {
        final String expression = "4 * cos(sin(pi()))";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);

        userFunctions.put(PI.getName(), PI);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(1, stokens.length);
        assertTrue(tokens.length > stokens.length);

        final double expected = new ExpressionBuilder(expression).build().evaluate();
        final double actual   = new ExpressionBuilder(expression).build(true).evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testOptimization3() {
        final String expression = "4 * cos(sin(pi())) + x";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);

        userFunctions.put(PI.getName(), PI);
        variableNames.add("x");

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(3, stokens.length);
        assertTrue(tokens.length > stokens.length);


        final double expected = new ExpressionBuilder(expression).variable("x").build().setVariable("x", 1).evaluate();
        final double actual   = new ExpressionBuilder(expression).variable("x").build(true).setVariable("x", 1).evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testOptimization4() {
        final String expression = "12^-+-+-+-+-+-+---2 * (-14) / 2 ^ -log(2.22323)";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(1,  stokens.length);
        assertEquals(27, tokens .length);

        final double expected = new ExpressionBuilder(expression).build().evaluate();
        final double actual   = new ExpressionBuilder(expression).build(true).evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testOptimization5() {
        final String expression = "(3 + 3 * 14) * (2 * (24-17) - 14)/((34) -2)";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);
        
        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(1, stokens.length);
        assertTrue(tokens.length > stokens.length);

        final double expected = new ExpressionBuilder(expression).build().evaluate();
        final double actual   = new ExpressionBuilder(expression).build(true).evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testOptimization6() {
        final String expression = "24 + 4 * 2^x";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);
        
        variableNames.add("x");

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertTrue(stokens.length > 0);
        assertEquals(tokens.length, stokens.length);

        final double expected = new ExpressionBuilder(expression).variable("x").build().setVariable("x", 4).evaluate();
        final double actual   = new ExpressionBuilder(expression).variable("x").build(true).setVariable("x", 4).evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testOptimization7() {
        final String expression = " -1 + + 2 +   -   1";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);
        
        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(1, stokens.length);
        assertTrue(tokens.length > stokens.length);

        final double expected = new ExpressionBuilder(expression).build().evaluate();
        final double actual   = new ExpressionBuilder(expression).build(true).evaluate();

        assertEquals(expected, actual, 0d);
    }

    @Test
    public void testOptimization8() {
        System.setProperty("exp4j.simplify_enabled", "true");

        final String expression = "sin(0)";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);
        
        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(1, stokens.length);
        assertTrue(tokens.length > stokens.length);

        final double expected = new ExpressionBuilder(expression).build().evaluate();
        final double actual   = new ExpressionBuilder(expression).build(true).evaluate();

        assertEquals(expected, actual, 0d);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testOptimization9() throws Exception {
        new ExpressionBuilder("1/0").build(true);
    }

    @Test(expected = ArithmeticException.class)
    public void testOptimization10() throws Exception {
        Expression exp = new ExpressionBuilder("1/0").build();
        exp.evaluate();
    }

    @Test
    public void testOptimization11() throws Exception {
        final String expression = "log(0)";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);
        assertEquals(1, stokens.length);
        assertEquals(((NumberToken)stokens[0]).getValue(), Double.NEGATIVE_INFINITY, 0d);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testOptimization12() throws Exception {
        final String expression = "1/0";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        Simplifier.simplify(tokens);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimization13() throws Exception {
        new ExpressionBuilder("pow(3,2,5)").build().evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimization14() throws Exception {
        Expression exp = new ExpressionBuilder("pow(3,2,5)").build();
        exp.evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimization15() throws Exception {
        new ExpressionBuilder("pow(3,2,5)").build(true).evaluate();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOptimization16() throws Exception {
        Expression exp = new ExpressionBuilder("pow(3,2,5)").build(true);
        exp.evaluate();
    }

    @Test
    public void testOptimization17() throws Exception {
        final String expression = "1 > 2 & 3 < 4";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        userOperators.put(">", OperatorsComparison.getOperator(">"));
        userOperators.put("<", OperatorsComparison.getOperator("<"));
        
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );
        
        final Token[] stokens = Simplifier.simplify(tokens);

        //Since the expression is constant only the result should be in here
        assertEquals(1, stokens.length);
        assertNotEquals(tokens.length, stokens.length);

        final double real     = 0;
        final double expected = new ExpressionBuilder(expression)
                                            .operator(OperatorsComparison.getOperator(">"))
                                            .operator(OperatorsComparison.getOperator("<"))
                                            .build().evaluate();
        final double actual   = new ExpressionBuilder(expression)
                                            .operator(OperatorsComparison.getOperator(">"))
                                            .operator(OperatorsComparison.getOperator("<"))
                                            .build(true).evaluate();
        assertEquals(real, expected, 0d);
        assertEquals(expected, actual, 0d);
        assertEquals(actual, real, 0d);
    }

    @Test
    public void testOptimization18() throws Exception {
        final String expression = "if(1 > 2 & 3 < 4, pi(), e())";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        userOperators.put(">", OperatorsComparison.getOperator(">"));
        userOperators.put("<", OperatorsComparison.getOperator("<"));
        userFunctions.put("if", FunctionsMisc.getFunction("if"));
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );
        
        final Token[] stokens = Simplifier.simplify(tokens);

        //Since the expression is constant only the result should be in here
        assertEquals(1, stokens.length);
        assertNotEquals(tokens.length, stokens.length);

        final double real     = Math.E;
        final double expected = new ExpressionBuilder(expression)
                                            .operator(OperatorsComparison.getOperator(">"))
                                            .operator(OperatorsComparison.getOperator("<"))
                                            .function(FunctionsMisc.getFunction("if"))
                                            .build().evaluate();
        final double actual   = new ExpressionBuilder(expression)
                                            .operator(OperatorsComparison.getOperator(">"))
                                            .operator(OperatorsComparison.getOperator("<"))
                                            .function(FunctionsMisc.getFunction("if"))
                                            .build(true).evaluate();
        assertEquals(real, expected, 0d);
        assertEquals(expected, actual, 0d);
        assertEquals(actual, real, 0d);
    }

    @Test
    public void testOptimization19() throws Exception {
        final String expression = "if(1 > a & 3 < 4, pi(), e())";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        userOperators.put(">", OperatorsComparison.getOperator(">"));
        userOperators.put("<", OperatorsComparison.getOperator("<"));
        userFunctions.put("if", FunctionsMisc.getFunction("if"));
        final Set<String> variableNames = new HashSet<>(4);
        variableNames.add("a");

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );
        
        final Token[] stokens = Simplifier.simplify(tokens);

        //Since the expression is constant only the result should be in here
        assertEquals(10, tokens.length);
        assertEquals(8, stokens.length);
        assertNotEquals(tokens.length, stokens.length);

        final double real     = Math.PI;
        final double expected = new ExpressionBuilder(expression)
                                            .operator(OperatorsComparison.getOperator(">"))
                                            .operator(OperatorsComparison.getOperator("<"))
                                            .function(FunctionsMisc.getFunction("if"))
                                            .variable("a")
                                            .build().setVariable("a", Math.random()).evaluate();
        final double actual   = new ExpressionBuilder(expression)
                                            .operator(OperatorsComparison.getOperator(">"))
                                            .operator(OperatorsComparison.getOperator("<"))
                                            .function(FunctionsMisc.getFunction("if"))
                                            .variable("a")
                                            .build(true).setVariable("a", Math.random()).evaluate();
        assertEquals(real, expected, 0d);
        assertEquals(expected, actual, 0d);
        assertEquals(actual, real, 0d);
    }

    @Test
    public void testOptimization20() throws Exception {
        final String expression = "if(1 > 0 & 3 < 4, a * pi(), e())";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        userOperators.put(">", OperatorsComparison.getOperator(">"));
        userOperators.put("<", OperatorsComparison.getOperator("<"));
        userFunctions.put("if", FunctionsMisc.getFunction("if"));
        final Set<String> variableNames = new HashSet<>(4);
        variableNames.add("a");

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );
        
        final Token[] stokens = Simplifier.simplify(tokens);

        //This should be simplified to if(1, a * pi(), e()) 
        assertEquals(12, tokens.length);
        assertEquals(6, stokens.length);
        assertNotEquals(tokens.length, stokens.length);

        final double real     = 0;
        final double expected = new ExpressionBuilder(expression)
                                            .operator(OperatorsComparison.getOperator(">"))
                                            .operator(OperatorsComparison.getOperator("<"))
                                            .function(FunctionsMisc.getFunction("if"))
                                            .variable("a")
                                            .build().setVariable("a", 0).evaluate();
        final double actual   = new ExpressionBuilder(expression)
                                            .operator(OperatorsComparison.getOperator(">"))
                                            .operator(OperatorsComparison.getOperator("<"))
                                            .function(FunctionsMisc.getFunction("if"))
                                            .variable("a")
                                            .build(true).setVariable("a", 0).evaluate();
        assertEquals(real, expected, 0d);
        assertEquals(expected, actual, 0d);
        assertEquals(actual, real, 0d);
    }

    @Test
    public void testDeterministic1() {
        final String expression = "randnd()";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        
        userFunctions.put(RAND_ND.getName(), RAND_ND);
        
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(1, stokens.length);
        assertEquals(tokens.length, stokens.length);

        Expression exp1 = new ExpressionBuilder(expression).function(RAND_ND).build();
        Expression exp2 = new ExpressionBuilder(expression).function(RAND_ND).build(true);
        final double expected = exp1.evaluate();
        final double actual   = exp2.evaluate();
        final double actual2  = exp2.evaluate();
        final double actual3  = exp2.evaluate();
        
        //Since the function is marked as 'non-deterministic' the simplifier 
        //will treat it as a variable, so the value should be different every 
        //time
        assertNotEquals(expected, actual, 0d);
        assertNotEquals(actual, actual2, 0d);
        assertNotEquals(actual, actual3, 0d);
    }

    @Test
    public void testDeterministic2() {
        System.setProperty("exp4j.simplify_enabled", "true");

        final String expression = "randd()";
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        
        userFunctions.put(RAND_D.getName(), RAND_D);
        
        final Set<String> variableNames = new HashSet<>(4);

        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                expression,
                userFunctions,
                userOperators,
                variableNames
        );

        final Token[] stokens = Simplifier.simplify(tokens);

        assertEquals(1, stokens.length);
        assertEquals(tokens.length, stokens.length);

        Expression exp1 = new ExpressionBuilder(expression).function(RAND_D).build();
        Expression exp2 = new ExpressionBuilder(expression).function(RAND_D).build(true);
        final double expected = exp1.evaluate();
        final double actual   = exp2.evaluate();
        final double actual2  = exp2.evaluate();
        final double actual3  = exp2.evaluate();

        //Since the function is marked as 'deterministic' the optimization will
        //replace it with it's value, so every time it's evaluated it should 
        //return the same numeric value (This will not apply for different calls
        //in the same expression)
        assertNotEquals(expected, actual, 0d);
        assertEquals(actual, actual2, 0d);
        assertEquals(actual, actual3, 0d);
    }
    
    @Test
    public void testPerformanceTestExpression() {
        String exp = "log(x) - (2 + 1) * y * (sqrt(x^cos(y)))";
        
        final Map<String, Function> userFunctions = new HashMap<>(4);
        final Map<String, Operator> userOperators = new HashMap<>(4);
        final Set<String> variableNames = new HashSet<>(4);
        variableNames.add("x");
        variableNames.add("y");
        final Token[] tokens = ShuntingYard.convertToRPN(
                false,
                exp,
                userFunctions,
                userOperators,
                variableNames
        );
        
        final Token[] stokens  = ShuntingYard.convertToRPN(
                true,
                exp,
                userFunctions,
                userOperators,
                variableNames
        );
        
        assertTrue(tokens.length > stokens.length);    
        
        Expression exp1 = new ExpressionBuilder(exp).variables("x", "y").build();
        Expression exp2 = new ExpressionBuilder(exp).variables("x", "y").build(true);
        
        Map<String, Double> vals = new HashMap<>(2);
        for (int i = 0; i < 10; i++) {
            vals.put("x", Math.random());
            vals.put("y", Math.random());
            assertEquals(exp1.setVariables(vals).evaluate(), exp2.setVariables(vals).evaluate(), 0d);
        }
    }

    private static final Operator FACT = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
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

    private static final Function PI = new Function("pi", 0) {
        @Override
        public double apply(double... args) {
            return Math.PI;
        }
    };

    private static final Function RAND_ND = new Function("randnd", 0, false) {
        @Override
        public double apply(double... args) {
            return Math.random();
        }
    };

    private static final Function RAND_D = new Function("randd", 0) {        
        @Override
        public double apply(double... args) {
            return Math.random();
        }
    };
}
