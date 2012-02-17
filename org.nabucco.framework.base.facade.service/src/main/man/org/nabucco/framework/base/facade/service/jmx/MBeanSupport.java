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
package org.nabucco.framework.base.facade.service.jmx;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
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

    /** The root domain of all NABUCCO MBeans. */
    public static final String DOMAIN = "org.nabucco";

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(MBeanSupport.class);

    /** The MBeanServer */
    private static List<MBeanServer> mbeanServerList = initMbeanServers();

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
    private static List<MBeanServer> initMbeanServers() {

        List<MBeanServer> servers = new ArrayList<MBeanServer>();

        try {
            List<MBeanServer> registeredServers = MBeanServerFactory.findMBeanServer(null);

            if (registeredServers == null || registeredServers.isEmpty()) {
                MBeanServer newServer = MBeanServerFactory.createMBeanServer();
                servers.add(newServer);
            } else {
                servers.addAll(registeredServers);
            }
        } catch (Exception e) {
            logger.error(e, "Error initializing MBean Servers.");
        }

        return servers;
    }

    /**
     * Convert the given keys to an object name.
     * 
     * @param keys
     *            the mbean key parameter
     * 
     * @return the mbean object name
     */
    public static ObjectName getName(Hashtable<String, String> keys) throws MalformedObjectNameException {
        return getName(null, keys);
    }

    /**
     * Convert the given keys to an object name.
     * 
     * @param subdomain
     *            the sub-domain
     * @param keys
     *            the mbean key parameter
     * 
     * @return the mbean object name
     */
    public static ObjectName getName(String subdomain, Hashtable<String, String> keys)
            throws MalformedObjectNameException {

        String domain = getDomain(subdomain);

        try {
            return new ObjectName(domain, keys);
        } catch (MalformedObjectNameException mone) {
            logger.error(mone, "The given object name '", domain, "' is not valid.");
            throw mone;
        }
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
        return register(null, mbean, keys);
    }

    /**
     * Registers the MBean in the registry under the given subdomain.
     * 
     * @param subdomain
     *            the subdomain, will be appended to the root domain
     * @param mbean
     *            the mbean instance
     * @param keys
     *            the mbean name keys
     */
    public static ObjectInstance register(String subdomain, MBean mbean, Hashtable<String, String> keys) {
        if (mbean == null) {
            logger.warning("Cannot register MBean [null].");
            return null;
        }

        try {
            String domain = getDomain(subdomain);
            ObjectName name = new ObjectName(domain, keys);
            ObjectInstance instance = register(mbean, name);

            return instance;
        } catch (Exception e) {
            logger.warning(e, "Cannot register MBean with id [null].");
        }

        return null;
    }

    /**
     * Getter for the domain depending on the given sub-domain.
     * 
     * @param subdomain
     *            the subdomain to append
     * 
     * @return the complete domain, starting with org.nabucco
     */
    private static String getDomain(String subdomain) {

        StringBuilder domain = new StringBuilder();
        domain.append(DOMAIN);

        if (subdomain != null && !subdomain.isEmpty()) {
            if (subdomain.charAt(0) == '.') {
                domain.append(subdomain);
            } else {
                domain.append('.');
                domain.append(subdomain);
            }
        }

        return domain.toString();
    }

    /**
     * Locate the MBean with the given object name and returns a proxy object.
     * 
     * @param <M>
     *            the MBean type
     * @param name
     *            the MBean object name
     * @param mbeanClass
     *            the MBean interface class
     * @param isNotificationListener
     *            whether the MBean is a notification listener or not
     * 
     * @return a proxy of the MBean
     */
    public static <M extends MBean> M findMBean(ObjectName name, Class<M> mbeanClass, boolean isNotificationListener) {
        MBeanServer mbeanServer = MBeanSupport.findMBeanServer(name);

        M mbean = JMX.newMBeanProxy(mbeanServer, name, mbeanClass, isNotificationListener);

        if (mbean == null) {
            logger.warning("Cannot locate MBean with id [", name.getCanonicalName(), "].");
        }

        return mbean;
    }

    /**
     * Find the MBean Server in which the given MBean is deployed.
     * 
     * @return the first MBeanServer holding the given object name, or null if none is found
     */
    public static MBeanServer findMBeanServer(ObjectName name) {
        for (MBeanServer mbeanServer : mbeanServerList) {
            try {
                if (mbeanServer.isRegistered(name)) {
                    return mbeanServer;
                }
            } catch (Exception e) {
                logger.warning(e, "Cannot find MBeanServer for MBean with id [", name.getCanonicalName(), "].");
            }
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

        unregister(name);
    }

    /**
     * Register the MBean in all registered MBean servers.
     * 
     * @param mbean
     *            the mbean to register
     * @param name
     *            the mbean's object name
     * 
     * @return the registered object instance, or null if the registration fails
     */
    private static ObjectInstance register(MBean mbean, ObjectName name) {

        ObjectInstance instance = null;

        try {
            for (MBeanServer mbeanServer : mbeanServerList) {
                if (mbeanServer.isRegistered(name)) {
                    logger.debug("MBean with id [", name.getCanonicalName(), "] is already registered in MBeanServer.");
                    instance = mbeanServer.getObjectInstance(name);
                } else {
                    instance = mbeanServer.registerMBean(mbean, name);
                }
            }

            if (instance == null) {
                logger.warning("Cannot register MBean with id [", name.getCanonicalName(), "].");
            } else {
                logger.debug("MBean with id [", name.getCanonicalName(), "] successfully registered.");
            }

        } catch (Exception e) {
            logger.warning(e, "Cannot register MBean with id [", mbean.getClass().getCanonicalName(), "].");
        }

        return instance;
    }

    /**
     * Unregister the MBean from the MBean servers.
     * 
     * @param name
     *            the object name to unregister
     */
    private static void unregister(ObjectName name) {
        for (MBeanServer mbeanServer : mbeanServerList) {
            try {
                mbeanServer.unregisterMBean(name);
            } catch (Exception e) {
                logger.warning(e, "Cannot unregister MBean with id [", name.getCanonicalName(), "].");
            }
        }
    }

}
