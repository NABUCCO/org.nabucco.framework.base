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
package org.nabucco.framework.base.impl.component.adapter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.nabucco.framework.base.facade.component.adapter.Adapter;
import org.nabucco.framework.base.facade.component.handler.PingHandler;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ping.PingRequest;
import org.nabucco.framework.base.facade.message.ping.PingResponse;
import org.nabucco.framework.base.facade.message.ping.PingStatus;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.component.adapter.registry.AdapterRegistryLocator;

/**
 * AdapterSupport
 * <p/>
 * Abstract support class for adapter implementations. Should be overriden for correct and expected
 * adapter implementation.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class AdapterSupport implements Adapter {

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
    public String getId() {
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

    @Override
    public final PingResponse ping(PingRequest request) {
        InjectionProvider injector = InjectionProvider.getInstance(this.getId());
        PingHandler handler = injector.inject(PingHandler.getId());

        if (handler == null) {
            this.getLogger().warning("No ping handler configured for adapter '", this.getId(), "'.");
            return new PingResponse(PingStatus.UNAVAILABLE, NabuccoSystem.getCurrentTimeMillis(), -1);
        }

        try {
            handler.setLocatable(this);
            handler.setLogger(this.getLogger());
            return handler.ping(request);
        } catch (Exception e) {
            this.getLogger().error(e, "Adapter '", this.getId(), "' is not available.");
            return new PingResponse(PingStatus.ERROR, NabuccoSystem.getCurrentTimeMillis(), -1);
        }
    }

    /**
     * Called after adapter construction.
     */
    public void postConstruct() {
        if (logger.isDebugEnabled()) {
            logger.debug("Starting up adapter ", this.getName(), ".");
        }

        AdapterRegistryLocator.getRegistry().register(this.getName(), this.getJndiName());
    }

    /**
     * Called before adapter destruction.
     */
    public void preDestroy() {
        if (logger.isDebugEnabled()) {
            logger.debug("Shutting down adapter ", this.getName(), ".");
        }

        AdapterRegistryLocator.getRegistry().unregister(this.getName());
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
