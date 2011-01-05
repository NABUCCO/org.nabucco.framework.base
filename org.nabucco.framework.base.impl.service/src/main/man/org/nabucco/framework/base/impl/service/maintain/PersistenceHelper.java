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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.persistence.EntityNotFoundException;
import org.nabucco.framework.base.facade.exception.persistence.OptimisticLockException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceExceptionMapper;

/**
 * PersistenceHelper
 * <p/>
 * Utilty class holding persistence operations for synchronizing {@link NabuccoDatatype} instances
 * with the database.
 * 
 * @author Jens Wurm, Nicolas Moser, Oliver Teichmann PRODYNA AG
 */
public class PersistenceHelper {

    /** The Entity Manager reference. */
    private EntityManager em;

    /** Set of already persisted datatype hash codes. */
    private Set<Integer> alreadyPersisted = new HashSet<Integer>();

    /** The NABUCCO logger. */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            PersistenceHelper.class);

    /**
     * Creates a new persistence helper instance.
     * 
     * @param entityManager
     *            the entity manager
     */
    public PersistenceHelper(EntityManager entityManager) {
        if (entityManager == null) {
            throw new IllegalArgumentException(
                    "Cannot create PersistenceHelper for EntityManager [null].");
        }
        this.em = entityManager;
    }

    /**
     * Loads a persistent datatype from the database.
     * 
     * @param <T>
     *            The NABUCCO datatype type.
     * @param type
     *            class of the datatype.
     * @param datatype
     *            the datatype.
     * 
     * @return the loaded datatype.
     * 
     * @throws PersistenceException
     */
    public <T extends NabuccoDatatype> T find(Class<T> type, T datatype)
            throws PersistenceException {

        if (datatype == null) {
            throw new IllegalArgumentException("Cannot find datatype [null].");
        }

        String name = datatype.getClass().getName();

        if (datatype.getId() == null) {
            logger.error("Cannot find non-persistent datatype '", name, "' with id [null].");
            throw new PersistenceException("Cannot modify non-persistent datatype '" + name + "'.");
        }

        T managedDatatype = this.em.find(type, datatype.getId());

        if (managedDatatype == null) {
            String msg = "Cannot find '" + name + "' with id " + datatype.getId() + ".";
            throw new EntityNotFoundException(msg);
        }

        // Cached entity instances should not be touched.
        if (managedDatatype.getDatatypeState() == DatatypeState.CONSTRUCTED) {
            managedDatatype.setDatatypeState(DatatypeState.PERSISTENT);
        }
        
        return managedDatatype;
    }

    /**
     * Persists a bulk of {@link NabuccoDatatype} instances with its children.
     * 
     * @param datatypes
     *            the datatypes
     * 
     * @throws PersistenceException
     */
    public void persist(Iterable<? extends NabuccoDatatype> datatypes) throws PersistenceException {
        for (NabuccoDatatype datatype : datatypes) {
            this.persist(datatype);
        }
    }

    /**
     * Persists a {@link NabuccoDatatype} instance with its children.
     * 
     * @param datatype
     *            the datatype
     * 
     * @throws PersistenceException
     */
    public <T extends NabuccoDatatype> T persist(T datatype) throws PersistenceException {

        if (datatype == null) {
            throw new PersistenceException("Cannot maintain datatype [null].");
        }

        if (this.isAlreadyPersisted(datatype)) {
            return datatype;
        }

        Long id = datatype.getId();
        String name = datatype.getClass().getName();

        try {
            switch (datatype.getDatatypeState()) {

            case CONSTRUCTED: {
                logger.error("Datatype is not initialized '", name, "'.");
                throw new PersistenceException("Datatype is not initialized '" + name + "'.");
            }
            case INITIALIZED: {
                datatype = this.createDatatype(datatype);
                break;
            }
            case MODIFIED: {
                datatype = this.modifyDatatype(datatype);
                break;
            }
            case DELETED: {
                datatype = this.deleteDatatype(datatype);
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
                throw new PersistenceException("Datatype state '"
                        + datatype.getDatatypeState() + "' is not valid for " + name + "'.");
            }
            }

            return datatype;

        } catch (PersistenceException pe) {
            throw pe;
        } catch (Exception e) {
            logger.error(e, "Error persisting datatype '", name, "' with id '", String.valueOf(id),
                    "'.");
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
     * Reset the already visited datatypes.
     */
    public void reset() {
        this.alreadyPersisted.clear();
    }

    /**
     * Creates the datatype in the database.
     * 
     * @param datatype
     *            the datatype
     * @param commit
     * 
     * @throws PersistenceException
     */
    private <T extends NabuccoDatatype> T createDatatype(T datatype) throws PersistenceException {

        String name = datatype.getClass().getName();

        try {
            this.em.persist(datatype);
            datatype.setDatatypeState(DatatypeState.PERSISTENT);

        } catch (Exception e) {
            logger.error(e, "Cannot create datatype '", name, "'.");
            throw PersistenceExceptionMapper.resolve(e, name, datatype.getId());
        }

        return datatype;
    }

    /**
     * Modifies the datatype in the database.
     * 
     * @param datatype
     *            the datatype to modify
     * @param commit
     * 
     * @throws PersistenceException
     */
    private <T extends NabuccoDatatype> T modifyDatatype(T datatype) throws PersistenceException {

        String name = datatype.getClass().getName();

        if (datatype.getId() == null) {
            logger.error("Cannot modify non-persistent datatype '", name, "' with id [null].");
            throw new PersistenceException("Cannot modify non-persistent datatype '" + name + "'.");
        }

        try {
            datatype = this.em.merge(datatype);
            datatype.setDatatypeState(DatatypeState.PERSISTENT);

        } catch (Exception e) {
            logger.error(e, "Cannot modify datatype '", name, "'.");
            throw PersistenceExceptionMapper.resolve(e, name, datatype.getId());
        }

        return datatype;
    }

    /**
     * Deletes the datatype from the database.
     * 
     * @param datatype
     *            the datatype to delete.
     * @param commit
     * @return
     * 
     * @throws PersistenceException
     */
    private <T extends NabuccoDatatype> T deleteDatatype(T datatype) throws PersistenceException {

        String name = datatype.getClass().getName();

        if (datatype.getId() == null) {
            logger.error("Cannot delete non-persistent datatype '", name, "' with id [null].");
            throw new PersistenceException("Cannot delete non-persistent datatype '" + name + "'.");
        }

        try {
            datatype = this.em.merge(datatype);
            this.em.remove(datatype);

            datatype.setDatatypeState(DatatypeState.DESTROYED);

        } catch (Exception e) {
            logger.error(e, "Cannot delete datatype '", name, "'.");
            throw PersistenceExceptionMapper.resolve(e, name, datatype.getId());
        }

        return datatype;
    }

    /**
     * (Re-)Loads the datatype from the database.
     * 
     * @param datatype
     *            the datatype to load
     * 
     * @return the loaded datatype
     * 
     * @throws PersistenceException
     */
    @SuppressWarnings("unused")
    private NabuccoDatatype findDatatype(NabuccoDatatype datatype) throws PersistenceException {

        String name = datatype.getClass().getName();
        NabuccoDatatype managedDatatype = this.em.find(datatype.getClass(), datatype.getId());

        if (managedDatatype == null) {
            String msg = "Cannot find '" + name + "' with id " + datatype.getId() + ".";
            throw new EntityNotFoundException(msg);
        }
        if (!managedDatatype.getVersion().equals(datatype.getVersion())) {
            String msg = "Concurrent access to '" + name + "' with id " + datatype.getId() + ".";
            throw new OptimisticLockException(msg);
        }

        // Cached entity instances should not be touched.
        if (managedDatatype.getDatatypeState() == DatatypeState.CONSTRUCTED) {
            managedDatatype.setDatatypeState(DatatypeState.PERSISTENT);
        }

        return managedDatatype;
    }

    /**
     * Synchronize the persistence context to the underlying database.
     */
    public void flush() {
        this.em.flush();
    }
}
