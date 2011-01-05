/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.impl.service.interceptor.service.connector;

import java.lang.reflect.Method;

import org.nabucco.framework.base.facade.component.NabuccoInstance;
import org.nabucco.framework.base.facade.component.application.Application;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContext;
import org.nabucco.framework.base.impl.service.interceptor.service.ServiceInterceptorStrategy;

/**
 * DatatypeConnectorInterceptorStrategy
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeConnectorInterceptorStrategy implements ServiceInterceptorStrategy {

    @Override
    public void beforeInvocation(InterceptorContext context, Service service, Method operation,
            ServiceRequest<?> request, NabuccoLogger logger) throws ServiceException {
        // Nothing to do before invocation!
    }

    @Override
    public void afterInvocation(InterceptorContext context, Service service, Method operation,
            ServiceRequest<?> request, ServiceResponse<?> response, NabuccoLogger logger,
            Throwable exception) throws ServiceException {

        Application application = NabuccoInstance.getInstance().getApplication();

        if (application == null) {
            logger.warning("No application has been configured for NabuccoInstance.");
            return;
        }

        try {
            DatatypeConnectorVisitor visitor = new DatatypeConnectorVisitor(request, response);
            response.getResponseMessage().accept(visitor);
        } catch (VisitorException e) {
            logger.error(e, "Error visiting response datatypes.");
        } catch (Exception e) {
            logger.error(e, "Error visiting response datatypes.");
        }
    }

}
