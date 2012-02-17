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
package org.nabucco.framework.base.ui.web.model.control.util.validator;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.ui.web.component.WebComponent;

/**
 * NabuccoDateValidator
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class NabuccoDateValidator extends NabuccoAbstractValidator<NDate> {

    /**
     * Creates a new {@link NabuccoDateValidator} instance.
     * 
     * @param component
     */
    public NabuccoDateValidator(WebComponent component) {
        super(component);

    }

    @Override
    public boolean validateElement(NDate input) {
        boolean retVal = true;
        if (input != null) {

            Calendar cal = new GregorianCalendar();
            cal.setTime(input.getValue());

            // Trivial validation
            Calendar min = new GregorianCalendar(1000, 01, 01);
            Calendar max = new GregorianCalendar(3000, 01, 01);

            if (cal.after(min) && cal.before(max)) {
                retVal = true;
            } else {
                retVal = false;
            }
        }
        return retVal;
    }
}
