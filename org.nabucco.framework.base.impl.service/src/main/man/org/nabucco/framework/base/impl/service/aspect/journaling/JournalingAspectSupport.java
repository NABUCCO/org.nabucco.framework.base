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
package org.nabucco.framework.base.impl.service.aspect.journaling;

import javax.naming.InitialContext;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.service.journal.JournalService;
import org.nabucco.framework.base.facade.service.journal.JournalServiceDefaultImpl;
import org.nabucco.framework.base.facade.service.journal.JournalServiceLocator;

/**
 * JournalingAspectSupport
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public abstract class JournalingAspectSupport {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            JournalingAspectSupport.class);

    private ServiceMessageContext context;

    /**
     * Setter for the service context.
     * 
     * @param context
     *            the service context
     */
    void setServiceContext(ServiceMessageContext context) {
        this.context = context;
    }

    /**
     * Getter for the service context.
     * 
     * @return the service context
     */
    public ServiceMessageContext getServiceContext() {
        return this.context;
    }

    /**
     * Getter for the crosscuttin journal service.
     * 
     * @return the journal service from JNDI
     */
    public JournalService getJournalService() {

        try {
            JournalServiceLocator locator = new JournalServiceLocator();
            JournalService journaling = locator.getJournalService(new InitialContext());

            return journaling;

        } catch (Exception e) {
            logger.error(e, "Cannot journal datatype");
            return new JournalServiceDefaultImpl();
        }
    }

}
