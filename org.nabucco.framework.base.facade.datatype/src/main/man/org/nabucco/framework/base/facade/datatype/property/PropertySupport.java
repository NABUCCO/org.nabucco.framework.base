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

/**
 * PropertySupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class PropertySupport<N extends Object> implements NabuccoProperty<N> {

    /** The property name. */
    private String name;

    /** The property class. */
    private Class<N> type;

    /** The property constraints. */
    private String constraints;

    /** Type of the property. */
    private PropertyType propertyType;

    /**
     * Creates a new {@link PropertySupport} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param constraints
     *            the property constraint string
     * @param propertyType
     *            the property type
     */
    PropertySupport(String name, Class<N> type, String constraints, PropertyType propertyType) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create NabuccoProperty with name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create NabuccoProperty with type [null].");
        }
        if (constraints == null) {
            throw new IllegalArgumentException(
                    "Cannot create NabuccoProperty with constraints [null].");
        }
        if (propertyType == null) {
            throw new IllegalArgumentException(
                    "Cannot create NabuccoProperty with propertyType [null].");
        }

        this.name = name;
        this.type = type;
        this.constraints = constraints;
        this.propertyType = propertyType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class<N> getType() {
        return this.type;
    }

    @Override
    public String getConstraints() {
        return this.constraints;
    }

    @Override
    public PropertyType getPropertyType() {
        return this.propertyType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.constraints == null) ? 0 : this.constraints.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.propertyType == null) ? 0 : this.propertyType.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PropertySupport<?> other = (PropertySupport<?>) obj;
        if (this.constraints == null) {
            if (other.constraints != null)
                return false;
        } else if (!this.constraints.equals(other.constraints))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        if (this.propertyType != other.propertyType)
            return false;
        if (this.type == null) {
            if (other.type != null)
                return false;
        } else if (!this.type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.propertyType);
        builder.append("<");
        builder.append(this.type.getSimpleName());
        builder.append("> (");
        builder.append(this.name);
        builder.append(")");
        return builder.toString();
    }

}
