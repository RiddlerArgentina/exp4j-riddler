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

import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.utils.Text;

import java.io.Serial;

/**
 * Represents an {@link Operator}
 */
public final class OperatorToken extends Token {
    @Serial
    private static final long serialVersionUID = -3066884319239647929L;

    private final Operator operator;

    /**
     * Create a new instance
     * @param op the operator
     */
    public OperatorToken(Operator op) {
        super(TokenType.OPERATOR);
        if (op == null) {
            throw new IllegalArgumentException(Text.l10n(
                    "Operator is unknown for token."
            ));
        }
        this.operator = op;
    }

    /**
     * Get the operator for that token
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return operator.getSymbol();
    }
}
