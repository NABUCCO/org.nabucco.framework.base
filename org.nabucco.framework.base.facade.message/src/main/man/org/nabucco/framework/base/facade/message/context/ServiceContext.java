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
package org.nabucco.framework.base.facade.message.context;

import java.io.Serializable;

import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;

/**
 * Context for service interactions.
 * 
 * @author Nicolas Moser, PRODYNA AG
 * @author Silas Schwarz, PRODYNA AG
 */
public interface ServiceContext extends Serializable {

    /**
     * Getter for the invocation identifier.
     * 
     * @return the invocation identifier
     */
    InvocationIdentifier getInvocationIdentifier();

    /**
     * Getter for the user ID of the current transaction.
     * 
     * @return the user id of the current user, or null if no user is authenticated
     */
    String getUserId();

    /**
     * Getter for the owner of the current transaction.
     * 
     * @return the owner of the current user, or null if no user is authenticated
     */
    String getOwner();

    /**
     * Getter for the tenant of the current transaction.
     * 
     * @return the tenant of the current user, or null if no user is authenticated
     */
    String getTenant();

    /**
     * Getter for the subject object.
     * 
     * @return the subject
     */
    Subject getSubject();

    /**
     * Adds a context object to the map.
     * 
     * @param subType
     *            key identifing the subtype
     * @param context
     *            the context object
     */
    void put(ServiceSubContextType subType, ServiceSubContext context);

    /**
     * Returns the context object with the specified key.
     * 
     * @param subType
     *            identifier for the subType
     * 
     * @return containted subType if any
     */
    ServiceSubContext get(ServiceSubContextType subType);

    /**
     * Remove the context object for the specified key from the map.
     * 
     * @param subType
     *            key identifing the subtype
     * 
     * @return the removed sub context
     */
    ServiceSubContext remove(ServiceSubContextType subType);

}
