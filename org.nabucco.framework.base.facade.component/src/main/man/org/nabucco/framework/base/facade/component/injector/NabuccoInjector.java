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
package org.nabucco.framework.base.facade.component.injector;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * NabuccoInjector
 * 
 * @author Silas Schwarz PRODYNA AG
 */
public class NabuccoInjector {

    // TODO: Change directory
    private static final String META_INF_PATH = "META-INF/";

    private static final String DEFAULT_PROPERTIES_FILENAME = "injection.properties";

    private static final Properties DEFAULT_PROPERTIES = new Properties();

    static {

        // TODO: Why static? Which file is loaded? Multiple ears? Multiple components?

        // try obtaining defaults
        try {
            InputStream is = NabuccoInjector.class.getClassLoader().getResourceAsStream(
                    META_INF_PATH + DEFAULT_PROPERTIES_FILENAME);
            // default.properties found
            if (is != null) {
                DEFAULT_PROPERTIES.load(NabuccoInjector.class.getClassLoader().getResourceAsStream(
                        META_INF_PATH + DEFAULT_PROPERTIES_FILENAME));
            }
        } catch (IOException e) {

            // FIXME: Throw exception / remove defaults

            // swallow exception
            throw new IllegalStateException("Error reading default properties.", e);
        }
    }

    private Properties properties = new Properties(DEFAULT_PROPERTIES);

    /**
     * Injector instances.
     */
    private static Map<Class<?>, NabuccoInjector> injectors = Collections
            .synchronizedMap(new HashMap<Class<?>, NabuccoInjector>());

    /**
     * Private constructor.
     * 
     * @param receiver
     *            the injection receiver
     */
    private NabuccoInjector(Class<? extends NabuccoInjectionReciever> receiver) {

        if (receiver == null) {
            throw new IllegalArgumentException("command class not valid [null].");
        }

        String fileName = receiver.getSimpleName().replace("Impl", "").concat(".properties");
        InputStream is = receiver.getClassLoader().getResourceAsStream(META_INF_PATH + fileName);

        try {
            // if there is no corresponding property file don't load, giving default a chance
            if (is != null) {
                properties.load(is);
            }
        } catch (IOException e) {
            // TODO: Logging
            throw new IllegalStateException("Error reading command handler properties for " + receiver.getName() + ".",
                    e);
        }
    }

    /**
     * Singleton access.
     * 
     * @return the NabuccoServiceHandlerInjector injectors.
     */
    public static synchronized NabuccoInjector getInstance(Class<? extends NabuccoInjectionReciever> receiver) {

        if (injectors.containsKey(receiver)) {
            return injectors.get(receiver);
        }

        NabuccoInjector injector = new NabuccoInjector(receiver);
        injectors.put(receiver, injector);
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
    public <T extends NabuccoInjectable> T inject(Class<T> superClass) {

        if (superClass == null) {
            throw new IllegalArgumentException("CommandHandler not valid [null].");
        }

        String handlerName = superClass.getCanonicalName();

        try {
            String implName = resolveImplementationName(handlerName);

            if (implName == null || implName.isEmpty()) {
                throw new IllegalArgumentException("CommandHandler implementation not found for " + handlerName + ".");
            }

            @SuppressWarnings("unchecked")
            Class<T> handler = (Class<T>) Class.forName(implName);

            return handler.newInstance();

        } catch (Exception e) {
            throw new IllegalStateException("CommandHandler implementation not found for " + handlerName + ".", e);
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
        String value = properties.getProperty(handlerName);
        return (value == null) ? null : String.valueOf(value);
    }
}
