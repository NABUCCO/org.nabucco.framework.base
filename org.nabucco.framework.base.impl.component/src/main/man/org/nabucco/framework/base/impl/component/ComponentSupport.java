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
package org.nabucco.framework.base.impl.component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.Service;

/**
 * ComponentSupport
 * <p/>
 * Abstract support class for component implementations. Should be overriden for correct and
 * expected component implementation.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ComponentSupport implements Component {

    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Logger */
    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(this.getClass());

    @Override
    public String getName() {
        Class<?>[] interfaces = this.getClass().getInterfaces();
        if (interfaces.length > 0) {
            return interfaces[0].getCanonicalName();
        }

        return null;
    }

    @Override
    public String getJndiName() {
        return null;
    }

    /**
     * Getter for the NABUCCO logger.
     * 
     * @return Returns the logger.
     */
    protected NabuccoLogger getLogger() {
        return this.logger;
    }

    /**
     * Called after component construction.
     */
    public void postConstruct() {
        if (logger.isDebugEnabled()) {
            logger.debug("Starting up component ", this.getName(), ".");
        }
    }

    /**
     * Called before component destruction.
     */
    public void preDestroy() {
        if (logger.isDebugEnabled()) {
            logger.debug("Shutting down component ", this.getName(), ".");
        }
    }

    /**
     * Lookup the given service from the JNDI tree.
     * 
     * @param <S>
     *            the service to locate
     * @param jndiName
     *            the service jndi name
     * @param serviceClass
     *            the service class to narrow to
     * 
     * @return the located service implementation
     * 
     * @throws ServiceException
     *             when the service cannot be located
     */
    @SuppressWarnings("unchecked")
    protected <S extends Service> S lookup(String jndiName, Class<S> serviceClass) throws ServiceException {
        try {
            Context context = new InitialContext();
            Object ref = context.lookup(jndiName);
            return (S) PortableRemoteObject.narrow(ref, serviceClass);
        } catch (NamingException e) {
            throw new ServiceException("Error locating service from JNDI '" + jndiName + "'.", e);
        } catch (Exception e) {
            throw new ServiceException("Error locating service from JNDI '" + jndiName + "'.", e);
        }
    }

}
