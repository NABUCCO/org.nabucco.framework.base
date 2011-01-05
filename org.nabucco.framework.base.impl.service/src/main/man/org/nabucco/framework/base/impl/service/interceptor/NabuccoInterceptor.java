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
package org.nabucco.framework.base.impl.service.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.impl.service.interceptor.component.ComponentInterceptor;
import org.nabucco.framework.base.impl.service.interceptor.service.ServiceInterceptor;

/**
 * NabuccoInterceptor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    private Interceptor delegate;

    @Override
    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {

        Object target = ctx.getTarget();

        if (target instanceof Component) {
            this.delegate = new ComponentInterceptor();
        } else if (target instanceof Service) {
            this.delegate = new ServiceInterceptor();
        } else {
            this.delegate = new DefaultInterceptor();
        }

        return this.delegate.intercept(ctx);
    }

}
