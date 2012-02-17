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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.nabucco.framework.base.facade.datatype.DatatypeAccessor;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntry;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntryFactory;
import org.nabucco.framework.base.facade.datatype.monitor.PersistenceOperationType;
import org.nabucco.framework.base.facade.exception.persistence.EntityNotFoundException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.impl.service.ServiceHandlerSupport;
import org.nabucco.framework.base.impl.service.monitor.MonitorFacade;
import org.nabucco.framework.base.impl.service.monitor.ServiceNameThreadLocal;
import org.nabucco.framework.base.impl.service.monitor.ServiceOperationNameThreadLocal;

/**
 * PersistenceManagerImpl
 * <p/>
 * Utilty class holding persistence operations for synchronizing {@link NabuccoDatatype} instances
 * with the database.
 * 
 * @see PersistenceManager
 * @see EntityManager
 * @see NabuccoQuery
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class PersistenceManagerImpl implements PersistenceManager {

    /** The Entity Manager reference. */
    private EntityManager entityManager;

    /** Set of already persisted datatype hash codes. */
    private Set<Integer> alreadyPersisted = new HashSet<Integer>();

    /** The NABUCCO logger. */
    private NabuccoLogger logger;

    /**
     * Creates a new persistence helper instance.
     * 
     * @param entityManager
     *            the entity manager
     */
    PersistenceManagerImpl(EntityManager entityManager) {
        this(entityManager, null);
    }

    /**
     * Creates a new persistence helper instance. Do not create this instance manually. Use
     * {@link ServiceHandlerSupport#getPersistenceManager()} instead.
     * 
     * @param entityManager
     *            the entity manager
     * @param logger
     *            the nabucco logger
     */
    PersistenceManagerImpl(EntityManager entityManager, NabuccoLogger logger) {
        if (entityManager == null) {
            throw new IllegalArgumentException("Cannot create PersistenceManager for EntityManager [null].");
        }

        if (logger == null) {
            logger = NabuccoLoggingFactory.getInstance().getLogger(PersistenceManagerImpl.class);
        }

        this.entityManager = entityManager;
        this.logger = logger;
    }

    @Override
    public EntityManager getDelegate() {
        return this.entityManager;
    }

    /**
     * Setter for the entity manager delegate.
     * 
     * @param delegate
     *            the new delegate to set
     */
    public void setDelegate(EntityManager delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("Cannot change delegating EntityManager to [null].");
        }

        this.entityManager = delegate;
    }

    /**
     * Loads a persistent datatype from the database and attaches it to the entity manager.
     * 
     * @deprecated use {@link PersistenceManagerImpl#find(NabuccoDatatype)} instead.
     * 
     * @param <T>
     *            The NABUCCO datatype type.
     * @param type
     *            class of the datatype.
     * @param datatype
     *            the empty datatype, primary key must be set
     * 
     * @return the loaded datatype.
     * 
     * @throws PersistenceException
     *             when the datatype cannot be found
     */
    @Deprecated
    public <T extends NabuccoDatatype> T find(Class<T> type, T datatype) throws PersistenceException {

        if (datatype == null) {
            throw new IllegalArgumentException("Cannot find datatype [null].");
        }

        return this.find(type, datatype.getId());
    }

    @Override
    public <T extends NabuccoDatatype> T find(T datatype) throws PersistenceException {

        if (datatype == null) {
            throw new IllegalArgumentException("Cannot find datatype [null].");
        }

        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) datatype.getClass();

        return this.find(type, datatype.getId());
    }

    @Override
    public <T extends NabuccoDatatype> T find(Class<T> type, Long id) throws PersistenceException {
        if (type == null) {
            throw new IllegalArgumentException("Cannot find datatype for type [null].");
        }

        String name = type.getName();

        if (id == null) {
            logger.error("Cannot find datatype ", name, " widh id [null].");
            PersistenceException exception = new PersistenceException("Cannot find datatype "
                    + name + " with id [null].");

            this.monitor(exception);

            throw exception;
        }

        T managedDatatype = this.entityManager.find(type, id);

        if (managedDatatype == null) {
            logger.error("Cannot find datatype ", name, " with id ", String.valueOf(id), ".");
            EntityNotFoundException exception = new EntityNotFoundException("Cannot find datatype "
                    + name + " with id " + id + ".");

            this.monitor(exception);

            throw exception;
        }

        return managedDatatype;
    }

    @Override
    public void refresh(NabuccoDatatype datatype) throws PersistenceException {
        if (datatype == null) {
            throw new IllegalArgumentException("Cannot refresh datatype [null].");
        }

        String name = datatype.getClass().getCanonicalName();

        if (datatype.getId() == null) {
            logger.error("Cannot refresh datatype ", name, " widh id [null].");

            PersistenceException exception = new PersistenceException("Cannot refresh datatype "
                    + name + " with id [null].");

            this.monitor(exception);

            throw exception;
        }

        String id = String.valueOf(datatype.getId());

        try {
            this.entityManager.refresh(datatype);

        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error refreshing datatype ", name, " with id '", id, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error refreshing datatype ", name, " with id '", id, "'.");
            throw new PersistenceException("Error refreshing datatype " + name + " with id '" + id + "'.", e);
        }
    }

    @Override
    public void persist(Iterable<? extends NabuccoDatatype> datatypes) throws PersistenceException {
        for (NabuccoDatatype datatype : datatypes) {
            this.persist(datatype);
        }
    }

    @Override
    public <T extends NabuccoDatatype> T persist(T datatype) throws PersistenceException {

        if (datatype == null) {
            throw new PersistenceException("Cannot persist datatype [null].");
        }

        if (this.isAlreadyPersisted(datatype)) {
            return datatype;
        }

        Long id = datatype.getId();
        String name = datatype.getClass().getName();

        try {
            switch (datatype.getDatatypeState()) {

            case CONSTRUCTED: {
                logger.error("Datatype is not initialized ", name, ".");
                throw new PersistenceException("Datatype is not initialized " + name + ".");
            }

            case INITIALIZED: {
                datatype = this.createDatatype(datatype, name);
                break;
            }

            case MODIFIED: {
                datatype = this.modifyDatatype(datatype, name);
                break;
            }

            case DELETED: {
                datatype = this.deleteDatatype(datatype, name);
                break;
            }

            case DESTROYED: {
                break;
            }

            case PERSISTENT: {
                break;
            }

            case TRANSIENT: {
                break;
            }

            default: {
                PersistenceException exception = new PersistenceException("Datatype state '"
                        + datatype.getDatatypeState() + "' is not supported for " + name + ".");
                this.monitor(exception);
                throw exception;
            }
            }

            return datatype;

        } catch (PersistenceException pe) {
            this.monitor(pe);
            logger.error(pe, "Error persisting datatype ", name, " with id '", String.valueOf(id), "'.");
            throw pe;
        } catch (Exception e) {
            this.monitor(e);
            logger.error(e, "Error persisting datatype ", name, " with id '", String.valueOf(id), "'.");
            throw PersistenceExceptionMapper.resolve(e, name, id);
        }
    }

    /**
     * Check whether the datatype has already been persisted.
     * 
     * @param datatype
     *            the datatype to check
     * 
     * @return <b>true</b> if the datatype has alread been persisted, <b>false</b> if not
     */
    private boolean isAlreadyPersisted(NabuccoDatatype datatype) {
        int identity = System.identityHashCode(datatype);
        return !this.alreadyPersisted.add(identity);
    }

    /**
     * Insert the datatype in the database.
     * 
     * @param <T>
     *            the persisted type
     * @param datatype
     *            the datatype
     * @param name
     *            the name of the datatype to create
     * 
     * @return the created datatype
     * 
     * @throws PersistenceException
     *             when the datatype cannot be inserted
     */
    private <T extends NabuccoDatatype> T createDatatype(T datatype, String name) throws PersistenceException {

        try {

            long start = NabuccoSystem.getCurrentTimeMillis();

            this.entityManager.persist(datatype);

            long end = NabuccoSystem.getCurrentTimeMillis();
            long duration = (end - start) / 10;

            this.monitor(name, PersistenceOperationType.CREATE, duration, start);

            datatype.setDatatypeState(DatatypeState.PERSISTENT);

        } catch (Exception e) {
            this.monitor(e);
            logger.error(e, "Cannot create datatype ", name, ".");
            throw PersistenceExceptionMapper.resolve(e, name, datatype.getId());
        }

        return datatype;
    }

    /**
     * Updates the datatype in the database.
     * 
     * @param <T>
     *            the persisted type
     * @param datatype
     *            the datatype to modify
     * @param name
     *            the name of the datatype to modify
     * 
     * @return the modified datatype
     * 
     * @throws PersistenceException
     *             when the datatype cannot be updated
     */
    private <T extends NabuccoDatatype> T modifyDatatype(T datatype, String name) throws PersistenceException {

        if (datatype.getId() == null) {
            logger.error("Cannot modify datatype ", name, " with id [null].");
            PersistenceException exception = new PersistenceException("Cannot modify datatype "
                    + name + " with id [null].");
            this.monitor(exception);
            throw exception;
        }

        try {
            // Save Component Relations from Datatype (Transient Values are Removed by JPA)
            ComponentRelationContainer componentRelation = DatatypeAccessor.getInstance()
                    .getComponentRelation(datatype);

            long start = NabuccoSystem.getCurrentTimeMillis();

            datatype = this.entityManager.merge(datatype);

            long end = NabuccoSystem.getCurrentTimeMillis();
            long duration = (end - start) / 10;

            this.monitor(name, PersistenceOperationType.UPDATE, duration, start);

            // Add Component Relations to Datatype
            DatatypeAccessor.getInstance().setComponentRelation(datatype, componentRelation);

            datatype.setDatatypeState(DatatypeState.PERSISTENT);

        } catch (Exception e) {
            this.monitor(e);
            logger.error(e, "Cannot modify datatype '", name, " with id '", String.valueOf(datatype.getId()), "'.");
            throw PersistenceExceptionMapper.resolve(e, name, datatype.getId());
        }

        return datatype;
    }

    /**
     * Deletes the datatype from the database.
     * 
     * @param <T>
     *            the persisted type
     * @param datatype
     *            the datatype to delete.
     * @param name
     *            the name of the datatype
     * 
     * @return the deleted datatype
     * 
     * @throws PersistenceException
     *             when the datatype cannot be removed
     */
    private <T extends NabuccoDatatype> T deleteDatatype(T datatype, String name) throws PersistenceException {

        if (datatype.getId() == null) {
            logger.error("Cannot delete datatype '", name, "' with id [null].");
            PersistenceException exception = new PersistenceException("Cannot delete datatype '"
                    + name + "' with id [null].");
            this.monitor(exception);
            throw exception;
        }

        try {
            // Save Component Relations from Datatype (Transient Values are Removed by JPA)
            ComponentRelationContainer componentRelation = DatatypeAccessor.getInstance()
                    .getComponentRelation(datatype);

            long start = NabuccoSystem.getCurrentTimeMillis();

            datatype = this.entityManager.merge(datatype);

            long end = NabuccoSystem.getCurrentTimeMillis();
            long duration = (end - start) / 10;

            this.monitor(name, PersistenceOperationType.DELETE, duration, start);

            this.entityManager.remove(datatype);

            // Remove All Component Relations!
            componentRelation.clear();

            // Add Component Relations to Datatype
            DatatypeAccessor.getInstance().setComponentRelation(datatype, componentRelation);

            datatype.setDatatypeState(DatatypeState.DESTROYED);

        } catch (Exception e) {
            this.monitor(e);
            logger.error(e, "Cannot delete datatype '", name, " with id '", String.valueOf(datatype.getId()), "'.");
            throw PersistenceExceptionMapper.resolve(e, name, datatype.getId());
        }

        return datatype;
    }

    @Override
    public void flush() throws PersistenceException {
        try {
            this.entityManager.flush();
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error flushing database connection.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error flushing database connection.");
            throw new PersistenceException("Error flushing database connection.", e);
        }
    }

    @Override
    public void clear() throws PersistenceException {
        try {
            this.alreadyPersisted.clear();
            this.entityManager.clear();
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error clearing database connection.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error clearing database connection.");
            throw new PersistenceException("Error clearing database connection.", e);
        }
    }

    @Override
    public void reset() {
        this.alreadyPersisted.clear();
    }

    @Override
    public <T extends Object> NabuccoQuery<T> createQuery(String query) throws PersistenceException {
        try {
            return new NabuccoQuerryImpl<T>(query, this.entityManager.createQuery(query), this.logger);
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error creating query '", query, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error creating query '", query, "'.");
            throw new PersistenceException("Error creating query '" + query + "'.", e);
        }
    }

    @Override
    public <T extends Object> NabuccoQuery<T> createNativeQuery(String query) throws PersistenceException {
        try {
            return new NabuccoQuerryImpl<T>(query, this.entityManager.createNativeQuery(query), this.logger);
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error creating query '", query, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error creating query '", query, "'.");
            throw new PersistenceException("Error creating query '" + query + "'.", e);
        }
    }

    @Override
    public <T extends Object> NabuccoQuery<T> createNativeQuery(String query, Class<T> type)
            throws PersistenceException {
        try {
            return new NabuccoQuerryImpl<T>(query, this.entityManager.createNativeQuery(query, type), this.logger);
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error creating query '", query, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error creating query '", query, "'.");
            throw new PersistenceException("Error creating query '" + query + "'.", e);
        }
    }

    @Override
    public <T extends Object> NabuccoQuery<T> createNativeQuery(String query, String resultsetMapping)
            throws PersistenceException {
        try {
            return new NabuccoQuerryImpl<T>(query, this.entityManager.createNativeQuery(query, resultsetMapping),
                    this.logger);
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error creating query '", query, "'.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error creating query '", query, "'.");
            throw new PersistenceException("Error creating query '" + query + "'.", e);
        }
    }

    @Override
    public boolean contains(Object entity) throws PersistenceException {
        try {
            return this.entityManager.contains(entity);
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error flushing database connection.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.logger.error(e, "Error flushing database connection.");
            throw new PersistenceException("Error flushing database connection.", e);
        }
    }

    @Override
    public boolean isOpen() throws PersistenceException {
        try {
            return this.entityManager.isOpen();
        } catch (javax.persistence.PersistenceException pe) {
            this.monitor(pe);
            this.logger.error(pe, "Error flushing database connection.");
            throw PersistenceExceptionMapper.resolve(pe);
        } catch (Exception e) {
            this.monitor(e);
            this.logger.error(e, "Error flushing database connection.");
            throw new PersistenceException("Error flushing database connection.", e);
        }
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
                    operationName, timestamp, duration, entityName, type, null);
            MonitorFacade.getInstance().putEntry(entry);
        } catch (Exception e) {
            logger.warning(e, "Error monitoring ", serviceName, ".", operationName, "().");
        }
    }

}
