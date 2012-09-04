/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.facade.message.queryfilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.queryfilter.QueryFilterParameter;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * QueryFilterRq<p/>Request message of the Query Filter services.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-10-30
 */
public class QueryFilterRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l1,512;u0,n;m1,1;", "m0,n;" };

    public static final String FILTERID = "filterId";

    public static final String PARAMETERS = "parameters";

    /** ID of the configured query filter extension. */
    private ExtensionId filterId;

    /** List of Query parameters. */
    private NabuccoList<QueryFilterParameter> parameters;

    /** Constructs a new QueryFilterRq instance. */
    public QueryFilterRq() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(FILTERID, PropertyDescriptorSupport.createBasetype(FILTERID, ExtensionId.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(PARAMETERS, PropertyDescriptorSupport.createCollection(PARAMETERS, QueryFilterParameter.class,
                1, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(QueryFilterRq.getPropertyDescriptor(FILTERID), this.filterId));
        properties.add(super.createProperty(QueryFilterRq.getPropertyDescriptor(PARAMETERS), this.parameters));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FILTERID) && (property.getType() == ExtensionId.class))) {
            this.setFilterId(((ExtensionId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PARAMETERS) && (property.getType() == QueryFilterParameter.class))) {
            this.parameters = ((NabuccoList<QueryFilterParameter>) property.getInstance());
            return true;
        }
        return false;
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
        final QueryFilterRq other = ((QueryFilterRq) obj);
        if ((this.filterId == null)) {
            if ((other.filterId != null))
                return false;
        } else if ((!this.filterId.equals(other.filterId)))
            return false;
        if ((this.parameters == null)) {
            if ((other.parameters != null))
                return false;
        } else if ((!this.parameters.equals(other.parameters)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.filterId == null) ? 0 : this.filterId.hashCode()));
        result = ((PRIME * result) + ((this.parameters == null) ? 0 : this.parameters.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * ID of the configured query filter extension.
     *
     * @return the ExtensionId.
     */
    public ExtensionId getFilterId() {
        return this.filterId;
    }

    /**
     * ID of the configured query filter extension.
     *
     * @param filterId the ExtensionId.
     */
    public void setFilterId(ExtensionId filterId) {
        this.filterId = filterId;
    }

    /**
     * List of Query parameters.
     *
     * @return the NabuccoList<QueryFilterParameter>.
     */
    public NabuccoList<QueryFilterParameter> getParameters() {
        if ((this.parameters == null)) {
            this.parameters = new NabuccoListImpl<QueryFilterParameter>(NabuccoCollectionState.INITIALIZED);
        }
        return this.parameters;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(QueryFilterRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(QueryFilterRq.class).getAllProperties();
    }
}
