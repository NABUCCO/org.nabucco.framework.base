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
package org.nabucco.framework.base.ui.web.component.work.editor.control;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorGridElement;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.EditorGridElementModel;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;

/**
 * EditorField
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class EditorControl extends EditorGridElement {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    /** Logger */
    protected static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EditorControl.class);

    /** The Editor Field Extension */
    private EditorControlExtension extension;

    /** The Control Model */
    protected ControlModel<?> model;

    /** The control dependency Set */
    protected DependencyController dependencySet;

    /**
     * Creates a new {@link EditorControl} instance.
     * 
     * @param extension
     *            the editor control extension
     */
    public EditorControl(EditorControlExtension extension) {
        super(WebElementType.EDITOR_CONTROL, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create editor control for extension 'null'.");
        }
        this.extension = extension;
    }

    /**
     * Creates a new {@link EditorControl} instance
     * 
     * @param extension
     *            the editor control Extension
     * @param type
     *            type of the control
     */
    public EditorControl(EditorControlExtension extension, WebElementType type) {
        super(type, extension);

        if (extension == null) {
            throw new IllegalArgumentException("Cannot create editor control for extension 'null'.");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        String propertyPath = this.getProperty();

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
        model = this.instantiateModel(id, propertyPath);
        model.init();
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
    public abstract ControlModel<?> instantiateModel(String id, String propertyPath) throws ExtensionException;

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    public ControlModel<?> getModel() {
        return model;
    }

    @Override
    public EditorGridElementModel getGridElementModel() {
        return model;
    }

    /**
     * Indicates if the control may be visible on the ui
     * 
     * @return true if visible
     */
    @Override
    public boolean isVisible() {
        return model.isVisible();
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
    public String getProperty() {
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
     * Getter for the extension.
     * 
     * @return Returns the extension.
     */
    protected EditorControlExtension getExtension() {
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
        JsonMap json = super.toJson();

        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());

        if (model != null) {
            json.add(JSON_MODEL, model.toJson());
        }

        return json;
    }

}
