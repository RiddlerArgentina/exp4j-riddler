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
package net.objecthunter.exp4j.shuntingyard;

import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

/**
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class TokenStackTest {
    @Test
    public void testConstructor() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new TokenStack(-1));
    }

    @Test
    public void testPushNoSize() {
        TokenStack stack = new TokenStack();

        stack.push(new NumberToken(1));
        stack.push(new NumberToken(2));
        stack.push(new NumberToken(3));

        Assertions.assertEquals(3, stack.size());
    }

    @Test
    public void testPushLessSize() {
        TokenStack stack = new TokenStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(new NumberToken(i));
        }

        Assertions.assertEquals(5, stack.size());
    }

    @Test
    public void testPeek() {
        TokenStack stack = new TokenStack(5);
        NumberToken last = null;
        for (int i = 0; i < 5; i++) {
            last = new NumberToken(i);
            stack.push(last);
        }

        Assertions.assertEquals(last, stack.peek());
        Assertions.assertEquals(last, stack.peek());
        Assertions.assertEquals(last, stack.peek());
    }

    @Test
    public void testPeek2() {
        TokenStack stack = new TokenStack(5);
        Token old = new NumberToken(-1);
        stack.push(old);
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(old, stack.peek());
            old = new NumberToken(i);
            stack.push(old);
            Assertions.assertEquals(old, stack.peek());
        }
    }

    @Test
    public void testPeekNoData() {
        TokenStack stack = new TokenStack(5);
        Assertions.assertThrowsExactly(EmptyStackException.class, stack::peek);
    }

    @Test
    public void testPop() {
        TokenStack stack = new TokenStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(new NumberToken(i));
        }

        Assertions.assertEquals(5, stack.size());
        Assertions.assertFalse(stack.isEmpty());

        while (!stack.isEmpty()) {
            stack.pop();
        }

        Assertions.assertEquals(0, stack.size());
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void testPop2() {
        TokenStack stack = new TokenStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(new NumberToken(i));
        }
        Assertions.assertThrowsExactly(EmptyStackException.class, () -> {
            while (true) {
                stack.pop();
            }
        });
    }

    @Test
    public void testPop3() {
        TokenStack stack = new TokenStack(5);

        for (int i = 0; i < 5; i++) {
            NumberToken foo = new NumberToken(i);
            stack.push(foo);
            Assertions.assertEquals(1, stack.size());
            Assertions.assertEquals(foo, stack.pop());
        }

        Assertions.assertEquals(0, stack.size());
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void testPopNoData() {
        TokenStack stack = new TokenStack(5);
        Assertions.assertThrowsExactly(EmptyStackException.class, stack::pop);
    }

    @Test
    public void testIsEmpty() {
        TokenStack stack = new TokenStack(5);
        Assertions.assertTrue(stack.isEmpty());
        stack.push(new FunctionToken(null));
        Assertions.assertFalse(stack.isEmpty());
        stack.push(new FunctionToken(null));
        Assertions.assertFalse(stack.isEmpty());
        stack.push(new FunctionToken(null));
        Assertions.assertFalse(stack.isEmpty());
        stack.pop();
        stack.pop();
        stack.pop();
        Assertions.assertTrue(stack.isEmpty());
        stack.push(new FunctionToken(null));
        Assertions.assertFalse(stack.isEmpty());
        stack.peek();
        Assertions.assertFalse(stack.isEmpty());
        stack.pop();
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void testSize() {
        TokenStack stack = new TokenStack(5);
        Assertions.assertEquals(0, stack.size());
        stack.push(new FunctionToken(null));
        Assertions.assertEquals(1, stack.size());
        stack.peek();
        Assertions.assertEquals(1, stack.size());
        stack.pop();
        Assertions.assertEquals(0, stack.size());
    }

}
