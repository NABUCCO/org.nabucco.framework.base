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
package org.nabucco.framework.base.impl.service.entitylistener.logging;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.impl.service.entitylistener.EntityListener;

/**
 * DatatypeLoggingEntityListener
 * <p/>
 * Logs datatbase load/persist operations to DEBUG log.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeLoggingEntityListener implements EntityListener {

    private static final long serialVersionUID = 1L;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EntityListener.class);

    @Override
    public void postPersist(Datatype datatype) {
        if (logger.isTraceEnabled()) {

            String name = datatype.getClass().getSimpleName();

            if (!(datatype instanceof NabuccoDatatype)) {
                logger.trace("Inserted datatype ", name, " into database.");
                return;
            }

            NabuccoDatatype nabuccoDatatype = (NabuccoDatatype) datatype;
            String id = String.valueOf(nabuccoDatatype.getId());
            String version = String.valueOf(nabuccoDatatype.getVersion());

            logger.trace("Inserted datatype ", name, " with ID ", id, " and version ", version, " into database.");
        }
    }

    @Override
    public void postUpdate(Datatype datatype) {
        if (logger.isTraceEnabled()) {

            String name = datatype.getClass().getSimpleName();

            if (!(datatype instanceof NabuccoDatatype)) {
                logger.trace("Updated datatype ", name, " in database.");
                return;
            }

            NabuccoDatatype nabuccoDatatype = (NabuccoDatatype) datatype;
            String id = String.valueOf(nabuccoDatatype.getId());
            String version = String.valueOf(nabuccoDatatype.getVersion());

            logger.trace("Updated datatype ", name, " with ID ", id, " and version ", version, " in database.");
        }
    }

    @Override
    public void postRemove(Datatype datatype) {
        if (logger.isTraceEnabled()) {

            String name = datatype.getClass().getSimpleName();

            if (!(datatype instanceof NabuccoDatatype)) {
                logger.trace("Removed datatype ", name, " from database.");
                return;
            }

            NabuccoDatatype nabuccoDatatype = (NabuccoDatatype) datatype;
            String id = String.valueOf(nabuccoDatatype.getId());
            String version = String.valueOf(nabuccoDatatype.getVersion());

            logger.trace("Removed datatype ", name, " with ID ", id, " and version ", version, " from database.");
        }
    }

    @Override
    public void postLoad(Datatype datatype) {
        if (logger.isTraceEnabled()) {

            String name = datatype.getClass().getSimpleName();

            if (!(datatype instanceof NabuccoDatatype)) {
                logger.trace("Loaded datatype ", name, " from database.");
                return;
            }

            NabuccoDatatype nabuccoDatatype = (NabuccoDatatype) datatype;
            String id = String.valueOf(nabuccoDatatype.getId());
            String version = String.valueOf(nabuccoDatatype.getVersion());

            logger.trace("Loaded datatype ", name, " with ID ", id, " and version ", version, " from database.");
        }
    }

    @Override
    public void prePersist(Datatype datatype) {
        // Nothing to log before persisting!
    }

    @Override
    public void preUpdate(Datatype datatype) {
        // Nothing to log before updating!
    }

    @Override
    public void preRemove(Datatype datatype) {
        // Nothing to log before removing!
    }

}
