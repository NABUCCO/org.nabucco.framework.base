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
package org.nabucco.framework.base.facade.datatype.componentrelation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ComponentRelationContainer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ComponentRelationContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<ComponentRelationType, List<ComponentRelation<?>>> relationMap = new HashMap<ComponentRelationType, List<ComponentRelation<?>>>();

    /**
     * Checks whether the container holds component relations or not.
     * 
     * @return <b>true</b> if the container does not hold any relations, <b>false</b> if it does
     */
    public boolean isEmpty() {
        return this.relationMap.isEmpty();
    }

    /**
     * Getter for the component relations of a given relation strategy.
     * 
     * @param strategy
     *            the component realation strategy
     * 
     * @return the list of component relations for this strategy
     */
    public List<ComponentRelation<?>> getComponentRelations(ComponentRelationType type) {

        List<ComponentRelation<?>> list = this.relationMap.get(type);
        if (list == null) {
            list = new ArrayList<ComponentRelation<?>>();
            relationMap.put(type, list);
        }

        return list;
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
        List<ComponentRelation<?>> relations = this.relationMap.get(relation.getRelationType());
        if (relations == null) {
            relations = new ArrayList<ComponentRelation<?>>();
            this.relationMap.put(relation.getRelationType(), relations);
        }
        relations.add(relation);
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
}
