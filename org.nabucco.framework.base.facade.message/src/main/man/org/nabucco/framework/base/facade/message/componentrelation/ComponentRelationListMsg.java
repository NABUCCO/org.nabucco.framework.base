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
package org.nabucco.framework.base.facade.message.componentrelation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * ComponentRelationListMsg
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class ComponentRelationListMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String COMPONENTRELATIONLIST = "componentRelationList";

    private NabuccoList<ComponentRelation<?>> componentRelationList;

    /** Constructs a new ComponentRelationListMsg instance. */
    public ComponentRelationListMsg() {
        super();
    }

    @Override
    public void init() {
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final ComponentRelationListMsg other = ((ComponentRelationListMsg) obj);
        if ((this.componentRelationList == null)) {
            if ((other.componentRelationList != null))
                return false;
        } else if ((!this.componentRelationList.equals(other.componentRelationList)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.componentRelationList == null) ? 0 : this.componentRelationList.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<ComponentRelationListMsg>\n");
        appendable.append(super.toString());
        appendable.append((("<componentRelationList>" + this.componentRelationList) + "</componentRelationList>\n"));
        appendable.append("</ComponentRelationListMsg>\n");
        return appendable.toString();
    }

    /**
     * Missing description at method getComponentRelationList.
     * 
     * @return the List<ComponentRelation>.
     */
    public List<ComponentRelation<?>> getComponentRelationList() {
        if ((this.componentRelationList == null)) {
            this.componentRelationList = new NabuccoListImpl<ComponentRelation<?>>();
        }
        return this.componentRelationList;
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ComponentRelationListMsg.getPropertyDescriptor(COMPONENTRELATIONLIST),
                this.componentRelationList));
        return properties;
    }

    /**
     * Getter for the PropertyDescriptor.
     * 
     * @param propertyName
     *            the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ComponentRelationListMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     * 
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ComponentRelationListMsg.class).getAllProperties();
    }

    /**
     * CreatePropertyContainer.
     * 
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();

        propertyMap.put(COMPONENTRELATIONLIST, PropertyDescriptorSupport.createCollection(COMPONENTRELATIONLIST,
                ComponentRelation.class, 0, "m0,n;", false, PropertyAssociationType.COMPOSITION));

        return new NabuccoPropertyContainer(propertyMap);
    }
}
