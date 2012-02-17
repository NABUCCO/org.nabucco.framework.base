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

import org.nabucco.framework.base.facade.exception.resource.ResourceException;

/**
 * ResourceManager
 * <p/>
 * Manager for resource adapter connections holding operations for synchronizing
 * {@link NabuccoDatatype} instances with external systems.
 * 
 * @see javax.resource.spi.ResourceAdapter
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface ResourceManager {

    /**
     * Establish the connection to resource with the given JNDI name. The resource must be deployed
     * in the java: JNDI namespace.
     * 
     * @param jndi
     *            the jndi name (without java: prefix)
     * 
     * @return the resource connection
     * 
     * @throws ResourceException
     *             when the connection factory cannot be found in JNDI
     */
    <C extends ResourceConnection> C getConnection(String jndi) throws ResourceException;

}
