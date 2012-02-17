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
package org.nabucco.framework.base.ui.web.model.control.relation.picker;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.control.ControlType;
import org.nabucco.framework.base.ui.web.model.control.relation.RelationControlModel;
import org.nabucco.framework.base.ui.web.model.control.relation.picker.delegates.RelationDelegate;
import org.nabucco.framework.base.ui.web.model.control.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.control.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * PickerControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class PickerControlModel extends RelationControlModel<NType> {

    private static final String JSON_MULTI_SELECTION = "multiSelection";

    private static final String JSON_OPEN_ACTION = "openAction";

    private boolean multipleSelection;

    private QueryParameterMapper queryParameterMapper;

    private String openAction;

    /**
     * Creates a new {@link PickerControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param tableModel
     *            the content provider for the dialog table content
     * @param propertyPath
     *            path to a relation property to be controlled by the model
     * @param validator
     *            validator element to check selection
     * @param multiSelection
     *            multiselection of elements allowed
     * @param displayPath
     *            name of the property to show
     * @param openAction
     *            the action to open the selected element
     * @param dependencySet
     *            the dependency set of the picker
     * @param queraParameterMapper
     *            the mapper for query parameters
     * @param multipleSelection
     *            multiple selection possible
     * @param editable
     *            editable field
     */
    public PickerControlModel(String id, String propertyPath, TableModel<Datatype> tableModel, String displayPath,
            String openAction, DependencyController dependencySet, QueryParameterMapper queraParameterMapper,
            boolean multipleSelection, boolean editable) {
        super(id, ControlType.PICKER, propertyPath, tableModel, displayPath, dependencySet, editable);

        this.queryParameterMapper = queraParameterMapper;
        this.setMultipleSelection(multipleSelection);
        this.setOpenAction(openAction);
    }

    @Override
    protected NType parse(String stringValue) throws ControlParserException {
        RelationDelegate<NabuccoProperty, NType> delegate = this.getDelegate();
        return delegate.parse(this.getProperty(), stringValue, this.isMultipleSelection());
    }

    /**
     * Setter for the multipleSelection.
     * 
     * @param multipleSelection
     *            The multipleSelection to set.
     */
    private void setMultipleSelection(boolean multipleSelection) {
        this.multipleSelection = multipleSelection;
    }

    /**
     * Getter for the multipleSelection.
     * 
     * @return Returns the multipleSelection.
     */
    private boolean isMultipleSelection() {
        return this.multipleSelection;
    }

    /**
     * Setter for the openAction.
     * 
     * @param openAction
     *            The openAction to set.
     */
    private void setOpenAction(String openAction) {
        this.openAction = openAction;
    }

    /**
     * Getter for the openAction.
     * 
     * @return Returns the openAction.
     */
    private String getOpenAction() {
        return this.openAction;
    }

    /**
     * Resolves given Query Parameter and returns the value of target property
     * 
     * @param parameter
     *            the parameter to process mapping for
     * @return The Basetype or Enumeration which was mapped or null if nothing found
     */
    public Object getQueryParameter(String parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Cannot map query parameter 'null'");
        }

        String parameterTargetProperty = this.queryParameterMapper.getParameterTargetProperty(parameter);

        if (parameterTargetProperty == null) {
            return null;
        }

        NabuccoProperty boundProperty = this.getProperty();
        PropertyOwner parentDatatype = boundProperty.getParent();

        if (parentDatatype instanceof Datatype) {
            Datatype datatype = (Datatype) parentDatatype;

            NabuccoPropertyResolver<Datatype> resolver = new NabuccoPropertyResolver<Datatype>(datatype);

            NabuccoProperty resolvedProperty = resolver.resolveProperty(parameterTargetProperty);

            if (resolvedProperty == null) {
                throw new IllegalArgumentException("The targetProperty "
                        + parameterTargetProperty
                        + " cannot be resolved to a type. It does not exist in bound datatype.");
            }

            return resolvedProperty.getInstance();
        }

        throw new IllegalArgumentException("The parent property is not a Datatype ");
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_MULTI_SELECTION, this.multipleSelection);
        json.add(JSON_OPEN_ACTION, this.getOpenAction());

        NType value = this.getValue();
        String displayPath = this.getDisplayPath();

        if (this.getDelegate() != null) {
            JsonElement valueList = this.getDelegate().toJson(value, displayPath);
            json.add(JSON_VALUE, valueList);
        } else {
            json.add(JSON_VALUE, "");
        }

        return json;
    }

}
