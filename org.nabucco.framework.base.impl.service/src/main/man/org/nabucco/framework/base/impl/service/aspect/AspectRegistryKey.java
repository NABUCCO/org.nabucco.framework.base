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

/**
 * Key for registering pointcuts.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class AspectRegistryKey {

    private String key;

    private String pointcut;

    private String advice;

    /**
     * Creates a new {@link AspectRegistryKey} instance.
     * 
     * @param key
     *            the service key
     * @param pointcut
     *            the pointcut
     * @param advice
     *            the advice (before, after, around)
     */
    public AspectRegistryKey(String key, String pointcut, String advice) {
        super();
        this.key = key;
        this.pointcut = pointcut;
        this.advice = advice;
    }

    /**
     * Getter for the service key.
     * 
     * @return the service key
     */
    public String getKey() {
        return key;
    }

    /**
     * Getter for the pointcut.
     * 
     * @return the pointcut
     */
    public String getPointcut() {
        return pointcut;
    }

    /**
     * Getter for the invocation advice.
     * 
     * @return the advice
     */
    public String getAdvice() {
        return advice;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((advice == null) ? 0 : advice.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((pointcut == null) ? 0 : pointcut.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AspectRegistryKey other = (AspectRegistryKey) obj;
        if (advice == null) {
            if (other.advice != null)
                return false;
        } else if (!advice.equals(other.advice))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (pointcut == null) {
            if (other.pointcut != null)
                return false;
        } else if (!pointcut.equals(other.pointcut))
            return false;
        return true;
    }

}
