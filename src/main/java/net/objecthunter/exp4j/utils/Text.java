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
package net.objecthunter.exp4j.utils;

import java.util.ResourceBundle;

/**
 *
 * @author Federico Vera {@literal <fede@riddler.com.ar>}
 */
public class Text {
    private final static ResourceBundle L10N = ResourceBundle.getBundle("net.objecthunter.exp4j.utils.Bundle");

    public static String l10n(String key) {
        try {
            return L10N.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return key;
        }
    }

    public static String l10n(String key, Object...args) {
        try {
            return String.format(L10N.getString(key), args);
        } catch (Exception e) {
            e.printStackTrace();
            return key;
        }
    }
}
