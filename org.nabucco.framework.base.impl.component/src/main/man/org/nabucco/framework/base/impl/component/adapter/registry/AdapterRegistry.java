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
package org.nabucco.framework.base.impl.component.adapter.registry;

import java.util.Set;

import org.nabucco.framework.base.facade.service.jmx.MBean;

/**
 * AdapterRegistry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface AdapterRegistry extends MBean {

    /**
     * Registers an adapter in the adapter registry.
     * 
     * @param adapter
     *            the adapter to register
     * @param the
     *            adapter jndi name
     */
    void register(String name, String jndiName);

    /**
     * Unregisters an adapter from the adapter registry.
     * 
     * @param adapter
     *            the adapter to remove from registry
     */
    void unregister(String name);

    /**
     * Getter for the names of all registered adapters.
     * 
     * @return all registered adapters
     */
    Set<String> getNames();

    /**
     * Getter for the adapter with the given name.
     * 
     * @param key
     *            the adapter name
     * 
     * @return the adapter entries for the given name
     */
    Set<AdapterRegistryEntry> get(String name);

    /**
     * Getter for all registered adapter entries.
     * 
     * @return all registered adapter entries
     */
    Set<AdapterRegistryEntry> getAll();

    /**
     * List all adapters as string.
     * 
     * @return the adapters as string
     */
    String list();

}
