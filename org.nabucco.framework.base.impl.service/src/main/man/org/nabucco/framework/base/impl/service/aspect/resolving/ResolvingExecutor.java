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
package org.nabucco.framework.base.impl.service.aspect.resolving;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.aspect.Aspect;
import org.nabucco.framework.base.impl.service.aspect.AspectException;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutionContext;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutor;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutorSupport;

/**
 * Executes aspect for resolving data like DynamicCodes or User.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class ResolvingExecutor extends AspectExecutorSupport implements AspectExecutor {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ResolvingExecutor.class);

    @Override
    public void executeBeforeAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request)
            throws AspectException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessageContext serviceContext = super.getServiceContext(request);

            ResolvingAspect resolvingAspect = (ResolvingAspect) aspect;
            resolvingAspect.setContext(serviceContext);
            resolvingAspect.resolveBefore(requestMessage);

        } catch (ResolvingException ce) {
            logger.error(ce, "Error during 'BEFORE' aspect resolving.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error during 'BEFORE' aspect resolving.");
            throw new ResolvingException("Error during 'BEFORE' aspect resolving.", e);
        }
    }

    @Override
    public void executeAfterAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request,
            ServiceResponse<?> response) throws AspectException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessage responseMessage = super.getResponseMessage(response);
            ServiceMessageContext serviceContext = super.getServiceContext(request);

            ResolvingAspect resolvingAspect = (ResolvingAspect) aspect;
            resolvingAspect.setContext(serviceContext);
            resolvingAspect.resolveAfter(requestMessage, responseMessage);

        } catch (ResolvingException ce) {
            logger.error(ce, "Error during 'AFTER' aspect resolving.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error during 'AFTER' aspect resolving.");
            throw new ResolvingException("Error during 'AFTER' aspect resolving.", e);
        }
    }
}
