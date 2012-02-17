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
package org.nabucco.framework.base.impl.service.maintain;

import javax.persistence.EntityManager;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * PersistenceManager
 * <p/>
 * Database synchronization manager holding persistence operations for synchronizing
 * {@link NabuccoDatatype} instances with the database.
 * 
 * @see EntityManager
 * @see NabuccoQuery
 * @see PersistenceManagerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface PersistenceManager {

    /**
     * Loads a persistent datatype from the database and attaches it to the entity manager.
     * 
     * @param <T>
     *            The NABUCCO datatype type.
     * @param type
     *            class of the datatype.
     * @param id
     *            the datatype id
     * 
     * @return the loaded datatype.
     * 
     * @throws PersistenceException
     *             when the datatype cannot be found
     */
    <T extends NabuccoDatatype> T find(Class<T> type, Long id) throws PersistenceException;

    /**
     * Loads a persistent datatype from the database and attaches it to the entity manager.
     * 
     * @param <T>
     *            The NABUCCO datatype type.
     * @param datatype
     *            the datatype to load (ID must be set)
     * 
     * @return the loaded datatype.
     * 
     * @throws PersistenceException
     *             when the datatype cannot be found
     */
    <T extends NabuccoDatatype> T find(T datatype) throws PersistenceException;

    /**
     * Synchronize the entity with its datatbase state.
     * 
     * @param <T>
     *            The NABUCCO datatype type.
     * @param datatype
     *            the datatype to refresh
     * 
     * @throws PersistenceException
     *             when the datatype is not persistent
     */
    <T extends NabuccoDatatype> void refresh(T datatype) throws PersistenceException;

    /**
     * Persists a {@link NabuccoDatatype} entity to the database. Depending on the datatype's state
     * a related database operation is performed.
     * 
     * @param <T>
     *            the persisted type
     * @param datatype
     *            the datatype to synchronize with the database
     * 
     * @return the persisted datatype
     * 
     * @throws PersistenceException
     *             when the persistence operation fails and the datatype cannot be synchronized with
     *             the database
     * 
     * @see DatatypeState
     * 
     * @throws PersistenceException
     *             when the datatype cannot be persisted
     */
    <T extends NabuccoDatatype> T persist(T datatype) throws PersistenceException;

    /**
     * Persists a list of {@link NabuccoDatatype} instances. Depending on the datatype's state a
     * related database operation is performed.
     * 
     * @param datatypes
     *            the list of datatype to synchronize with the database
     * 
     * @throws PersistenceException
     *             when the persistence operation fails and the datatype cannot be synchronized with
     *             the database
     * 
     * @see DatatypeState
     * 
     * @throws PersistenceException
     *             when a datatype in the list cannot be persisted
     */
    void persist(Iterable<? extends NabuccoDatatype> datatypes) throws PersistenceException;

    /**
     * Create a JPA query for database querying.
     * 
     * @param <T>
     *            the queried object
     * @param query
     *            the query string
     * 
     * @return the query object
     * 
     * @throws PersistenceException
     *             when the query is not valid and cannot be created
     */
    <T extends Object> NabuccoQuery<T> createQuery(String query) throws PersistenceException;

    /**
     * Create a SQL query for database querying.
     * 
     * @param <T>
     *            the queried object
     * @param query
     *            the query string
     * 
     * @return the query object
     * 
     * @throws PersistenceException
     *             when the query is not valid and cannot be created
     */
    <T extends Object> NabuccoQuery<T> createNativeQuery(String query) throws PersistenceException;

    /**
     * Create a SQL query for database querying.
     * 
     * @param <T>
     *            the queried object
     * @param query
     *            the query string
     * @param type
     *            the resulting class
     * 
     * @return the query object
     * 
     * @throws PersistenceException
     *             when the query is not valid and cannot be created
     */
    <T extends Object> NabuccoQuery<T> createNativeQuery(String query, Class<T> type) throws PersistenceException;

    /**
     * Create a SQL query for database querying.
     * 
     * @param <T>
     *            the queried object
     * @param query
     *            the query string
     * @param resultsetMapping
     *            the resultset mapping
     * 
     * @return the query object
     * 
     * @throws PersistenceException
     *             when the query is not valid and cannot be created
     */
    <T extends Object> NabuccoQuery<T> createNativeQuery(String query, String resultsetMapping)
            throws PersistenceException;

    /**
     * Getter for the entity manager delegate.
     * <p/>
     * <b>Attention:</b> Do not use the entity manager without acceptable reason. Each entity
     * manager operation may be accessed without using the entity manager explicitly, by using the
     * persistence manager operations.
     * 
     * @see EntityManager
     * 
     * @return the entity manager delegate
     */
    EntityManager getDelegate();

    /**
     * Synchronize the persistence context to the underlying database.
     * 
     * @throws PersistenceException
     *             when the database connection is not active
     */
    void flush() throws PersistenceException;

    /**
     * Detaches all managed entities.
     * 
     * @throws PersistenceException
     *             when the database connection is not active
     */
    void clear() throws PersistenceException;

    /**
     * Reset the already persistet datatypes.
     * 
     * @throws PersistenceException
     *             when the database connection is not active
     */
    void reset() throws PersistenceException;

    /**
     * Check whether the entity is managed or not.
     * 
     * @param entity
     *            the entity to check
     * 
     * @return <b>true</b> if the entity is managed, <b>false</b> if not
     * 
     * @throws PersistenceException
     *             when the database connection is not active
     */
    boolean contains(Object entity) throws PersistenceException;

    /**
     * CHeck whether the database connection is open, or not.
     * 
     * @return <b>true</b> if the connection is open, <b>false</b> if not
     * 
     * @throws PersistenceException
     *             when the database connection is not active
     */
    boolean isOpen() throws PersistenceException;

}
