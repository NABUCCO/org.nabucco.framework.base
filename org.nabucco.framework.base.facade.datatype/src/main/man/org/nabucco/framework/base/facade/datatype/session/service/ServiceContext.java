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
package org.nabucco.framework.base.facade.datatype.session.service;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;

/**
 * Facade for service contexts.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ServiceContext {

    /** Service Sub Contexts send to Server */
    private Map<ServiceSubContextType, ServiceSubContext> requestMap = new HashMap<ServiceSubContextType, ServiceSubContext>();

    /** Service Sub Contexts received from Server */
    private Map<ServiceSubContextType, ServiceSubContext> responseMap = new HashMap<ServiceSubContextType, ServiceSubContext>();

    /**
     * Add a sub context to the session.
     * 
     * @param context
     *            the context to add
     */
    public void putRequestContext(ServiceSubContext context) {
        if (context != null) {
            this.requestMap.put(context.getContextType(), context);
        }
    }

    /**
     * Getter for the sub service context type with the given id.
     * 
     * @param type
     *            the context type
     * 
     * @return the context, or null if it does not exist
     */
    public ServiceSubContext getRequestContext(ServiceSubContextType type) {
        return this.requestMap.get(type);
    }

    /**
     * Add a sub context to the session.
     * 
     * @param context
     *            the context to add
     */
    public void putResponseContext(ServiceSubContext context) {
        if (context != null) {
            this.responseMap.put(context.getContextType(), context);
        }
    }

    /**
     * Getter for the sub service context type with the given id.
     * 
     * @param type
     *            the context type
     * 
     * @return the context, or null if it does not exist
     */
    public ServiceSubContext getResponseContext(ServiceSubContextType type) {
        return this.responseMap.get(type);
    }

    /**
     * Removes all response sub contexts.
     */
    public void clear() {
        this.requestMap.clear();
        this.responseMap.clear();
    }
}
