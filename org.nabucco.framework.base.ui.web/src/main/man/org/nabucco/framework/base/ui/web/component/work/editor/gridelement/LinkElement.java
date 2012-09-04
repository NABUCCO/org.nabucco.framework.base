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
package org.nabucco.framework.base.ui.web.component.work.editor.gridelement;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.LinkControlExtension;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorGridElement;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.EditorGridElementModel;
import org.nabucco.framework.base.ui.web.model.editor.gridelement.LinkElementModel;
import org.nabucco.framework.base.ui.web.model.editor.gridelement.LinkElementType;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;

/**
 * LinkControl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class LinkElement extends EditorGridElement {

    private static final long serialVersionUID = 1L;

    private LinkControlExtension extension;

    private LinkElementModel model;

    /**
     * Creates a new {@link CodeControl} instance.
     * 
     * @param extension
     */
    public LinkElement(LinkControlExtension extension) {
        super(WebElementType.LINK, extension);

        if (extension == null) {
            throw new IllegalArgumentException("Cannot create editor control for extension 'null'.");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        DependencyController dependencyController = new DependencyController(extension.getDependencySet());
        dependencyController.init();

        model = new LinkElementModel(this.getId(), dependencyController, this.getLabel(), this.getTooltip(),
                this.getIcon(), this.getUrl(), this.getAction(), this.getLinkType());

        model.init();
    }

    @Override
    public EditorGridElementModel getGridElementModel() {
        return model;
    }

    /**
     * Getter for the link action.
     * 
     * @return the link action
     */
    public String getAction() {
        return PropertyLoader.loadProperty(extension.getAction());
    }

    /*
     * Getter for the link action.
     * 
     * @return the link action
     */
    public String getUrl() {
        return PropertyLoader.loadProperty(extension.getUrl());
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
     * Returns the link type (INTERNAL or EXTERNAL)
     * 
     * @return the link type
     */
    public LinkElementType getLinkType() {
        return PropertyLoader.loadProperty(LinkElementType.class, extension.getLinkType());
    }

    /**
     * Getter for the editor control tooltip.
     * 
     * @return the editor control tooltip
     */
    public String getIcon() {
        return PropertyLoader.loadProperty(extension.getIcon());
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
        json.add(JSON_ID, this.getId());

        // The Link element has no label => the label is the link element itself.
        // That is why the label and tooltip should be transfered in the model
        json.add(JSON_MODEL, model.toJson());

        return json;
    }

}
