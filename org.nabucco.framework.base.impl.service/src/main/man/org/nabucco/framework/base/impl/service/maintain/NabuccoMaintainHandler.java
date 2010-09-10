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
package org.nabucco.framework.base.impl.service.maintain;

import javax.persistence.EntityManager;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * NabuccoMaintainHandler
 * <p/>
 * Offers CRUD (Create-Read-Update-Delete) operations on datatypes for database persistence.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoMaintainHandler<T extends Datatype> {

    private EntityManager em;

    private Class<T> datatypeClass;

    /**
     * Creates a new {@link NabuccoMaintainHandler} instance.
     * 
     * @param datatypeClass
     *            class of the datatype in view
     * @param em
     *            the persistence provider
     */
    public NabuccoMaintainHandler(Class<T> datatypeClass, EntityManager em) {
        if (em == null) {
            throw new IllegalArgumentException("EntityManager must not be null.");
        }
        if (datatypeClass == null) {
            throw new IllegalArgumentException("Datatype class must not be null.");
        }
        this.em = em;
        this.datatypeClass = datatypeClass;
    }

    /**
     * Inserts the datatype into the database.
     * 
     * @param datatype
     *            the datatype to persist
     * 
     * @return the persisted datatype (id != null)
     * 
     * @throws PersistenceException
     */
    public T create(T datatype) throws PersistenceException {

        try {
            this.em.persist(datatype);
            this.em.flush();
            return datatype;
        } catch (Exception e) {
            String name = this.datatypeClass.getName();
            throw new PersistenceException("Cannot create datatype '" + name + "'.", e);
        }
    }

    /**
     * Updates the datatype to the database.
     * 
     * @param datatype
     *            the datatype to merge
     * 
     * @return the merged datatype.
     * 
     * @throws PersistenceException
     */
    public T update(T datatype) throws PersistenceException {

        try {
            datatype = this.em.merge(datatype);
            this.em.flush();
            return datatype;
        } catch (Exception e) {
            String name = this.datatypeClass.getName();
            throw new PersistenceException("Cannot update datatype '" + name + "'.", e);
        }
    }

    /**
     * Removes the datatype from the database.
     * 
     * @param datatype
     *            the datatype to remove
     * 
     * @throws PersistenceException
     */
    public void remove(T datatype) throws PersistenceException {
        try {
            this.em.remove(datatype);
            this.em.flush();
        } catch (Exception e) {
            String name = this.datatypeClass.getName();
            throw new PersistenceException("Cannot remove datatype '" + name + "'.", e);
        }
    }

    /**
     * Finds the datatype by a long identifier.
     * 
     * @param id
     *            the identifier
     * 
     * @return the datatype
     * 
     * @throws PersistenceException
     */
    public T find(Long id) throws PersistenceException {
        try {
            T datatype = this.em.find(this.datatypeClass, id);
            return datatype;
        } catch (Exception e) {
            String name = this.datatypeClass.getName();
            throw new PersistenceException("Cannot find datatype '" + name + "'.", e);
        }
    }

    /**
     * Resets the datatype and disconnects the DB connection.
     * 
     * @param datatype
     *            the datatype to reset
     */
    public void resetState(Datatype datatype) {
        this.em.clear();
        if (datatype != null) {
            datatype.setDatatypeState(DatatypeState.PERSISTENT);
        }
    }
}
