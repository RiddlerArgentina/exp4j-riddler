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

import net.objecthunter.exp4j.tokenizer.*;
import org.junit.jupiter.api.Assertions;

public abstract class TestUtil {

    public static void assertVariableToken(Token token, String name) {
        Assertions.assertEquals(TokenType.VARIABLE, token.getType());
        Assertions.assertEquals(name, ((VariableToken) token).getName());
    }

    public static void assertOpenParenthesesToken(Token token) {
        Assertions.assertEquals(TokenType.PARENTHESES_OPEN, token.getType());
    }

    public static void assertCloseParenthesesToken(Token token) {
        Assertions.assertEquals(TokenType.PARENTHESES_CLOSE, token.getType());
    }

    public static void assertFunctionToken(Token token, String name, int i) {
        Assertions.assertEquals(token.getType(), TokenType.FUNCTION);
        FunctionToken f = (FunctionToken) token;
        Assertions.assertEquals(i, f.getFunction().getNumArguments());
        Assertions.assertEquals(name, f.getFunction().getName());
    }

    public static void assertOperatorToken(Token tok, String symbol, int numArgs, int precedence) {
        Assertions.assertEquals(tok.getType(), TokenType.OPERATOR);
        Assertions.assertEquals(numArgs, ((OperatorToken) tok).getOperator().getNumOperands());
        Assertions.assertEquals(symbol, ((OperatorToken) tok).getOperator().getSymbol());
        Assertions.assertEquals(precedence, ((OperatorToken) tok).getOperator().getPrecedence());
    }

    public static void assertNumberToken(Token tok, double v) {
        Assertions.assertEquals(tok.getType(), TokenType.NUMBER);
        Assertions.assertEquals(v, ((NumberToken) tok).getValue(), 0d);
    }

    public static void assertFunctionSeparatorToken(Token t) {
        Assertions.assertEquals(t.getType(), TokenType.SEPARATOR);
    }
}
