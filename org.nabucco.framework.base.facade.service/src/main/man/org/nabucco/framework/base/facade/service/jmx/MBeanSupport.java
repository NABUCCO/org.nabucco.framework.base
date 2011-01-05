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
package org.nabucco.framework.base.facade.service.jmx;

import java.util.Hashtable;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * MBeanSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class MBeanSupport implements MBean {

    private static final String DOMAIN = "org.nabucco";

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            MBeanSupport.class);

    /** The MBeanServer */
    private static MBeanServer mbeanServer = initMbeanServer();

    /**
     * Private constructor must not be invoked.
     */
    private MBeanSupport() {
    }

    /**
     * Initialize the MBeanServer. When multiple MBeanServers are defined, the first one is choosen.
     * If none is defined a new one is created.
     * 
     * @return the initialized MBeanServer
     */
    private static MBeanServer initMbeanServer() {
        MBeanServer mbeanServer = null;

        List<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);

        if (servers.size() > 0) {
            mbeanServer = servers.get(0);
        }

        if (mbeanServer == null) {
            mbeanServer = MBeanServerFactory.createMBeanServer();
        }

        return mbeanServer;
    }

    /**
     * Registers the MBean in the registry.
     * 
     * @param mbean
     *            the mbean instance
     * @param keys
     *            the mbean name keys
     */
    public static ObjectInstance register(MBean mbean, Hashtable<String, String> keys) {
        if (mbean == null) {
            logger.warning("Cannot register MBean [null].");
            return null;
        }

        try {
            ObjectName name = new ObjectName(DOMAIN, keys);

            if (mbeanServer.isRegistered(name)) {
                logger.debug("MBean with id [", name.getCanonicalName(), "] is already registered.");
                return mbeanServer.getObjectInstance(name);
            }

            ObjectInstance instance = mbeanServer.registerMBean(mbean, name);

            if (instance == null) {
                logger.warning("Cannot register MBean with id [", name.getCanonicalName(), "].");
            } else {
                logger.debug("MBean with id [", name.getCanonicalName(),
                        "] successfully registered.");
            }

            return instance;

        } catch (Exception e) {
            logger.warning(e, "Cannot register MBean with id [", mbean.getClass()
                    .getCanonicalName(), "].");
        }

        return null;
    }

    /**
     * Unegisters the MBean in the registry.
     * 
     * @param name
     *            the mbean id
     * @param keys
     *            the mbean name keys
     */
    public static void unregister(Hashtable<String, String> keys) {

        ObjectName name = null;

        try {
            name = new ObjectName(DOMAIN, keys);
        } catch (Exception e) {
            logger.warning(e, "Cannot unregister MBean with id [null].");
        }

        if (name == null) {
            logger.warning("Cannot unregister MBean with id [null].");
            return;
        }

        try {
            mbeanServer.unregisterMBean(name);
        } catch (Exception e) {
            logger.warning(e, "Cannot unregister MBean with id [", name.getCanonicalName(), "].");
        }
    }

}
