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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.DropDownColumnExtension;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.model.bulkeditor.BulkEditorModel;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.DropDownControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoEnumerationValidator;

/**
 * BulkEditorDropDownItem
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BulkEditorDropDownColumn extends BulkEditorColumn {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link BulkEditorDropDownColumn} instance.
     * 
     * @param extension
     *            the extension to use
     */
    public BulkEditorDropDownColumn(DropDownColumnExtension extension, BulkEditorModel editorModel) {
        super(extension, editorModel);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ControlModel<?> instantiateModel(String id, String propertyPath) throws ExtensionException {
        NabuccoEnumerationValidator validator = new NabuccoEnumerationValidator(this);

        return new DropDownControlModel(id, propertyPath, validator, super.getDependencySet(), this.isEditable());
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

}
