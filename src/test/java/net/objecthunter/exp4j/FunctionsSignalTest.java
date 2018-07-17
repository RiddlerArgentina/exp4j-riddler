/*
* Copyright 2018 Federico Vera
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

import net.objecthunter.exp4j.extras.FunctionsSignal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Federico Vera {@literal <fede [at] riddler.com.ar>}
 */
public class FunctionsSignalTest {


    @Test
    public void testSinc() {
        Expression sinc = new ExpressionBuilder("sinc(x)")
                             .functions(FunctionsSignal.getFunctions())
                             .variable("x")
                             .build();

        assertEquals(1, sinc.setVariable("x", 0).evaluate(), 0d);
        assertEquals(0, sinc.setVariable("x", Math.PI).evaluate(), 1e-12);
        assertEquals(0, sinc.setVariable("x", -Math.PI).evaluate(), 1e-12);
        assertEquals(0, sinc.setVariable("x", -2 * Math.PI).evaluate(), 1e-12);
        assertEquals(0, sinc.setVariable("x",  2 * Math.PI).evaluate(), 1e-12);

        double x = Math.random();
        assertEquals(Math.sin(x)/x, sinc.setVariable("x",  x).evaluate(), 1e-12);
    }
}
