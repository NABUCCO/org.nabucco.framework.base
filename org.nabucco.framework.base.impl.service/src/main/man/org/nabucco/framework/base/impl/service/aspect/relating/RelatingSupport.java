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
package org.nabucco.framework.base.impl.service.aspect.relating;

import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * RelatingSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class RelatingSupport {

    /** The service context for workflow connection. */
    private ServiceMessageContext context;

    /**
     * Setter for the service message context.
     * 
     * @param context
     *            the context
     */
    public final void setContext(ServiceMessageContext context) {
        this.context = context;
    }

    /**
     * Getter for the context.
     * 
     * @return Returns the context.
     */
    public final ServiceMessageContext getContext() {
        return this.context;
    }

}
