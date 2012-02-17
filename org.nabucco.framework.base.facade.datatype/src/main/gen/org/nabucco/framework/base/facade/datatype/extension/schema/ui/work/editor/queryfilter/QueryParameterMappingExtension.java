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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.queryfilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.queryfilter.QueryParameterExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * QueryParameterMappingExtension<p/>Query Parameter Mapping Extension. Used to setup mapping between datatype path and queryfilter paramter<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-12-30
 */
public class QueryParameterMappingExtension extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String PARAMETERS = "parameters";

    /** The mapping parameters */
    private NabuccoList<QueryParameterExtension> parameters;

    /** Constructs a new QueryParameterMappingExtension instance. */
    public QueryParameterMappingExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the QueryParameterMappingExtension.
     */
    protected void cloneObject(QueryParameterMappingExtension clone) {
        super.cloneObject(clone);
        if ((this.parameters != null)) {
            clone.parameters = this.parameters.cloneCollection();
        }
    }

    /**
     * Getter for the ParametersJPA.
     *
     * @return the List<QueryParameterExtension>.
     */
    List<QueryParameterExtension> getParametersJPA() {
        if ((this.parameters == null)) {
            this.parameters = new NabuccoListImpl<QueryParameterExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<QueryParameterExtension>) this.parameters).getDelegate();
    }

    /**
     * Setter for the ParametersJPA.
     *
     * @param parameters the List<QueryParameterExtension>.
     */
    void setParametersJPA(List<QueryParameterExtension> parameters) {
        if ((this.parameters == null)) {
            this.parameters = new NabuccoListImpl<QueryParameterExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<QueryParameterExtension>) this.parameters).setDelegate(parameters);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(PARAMETERS, PropertyDescriptorSupport.createCollection(PARAMETERS,
                QueryParameterExtension.class, 3, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(QueryParameterMappingExtension.getPropertyDescriptor(PARAMETERS),
                this.parameters, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PARAMETERS) && (property.getType() == QueryParameterExtension.class))) {
            this.parameters = ((NabuccoList<QueryParameterExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public QueryParameterMappingExtension cloneObject() {
        QueryParameterMappingExtension clone = new QueryParameterMappingExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The mapping parameters
     *
     * @return the NabuccoList<QueryParameterExtension>.
     */
    public NabuccoList<QueryParameterExtension> getParameters() {
        if ((this.parameters == null)) {
            this.parameters = new NabuccoListImpl<QueryParameterExtension>(NabuccoCollectionState.INITIALIZED);
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
        return PropertyCache.getInstance().retrieve(QueryParameterMappingExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(QueryParameterMappingExtension.class).getAllProperties();
    }
}
