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
package org.nabucco.framework.base.facade.datatype.serialization.json;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.text.NumberFormat;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * JsonFormatter
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public class JsonFormatter implements JsonSyntaxConstants {

    private static final NabuccoLogger LOGGER = NabuccoLoggingFactory.getInstance().getLogger(JsonFormatter.class);

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    /**
     * 
     * @param s
     * @return
     */
    public static String format(String s) {
        StringBuffer result = new StringBuffer();
        if (s == null) {
            result.append("");
        } else {
            process(result, s);
        }
        return result.toString();
    }

    /**
     * @param result
     * @param s
     */
    private static void process(StringBuffer result, String s) {
        StreamTokenizer st = new StreamTokenizer(new StringReader(s));
        int current = StreamTokenizer.TT_EOF;
        try {
            int currentIndent = 0;
            while ((current = st.nextToken()) != StreamTokenizer.TT_EOF) {
                switch (current) {
                case LEFT_BRACE: {
                    currentIndent += 1;
                    result.append(LEFT_BRACE);
                    result.append(NEW_LINE);
                    indent(currentIndent, result);
                    break;
                }
                case RIGHT_BRACE: {
                    currentIndent -= 1;
                    result.append(NEW_LINE);
                    indent(currentIndent, result);
                    result.append(RIGHT_BRACE);
                    break;
                }
                case LEFT_BRACKET: {
                    result.append(LEFT_BRACKET);
                    break;
                }
                case RIGHT_BRACKET: {
                    result.append(RIGHT_BRACKET);
                    break;
                }
                case COMMA: {
                    result.append(COMMA);
                    result.append(NEW_LINE);
                    indent(currentIndent, result);
                    break;
                }
                case COLON: {
                    result.append(COLON);
                    break;
                }
                case StreamTokenizer.TT_NUMBER: {
                    result.append(NUMBER_FORMAT.format(st.nval));
                    break;
                }
                case StreamTokenizer.TT_WORD: {
                    result.append(st.sval);
                    break;
                }
                case DOUBLE_QUOTES: {
                    result.append(DOUBLE_QUOTES);
                    result.append(st.sval);
                    result.append(DOUBLE_QUOTES);
                    break;
                }
                default:
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.error(e, "error formating json string");
        }
    }

    private static void indent(int indent, StringBuffer a) {
        for (int i = 0; i < indent; i++) {
            a.append(TAB);
        }
    }
}
