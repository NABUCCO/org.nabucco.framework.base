/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype;

/**
 * DatatypeState
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public enum DatatypeState implements Enumeration {

    /**
     * Datatype is created by constructor but not initialized
     */
    CONSTRUCTED("C"),

    /**
     * Datatype is initialized but not persistent
     */
    INITIALIZED("I"),

    /**
     * Datatype is persistent (id != null) and has changes
     */
    MODIFIED("M"),

    /**
     * Datatype is persistent and not modified
     */
    PERSISTENT("P"),

    /**
     * Datatype is persistent (id != null) and has transient changes or not persistent (id == null)
     */
    TRANSIENT("T"),

    /**
     * Datatype is persistent (id != null) and will be deleted by the next maintain operation or not
     * persistent (id == null) deletion will be done without DB operation.
     */
    DELETED("D"),

    /**
     * Datatype was marked as DELETED and has already been removed from database.
     */
    DESTROYED("X");

    /**
     * Private constructor with database ID.
     * 
     * @param id
     *            id of the enum literal
     */
    private DatatypeState(String id) {
        this.id = id;
    }

    private String id;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getOrdinal() {
        return this.getOrdinal();
    }

    @Override
    public DatatypeState cloneObject() {
        return this;
    }
}
