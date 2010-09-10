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
package org.nabucco.framework.base.ui.web.communication;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifierFactory;

/**
 * ServiceDelegateSupport
 * <p/>
 * Supporting super-class of all service delegates. Provides service context initialization and
 * manipulation.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceDelegateSupport {

    /** The subject session name. */
    private static final String NABUCCO_SUBJECT = "NABUCCO_SUBJECT";

    /**
     * Creates a new service context for the current user.
     * 
     * @return the service context
     */
    protected ServiceMessageContext createServiceContext() {

        InvocationIdentifier identifier = InvocationIdentifierFactory.getInstance()
                .createInvocationIdentifier();

        Subject subject = this.retrieveSubject();

        ServiceMessageContext context = ServiceContextFactory.getInstance()
                .newServiceMessageContext(identifier, subject);

        return context;
    }

    /**
     * Creates a new service context for the current user (identified by the subject). If the
     * subject is null the subject is retrieved from HTTP Session.
     * 
     * @param the
     *            current security subject
     * 
     * @return the service context
     */
    protected ServiceMessageContext createServiceContext(Subject subject) {

        InvocationIdentifier identifier = InvocationIdentifierFactory.getInstance()
                .createInvocationIdentifier();

        if (subject == null) {
            subject = this.retrieveSubject();
        }

        ServiceMessageContext context = ServiceContextFactory.getInstance()
                .newServiceMessageContext(identifier, subject);

        return context;
    }

    /**
     * Retrieves the NABUCCO subject from the HTTP session.
     * 
     * @return the authorization subject
     */
    private Subject retrieveSubject() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context == null || context.getExternalContext() == null) {
            throw new IllegalStateException("Cannot create a connection without a FacesContext.");
        }

        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        if (session == null) {
            throw new IllegalStateException("Cannot create a connection without an HTTP session.");
        }

        return (Subject) session.getAttribute(NABUCCO_SUBJECT);
    }

}
