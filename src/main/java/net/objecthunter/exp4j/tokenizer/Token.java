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
import java.io.Serializable;

/**
 * Abstract class for tokens used by exp4j to tokenize expressions
 */
public abstract class Token implements Serializable {
    @Serial
    private static final long serialVersionUID = 2938544527369187154L;

    private final TokenType type;

    Token(TokenType type) {
        this.type = type;
    }

    /**
     * Retrieves the {@link TokenType} of this token
     * @return {@link TokenType}
     */
    public final TokenType getType() {
        return type;
    }

}
