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
package net.objecthunter.exp4j;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class SerializationTest {

    @Test
    public void serialize1() throws Exception {
        Expression exp = new ExpressionBuilder("2").build();
        byte[] bytes = toByteArray(exp);
        Expression exp1 = fromByteArray(bytes);
        Assertions.assertNotNull(exp1);
        Assertions.assertNotSame(exp, exp1);
        Assertions.assertEquals(exp.evaluate(), exp1.evaluate(), 1e-12);
    }

    @Test
    public void serialize2() throws Exception {
        Expression exp = new ExpressionBuilder("2 + 2").build();
        byte[] bytes = toByteArray(exp);
        Expression exp1 = fromByteArray(bytes);
        Assertions.assertNotNull(exp1);
        Assertions.assertNotSame(exp, exp1);
        Assertions.assertEquals(exp.evaluate(), exp1.evaluate(), 1e-12);
        Assertions.assertEquals(exp.evaluate(), 4, 1e-12);
    }

    @Test
    public void serialize3() throws Exception {
        Expression exp = new ExpressionBuilder("2 + 2").build();
        byte[] bytes = toByteArray(exp);
        Expression exp1 = fromByteArray(bytes);
        Assertions.assertNotNull(exp1);
        Assertions.assertNotSame(exp, exp1);
        Assertions.assertEquals(exp.evaluate(), exp1.evaluate(), 1e-12);
        Assertions.assertEquals(exp.evaluate(), 4, 1e-12);
    }

    @Test
    public void serialize4() throws Exception {
        Expression exp = new ExpressionBuilder("sin(pi())").build();
        byte[] bytes = toByteArray(exp);
        Expression exp1 = fromByteArray(bytes);
        Assertions.assertNotNull(exp1);
        Assertions.assertNotSame(exp, exp1);
        Assertions.assertEquals(exp.evaluate(), exp1.evaluate(), 1e-12);
    }

    @Test
    public void serialize5() throws Exception {
        Expression exp = new ExpressionBuilder("sin(pi())").build(true);
        byte[] bytes = toByteArray(exp);
        Expression exp1 = fromByteArray(bytes);
        Assertions.assertNotNull(exp1);
        Assertions.assertNotSame(exp, exp1);
        Assertions.assertEquals(exp.evaluate(), exp1.evaluate(), 1e-12);
    }

    @Test
    public void serialize6() throws Exception {
        Expression exp = new ExpressionBuilder("2 : 2").operator(new OpCustom()).build();
        byte[] bytes = toByteArray(exp);
        Expression exp1 = fromByteArray(bytes);
        Assertions.assertNotNull(exp1);
        Assertions.assertNotSame(exp, exp1);
        Assertions.assertEquals(exp.evaluate(), exp1.evaluate(), 1e-12);
        Assertions.assertEquals(exp.evaluate(), 4, 1e-12);
    }

    @Test
    public void serialize7() throws Exception {
        Expression exp = new ExpressionBuilder("myFunc(2,2)").function(new FuncCustom()).build();
        byte[] bytes = toByteArray(exp);
        Expression exp1 = fromByteArray(bytes);
        Assertions.assertNotNull(exp1);
        Assertions.assertNotSame(exp, exp1);
        Assertions.assertEquals(exp.evaluate(), exp1.evaluate(), 1e-12);
        Assertions.assertEquals(exp.evaluate(), 4, 1e-12);
    }

    @Test
    public void serialize8() throws Exception {
        Expression exp = new ExpressionBuilder("myFunc(2,2)").function(new FuncCustom()).build(true);
        byte[] bytes = toByteArray(exp);
        Expression exp1 = fromByteArray(bytes);
        Assertions.assertNotNull(exp1);
        Assertions.assertNotSame(exp, exp1);
        Assertions.assertEquals(exp.evaluate(), exp1.evaluate(), 1e-12);
        Assertions.assertEquals(exp.evaluate(), 4, 1e-12);
    }

    public byte[] toByteArray(Expression exp) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(exp);
            return bos.toByteArray();
        }
    }

    public Expression fromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (Expression) in.readObject();
        }
    }

    private static class OpCustom extends Operator {
        @Serial
        private static final long serialVersionUID = -1761106184622718383L;

        public OpCustom() {
            super(":", 2, false, Operator.PRECEDENCE_ADDITION);
        }

        @Override
        public double apply(double... args) {
            return args[0] + args[1];
        }

    }

    private static class FuncCustom extends Function {
        @Serial
        private static final long serialVersionUID = -564878091215141197L;

        public FuncCustom() {
            super("myFunc", 2);
        }

        @Override
        public double apply(double... args) {
            return args[0] + args[1];
        }

    }
}
