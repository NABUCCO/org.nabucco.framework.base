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
package org.nabucco.framework.base.impl.service.aspect.caching;

import org.nabucco.common.cache.CacheFactory;
import org.nabucco.common.cache.CacheManager;
import org.nabucco.common.cache.CacheType;
import org.nabucco.common.cache.TenantCache;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * CachingSupport
 * <p/>
 * Support class for caching aspects.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class CachingSupport implements CachingAspect {

    private static final String CACHE_NAME = "CachingAspectCache";

    private static final String PROPERTY_TIMEOUT = "TIMEOUT";

    /** The Tenant Enabled Cache */
    private static TenantCache<ServiceMessage> cache;

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(CachingSupport.class);

    /** The cache timeout */
    private long cacheTimeout = 1800000;

    /** The service context */
    private ServiceMessageContext context;

    @Override
    public void configure(PropertyExtension properties) {
        try {
            if (properties != null) {
                IntegerProperty timeout = (IntegerProperty) properties.getPropertyMap().get(PROPERTY_TIMEOUT);
                this.cacheTimeout = timeout.getValue().getValue().longValue();
            }
        } catch (Exception e) {
            logger.warning("Property TIMEOUT not set for caching aspect, using default timeout ["
                    + this.cacheTimeout + "].");
        }
    }

    /**
     * Retrieve the caching aspect cache.
     * 
     * @return the caching aspect cache
     */
    public static synchronized final TenantCache<ServiceMessage> getCache() {
        if (cache == null) {
            CacheFactory<ServiceMessage> factory = new CacheFactory<ServiceMessage>();
            CacheManager manager = new CacheManager(CACHE_NAME);
            cache = factory.newTenantCache(CacheType.SIMPLE, manager);
        }

        return cache;
    }

    /**
     * Stores a service message in the cache for a given key.
     * 
     * @param key
     *            the cache key
     * @param message
     *            the message to cache
     */
    protected final void storeInCache(String key, ServiceMessage message) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot store key [null] in cache.");
        }
        if (message == null) {
            throw new IllegalArgumentException("Cannot store message [null] in cache.");
        }

        getCache().store(this.getTenant(), key, message, cacheTimeout);
    }

    /**
     * Retrieves a cached service message for a given key from the cache.
     * 
     * @param key
     *            the cache key
     * 
     * @return the cached message, or null if no message is registered in the cache
     */
    protected final ServiceMessage retrieveFromCache(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot retrieve key [null] from cache.");
        }

        return getCache().retrieve(this.getTenant(), key);
    }

    /**
     * Retrieve the tenant from the current service context.
     * 
     * @return the current tenant
     */
    private String getTenant() {
        if (this.context == null) {
            throw new IllegalStateException("Service Request Context [null] is not valid.");
        }
        if (this.context.getSubject() == null || this.context.getSubject().getUser() == null) {
            throw new IllegalStateException("User 'null' is not authorized to access the cache.");
        }

        UserId user = this.context.getSubject().getUserId();
        Tenant tenant = this.context.getSubject().getUser().getTenant();

        if (tenant == null || tenant.getValue() == null) {
            throw new IllegalStateException("User '" + user + "' is not authorized to access the cache.");
        }

        return tenant.getValue();
    }

    /**
     * Removes a cached service message with a given key from the cache.
     * 
     * @param tenant
     *            the cache tenant
     * @param key
     *            the cache key
     * 
     * @return the service message removed from cache
     */
    protected final ServiceMessage removeFromCache(Tenant tenant, String key) {
        if (tenant == null || tenant.getValue() == null) {
            throw new IllegalArgumentException("Cannot remove from cache with tenant [null].");
        }
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove key [null] from cache.");
        }

        return getCache().invalidate(tenant.getValue(), key);
    }

    /**
     * Clear the complete cache.
     */
    protected final void clearCache() {
        getCache().clear();
    }

    /**
     * Clear the cache for the given tenant.
     * 
     * @param tenant
     *            the cache tenant
     */
    protected final void clearCache(Tenant tenant) {
        if (tenant == null || tenant.getValue() == null) {
            throw new IllegalArgumentException("Cannot clear cache with tenant [null].");
        }

        getCache().clear(tenant.getValue());
    }

    @Override
    public final void setContext(ServiceMessageContext context) {
        this.context = context;
    }

}
