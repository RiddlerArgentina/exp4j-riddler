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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Formatter;
import java.util.Random;

public final class PerformanceTest {
    private static final long BENCH_TIME = 2L;
    private static final String EXPRESSION = "log(x + y) - (2 + 1 - 1) * y * (sqrt(pow(x,cos(y))))";

    @Test
    public void testBenches() {
        StringBuffer sb = new StringBuffer();
        Formatter fmt = new Formatter(sb);
        fmt.format("+------------------------+---------------------------+--------------------------+%n");
        fmt.format("| %-22s | %-25s | %-24s |%n", "Implementation", "Calculations per Second", "Percentage of Math");
        fmt.format("+------------------------+---------------------------+--------------------------+%n");
        System.out.print(sb);
        sb.setLength(0);

        int math = benchJavaMath();
        math = benchJavaMath();
        double mathRate = (double) math / (double) BENCH_TIME;
        fmt.format("| %-22s | %25.2f | %22.4f %% |%n", "Java Math", mathRate, 100f);
        System.out.print(sb);
        sb.setLength(0);

        int db = benchDouble();
        db = benchDouble();
        double dbRate = (double) db / (double) BENCH_TIME;
        fmt.format("| %-22s | %25.2f | %22.4f %% |%n", "exp4j", dbRate, dbRate * 100 / mathRate);
        System.out.print(sb);
        sb.setLength(0);

        int sd = benchDoubleSimplify();
        sd = benchDoubleSimplify();
        double sdRate = (double) sd / (double) BENCH_TIME;
        fmt.format("| %-22s | %25.2f | %22.4f %% |%n", "exp4j simplified", sdRate, sdRate * 100 / mathRate);
        System.out.print(sb);
        sb.setLength(0);

//        int js = benchJavaScript();
//        js = benchJavaScript();
//        double jsRate = (double) js / (double) BENCH_TIME;
//        fmt.format("| %-22s | %25.2f | %22.4f %% |%n", "JSR-223 (Java Script)", jsRate, jsRate * 100 / mathRate);
        fmt.format("+------------------------+---------------------------+--------------------------+%n");
        Assertions.assertNotNull(sb.toString()); //Silence warning
        System.out.print(sb);
    }

    private int benchDoubleSimplify() {
        final Expression expression = new ExpressionBuilder(EXPRESSION)
                .variables("x", "y")
                .build(true);
        double val = 0;
        Random rnd = new Random();
        long time = System.nanoTime() + (1000000000 * BENCH_TIME);
        int count = 0;
        while (time > System.nanoTime()) {
            expression.setVariable("x", rnd.nextDouble());
            expression.setVariable("y", rnd.nextDouble());
            val += expression.evaluate();
            count++;
        }

        return count + (int) (1.0);
    }

    private int benchDouble() {
        final Expression expression = new ExpressionBuilder(EXPRESSION)
                .variables("x", "y")
                .build();
        double val = 0;
        Random rnd = new Random();
        long time = System.nanoTime() + (1000000000 * BENCH_TIME);
        int count = 0;
        while (time > System.nanoTime()) {
            expression.setVariable("x", rnd.nextDouble());
            expression.setVariable("y", rnd.nextDouble());
            val += expression.evaluate();
            count++;
        }
        return count + (int) (1.0);
    }

    private int benchJavaMath() {
        long time = System.nanoTime() + (1000000000 * BENCH_TIME);
        double x;
        double y;
        double val = 0;
        int count = 0;
        Random rnd = new Random();
        while (time > System.nanoTime()) {
            x = rnd.nextDouble();
            y = rnd.nextDouble();
            val += Math.log(x) - (2 + 1) * y * (Math.sqrt(Math.pow(x, Math.cos(y))));
            count++;
        }
        return count + (int) (1.0);
    }

    private int benchJavaScript() throws Exception {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        double x;
        double y;
        double val = 0;
        int count = 0;
        Random rnd = new Random();
        if (engine == null) {
            System.err.println("Unable to instantiate javascript engine. skipping naive JS bench.");
            return -1;
        } else {
            long time = System.nanoTime() + (1000000000 * BENCH_TIME);
            engine.eval("function f(x, y) {return Math.log(x) - (2 + 1) * y * (Math.sqrt(Math.pow(x, Math.cos(y))))}");
            Invocable inv = (Invocable) engine;
            while (time > System.nanoTime()) {
                x = rnd.nextDouble();
                y = rnd.nextDouble();
                val += (Double) inv.invokeFunction("f", x, y);
                count++;
            }
        }
        return count + (int) (1.0);
    }

}
