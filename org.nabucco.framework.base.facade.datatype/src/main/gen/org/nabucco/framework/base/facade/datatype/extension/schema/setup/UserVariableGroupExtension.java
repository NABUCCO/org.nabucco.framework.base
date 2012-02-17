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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoMap;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoMapImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.LabelExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * UserVariableGroupExtension<p/>Schema definition configuration for User Variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-08
 */
public class UserVariableGroupExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;", "m0,n;" };

    public static final String ID = "id";

    public static final String LABELMAP = "labelMap";

    public static final String GROUPMAP = "groupMap";

    /** The identifier of the group. */
    private StringProperty id;

    /** Map of Labels, the language (EN, DE, etc.) will be used as key. */
    private NabuccoMap<LabelExtension> labelMap;

    /** Map of groups, the id of the group will be used as key. */
    private NabuccoMap<UserVariableGroupExtension> groupMap;

    /** Constructs a new UserVariableGroupExtension instance. */
    public UserVariableGroupExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the UserVariableGroupExtension.
     */
    protected void cloneObject(UserVariableGroupExtension clone) {
        super.cloneObject(clone);
        if ((this.getId() != null)) {
            clone.setId(this.getId().cloneObject());
        }
        if ((this.labelMap != null)) {
            clone.labelMap = this.labelMap.cloneCollection();
        }
        if ((this.groupMap != null)) {
            clone.groupMap = this.groupMap.cloneCollection();
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(ID, PropertyDescriptorSupport.createDatatype(ID, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LABELMAP, PropertyDescriptorSupport.createCollection(LABELMAP, LabelExtension.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(GROUPMAP, PropertyDescriptorSupport.createCollection(GROUPMAP,
                UserVariableGroupExtension.class, 4, PROPERTY_CONSTRAINTS[2], false,
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
        properties.add(super.createProperty(UserVariableGroupExtension.getPropertyDescriptor(ID), this.getId(), null));
        properties.add(super.createProperty(UserVariableGroupExtension.getPropertyDescriptor(LABELMAP), this.labelMap,
                null));
        properties.add(super.createProperty(UserVariableGroupExtension.getPropertyDescriptor(GROUPMAP), this.groupMap,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ID) && (property.getType() == StringProperty.class))) {
            this.setId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LABELMAP) && (property.getType() == LabelExtension.class))) {
            this.labelMap = ((NabuccoMap<LabelExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(GROUPMAP) && (property.getType() == UserVariableGroupExtension.class))) {
            this.groupMap = ((NabuccoMap<UserVariableGroupExtension>) property.getInstance());
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
        final UserVariableGroupExtension other = ((UserVariableGroupExtension) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        return result;
    }

    @Override
    public UserVariableGroupExtension cloneObject() {
        UserVariableGroupExtension clone = new UserVariableGroupExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The identifier of the group.
     *
     * @param id the StringProperty.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * The identifier of the group.
     *
     * @return the StringProperty.
     */
    public StringProperty getId() {
        return this.id;
    }

    /**
     * Map of Labels, the language (EN, DE, etc.) will be used as key.
     *
     * @return the NabuccoMap<LabelExtension>.
     */
    public NabuccoMap<LabelExtension> getLabelMap() {
        if ((this.labelMap == null)) {
            this.labelMap = new NabuccoMapImpl<LabelExtension>();
        }
        return this.labelMap;
    }

    /**
     * Map of groups, the id of the group will be used as key.
     *
     * @return the NabuccoMap<UserVariableGroupExtension>.
     */
    public NabuccoMap<UserVariableGroupExtension> getGroupMap() {
        if ((this.groupMap == null)) {
            this.groupMap = new NabuccoMapImpl<UserVariableGroupExtension>();
        }
        return this.groupMap;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(UserVariableGroupExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(UserVariableGroupExtension.class).getAllProperties();
    }
}
