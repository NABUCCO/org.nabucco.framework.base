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
 * InitializingAspectExecutor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class InitializingExecutor extends AspectExecutorSupport implements AspectExecutor {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            InitializingExecutor.class);

    @Override
    public void executeBeforeAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request)
            throws AspectException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessageContext serviceContext = super.getServiceContext(request);

            InitializingAspect initializingAspect = (InitializingAspect) aspect;
            if (initializingAspect instanceof InitializingAspectSupport) {
                InitializingAspectSupport support = (InitializingAspectSupport) initializingAspect;
                support.setContext(serviceContext);
            }

            initializingAspect.initializeBefore(requestMessage);

        } catch (InitializingException ie) {
            logger.error(ie, "Error initializing before service.");
            throw ie;
        } catch (Exception e) {
            logger.error(e, "Error initializing before service.");
            throw new InitializingException("Error initializing before service.", e);
        }
    }

    @Override
    public void executeAfterAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request,
            ServiceResponse<?> response) throws AspectException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessage responseMessage = super.getResponseMessage(response);
            ServiceMessageContext serviceContext = super.getServiceContext(request);

            InitializingAspect initializingAspect = (InitializingAspect) aspect;
            if (initializingAspect instanceof InitializingAspectSupport) {
                InitializingAspectSupport support = (InitializingAspectSupport) initializingAspect;
                support.setContext(serviceContext);
            }
            
            initializingAspect.initializeAfter(requestMessage, responseMessage);

        } catch (InitializingException ie) {
            logger.error(ie, "Error initializing after service.");
            throw ie;
        } catch (Exception e) {
            logger.error(e, "Error initializing after service.");
            throw new InitializingException("Error initializing after service.", e);
        }
    }

}
