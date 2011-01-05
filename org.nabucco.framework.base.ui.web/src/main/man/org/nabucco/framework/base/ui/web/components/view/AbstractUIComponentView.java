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

import org.nabucco.framework.base.ui.web.components.model.UIComponentModel;

import com.vaadin.ui.CustomComponent;

/**
 * Abstract implementation of {@link UIComponentView} interface.
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
public abstract class AbstractUIComponentView extends CustomComponent implements UIComponentView {

	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	 
	private final UIComponentModel model;
	
	public AbstractUIComponentView(UIComponentModel model) {
		this.model = model;
	}
	
	/* (non-Javadoc)
	 * @see org.nabucco.framework.base.ui.web.view.NabuccoView#getModel()
	 */
	@Override
	public UIComponentModel getModel() {
		return this.model;
	}

}
