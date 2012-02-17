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
package org.nabucco.framework.base.impl.component.adapter.registry;

import org.nabucco.framework.base.facade.message.ping.PingStatus;

/**
 * AdapterRegistryEntry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AdapterRegistryEntry {

    private final String name;

    private final String adapterJndi;

    private final String resourceJndi;

    private PingStatus status;

    private long lastTime;

    private long lastDuration;

    /**
     * Creates a new {@link AdapterRegistryEntry} instance.
     * 
     * @param name
     *            the fully qualified adapter interface name
     * @param adapterJndi
     *            the adapter jndi name
     * @param resourceJndi
     *            the jndi name of the resource
     */
    AdapterRegistryEntry(String name, String adapterJndi, String resourceJndi) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create registry entry for adapter name [null].");
        }
        if (adapterJndi == null) {
            throw new IllegalArgumentException("Cannot create registry entry for adapter JNDI [null].");
        }

        this.name = name;
        this.adapterJndi = adapterJndi;
        this.resourceJndi = resourceJndi;
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the adapterJndi.
     * 
     * @return Returns the adapterJndi.
     */
    public String getAdapterJndi() {
        return this.adapterJndi;
    }

    /**
     * Getter for the jndiName of the adapter resource.
     * 
     * @return Returns the jndiName.
     */
    public String getResourceJndi() {
        return this.resourceJndi;
    }

    /**
     * Getter for the status.
     * 
     * @return Returns the status.
     */
    public PingStatus getStatus() {
        return this.status;
    }

    /**
     * Setter for the status.
     * 
     * @param status
     *            The status to set.
     */
    public void setStatus(PingStatus status) {
        this.status = status;
    }

    /**
     * Getter for the lastTime.
     * 
     * @return Returns the lastTime.
     */
    public long getLastTime() {
        return this.lastTime;
    }

    /**
     * Setter for the lastTime.
     * 
     * @param lastTime
     *            The lastTime to set.
     */
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * Getter for the lastDuration.
     * 
     * @return Returns the lastDuration.
     */
    public long getLastDuration() {
        return this.lastDuration;
    }

    /**
     * Setter for the lastDuration.
     * 
     * @param lastDuration
     *            The lastDuration to set.
     */
    public void setLastDuration(long lastDuration) {
        this.lastDuration = lastDuration;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder();
        result.append(this.name);
        result.append(" (");
        result.append(this.resourceJndi);
        result.append(")");

        return result.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.resourceJndi == null) ? 0 : this.resourceJndi.hashCode());
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
        AdapterRegistryEntry other = (AdapterRegistryEntry) obj;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        if (this.resourceJndi == null) {
            if (other.resourceJndi != null)
                return false;
        } else if (!this.resourceJndi.equals(other.resourceJndi))
            return false;
        return true;
    }

}
