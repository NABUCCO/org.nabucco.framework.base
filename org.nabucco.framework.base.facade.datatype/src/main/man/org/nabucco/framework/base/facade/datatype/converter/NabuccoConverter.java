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
package org.nabucco.framework.base.facade.datatype.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * NabuccoConverter
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoConverter {

    /** The static map of instances per thread id. */
    private static Map<Long, NabuccoConverter> instanceMap;

    /** The map of converters per type. */
    private final Map<Class<? extends Converter<?>>, Converter<?>> converterMap;

    /**
     * Private constructor.
     */
    private NabuccoConverter() {
        this.converterMap = new HashMap<Class<? extends Converter<?>>, Converter<?>>();
    }

    /**
     * Singleton access.
     * 
     * @return the NabuccoConverter instance.
     */
    public static synchronized NabuccoConverter getInstance() {
        if (instanceMap == null) {
            instanceMap = new HashMap<Long, NabuccoConverter>();
        }

        long threadId = Thread.currentThread().getId();
        NabuccoConverter converter = instanceMap.get(threadId);

        if (converter == null) {
            converter = new NabuccoConverter();
            instanceMap.put(threadId, converter);
        }

        return converter;
    }

    /**
     * Getter for the date converter instance.
     * 
     * @return the date converter instance
     */
    public synchronized DateConverter getDateConverter() {
        Converter<?> dateConverter = this.converterMap.get(DateConverter.class);

        if (dateConverter == null) {
            dateConverter = new DateConverter();
            this.converterMap.put(DateConverter.class, dateConverter);
        }

        return (DateConverter) dateConverter;
    }

}
