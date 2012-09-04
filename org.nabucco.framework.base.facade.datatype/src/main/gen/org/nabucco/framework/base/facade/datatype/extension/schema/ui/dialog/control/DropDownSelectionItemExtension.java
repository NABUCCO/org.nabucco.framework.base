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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DropDownSelectionItemExtension<p/>NABUCCO User Interface Dialog extension control.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-08
 */
public class DropDownSelectionItemExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String LOCALIZATIONPATH = "localizationPath";

    /** The id of the selection */
    private StringProperty id;

    /** The Name of the selection */
    private StringProperty name;

    /** The suffix that describes full qualified localizable path to the selection item. eg org.nabucco.ui.dialog.control.type */
    private StringProperty localizationPath;

    /** Constructs a new DropDownSelectionItemExtension instance. */
    public DropDownSelectionItemExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DropDownSelectionItemExtension.
     */
    protected void cloneObject(DropDownSelectionItemExtension clone) {
        super.cloneObject(clone);
        if ((this.getId() != null)) {
            clone.setId(this.getId().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getLocalizationPath() != null)) {
            clone.setLocalizationPath(this.getLocalizationPath().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(ID, PropertyDescriptorSupport.createDatatype(ID, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NAME, PropertyDescriptorSupport.createDatatype(NAME, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LOCALIZATIONPATH, PropertyDescriptorSupport.createDatatype(LOCALIZATIONPATH,
                StringProperty.class, 6, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DropDownSelectionItemExtension.getPropertyDescriptor(ID), this.getId(),
                null));
        properties.add(super.createProperty(DropDownSelectionItemExtension.getPropertyDescriptor(NAME), this.getName(),
                null));
        properties.add(super.createProperty(DropDownSelectionItemExtension.getPropertyDescriptor(LOCALIZATIONPATH),
                this.getLocalizationPath(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ID) && (property.getType() == StringProperty.class))) {
            this.setId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCALIZATIONPATH) && (property.getType() == StringProperty.class))) {
            this.setLocalizationPath(((StringProperty) property.getInstance()));
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
        final DropDownSelectionItemExtension other = ((DropDownSelectionItemExtension) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.localizationPath == null)) {
            if ((other.localizationPath != null))
                return false;
        } else if ((!this.localizationPath.equals(other.localizationPath)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.localizationPath == null) ? 0 : this.localizationPath.hashCode()));
        return result;
    }

    @Override
    public DropDownSelectionItemExtension cloneObject() {
        DropDownSelectionItemExtension clone = new DropDownSelectionItemExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The id of the selection
     *
     * @param id the StringProperty.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * The id of the selection
     *
     * @return the StringProperty.
     */
    public StringProperty getId() {
        return this.id;
    }

    /**
     * The Name of the selection
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The Name of the selection
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The suffix that describes full qualified localizable path to the selection item. eg org.nabucco.ui.dialog.control.type
     *
     * @param localizationPath the StringProperty.
     */
    public void setLocalizationPath(StringProperty localizationPath) {
        this.localizationPath = localizationPath;
    }

    /**
     * The suffix that describes full qualified localizable path to the selection item. eg org.nabucco.ui.dialog.control.type
     *
     * @return the StringProperty.
     */
    public StringProperty getLocalizationPath() {
        return this.localizationPath;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DropDownSelectionItemExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DropDownSelectionItemExtension.class).getAllProperties();
    }
}
