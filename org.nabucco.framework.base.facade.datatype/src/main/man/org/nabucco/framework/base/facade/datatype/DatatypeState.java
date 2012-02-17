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
package org.nabucco.framework.base.facade.datatype;

import java.util.Collections;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatatypeState
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
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

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        visitor.visit(this);
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return Collections.emptySet();
    }
}
