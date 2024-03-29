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
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class SanityFunctionCallingTest {
    @Test
    public void testFunctionsBuiltin() {
        Function[] functions = Functions.getFunctions();
        for (Function function : functions) {
            Assertions.assertNotNull(function);
            Assertions.assertEquals(function, Functions.getBuiltinFunction(function.getName()));
        }
    }

    @Test
    public void testFunctionsBoolean() {
        Function[] functions = FunctionsBoolean.getFunctions();
        for (Function function : functions) {
            Assertions.assertNotNull(function);
            Assertions.assertEquals(function, FunctionsBoolean.getFunction(function.getName()));
        }
    }

    @Test
    public void testFunctionsMisc() {
        Function[] functions = FunctionsMisc.getFunctions();
        for (Function function : functions) {
            Assertions.assertNotNull(function);
            Assertions.assertEquals(function, FunctionsMisc.getFunction(function.getName()));
        }
    }

    @Test
    public void testOperatorsBuiltin() {
        Operator[] operators = Operators.getOperators();
        for (Operator operator : operators) {
            if (operator.getSymbol().equals("!")) {
                Assertions.assertNotNull(operator);
                Assertions.assertEquals(operator, Operators.getBuiltinOperator(operator.getSymbol().charAt(0),
                        operator.getNumOperands() + 1));
            } else {
                Assertions.assertNotNull(operator);
                Assertions.assertEquals(operator, Operators.getBuiltinOperator(operator.getSymbol().charAt(0),
                        operator.getNumOperands()));
            }
        }
    }

    @Test
    public void testOperatorsComparison() {
        Operator[] operators = OperatorsComparison.getOperators();
        for (Operator operator : operators) {
            Assertions.assertNotNull(operator);
            Assertions.assertEquals(operator, OperatorsComparison.getOperator(operator.getSymbol()));
        }
    }
}
