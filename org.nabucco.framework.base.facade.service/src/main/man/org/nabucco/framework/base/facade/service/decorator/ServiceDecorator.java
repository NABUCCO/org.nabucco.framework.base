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
package org.nabucco.framework.base.facade.service.decorator;

import java.lang.reflect.Proxy;

import org.nabucco.framework.base.facade.service.Service;

/**
 * ServiceDecorator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ServiceDecorator {

    /**
     * Decorates a {@link Service} with an appropriate {@link ServiceDelegate}.
     * 
     * @param <T>
     *            the Service
     * @param target
     *            the service instance
     * @param view
     *            the service interface
     * @param handler
     *            the delegate
     * 
     * @return service proxy
     */
    @SuppressWarnings("unchecked")
    public static <T extends Service> T decorate(T target, Class<T> view, ServiceDelegate handler) {
        handler.setTargetObject(target);

        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[] { view }, handler);
    }

    /**
     * Decorates a {@link Service} with a {@link TracingServiceInvocationHandler} for tracing
     * purposes.
     * 
     * @param <T>
     *            the Service
     * @param target
     *            the service instance
     * @param view
     *            the service interface
     * 
     * @return service proxy
     */
    public static <T extends Service> T trace(T target, Class<T> view) {
        return decorate(target, view, new TracingServiceInvocationHandler());
    }

}
