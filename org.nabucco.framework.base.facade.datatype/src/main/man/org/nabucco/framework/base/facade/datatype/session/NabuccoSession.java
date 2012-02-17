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
package org.nabucco.framework.base.facade.datatype.session;

import java.util.Locale;

import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.session.authorization.SecurityContext;
import org.nabucco.framework.base.facade.datatype.session.service.ServiceContext;

/**
 * NabuccoSession
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoSession {

    private SecurityContext securityContext;

    private ServiceContext serviceContext;

    private Locale locale = Locale.getDefault();

    /**
     * Getter for the securityContext.
     * 
     * @return Returns the securityContext.
     */
    public SecurityContext getSecurityContext() {
        if (this.securityContext == null) {
            this.securityContext = new SecurityContext();
        }
        return this.securityContext;
    }

    /**
     * Getter for the serviceContext.
     * 
     * @return Returns the serviceContext.
     */
    public ServiceContext getServiceContext() {
        if (this.serviceContext == null) {
            this.serviceContext = new ServiceContext();
        }
        return this.serviceContext;
    }

    /**
     * Getter for the current owner.
     * 
     * @return Returns the owner.
     */
    public Owner getOwner() {
        if (this.getSecurityContext().getSubject() == null) {
            return null;
        }
        return this.getSecurityContext().getSubject().getOwner();
    }

    /**
     * Getter for the locale.
     * 
     * @return Returns the locale.
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Setter for the locale.
     * 
     * @param locale
     *            The locale to set.
     */
    public void setLocale(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        this.locale = locale;
    }

}
