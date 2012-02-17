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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DependencyExtension<p/>The dependency extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-12-28
 */
public class DependencyExtension extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String TARGETPROPERTY = "targetProperty";

    public static final String CONDITION = "condition";

    /** The target property for the dependency */
    private StringProperty targetProperty;

    /** The condition on the dependency (NULL, VALUE etc.) */
    private EnumerationProperty condition;

    /** Constructs a new DependencyExtension instance. */
    public DependencyExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DependencyExtension.
     */
    protected void cloneObject(DependencyExtension clone) {
        super.cloneObject(clone);
        if ((this.getTargetProperty() != null)) {
            clone.setTargetProperty(this.getTargetProperty().cloneObject());
        }
        if ((this.getCondition() != null)) {
            clone.setCondition(this.getCondition().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(TARGETPROPERTY, PropertyDescriptorSupport.createDatatype(TARGETPROPERTY, StringProperty.class,
                3, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONDITION, PropertyDescriptorSupport.createDatatype(CONDITION, EnumerationProperty.class, 4,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DependencyExtension.getPropertyDescriptor(TARGETPROPERTY),
                this.getTargetProperty(), null));
        properties.add(super.createProperty(DependencyExtension.getPropertyDescriptor(CONDITION), this.getCondition(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TARGETPROPERTY) && (property.getType() == StringProperty.class))) {
            this.setTargetProperty(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONDITION) && (property.getType() == EnumerationProperty.class))) {
            this.setCondition(((EnumerationProperty) property.getInstance()));
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
        final DependencyExtension other = ((DependencyExtension) obj);
        if ((this.targetProperty == null)) {
            if ((other.targetProperty != null))
                return false;
        } else if ((!this.targetProperty.equals(other.targetProperty)))
            return false;
        if ((this.condition == null)) {
            if ((other.condition != null))
                return false;
        } else if ((!this.condition.equals(other.condition)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.targetProperty == null) ? 0 : this.targetProperty.hashCode()));
        result = ((PRIME * result) + ((this.condition == null) ? 0 : this.condition.hashCode()));
        return result;
    }

    @Override
    public DependencyExtension cloneObject() {
        DependencyExtension clone = new DependencyExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The target property for the dependency
     *
     * @param targetProperty the StringProperty.
     */
    public void setTargetProperty(StringProperty targetProperty) {
        this.targetProperty = targetProperty;
    }

    /**
     * The target property for the dependency
     *
     * @return the StringProperty.
     */
    public StringProperty getTargetProperty() {
        return this.targetProperty;
    }

    /**
     * The condition on the dependency (NULL, VALUE etc.)
     *
     * @param condition the EnumerationProperty.
     */
    public void setCondition(EnumerationProperty condition) {
        this.condition = condition;
    }

    /**
     * The condition on the dependency (NULL, VALUE etc.)
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getCondition() {
        return this.condition;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DependencyExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DependencyExtension.class).getAllProperties();
    }
}
