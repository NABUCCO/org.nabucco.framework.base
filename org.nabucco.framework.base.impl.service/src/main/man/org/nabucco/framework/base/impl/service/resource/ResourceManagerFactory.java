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

import javax.ejb.SessionContext;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;

/**
 * ResourceManagerFactory
 * <p/>
 * Manages the of resource managers instances.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ResourceManagerFactory {

    /**
     * Singleton instance.
     */
    private static ResourceManagerFactory instance = new ResourceManagerFactory();

    /**
     * Private constructor.
     */
    private ResourceManagerFactory() {
    }

    /**
     * Singleton access.
     * 
     * @return the ResourceManagerFactory instance.
     */
    public static ResourceManagerFactory getInstance() {
        return instance;
    }

    /**
     * Create a new instance of the {@link ResourceManager}.
     * 
     * @param context
     *            the current session context
     * @param logger
     *            the nabucco logger instance
     * 
     * @return the resource manager
     */
    public ResourceManager createResourceManager(SessionContext context, NabuccoLogger logger) {
        if (context == null) {
            throw new IllegalArgumentException("Cannot create ResourceManager for context [null].");
        }

        return new ResourceManagerImpl(context, logger);
    }

}
