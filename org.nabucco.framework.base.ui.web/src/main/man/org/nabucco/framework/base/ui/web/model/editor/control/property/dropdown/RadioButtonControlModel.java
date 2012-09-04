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
package org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionItem;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.EnumerationControlParser;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoValidator;

/**
 * RadioButtonControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class RadioButtonControlModel<N extends NType> extends PropertyControlModel<N> {

    private static final String JSON_ITEMS = "items";

    /**
     * 
     * Creates a new {@link RadioButtonControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            property path
     * @param validator
     *            validator
     * @param dependencyController
     *            dependency controller
     * 
     * @param editable
     *            editable field
     */
    public RadioButtonControlModel(String id, String propertyPath, NabuccoValidator<N> validator,
            DependencyController dependencyController, boolean editable) {
        super(id, propertyPath, ControlType.RADIO, validator, dependencyController, editable);

        EnumerationControlParser<N> nabuccoEnumerationParser = new EnumerationControlParser<N>(this,
                super.getSelectionProvider());
        this.setParser(nabuccoEnumerationParser);
    }

    /**
     * Getter for the basetype value object.
     * 
     * @return the input value
     */
    public N getInputValue() {
        return super.getValue();
    }

    /**
     * Getter for the value as string.
     * 
     * @return the string value
     */
    public SelectionItem<N> getSelectionValue() {
        return this.getSelectionProvider().getSelectionValue(this.getProperty(), this.getValue());
    }

    @Override
    public String getStringValue() {
        return this.getSelectionValue().getI18n();
    }

    @Override
    public N parse(String value) throws ControlParserException {

        N parsedString = this.getParser().parseString(value);

        return parsedString;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        JsonList jsonList = new JsonList();

        List<SelectionItem<N>> selectionItems = this.getSelectionProvider().getSelectionItems(this.getProperty());
        for (SelectionItem<N> val : selectionItems) {
            jsonList.add(val.toJson());
        }

        json.add(JSON_VALUE, this.getSelectionValue().toJson());
        json.add(JSON_ITEMS, jsonList);

        return json;
    }

}
