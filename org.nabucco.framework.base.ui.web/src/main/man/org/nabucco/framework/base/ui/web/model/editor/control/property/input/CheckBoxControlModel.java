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
package org.nabucco.framework.base.ui.web.model.editor.control.property.input;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NBoolean;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.BooleanControlParser;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoValidator;

/**
 * CheckBoxControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CheckBoxControlModel extends PropertyControlModel<NBoolean> {

    /**
     * 
     * Creates a new {@link CheckBoxControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            property path
     * @param validator
     *            validator
     * @param editable
     *            editable
     */
    public CheckBoxControlModel(String id, String propertyPath, NabuccoValidator<NBoolean> validator,
            DependencyController dependencyController, boolean editable) {
        super(id, propertyPath, ControlType.CHECKBOX, validator, dependencyController, editable);
        this.setParser(new BooleanControlParser());
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
    @Override
    public String getStringValue() {
        Basetype value = super.getValue();
        if (value != null) {
            return value.getValueAsString();
        }
        return String.valueOf(false);
    }

    @Override
    public NBoolean parse(String value) throws ControlParserException {

        NBoolean parsedVal = this.getParser().parseString(this.getProperty(), value);
        return parsedVal;

    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_VALUE, this.getStringValue());

        return json;
    }
}
