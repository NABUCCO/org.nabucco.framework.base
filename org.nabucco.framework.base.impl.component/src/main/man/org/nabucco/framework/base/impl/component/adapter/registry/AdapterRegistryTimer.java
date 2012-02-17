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
package org.nabucco.framework.base.impl.component.adapter.registry;

import java.util.Set;

import org.nabucco.framework.base.facade.component.adapter.Adapter;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.component.locator.LookupUtility;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.message.ping.PingRequest;
import org.nabucco.framework.base.facade.message.ping.PingResponse;
import org.nabucco.framework.base.facade.message.ping.PingStatus;
import org.nabucco.framework.base.impl.service.timer.Timer;
import org.nabucco.framework.base.impl.service.timer.TimerExecutionException;
import org.nabucco.framework.base.impl.service.timer.TimerPriority;

/**
 * AdapterTimer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AdapterRegistryTimer extends Timer {

    private static final long serialVersionUID = 1L;

    private static final String NAME = "AdapterTimer";

    private static final TimerPriority PRIORITY = TimerPriority.MEDIUM;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(AdapterRegistryTimer.class);

    /**
     * Creates a new {@link AdapterTimer} instance.
     */
    public AdapterRegistryTimer() {
        super(NAME, PRIORITY);
    }

    @Override
    public void execute() throws TimerExecutionException {
        Set<AdapterRegistryEntry> registryEntry = AdapterRegistryLocator.getRegistry().getAll();

        for (AdapterRegistryEntry entry : registryEntry) {

            try {

                long start = NabuccoSystem.getCurrentTimeMillis();
                PingResponse response = this.ping(entry);
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = end - start;

                switch (response.getStatus()) {

                case AVAILABLE: {
                    logger.debug("Ping of Adapter ", entry, " finished successful after ", duration, "ms.");
                    entry.setLastTime(start);
                    entry.setLastDuration(duration);
                    break;
                }

                case UNAVAILABLE: {
                    logger.warning("Adapter ", entry, " is currently unavailable.");
                    break;
                }

                case ERROR: {
                    logger.warning("Ping of Adapter ", entry, " finished with errors.");
                    break;
                }

                }

                entry.setStatus(response.getStatus());

            } catch (TimerExecutionException tee) {
                logger.warning("Ping of Adapter ", entry, " finished with errors.");
                entry.setStatus(PingStatus.ERROR);
            } catch (Exception e) {
                logger.error(e, "Error pinging adapter '", entry.getName(), "'.");
                entry.setStatus(PingStatus.ERROR);
            }
        }
    }

    /**
     * Ping the registered adapter.
     * 
     * @param entry
     *            the adapter registry entry
     * @param request
     *            the ping request
     * 
     * @return the ping response
     * 
     * @throws TimerExecutionException
     *             when the ping failed
     */
    private PingResponse ping(AdapterRegistryEntry entry) throws TimerExecutionException {

        PingRequest request = new PingRequest();
        request.setJndiName(entry.getResourceJndi());

        String adapterName = entry.getName();
        Class<Adapter> adapterClass = this.loadClass(adapterName);

        try {
            Adapter adapter = LookupUtility.getAdapter(adapterClass, entry.getAdapterJndi(), ConnectionFactory
                    .getInstance().createLocalConnection());

            return adapter.ping(request);

        } catch (ConnectionException ce) {
            logger.error(ce, "Cannot establish connection to adapter '", adapterName, "'.");
            throw new TimerExecutionException("Cannot establish connection to adapter '" + adapterName + "'.", ce);
        }
    }

    /**
     * Load the adapter class from the current classloader.
     * 
     * @param adapterName
     *            the class name of the adapter
     * 
     * @return the loaded adapter class
     */
    private Class<Adapter> loadClass(String adapterName) throws TimerExecutionException {

        try {
            ClassLoader classLoader = super.getClass().getClassLoader();

            @SuppressWarnings("unchecked")
            Class<Adapter> adapterClass = (Class<Adapter>) classLoader.loadClass(adapterName);
            return adapterClass;

        } catch (ClassNotFoundException cnfe) {
            logger.error(cnfe, "Cannot find adapter class '", adapterName, "' in classloader.");
            throw new TimerExecutionException("Cannot find adapter class '" + adapterName + "' in classloader.", cnfe);
        }
    }
}
