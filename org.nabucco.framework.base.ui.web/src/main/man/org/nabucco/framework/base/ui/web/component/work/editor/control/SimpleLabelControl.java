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
package org.nabucco.framework.base.ui.web.component.work.editor.control;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.LabelControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorGridElement;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.EditorGridElementModel;
import org.nabucco.framework.base.ui.web.model.editor.control.property.util.SimpleLabelControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;

/**
 * SimpleLabelControl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SimpleLabelControl extends EditorGridElement {

    private static final long serialVersionUID = 1L;

    /** The Editor Field Extension */
    private EditorControlExtension extension;

    /** The control dependency Set */
    protected DependencyController dependencySet;

    private EditorGridElementModel model;

    /**
     * 
     * Creates a new {@link SimpleLabelControl} instance.
     * 
     * @param extension
     *            the simple label extension
     */
    public SimpleLabelControl(LabelControlExtension extension) {
        super(WebElementType.LABEL, extension);

        this.extension = extension;
    }

    @Override
    public EditorGridElementModel getGridElementModel() {
        return model;
    }

    @Override
    public void init() throws ExtensionException {
        String id = this.getId();

        if (id == null) {
            throw new IllegalArgumentException(
                    "Error by instanciation of the control. The id is not set and is 'null'.");
        }

        DependencySetExtension dependencySetExtension = extension.getDependencySet();
        dependencySet = new DependencyController(dependencySetExtension);
        dependencySet.init();
        model = new SimpleLabelControlModel(id, dependencySet);
        model.init();
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
