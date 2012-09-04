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

import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.FileControlExtension;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.property.file.FileControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoFileValidator;

/**
 * File Control
 * 
 * Element to upload files to server
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class FileControl extends EditorControl {

    private static final String DEFAULT_ATTACHMENTS_PATH = "org/nabucco/attachments";

    private static final long serialVersionUID = 1L;

    private static final String JSON_EXTENSION_FILTER = "extensionFilter";

    /**
     * Creates a new {@link CodeControl} instance.
     * 
     * @param extension
     */
    public FileControl(FileControlExtension extension) {
        super(extension);

    }

    /**
     * Getter for the link action.
     * 
     * @return the configured upload path or default path if not defined
     */
    public String getUploadPath() {
        String configuredPath = PropertyLoader.loadProperty(((FileControlExtension) this.getExtension())
                .getUploadPath());

        if (configuredPath != null) {
            return configuredPath;
        } else {
            return DEFAULT_ATTACHMENTS_PATH;
        }

    }

    /**
     * Returns the configured extension filter
     * 
     * @return extension filter as string or null if not defined
     */
    public String getExtensionFilter() {
        return PropertyLoader.loadProperty(((FileControlExtension) this.getExtension()).getExtensionFilter());
    }

    @Override
    public ControlModel<?> instantiateModel(String id, String propertyPath) {
        NabuccoFileValidator validator = new NabuccoFileValidator(this);
        return new FileControlModel(id, propertyPath, validator, super.getDependencySet(), this.isEditable(),
                this.getUploadPath());
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

        json.add(JSON_EXTENSION_FILTER, this.getExtensionFilter());
        return json;
    }

}
