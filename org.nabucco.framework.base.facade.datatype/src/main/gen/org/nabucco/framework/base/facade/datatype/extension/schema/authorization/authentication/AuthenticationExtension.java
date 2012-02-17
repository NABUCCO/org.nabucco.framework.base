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
package org.nabucco.framework.base.facade.datatype.extension.schema.authorization.authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.authorization.authentication.AuthenticationExtensionType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * AuthenticationExtension<p/>Authentication Type<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2012-01-29
 */
public abstract class AuthenticationExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String AUTHENTICATIONTYPE = "authenticationType";

    /** Authentication Type */
    protected AuthenticationExtensionType authenticationType;

    /** Constructs a new AuthenticationExtension instance. */
    public AuthenticationExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AuthenticationExtension.
     */
    protected void cloneObject(AuthenticationExtension clone) {
        super.cloneObject(clone);
        clone.setAuthenticationType(this.getAuthenticationType());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(AUTHENTICATIONTYPE, PropertyDescriptorSupport.createEnumeration(AUTHENTICATIONTYPE,
                AuthenticationExtensionType.class, 2, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(AuthenticationExtension.getPropertyDescriptor(AUTHENTICATIONTYPE),
                this.getAuthenticationType(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(AUTHENTICATIONTYPE) && (property.getType() == AuthenticationExtensionType.class))) {
            this.setAuthenticationType(((AuthenticationExtensionType) property.getInstance()));
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
        final AuthenticationExtension other = ((AuthenticationExtension) obj);
        if ((this.authenticationType == null)) {
            if ((other.authenticationType != null))
                return false;
        } else if ((!this.authenticationType.equals(other.authenticationType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.authenticationType == null) ? 0 : this.authenticationType.hashCode()));
        return result;
    }

    @Override
    public abstract AuthenticationExtension cloneObject();

    /**
     * Authentication Type
     *
     * @return the AuthenticationExtensionType.
     */
    public AuthenticationExtensionType getAuthenticationType() {
        return this.authenticationType;
    }

    /**
     * Authentication Type
     *
     * @param authenticationType the AuthenticationExtensionType.
     */
    public void setAuthenticationType(AuthenticationExtensionType authenticationType) {
        this.authenticationType = authenticationType;
    }

    /**
     * Authentication Type
     *
     * @param authenticationType the String.
     */
    public void setAuthenticationType(String authenticationType) {
        if ((authenticationType == null)) {
            this.authenticationType = null;
        } else {
            this.authenticationType = AuthenticationExtensionType.valueOf(authenticationType);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AuthenticationExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AuthenticationExtension.class).getAllProperties();
    }
}
