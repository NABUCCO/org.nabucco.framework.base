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

import java.util.Hashtable;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.jmx.MBean;
import org.nabucco.framework.base.facade.service.jmx.MBeanSupport;

/**
 * AdapterRegistryLocator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AdapterRegistryLocator {

    private static final String REGISTRY_NAME = "AdapterRegistry";

    private static final String REGISTRY_TYPE = "Monitor";

    /** The MBean instance. */
    private static AdapterRegistry registry;

    /** The Logger. */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(AdapterRegistryLocator.class);

    /**
     * Private constructor must not be invoked.
     */
    private AdapterRegistryLocator() {
    }

    /**
     * Locates the timer registry from the MBean registry.
     * 
     * @return the timer registry
     * 
     * @throws TimerLookupException
     *             when the timer mbean cannot be located
     */
    public static synchronized AdapterRegistry getRegistry() {

        if (registry == null) {

            Hashtable<String, String> keys = new Hashtable<String, String>(2);
            keys.put(MBean.NAME, REGISTRY_NAME);
            keys.put(MBean.TYPE, REGISTRY_TYPE);

            try {

                ObjectName name = MBeanSupport.getName(keys);
                registry = MBeanSupport.findMBean(name, AdapterRegistry.class, true);

                if (registry == null) {
                    createNewMBeanInstance(keys);
                }

            } catch (MalformedObjectNameException moe) {
                logger.error(moe, "AdapterRegistry object name configuration is not valid!");
                throw new IllegalStateException("AdapterRegistry object name configuration is not valid!", moe);

            } catch (Exception e) {
                createNewMBeanInstance(keys);
            }
        }

        return registry;
    }

    /**
     * Create a new MBean instance.
     * 
     * @param keys
     *            the mbean object name keys
     */
    private static void createNewMBeanInstance(Hashtable<String, String> keys) {
        logger.warning("Cannot locate AdapterRegistry MBean, creating new instance!");
        registry = new AdapterRegistryMBean();
        MBeanSupport.register(registry, keys);
    }
}
