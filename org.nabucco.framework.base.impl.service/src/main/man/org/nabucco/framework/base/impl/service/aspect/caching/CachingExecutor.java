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
package org.nabucco.framework.base.impl.service.aspect.caching;

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
 * Executor for caching aspects.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class CachingExecutor extends AspectExecutorSupport implements AspectExecutor {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(CachingExecutor.class);

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void executeBeforeAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request)
            throws AspectException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessageContext serviceContext = super.getServiceContext(request);

            CachingAspect cachingAspect = (CachingAspect) aspect;
            cachingAspect.setContext(serviceContext);
            cachingAspect.before(requestMessage);

            if (cachingAspect.isCached()) {
                ServiceResponse response = new ServiceResponse(request.getContext());
                response.setResponseMessage(cachingAspect.getResponseMessage());
                context.setServiceResponse(response);
                context.setShortenExecution(true);
            }

        } catch (CachingException ce) {
            logger.error(ce, "Unable to load cached service message.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Unable to load cached service message.");
            throw new CachingException("Unable to load cached service message.", e);
        }
    }

    @Override
    public void executeAfterAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request,
            ServiceResponse<?> response) throws AspectException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessage responseMessage = super.getResponseMessage(response);
            ServiceMessageContext serviceContext = super.getServiceContext(request);

            CachingAspect cachingAspect = (CachingAspect) aspect;
            cachingAspect.setContext(serviceContext);
            cachingAspect.after(requestMessage, responseMessage);
        } catch (CachingException ce) {
            logger.error(ce, "Unable to cache service message.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Unable to cache service message.");
            throw new CachingException("Unable to cache service message.", e);
        }
    }

}
