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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ListButtonExtension<p/>NABUCCO List Button Extension interface<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-02
 */
public class ListButtonExtension extends ButtonExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String SELECTION = "selection";

    public static final String MODIFICATION = "modification";

    /** Defines if the selection of any value is nessecary to process the button action */
    private BooleanProperty selection;

    /** Describes what art of modification is made by this button */
    private EnumerationProperty modification;

    /** Constructs a new ListButtonExtension instance. */
    public ListButtonExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ListButtonExtension.
     */
    protected void cloneObject(ListButtonExtension clone) {
        super.cloneObject(clone);
        if ((this.getSelection() != null)) {
            clone.setSelection(this.getSelection().cloneObject());
        }
        if ((this.getModification() != null)) {
            clone.setModification(this.getModification().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ButtonExtension.class).getPropertyMap());
        propertyMap.put(SELECTION, PropertyDescriptorSupport.createDatatype(SELECTION, BooleanProperty.class, 9,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MODIFICATION, PropertyDescriptorSupport.createDatatype(MODIFICATION, EnumerationProperty.class,
                10, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ListButtonExtension.getPropertyDescriptor(SELECTION), this.getSelection(),
                null));
        properties.add(super.createProperty(ListButtonExtension.getPropertyDescriptor(MODIFICATION),
                this.getModification(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SELECTION) && (property.getType() == BooleanProperty.class))) {
            this.setSelection(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODIFICATION) && (property.getType() == EnumerationProperty.class))) {
            this.setModification(((EnumerationProperty) property.getInstance()));
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
        final ListButtonExtension other = ((ListButtonExtension) obj);
        if ((this.selection == null)) {
            if ((other.selection != null))
                return false;
        } else if ((!this.selection.equals(other.selection)))
            return false;
        if ((this.modification == null)) {
            if ((other.modification != null))
                return false;
        } else if ((!this.modification.equals(other.modification)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.selection == null) ? 0 : this.selection.hashCode()));
        result = ((PRIME * result) + ((this.modification == null) ? 0 : this.modification.hashCode()));
        return result;
    }

    @Override
    public ListButtonExtension cloneObject() {
        ListButtonExtension clone = new ListButtonExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Defines if the selection of any value is nessecary to process the button action
     *
     * @param selection the BooleanProperty.
     */
    public void setSelection(BooleanProperty selection) {
        this.selection = selection;
    }

    /**
     * Defines if the selection of any value is nessecary to process the button action
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getSelection() {
        return this.selection;
    }

    /**
     * Describes what art of modification is made by this button
     *
     * @param modification the EnumerationProperty.
     */
    public void setModification(EnumerationProperty modification) {
        this.modification = modification;
    }

    /**
     * Describes what art of modification is made by this button
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getModification() {
        return this.modification;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ListButtonExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ListButtonExtension.class).getAllProperties();
    }
}
