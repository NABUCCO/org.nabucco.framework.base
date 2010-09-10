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
package org.nabucco.framework.base.impl.service.handler;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.service.injection.Injectable;

/**
 * ServiceHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface ServiceHandler extends Injectable, Serializable {

    /**
     * Initializes the service handler invocation.
     */
    void init();

    /**
     * Finishs the service handler invocation.
     */
    void finish();

    /**
     * Setter for the service context
     * 
     * @param context
     *            the service context to set
     */
    void setContext(ServiceMessageContext context);

    /**
     * Setter for the entity manager instance.
     * 
     * @param em
     *            the entity manager
     */
    void setEntityManager(EntityManager em);

    /**
     * Setter for the NABUCCO logger instance.
     * 
     * @param logger
     *            the logger
     */
    void setLogger(NabuccoLogger logger);
}
