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

import com.vaadin.ui.ComponentContainer;

/**
 * The main interface for all UI views. These views act as a container for
 * other more simple components an may be placed on a window. Instances of this class
 * hold references on an {@link UIComponentModel}. They also need an {@link UIComponentViewLayout} 
 * and an {@link UIComponentViewContent} reference to display contents.
 * 
 * @author Oliver Teichmann, PRODYNA AG
 * 
 */
public interface UIComponentView extends ComponentContainer {

	UIComponentViewContent getViewContent();
	
	UIComponentModel getModel();

}
