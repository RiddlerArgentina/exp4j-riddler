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
package net.objecthunter.exp4j.tokenizer;

/**
 * Types of tokens available
 *
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public enum TokenType {
    /**
     * Associated with {@link NumberToken}
     */
    NUMBER,

    /**
     * Associated with {@link OperatorToken}
     */
    OPERATOR,

    /**
     * Associated with {@link FunctionToken}
     */
    FUNCTION,

    /**
     * Associated with {@link OpenParenthesesToken}
     */
    PARENTHESES_OPEN,

    /**
     * Associated with {@link CloseParenthesesToken}
     */
    PARENTHESES_CLOSE,

    /**
     * Associated with {@link VariableToken}
     */
    VARIABLE,

    /**
     * Represents a function parameter delimiter
     */
    SEPARATOR
}
