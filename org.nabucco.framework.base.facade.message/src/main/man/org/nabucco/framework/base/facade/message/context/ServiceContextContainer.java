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

import java.io.Serializable;

/**
 * ServiceContextContainer
 * 
 * @param <C>
 *            the context type
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceContextContainer<C extends ServiceContext> implements Serializable {

    private static final long serialVersionUID = 1L;

    private C context;

    /**
     * Creates a new {@link ServiceContextContainer} instance.
     * 
     * @param context
     *            the service context
     */
    public ServiceContextContainer(C context) {
        this.context = context;
    }

    /**
     * Gets the service context of the container.
     * 
     * @return the context
     */
    public C getContext() {
        return context;
    }

}
