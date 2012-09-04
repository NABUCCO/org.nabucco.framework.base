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
package org.nabucco.framework.base.ui.web.model.dialog.controls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;

/**
 * DialogDateControlModel
 * 
 * The model of the date dialog control.
 * 
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogDateControlModel extends DialogControlModel {

    private static final String DATE_FORMAT = "dd.MM.yy";

    /**
     * 
     * Creates a new {@link DialogDateControlModel} instance.
     * 
     * @param id
     *            The id of the control
     * @param label
     *            the label of the control
     * @param tooltip
     *            the tooltip of the control
     * @param type
     *            the type of the control
     * @param defaultValue
     *            default value of the control
     * @param position
     *            the position of the control
     * @param mandatory
     *            is the field mandatory
     * @param editable
     *            is the control editable
     */
    public DialogDateControlModel(String id, String label, String tooltip, String position, String defaultValue,
            boolean mandatory, boolean editable) {
        super(id, label, tooltip, DialogControlType.DATE, position, mandatory, editable);

        super.setValue(defaultValue);
    }

    @Override
    public void init() {

    }

    @Override
    public boolean isValid() {
        boolean standardResult = super.isValid();
        if (standardResult == true) {
            if (!this.isInitialized()) {
                return true;
            }

            String date = this.getValue();

            try {
                if (date.length() == 0) {
                    return true;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                dateFormat.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        return standardResult;
    }

    /**
     * Returns the value as a Date
     * 
     * @return Date or null if nothing given
     * @throws ParseException
     *             if cannot parse
     */
    public Date getValueAsDate() throws ParseException {
        String date = this.getValue();

        if (date.length() == 0) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date parsedDate = dateFormat.parse(date);
        return parsedDate;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();

        return retVal;
    }
}
