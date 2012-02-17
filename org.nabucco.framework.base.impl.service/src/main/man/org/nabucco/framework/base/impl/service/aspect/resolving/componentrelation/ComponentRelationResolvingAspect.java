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
package org.nabucco.framework.base.impl.service.aspect.resolving.componentrelation;

import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.resolving.ResolvingAspect;
import org.nabucco.framework.base.impl.service.aspect.resolving.ResolvingException;
import org.nabucco.framework.base.impl.service.aspect.resolving.ResolvingSupport;

/**
 * ComponentRelationResolvingAspect
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationResolvingAspect extends ResolvingSupport implements ResolvingAspect {

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ComponentRelationResolvingAspect.class);

    @Override
    public void configure(PropertyExtension properties) {
    }

    @Override
    public void resolveBefore(ServiceMessage requestMessage) throws ResolvingException {
        // Nothing to do before!
    }

    @Override
    public void resolveAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws ResolvingException {

        if (responseMessage == null) {
            return;
        }

        try {
            ComponentRelationResolvingVisitor visitor = new ComponentRelationResolvingVisitor(super.getContext());
            responseMessage.accept(visitor);
        } catch (Exception e) {
            logger.error(e, "Error resolving component relations for '" + requestMessage.getClass().getName() + "'.");
        }
    }

}
