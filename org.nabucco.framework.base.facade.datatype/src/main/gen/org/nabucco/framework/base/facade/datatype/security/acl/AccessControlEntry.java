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
package org.nabucco.framework.base.facade.datatype.security.acl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.acl.AccessControlEntryType;
import org.nabucco.framework.base.facade.datatype.security.acl.AccessControlGrantType;

/**
 * AccessControlEntry<p/>A single entry in the ACL<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-28
 */
public class AccessControlEntry extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final AccessControlGrantType GRANTTYPE_DEFAULT = AccessControlGrantType.GRANT;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "m1,1;", "m1,1;", "m1,1;",
            "l0,255;u0,n;m0,1;" };

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String GRANTTYPE = "grantType";

    public static final String CONTROLTYPE = "controlType";

    public static final String DESCRIPTION = "description";

    /** Name of the access control object. */
    private Name name;

    /** The security element which the access control element validates for. */
    private AccessControlEntryType type;

    /** The grant type of access control, whether the control type is granted or denied. */
    private AccessControlGrantType grantType;

    /** The type of access control. */
    private Code controlType;

    protected static final String CONTROLTYPE_CODEPATH = "org.nabucco.ace";

    /** Description of the access control object. */
    private Description description;

    /** Constructs a new AccessControlEntry instance. */
    public AccessControlEntry() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        grantType = GRANTTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the AccessControlEntry.
     */
    protected void cloneObject(AccessControlEntry clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        clone.setType(this.getType());
        clone.setGrantType(this.getGrantType());
        if ((this.getControlType() != null)) {
            clone.setControlType(this.getControlType().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
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
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, AccessControlEntryType.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(GRANTTYPE, PropertyDescriptorSupport.createEnumeration(GRANTTYPE, AccessControlGrantType.class,
                5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(CONTROLTYPE, PropertyDescriptorSupport.createDatatype(CONTROLTYPE, Code.class, 6,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION, CONTROLTYPE_CODEPATH));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 7,
                PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(AccessControlEntry.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(AccessControlEntry.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(AccessControlEntry.getPropertyDescriptor(GRANTTYPE), this.getGrantType(),
                null));
        properties.add(super.createProperty(AccessControlEntry.getPropertyDescriptor(CONTROLTYPE),
                this.getControlType(), null));
        properties.add(super.createProperty(AccessControlEntry.getPropertyDescriptor(DESCRIPTION), this.description,
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == AccessControlEntryType.class))) {
            this.setType(((AccessControlEntryType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(GRANTTYPE) && (property.getType() == AccessControlGrantType.class))) {
            this.setGrantType(((AccessControlGrantType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTROLTYPE) && (property.getType() == Code.class))) {
            this.setControlType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
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
        final AccessControlEntry other = ((AccessControlEntry) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.grantType == null)) {
            if ((other.grantType != null))
                return false;
        } else if ((!this.grantType.equals(other.grantType)))
            return false;
        if ((this.controlType == null)) {
            if ((other.controlType != null))
                return false;
        } else if ((!this.controlType.equals(other.controlType)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.grantType == null) ? 0 : this.grantType.hashCode()));
        result = ((PRIME * result) + ((this.controlType == null) ? 0 : this.controlType.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        return result;
    }

    @Override
    public AccessControlEntry cloneObject() {
        AccessControlEntry clone = new AccessControlEntry();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the access control object.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Name of the access control object.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Name of the access control object.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * The security element which the access control element validates for.
     *
     * @return the AccessControlEntryType.
     */
    public AccessControlEntryType getType() {
        return this.type;
    }

    /**
     * The security element which the access control element validates for.
     *
     * @param type the AccessControlEntryType.
     */
    public void setType(AccessControlEntryType type) {
        this.type = type;
    }

    /**
     * The security element which the access control element validates for.
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = AccessControlEntryType.valueOf(type);
        }
    }

    /**
     * The grant type of access control, whether the control type is granted or denied.
     *
     * @return the AccessControlGrantType.
     */
    public AccessControlGrantType getGrantType() {
        return this.grantType;
    }

    /**
     * The grant type of access control, whether the control type is granted or denied.
     *
     * @param grantType the AccessControlGrantType.
     */
    public void setGrantType(AccessControlGrantType grantType) {
        this.grantType = grantType;
    }

    /**
     * The grant type of access control, whether the control type is granted or denied.
     *
     * @param grantType the String.
     */
    public void setGrantType(String grantType) {
        if ((grantType == null)) {
            this.grantType = null;
        } else {
            this.grantType = AccessControlGrantType.valueOf(grantType);
        }
    }

    /**
     * The type of access control.
     *
     * @param controlType the Code.
     */
    public void setControlType(Code controlType) {
        this.controlType = controlType;
    }

    /**
     * The type of access control.
     *
     * @return the Code.
     */
    public Code getControlType() {
        return this.controlType;
    }

    /**
     * Description of the access control object.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Description of the access control object.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Description of the access control object.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AccessControlEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AccessControlEntry.class).getAllProperties();
    }

    /**
     * Getter for the ControlTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getControlTypeCodePath() {
        return new CodePath(CONTROLTYPE_CODEPATH);
    }
}
