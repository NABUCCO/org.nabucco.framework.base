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

/**
 * JsonSyntaxConstants
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public interface JsonSyntaxConstants {

    /**
     * Open JSON Datatype.
     */
    final char LEFT_BRACE = '{';

    /**
     * Close JSON Datatype.
     */
    final char RIGHT_BRACE = '}';

    /**
     * Open JSON Array.
     */
    final char LEFT_BRACKET = '[';

    /**
     * Close JSON Array.
     */
    final char RIGHT_BRACKET = ']';

    /**
     * Colon.
     */
    final char COLON = ':';

    /**
     * Comma.
     */
    final char COMMA = ',';

    /**
     * Double Quotes.
     */
    final char DOUBLE_QUOTES = '"';

    /**
     * True literal.
     */
    final String TRUE = "true";

    /**
     * False literal.
     */
    final String FALSE = "false";

    /**
     * Null literal.
     */
    final String NULL = "null";

    /**
     * New Line.
     */
    final char NEW_LINE = '\n';

    /**
     * Tabstop.
     */
    final char TAB = '\t';
}
