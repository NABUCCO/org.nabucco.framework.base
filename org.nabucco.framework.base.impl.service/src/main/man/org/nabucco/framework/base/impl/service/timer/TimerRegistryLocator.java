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
package org.nabucco.framework.base.impl.service.timer;

import java.util.Hashtable;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.nabucco.framework.base.facade.service.jmx.MBeanSupport;

/**
 * TimerRegistryLocator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TimerRegistryLocator {

    private static final String DOMAIN = "org.nabucco";

    private static final String ATTRIBUTE_NAME = "name";

    private static final String ATTRIBUTE_TYPE = "type";

    private static final String ATTRIBUTE_SERVICE = "service";

    private static final String TIMER_NAME = "NabuccoTimer";

    private static final String TIMER_TYPE = "TimerRegistry";

    private static final String TIMER_SERVICE = "NotificationListener";

    /**
     * Singleton instance.
     */
    private static TimerRegistryLocator instance = new TimerRegistryLocator();

    /**
     * The timer registry proxy.
     */
    private TimerRegistry registry;

    /**
     * Private constructor.
     */
    private TimerRegistryLocator() {
    }

    /**
     * Singleton access.
     * 
     * @return the TimerRegistryLocator instance.
     */
    public static TimerRegistryLocator getInstance() {
        return instance;
    }

    /**
     * Locates the timer registry from the MBean registry.
     * 
     * @return the timer registry
     * 
     * @throws TimerLookupException
     *             when the timer mbean cannot be located
     */
    public synchronized TimerRegistry getRegistry() throws TimerLookupException {

        if (this.registry == null) {

            Hashtable<String, String> keys = new Hashtable<String, String>(2);
            keys.put(ATTRIBUTE_NAME, TIMER_NAME);
            keys.put(ATTRIBUTE_TYPE, TIMER_TYPE);
            keys.put(ATTRIBUTE_SERVICE, TIMER_SERVICE);

            try {

                ObjectName name = new ObjectName(DOMAIN, keys);
                this.registry = MBeanSupport.findMBean(name, TimerRegistry.class, true);

                if (this.registry == null) {
                    throw new TimerLookupException("Cannot locate TimerRegistry MBean!");
                }

            } catch (MalformedObjectNameException moe) {
                throw new TimerLookupException("TimerRegistry Configuration is not valid!", moe);
            } catch (Exception e) {
                throw new TimerLookupException("Cannot locate TimerRegistry MBean!", e);
            }
        }

        return this.registry;
    }

}
