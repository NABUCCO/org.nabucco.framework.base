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
package org.nabucco.framework.base.ui.web.components;


/**
 * A NABUCCO specific implementation of the {@link com.vaadin.ui.GridLayout} class.
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
public class GridLayout extends com.vaadin.ui.GridLayout {

	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * 
	 */
	public GridLayout() {
		super();
		setDefaults();
	}

	/**
	 * @param columns
	 * @param rows
	 */
	public GridLayout(int columns, int rows) {
		super(columns, rows);
		setDefaults();
	}
	
	/**
	 * 
	 */
	private void setDefaults() {
		// Use top-left margin and spacing
        setMargin(true, false, false, true);
        setSpacing(true);
	}
}
