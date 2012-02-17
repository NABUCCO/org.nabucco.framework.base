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
package org.nabucco.framework.base.facade.service.jmx.dynamic;

/**
 * MBeanArgument
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MBeanArgument {

    private final String name;

    private final String type;

    private final String description;

    /**
     * Creates a new {@link MBeanArgument} instance.
     * 
     * @param name
     *            the argument name
     * @param type
     *            the argument type
     * @param description
     *            the argument description
     */
    MBeanArgument(String name, String type, String description) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create MBeanArgument for name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create MBeanArgument for type [null].");
        }

        this.name = name;
        this.type = type;

        this.description = description != null ? description : type;
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Getter for the description.
     * 
     * @return Returns the description.
     */
    public String getDescription() {
        return this.description;
    }
}
