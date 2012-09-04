/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.model.bulkeditor.column;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;

/**
 * BuldEditorColumn
 * 
 * The concept of representing and modifiying of datatypes by bulk editing is that the column has a
 * logic and setting how the data need to be edited. The table model is able to connect the columns
 * with actually bound datatypes.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BulkEditorColumnModel extends TableColumn {

    private ControlModel<?> controlModel;

    private boolean editable;

    /** Logger */
    protected static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(BulkEditorColumnModel.class);

    /**
     * Creates a new {@link BulkEditorColumnModel} instance.
     * 
     * @param id
     *            the id of the column
     * @param propertyPath
     *            the property to be bound to the column
     */
    public BulkEditorColumnModel(String id, String propertyPath, boolean editable, ControlModel<?> controlModel) {
        super(id, propertyPath);

        if (controlModel == null) {
            throw new IllegalArgumentException("Cannot create controlModel null");
        }

        this.controlModel = controlModel;
        this.editable = editable;
    }

    /**
     * Getter for the control model. It sets the control model to the given datatype
     * 
     * @param datatype
     *            the datatype to get the control model for or null to recieve the empty control
     *            model
     * @return
     */
    public ControlModel<?> getControlModel(Datatype datatype) {
        if (datatype == null) {
            controlModel.changeProperty(null);
            return controlModel;
        }

        NabuccoPropertyResolver<Datatype> resolver = new NabuccoPropertyResolver<Datatype>(datatype);
        NabuccoProperty actualProperty = resolver.resolveProperty(this.getPropertyPath());

        controlModel.changeProperty(actualProperty);
        return controlModel;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public ControlType getType() {
        ControlModel<?> controlModel = this.getControlModel(null);

        return controlModel.getControlType();
    }

    /**
     * Getter for the editable.
     * 
     * @return Returns the editable.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Applyes the given String to the column bound property of the given datatype
     * 
     * @param datatype
     *            the datatype to set new value for
     * @param newValue
     *            the new value
     * @throws ControlParserException
     */
    public void applyNewValue(Datatype datatype, String newValue) {
        ControlModel<?> controlModel = this.getControlModel(datatype);

        Object valueBefore = controlModel.getValue();

        controlModel.setValue(newValue);

        Object valueAfter = controlModel.getValue();

        boolean changed = this.checkIfChanged(valueBefore, valueAfter);

        if (changed) {
            NabuccoProperty property = controlModel.getProperty();
            PropertyOwner parent = property.getParent();

            if (parent instanceof Datatype) {
                Datatype instance = (Datatype) parent;

                // Only Persistent Datatypes must be set to MODIFIED.
                if (instance.getDatatypeState() == DatatypeState.PERSISTENT) {
                    instance.setDatatypeState(DatatypeState.MODIFIED);
                }
            }
        }
    }

    /**
     * Compares two values and returns true if they are not same
     * 
     * @param valueBefore
     *            the first value
     * @param valueAfter
     *            the second value
     * @return
     */
    private boolean checkIfChanged(Object valueBefore, Object valueAfter) {
        if (valueBefore == null && valueAfter != null) {
            return true;
        } else if (valueBefore == null && valueAfter == null) {
            return false;
        }

        return !valueBefore.equals(valueAfter);
    }

    /**
     * Creates the json representation of the datatype according to the column settings
     * 
     * @param datatype
     *            datatype to be represented
     * @return json reporesentation
     */
    public JsonMap render(Datatype datatype) {
        JsonMap jsonMap = new JsonMap();

        jsonMap.add(JSON_TYPE, this.getType());
        jsonMap.add(JSON_ID, this.getId());

        ControlModel<?> controlModel = this.getControlModel(datatype);
        jsonMap.add(JSON_EDITABLE, this.isEditable() && controlModel.isEditable());

        jsonMap.add(JSON_MODEL, controlModel.toJson());

        return jsonMap;
    }

}
