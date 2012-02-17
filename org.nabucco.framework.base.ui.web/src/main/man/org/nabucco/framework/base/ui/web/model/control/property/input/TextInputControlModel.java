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
package org.nabucco.framework.base.ui.web.model.control.property.input;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.converter.BasetypeConverter;
import org.nabucco.framework.base.facade.datatype.converter.ConverterException;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.control.ControlType;
import org.nabucco.framework.base.ui.web.model.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.control.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.control.util.formatter.ControlFormatter;
import org.nabucco.framework.base.ui.web.model.control.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.control.util.validator.NabuccoValidator;

/**
 * ControlModel for input fields.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TextInputControlModel extends PropertyControlModel<Basetype> {

    /**
     * Creates a new {@link TextInputControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            property path
     * @param validator
     *            validator
     * @param formatter
     *            the data formatter
     * @param dependencySet
     *            the dependency set
     * @param editable
     *            editable
     */
    public TextInputControlModel(String id, String propertyPath, NabuccoValidator<Basetype> validator,
            ControlFormatter<Basetype> formatter, DependencyController dependencySet, boolean editable) {
        super(id, propertyPath, ControlType.TEXT, validator, dependencySet, editable);

        super.setFormatter(formatter);
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
        Basetype value = super.getValue();

        String retVal = this.getFormatter().format(value);

        return retVal;
    }

    @Override
    public Basetype parse(String value) throws ControlParserException {
        try {
            Basetype newValue = this.instantiate();

            String clearedString = this.getFormatter().removeFormat(value);

            BasetypeConverter.setValueAsString(newValue, clearedString);

            return newValue;

        } catch (ConverterException ce) {
            throw new ControlParserException("Cannot convert String '" + value + "' to Basetype.'", ce);
        }
    }


    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_VALUE, this.getStringValue());

        return json;
    }

}
