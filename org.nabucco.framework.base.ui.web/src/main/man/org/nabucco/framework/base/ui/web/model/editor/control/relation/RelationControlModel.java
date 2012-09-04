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
package org.nabucco.framework.base.ui.web.model.editor.control.relation;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.delegates.CollectionRelationDelegate;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.delegates.ComponentRelationDelegate;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.delegates.DatatypeRelationDelegate;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.delegates.RelationDelegate;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * RelationControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class RelationControlModel<N extends NType> extends ControlModel<N> {

    private TableModel<Datatype> tableModel;

    private String displayPath;

    private RelationDelegate<NabuccoProperty, NType> delegate;

    /**
     * Creates a new {@link RelationControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param type
     *            the type of the control
     * @param propertyPath
     *            path to the bound datatype element
     * @param displayPath
     *            path to display
     * @param dependencyController
     *            dependency controller
     * @param editable
     *            if the control editable
     */
    public RelationControlModel(String id, ControlType type, String propertyPath, String displayPath,
            DependencyController dependencyController, boolean editable) {
        super(id, propertyPath, type, null, dependencyController, editable);

        this.displayPath = displayPath;
    }

    @Override
    public void setProperty(NabuccoProperty property) {
        super.setProperty(property);
        this.initDelegate();
    }

    @Override
    public void changeProperty(NabuccoProperty property) {
        super.changeProperty(property);

        if (property != null) {
            this.initDelegate();
        }
    }

    /**
     * Setter for the delegate.
     * 
     * @param delegate
     *            The delegate to set.
     */
    private void setDelegate(RelationDelegate<NabuccoProperty, NType> delegate) {
        this.delegate = delegate;
    }

    /**
     * Returns the actual relation delegate
     * 
     * @return
     */
    protected RelationDelegate<NabuccoProperty, NType> getDelegate() {
        return this.delegate;
    }

    /**
     * Instanciates a correct delegate instance of the rekation delegate according to the set
     * property type
     * 
     * @return the relation delegate
     */
    @SuppressWarnings("unchecked")
    private RelationDelegate<?, ?> initDelegate() {
        NabuccoProperty property = this.getProperty();
        RelationDelegate<?, ?> retVal = null;

        if (property == null) {
            throw new IllegalStateException("Cannot get Delegate for property type 'null'.");
        }

        switch (property.getPropertyType()) {

        case DATATYPE:
            retVal = new DatatypeRelationDelegate(this.getTableModel());
            break;

        case COLLECTION:
            retVal = new CollectionRelationDelegate(this.getTableModel());
            break;

        case COMPONENT_RELATION:
            retVal = new ComponentRelationDelegate(this.getTableModel());
            break;

        }

        if (retVal == null) {
            throw new IllegalStateException("Cannot get Delegate for property type '"
                    + property.getPropertyType() + "'.");
        }

        this.setDelegate((RelationDelegate<NabuccoProperty, NType>) retVal);

        return retVal;
    }

    /**
     * Getter for the tableModel.
     * 
     * @return Returns the tableModel.
     */
    protected TableModel<Datatype> getTableModel() {
        return this.tableModel;
    }

    /**
     * Setter for the tableModel.
     * 
     * @param tableModel
     *            The tableModel to set.
     */
    protected void setTableModel(TableModel<Datatype> tableModel) {
        this.tableModel = tableModel;
    }

    /**
     * Getter for the displayPath.
     * 
     * @return Returns the displayPath.
     */
    public String getDisplayPath() {
        return this.displayPath;
    }

    /**
     * Setter for the displayPath.
     * 
     * @param displayPath
     *            The displayPath to set.
     */
    protected void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

}
