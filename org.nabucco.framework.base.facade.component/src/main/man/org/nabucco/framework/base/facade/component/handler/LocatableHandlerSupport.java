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
package org.nabucco.framework.base.facade.component.handler;

import org.nabucco.framework.base.facade.component.locator.Locatable;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * LocatableHandlerSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class LocatableHandlerSupport implements LocatableHandler {

    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The constructed component. */
    private Locatable component;

    /** The NABUCCO logger */
    private NabuccoLogger logger;

    /**
     * Getter for the component.
     * 
     * @return Returns the component.
     */
    protected Locatable getLocatable() {
        return this.component;
    }

    /**
     * Getter for the NABUCCO logger.
     * 
     * @return the NABUCCO logger.
     */
    protected NabuccoLogger getLogger() {
        if (this.logger == null) {
            this.logger = NabuccoLoggingFactory.getInstance().getLogger(this.getClass());
        }
        return this.logger;
    }

    @Override
    public void setLogger(NabuccoLogger logger) {
        this.logger = logger;
    }

    @Override
    public void setLocatable(Locatable component) {
        this.component = component;
    }
}
