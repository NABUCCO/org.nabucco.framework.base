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
package org.nabucco.framework.base.ui.web.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;

/**
 * NabuccoSessionFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoWebSessionFactory {

    /** The subject session name. */
    private static final String NABUCCO_SESSION = "NABUCCO_SESSION";

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoWebSessionFactory.class);

    /** The map of current web sessions. */
    private static final Map<Long, NabuccoWebSession> sessionMap = Collections
            .synchronizedMap(new HashMap<Long, NabuccoWebSession>());

    /**
     * Private constructor must not be invoked.
     */
    private NabuccoWebSessionFactory() {
    }

    /**
     * Getter for teh current session instance.
     * 
     * @return the current web session instance
     */
    public static NabuccoWebSession getCurrentSession() {
        long threadId = Thread.currentThread().getId();

        NabuccoWebSession currentSession = sessionMap.get(threadId);

        if (currentSession == null) {
            throw new IllegalStateException("No Session in Thread [" + threadId + "] registered.");
        }

        return currentSession;
    }

    /**
     * Create the {@link NabuccoSession} instance
     * 
     * @param session
     *            the HTTP session holding the NabuccoSession
     * 
     * @return the {@link NabuccoSession} instance
     */
    public static NabuccoWebSession createSession(HttpSession session) {
        if (session == null) {
            throw new IllegalStateException("Cannot create a connection without an HTTP session.");
        }

        Object nbcSession = session.getAttribute(NABUCCO_SESSION);

        if (nbcSession != null) {
            if (!(nbcSession instanceof NabuccoWebSession)) {
                throw new IllegalStateException("HTTP session does not contain a valid NabuccoSession.");
            }
        } else {
            nbcSession = new NabuccoWebSession();
            session.setAttribute(NABUCCO_SESSION, nbcSession);
        }

        NabuccoWebSession currentSession = (NabuccoWebSession) nbcSession;

        if (!currentSession.isActive()) {
            currentSession = new NabuccoWebSession();
            session.setAttribute(NABUCCO_SESSION, currentSession);
        }

        long threadId = Thread.currentThread().getId();
        sessionMap.put(threadId, currentSession);

        logger.debug("Registered Session in Thread '", threadId, "'.");

        return currentSession;
    }
}
