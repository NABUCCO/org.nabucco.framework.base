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
package org.nabucco.framework.base.ui.web.model.common.renderer;

import org.nabucco.framework.base.facade.datatype.Datatype;

/**
 * Provides labels of datatypes.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class WebLabelProvider<T extends Datatype> {

    /** Constant for unconfigured labels. */
    protected static final String DEFAULT_LABEL = "";

    /**
     * Creates a new {@link WebLabelProvider} instance.
     */
    public WebLabelProvider() {
        super();
    }

    /**
     * Convert the property path into a readable label.
     * 
     * @param propertyPath
     *            the property path pointing to the property of the given datatype. A property path
     *            may be seperated by a point <b>"."</b> to reference a sub-property.
     * 
     *            <p/>
     *            <b>Examples:</b>
     *            <li><code>description</code></li>
     *            <li><code>user.username</code></li>
     * 
     * 
     * @return the referenced label or an empty string
     */
    public abstract String getLabel(String propertyPath, WebRenderer renderer);

    /**
     * Convert the property given message into a readable label.
     * 
     * @param message
     *            the massage to be resolved
     * @param renderer
     *            the renderer to use to render the output
     */
    public abstract String getLabelFromMessage(String message, WebRenderer renderer);

}
