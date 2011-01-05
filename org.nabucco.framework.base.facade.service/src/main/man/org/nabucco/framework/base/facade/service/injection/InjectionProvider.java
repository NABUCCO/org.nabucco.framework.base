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
 * @author Silas Schwarz PRODYNA AG
 */
public class InjectionProvider implements InjectionProviderMBean {

    private String id;

    private String fileName;

    private Properties properties;

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            InjectionProvider.class);

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
     */
    private InjectionProvider(String id) {
        if (id == null) {
            throw new InjectionException("No valid injection id defined [null].");
        }

        this.id = id;
        this.fileName = id.concat(PROPERTIES_SUFFIX);
        InputStream is = InjectionProvider.class.getClassLoader().getResourceAsStream(
                META_INF_PATH + fileName);

        this.properties = new Properties();

        try {
            if (is != null) {
                this.properties.load(is);
            } else {
                logger.warning("Cannot load injection properties for '" + fileName + "'.");
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
        MBeanSupport.register(injector, keys);
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
        keys.put(MBeanSupport.TYPE, "InjectionProvider");
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

        try {
            String implName = this.resolveImplementationName(id);

            if (implName == null || implName.isEmpty()) {
                logger.warning("Implementation not found for injection " + id + ".");
                return null;
            }

            ClassLoader classLoader = super.getClass().getClassLoader();

            @SuppressWarnings("unchecked")
            Class<T> injectable = (Class<T>) classLoader.loadClass(implName);

            return injectable.newInstance();

        } catch (Exception e) {
            logger.warning("Implementation not found for injection " + id + ".");
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
     * Loads the stream as XML and extracts the appropriate service-handler-implementation.
     * 
     * @param handlerName
     *            name of the service handler
     * 
     * @return name of the implementation
     */
    private String resolveImplementationName(String handlerName) {
        return this.properties.getProperty(handlerName);
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter for the fileName.
     * 
     * @return Returns the fileName.
     */
    public String getFileName() {
        return this.fileName;
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
