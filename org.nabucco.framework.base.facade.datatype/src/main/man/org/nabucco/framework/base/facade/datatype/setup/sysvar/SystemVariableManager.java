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
package org.nabucco.framework.base.facade.datatype.setup.sysvar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * SystemVariableManager
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public final class SystemVariableManager {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            SystemVariableManager.class);

    private static SystemVariableManager instance;

    private Map<String, SystemVariableEntry> variableMap;

    private boolean evicted = true;

    private SystemVariableProvider provider;

    /**
     * Creates a new {@link SystemVariableManager} instance.
     */
    private SystemVariableManager() {
        this.variableMap = new HashMap<String, SystemVariableEntry>();
    }

    /**
     * Getter for the singleton instance.
     * 
     * @return the instance
     */
    public static synchronized SystemVariableManager getInstance() {
        if (instance == null) {
            instance = new SystemVariableManager();
        }
        return instance;
    }

    /**
     * Getter for the system variable with the given name.
     * 
     * @param name
     *            the variable name
     * 
     * @return the variable, or null if no variable was found
     */
    public synchronized SystemVariable getVariable(String name) {
        if (evicted) {
            load();
        }
        SystemVariable sv = this.variableMap.get(name).getActualValue();
        if (sv == null) {
            logger.warning("Getting null as system variable with id=[" + name + "]");
        }
        return sv;
    }

    /**
     * Getter for the system variable with the given name.
     * 
     * @param name
     *            the variable name
     * 
     * @return the variable, or null if no variable was found
     */
    public synchronized List<SystemVariable> getAllVariables() {
        if (evicted) {
            load();
        }

        List<SystemVariable> result = new ArrayList<SystemVariable>();

        Iterator<Entry<String, SystemVariableEntry>> iterator = this.variableMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, SystemVariableEntry> next = iterator.next();
            SystemVariable actualValue = next.getValue().getActualValue();
            if (actualValue != null) {
                result.add(actualValue);
            } else {
                logger.warning("Getting null as system variable with id=[" + next.getKey() + "]");
            }
        }
        return result;
    }

    /**
     * 
     */
    private void load() {
        logger.info("loading system variable from provider: " + this.provider.getClass().getName());
        long now = NabuccoSystem.getCurrentTimeMillis();

        if (provider == null) {
            logger.warning("SystemVariableProvider is null");
        }

        List<SystemVariable> providedVariables;
        try {
            providedVariables = provider.provideVariables();
        } catch (SystemVariableProviderException e) {
            logger.error(e, "unable to load system variables from provides");
            providedVariables = Collections.<SystemVariable> emptyList();
        }

        if (providedVariables.isEmpty()) {
            logger.warning("SystemVariableProvider: "
                    + this.provider.getClass().getName() + " returned no SystemVariables");
        }

        for (SystemVariable sv : providedVariables) {

            String name = sv.getName().getValue();
            List<SystemVariable> valueList;
            SystemVariableEntry entry = this.variableMap.get(name);
            if (entry == null) {
                valueList = new ArrayList<SystemVariable>();
                entry = new SystemVariableEntry(valueList);
                valueList.add(sv);
                this.variableMap.put(name, entry);
            } else {
                valueList = entry.getList();
                valueList.add(sv);
            }
            SystemVariable actual = getActual(valueList, now);
            if (actual != null) {
                entry.setActualValue(actual);
            }
        }

        this.evicted = false;
    }

    /**
     * Evict the {@link SystemVariableManager} and force a reload form the configured (via
     * {@link SystemVariableManager#init(SystemVariableProvider)}) provider.
     * 
     * @return <code>true</code> if the call evicted the {@link SystemVariableManager}.
     */
    public boolean evict() {
        if (!this.evicted) {
            return (this.evicted = true);
        }
        return false;

    }

    /**
     * Initializes the {@link SystemVariableManager} with a provider and clears the current variable
     * map.
     * 
     * @param svProvider
     *            the provider for {@link SystemVariable}'s
     */
    public synchronized void init(SystemVariableProvider svProvider) {

        if (svProvider == null) {
            logger.warning("SystemVariableProvider is null get() calls will fail");
        }

        this.evicted = true;

        this.variableMap.clear();

        this.provider = svProvider;

    }

    /**
     * Getter for the actual system variable.
     * 
     * @param valueList
     *            list of variables
     * @param now
     *            the current timestamp
     * 
     * @return the actual variable
     */
    SystemVariable getActual(List<SystemVariable> valueList, long now) {

        int size = valueList.size();
        if (size == 0) {
            return null;
        }

        SystemVariable actual = valueList.get(0);

        for (SystemVariable sv : valueList) {

            long svTime = sv.getEffectiveDate().getValue().getTime();

            if (svTime <= now && actual.getEffectiveDate().getValue().getTime() < svTime) {
                actual = sv;
            }

        }
        return actual;

    }
}
