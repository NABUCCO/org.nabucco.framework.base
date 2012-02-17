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
package org.nabucco.framework.base.impl.service.monitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntry;
import org.nabucco.framework.base.facade.exception.service.MonitorException;

/**
 * MonitorFacade
 * <p/>
 * Facade for monitoring purpose. A monitoring processor can be registered to receive all monitoring
 * entries.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MonitorFacade {

    /** The default queue size for the temporary entry queue. */
    private static final int DEFAULT_QUEUE_SIZE = 1000;

    /** The logger. */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(MonitorFacade.class);

    /** The list of registered processors. */
    private Set<MonitorProcessor> processorRegistry = new HashSet<MonitorProcessor>();

    /** The list of temporary entries queued until a processor is registerd. */
    private List<MonitorEntry> temporaryQueue = new ArrayList<MonitorEntry>();

    /** Maximum size of the temporary entry queue. */
    private int queueSize = DEFAULT_QUEUE_SIZE;

    /**
     * Singleton instance.
     */
    private static MonitorFacade instance;

    /**
     * Private constructor.
     */
    private MonitorFacade() {
    }

    /**
     * Singleton access.
     * 
     * @return the MonitorFacade instance.
     */
    public static synchronized MonitorFacade getInstance() {
        if (instance == null) {
            instance = new MonitorFacade();
        }
        return instance;
    }

    /**
     * Registers the entry at the monitoring.
     * 
     * @param entry
     *            the entry to monitor
     */
    public synchronized void putEntry(MonitorEntry entry) {
        if (this.processorRegistry.isEmpty()) {

            if (this.temporaryQueue.isEmpty()) {
                logger.warning("No monitor processor registered. Queueing up to "
                        + this.queueSize + " temporary entries.");
            }

            if (this.temporaryQueue.size() < this.queueSize) {
                this.temporaryQueue.add(entry);
            } else {
                logger.warning("Monitoring queue exceeded maximum size. Earlier monitoring entries are discarded.");
                this.temporaryQueue.clear();
            }
        }

        for (MonitorProcessor processor : this.processorRegistry) {
            try {
                processor.process(entry);
            } catch (MonitorException e) {
                logger.warning(e, "Error monitoring service '", String.valueOf(entry.getServiceName()),
                        "' and operation '", String.valueOf(entry.getOperationName()), "'.");
            }
        }
    }

    /**
     * Registers a monitor processor in the monitoring registry. A processor will be informed for
     * each monitor entry.
     * 
     * @param processor
     *            the processor to register
     */
    public synchronized void registerProcessor(MonitorProcessor processor) {
        this.processorRegistry.add(processor);

        if (!this.temporaryQueue.isEmpty()) {
            for (MonitorEntry entry : this.temporaryQueue) {
                try {
                    processor.process(entry);
                } catch (MonitorException e) {
                    logger.warning(e, "Error monitoring service '", String.valueOf(entry.getServiceName()),
                            "' and operation '", String.valueOf(entry.getOperationName()), "'.");
                }
            }

            this.temporaryQueue.clear();
        }
    }

    /**
     * Removes a monitor processor from the monitoring registry.
     * 
     * @param processor
     *            the processor to unregister
     */
    public synchronized void unregisterProcessor(MonitorProcessor processor) {
        this.processorRegistry.remove(processor);
    }
}
