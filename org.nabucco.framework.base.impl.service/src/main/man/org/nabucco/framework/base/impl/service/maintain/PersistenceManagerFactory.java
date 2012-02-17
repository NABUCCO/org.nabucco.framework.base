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

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;

/**
 * PersistenceManagerFactory
 * <p/>
 * Manages the of persistence managers instances.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PersistenceManagerFactory {

    /**
     * Singleton instance.
     */
    private static PersistenceManagerFactory instance = new PersistenceManagerFactory();

    /**
     * Private constructor.
     */
    private PersistenceManagerFactory() {
    }

    /**
     * Singleton access.
     * 
     * @return the PersistenceManagerFactory instance.
     */
    public static PersistenceManagerFactory getInstance() {
        return instance;
    }

    /**
     * Create a new instance of the {@link PersistenceManager}.
     * 
     * @param entityManager
     *            the entity manager
     * 
     * @return the persistence manager
     */
    public PersistenceManager createPersistenceManager(EntityManager entityManager) {
        return new PersistenceManagerImpl(entityManager, null);
    }

    /**
     * Create a new instance of the {@link PersistenceManager}.
     * 
     * @param entityManager
     *            the entity manager
     * @param logger
     *            the nabucco logger
     * 
     * @return the persistence manager
     */
    public PersistenceManager createPersistenceManager(EntityManager entityManager, NabuccoLogger logger) {
        return new PersistenceManagerImpl(entityManager, logger);
    }

}
