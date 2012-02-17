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
package org.nabucco.framework.base.facade.datatype.logger;

import org.apache.log4j.PropertyConfigurator;

/**
 * NabuccoLoggingFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoLoggingFactory {

    /** The singleton instance. */
    private static NabuccoLoggingFactory instance;

    /**
     * Creates a new {@link NabuccoLoggingFactory} instance.
     */
    private NabuccoLoggingFactory() {
        PropertyConfigurator.configureAndWatch("conf/log/log4j.properties");
    }

    /**
     * Getter for the factory instance.
     * 
     * @return the singleton instance
     */
    public static synchronized NabuccoLoggingFactory getInstance() {
        if (instance == null) {
            instance = new NabuccoLoggingFactory();
        }
        return instance;
    }

    /**
     * Getter for the logger of the specified class.
     * 
     * @param clazz
     *            the logging class
     * 
     * @return the nabucco logger
     */
    public NabuccoLogger getLogger(Class<?> clazz) {
        return new Log4JNabuccoLogger(clazz);
    }
}
