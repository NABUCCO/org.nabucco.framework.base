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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.PermissionExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * UiExtension<p/>NABUCCO User Interface Action extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-20
 */
public abstract class UiExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String GRANT = "grant";

    public static final String PERMISSIONS = "permissions";

    /** Default access when no permission is defined. */
    private EnumerationProperty grant;

    /** List of permissions to validate for this ui element. */
    private NabuccoList<PermissionExtension> permissions;

    /** Constructs a new UiExtension instance. */
    public UiExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the UiExtension.
     */
    protected void cloneObject(UiExtension clone) {
        super.cloneObject(clone);
        if ((this.getGrant() != null)) {
            clone.setGrant(this.getGrant().cloneObject());
        }
        if ((this.permissions != null)) {
            clone.permissions = this.permissions.cloneCollection();
        }
    }

    /**
     * Getter for the PermissionsJPA.
     *
     * @return the List<PermissionExtension>.
     */
    List<PermissionExtension> getPermissionsJPA() {
        if ((this.permissions == null)) {
            this.permissions = new NabuccoListImpl<PermissionExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<PermissionExtension>) this.permissions).getDelegate();
    }

    /**
     * Setter for the PermissionsJPA.
     *
     * @param permissions the List<PermissionExtension>.
     */
    void setPermissionsJPA(List<PermissionExtension> permissions) {
        if ((this.permissions == null)) {
            this.permissions = new NabuccoListImpl<PermissionExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<PermissionExtension>) this.permissions).setDelegate(permissions);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(GRANT, PropertyDescriptorSupport.createDatatype(GRANT, EnumerationProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PERMISSIONS, PropertyDescriptorSupport.createCollection(PERMISSIONS, PermissionExtension.class,
                3, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(UiExtension.getPropertyDescriptor(GRANT), this.getGrant(), null));
        properties.add(super.createProperty(UiExtension.getPropertyDescriptor(PERMISSIONS), this.permissions, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(GRANT) && (property.getType() == EnumerationProperty.class))) {
            this.setGrant(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PERMISSIONS) && (property.getType() == PermissionExtension.class))) {
            this.permissions = ((NabuccoList<PermissionExtension>) property.getInstance());
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
        final UiExtension other = ((UiExtension) obj);
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
        result = ((PRIME * result) + ((this.grant == null) ? 0 : this.grant.hashCode()));
        return result;
    }

    @Override
    public abstract UiExtension cloneObject();

    /**
     * Default access when no permission is defined.
     *
     * @param grant the EnumerationProperty.
     */
    public void setGrant(EnumerationProperty grant) {
        this.grant = grant;
    }

    /**
     * Default access when no permission is defined.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getGrant() {
        return this.grant;
    }

    /**
     * List of permissions to validate for this ui element.
     *
     * @return the NabuccoList<PermissionExtension>.
     */
    public NabuccoList<PermissionExtension> getPermissions() {
        if ((this.permissions == null)) {
            this.permissions = new NabuccoListImpl<PermissionExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.permissions;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(UiExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(UiExtension.class).getAllProperties();
    }
}
