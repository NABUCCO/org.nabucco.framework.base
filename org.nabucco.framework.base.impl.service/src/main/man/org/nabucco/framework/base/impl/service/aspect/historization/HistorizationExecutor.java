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
package org.nabucco.framework.base.impl.service.aspect.historization;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.aspect.Aspect;
import org.nabucco.framework.base.impl.service.aspect.AspectException;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutionContext;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutor;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutorSupport;
import org.nabucco.framework.base.impl.service.aspect.constraining.ConstrainingAspect;
import org.nabucco.framework.base.impl.service.aspect.constraining.ConstrainingException;

/**
 * Executes an historization aspect for a service operation.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 */
public class HistorizationExecutor extends AspectExecutorSupport implements AspectExecutor {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(HistorizationExecutor.class);

    @Override
    public void executeBeforeAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request)
            throws AspectException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);

            HistorizationAspect historizationAspect = (HistorizationAspect) aspect;

            if (historizationAspect instanceof HistorizationAspectSupport) {
                HistorizationAspectSupport support = (HistorizationAspectSupport) aspect;
                support.setServiceContext(request.getContext());
            }

            historizationAspect.contrainBefore(requestMessage);

        } catch (HistorizationException ce) {
            logger.error(ce, "Unable to constrain message.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Unable to constrain message.");
            throw new ConstrainingException("Unable to constrain message.", e);
        }
    }

    @Override
    public void executeAfterAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request,
            ServiceResponse<?> response) throws AspectException {

        try {
            ServiceMessage requestMessage = super.getRequestMessage(request);
            ServiceMessage responseMessage = super.getResponseMessage(response);

            ConstrainingAspect constrainingAspect = (ConstrainingAspect) aspect;

            if (constrainingAspect instanceof HistorizationAspectSupport) {
                HistorizationAspectSupport support = (HistorizationAspectSupport) aspect;
                support.setServiceContext(request.getContext());
            }

            constrainingAspect.contrainAfter(requestMessage, responseMessage);

        } catch (ConstrainingException ce) {
            logger.error(ce, "Unable to constrain message.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Unable to constrain message.");
            throw new ConstrainingException("Unable to constrain message.", e);
        }
    }

}
