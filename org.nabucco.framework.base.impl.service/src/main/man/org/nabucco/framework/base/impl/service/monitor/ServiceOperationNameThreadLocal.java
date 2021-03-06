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
package org.nabucco.framework.base.impl.service.monitor;

/**
 * ServiceOperationNameThreadLocal
 * <p/>
 * Utility class for accessing the operation name by thread-local variables.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ServiceOperationNameThreadLocal {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

    /**
     * Private constructor must not be invoked.
     */
    private ServiceOperationNameThreadLocal() {
    }

    /**
     * Sets the operation name to the thread-local variables.
     * 
     * @param operationName
     *            the operation name to set into the thread-local variables
     */
    public static final void setOperationName(String operationName) {
        THREAD_LOCAL.set(operationName);
    }

    /**
     * Gets the operation name from the thread-local variables.
     * 
     * @return the operation name to from the thread-local variables
     */
    public static final String getOperationName() {
        return THREAD_LOCAL.get();
    }

}
