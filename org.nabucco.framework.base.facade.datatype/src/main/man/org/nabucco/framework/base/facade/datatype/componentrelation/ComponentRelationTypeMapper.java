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
package org.nabucco.framework.base.facade.datatype.componentrelation;

/**
 * ComponentRelationTypeMapper
 * <p/>
 * Mapper between {@link ComponentRelationType} and its string representations.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationTypeMapper {

    /** The seperator between enumeration and literal id. */
    private static final String SEPARATOR = "#";

    /**
     * Singleton instance.
     */
    private static ComponentRelationTypeMapper instance = new ComponentRelationTypeMapper();

    /**
     * Private constructor.
     */
    private ComponentRelationTypeMapper() {
    }

    /**
     * Singleton access.
     * 
     * @return the ComponentRelationTypeMapper instance.
     */
    public static ComponentRelationTypeMapper getInstance() {
        return instance;
    }

    /**
     * Map a component relation type to its string representation.
     * 
     * @param relationType
     *            the relation type to map
     * 
     * @return the relation type string representation
     */
    public String toString(ComponentRelationType relationType) {
        if (relationType == null) {
            return null;
        }

        if (relationType instanceof ComponentRelationTypeProxy) {
            return relationType.getId();
        }

        return relationType.getClass().getName() + SEPARATOR + relationType.getId();
    }

    /**
     * Map a component relation type string representation to the appropriate enumeration literal.
     * 
     * @param relationType
     *            the relation type to parse
     * 
     * @return the enumeration literal
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ComponentRelationType valueOf(String relationType) {
        if (relationType == null || relationType.isEmpty()) {
            return null;
        }

        try {
            if (!relationType.contains(SEPARATOR)) {
                return ComponentRelationTypeProxy.valueOf(relationType);
            }

            String className = relationType.substring(0, relationType.indexOf(SEPARATOR));
            String relationId = relationType.substring(relationType.indexOf(SEPARATOR) + 1, relationType.length());

            Class<?> relationClass = this.getClass().getClassLoader().loadClass(className);
            
            if (Enum.class.isAssignableFrom(relationClass)) {
                Enum value = Enum.valueOf((Class<Enum>) relationClass, relationId);
                return (ComponentRelationType) value;
            }

            if (ComponentRelationTypeProxy.class.isAssignableFrom(relationClass)) {
                return ComponentRelationTypeProxy.valueOf(relationId);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Error parsing component relation type [" + relationType + "].", e);
        }

        return null;
    }
}
