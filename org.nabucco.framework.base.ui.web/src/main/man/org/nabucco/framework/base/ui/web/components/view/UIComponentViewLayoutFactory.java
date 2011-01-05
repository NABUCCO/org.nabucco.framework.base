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
package org.nabucco.framework.base.ui.web.components.view;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.service.injection.InjectionProvider;

/**
 * The {@link UIComponentViewLayoutFactory} resolves the configured layouts for 
 * {@link UIComponentView} implementations.
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
public class UIComponentViewLayoutFactory {
	
    /** Injector instances. */
    private static final Map<String, SoftReference<UIComponentViewLayoutFactory>> FACTORY_MAP = Collections
            .synchronizedMap(new HashMap<String, SoftReference<UIComponentViewLayoutFactory>>());
	
	private final InjectionProvider injectionProvider;
	
    public static synchronized UIComponentViewLayoutFactory getInstance(String id) {

        if (FACTORY_MAP.containsKey(id) && FACTORY_MAP.get(id).get() != null) {
            return FACTORY_MAP.get(id).get();
        }
        
        UIComponentViewLayoutFactory factory = new UIComponentViewLayoutFactory(id);

        FACTORY_MAP.put(id, new SoftReference<UIComponentViewLayoutFactory>(factory));

        return factory;
    }
	
	/**
	 * 
	 */
	private UIComponentViewLayoutFactory(String id) {
		
		this.injectionProvider = InjectionProvider.getInstance(id);
	}
	
	/**
	 * @param uIComponentView
	 * @return
	 */
	public UIComponentViewLayout getViewLayout(UIComponentView uIComponentView) {
		
		String viewName = uIComponentView.getClass().getName();
		UIComponentViewLayout viewLayout = this.injectionProvider.inject(viewName);
		if(viewLayout == null) {
			throw new RuntimeException("No Layout configured for view " + viewName);
		}
		
		return viewLayout;
	}

}
