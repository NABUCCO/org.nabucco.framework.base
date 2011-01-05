/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.property;

import java.util.Collection;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * PropertyType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum PropertyType {

    /**
     * A {@link Basetype} property.
     */
    BASETYPE("Basetype"),

    /**
     * An {@link Enumeration} property.
     */
    ENUMERATION("Enumeration"),

    /**
     * A {@link Datatype} property.
     */
    DATATYPE("Datatype"),

    /**
     * A property for primitive type wrapper objects.
     */
    SIMPLE("Simple"),

    /**
     * A {@link Collection} property.
     */
    LIST("Collection");

    private String name;

    /**
     * Creates a new {@link PropertyType} instance.
     * 
     * @param name
     *            the property name
     */
    private PropertyType(String name) {
        this.name = name;
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
