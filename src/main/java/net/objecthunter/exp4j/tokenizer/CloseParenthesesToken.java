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
package net.objecthunter.exp4j.tokenizer;

import java.io.Serial;

/**
 * Represents closed parentheses
 */
final class CloseParenthesesToken extends Token {
    @Serial
    private static final long serialVersionUID = -3053467429836059238L;

    /**
     * Create a new instance
     */
    CloseParenthesesToken() {
        super(TokenType.PARENTHESES_CLOSE);
    }
}
