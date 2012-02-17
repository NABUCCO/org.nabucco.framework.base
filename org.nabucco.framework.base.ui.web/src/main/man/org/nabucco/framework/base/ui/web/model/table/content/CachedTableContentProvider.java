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
package org.nabucco.framework.base.ui.web.model.table.content;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.cache.BasicCache;
import org.nabucco.common.cache.CacheFactory;
import org.nabucco.common.cache.CacheManager;
import org.nabucco.common.cache.CacheType;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;

/**
 * CachedTableContentProvider
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class CachedTableContentProvider<D extends Datatype> extends TableContentProvider<D> {

    /** Cache */
    private BasicCache<List<D>> cache;

    /** Cache Manager */
    private CacheManager cacheManager;

    private static final String SEPARATOR = "->";

    private static final long TIMEOUT = 1800000L;

    private static final String CACHE_MANAGER = CachedTableContentProvider.class.getCanonicalName();

    /**
     * Creates a new {@link CachedTableContentProvider} instance.
     */
    public CachedTableContentProvider() {
        CacheFactory<List<D>> factory = new CacheFactory<List<D>>();
        this.cacheManager = new CacheManager(CACHE_MANAGER);
        this.cache = factory.newCache(CacheType.SIMPLE, this.cacheManager);
    }

    @Override
    public final List<D> getEntries(Datatype datatype, int index, int amount) throws ClientException {

        String key = createKey(index, amount);

        List<D> cached = this.cache.retrieve(key);
        if (cached != null) {
            return new ArrayList<D>(cached);
        }

        List<D> entries = this.loadEntries(datatype, index, amount);
        this.cache.store(key, new ArrayList<D>(entries), TIMEOUT);

        return entries;
    }

    /**
     * Create the cache key for the current index and amount.
     * 
     * @param index
     *            the current index
     * @param amount
     *            the displayed amount
     * 
     * @return the cache key
     */
    private static String createKey(int index, int amount) {
        StringBuilder key = new StringBuilder();
        key.append(index);
        key.append(SEPARATOR);
        key.append(amount);
        return key.toString();
    }

    /**
     * Load the entries when they are not cached anymore.
     * 
     * @param datatype
     *            the business object
     * @param index
     *            the index number
     * @param amount
     *            the amount of entries to return
     * 
     * @return the entries
     * 
     * @throws ClientException
     *             when the entries cannot be loaded
     */
    public abstract List<D> loadEntries(Datatype datatype, int index, int amount) throws ClientException;

}
