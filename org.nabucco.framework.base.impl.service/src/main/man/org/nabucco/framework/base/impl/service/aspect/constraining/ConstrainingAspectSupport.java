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
package org.nabucco.framework.base.impl.service.aspect.constraining;

import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * Support for the constraining aspect
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ConstrainingAspectSupport {

    private ServiceMessageContext context;

    /**
     * Setter for the service context.
     * 
     * @param context
     *            the service context to set
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
}
