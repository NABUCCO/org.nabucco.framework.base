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
package org.nabucco.framework.base.ui.web.model.editor.control.property;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionProvider;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionProviderDelegate;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoValidator;

/**
 * PropertyControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class PropertyControlModel<N extends NType> extends ControlModel<N> {


    /** Selection provider provides data from enums and codes */
    private SelectionProvider<N> provider;

    /**
     * 
     * Creates a new {@link PropertyControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            the property path of the control
     * @param type
     *            the control type
     * @param validator
     *            the validator
     * @param dependencyController
     *            the dependency controller
     * @param editable
     *            indicator if the control is editable or not
     */
    public PropertyControlModel(String id, String propertyPath, ControlType type, NabuccoValidator<N> validator,
            DependencyController dependencyController, boolean editable) {
        super(id, propertyPath, type, validator, dependencyController, editable);

        if (propertyPath == null) {
            throw new IllegalArgumentException("Cannot create control model for property path [null].");
        }
    }


    /**
     * Getter for the provider.
     * 
     * @return Returns the provider.
     */
    protected SelectionProvider<N> getSelectionProvider() {
        if (this.provider == null) {
            this.provider = new SelectionProviderDelegate<N>();
        }
        return this.provider;
    }


}
