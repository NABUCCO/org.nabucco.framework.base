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
package org.nabucco.framework.base.facade.service.injection;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.jmx.MBeanSupport;

/**
 * InjectionProvider
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class InjectionProvider implements InjectionProviderMBean {

    private static final String TYPE = "InjectionProvider";

    private static final String DEFAULT = "default";

    private static final String DEFAULT_SUFFIX = "Impl";

    private static final String SUBDOMAIN = "inject";

    private String id;

    private String fileName;

    private Properties properties;

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(InjectionProvider.class);

    /** PropertySupport Location. */
    private static final String META_INF_PATH = "META-INF/";

    /** File Suffix. */
    private static final String PROPERTIES_SUFFIX = ".properties";

    /** Injector instances. */
    private static final Map<String, SoftReference<InjectionProvider>> INJECTOR_MAP = Collections
            .synchronizedMap(new HashMap<String, SoftReference<InjectionProvider>>());

    /**
     * Private constructor.
     * 
     * @param receiver
     *            the injection receiver
     * 
     * @throws InjectionException
     *             when the injection ID is not valid
     */
    private InjectionProvider(String id) throws InjectionException {
        if (id == null) {
            throw new InjectionException("No valid injection id defined [null].");
        }

        this.id = id;
        this.fileName = id.concat(PROPERTIES_SUFFIX);
        InputStream is = InjectionProvider.class.getClassLoader().getResourceAsStream(META_INF_PATH + fileName);

        this.properties = new Properties();

        try {
            if (is != null) {
                this.properties.load(is);
            } else {
                logger.warning("Cannot load injection properties for '", this.fileName, "'.");
            }
        } catch (IOException e) {
            throw new InjectionException("Error reading injection properties for " + id + ".", e);
        }
    }

    /**
     * Singleton access.
     * 
     * @param id
     *            the injector id
     * 
     * @return the NabuccoServiceHandlerInjector instance.
     * 
     * @throws InjectionException
     *             when the injection ID is not valid or an unexpected error occurs
     */
    public static synchronized InjectionProvider getInstance(String id) {

        if (INJECTOR_MAP.containsKey(id) && INJECTOR_MAP.get(id).get() != null) {
            return INJECTOR_MAP.get(id).get();
        }

        InjectionProvider injector = new InjectionProvider(id);

        INJECTOR_MAP.put(id, new SoftReference<InjectionProvider>(injector));

        register(id, injector);

        return injector;
    }

    /**
     * Registers the Injection Provider in the MBean registry.
     * 
     * @param id
     *            the injection id
     * @param injector
     *            the injection provider
     */
    private static void register(String id, InjectionProvider injector) {
        Hashtable<String, String> keys = createKeys(id);
        MBeanSupport.register(SUBDOMAIN, injector, keys);
    }

    /**
     * Create the MBean keys.
     * 
     * @param id
     *            the injector ID
     * 
     * @return the injector keys
     */
    private static Hashtable<String, String> createKeys(String id) {
        Hashtable<String, String> keys = new Hashtable<String, String>(2);
        keys.put(MBeanSupport.TYPE, TYPE);
        keys.put(MBeanSupport.NAME, id);
        return keys;
    }

    /**
     * Injects an appropriate injectable implementation by a given {@link Injectable} id. The
     * injection binding must be defined in META_INF/*.properties files.
     * 
     * @param <T>
     *            the injection type
     * @param id
     *            the injection id (key in properties file)
     * 
     * @return the injected instance
     */
    public <T extends Injectable> T inject(String id) {

        if (id == null) {
            throw new InjectionException("No valid injection ID defined [null].");
        }

        String implName = this.resolveImplementationName(id);

        try {

            if (implName == null || implName.isEmpty()) {
                logger.warning("Implementation not found for injection " + id + ".");
                return null;
            }

            ClassLoader classLoader = super.getClass().getClassLoader();

            @SuppressWarnings("unchecked")
            Class<T> injectable = (Class<T>) classLoader.loadClass(implName);

            return injectable.newInstance();

        } catch (ClassNotFoundException cnfe) {
            logger.warning("Implementation class not found for injection id ", id, ".");
            return null;
        } catch (IllegalAccessException iae) {
            logger.warning("Implementation class cannot be instantiated ", implName, ".");
            return null;
        } catch (InstantiationException ie) {
            logger.warning("Implementation class cannot be instantiated ", implName, ".");
            return null;
        } catch (SecurityException ie) {
            logger.warning("Implementation class cannot be instantiated ", implName, ".");
            return null;
        } catch (Exception e) {
            logger.error(e, "Unexpected error during injection of " + id + ".");
            return null;
        }
    }

    /**
     * Injects an appropriate injectable implementation by a given {@link Injectable} id. The
     * injection binding must be defined in META_INF/*.properties files.
     * 
     * @param <T>
     *            the injection type
     * 
     * @return a list of injected instance
     */
    public <T extends Injectable> List<T> injectAll() {

        List<T> injections = new ArrayList<T>();

        for (Object id : this.properties.keySet()) {
            if (id instanceof String) {
                T instance = this.<T> inject((String) id);

                if (instance != null) {
                    injections.add(instance);
                }
            }
        }

        return injections;
    }

    /**
     * Loads the stream as XML and extracts the appropriate injection-handler-implementation.
     * 
     * @param interfaceName
     *            name of the injection handler
     * 
     * @return name of the implementation
     */
    private String resolveImplementationName(String interfaceName) {
        String implName = this.properties.getProperty(interfaceName);

        if (implName == null) {
            return null;
        }

        implName = implName.trim();

        if (implName.equalsIgnoreCase(DEFAULT)) {
            implName = interfaceName + DEFAULT_SUFFIX;
        }

        return implName;
    }

    /**
     * Getter for the properties.
     * 
     * @return Returns the properties.
     */
    public Properties getProperties() {
        return new Properties(this.properties);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public String getInjection(String injectionId) {
        if (injectionId == null) {
            return null;
        }

        return this.properties.getProperty(injectionId);
    }

    @Override
    public void addInjection(String injectionId, String implementation) {
        if (injectionId == null || implementation == null) {
            return;
        }

        this.properties.setProperty(injectionId, implementation);
    }

    @Override
    public String listInjections() {
        StringBuilder result = new StringBuilder();
        for (String key : this.properties.stringPropertyNames()) {
            result.append(key);
            result.append(" = ");
            result.append(this.properties.getProperty(key));
            result.append('\n');
        }
        return result.toString();
    }

    @Override
    public void reset() {
        this.properties = null;
        INJECTOR_MAP.remove(this.getId());

        MBeanSupport.unregister(createKeys(this.getId()));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.getId());
        result.append(" (");
        result.append(this.getFileName());
        result.append(")");
        return result.toString();
    }
}
