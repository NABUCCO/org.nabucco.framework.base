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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;

/**
 * Container holding all component relations of a datatype.
 * 
 * @author Nicolas Moser, PRODYNA AG
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public final class ComponentRelationContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<ComponentRelationType, NabuccoList<ComponentRelation<?>>> relationMap = new HashMap<ComponentRelationType, NabuccoList<ComponentRelation<?>>>();

    /**
     * Checks whether the container holds component relations or not.
     * 
     * @return <b>true</b> if the container does not hold any relations, <b>false</b> if it does
     */
    public boolean isEmpty() {
        return this.relationMap.isEmpty();
    }

    /**
     * Getter for the component relations for a given relation type.
     * 
     * @param type
     *            the component realation type
     * 
     * @return the list of component relations for this type
     */
    public List<ComponentRelation<?>> getComponentRelations(ComponentRelationType type) {
        if (type == null) {
            return Collections.emptyList();
        }

        NabuccoList<ComponentRelation<?>> list = this.relationMap.get(type);
        if (list == null) {
            list = new NabuccoListImpl<ComponentRelation<?>>();
            this.relationMap.put(type, list);
        }

        return list;
    }

    /**
     * Getter for the map of component relations for a all relation types.
     * 
     * @return a cloned map of all component relations
     */
    public Map<ComponentRelationType, NabuccoList<ComponentRelation<?>>> getComponentRelationMap() {
        return new HashMap<ComponentRelationType, NabuccoList<ComponentRelation<?>>>(this.relationMap);
    }

    /**
     * Getter for the component relations of a all relation types.
     * 
     * @return the list of all component relations
     */
    public List<ComponentRelation<?>> getAllComponentRelations() {
        List<ComponentRelation<?>> allRelations = new ArrayList<ComponentRelation<?>>();
        for (List<ComponentRelation<?>> relationList : this.relationMap.values()) {
            allRelations.addAll(relationList);
        }
        return allRelations;
    }

    /**
     * Puts a component relation to the relation container.
     * 
     * @param relation
     *            the relation to add
     */
    public void putComponentRelation(ComponentRelation<?> relation) {
        NabuccoList<ComponentRelation<?>> relations = this.relationMap.get(relation.getFunctionalType());
        if (relations == null) {
            relations = new NabuccoListImpl<ComponentRelation<?>>();
            this.relationMap.put(relation.getFunctionalType(), relations);
        }
        relations.add(relation);
    }

    /**
     * Puts a list of component relations to the relation container.
     * 
     * @param relationList
     *            the list of relations to add
     */
    public void putAllComponentRelations(List<ComponentRelation<?>> relationList) {
        for (ComponentRelation<?> componentRelation : relationList) {
            this.putComponentRelation(componentRelation);
        }
    }

    /**
     * Setter for the component relation list.
     * 
     * @param type
     *            the component relation type
     * @param relations
     *            the list of relations to set
     * 
     * @return the old relations
     */
    public NabuccoList<ComponentRelation<?>> setComponentRelations(ComponentRelationType type,
            NabuccoList<ComponentRelation<?>> relations) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot set component relations for type [null].");
        }
        if (relations == null) {
            relations = new NabuccoListImpl<ComponentRelation<?>>();
        }
        return this.relationMap.put(type, relations);
    }

    /**
     * Puts a list of component relations to the relation container.
     * 
     * @deprecated use {@link ComponentRelationContainer#putAllComponentRelations(List)} instead.
     * 
     * @param relationList
     *            the list of relations to add
     */
    @Deprecated
    public void putAllComponentRelation(List<ComponentRelation<?>> relationList) {
        this.putAllComponentRelations(relationList);
    }

    /**
     * Removes a component relation from the relation container
     * 
     * @param relation
     *            the relation to remove
     */
    public void removeComponentRelation(ComponentRelation<?> relation) {
        if (relation == null || relation.getFunctionalType() == null) {
            return;
        }

        List<ComponentRelation<?>> list = this.relationMap.get(relation.getFunctionalType());

        if (list != null) {
            list.remove(relation);
        }
    }

    /**
     * Replaces a component relation from the relation container with another component relation
     * 
     * @param oldRelation
     *            the old relation to replace
     * @param newRelation
     *            the new relation
     */
    public void replaceComponentRelation(ComponentRelation<?> oldRelation, ComponentRelation<?> newRelation) {
        List<ComponentRelation<?>> list = this.relationMap.get(oldRelation.getFunctionalType());
        list.set(list.indexOf(oldRelation), newRelation);
    }

    /**
     * Removes all existing component relations.
     */
    public void clear() {
        for (NabuccoList<ComponentRelation<?>> relations : this.relationMap.values()) {
            relations.clear();
        }
    }

    /**
     * Clones the component relation container.
     * 
     * @return the instance clone
     */
    public ComponentRelationContainer cloneObject() {
        ComponentRelationContainer clone = new ComponentRelationContainer();
        for (ComponentRelation<?> relation : this.getAllComponentRelations()) {
            clone.putComponentRelation(relation.cloneObject());
        }
        return clone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.relationMap == null) ? 0 : this.relationMap.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ComponentRelationContainer other = (ComponentRelationContainer) obj;
        if (this.relationMap == null) {
            if (other.relationMap != null) {
                return false;
            }
        } else if (!this.relationMap.equals(other.relationMap)) {
            return false;
        }
        return true;
    }
}
