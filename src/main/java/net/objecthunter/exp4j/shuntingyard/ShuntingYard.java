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
package net.objecthunter.exp4j.shuntingyard;

import java.util.*;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.Tokenizer;

/**
 * Shunting yard implementation to convert infix to reverse polish notation
 */
public class ShuntingYard {

    /**
     * Convert a Set of tokens from infix to reverse polish notation
     * @param expression the expression to convert
     * @param userFunctions the custom functions used
     * @param userOperators the custom operators used
     * @param variableNames the variable names used in the expression
     * @return a {@link net.objecthunter.exp4j.tokenizer.Token} array containing the result
     */
    public static Token[] convertToRPN(final String expression, final Map<String, Function> userFunctions,
            final Map<String, Operator> userOperators, final Set<String> variableNames){
        final Stack<Token> stack = new Stack<>();
        final List<Token> output = new ArrayList<>();

        final Tokenizer tokenizer = new Tokenizer(expression, userFunctions, userOperators, variableNames);
        while (tokenizer.hasNext()) {
            Token token = tokenizer.nextToken();
            switch (token.getType()) {
            case Token.TOKEN_NUMBER:
            case Token.TOKEN_VARIABLE:
                output.add(token);
                break;
            case Token.TOKEN_FUNCTION:
                stack.add(token);
                break;
            case Token.TOKEN_SEPARATOR:
                while (!stack.empty() && stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    output.add(stack.pop());
                }
                if (stack.empty() || stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    throw new IllegalArgumentException(
                        "Misplaced function separator ',' or mismatched parentheses"
                    );
                }
                break;
            case Token.TOKEN_OPERATOR:
                while (!stack.empty() && stack.peek().getType() == Token.TOKEN_OPERATOR) {
                    final Operator o1 = ((OperatorToken) token).getOperator();
                    final Operator o2 = ((OperatorToken) stack.peek()).getOperator();
                    if (o1.getNumOperands() == 1 && o2.getNumOperands() == 2) {
                        break;
                    } else if ((o1.isLeftAssociative() && o1.getPrecedence() <= o2.getPrecedence())
                            || (o1.getPrecedence() < o2.getPrecedence())) {
                        output.add(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(token);
                break;
            case Token.TOKEN_PARENTHESES_OPEN:
                stack.push(token);
                break;
            case Token.TOKEN_PARENTHESES_CLOSE:
                while (stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    output.add(stack.pop());
                }
                stack.pop();
                if (!stack.isEmpty() && stack.peek().getType() == Token.TOKEN_FUNCTION) {
                    output.add(stack.pop());
                }
                break;
            default:
                throw new IllegalArgumentException(
                    "Unknown Token type encountered. This should not happen"
                );
            }
        }
        while (!stack.empty()) {
            Token t = stack.pop();
            if (t.getType() == Token.TOKEN_PARENTHESES_CLOSE || 
                t.getType() == Token.TOKEN_PARENTHESES_OPEN) {
                throw new IllegalArgumentException(
                    "Mismatched parentheses detected. Please check the expression"
                );
            } else {
                output.add(t);
            }
        }
        return (Token[]) output.toArray(new Token[output.size()]);
    }
}
