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
package org.nabucco.framework.base.ui.web.model.control.util.validator;

import org.nabucco.framework.base.ui.web.component.WebComponent;

/**
 * NabuccoAbstractValidator
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class NabuccoAbstractValidator<N> implements NabuccoValidator<N> {

    private WebComponent component;

    /**
     * 
     * Creates a new {@link NabuccoAbstractValidator} instance.
     * 
     * @param component
     */
    public NabuccoAbstractValidator(WebComponent component) {
        this.component = component;
    }

    @Override
    public boolean validateElement(N input) {
        return false;
    }

    /**
     * Getter for the component.
     * 
     * @return Returns the component.
     */
    protected WebComponent getComponent() {
        return this.component;
    }

    /**
     * Setter for the component.
     * 
     * @param component
     *            The component to set.
     */
    protected void setComponent(WebComponent component) {
        this.component = component;
    }
}
