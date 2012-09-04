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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.shortcuts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ShortcutExtension<p/>NABUCCO User Interface Shortcut extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranosvkiy, PRODYNA AG, 2012-04-20
 */
public class ShortcutExtension extends WorkItemExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;" };

    public static final String ALIAS = "alias";

    public static final String FUNCTIONALTYPE = "functionalType";

    public static final String PARAMETER = "parameter";

    /** The alias for the shortcut */
    private StringProperty alias;

    /** The functional type of the shortcut */
    private StringProperty functionalType;

    /** The optional parameter for the datesource */
    private EnumerationProperty parameter;

    /** Constructs a new ShortcutExtension instance. */
    public ShortcutExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ShortcutExtension.
     */
    protected void cloneObject(ShortcutExtension clone) {
        super.cloneObject(clone);
        if ((this.getAlias() != null)) {
            clone.setAlias(this.getAlias().cloneObject());
        }
        if ((this.getFunctionalType() != null)) {
            clone.setFunctionalType(this.getFunctionalType().cloneObject());
        }
        if ((this.getParameter() != null)) {
            clone.setParameter(this.getParameter().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkItemExtension.class).getPropertyMap());
        propertyMap.put(ALIAS, PropertyDescriptorSupport.createDatatype(ALIAS, StringProperty.class, 11,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FUNCTIONALTYPE, PropertyDescriptorSupport.createDatatype(FUNCTIONALTYPE, StringProperty.class,
                12, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PARAMETER, PropertyDescriptorSupport.createDatatype(PARAMETER, EnumerationProperty.class, 13,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ShortcutExtension.getPropertyDescriptor(ALIAS), this.getAlias(), null));
        properties.add(super.createProperty(ShortcutExtension.getPropertyDescriptor(FUNCTIONALTYPE),
                this.getFunctionalType(), null));
        properties.add(super.createProperty(ShortcutExtension.getPropertyDescriptor(PARAMETER), this.getParameter(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ALIAS) && (property.getType() == StringProperty.class))) {
            this.setAlias(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FUNCTIONALTYPE) && (property.getType() == StringProperty.class))) {
            this.setFunctionalType(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PARAMETER) && (property.getType() == EnumerationProperty.class))) {
            this.setParameter(((EnumerationProperty) property.getInstance()));
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
        final ShortcutExtension other = ((ShortcutExtension) obj);
        if ((this.alias == null)) {
            if ((other.alias != null))
                return false;
        } else if ((!this.alias.equals(other.alias)))
            return false;
        if ((this.functionalType == null)) {
            if ((other.functionalType != null))
                return false;
        } else if ((!this.functionalType.equals(other.functionalType)))
            return false;
        if ((this.parameter == null)) {
            if ((other.parameter != null))
                return false;
        } else if ((!this.parameter.equals(other.parameter)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.alias == null) ? 0 : this.alias.hashCode()));
        result = ((PRIME * result) + ((this.functionalType == null) ? 0 : this.functionalType.hashCode()));
        result = ((PRIME * result) + ((this.parameter == null) ? 0 : this.parameter.hashCode()));
        return result;
    }

    @Override
    public ShortcutExtension cloneObject() {
        ShortcutExtension clone = new ShortcutExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The alias for the shortcut
     *
     * @param alias the StringProperty.
     */
    public void setAlias(StringProperty alias) {
        this.alias = alias;
    }

    /**
     * The alias for the shortcut
     *
     * @return the StringProperty.
     */
    public StringProperty getAlias() {
        return this.alias;
    }

    /**
     * The functional type of the shortcut
     *
     * @param functionalType the StringProperty.
     */
    public void setFunctionalType(StringProperty functionalType) {
        this.functionalType = functionalType;
    }

    /**
     * The functional type of the shortcut
     *
     * @return the StringProperty.
     */
    public StringProperty getFunctionalType() {
        return this.functionalType;
    }

    /**
     * The optional parameter for the datesource
     *
     * @param parameter the EnumerationProperty.
     */
    public void setParameter(EnumerationProperty parameter) {
        this.parameter = parameter;
    }

    /**
     * The optional parameter for the datesource
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getParameter() {
        return this.parameter;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ShortcutExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ShortcutExtension.class).getAllProperties();
    }
}
