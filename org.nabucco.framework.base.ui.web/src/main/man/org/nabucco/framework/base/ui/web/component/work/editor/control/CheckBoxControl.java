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

import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.CheckBoxControlExtension;
import org.nabucco.framework.base.ui.web.model.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.control.property.input.CheckBoxControlModel;
import org.nabucco.framework.base.ui.web.model.control.util.validator.NabuccoBooleanValidator;

/**
 * CheckBoxControl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CheckBoxControl extends EditorControl {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link CodeControl} instance.
     * 
     * @param extension
     */
    public CheckBoxControl(CheckBoxControlExtension extension) {
        super(extension);

    }

    @Override
    public ControlModel<?> instantiateModel(String id, String propertyPath) {
        NabuccoBooleanValidator validator = new NabuccoBooleanValidator(this);
        return new CheckBoxControlModel(id, propertyPath, validator, super.getDependencySet(), this.getEditable());
    }
}
