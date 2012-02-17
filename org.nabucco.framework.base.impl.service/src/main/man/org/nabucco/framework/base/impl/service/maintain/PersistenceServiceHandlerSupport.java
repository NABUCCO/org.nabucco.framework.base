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

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.ServiceHandlerSupport;

/**
 * PersistenceServiceHandlerSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class PersistenceServiceHandlerSupport extends ServiceHandlerSupport {

    private static final long serialVersionUID = 1L;

    private PersistenceManager persistenceManager;

    /**
     * Setter for the persistence manager instance.
     * 
     * @param persistenceManager
     *            the persistence manager
     */
    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    /**
     * Getter for the persistence manager.
     * 
     * @return the persistence manager
     */
    protected PersistenceManager getPersistenceManager() {
        return this.persistenceManager;
    }

    @Override
    protected <T extends ServiceMessage> void cleanServiceMessage(T message) throws ServiceException {
        if (message == null) {
            throw new IllegalArgumentException("Cannot clean service message [null].");
        }

        try {
            this.persistenceManager.flush();
            this.persistenceManager.clear();

            PersistenceCleaner cleaner = new PersistenceCleaner();
            message.accept(cleaner);

        } catch (PersistenceException pe) {
            super.getLogger().error(pe, "Error flushing entity state to database.");
            throw new ServiceException("Error flushing entity state to database.", pe);
        } catch (VisitorException ve) {
            super.getLogger().error(ve, "Cannot clean service message '", message.getClass().getName(), "'.");
            throw new ServiceException("Cannot clean service message '" + message.getClass().getName() + "'.", ve);
        } catch (Exception e) {
            super.getLogger().error(e, "Cannot clean service message '", message.getClass().getName(), "'.");
            throw new ServiceException("Cannot clean service message '" + message.getClass().getName() + "'.", e);
        }
    }

}
