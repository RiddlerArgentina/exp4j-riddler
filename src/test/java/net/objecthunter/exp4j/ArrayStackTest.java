/*
 * Copyright 2015-2023 Federico Vera
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

/**
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class ArrayStackTest {

    @Test
    public void testConstructor() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> new ArrayStack(-1));
    }

    @Test
    public void testPushNoSize() {
        ArrayStack stack = new ArrayStack();

        stack.push(0);
        stack.push(1);
        stack.push(3);

        Assertions.assertEquals(3, stack.size());
    }

    @Test
    public void testPushLessSize() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }

        Assertions.assertEquals(5, stack.size());
    }

    @Test
    public void testPeek() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }

        Assertions.assertEquals(4d, stack.peek(), 0d);
        Assertions.assertEquals(4d, stack.peek(), 0d);
        Assertions.assertEquals(4d, stack.peek(), 0d);
    }

    @Test
    public void testPeek2() {
        ArrayStack stack = new ArrayStack(5);
        stack.push(-1);
        double old = -1;
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(old, stack.peek(), 0d);
            stack.push(i);
            old = i;
            Assertions.assertEquals(old, stack.peek(), 0d);
        }
    }

    @Test
    public void testPeekNoData() {
        ArrayStack stack = new ArrayStack(5);
        Assertions.assertThrowsExactly(EmptyStackException.class, stack::peek);
    }

    @Test
    public void testPop() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
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
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }
        Assertions.assertThrowsExactly(EmptyStackException.class, () -> {
            while (true) {
                stack.pop();
            }
        });
    }

    @Test
    public void testPop3() {
        ArrayStack stack = new ArrayStack(5);

        for (int i = 0; i < 5; i++) {
            stack.push(i);
            Assertions.assertEquals(1, stack.size());
            Assertions.assertEquals(i, stack.pop(), 0d);
        }

        Assertions.assertEquals(0, stack.size());
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void testPopNoData() {
        ArrayStack stack = new ArrayStack(5);
        Assertions.assertThrowsExactly(EmptyStackException.class, stack::pop);
    }

    @Test
    public void testIsEmpty() {
        ArrayStack stack = new ArrayStack(5);
        Assertions.assertTrue(stack.isEmpty());
        stack.push(4);
        Assertions.assertFalse(stack.isEmpty());
        stack.push(4);
        Assertions.assertFalse(stack.isEmpty());
        stack.push(4);
        Assertions.assertFalse(stack.isEmpty());
        stack.pop();
        stack.pop();
        stack.pop();
        Assertions.assertTrue(stack.isEmpty());
        stack.push(4);
        Assertions.assertFalse(stack.isEmpty());
        stack.peek();
        Assertions.assertFalse(stack.isEmpty());
        stack.pop();
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void testSize() {
        ArrayStack stack = new ArrayStack(5);
        Assertions.assertEquals(0, stack.size());
        stack.push(4);
        Assertions.assertEquals(1, stack.size());
        stack.peek();
        Assertions.assertEquals(1, stack.size());
        stack.pop();
        Assertions.assertEquals(0, stack.size());
    }

}