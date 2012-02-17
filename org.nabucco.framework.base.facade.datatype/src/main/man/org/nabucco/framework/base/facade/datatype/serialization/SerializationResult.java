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
package org.nabucco.framework.base.facade.datatype.serialization;

import org.nabucco.framework.base.facade.datatype.serialization.resource.SerializationResourceContainer;

/**
 * SerializationResult
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SerializationResult {

    private String content;

    private SerializationResourceContainer container;

    /**
     * Creates a new {@link SerializationResult} instance.
     */
    public SerializationResult() {
    }

    /**
     * Getter for the content.
     * 
     * @return Returns the content.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Setter for the content.
     * 
     * @param content
     *            The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter for the container.
     * 
     * @return Returns the container.
     */
    public SerializationResourceContainer getResourceContainer() {
        if (this.container == null) {
            this.container = new SerializationResourceContainer();
        }
        return this.container;
    }

}
