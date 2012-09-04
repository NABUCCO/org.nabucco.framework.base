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
package org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterParameterExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * QueryFilterExtension<p/>Extension for filter configuration.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-10-24
 */
public class QueryFilterExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String QUERY = "query";

    public static final String PARAMETERS = "parameters";

    /** The name of the filter. */
    private StringProperty name;

    /** The description of the filter. */
    private StringProperty description;

    /** The filter query. */
    private StringProperty query;

    /** The filter parameters. */
    private NabuccoList<QueryFilterParameterExtension> parameters;

    /** Constructs a new QueryFilterExtension instance. */
    public QueryFilterExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the QueryFilterExtension.
     */
    protected void cloneObject(QueryFilterExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getQuery() != null)) {
            clone.setQuery(this.getQuery().cloneObject());
        }
        if ((this.parameters != null)) {
            clone.parameters = this.parameters.cloneCollection();
        }
    }

    /**
     * Getter for the ParametersJPA.
     *
     * @return the List<QueryFilterParameterExtension>.
     */
    List<QueryFilterParameterExtension> getParametersJPA() {
        if ((this.parameters == null)) {
            this.parameters = new NabuccoListImpl<QueryFilterParameterExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<QueryFilterParameterExtension>) this.parameters).getDelegate();
    }

    /**
     * Setter for the ParametersJPA.
     *
     * @param parameters the List<QueryFilterParameterExtension>.
     */
    void setParametersJPA(List<QueryFilterParameterExtension> parameters) {
        if ((this.parameters == null)) {
            this.parameters = new NabuccoListImpl<QueryFilterParameterExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<QueryFilterParameterExtension>) this.parameters).setDelegate(parameters);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(NAME, PropertyDescriptorSupport.createDatatype(NAME, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(QUERY, PropertyDescriptorSupport.createDatatype(QUERY, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PARAMETERS, PropertyDescriptorSupport.createCollection(PARAMETERS,
                QueryFilterParameterExtension.class, 5, PROPERTY_CONSTRAINTS[3], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(QueryFilterExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(QueryFilterExtension.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(QueryFilterExtension.getPropertyDescriptor(QUERY), this.getQuery(), null));
        properties.add(super.createProperty(QueryFilterExtension.getPropertyDescriptor(PARAMETERS), this.parameters,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(QUERY) && (property.getType() == StringProperty.class))) {
            this.setQuery(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PARAMETERS) && (property.getType() == QueryFilterParameterExtension.class))) {
            this.parameters = ((NabuccoList<QueryFilterParameterExtension>) property.getInstance());
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
        final QueryFilterExtension other = ((QueryFilterExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.query == null)) {
            if ((other.query != null))
                return false;
        } else if ((!this.query.equals(other.query)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.query == null) ? 0 : this.query.hashCode()));
        return result;
    }

    @Override
    public QueryFilterExtension cloneObject() {
        QueryFilterExtension clone = new QueryFilterExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the filter.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the filter.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The description of the filter.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * The description of the filter.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The filter query.
     *
     * @param query the StringProperty.
     */
    public void setQuery(StringProperty query) {
        this.query = query;
    }

    /**
     * The filter query.
     *
     * @return the StringProperty.
     */
    public StringProperty getQuery() {
        return this.query;
    }

    /**
     * The filter parameters.
     *
     * @return the NabuccoList<QueryFilterParameterExtension>.
     */
    public NabuccoList<QueryFilterParameterExtension> getParameters() {
        if ((this.parameters == null)) {
            this.parameters = new NabuccoListImpl<QueryFilterParameterExtension>(NabuccoCollectionState.INITIALIZED);
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
        return PropertyCache.getInstance().retrieve(QueryFilterExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(QueryFilterExtension.class).getAllProperties();
    }
}
