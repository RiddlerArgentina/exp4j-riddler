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
import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.Token;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Federico Vera {@literal dktcoding [at] gmail}
 */
public class TokenStackTest {
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        TokenStack stack = new TokenStack(-1);
    }

    @Test
    public void testPushNoSize() {
        TokenStack stack = new TokenStack();

        stack.push(new NumberToken(1));
        stack.push(new NumberToken(2));
        stack.push(new NumberToken(3));

        assertEquals(3, stack.size());
    }

    @Test
    public void testPushLessSize() {
        TokenStack stack = new TokenStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(new NumberToken(i));
        }

        assertEquals(5, stack.size());
    }

    @Test
    public void testPeek() {
        TokenStack stack = new TokenStack(5);
        NumberToken last = null;
        for (int i = 0; i < 5; i++) {
            last = new NumberToken(i);
            stack.push(last);
        }

        assertEquals(last, stack.peek());
        assertEquals(last, stack.peek());
        assertEquals(last, stack.peek());
    }

    @Test
    public void testPeek2() {
        TokenStack stack = new TokenStack(5);
        Token old = new NumberToken(-1);
        stack.push(old);
        for (int i = 0; i < 5; i++) {
            assertEquals(old, stack.peek());
            old = new NumberToken(i);
            stack.push(old);
            assertEquals(old, stack.peek());
        }
    }

    @Test(expected = EmptyStackException.class)
    public void testPeekNoData() {
        TokenStack stack = new TokenStack(5);
        stack.peek();
    }

    @Test
    public void testPop() {
        TokenStack stack = new TokenStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(new NumberToken(i));
        }

        while (!stack.isEmpty()) {
            stack.pop();
        }
    }

    @Test(expected = EmptyStackException.class)
    public void testPop2() {
        TokenStack stack = new TokenStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(new NumberToken(i));
        }

        while (true) {
            stack.pop();
        }
    }

    @Test
    public void testPop3() {
        TokenStack stack = new TokenStack(5);

        for (int i = 0; i < 5; i++) {
            NumberToken foo = new NumberToken(i);
            stack.push(foo);
            assertEquals(1, stack.size());
            assertEquals(foo, stack.pop());
        }

        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }

    @Test(expected = EmptyStackException.class)
    public void testPopNoData() {
        TokenStack stack = new TokenStack(5);
        stack.pop();
    }

    @Test
    public void testIsEmpty() {
        TokenStack stack = new TokenStack(5);
        assertTrue(stack.isEmpty());
        stack.push(new FunctionToken(null));
        assertFalse(stack.isEmpty());
        stack.push(new FunctionToken(null));
        assertFalse(stack.isEmpty());
        stack.push(new FunctionToken(null));
        assertFalse(stack.isEmpty());
        stack.pop();
        stack.pop();
        stack.pop();
        assertTrue(stack.isEmpty());
        stack.push(new FunctionToken(null));
        assertFalse(stack.isEmpty());
        stack.peek();
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testSize() {
        TokenStack stack = new TokenStack(5);
        assertEquals(0, stack.size());
        stack.push(new FunctionToken(null));
        assertEquals(1, stack.size());
        stack.peek();
        assertEquals(1, stack.size());
        stack.pop();
        assertEquals(0, stack.size());
    }

}
