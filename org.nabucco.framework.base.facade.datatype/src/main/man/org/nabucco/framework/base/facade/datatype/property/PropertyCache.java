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
package org.nabucco.framework.base.facade.datatype.property;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * PropertyCache
 * <p/>
 * Cache for {@link NabuccoPropertyDescriptor} instances. This cache is called implicity by a
 * Datatypes getPropertyDescriptor() method is invoked. All properties of a datatype are stored in
 * this cache.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PropertyCache {

    private static final String METHOD_CREATE_CONTAINER = "createPropertyContainer";

    private Map<Class<? extends NType>, SoftReference<NabuccoPropertyContainer>> cache = new HashMap<Class<? extends NType>, SoftReference<NabuccoPropertyContainer>>();

    /** The NABUCCO Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(PropertyCache.class);

    /**
     * Singleton instance.
     */
    private static PropertyCache instance = new PropertyCache();

    /**
     * Private constructor.
     */
    private PropertyCache() {
    }

    /**
     * Singleton access.
     * 
     * @return the PropertyCache instance.
     */
    public static PropertyCache getInstance() {
        return instance;
    }

    /**
     * Retrieve a property container from cache. When it is not already cached it is resolved
     * statically from the given NType class and registered in the cache.
     * 
     * @param key
     *            the class of the property holder
     * 
     * @return the cached property container
     */
    public NabuccoPropertyContainer retrieve(Class<? extends NType> key) {
        SoftReference<NabuccoPropertyContainer> reference = this.cache.get(key);
        if (reference != null && reference.get() != null) {
            return reference.get();
        }

        try {
            Method method = key.getDeclaredMethod(METHOD_CREATE_CONTAINER);
            method.setAccessible(true);
            Object container = method.invoke(key);

            if (container instanceof NabuccoPropertyContainer) {
                this.register(key, (NabuccoPropertyContainer) container);
            }
        } catch (Exception e) {
            logger.error(e, "Unexpected error in static property creation.");
            throw new IllegalStateException("Unexpected error in static property creation.", e);
        }

        reference = this.cache.get(key);
        if (reference != null && reference.get() != null) {
            return reference.get();
        }

        return null;
    }

    /**
     * Register a property container for the given parent class in the cache.
     * 
     * @param key
     *            the class holding the properties
     * @param value
     *            the property container to cache
     * 
     * @return the prior cache entry for the given key class or <b>null</b> if none exists
     */
    private NabuccoPropertyContainer register(Class<? extends NType> key, NabuccoPropertyContainer value) {

        SoftReference<NabuccoPropertyContainer> old = this.cache.put(key, new SoftReference<NabuccoPropertyContainer>(
                value));

        if (old == null) {
            return null;
        }

        return old.get();
    }
}
