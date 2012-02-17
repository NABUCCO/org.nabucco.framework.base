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
package org.nabucco.framework.base.ui.web.model.control.util.parser;

import org.nabucco.framework.base.facade.datatype.NBoolean;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanPropertyValue;

/**
 * NabuccoBooleanParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BooleanControlParser implements ControlParser<NBoolean> {

    @Override
    public NBoolean parseString(String value) throws ControlParserException {

        Boolean parsedBool = Boolean.parseBoolean(value);
        NBoolean retVal = new BooleanPropertyValue(parsedBool);
        return retVal;
    }

}
