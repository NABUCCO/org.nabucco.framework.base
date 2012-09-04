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
package org.nabucco.framework.base.facade.datatype.extension.schema.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ClassExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ExtensionConfigurationExtension<p/>Extension for assocating the ExtensionPoint to the ExtensionPoint Schema.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class ExtensionConfigurationExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String EXTENSIONPOINT = "extensionPoint";

    public static final String EXTENSIONCLASS = "extensionClass";

    public static final String RELOADABLE = "reloadable";

    /** The id of the extension point. */
    private StringProperty extensionPoint;

    /** The class of the extension point schema. */
    private ClassExtension extensionClass;

    /** The extension is reloadabe, the consumer has to implement an extensionlistener. */
    private BooleanProperty reloadable;

    /** Constructs a new ExtensionConfigurationExtension instance. */
    public ExtensionConfigurationExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ExtensionConfigurationExtension.
     */
    protected void cloneObject(ExtensionConfigurationExtension clone) {
        super.cloneObject(clone);
        if ((this.getExtensionPoint() != null)) {
            clone.setExtensionPoint(this.getExtensionPoint().cloneObject());
        }
        if ((this.getExtensionClass() != null)) {
            clone.setExtensionClass(this.getExtensionClass().cloneObject());
        }
        if ((this.getReloadable() != null)) {
            clone.setReloadable(this.getReloadable().cloneObject());
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
        propertyMap.put(EXTENSIONPOINT, PropertyDescriptorSupport.createDatatype(EXTENSIONPOINT, StringProperty.class,
                2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(EXTENSIONCLASS, PropertyDescriptorSupport.createDatatype(EXTENSIONCLASS, ClassExtension.class,
                3, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(RELOADABLE, PropertyDescriptorSupport.createDatatype(RELOADABLE, BooleanProperty.class, 4,
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
        properties.add(super.createProperty(ExtensionConfigurationExtension.getPropertyDescriptor(EXTENSIONPOINT),
                this.getExtensionPoint(), null));
        properties.add(super.createProperty(ExtensionConfigurationExtension.getPropertyDescriptor(EXTENSIONCLASS),
                this.getExtensionClass(), null));
        properties.add(super.createProperty(ExtensionConfigurationExtension.getPropertyDescriptor(RELOADABLE),
                this.getReloadable(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXTENSIONPOINT) && (property.getType() == StringProperty.class))) {
            this.setExtensionPoint(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EXTENSIONCLASS) && (property.getType() == ClassExtension.class))) {
            this.setExtensionClass(((ClassExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RELOADABLE) && (property.getType() == BooleanProperty.class))) {
            this.setReloadable(((BooleanProperty) property.getInstance()));
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
        final ExtensionConfigurationExtension other = ((ExtensionConfigurationExtension) obj);
        if ((this.extensionPoint == null)) {
            if ((other.extensionPoint != null))
                return false;
        } else if ((!this.extensionPoint.equals(other.extensionPoint)))
            return false;
        if ((this.extensionClass == null)) {
            if ((other.extensionClass != null))
                return false;
        } else if ((!this.extensionClass.equals(other.extensionClass)))
            return false;
        if ((this.reloadable == null)) {
            if ((other.reloadable != null))
                return false;
        } else if ((!this.reloadable.equals(other.reloadable)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.extensionPoint == null) ? 0 : this.extensionPoint.hashCode()));
        result = ((PRIME * result) + ((this.extensionClass == null) ? 0 : this.extensionClass.hashCode()));
        result = ((PRIME * result) + ((this.reloadable == null) ? 0 : this.reloadable.hashCode()));
        return result;
    }

    @Override
    public ExtensionConfigurationExtension cloneObject() {
        ExtensionConfigurationExtension clone = new ExtensionConfigurationExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The id of the extension point.
     *
     * @param extensionPoint the StringProperty.
     */
    public void setExtensionPoint(StringProperty extensionPoint) {
        this.extensionPoint = extensionPoint;
    }

    /**
     * The id of the extension point.
     *
     * @return the StringProperty.
     */
    public StringProperty getExtensionPoint() {
        return this.extensionPoint;
    }

    /**
     * The class of the extension point schema.
     *
     * @param extensionClass the ClassExtension.
     */
    public void setExtensionClass(ClassExtension extensionClass) {
        this.extensionClass = extensionClass;
    }

    /**
     * The class of the extension point schema.
     *
     * @return the ClassExtension.
     */
    public ClassExtension getExtensionClass() {
        return this.extensionClass;
    }

    /**
     * The extension is reloadabe, the consumer has to implement an extensionlistener.
     *
     * @param reloadable the BooleanProperty.
     */
    public void setReloadable(BooleanProperty reloadable) {
        this.reloadable = reloadable;
    }

    /**
     * The extension is reloadabe, the consumer has to implement an extensionlistener.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getReloadable() {
        return this.reloadable;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ExtensionConfigurationExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ExtensionConfigurationExtension.class).getAllProperties();
    }
}
