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
package org.nabucco.framework.base.facade.datatype.cache;

import java.util.Set;

import org.nabucco.common.cache.CacheManager;
import org.nabucco.common.cache.CacheManagerRegistry;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * NabuccoCacheManager
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoCacheManager {

    private static final String MESSAGE_EMPTY_MANAGERS = "No cache manager registered!";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoCacheManager.class);

    /**
     * Evict the cache manager with the given name.
     * 
     * @param name
     *            the name of the cache manager to evict
     */
    public void evict(String name) {
        CacheManager manager = CacheManagerRegistry.getInstance().get(name);

        if (manager == null) {
            return;
        }

        manager.evict();
        logger.info("Evicted cache manager '", name, "'.");
    }

    /**
     * Evict all registered cache managers.
     */
    public void evictAll() {
        for (CacheManager manager : CacheManagerRegistry.getInstance().getAll()) {
            manager.evict();
            logger.info("Evicted cache manager '", manager.getName(), "'.");
        }
    }

    /**
     * Getter for the amount of registered cache managers.
     * 
     * @return the amount of registerd cache managers
     */
    public int getSize() {
        return CacheManagerRegistry.getInstance().getAll().size();
    }

    /**
     * List all registered cache managers.
     * 
     * @return all registered cache managers
     */
    public String listAll() {
        Set<CacheManager> managers = CacheManagerRegistry.getInstance().getAll();

        if (managers.isEmpty()) {
            return MESSAGE_EMPTY_MANAGERS;
        }

        StringBuilder result = new StringBuilder();

        for (CacheManager manager : managers) {
            result.append(" - ");
            result.append(manager.getName());
            result.append(": ");
            result.append(manager.getCaches().size());
            result.append('\n');
        }

        return result.toString();
    }
}
