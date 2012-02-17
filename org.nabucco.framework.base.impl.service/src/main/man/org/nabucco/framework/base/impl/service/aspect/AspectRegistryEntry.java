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

import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.PointcutExtension;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class AspectRegistryEntry {

    private PointcutExtension extension;

    /**
     * Creates a new {@link AspectRegistryEntry} instance.
     * 
     * @param extension
     *            the pointcut extension
     */
    public AspectRegistryEntry(PointcutExtension extension) {
        super();
        this.extension = extension;
    }

    /**
     * Getter for the pointcut extension.
     * 
     * @return the extension
     */
    public PointcutExtension getExtension() {
        return extension;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((extension == null) ? 0 : extension.hashCode());
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
        AspectRegistryEntry other = (AspectRegistryEntry) obj;
        if (extension == null) {
            if (other.extension != null)
                return false;
        } else if (!extension.equals(other.extension))
            return false;
        return true;
    }

}
