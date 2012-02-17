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
package org.nabucco.framework.base.ui.portlet.session;

import javax.portlet.PortletSession;

/**
 * NabuccoSessionFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoPortletSessionFactory {

    /** The subject session name. */
    private static final String NABUCCO_SESSION = "NABUCCO_SESSION";

    /**
     * Private constructor must not be invoked.
     */
    private NabuccoPortletSessionFactory() {
    }

    /**
     * Create the {@link NabuccoSession} instance
     * 
     * @param session
     *            the Portlet session holding the NabuccoSession
     * 
     * @return the {@link NabuccoSession} instance
     */
    public static NabuccoPortletSession createSession(PortletSession session) {
        if (session == null) {
            throw new IllegalStateException("Cannot create a connection without a Portlet session.");
        }

        Object nbcSession = session.getAttribute(NABUCCO_SESSION);

        if (nbcSession != null) {
            if (!(nbcSession instanceof NabuccoPortletSession)) {
                throw new IllegalStateException("HTTP session does not contain a valid NabuccoSession.");
            }
        } else {
            nbcSession = new NabuccoPortletSession();
            session.setAttribute(NABUCCO_SESSION, nbcSession);
        }

        return (NabuccoPortletSession) nbcSession;
    }
}
