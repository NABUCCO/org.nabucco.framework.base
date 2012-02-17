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

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifierFactory;

/**
 * ServiceContextFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ServiceContextFactory {

    /** System User ID */
    private static final String SYSTEM_USER = "System";

    /** System Owner */
    private static final String SYSTEM_OWNER = "nabucco";

    /** Singleton Instance */
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
     * Creates a system subject that can be used for unsecured service operations.
     * 
     * @return the system subject
     */
    public Subject systemSubject() {
        Subject subject = new Subject();
        subject.setDatatypeState(DatatypeState.TRANSIENT);
        subject.setLoginTime(NabuccoSystem.getCurrentTimeMillis());
        subject.setTenant(new Tenant());
        subject.setOwner(SYSTEM_OWNER);
        subject.setUserId(SYSTEM_USER);
        subject.setToken(new byte[0]);

        User user = new User();
        user.setDatatypeState(DatatypeState.TRANSIENT);
        user.setTenant(new Tenant());
        user.setUsername(SYSTEM_USER);
        user.setDescription(SYSTEM_USER);
        user.setOwner(SYSTEM_OWNER);
        subject.setUser(user);

        return subject;
    }

    /**
     * Creates a new ServiceMessageContext implementation instance using an unauthorized subject and
     * a new invocation identifier.
     * 
     * @return the context
     */
    public ServiceMessageContext newServiceMessageContext() {
        return newServiceMessageContext(null, null);
    }

    /**
     * Creates a new ServiceMessageContext implementation instance using an existing subject.
     * 
     * @param subject
     *            the authentication subject
     * 
     * @return the context
     */
    public ServiceMessageContext newServiceMessageContext(Subject subject) {
        return newServiceMessageContext(null, subject);
    }

    /**
     * Creates a new ServiceMessageContext implementation instance using an existing invocation
     * identifier.
     * 
     * @param identifier
     *            the invocation identifier
     * 
     * @return the context
     */
    public ServiceMessageContext newServiceMessageContext(InvocationIdentifier identifier) {
        return this.newServiceMessageContext(identifier, null);
    }

    /**
     * Creates a new ServiceMessageContext implementation instance using an existing invocation
     * identifier and subject.
     * 
     * @param identifier
     *            the invocation identifier
     * @param subject
     *            the authentication subject
     * 
     * @return the context
     */
    public ServiceMessageContext newServiceMessageContext(InvocationIdentifier identifier, Subject subject) {
        if (identifier == null) {
            identifier = InvocationIdentifierFactory.getInstance().createInvocationIdentifier();
        }
        if (subject == null) {
            subject = this.systemSubject();
        }

        return new ServiceMessageContextImpl(identifier, subject);
    }

    /**
     * Clones an existing service context instance with a new invocation identifier.
     * 
     * @param context
     *            the service context to clone
     * 
     * @return the cloned context
     */
    public ServiceContext cloneServiceContext(ServiceContext context) {
        Subject subject = context.getSubject().cloneObject();
        return this.newServiceMessageContext(subject);
    }

    /**
     * Clones an existing service context instance with an existing invocation identifier.
     * 
     * @param context
     *            the service context to clone
     * @param identifier
     *            the new invocation identifier
     * 
     * @return the cloned context
     */
    public ServiceContext cloneServiceContext(ServiceContext context, InvocationIdentifier identifier) {
        Subject subject = context.getSubject().cloneObject();
        return this.newServiceMessageContext(identifier, subject);
    }

}
