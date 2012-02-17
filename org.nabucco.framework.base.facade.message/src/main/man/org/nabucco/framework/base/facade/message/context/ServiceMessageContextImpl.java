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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;

/**
 * ServiceMessageContextImpl
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
final class ServiceMessageContextImpl implements ServiceMessageContext {

    private static final long serialVersionUID = 1L;

    private InvocationIdentifier invocationIdentifier;

    private Subject subject;

    private Map<ServiceSubContextType, ServiceSubContext> subContextMap;

    /**
     * Creates a new {@link ServiceMessageContextImpl} instance.
     * 
     * @param identifier
     *            the invocation identifier.
     * @param subject
     *            the logged-in user.
     */
    public ServiceMessageContextImpl(InvocationIdentifier identifier, Subject subject) {
        if (identifier == null) {
            throw new IllegalArgumentException("Invocation Identifier must be configured.");
        }
        this.subject = subject;
        this.invocationIdentifier = identifier;
        this.subContextMap = new HashMap<ServiceSubContextType, ServiceSubContext>();

    }

    @Override
    public InvocationIdentifier getInvocationIdentifier() {
        return invocationIdentifier;
    }

    @Override
    public String getUserId() {
        if (this.subject == null) {
            return null;
        }
        if (this.subject.getUserId() == null) {
            return null;
        }
        return this.subject.getUserId().getValue();
    }

    @Override
    public String getOwner() {
        if (this.subject == null) {
            return null;
        }
        if (this.subject.getOwner() == null) {
            return null;
        }
        return this.subject.getOwner().getValue();
    }

    @Override
    public String getTenant() {
        if (this.subject == null) {
            return null;
        }
        if (this.subject.getTenant() == null) {
            return null;
        }
        return this.subject.getTenant().getValue();
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public void put(ServiceSubContextType subType, ServiceSubContext context) {
        this.subContextMap.put(subType, context);

    }

    @Override
    public ServiceSubContext get(ServiceSubContextType subType) {
        return this.subContextMap.get(subType);
    }

    @Override
    public ServiceSubContext remove(ServiceSubContextType subType) {
        return this.subContextMap.remove(subType);
    }
}
