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
package org.nabucco.framework.base.ui.web.component.work.bulkeditor.column;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.BulkEditorColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.component.work.list.TableElement;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.bulkeditor.BulkEditorModel;
import org.nabucco.framework.base.ui.web.model.bulkeditor.column.BulkEditorColumnModel;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnLayoutType;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnRenderer;

/**
 * BulkEditorControl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class BulkEditorColumn extends WebComponent implements TableElement {

    private static final long serialVersionUID = 1L;

    /** Logger */
    protected static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EditorControl.class);

    /** The Editor Field Extension */
    private BulkEditorColumnExtension extension;

    private BulkEditorColumnModel model;

    /** The editor model */
    private BulkEditorModel editorModel;

    /** The control dependency Set */
    private DependencyController dependencySet;

    /**
     * Creates a new {@link BulkEditorColumn} instance.
     * 
     * @param extension
     */
    public BulkEditorColumn(BulkEditorColumnExtension extension, BulkEditorModel editorModel) {
        super(WebElementType.BULKEDITOR_COLUMN, extension);
        if (editorModel == null) {
            throw new IllegalArgumentException("Cannot create Bulk Editor Column. Bulk Editor model is 'null'.");

        }

        this.extension = extension;
        this.editorModel = editorModel;
    }

    @Override
    public void init() throws ExtensionException {
        String propertyPath = this.getPropertyPath();

        String id = this.getId();

        if (id == null) {
            throw new IllegalArgumentException(
                    "Error by instanciation of the control. The id is not set and is 'null'.");
        }

        if (propertyPath == null || propertyPath.isEmpty()) {
            throw new IllegalArgumentException("Cannot add Control for property [null] to the model.");
        }

        DependencySetExtension dependencySetExtension = extension.getDependencySet();
        dependencySet = new DependencyController(dependencySetExtension);
        dependencySet.init();
        ControlModel<?> controlModel = this.instantiateModel(id, propertyPath);
        controlModel.init();

        model = new BulkEditorColumnModel(this.getId(), this.getPropertyPath(), this.isEditable(), controlModel);

        model.setLabel(this.getLabel());
        model.setTooltip(this.getTooltip());
        model.setSortable(this.isSortable());
        model.setWidth(this.getWidth());
        model.setVisible(true);
        model.setRenderer(ColumnRenderer.getDefaultRenderer(ColumnLayoutType.TEXT));

        // Add column to the editor model
        editorModel.addColumn(model);
    }

    @Override
    public <T extends Datatype> TableModel<T> getTableModel() {
        return editorModel.getTableModel();
    }

    /**
     * Instanciate a Model of the Control
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            the property path
     * @return
     */
    protected abstract ControlModel<?> instantiateModel(String id, String propertyPath) throws ExtensionException;

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    public BulkEditorColumnModel getModel() {
        return model;
    }

    /**
     * Getter for the editor model.
     * 
     * @return Returns the model.
     */
    public BulkEditorModel getEditorModel() {
        return editorModel;
    }

    /**
     * Getter for the dependency set
     * 
     * @return dependency set
     */
    protected DependencyController getDependencySet() {
        return dependencySet;
    }

    /**
     * Getter for the editor control id.
     * 
     * @return the editor control id or null if no setted
     */
    @Override
    public String getId() {
        if (extension.getIdentifier() == null) {
            return null;
        }

        return extension.getIdentifier().getValue();
    }

    /**
     * Getter for the editor control property.
     * 
     * @return the editor control property
     */
    public String getPropertyPath() {
        return PropertyLoader.loadProperty(extension.getProperty());
    }

    /**
     * Getter for the editor control label.
     * 
     * @return the editor control label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(extension.getLabel());
    }

    /**
     * Getter for the editor control tooltip.
     * 
     * @return the editor control tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(extension.getTooltip());
    }

    /**
     * Return if the Control Editable Flag.
     * 
     * @return <b>true</b> if the control is editable, <b>false</b> if not
     */
    public boolean isEditable() {
        return PropertyLoader.loadProperty(extension.getEditable());
    }

    /**
     * Returns the property path
     * 
     * @return
     */
    public Integer getWidth() {
        return PropertyLoader.loadProperty(extension.getWidth());
    }

    /**
     * Returns if the column is editable
     * 
     * @return
     */
    public boolean isSortable() {
        return PropertyLoader.loadProperty(extension.getSortable());
    }

    /**
     * Getter for the extension.
     * 
     * @return Returns the extension.
     */
    protected BulkEditorColumnExtension getExtension() {
        return extension;
    }

    /**
     * Accepts the web element visitor. Overload this function to let element be visited
     * 
     * @param visitor
     *            visitor to be accepted
     * @param context
     *            context of the visitor
     */
    @Override
    public <T extends WebElementVisitorContext> void accept(WebElementVisitor<T> visitor, T context)
            throws VisitorException {
        if (visitor != null) {
            visitor.visit(this, context);
        }
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = new JsonMap();

        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());

        if (model != null) {
            json.add(JSON_MODEL, model.toJson());
        }

        return json;
    }
}
