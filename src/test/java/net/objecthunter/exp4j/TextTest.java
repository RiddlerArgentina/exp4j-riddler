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

import java.util.Locale;
import java.util.ResourceBundle;
import net.objecthunter.exp4j.utils.Text;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class TextTest {
    @Test
    public void testText() {
        ResourceBundle L10N_en = ResourceBundle.getBundle("net.objecthunter.exp4j.utils.Bundle", Locale.ENGLISH);
        ResourceBundle L10N_es = ResourceBundle.getBundle("net.objecthunter.exp4j.utils.Bundle", new Locale("es"));

        for (String key : L10N_en.keySet()) {
            assertTrue(L10N_es.containsKey(key));
        }
        for (String key : L10N_es.keySet()) {
            assertTrue(L10N_en.containsKey(key));
        }
    }

    public void testText2() {
        String foo = "Unexistent String ofhaiwogheowihgoih;";
        String bar = Text.l10n(foo);
        Assert.assertEquals(foo, bar);
    }

    public void testText3() {
        String foo = "Unexistent String ofhaiwogheowihgoih;";
        String bar = Text.l10n(foo, 10);
        Assert.assertEquals(foo, bar);
    }
}
