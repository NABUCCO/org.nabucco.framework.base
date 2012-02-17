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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TemporalType;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * NabuccoQuery
 * <p/>
 * Object for executing SQL/JPQL queries against the database.
 * 
 * @param <T>
 *            the query object
 * 
 * @see Query
 * @see PersistenceManager
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoQuery<T extends Object> {

    /**
     * Execute the query and expect a single result.
     * 
     * @return the single result
     * 
     * @throws PersistenceException
     *             when the query cannot be executed, no result or more than one result was found
     */
    T getSingleResult() throws PersistenceException;

    /**
     * Execute the query and expect a result list.
     * 
     * @return the result list
     * 
     * @throws PersistenceException
     *             when the query cannot be executed
     */
    List<T> getResultList() throws PersistenceException;

    /**
     * Execute an update or delete statement.
     * 
     * @return the number of modified datatypes.
     * 
     * @throws PersistenceException
     *             when the query cannot be executed
     */
    int executeUpdate() throws PersistenceException;

    /**
     * Set the row of the first result.
     * 
     * @param firstResult
     *            the first row of the result list
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the first result cannot be set
     */
    NabuccoQuery<T> setFirstResult(int firstResult) throws PersistenceException;

    /**
     * Set the maximum number of results to retrieve.
     * 
     * @param maxResults
     *            the maximum number of resultst
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the max results cannot be set
     */
    NabuccoQuery<T> setMaxResults(int maxResults) throws PersistenceException;

    /**
     * Set an implementation-specific hint to the query.
     * 
     * @param name
     *            the name of the hint
     * @param hint
     *            the hint value
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the hint cannot be set
     */
    NabuccoQuery<T> setHint(String name, Object hint) throws PersistenceException;

    /**
     * Set the flush mode type to be used for the query execution. The flush mode type applies to
     * the query regardless of the flush mode type in use for the entity manager.
     * 
     * @param flushMode
     *            the flush mode (AUTO, COMMIT)
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the flush mode cannot be set
     */
    NabuccoQuery<T> setFlushMode(FlushModeType flushMode) throws PersistenceException;

    /**
     * Bind an argument to a named parameter. A named parameter must be defined in the query by an
     * <code>:name</code> argument.
     * 
     * @param name
     *            the argument name
     * @param value
     *            the argument value
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the parameter cannot does not exist or cannot be set
     */
    NabuccoQuery<T> setParameter(String name, Object value) throws PersistenceException;

    /**
     * Bind an instance of {@link java.util.Date} to a named parameter. A named parameter must be
     * defined in the query by an <code>:name</code> argument.
     * 
     * @param name
     *            the argument name
     * @param value
     *            the argument value
     * @param temporalType
     *            the type of the date (DATE, TIME, TIMESTAMP)
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the parameter cannot does not exist or cannot be set
     */
    NabuccoQuery<T> setParameter(String name, Date value, TemporalType temporalType) throws PersistenceException;

    /**
     * Bind an instance of {@link java.util.Calendar} to a named parameter. A named parameter must
     * be defined in the query by an <code>:name</code> argument.
     * 
     * @param name
     *            the argument name
     * @param value
     *            the argument value
     * @param temporalType
     *            the type of the calendar (DATE, TIME, TIMESTAMP)
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the parameter cannot does not exist or cannot be set
     */
    NabuccoQuery<T> setParameter(String name, Calendar value, TemporalType temporalType) throws PersistenceException;

    /**
     * Bind an argument to a positional parameter. A positional parameter must be defined in the
     * query by an <code>?position</code> argument.
     * 
     * @param position
     *            the argument position
     * @param value
     *            the argument value
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the parameter cannot be set
     */
    NabuccoQuery<T> setParameter(int position, Object value) throws PersistenceException;

    /**
     * Bind an instance of {@link java.util.Date} to a positional parameter. A positional parameter
     * must be defined in the query by an <code>?position</code> argument.
     * 
     * @param position
     *            the argument position
     * @param value
     *            the argument value
     * @param temporalType
     *            the type of the date (DATE, TIME, TIMESTAMP)
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the parameter cannot does not exist or cannot be set
     */
    NabuccoQuery<T> setParameter(int position, Date value, TemporalType temporalType) throws PersistenceException;

    /**
     * Bind an instance of {@link java.util.Calendar} to a positional parameter. A positional
     * parameter must be defined in the query by an <code>?position</code> argument.
     * 
     * @param position
     *            the argument position
     * @param value
     *            the argument value
     * @param temporalType
     *            the type of the calendar (DATE, TIME, TIMESTAMP)
     * 
     * @return the query instance
     * 
     * @throws PersistenceException
     *             when the parameter cannot does not exist or cannot be set
     */
    NabuccoQuery<T> setParameter(int position, Calendar value, TemporalType temporalType) throws PersistenceException;

}
