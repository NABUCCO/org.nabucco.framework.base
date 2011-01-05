/*
 * Copyright 2010 PRODYNA AG
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
package org.nabucco.framework.base.facade.message.context;

import java.io.Serializable;

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;

/**
 * ServiceContext
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface ServiceContext extends Serializable {

    /**
     * Getter for the invocation identifier.
     * 
     * @return the invocation identifier
     */
    InvocationIdentifier getInvocationIdentifier();

    /**
     * Getter for the subject object.
     * 
     * @return the subject
     */
    Subject getSubject();

    /**
     * Adds a context object to the map.
     * 
     * @param contextKey
     *            key of the context object
     * @param contextObject
     *            the context object
     */
    void put(String contextKey, Object contextObject);

    /**
     * Returns the context object with the specified key.
     * 
     * @param contextKey
     *            the context key
     * 
     * @return the appropriate context objectF
     */
    Object get(String contextKey);

}
