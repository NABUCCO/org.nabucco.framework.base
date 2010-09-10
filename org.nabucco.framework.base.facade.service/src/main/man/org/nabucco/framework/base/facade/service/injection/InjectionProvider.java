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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * InjectionProvider
 * 
 * @author Silas Schwarz PRODYNA AG
 */
public class InjectionProvider {

    // TODO: Change directory
    private static final String META_INF_PATH = "META-INF/";

    private static final String PROPERTIES_SUFFIX = ".properties";

    /** Injector instances. */
    private static final Map<String, SoftReference<InjectionProvider>> INJECTOR_MAP = Collections
            .synchronizedMap(new HashMap<String, SoftReference<InjectionProvider>>());

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            InjectionProvider.class);

    private Properties properties;

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

        String fileName = id.concat(PROPERTIES_SUFFIX);
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
        return injector;
    }

    /**
     * Injects an appropriate service handler implementation by a given
     * {@link NabuccoServiceHandler} interface class. The service handler binding must be defined in
     * META_INF/*.properties files.
     * 
     * @param <T>
     *            the service handler type
     * @param superClass
     *            the service handler interface
     * 
     * @return the service handler implementation instance
     */
    public <T extends Injectable> T inject(String id) {

        if (id == null) {
            throw new InjectionException("No valid injection ID defined [null].");
        }

        try {
            String implName = this.resolveImplementationName(id);

            if (implName == null || implName.isEmpty()) {
                logger.warning("Implementation not found for " + id + ".");
                return null;
            }

            @SuppressWarnings("unchecked")
            Class<T> handler = (Class<T>) Class.forName(implName);

            return handler.newInstance();

        } catch (Exception e) {
            logger.warning("Implementation not found for " + id + ".");
            return null;
        }
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
}
