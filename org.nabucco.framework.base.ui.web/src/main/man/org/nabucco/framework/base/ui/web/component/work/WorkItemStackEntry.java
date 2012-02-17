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
package org.nabucco.framework.base.ui.web.component.work;

/**
 * WorkItemStackEntry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
final class WorkItemStackEntry {

    private final String itemId;

    private String instanceId;

    /**
     * Creates a new {@link WorkItemStackEntry} instance.
     * 
     * @param itemId
     *            the work item id
     * @param instanceId
     *            the work item instance id
     */
    WorkItemStackEntry(String itemId, String instanceId) {
        if (itemId == null) {
            throw new IllegalArgumentException("Cannot create stack entry for item id [null].");
        }
        if (instanceId == null) {
            throw new IllegalArgumentException("Cannot create stack entry for instance id [null].");
        }

        this.itemId = itemId;
        this.instanceId = instanceId;
    }

    /**
     * Getter for the instanceId.
     * 
     * @return Returns the instanceId.
     */
    public String getInstanceId() {
        return this.instanceId;
    }

    /**
     * Setter for the instanceId.
     * 
     * @param instanceId
     *            The instanceId to set.
     */
    void setInstanceId(String instanceId) {
        if (instanceId == null) {
            throw new IllegalArgumentException("Cannot set stack entry instance id [null].");
        }
        this.instanceId = instanceId;
    }

    /**
     * Getter for the itemId.
     * 
     * @return Returns the itemId.
     */
    public String getItemId() {
        return this.itemId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.instanceId == null) ? 0 : this.instanceId.hashCode());
        result = prime * result + ((this.itemId == null) ? 0 : this.itemId.hashCode());
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
        WorkItemStackEntry other = (WorkItemStackEntry) obj;
        if (this.instanceId == null) {
            if (other.instanceId != null)
                return false;
        } else if (!this.instanceId.equals(other.instanceId))
            return false;
        if (this.itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!this.itemId.equals(other.itemId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.instanceId + " [" + this.itemId + "]";
    }

}
