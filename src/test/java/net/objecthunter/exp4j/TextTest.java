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

import net.objecthunter.exp4j.utils.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class TextTest {
    @Test
    public void testText() {
        ResourceBundle L10N_en = ResourceBundle.getBundle("net.objecthunter.exp4j.utils.Bundle", Locale.ENGLISH);
        ResourceBundle L10N_es = ResourceBundle.getBundle("net.objecthunter.exp4j.utils.Bundle", new Locale("es"));

        for (String key : L10N_en.keySet()) {
            Assertions.assertTrue(L10N_es.containsKey(key));
        }
        for (String key : L10N_es.keySet()) {
            Assertions.assertTrue(L10N_en.containsKey(key));
        }
    }

    @Test
    public void testText2() {
        String foo = "Unexistent String ofhaiwogheowihgoih;";
        String bar = Text.l10n(foo);
        Assertions.assertEquals(foo, bar);
    }

    @Test
    public void testText3() {
        String foo = "Unexistent String ofhaiwogheowihgoih;";
        String bar = Text.l10n(foo, 10);
        Assertions.assertEquals(foo, bar);
    }

    @Test
    public void testText4() {
        String bar = Text.l10n(null);
        Assertions.assertNull(bar);
    }

    @Test
    public void testText5() {
        String bar = Text.l10n(null, 10);
        Assertions.assertNull(bar);
    }
}
