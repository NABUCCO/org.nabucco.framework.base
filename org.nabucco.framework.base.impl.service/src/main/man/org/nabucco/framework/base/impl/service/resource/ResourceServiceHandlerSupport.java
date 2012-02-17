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
package org.nabucco.framework.base.impl.service.resource;

import org.nabucco.framework.base.impl.service.ServiceHandlerSupport;

/**
 * PersistenceServiceHandlerSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ResourceServiceHandlerSupport extends ServiceHandlerSupport {

    private static final long serialVersionUID = 1L;

    private ResourceManager resourceManager;

    /**
     * Setter for the resource manager instance.
     * 
     * @param resourceManager
     *            the resource manager
     */
    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    /**
     * Getter for the resource manager.
     * 
     * @return the resource manager
     */
    protected ResourceManager getResourceManager() {
        return this.resourceManager;
    }

}
