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
package org.nabucco.framework.base.ui.web.component.common.controller.resource;

import java.util.Arrays;

/**
 * TemproraryResourceEntry
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TemproraryResourceEntry {

    ResourceContentType type;

    byte[] data;

    /**
     * Creates a new Resource controller entry instance
     * 
     * @param id
     *            the id of resource
     * @param type
     *            the type of the resorce
     */
    public TemproraryResourceEntry(ResourceContentType type, byte[] data) {

        if (type == null) {
            throw new IllegalArgumentException("Cannot create a new resorce controller entry with id 'null'");
        }
        if (data == null) {
            throw new IllegalArgumentException("Cannot create a new resorce controller entry with type 'null'");
        }

        this.data = data;
        this.type = type;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public ResourceContentType getType() {
        return type;
    }

    /**
     * Getter for the data.
     * 
     * @return Returns the data.
     */
    public byte[] getData() {
        return data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(data);
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TemproraryResourceEntry other = (TemproraryResourceEntry) obj;
        if (!Arrays.equals(data, other.data)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

}
