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
package org.nabucco.framework.base.facade.datatype.logger;

/**
 * UserIdThreadLocal
 * <p/>
 * Utility class for accessing the user id by thread-local variables.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public final class UserIdThreadLocal {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();

    /**
     * Private constructor must not be invoked.
     */
    private UserIdThreadLocal() {
    }

    /**
     * Sets the user id to the thread-local variables.
     * 
     * @param userId
     *            the user id to set into the thread-local variables
     */
    public static final void setUserId(String userId) {
        THREAD_LOCAL.set(userId);
    }

    /**
     * Gets the user id from the thread-local variables.
     * 
     * @return the user id to from the thread-local variables
     */
    public static final String getUserId() {
        return THREAD_LOCAL.get();
    }

}
