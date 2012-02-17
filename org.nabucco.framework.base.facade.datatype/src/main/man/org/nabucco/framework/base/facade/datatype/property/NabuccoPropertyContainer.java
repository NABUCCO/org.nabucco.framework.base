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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NabuccoPropertyContainer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoPropertyContainer {

    private Map<String, NabuccoPropertyDescriptor> properties;

    /**
     * Creates a new {@link NabuccoPropertyContainer} instance.
     * 
     * @param properties
     *            the map of property name to its descriptor
     */
    public NabuccoPropertyContainer(Map<String, NabuccoPropertyDescriptor> properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyContainer for properties [null].");
        }

        this.properties = Collections.unmodifiableMap(properties);
    }

    /**
     * Getter for the property with the given name.
     * 
     * @param name
     *            the name of the property to resolve
     * 
     * @return the property descriptor with the given name, or null if none was found
     */
    public NabuccoPropertyDescriptor getProperty(String name) {
        return this.properties.get(name);
    }

    /**
     * Getter for the properties.
     * 
     * @return Returns the properties.
     */
    public List<NabuccoPropertyDescriptor> getAllProperties() {
        return new ArrayList<NabuccoPropertyDescriptor>(properties.values());
    }

    /**
     * Getter for the properties.
     * 
     * @return Returns the properties.
     */
    public Map<String, NabuccoPropertyDescriptor> getPropertyMap() {
        return new HashMap<String, NabuccoPropertyDescriptor>(this.properties);
    }

}
