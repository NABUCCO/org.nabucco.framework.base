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
package org.nabucco.framework.base.ui.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.nabucco.framework.base.ui.web.AbstractApplication;

import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * Abstract implementation of {@link ApplicationPortlet} interface. All instances of this class also implement
 * the {@link PortletListener} interface to handle portlet specific events and requests.
 * 
 * @author Oliver Teichmann, PRODYNA AG
 * 
 */
public abstract class AbstractApplicationPortlet extends AbstractApplication
		implements ApplicationPortlet, PortletListener {

	/** Default serial version ID */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.nabucco.framework.base.ui.web.AbstractApplication#init()
	 */
	@Override
	public void init() {

		if (getContext() instanceof PortletApplicationContext2) {
			PortletApplicationContext2 ctx = (PortletApplicationContext2) getContext();
			ctx.addPortletListener(this, this);
		} else {
			getMainWindow().showNotification("Not initialized via Portal!",
					Notification.TYPE_ERROR_MESSAGE);
		}

		super.init();
	}
	
    /* (non-Javadoc)
     * @see com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener#handleRenderRequest(javax.portlet.RenderRequest, javax.portlet.RenderResponse, com.vaadin.ui.Window)
     */
    @Override
	public void handleRenderRequest(RenderRequest request,
            RenderResponse response, Window window) {
    	
    	getLogger().debug("Received RenderRequest: " + request.toString());
    }

    /* (non-Javadoc)
     * @see com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener#handleActionRequest(javax.portlet.ActionRequest, javax.portlet.ActionResponse, com.vaadin.ui.Window)
     */
    @Override
	public void handleActionRequest(ActionRequest request,
            ActionResponse response, Window window) {
    	
    	getLogger().debug("Received ActionRequest: " + request.toString());
    }

    /* (non-Javadoc)
     * @see com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener#handleEventRequest(javax.portlet.EventRequest, javax.portlet.EventResponse, com.vaadin.ui.Window)
     */
    @Override
	public void handleEventRequest(EventRequest request,
            EventResponse response, Window window) {
    	
    	getLogger().debug("Received EventRequest: " + request.toString());
    }

    /* (non-Javadoc)
     * @see com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener#handleResourceRequest(javax.portlet.ResourceRequest, javax.portlet.ResourceResponse, com.vaadin.ui.Window)
     */
    @Override
	public void handleResourceRequest(ResourceRequest request,
            ResourceResponse response, Window window) {
    	
    	getLogger().debug("Received ResourceRequest: " + request.toString());
    }

}
