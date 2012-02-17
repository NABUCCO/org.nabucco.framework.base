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
package org.nabucco.framework.base.impl.service.aspect;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class AspectMap {

    private Map<String, AspectHandler> handlerMapping;

    private static AspectMap instance;

    /**
     * Creates a new {@link AspectMap} instance.
     */
    private AspectMap() {
        handlerMapping = new HashMap<String, AspectHandler>();
    }

    /**
     * Getter for the singleton instance.
     * 
     * @return the instance
     */
    public static synchronized AspectMap getInstance() {
        if (instance == null) {
            instance = new AspectMap();
        }
        return instance;
    }

    /**
     * Add an aspect handler into the registry
     * 
     * @param serviceKey
     *            the service key
     * @param handler
     *            the aspect handler
     */
    public void put(String serviceKey, AspectHandler handler) {
        this.handlerMapping.put(serviceKey, handler);
    }

    /**
     * Check whether a handler is alrady mapped.
     * 
     * @param serviceKey
     *            the service key to check
     * 
     * @return <b>true</b> if an aspect handler is registered, <b>false</b> if not
     */
    public boolean hasMappedHandler(String serviceKey) {
        return this.handlerMapping.containsKey(serviceKey);
    }

    /**
     * Getter for the aspect handler for the given service key.
     * 
     * @param serviceKey
     *            the service key
     * 
     * @return the aspect handler
     */
    public AspectHandler getHandler(String serviceKey) {
        return this.handlerMapping.get(serviceKey);
    }
}
