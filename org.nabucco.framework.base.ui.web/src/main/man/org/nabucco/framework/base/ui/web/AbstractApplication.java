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
package org.nabucco.framework.base.ui.web;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.ui.web.components.view.UIComponentView;

import com.vaadin.ui.Window;


/**
 * Abstract implementation of {@link Application} interface.
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
public abstract class AbstractApplication extends com.vaadin.Application implements Application {

	/** Default serial version ID */
	private static final long serialVersionUID = 1L;
	
	/* (non-Javadoc)
	 * @see com.vaadin.Application#init()
	 */
	@Override
	public void init() {
		
		setTheme(getThemeName());
		
		Window mainWindow = new Window(getWindowName(), getRootView());
		setMainWindow(mainWindow);
	}

	/**
	 * Returns the name of the theme this application uses.
	 * 
	 * @return
	 */
	abstract protected String getThemeName();

	/**
	 * Returns the name of the main window of this application.
	 * 
	 * @return Main window name
	 */
	abstract protected String getWindowName();

	/**
	 * Returns the root view element of this application.
	 * 
	 * @return The root view
	 */
	abstract protected UIComponentView getRootView();
	
	/**
	 * Returns the logger instance of this application.
	 * 
	 * @return The logger instance
	 */
	abstract protected NabuccoLogger getLogger();
}
