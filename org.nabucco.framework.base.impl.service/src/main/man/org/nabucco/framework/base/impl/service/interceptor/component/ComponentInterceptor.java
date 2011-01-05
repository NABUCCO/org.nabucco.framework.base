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
package org.nabucco.framework.base.impl.service.interceptor.component;

import javax.interceptor.InvocationContext;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.impl.service.interceptor.Interceptor;

/**
 * ComponentInterceptor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ComponentInterceptor.class);

    @Override
    public Object intercept(InvocationContext context) throws NabuccoException {
        try {
            return context.proceed();
        } catch (Exception e) {
            logger.error(e, "Component invocation raised an exception.");
            throw new NabuccoException(e);
        }
    }

}
