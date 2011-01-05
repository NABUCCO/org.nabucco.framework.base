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

import java.util.HashMap;

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

    private HashMap<String, Object> contextMap;

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
        this.contextMap = new HashMap<String, Object>();
    }

    @Override
    public InvocationIdentifier getInvocationIdentifier() {
        return invocationIdentifier;
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public Object get(String contextKey) {
        return this.contextMap.get(contextKey);
    }

    @Override
    public void put(String contextKey, Object contextObject) {
        this.contextMap.put(contextKey, contextObject);
    }
}
