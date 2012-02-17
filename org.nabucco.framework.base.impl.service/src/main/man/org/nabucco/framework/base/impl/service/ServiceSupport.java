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
package org.nabucco.framework.base.impl.service;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.Service;

/**
 * ServiceSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceSupport implements Service {

    private static final long serialVersionUID = 1L;

    /** Empty array when no aspects are registerd. */
    protected static final String[] NO_ASPECTS = new String[0];

    /** The Logger */
    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(this.getClass());

    /**
     * Called after service construction.
     */
    public void postConstruct() {
        if (logger.isDebugEnabled()) {
            logger.debug("Constructing service ", this.getName(), ".");
        }
    }

    /**
     * Called before service destruction.
     */
    public void preDestroy() {
        if (logger.isDebugEnabled()) {
            logger.debug("Destroying service ", this.getName(), ".");
        }
    }

    @Override
    public final String getName() {
        Class<?>[] interfaces = this.getClass().getInterfaces();
        if (interfaces.length > 0) {
            return interfaces[0].getCanonicalName();
        }

        return null;
    }

    @Override
    public String[] getAspects(String operationName) {
        return NO_ASPECTS;
    }

    /**
     * Getter for the NABUCCO logger.
     * 
     * @return Returns the logger.
     */
    public NabuccoLogger getLogger() {
        return this.logger;
    }

}
