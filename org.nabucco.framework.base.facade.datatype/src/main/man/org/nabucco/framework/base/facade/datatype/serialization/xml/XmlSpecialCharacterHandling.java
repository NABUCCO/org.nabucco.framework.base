/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.serialization.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * XmlSpecialCharacterHandling
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public class XmlSpecialCharacterHandling {

    private static char[][] CHARS = { { '&', '\\', '\'', '<', '>' }, "&amp;".toCharArray(), "&quot;".toCharArray(),
            "&apos;".toCharArray(), "&lt;".toCharArray(), "&gt;".toCharArray() };

    /**
     * Quotes a given String by replacing all XML Special Entities.
     * 
     * @param input
     *            input to Quote.
     * @return Quoted representation of the String.
     * @throws IOException
     */
    public static String quote(String input) throws IOException {
        StringReader sr = new StringReader(input);
        StringWriter sw = new StringWriter();
        int current;
        while ((current = sr.read()) != -1) {
            int replace = Arrays.binarySearch(CHARS[0], (char) current);
            if (replace > -1) {
                sw.write(CHARS[1 + replace]);
            } else {
                sw.write(current);
            }
        }
        return sw.toString();
    }

    /**
     * Unquoted special XML related Entities
     * 
     * @param input
     *            String containing quoted text.
     * @return Unquoted representation.
     */
    public static String unquote(String input) {
        for (int i = 0; i < CHARS[0].length; i++) {
            input = input.replaceAll(String.valueOf(CHARS[i + 1]), String.valueOf(CHARS[0][i]));
        }
        return input;
    }
}
