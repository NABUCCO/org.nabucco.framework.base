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

import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;

/**
 * ResourceControllerEntry
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ResourceControllerEntry {

    Long id;

    ContentEntryType type;

    /**
     * Creates a new Resource controller entry instance
     * 
     * @param id
     *            the id of resource
     * @param type
     *            the type of the resorce
     */
    public ResourceControllerEntry(Long id, ContentEntryType type) {

        if (id == null) {
            throw new IllegalArgumentException("Cannot create a new resorce controller entry with id 'null'");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create a new resorce controller entry with type 'null'");
        }

        this.id = id;
        this.type = type;
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public ContentEntryType getType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
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
        ResourceControllerEntry other = (ResourceControllerEntry) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
}
