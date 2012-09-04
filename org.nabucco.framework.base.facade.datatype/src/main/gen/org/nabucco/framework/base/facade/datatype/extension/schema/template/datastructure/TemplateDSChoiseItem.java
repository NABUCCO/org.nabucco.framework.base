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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElementItem;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateDSChoiseItem<p/>Template Datastructure Choise Element Item.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-03
 */
public class TemplateDSChoiseItem extends TemplateDSElementItem implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String SELECTIONPATH = "selectionPath";

    /** The path to the dynamic code representing the selection options */
    private StringProperty selectionPath;

    /** Constructs a new TemplateDSChoiseItem instance. */
    public TemplateDSChoiseItem() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDSChoiseItem.
     */
    protected void cloneObject(TemplateDSChoiseItem clone) {
        super.cloneObject(clone);
        if ((this.getSelectionPath() != null)) {
            clone.setSelectionPath(this.getSelectionPath().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateDSElementItem.class).getPropertyMap());
        propertyMap.put(SELECTIONPATH, PropertyDescriptorSupport.createDatatype(SELECTIONPATH, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateDSChoiseItem.getPropertyDescriptor(SELECTIONPATH),
                this.getSelectionPath(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SELECTIONPATH) && (property.getType() == StringProperty.class))) {
            this.setSelectionPath(((StringProperty) property.getInstance()));
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
        final TemplateDSChoiseItem other = ((TemplateDSChoiseItem) obj);
        if ((this.selectionPath == null)) {
            if ((other.selectionPath != null))
                return false;
        } else if ((!this.selectionPath.equals(other.selectionPath)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.selectionPath == null) ? 0 : this.selectionPath.hashCode()));
        return result;
    }

    @Override
    public TemplateDSChoiseItem cloneObject() {
        TemplateDSChoiseItem clone = new TemplateDSChoiseItem();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The path to the dynamic code representing the selection options
     *
     * @param selectionPath the StringProperty.
     */
    public void setSelectionPath(StringProperty selectionPath) {
        this.selectionPath = selectionPath;
    }

    /**
     * The path to the dynamic code representing the selection options
     *
     * @return the StringProperty.
     */
    public StringProperty getSelectionPath() {
        return this.selectionPath;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateDSChoiseItem.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDSChoiseItem.class).getAllProperties();
    }
}
