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

import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AdviceType;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;

/**
 * AspectHandlerEntry
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class AspectHandlerEntry {

    private AdviceType advice;

    private Class<? extends Aspect> aspectClass;

    private Class<? extends AspectExecutor> aspectExecutorClass;

    private PropertyExtension aspectProperties;

    /**
     * Creates a new {@link AspectHandlerEntry} instance.
     * 
     * @param aspectClass
     *            the aspect class
     * @param aspectExecutorClass
     *            the aspect executor class
     * @param type
     *            the advice
     * @param props
     *            the extension properties
     */
    public AspectHandlerEntry(Class<? extends Aspect> aspectClass, Class<? extends AspectExecutor> aspectExecutorClass,
            AdviceType type, PropertyExtension props) {
        super();
        this.aspectClass = aspectClass;
        this.aspectExecutorClass = aspectExecutorClass;
        this.advice = type;
        this.aspectProperties = props;
    }

    /**
     * Getter for the advice.
     * 
     * @return the advice type
     */
    public AdviceType getAdvice() {
        return advice;
    }

    /**
     * Getter for the aspect class.
     * 
     * @return the aspect class
     */
    public Class<? extends Aspect> getAspectClass() {
        return aspectClass;
    }

    /**
     * Getter for the aspect exector class.
     * 
     * @return the executor class
     */
    public Class<? extends AspectExecutor> getAspectExecutorClass() {
        return aspectExecutorClass;
    }

    /**
     * Getter for the aspect properties.
     * 
     * @return the properties
     */
    public PropertyExtension getAspectProperties() {
        return aspectProperties;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((advice == null) ? 0 : advice.hashCode());
        result = prime * result + ((aspectClass == null) ? 0 : aspectClass.hashCode());
        result = prime * result + ((aspectExecutorClass == null) ? 0 : aspectExecutorClass.hashCode());
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
        AspectHandlerEntry other = (AspectHandlerEntry) obj;
        if (advice != other.advice)
            return false;
        if (aspectClass == null) {
            if (other.aspectClass != null)
                return false;
        } else if (!aspectClass.equals(other.aspectClass))
            return false;
        if (aspectExecutorClass == null) {
            if (other.aspectExecutorClass != null)
                return false;
        } else if (!aspectExecutorClass.equals(other.aspectExecutorClass))
            return false;
        return true;
    }

}
