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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * NabuccoDateParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DateControlParser implements ControlParser<NDate> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    @Override
    public NDate parseString(String value) throws ControlParserException {
        NDate retVal = null;
        try {
            Date parsedDate = dateFormat.parse(value);
            retVal = new org.nabucco.framework.base.facade.datatype.date.Date();
            retVal.setValue(parsedDate);
        } catch (ParseException ex) {
            throw new ControlParserException("Cannot parse Date", ex);
        }
        return retVal;
    }

    @Override
    public NDate parseString(NabuccoProperty property, String value) throws ControlParserException {
        return this.parseString(value);
    }

}
