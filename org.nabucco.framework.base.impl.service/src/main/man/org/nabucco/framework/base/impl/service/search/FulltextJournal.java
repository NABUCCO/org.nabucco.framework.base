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
package org.nabucco.framework.base.impl.service.search;

import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.journaling.JournalingAspect;
import org.nabucco.framework.base.impl.service.aspect.journaling.JournalingException;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class FulltextJournal implements JournalingAspect {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(FulltextJournal.class);

    @Override
    public void journalBefore(ServiceMessage requestMessage) throws JournalingException {
        logger.info("Journaling [before] called");
    }

    @Override
    public void journalAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws JournalingException {
        logger.info("Journaling [after] called");
    }

    @Override
    public void configure(PropertyExtension properties) {
    }

}
