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
package org.nabucco.framework.base.impl.service.entitylistener;

import java.io.Serializable;

import org.nabucco.framework.base.facade.datatype.Datatype;

/**
 * Listener for entity lifecycle.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface EntityListener extends Serializable {

    /**
     * Called before a datatype is inserted into the database.
     * 
     * @param datatype
     *            the datatype to persist
     */
    void prePersist(Datatype datatype);

    /**
     * Called after a datatype is inserted into the database.
     * 
     * @param datatype
     *            the persisted datatype
     */
    void postPersist(Datatype datatype);

    /**
     * Called before a datatype is updated to the database.
     * 
     * @param datatype
     *            the datatype to update
     */
    void preUpdate(Datatype datatype);

    /**
     * Called after a datatype is updated to the database.
     * 
     * @param datatype
     *            the updated datatype
     */
    void postUpdate(Datatype datatype);

    /**
     * Called before a datatype is removed from the database.
     * 
     * @param datatype
     *            the datatype to remove
     */
    void preRemove(Datatype datatype);

    /**
     * Called after a datatype is removed from the database.
     * 
     * @param datatype
     *            the removed datatype
     */
    void postRemove(Datatype datatype);

    /**
     * Called after a datatype is load from the database.
     * 
     * @param datatype
     *            the loaded datatype
     */
    void postLoad(Datatype datatype);
}
