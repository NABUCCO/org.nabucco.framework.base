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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.impl.service.entitylistener.logging.DatatypeLoggingEntityListener;
import org.nabucco.framework.base.impl.service.entitylistener.state.DatatypeStateEntityListener;

/**
 * NabuccoEntityListener
 * <p/>
 * The entity listener for NABUCCO persistence operations. Listens on each entity (persistent
 * datatype) insertion to database and load from datatbase.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoEntityListener {

    private static final long serialVersionUID = 1L;

    private static final List<EntityListener> delegates = new ArrayList<EntityListener>();

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EntityListener.class);

    static {
        delegates.add(new DatatypeStateEntityListener());
        logger.info("Adding DatatypeStateEntityListener to NabuccoEntityListener.");

        delegates.add(new DatatypeLoggingEntityListener());
        logger.info("Adding DatatypeLoggingEntityListener to NabuccoEntityListener.");
    }

    /**
     * Called before an entity is inserted into the database.
     * 
     * @param entity
     *            the entity to persist
     */
    public void prePersist(Object entity) {
        if (!(entity instanceof Datatype)) {
            logger.warning("Entity to persist is not of type Datatype.");
            return;
        }

        for (EntityListener listener : delegates) {
            listener.postPersist((Datatype) entity);
        }
    }

    /**
     * Called after an entity is inserted into the database.
     * 
     * @param entity
     *            the persisted entity
     */
    public void postPersist(Object entity) {
        if (!(entity instanceof Datatype)) {
            logger.warning("Persisted entity is not of type Datatype.");
            return;
        }

        for (EntityListener listener : delegates) {
            listener.postPersist((Datatype) entity);
        }
    }

    /**
     * Called before an entity is updated to the database.
     * 
     * @param entity
     *            the entity to update
     */
    public void preUpdate(Object entity) {
        if (!(entity instanceof Datatype)) {
            logger.warning("Entity to update is not of type Datatype.");
            return;
        }

        for (EntityListener listener : delegates) {
            listener.postPersist((Datatype) entity);
        }
    }

    /**
     * Called after an entity is update to the database.
     * 
     * @param entity
     *            the updated entity
     */
    public void postUpdate(Object entity) {
        if (!(entity instanceof Datatype)) {
            logger.warning("Updated entity is not of type Datatype.");
            return;
        }

        for (EntityListener listener : delegates) {
            listener.postUpdate((Datatype) entity);
        }
    }

    /**
     * Called before an entity is removed from the database.
     * 
     * @param entity
     *            the entity to remove
     */
    public void preRemove(Object entity) {
        if (!(entity instanceof Datatype)) {
            logger.warning("Entity to remove is not of type Datatype.");
            return;
        }

        for (EntityListener listener : delegates) {
            listener.postPersist((Datatype) entity);
        }
    }

    /**
     * Called after an entity is removed from the database.
     * 
     * @param entity
     *            the removed entity
     */
    public void postRemove(Object entity) {
        if (!(entity instanceof Datatype)) {
            logger.warning("Removed entity is not of type Datatype.");
            return;
        }

        for (EntityListener listener : delegates) {
            listener.postRemove((Datatype) entity);
        }
    }

    /**
     * Called after an entity is load from the database.
     * 
     * @param entity
     *            the loaded entity
     */
    public void postLoad(Object entity) {
        if (!(entity instanceof Datatype)) {
            logger.warning("Loaded entity is not of type Datatype.");
            return;
        }

        for (EntityListener listener : delegates) {
            listener.postLoad((Datatype) entity);
        }
    }

}
