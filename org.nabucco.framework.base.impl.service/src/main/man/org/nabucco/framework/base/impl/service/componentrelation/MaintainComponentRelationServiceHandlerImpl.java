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
package org.nabucco.framework.base.impl.service.componentrelation;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationState;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.persistence.EntityNotFoundException;
import org.nabucco.framework.base.facade.exception.persistence.OptimisticLockException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceExceptionMapper;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;

/**
 * MaintainComponentRelationServiceHandlerImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class MaintainComponentRelationServiceHandlerImpl extends
        MaintainComponentRelationServiceHandler {

    /** The NABUCCO logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            MaintainComponentRelationServiceHandlerImpl.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected ComponentRelationMsg maintainComponentRelation(ComponentRelationMsg msg)
            throws MaintainException {

        ComponentRelation<?> componentRelation = msg.getComponentRelation();

        try {
            this.persist(componentRelation);
        } catch (Exception pe) {
            throw new MaintainException("Error maintaining Component Relation.", pe);
        }

        return msg;
    }

    /**
     * Persists a {@link ComponentRelation} instance with its children.
     * 
     * @param componentRelation
     *            the component relation
     * 
     * @throws PersistenceException
     */
    private void persist(ComponentRelation<?> componentRelation) throws PersistenceException {

        if (componentRelation == null) {
            throw new PersistenceException("Cannot maintain Component Relation [null].");
        }

        String name = componentRelation.getClass().getName();

        try {
            switch (componentRelation.getRelationState()) {
            case INITIALIZED:
                this.createComponentRelation(componentRelation);
                break;
            case MODIFIED:
                this.modifyComponentRelation(componentRelation);
                break;
            case DELETED:
                this.deleteComponentRelation(componentRelation);
                break;
            case PERSISTENT:
                break;
            default:
                throw new PersistenceException("Component Relation state '"
                        + componentRelation.getRelationState() + "' is not valid for " + name
                        + "'.");
            }

        } catch (PersistenceException pe) {
            throw pe;
        } catch (Exception e) {
            logger.error(e, "Error persisting Component Relation '", name, "'.");
            throw PersistenceExceptionMapper.resolve(e, name, componentRelation.getId());
        }
    }

    /**
     * Creates the componentRelation in the database.
     * 
     * @param componentRelation
     *            the componentRelation
     * 
     * @throws PersistenceException
     */
    private void createComponentRelation(ComponentRelation<?> componentRelation)
            throws PersistenceException {

        String name = componentRelation.getClass().getName();

        try {
            super.getEntityManager().persist(componentRelation);
            super.getEntityManager().flush();
            super.getEntityManager().clear();

            componentRelation.setRelationState(ComponentRelationState.PERSISTENT);

        } catch (Exception e) {
            logger.error(e, "Cannot create Component Relation '", name, "'.");
            throw PersistenceExceptionMapper.resolve(e, name, componentRelation.getId());
        }
    }

    /**
     * Modifies the componentRelation in the database.
     * 
     * @param relation
     *            the componentRelation to modify
     * 
     * @throws PersistenceException
     */
    private void modifyComponentRelation(ComponentRelation<?> relation) throws PersistenceException {

        String name = relation.getClass().getName();

        if (relation.getId() == null) {
            logger.error("Cannot modify non-persistent Component Relation '", name,
                    "' with id [null].");
            throw new PersistenceException("Cannot modify non-persistent Component Relation '"
                    + name + "'.");
        }

        try {
            ComponentRelation<?> managedRelation = super.getEntityManager().merge(relation);

            super.getEntityManager().flush();
            super.getEntityManager().clear();

            // set the version id from the merged copy
            relation.setVersion(managedRelation.getVersion());
            relation.setRelationState(ComponentRelationState.PERSISTENT);

        } catch (Exception e) {
            logger.error(e, "Cannot modify Component Relation '", name, "'.");
            throw PersistenceExceptionMapper.resolve(e, name, relation.getId());
        }
    }

    /**
     * Deletes the component relation from the database.
     * 
     * @param relation
     *            the component relation to delete.
     * 
     * @throws PersistenceException
     */
    private void deleteComponentRelation(ComponentRelation<?> relation) throws PersistenceException {

        String name = relation.getClass().getName();

        if (relation.getId() == null) {
            logger.error("Cannot delete non-persistent Component Relation '", name,
                    "' with id [null].");
            throw new PersistenceException("Cannot delete non-persistent Component Relation '"
                    + name + "'.");
        }

        try {
            ComponentRelation<?> managedRelation = this.findComponentRelation(relation);

            super.getEntityManager().remove(managedRelation);
            super.getEntityManager().flush();
            super.getEntityManager().clear();

        } catch (Exception e) {
            logger.error(e, "Cannot delete Component Relation '", name, "'.");
            throw PersistenceExceptionMapper.resolve(e, name, relation.getId());
        }
    }

    /**
     * (Re-)Loads the component relation from the database.
     * 
     * @param relation
     *            the component relation to load
     * 
     * @return the loaded componentRelation
     * 
     * @throws PersistenceException
     */
    private ComponentRelation<?> findComponentRelation(ComponentRelation<?> relation)
            throws PersistenceException {

        String name = relation.getClass().getName();

        ComponentRelation<?> managedRelation = super.getEntityManager().find(relation.getClass(),
                relation.getId());

        if (managedRelation == null) {
            String msg = "Cannot find '" + name + "' with id " + relation.getId() + ".";
            throw new EntityNotFoundException(msg);
        }

        if (!managedRelation.getVersion().equals(relation.getVersion())) {
            String msg = "Concurrent access to '" + name + "' with id " + relation.getId() + ".";
            throw new OptimisticLockException(msg);
        }

        return managedRelation;
    }
}
