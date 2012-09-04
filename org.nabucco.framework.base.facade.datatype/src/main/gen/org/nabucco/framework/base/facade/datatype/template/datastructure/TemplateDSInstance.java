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
package org.nabucco.framework.base.facade.datatype.template.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Version;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceElement;

/**
 * TemplateDSInstance<p/>The instance references the datatstructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-05
 */
public abstract class TemplateDSInstance extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "m1,1;" };

    public static final String EXTENSIONNAME = "extensionName";

    public static final String EXTENSIONVERSION = "extensionVersion";

    public static final String DATATSTRUCTURE = "datatstructure";

    /** The name of the instanciated extension */
    private Name extensionName;

    /** The version of the referenced datastructure */
    private Version extensionVersion;

    /** The reference on the datatstructure */
    private TemplateDSInstanceElement datatstructure;

    /** Constructs a new TemplateDSInstance instance. */
    public TemplateDSInstance() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDSInstance.
     */
    protected void cloneObject(TemplateDSInstance clone) {
        super.cloneObject(clone);
        if ((this.getExtensionName() != null)) {
            clone.setExtensionName(this.getExtensionName().cloneObject());
        }
        if ((this.getExtensionVersion() != null)) {
            clone.setExtensionVersion(this.getExtensionVersion().cloneObject());
        }
        if ((this.getDatatstructure() != null)) {
            clone.setDatatstructure(this.getDatatstructure().cloneObject());
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
        propertyMap.put(EXTENSIONNAME,
                PropertyDescriptorSupport.createBasetype(EXTENSIONNAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(EXTENSIONVERSION, PropertyDescriptorSupport.createBasetype(EXTENSIONVERSION, Version.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DATATSTRUCTURE, PropertyDescriptorSupport
                .createDatatype(DATATSTRUCTURE, TemplateDSInstanceElement.class, 5, PROPERTY_CONSTRAINTS[2], false,
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
        properties.add(super.createProperty(TemplateDSInstance.getPropertyDescriptor(EXTENSIONNAME),
                this.extensionName, null));
        properties.add(super.createProperty(TemplateDSInstance.getPropertyDescriptor(EXTENSIONVERSION),
                this.extensionVersion, null));
        properties.add(super.createProperty(TemplateDSInstance.getPropertyDescriptor(DATATSTRUCTURE),
                this.getDatatstructure(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXTENSIONNAME) && (property.getType() == Name.class))) {
            this.setExtensionName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EXTENSIONVERSION) && (property.getType() == Version.class))) {
            this.setExtensionVersion(((Version) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATATSTRUCTURE) && (property.getType() == TemplateDSInstanceElement.class))) {
            this.setDatatstructure(((TemplateDSInstanceElement) property.getInstance()));
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
        final TemplateDSInstance other = ((TemplateDSInstance) obj);
        if ((this.extensionName == null)) {
            if ((other.extensionName != null))
                return false;
        } else if ((!this.extensionName.equals(other.extensionName)))
            return false;
        if ((this.extensionVersion == null)) {
            if ((other.extensionVersion != null))
                return false;
        } else if ((!this.extensionVersion.equals(other.extensionVersion)))
            return false;
        if ((this.datatstructure == null)) {
            if ((other.datatstructure != null))
                return false;
        } else if ((!this.datatstructure.equals(other.datatstructure)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.extensionName == null) ? 0 : this.extensionName.hashCode()));
        result = ((PRIME * result) + ((this.extensionVersion == null) ? 0 : this.extensionVersion.hashCode()));
        result = ((PRIME * result) + ((this.datatstructure == null) ? 0 : this.datatstructure.hashCode()));
        return result;
    }

    @Override
    public abstract TemplateDSInstance cloneObject();

    /**
     * The name of the instanciated extension
     *
     * @return the Name.
     */
    public Name getExtensionName() {
        return this.extensionName;
    }

    /**
     * The name of the instanciated extension
     *
     * @param extensionName the Name.
     */
    public void setExtensionName(Name extensionName) {
        this.extensionName = extensionName;
    }

    /**
     * The name of the instanciated extension
     *
     * @param extensionName the String.
     */
    public void setExtensionName(String extensionName) {
        if ((this.extensionName == null)) {
            if ((extensionName == null)) {
                return;
            }
            this.extensionName = new Name();
        }
        this.extensionName.setValue(extensionName);
    }

    /**
     * The version of the referenced datastructure
     *
     * @return the Version.
     */
    public Version getExtensionVersion() {
        return this.extensionVersion;
    }

    /**
     * The version of the referenced datastructure
     *
     * @param extensionVersion the Version.
     */
    public void setExtensionVersion(Version extensionVersion) {
        this.extensionVersion = extensionVersion;
    }

    /**
     * The version of the referenced datastructure
     *
     * @param extensionVersion the Long.
     */
    public void setExtensionVersion(Long extensionVersion) {
        if ((this.extensionVersion == null)) {
            if ((extensionVersion == null)) {
                return;
            }
            this.extensionVersion = new Version();
        }
        this.extensionVersion.setValue(extensionVersion);
    }

    /**
     * The reference on the datatstructure
     *
     * @param datatstructure the TemplateDSInstanceElement.
     */
    public void setDatatstructure(TemplateDSInstanceElement datatstructure) {
        this.datatstructure = datatstructure;
    }

    /**
     * The reference on the datatstructure
     *
     * @return the TemplateDSInstanceElement.
     */
    public TemplateDSInstanceElement getDatatstructure() {
        return this.datatstructure;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateDSInstance.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDSInstance.class).getAllProperties();
    }
}
