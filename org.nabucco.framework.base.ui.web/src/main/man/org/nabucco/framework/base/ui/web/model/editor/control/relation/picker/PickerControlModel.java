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
package org.nabucco.framework.base.ui.web.model.editor.control.relation.picker;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.RelationControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.delegates.RelationDelegate;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * PickerControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class PickerControlModel extends RelationControlModel<NType> {

    private static final String JSON_MULTI_SELECTION = "multiSelection";

    private static final String JSON_OPEN_ACTION = "openAction";

    private static final String JSON_AUTO_COMPLETE = "autoComplete";

    private boolean multipleSelection;

    private QueryParameterMapper queryParameterMapper;

    private String openAction;

    private String autoCompletionFilter;

    /**
     * 
     * Creates a new {@link PickerControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            the picker property path
     * @param dialogInstanceId
     *            the dialog instance id to use if any
     * @param displayPath
     *            the path to be displays
     * @param openAction
     *            the action to open picker dialog
     * @param dependencySet
     *            the dependency set
     * @param queryParameterMapper
     *            query parameter mapper
     * @param multipleSelection
     *            the multiple selection allowed or not
     * @param editable
     *            if the control is editable
     */
    public PickerControlModel(String id, String propertyPath, String dialogInstanceId, String displayPath,
            String openAction, DependencyController dependencySet, QueryParameterMapper queryParameterMapper,
            boolean multipleSelection, boolean editable) {
        super(id, ControlType.PICKER, propertyPath, displayPath, dependencySet, editable);

        this.queryParameterMapper = queryParameterMapper;
        this.setMultipleSelection(multipleSelection);
        this.setOpenAction(openAction);

        if (dialogInstanceId != null && !dialogInstanceId.isEmpty()) {
            PickerDialog pickerDialog = NabuccoServletUtil.getPickerDialogController()
                    .getPickerDialog(dialogInstanceId);
            this.setTableModel(pickerDialog.getTableModel());
        }
    }

    /**
     * 
     * Creates a new picker control without picker dialog
     * 
     * @param id
     *            The id of the picker control
     * @param propertyPath
     *            the property path
     * @param displayPath
     *            the path to display
     * @param editable
     *            is editable
     */
    public PickerControlModel(String id, String propertyPath, String displayPath, DependencyController dependencySet,
            boolean editable) {
        this(id, propertyPath, null, displayPath, null, dependencySet, null, false, editable);
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
     * Setter for the autoCompletionFilter.
     * 
     * @param autoCompletionFilter
     *            The autoCompletionFilter to set.
     */
    public void setAutoCompletionFilter(String autoCompletionFilter) {
        this.autoCompletionFilter = autoCompletionFilter;
    }

    /**
     * Getter for the autoCompletionFilter.
     * 
     * @return Returns the autoCompletionFilter.
     */
    public String getAutoCompletionFilter() {
        return this.autoCompletionFilter;
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

        if (queryParameterMapper == null) {
            return null;
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
    public String getStringValue() {
        NType value = this.getValue();
        String displayPath = this.getDisplayPath();
        if (this.getDelegate() != null) {
            return this.getDelegate().toString(value, displayPath);
        } else {
            return "";
        }
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_MULTI_SELECTION, this.multipleSelection);
        json.add(JSON_OPEN_ACTION, this.getOpenAction());

        String autoCompletionFilter = this.getAutoCompletionFilter();
        if (autoCompletionFilter != null && !autoCompletionFilter.isEmpty()) {
            json.add(JSON_AUTO_COMPLETE, autoCompletionFilter);
        }

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
