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
package org.nabucco.framework.base.facade.component.decorator;

import java.lang.reflect.Proxy;

import org.nabucco.framework.base.facade.component.Component;

/**
 * ComponentDecorator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentDecorator {

    /**
     * Decorates a {@link Component} with an appropriate {@link ComponentDelegate}.
     * 
     * @param <T>
     *            the Component
     * @param target
     *            the component instance
     * @param view
     *            the component interface
     * @param handler
     *            the delegate
     * 
     * @return component proxy
     */
    @SuppressWarnings("unchecked")
    public static <T extends Component> T decorate(T target, Class<T> view,
            ComponentDelegate handler) {
        handler.setTargetObject(target);

        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[] { view },
                handler);
    }

    /**
     * Decorates a {@link Component} with a {@link TracingComponentInvocationHandler} for tracing
     * purposes.
     * 
     * @param <T>
     *            the Component
     * @param target
     *            the component instance
     * @param view
     *            the component interface
     * 
     * @return component proxy
     */
    public static <T extends Component> T trace(T target, Class<T> view) {
        return decorate(target, view, new TracingComponentInvocationHandler());
    }

}
