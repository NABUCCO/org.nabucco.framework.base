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
package org.nabucco.framework.base.ui.web.model.editor.control.property.date;

import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.formatter.ControlFormatter;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.DateControlParser;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoValidator;

/**
 * DateControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DateControlModel extends PropertyControlModel<NDate> {

    /**
     * 
     * Creates a new {@link DateControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            propertypath
     * @param validator
     *            validator
     * @param formatter
     *            formatter
     * @param dependencyController
     *            dependency controller
     * @param editable
     *            editable
     */
    public DateControlModel(String id, String propertyPath, NabuccoValidator<NDate> validator,
            ControlFormatter<NDate> formatter, DependencyController dependencyController, boolean editable) {
        super(id, propertyPath, ControlType.DATE, validator, dependencyController, editable);
        this.setFormatter(formatter);
        this.setParser(new DateControlParser());
    }

    /**
     * Getter for the basetype value object.
     * 
     * @return the input value
     */
    public Object getInputValue() {
        return super.getValue().getValue();
    }

    /**
     * Getter for the value as string.
     * 
     * @return the string value
     */
    public String getStringValue() {
        NDate value = super.getValue();
        if (value != null) {
            return value.getValueAsString();
        }
        return null;
    }

    @Override
    public NDate parse(String value) throws ControlParserException {
        NDate parsedVal = this.getParser().parseString(value);
        return parsedVal;

    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();
        String formattedString = this.getFormatter().format(this.getValue());
        json.add(JSON_VALUE, formattedString);

        return json;
    }

}
