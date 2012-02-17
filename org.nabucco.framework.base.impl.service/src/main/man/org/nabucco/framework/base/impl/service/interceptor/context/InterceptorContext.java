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
package org.nabucco.framework.base.impl.service.interceptor.context;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * InterceptorStrategyContext
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class InterceptorContext {

    private Map<InterceptorContextType, Object> contextData;

    private boolean shorten;

    private int depth = 0;

    /**
     * Creates a new {@link InterceptorContext} instance.
     */
    public InterceptorContext() {
        this.contextData = Collections.synchronizedMap(new EnumMap<InterceptorContextType, Object>(
                InterceptorContextType.class));
    }

    /**
     * Puts a value into the interceptor context.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void put(InterceptorContextType key, Object value) {
        this.contextData.put(key, value);
    }

    /**
     * Gets a value from the interceptor context.
     * 
     * @param key
     *            the key
     * 
     * @return the value
     */
    public Object get(InterceptorContextType key) {
        return this.contextData.get(key);
    }

    /**
     * Removes a value from the interceptor context and returns it.
     * 
     * @param key
     *            the key
     * 
     * @return the value
     */
    public Object remove(InterceptorContextType key) {
        return this.contextData.remove(key);
    }

    /**
     * Shortens the interceptor chain.
     * 
     * @return <b>true</b> if the service call is shortened, <b>false</b> if not
     */
    public boolean isShorten() {
        return shorten;
    }

    /**
     * Shortens the interceptor chain.
     * 
     * @param shorten
     */
    public void setShorten(boolean shorten) {
        this.shorten = shorten;
    }

    /**
     * Getter for the interceptor chain depth.
     * 
     * @return the current depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Increment the interceptor chain depth by 1.
     */
    public void incrementDepth() {
        this.depth++;
    }
}
