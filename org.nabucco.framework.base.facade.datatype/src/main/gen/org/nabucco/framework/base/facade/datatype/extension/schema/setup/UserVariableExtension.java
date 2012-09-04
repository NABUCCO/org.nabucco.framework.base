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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoMap;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoMapImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.LabelExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.UserVariableValueExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * UserVariableExtension<p/>Schema definition for a user variable.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-08
 */
public class UserVariableExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,n;",
            "m0,n;" };

    public static final String ID = "id";

    public static final String PATH = "path";

    public static final String TYPE = "type";

    public static final String DEFAULTVALUE = "defaultValue";

    public static final String EDITOR = "editor";

    public static final String VALUELIST = "valueList";

    public static final String LABELMAP = "labelMap";

    /** The identifier of the variable. */
    private StringProperty id;

    /** The group path of the variable */
    private StringProperty path;

    /** The type of the variable value */
    private StringProperty type;

    /** The default value, used if no value is defined for a specific user. */
    private StringProperty defaultValue;

    /** The associated editor for editing the value. */
    private StringProperty editor;

    /** The list of possible values. */
    private NabuccoList<UserVariableValueExtension> valueList;

    /** Map of labels, the language will be used as key. */
    private NabuccoMap<LabelExtension> labelMap;

    /** Constructs a new UserVariableExtension instance. */
    public UserVariableExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the UserVariableExtension.
     */
    protected void cloneObject(UserVariableExtension clone) {
        super.cloneObject(clone);
        if ((this.getId() != null)) {
            clone.setId(this.getId().cloneObject());
        }
        if ((this.getPath() != null)) {
            clone.setPath(this.getPath().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getDefaultValue() != null)) {
            clone.setDefaultValue(this.getDefaultValue().cloneObject());
        }
        if ((this.getEditor() != null)) {
            clone.setEditor(this.getEditor().cloneObject());
        }
        if ((this.valueList != null)) {
            clone.valueList = this.valueList.cloneCollection();
        }
        if ((this.labelMap != null)) {
            clone.labelMap = this.labelMap.cloneCollection();
        }
    }

    /**
     * Getter for the ValueListJPA.
     *
     * @return the List<UserVariableValueExtension>.
     */
    List<UserVariableValueExtension> getValueListJPA() {
        if ((this.valueList == null)) {
            this.valueList = new NabuccoListImpl<UserVariableValueExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<UserVariableValueExtension>) this.valueList).getDelegate();
    }

    /**
     * Setter for the ValueListJPA.
     *
     * @param valueList the List<UserVariableValueExtension>.
     */
    void setValueListJPA(List<UserVariableValueExtension> valueList) {
        if ((this.valueList == null)) {
            this.valueList = new NabuccoListImpl<UserVariableValueExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<UserVariableValueExtension>) this.valueList).setDelegate(valueList);
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
        propertyMap.put(PATH, PropertyDescriptorSupport.createDatatype(PATH, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEFAULTVALUE, PropertyDescriptorSupport.createDatatype(DEFAULTVALUE, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(EDITOR, PropertyDescriptorSupport.createDatatype(EDITOR, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(VALUELIST, PropertyDescriptorSupport.createCollection(VALUELIST,
                UserVariableValueExtension.class, 7, PROPERTY_CONSTRAINTS[5], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(LABELMAP, PropertyDescriptorSupport.createCollection(LABELMAP, LabelExtension.class, 8,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(UserVariableExtension.getPropertyDescriptor(ID), this.getId(), null));
        properties.add(super.createProperty(UserVariableExtension.getPropertyDescriptor(PATH), this.getPath(), null));
        properties.add(super.createProperty(UserVariableExtension.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(UserVariableExtension.getPropertyDescriptor(DEFAULTVALUE),
                this.getDefaultValue(), null));
        properties
                .add(super.createProperty(UserVariableExtension.getPropertyDescriptor(EDITOR), this.getEditor(), null));
        properties.add(super.createProperty(UserVariableExtension.getPropertyDescriptor(VALUELIST), this.valueList,
                null));
        properties
                .add(super.createProperty(UserVariableExtension.getPropertyDescriptor(LABELMAP), this.labelMap, null));
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
        } else if ((property.getName().equals(PATH) && (property.getType() == StringProperty.class))) {
            this.setPath(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == StringProperty.class))) {
            this.setType(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DEFAULTVALUE) && (property.getType() == StringProperty.class))) {
            this.setDefaultValue(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EDITOR) && (property.getType() == StringProperty.class))) {
            this.setEditor(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUELIST) && (property.getType() == UserVariableValueExtension.class))) {
            this.valueList = ((NabuccoList<UserVariableValueExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(LABELMAP) && (property.getType() == LabelExtension.class))) {
            this.labelMap = ((NabuccoMap<LabelExtension>) property.getInstance());
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
        final UserVariableExtension other = ((UserVariableExtension) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.path == null)) {
            if ((other.path != null))
                return false;
        } else if ((!this.path.equals(other.path)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.defaultValue == null)) {
            if ((other.defaultValue != null))
                return false;
        } else if ((!this.defaultValue.equals(other.defaultValue)))
            return false;
        if ((this.editor == null)) {
            if ((other.editor != null))
                return false;
        } else if ((!this.editor.equals(other.editor)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.path == null) ? 0 : this.path.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.defaultValue == null) ? 0 : this.defaultValue.hashCode()));
        result = ((PRIME * result) + ((this.editor == null) ? 0 : this.editor.hashCode()));
        return result;
    }

    @Override
    public UserVariableExtension cloneObject() {
        UserVariableExtension clone = new UserVariableExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The identifier of the variable.
     *
     * @param id the StringProperty.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * The identifier of the variable.
     *
     * @return the StringProperty.
     */
    public StringProperty getId() {
        return this.id;
    }

    /**
     * The group path of the variable
     *
     * @param path the StringProperty.
     */
    public void setPath(StringProperty path) {
        this.path = path;
    }

    /**
     * The group path of the variable
     *
     * @return the StringProperty.
     */
    public StringProperty getPath() {
        return this.path;
    }

    /**
     * The type of the variable value
     *
     * @param type the StringProperty.
     */
    public void setType(StringProperty type) {
        this.type = type;
    }

    /**
     * The type of the variable value
     *
     * @return the StringProperty.
     */
    public StringProperty getType() {
        return this.type;
    }

    /**
     * The default value, used if no value is defined for a specific user.
     *
     * @param defaultValue the StringProperty.
     */
    public void setDefaultValue(StringProperty defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * The default value, used if no value is defined for a specific user.
     *
     * @return the StringProperty.
     */
    public StringProperty getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * The associated editor for editing the value.
     *
     * @param editor the StringProperty.
     */
    public void setEditor(StringProperty editor) {
        this.editor = editor;
    }

    /**
     * The associated editor for editing the value.
     *
     * @return the StringProperty.
     */
    public StringProperty getEditor() {
        return this.editor;
    }

    /**
     * The list of possible values.
     *
     * @return the NabuccoList<UserVariableValueExtension>.
     */
    public NabuccoList<UserVariableValueExtension> getValueList() {
        if ((this.valueList == null)) {
            this.valueList = new NabuccoListImpl<UserVariableValueExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.valueList;
    }

    /**
     * Map of labels, the language will be used as key.
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
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(UserVariableExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(UserVariableExtension.class).getAllProperties();
    }
}
