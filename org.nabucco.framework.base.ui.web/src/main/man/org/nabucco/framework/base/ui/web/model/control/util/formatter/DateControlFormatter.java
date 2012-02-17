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
package org.nabucco.framework.base.ui.web.model.control.util.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.ui.web.component.WebComponent;

/**
 * NabuccoDateFormatter
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DateControlFormatter implements ControlFormatter<NDate> {

    /**
     * Default private constructor Creates a new {@link CurrencyControlFormatter} instance.
     * 
     * @param component
     *            not used at the moment
     */
    public DateControlFormatter(WebComponent component) {
    }

    /**
     * Format value to a correct String
     */
    @Override
    public String format(NDate value) {
        String retVal = "";

        if (value != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date dateValue = value.getValue();
            retVal = dateFormat.format(dateValue);
        }
        return retVal;
    }

    @Override
    public String removeFormat(String value) {
        return value;
    }


}
