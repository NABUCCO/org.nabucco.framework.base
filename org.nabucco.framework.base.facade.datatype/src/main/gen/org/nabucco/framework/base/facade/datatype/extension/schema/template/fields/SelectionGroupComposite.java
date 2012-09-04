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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.fields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateComposite;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SelectionGroupComposite<p/>Composite element for selection stuff<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-29
 */
public class SelectionGroupComposite extends TemplateComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;" };

    public static final String ISMULTIPLE = "isMultiple";

    /** If set, 0..* selections are possible */
    private Flag isMultiple;

    /** Constructs a new SelectionGroupComposite instance. */
    public SelectionGroupComposite() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SelectionGroupComposite.
     */
    protected void cloneObject(SelectionGroupComposite clone) {
        super.cloneObject(clone);
        if ((this.getIsMultiple() != null)) {
            clone.setIsMultiple(this.getIsMultiple().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateComposite.class).getPropertyMap());
        propertyMap.put(ISMULTIPLE,
                PropertyDescriptorSupport.createBasetype(ISMULTIPLE, Flag.class, 6, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SelectionGroupComposite.getPropertyDescriptor(ISMULTIPLE), this.isMultiple,
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ISMULTIPLE) && (property.getType() == Flag.class))) {
            this.setIsMultiple(((Flag) property.getInstance()));
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
        final SelectionGroupComposite other = ((SelectionGroupComposite) obj);
        if ((this.isMultiple == null)) {
            if ((other.isMultiple != null))
                return false;
        } else if ((!this.isMultiple.equals(other.isMultiple)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.isMultiple == null) ? 0 : this.isMultiple.hashCode()));
        return result;
    }

    @Override
    public SelectionGroupComposite cloneObject() {
        SelectionGroupComposite clone = new SelectionGroupComposite();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * If set, 0..* selections are possible
     *
     * @return the Flag.
     */
    public Flag getIsMultiple() {
        return this.isMultiple;
    }

    /**
     * If set, 0..* selections are possible
     *
     * @param isMultiple the Flag.
     */
    public void setIsMultiple(Flag isMultiple) {
        this.isMultiple = isMultiple;
    }

    /**
     * If set, 0..* selections are possible
     *
     * @param isMultiple the Boolean.
     */
    public void setIsMultiple(Boolean isMultiple) {
        if ((this.isMultiple == null)) {
            if ((isMultiple == null)) {
                return;
            }
            this.isMultiple = new Flag();
        }
        this.isMultiple.setValue(isMultiple);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SelectionGroupComposite.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SelectionGroupComposite.class).getAllProperties();
    }
}
