/*
 * Copyright 2016 Federico Vera
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

import java.util.EmptyStackException;
import net.objecthunter.exp4j.tokenizer.Token;

/**
 * Simple Token stack using a Token array as data storage
 *
 * @author Federico Vera (dktcoding [at] gmail)
 */
final class TokenStack {

    private Token[] data;

    private int idx;

    TokenStack() {
        this(32);
    }

    TokenStack(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Stack's capacity must be positive");
        }

        data = new Token[initialCapacity];
        idx = -1;
    }

    void push(Token value) {
        if (idx + 1 == data.length) {
            Token[] temp = new Token[(int) (data.length * 2) + 1];
            System.arraycopy(data, 0, temp, 0, data.length);
            data = temp;
        }

        data[++idx] = value;
    }

    Token peek() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx];
    }

    Token pop() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx--];
    }

    boolean isEmpty() {
        return idx == -1;
    }

    int size() {
        return idx + 1;
    }
    
    Token[] toArray() {
        Token[] ret = new Token[idx + 1];
        System.arraycopy(data, 0, ret, 0, ret.length);
        return ret;
    }
}