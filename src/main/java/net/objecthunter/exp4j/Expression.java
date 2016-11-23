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
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.VariableToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Expression {

    private final Token[] tokens;

    private final Set<String> userFunctionNames;
    
    private final HashMap<String, VariableToken> variables = new HashMap<>(4);
    /**
     * Creates a new expression that is a copy of the existing one.
     * 
     * @param existing the expression to copy
     */
    public Expression(final Expression existing) {
    	this.tokens = Arrays.copyOf(existing.tokens, existing.tokens.length);
    	this.userFunctionNames = new HashSet<>(existing.userFunctionNames);
        populateVariablesMap();
    }

    Expression(final Token[] tokens) {
        this.tokens = tokens;
        this.userFunctionNames = Collections.<String>emptySet();
        populateVariablesMap();
    }

    Expression(final Token[] tokens, Set<String> userFunctionNames) {
        this.tokens = tokens;
        this.userFunctionNames = userFunctionNames;
        populateVariablesMap();
    }

    private void populateVariablesMap() {
        for (final Token t: tokens) {
            if (t.getType() == Token.TOKEN_VARIABLE) {
                variables.put(((VariableToken)t).getName(), (VariableToken)t);
            }
        }
    }
    
    public Expression setVariable(final String name, final double value) {
        this.checkVariableName(name);
        variables.get(name).setValue(value);
        return this;
    }

    private void checkVariableName(String name) {
        if (this.userFunctionNames.contains(name) || Functions.getBuiltinFunction(name) != null) {
            throw new IllegalArgumentException(
                    "The variable name '" + name + "' is invalid. Since "
                  + "there exists a function with the same name"
            );
        }
    }

    public Expression setVariables(Map<String, Double> variables) {
        for (Map.Entry<String, Double> v : variables.entrySet()) {
            this.setVariable(v.getKey(), v.getValue());
        }
        return this;
    }

    /**
     * Retrieves a {@link Set} containing all the variable names
     * 
     * @return variable names
     */
    public Set<String> getVariableNames() {
        return variables.keySet();
    }

    /**
     * Tells if a variable exists in the expression
     * 
     * @param name variable name
     * @return {@code true} if the variable exists and {@code false} otherwise
     */
    public boolean containsVariable(String name) {
        return variables.containsKey(name);
    }

    public ValidationResult validate(boolean checkVariablesSet) {
        final List<String> errors = new ArrayList<>(0);
        if (checkVariablesSet) {
            /* check that all vars have a value set */
            for (VariableToken vt : variables.values()) {
                if (!vt.isValueSet()) {
                    errors.add("The setVariable '" + vt.getName() + "' has not been set");
                }
            }
        }

        /* Check if the number of operands, functions and operators match.
           The idea is to increment a counter for operands and decrease it for operators.
           When a function occurs the number of available arguments has to be greater
           than or equals to the function's expected number of arguments.
           The count has to be larger than 1 at all times and exactly 1 after all tokens
           have been processed */
        int count = 0;
        for (Token tok : this.tokens) {
            switch (tok.getType()) {
                case Token.TOKEN_NUMBER:
                case Token.TOKEN_VARIABLE:
                    count++;
                    break;
                case Token.TOKEN_FUNCTION:
                    final Function func = ((FunctionToken) tok).getFunction();
                    final int argsNum = func.getNumArguments(); 
                    if (argsNum > count) {
                        errors.add("Not enough arguments for '" + func.getName() + "'");
                    }
                    if (argsNum > 1) {
                        count -= argsNum - 1;
                    } else if (argsNum == 0) {
                        // see https://github.com/fasseg/exp4j/issues/59
                        count++;
                    }
                    break;
                case Token.TOKEN_OPERATOR:
                    Operator op = ((OperatorToken) tok).getOperator();
                    if (op.getNumOperands() == 2) {
                        count--;
                    }
                    break;
            }
            if (count < 1) {
                errors.add("Too many operators");
                return new ValidationResult(false, errors);
            }
        }
        if (count > 1) {
            errors.add("Too many operands");
        }
        return errors.isEmpty() ? ValidationResult.SUCCESS : new ValidationResult(false, errors);

    }

    public ValidationResult validate() {
        return validate(true);
    }

    public Future<Double> evaluateAsync(ExecutorService executor) {
        return executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return evaluate();
            }
        });
    }

    public double evaluate() {
        final ArrayStack output = new ArrayStack();
        for (Token t : tokens) {
            if (t.getType() == Token.TOKEN_NUMBER) {
                output.push(((NumberToken) t).getValue());
            } else if (t.getType() == Token.TOKEN_VARIABLE) {
                final VariableToken vt = (VariableToken)t;
                if (!vt.isValueSet()) {
                    throw new IllegalArgumentException(
                            "No value has been set for the setVariable '" + vt.getName() + "'."
                    );
                }
                output.push(vt.getValue());
            } else if (t.getType() == Token.TOKEN_OPERATOR) {
                OperatorToken op = (OperatorToken) t;
                if (output.size() < op.getOperator().getNumOperands()) {
                    throw new IllegalArgumentException(
                            "Invalid number of operands available for '" 
                           + op.getOperator().getSymbol() + "' operator"
                    );
                }
                if (op.getOperator().getNumOperands() == 2) {
                    /* pop the operands and push the result of the operation */
                    double rightArg = output.pop();
                    double leftArg = output.pop();
                    output.push(op.getOperator().apply(leftArg, rightArg));
                } else if (op.getOperator().getNumOperands() == 1) {
                    /* pop the operand and push the result of the operation */
                    double arg = output.pop();
                    output.push(op.getOperator().apply(arg));
                }
            } else if (t.getType() == Token.TOKEN_FUNCTION) {
                FunctionToken func = (FunctionToken) t;
                final int numArguments = func.getFunction().getNumArguments();
                if (output.size() < numArguments) {
                    throw new IllegalArgumentException(
                            "Invalid number of arguments available for '" 
                           + func.getFunction().getName() + "' function"
                    );
                }
                /* collect the arguments from the stack */
                double[] args = new double[numArguments];
                for (int j = numArguments - 1; j >= 0; j--) {
                    args[j] = output.pop();
                }
                output.push(func.getFunction().apply(args));
            }
        }
        
        if (output.size() > 1) {
            throw new IllegalArgumentException(
                    "Invalid number of items on the output queue. "
                  + "Might be caused by an invalid number of arguments for a function."
            );
        }
        
        return output.pop();
    }
}
