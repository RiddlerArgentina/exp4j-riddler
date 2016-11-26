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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.shuntingyard.ShuntingYard;
import net.objecthunter.exp4j.tokenizer.Token;

/**
 * Factory class for {@link Expression} instances. This class is the main API entrypoint.
 * Users should create new {@link Expression} instances using this factory class.
 */
public class ExpressionBuilder {

    private final String expression;

    private final Map<String, Function> userFunctions;

    private final Map<String, Operator> userOperators;

    private final Set<String> variableNames;

    /**
     * Create a new ExpressionBuilder instance and initialize it with a given expression 
     * string.
     * @param expression the expression to be parsed
     */
    public ExpressionBuilder(String expression) {
        if (expression == null || expression.trim().length() == 0) {
            throw new IllegalArgumentException("Expression can not be empty");
        }
        
        this.expression = expression;
        this.userOperators = new HashMap<>(4);
        this.userFunctions = new HashMap<>(4);
        this.variableNames = new HashSet<>(4);
    }

    /**
     * Add a {@link Function} implementation available for use in the expression.
     * @param function the custom {@link Function} implementation that should be available for 
     * use in the expression.
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder function(Function function) {
        this.userFunctions.put(function.getName(), function);
        return this;
    }

    /**
     * Add multiple {@link Function} implementations 
     * available for use in the expression.
     * @param functions the custom {@link Function} implementations
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder functions(Function... functions) {
        for (Function f : functions) {
            this.userFunctions.put(f.getName(), f);
        }
        return this;
    }

    /**
     * Add multiple {@link net.objecthunter.exp4j.function.Function} implementations 
     * available for use in the expression
     * @param functions A {@link java.util.List} of custom {@link Function} implementations
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder functions(List<Function> functions) {
        for (Function f : functions) {
            this.userFunctions.put(f.getName(), f);
        }
        return this;
    }

    public ExpressionBuilder variables(Set<String> variableNames) {
        this.variableNames.addAll(variableNames);
        return this;
    }

    public ExpressionBuilder variables(String ... variableNames) {
        Collections.addAll(this.variableNames, variableNames);
        return this;
    }

    public ExpressionBuilder variable(String variableName) {
        this.variableNames.add(variableName);
        return this;
    }

    /**
     * Add an {@link Operator} which should be available for use in the expression
     * @param operator the custom {@link Operator} to add
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder operator(Operator operator) {
        this.checkOperatorSymbol(operator);
        this.userOperators.put(operator.getSymbol(), operator);
        return this;
    }

    private void checkOperatorSymbol(Operator op) {
        String name = op.getSymbol();
        for (char ch : name.toCharArray()) {
            if (!Operator.isAllowedOperatorChar(ch)) {
                throw new IllegalArgumentException(String.format(
                    "The operator symbol '%s' is invalid", name
                ));
            }
        }
    }

    /**
     * Add multiple {@link Operator} implementations
     * which should be available for use in the expression
     * @param operators the set of custom {@link Operator} implementations to add
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder operator(Operator... operators) {
        for (Operator o : operators) {
            this.operator(o);
        }
        return this;
    }

    /**
     * Add multiple {@link Operator} implementations which should be available for use 
     * in the expression
     * @param operators the {@link List} of custom {@link Operator} implementations to add
     * @return the ExpressionBuilder instance
     */
    public ExpressionBuilder operator(List<Operator> operators) {
        for (Operator o : operators) {
            this.operator(o);
        }
        return this;
    }

    /**
     * Build the {@link Expression} instance using the custom operators and functions set.
     * @return an {@link Expression} instance which can be used to evaluate the result of the
     * expression
     */
    public Expression build() {
        return build(false);
    }

    /**
     * Build the {@link Expression} instance using the custom operators and functions set.
     * @param simplify {@code true} if you want to attempt to simplify constants {@code false}
     * otherwise
     * @return an {@link Expression} instance which can be used to evaluate the result of the
     * expression
     */
    public Expression build(boolean simplify) {
        if (expression.length() == 0) {
            throw new IllegalArgumentException("The expression can not be empty");
        }
        
        /* Check if there are duplicate vars/functions */
        for (String var : variableNames) {
            if (Functions.getBuiltinFunction(var) != null || userFunctions.containsKey(var)) {
                throw new IllegalArgumentException(String.format(
                    "A variable can not have the same name as a function [%s]", var
                ));
            }
        }
        
        Token[] tokens = ShuntingYard.convertToRPN(
                simplify,
                this.expression, 
                this.userFunctions,
                this.userOperators,
                this.variableNames
        ); 
        
        return new Expression(tokens, this.userFunctions.keySet());
    }
    
    @Override
    public String toString() {
        return expression;
    }

}
