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
package org.nabucco.framework.base.impl.service.aspect.journaling;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.aspect.Aspect;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutionContext;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutor;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutorSupport;

/**
 * JournalingExecutor
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class JournalingExecutor extends AspectExecutorSupport implements AspectExecutor {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(JournalingExecutor.class);

    @Override
    public void executeBeforeAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request)
            throws JournalingException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessageContext serviceContext = super.getServiceContext(request);

            JournalingAspect journalingAspect = (JournalingAspect) aspect;

            if (journalingAspect instanceof JournalingAspectSupport) {
                JournalingAspectSupport support = (JournalingAspectSupport) aspect;
                support.setServiceContext(serviceContext);
            }

            journalingAspect.journalBefore(requestMessage);

        } catch (JournalingException je) {
            logger.error(je, "Cannot execute journaling before aspect.");
            throw je;
        } catch (Exception e) {
            logger.error(e, "Cannot execute journaling before aspect.");
            throw new JournalingException("Cannot execute journaling before aspect.", e);
        }
    }

    @Override
    public void executeAfterAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request,
            ServiceResponse<?> response) throws JournalingException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessage responseMessage = super.getResponseMessage(response);
            ServiceMessageContext serviceContext = super.getServiceContext(request);

            JournalingAspect journalingAspect = (JournalingAspect) aspect;

            if (journalingAspect instanceof JournalingAspectSupport) {
                JournalingAspectSupport support = (JournalingAspectSupport) aspect;
                support.setServiceContext(serviceContext);
            }

            journalingAspect.journalAfter(requestMessage, responseMessage);

        } catch (JournalingException je) {
            logger.error(je, "Cannot execute journaling after aspect.");
            throw je;
        } catch (Exception e) {
            logger.error(e, "Cannot execute journaling after aspect.");
            throw new JournalingException("Cannot execute journaling after aspect.", e);
        }
    }

}
