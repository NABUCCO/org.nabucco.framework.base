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
package org.nabucco.framework.base.impl.service.aspect.initializing;

import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.AspectSupport;

/**
 * InitializingAspectSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class InitializingAspectSupport extends AspectSupport {

    /**
     * Additional aspect properties are configured here.
     * 
     * @param properties
     *            the property extension
     */
    public void configure(PropertyExtension properties) {
    }

    /**
     * Converting the datatype content of a service message to a fulltext document for indexing.
     * 
     * @param requestMessage
     *            The request message of the service operation.
     * 
     * @throws InitializingException
     *             Thrown if converting cannot be done.
     */
    public void initializeBefore(ServiceMessage requestMessage) throws InitializingException {
        // Nothing to do before!
    }
}
