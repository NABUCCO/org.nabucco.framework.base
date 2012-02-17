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
package org.nabucco.framework.base.facade.datatype;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;

/**
 * DatatypeAccessor
 * <p/>
 * Utility class for accessing protected, technical datatype fields.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeAccessor {

    /**
     * Singleton instance.
     */
    private static DatatypeAccessor instance = new DatatypeAccessor();

    /**
     * Private constructor.
     */
    private DatatypeAccessor() {
    }

    /**
     * Singleton access.
     * 
     * @return the DatatypeAccessor instance.
     */
    public static DatatypeAccessor getInstance() {
        return instance;
    }

    /**
     * Returns the component relation container of a datatype.
     * 
     * @param datatype
     *            the datatype holding the component relations
     * 
     * @return the component relation container
     */
    public ComponentRelationContainer getComponentRelation(Datatype datatype) {
        if (!(datatype instanceof DatatypeSupport)) {
            return new ComponentRelationContainer();
        }
        if (datatype instanceof ComponentRelation<?>) {
            return new ComponentRelationContainer();
        }
        return ((DatatypeSupport) datatype).getComponentRelationContainer();
    }

    /**
     * Setter for the component relation container. Sets the component relation container into the
     * given datatype.
     * 
     * @param datatype
     *            the datatype to add the component relation container
     * @param container
     *            the component relation container to add
     */
    public void setComponentRelation(Datatype datatype, ComponentRelationContainer container) {
        if (!(datatype instanceof DatatypeSupport)) {
            return;
        }
        if (datatype instanceof ComponentRelation<?>) {
            return;
        }
        ((DatatypeSupport) datatype).setComponentRelationContainer(container);
    }

}
