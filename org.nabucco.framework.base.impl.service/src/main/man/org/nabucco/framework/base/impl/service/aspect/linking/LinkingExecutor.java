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
package org.nabucco.framework.base.impl.service.aspect.linking;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.aspect.Aspect;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutionContext;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutor;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutorSupport;

/**
 * LinkingExecutor
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class LinkingExecutor extends AspectExecutorSupport implements AspectExecutor {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(LinkingExecutor.class);

    @Override
    public void executeBeforeAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request)
            throws LinkingException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);

            LinkingAspect linkingAspect = (LinkingAspect) aspect;
            linkingAspect.linkBefore(requestMessage);

        } catch (LinkingException le) {
            logger.error(le, "Unable to link message.");
            throw le;
        } catch (Exception e) {
            logger.error(e, "Unable to link message.");
            throw new LinkingException("Unable to constrain message.", e);
        }
    }

    @Override
    public void executeAfterAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request,
            ServiceResponse<?> response) throws LinkingException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessage responseMessage = super.getResponseMessage(response);

            LinkingAspect linkingAspect = (LinkingAspect) aspect;
            linkingAspect.linkAfter(requestMessage, responseMessage);

        } catch (LinkingException le) {
            logger.error(le, "Unable to link message.");
            throw le;
        } catch (Exception e) {
            logger.error(e, "Unable to constrain message.");
            throw new LinkingException("Unable to constrain message.", e);
        }
    }

}
