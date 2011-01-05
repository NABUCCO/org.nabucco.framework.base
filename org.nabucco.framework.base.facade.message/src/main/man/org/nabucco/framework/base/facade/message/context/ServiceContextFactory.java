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

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifierFactory;

/**
 * ServiceContextFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ServiceContextFactory {

    private static ServiceContextFactory instance;

    /**
     * Private constructor must not be invoked.
     */
    private ServiceContextFactory() {
    }

    /**
     * Getter for the factory instance.
     * 
     * @return the singleton instance.
     */
    public static synchronized ServiceContextFactory getInstance() {
        if (instance == null) {
            instance = new ServiceContextFactory();
        }

        return instance;
    }

    /**
     * Creates a new ServiceMessageContext implementation instance.
     * 
     * @param subject
     *            the authentication subject
     * 
     * @return the context
     */
    public ServiceMessageContext newServiceMessageContext(Subject subject) {

        InvocationIdentifier identifier = InvocationIdentifierFactory.getInstance()
                .createInvocationIdentifier();

        return new ServiceMessageContextImpl(identifier, subject);
    }

    /**
     * Creates a new ServiceMessageContext implementation instance using an existing invocation
     * identifier.
     * 
     * @param identifier
     *            the invocation identifier
     * @param subject
     *            the authentication subject
     * 
     * @return the context
     */
    public ServiceMessageContext newServiceMessageContext(InvocationIdentifier identifier,
            Subject subject) {
        return new ServiceMessageContextImpl(identifier, subject);
    }

    /**
     * Clones a service context instance.
     * 
     * @param context
     *            the service context to clone
     * 
     * @return the cloned context
     */
    public ServiceContext cloneServiceContext(ServiceContext context) {

        // TODO: Clone ServiceContext

        return context;
    }

}
