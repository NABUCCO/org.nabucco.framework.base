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
package org.nabucco.framework.base.ui.web.model.editor.util.parser;

import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * NabuccoParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface ControlParser<N> {

    /**
     * Parse String value
     * 
     * @param value
     *            Value to parse
     * @return Parsed type
     */
    N parseString(String value) throws ControlParserException;

    /**
     * Parse String value
     * 
     * @param property
     *            the property to parse the string for
     * @param value
     *            Value to parse
     * @return Parsed type
     */
    N parseString(NabuccoProperty property, String value) throws ControlParserException;
}
