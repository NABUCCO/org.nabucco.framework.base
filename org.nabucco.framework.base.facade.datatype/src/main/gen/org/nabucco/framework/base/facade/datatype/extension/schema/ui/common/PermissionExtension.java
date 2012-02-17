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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PermissionExtension<p/>NABUCCO User Interface Permission extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-20
 */
public class PermissionExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String PERMISSIONID = "permissionId";

    public static final String GRANT = "grant";

    /** Name of the permission. */
    private StringProperty permissionId;

    /** Status of the element when granted. */
    private EnumerationProperty grant;

    /** Constructs a new PermissionExtension instance. */
    public PermissionExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PermissionExtension.
     */
    protected void cloneObject(PermissionExtension clone) {
        super.cloneObject(clone);
        if ((this.getPermissionId() != null)) {
            clone.setPermissionId(this.getPermissionId().cloneObject());
        }
        if ((this.getGrant() != null)) {
            clone.setGrant(this.getGrant().cloneObject());
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
        propertyMap.put(PERMISSIONID, PropertyDescriptorSupport.createDatatype(PERMISSIONID, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(GRANT, PropertyDescriptorSupport.createDatatype(GRANT, EnumerationProperty.class, 3,
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
        properties.add(super.createProperty(PermissionExtension.getPropertyDescriptor(PERMISSIONID),
                this.getPermissionId(), null));
        properties.add(super.createProperty(PermissionExtension.getPropertyDescriptor(GRANT), this.getGrant(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PERMISSIONID) && (property.getType() == StringProperty.class))) {
            this.setPermissionId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(GRANT) && (property.getType() == EnumerationProperty.class))) {
            this.setGrant(((EnumerationProperty) property.getInstance()));
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
        final PermissionExtension other = ((PermissionExtension) obj);
        if ((this.permissionId == null)) {
            if ((other.permissionId != null))
                return false;
        } else if ((!this.permissionId.equals(other.permissionId)))
            return false;
        if ((this.grant == null)) {
            if ((other.grant != null))
                return false;
        } else if ((!this.grant.equals(other.grant)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.permissionId == null) ? 0 : this.permissionId.hashCode()));
        result = ((PRIME * result) + ((this.grant == null) ? 0 : this.grant.hashCode()));
        return result;
    }

    @Override
    public PermissionExtension cloneObject() {
        PermissionExtension clone = new PermissionExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the permission.
     *
     * @param permissionId the StringProperty.
     */
    public void setPermissionId(StringProperty permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * Name of the permission.
     *
     * @return the StringProperty.
     */
    public StringProperty getPermissionId() {
        return this.permissionId;
    }

    /**
     * Status of the element when granted.
     *
     * @param grant the EnumerationProperty.
     */
    public void setGrant(EnumerationProperty grant) {
        this.grant = grant;
    }

    /**
     * Status of the element when granted.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getGrant() {
        return this.grant;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PermissionExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PermissionExtension.class).getAllProperties();
    }
}
