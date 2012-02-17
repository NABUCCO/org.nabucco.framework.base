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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.StandardMBean;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.AdapterManagementExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.impl.service.timer.TimerLookupException;
import org.nabucco.framework.base.impl.service.timer.TimerService;

/**
 * AdapterRegistry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AdapterRegistryMBean extends StandardMBean implements AdapterRegistry {

    private Map<String, Set<AdapterRegistryEntry>> adapterMap = new HashMap<String, Set<AdapterRegistryEntry>>();

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(AdapterRegistryMBean.class);

    /**
     * Creates a new {@link AdapterRegistryMBean} instance.
     */
    AdapterRegistryMBean() {
        super(AdapterRegistry.class, false);
    }

    @Override
    public void register(String adapterName, String jndiName) {
        if (adapterName == null) {
            throw new IllegalArgumentException("Cannot register adapter [null] in registry.");
        }

        String adapterInterface = this.getAdapterInterface(adapterName);

        if (adapterInterface == null) {
            logger.warning("Adapter '", adapterName, "' is not configured correctly and cannot be registerd.");
            return;
        }

        List<String> jndiNames = this.getJndiNames(adapterName);

        Set<AdapterRegistryEntry> entries = this.adapterMap.get(adapterName);

        if (entries == null) {
            entries = new HashSet<AdapterRegistryEntry>();
            this.adapterMap.put(adapterName, entries);
        }

        for (String resourceJndi : jndiNames) {
            AdapterRegistryEntry entry = new AdapterRegistryEntry(adapterInterface, jndiName, resourceJndi);
            entries.add(entry);
        }

        try {
            logger.info("Starting new adapter registry timer!");
            TimerService.getInstance().schedule(new AdapterRegistryTimer());
        } catch (TimerLookupException te) {
            logger.error(te, "Cannot start adapter registry timer, timer service is not deployed.");
        } catch (Exception e) {
            logger.error(e, "Error starting adapter registry timer.");
        }
    }

    /**
     * Resolve the JNDI names of a given adapter name.
     * 
     * @param extensionName
     *            the name of the org.nabucco.management.adapter extension
     * 
     * @return the configured adapter names
     */
    private String getAdapterInterface(String extensionName) {

        try {
            AdapterManagementExtension extension = (AdapterManagementExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_MANAGEMENT_ADAPTER, extensionName);

            if (extension == null || extension.getAdapterInterface() == null) {
                return null;
            }

            ClassProperty adapterInterface = extension.getAdapterInterface();
            if (adapterInterface.getValue() != null && adapterInterface.getValue().getValue() != null) {
                return adapterInterface.getValue().getValue();
            }

        } catch (ExtensionException ee) {
            logger.error(ee, "Cannot resolve configured adapter JNDI names.");
        }

        return null;
    }

    /**
     * Resolve the JNDI names of a given adapter name.
     * 
     * @param name
     *            the name of the adapter
     * 
     * @return the configured jndi names
     */
    private List<String> getJndiNames(String name) {

        try {
            AdapterManagementExtension extension = (AdapterManagementExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_MANAGEMENT_ADAPTER, name);

            if (extension == null) {
                logger.error("Cannot resolve configured adapter JNDI names.");
                return Collections.emptyList();
            }

            List<String> jndiNames = new ArrayList<String>();
            for (StringProperty property : extension.getJndiNames()) {
                if (property != null && property.getValue() != null && property.getValue().getValue() != null) {
                    jndiNames.add(property.getValue().getValue());
                }
            }

            return jndiNames;

        } catch (ExtensionException ee) {
            logger.error(ee, "Cannot resolve configured adapter JNDI names.");
        }

        return Collections.emptyList();
    }

    @Override
    public void unregister(String adapterName) {
        if (adapterName == null) {
            throw new IllegalArgumentException("Cannot unregister adapter [null] from registry.");
        }

        this.adapterMap.remove(adapterName);
    }

    @Override
    public Set<String> getNames() {
        return new HashSet<String>(this.adapterMap.keySet());
    }

    @Override
    public Set<AdapterRegistryEntry> get(String name) {
        return new HashSet<AdapterRegistryEntry>(this.adapterMap.get(name));
    }

    @Override
    public Set<AdapterRegistryEntry> getAll() {
        Set<AdapterRegistryEntry> entries = new HashSet<AdapterRegistryEntry>();
        for (Set<AdapterRegistryEntry> currentEntries : this.adapterMap.values()) {
            entries.addAll(currentEntries);
        }
        return entries;
    }

    @Override
    public String list() {
        StringBuilder result = new StringBuilder();
        for (String adapter : this.adapterMap.keySet()) {
            result.append(adapter);
            for (AdapterRegistryEntry entry : this.adapterMap.get(adapter)) {
                result.append(entry);
                result.append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }

}
