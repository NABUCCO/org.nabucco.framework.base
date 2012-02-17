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
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntry;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntryFactory;
import org.nabucco.framework.base.facade.datatype.monitor.PersistenceOperationType;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.impl.service.monitor.MonitorFacade;
import org.nabucco.framework.base.impl.service.monitor.ServiceNameThreadLocal;
import org.nabucco.framework.base.impl.service.monitor.ServiceOperationNameThreadLocal;

/**
 * NabuccoQuerryImpl
 * <p/>
 * A query implementation for datatbase query executions.
 * 
 * @param <T>
 *            the query object
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class NabuccoQuerryImpl<T extends Object> implements NabuccoQuery<T> {

    private final NabuccoLogger logger;

    private final String queryString;

    private final Query delegate;

    /**
     * Creates a new {@link NabuccoQuerryImpl} instance.
     * 
     * @param queryString
     *            the query string
     * @param delegate
     *            the delegating query
     * @param logger
     *            the logger
     */
    NabuccoQuerryImpl(String queryString, Query delegate, NabuccoLogger logger) {
        this.queryString = queryString;
        this.delegate = delegate;
        this.logger = logger;
    }

    @Override
    public int executeUpdate() throws PersistenceException {
        try {
            long start = NabuccoSystem.getCurrentTimeMillis();

            int modifiedDatatypes = this.delegate.executeUpdate();

            long end = NabuccoSystem.getCurrentTimeMillis();
            long duration = (end - start) / 10;

            this.monitor(null, PersistenceOperationType.UPDATE, duration, start);

            return modifiedDatatypes;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error executing update by query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error executing update by query '", this.queryString, "'.");
            throw new PersistenceException("Error executing update by query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public List<T> getResultList() throws PersistenceException {
        try {
            long start = NabuccoSystem.getCurrentTimeMillis();

            @SuppressWarnings("unchecked")
            List<T> resultList = this.delegate.getResultList();

            long end = NabuccoSystem.getCurrentTimeMillis();
            long duration = (end - start) / 10;

            String entityName = null;
            if (!resultList.isEmpty()) {
                entityName = resultList.get(0).getClass().getCanonicalName();
            }

            this.monitor(entityName, PersistenceOperationType.READ, duration, start);

            return resultList;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error retrieving result list of query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error retrieving result list of query '", this.queryString, "'.");
            throw new PersistenceException("Error retrieving result list of query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public T getSingleResult() throws PersistenceException {
        try {
            long start = NabuccoSystem.getCurrentTimeMillis();

            @SuppressWarnings("unchecked")
            T result = (T) this.delegate.getSingleResult();

            long end = NabuccoSystem.getCurrentTimeMillis();
            long duration = (end - start) / 10;

            String entityName = null;
            if (result != null) {
                entityName = result.getClass().getCanonicalName();
            }

            this.monitor(entityName, PersistenceOperationType.READ, duration, start);

            return result;

        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error retrieving single result of query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error retrieving single result of query '", this.queryString, "'.");
            throw new PersistenceException("Error retrieving single result of query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setFirstResult(int firstResult) throws PersistenceException {
        try {
            this.delegate.setFirstResult(firstResult);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error in operation 'setFirstResult' of query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error in operation 'setFirstResult' of query '", this.queryString, "'.");
            throw new PersistenceException("Error in operation 'setFirstResult' of query '" + this.queryString + "'.",
                    e);
        }
    }

    @Override
    public NabuccoQuery<T> setMaxResults(int maxResults) throws PersistenceException {
        try {
            this.delegate.setMaxResults(maxResults);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error in operation 'setMaxResults' of query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error in operation 'setMaxResults' of query '", this.queryString, "'.");
            throw new PersistenceException("Error in operation 'setMaxResults' of query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setFlushMode(FlushModeType flushModeType) throws PersistenceException {
        try {
            this.delegate.setFlushMode(flushModeType);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error in operation 'setFlushMode' of query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error in operation 'setFlushMode' of query '", this.queryString, "'.");
            throw new PersistenceException("Error in operation 'setFlushMode' of query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setHint(String key, Object value) throws PersistenceException {
        try {
            this.delegate.setHint(key, value);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error in operation 'setHint' of query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error in operation 'setHint' of query '", this.queryString, "'.");
            throw new PersistenceException("Error in operation 'setHint' of query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setParameter(int position, Calendar value, TemporalType temporalType)
            throws PersistenceException {
        try {
            this.delegate.setParameter(position, value, temporalType);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error setting parameter in query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error setting parameter in query '", this.queryString, "'.");
            throw new PersistenceException("Error setting parameter in query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setParameter(int position, Date value, TemporalType temporalType)
            throws PersistenceException {
        try {
            this.delegate.setParameter(position, value, temporalType);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error setting parameter in query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error setting parameter in query '", this.queryString, "'.");
            throw new PersistenceException("Error setting parameter in query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setParameter(int position, Object value) throws PersistenceException {
        try {
            this.delegate.setParameter(position, value);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error setting parameter in query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error setting parameter in query '", this.queryString, "'.");
            throw new PersistenceException("Error setting parameter in query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setParameter(String nam, Calendar value, TemporalType temporalType)
            throws PersistenceException {
        try {
            this.delegate.setParameter(nam, value, temporalType);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error setting parameter in query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error setting parameter in query '", this.queryString, "'.");
            throw new PersistenceException("Error setting parameter in query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setParameter(String name, Date value, TemporalType temporalType) throws PersistenceException {
        try {
            this.delegate.setParameter(name, value, temporalType);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error setting parameter in query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error setting parameter in query '", this.queryString, "'.");
            throw new PersistenceException("Error setting parameter in query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public NabuccoQuery<T> setParameter(String name, Object value) throws PersistenceException {
        try {
            this.delegate.setParameter(name, value);
            return this;
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error setting parameter in query '", this.queryString, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error setting parameter in query '", this.queryString, "'.");
            throw new PersistenceException("Error setting parameter in query '" + this.queryString + "'.", e);
        }
    }

    @Override
    public String toString() {
        return this.queryString;
    }

    /**
     * Monitors the given persistence execution.
     * 
     * @param exception
     *            the raised exception
     */
    private void monitor(Exception exception) {

        String exceptionName = exception.getClass().getCanonicalName();
        String serviceName = ServiceNameThreadLocal.getServiceName();
        String operationName = ServiceOperationNameThreadLocal.getOperationName();

        try {
            MonitorEntry entry = MonitorEntryFactory.getInstance().createExceptionMonitorEntry(serviceName,
                    operationName, NabuccoSystem.getCurrentTimeMillis(), exceptionName);
            MonitorFacade.getInstance().putEntry(entry);

        } catch (Exception e) {
            logger.warning(e, "Error monitoring ", serviceName, ".", operationName, "().");
        }
    }

    /**
     * Monitors the given persistence execution.
     * 
     * @param entityName
     *            name of the processing entity
     * @param type
     *            type of the executed operation
     * @param duration
     *            persistence execution duration in centiseconds
     * @param timestamp
     *            the starting timestamp
     */
    private void monitor(String entityName, PersistenceOperationType type, long duration, long timestamp) {

        String serviceName = ServiceNameThreadLocal.getServiceName();
        String operationName = ServiceOperationNameThreadLocal.getOperationName();

        try {
            MonitorEntry entry = MonitorEntryFactory.getInstance().createPersistenceMonitorEntry(serviceName,
                    operationName, timestamp, duration, entityName, type, this.queryString);
            MonitorFacade.getInstance().putEntry(entry);
        } catch (Exception e) {
            logger.warning(e, "Error monitoring ", serviceName, ".", operationName, "().");
        }
    }
}
