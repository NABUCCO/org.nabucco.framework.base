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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.authorization.authentication.AuthenticationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.authorization.authentication.AuthenticationExtensionType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * LdapAuthenticationExtension<p/>LDAP Authentication<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2012-01-29
 */
public class LdapAuthenticationExtension extends AuthenticationExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final AuthenticationExtensionType AUTHENTICATIONTYPE_DEFAULT = AuthenticationExtensionType.LDAP;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,n;", "m1,1;",
            "m0,1;", "m0,1;", "m0,1;" };

    public static final String FACTORY = "factory";

    public static final String URL = "url";

    public static final String BASEDN = "baseDn";

    public static final String OBJECTFILTER = "objectFilter";

    public static final String RETURNATTRIBUTES = "returnAttributes";

    public static final String SECURITYTYPE = "securityType";

    public static final String SECURITYPROTOCOL = "securityProtocol";

    public static final String SECURITYPRINCIPAL = "securityPrincipal";

    public static final String SECURITYCREDENTIALS = "securityCredentials";

    /** LDAP Implementation Facatory */
    private EnumerationProperty factory;

    /** URL of the LDAP */
    private StringProperty url;

    /** LDAP base Distinguished Name. */
    private StringProperty baseDn;

    /** LDAP Object Filter. */
    private StringProperty objectFilter;

    /** List of return attributes. */
    private NabuccoList<StringProperty> returnAttributes;

    /** Security Authentication Type of the LDAP */
    private EnumerationProperty securityType;

    /** Security Protocol of the LDAP */
    private EnumerationProperty securityProtocol;

    /** Security Principal of the LDAP */
    private StringProperty securityPrincipal;

    /** Security Credentials of the LDAP */
    private StringProperty securityCredentials;

    /** Constructs a new LdapAuthenticationExtension instance. */
    public LdapAuthenticationExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        authenticationType = AUTHENTICATIONTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the LdapAuthenticationExtension.
     */
    protected void cloneObject(LdapAuthenticationExtension clone) {
        super.cloneObject(clone);
        clone.setAuthenticationType(this.getAuthenticationType());
        if ((this.getFactory() != null)) {
            clone.setFactory(this.getFactory().cloneObject());
        }
        if ((this.getUrl() != null)) {
            clone.setUrl(this.getUrl().cloneObject());
        }
        if ((this.getBaseDn() != null)) {
            clone.setBaseDn(this.getBaseDn().cloneObject());
        }
        if ((this.getObjectFilter() != null)) {
            clone.setObjectFilter(this.getObjectFilter().cloneObject());
        }
        if ((this.returnAttributes != null)) {
            clone.returnAttributes = this.returnAttributes.cloneCollection();
        }
        if ((this.getSecurityType() != null)) {
            clone.setSecurityType(this.getSecurityType().cloneObject());
        }
        if ((this.getSecurityProtocol() != null)) {
            clone.setSecurityProtocol(this.getSecurityProtocol().cloneObject());
        }
        if ((this.getSecurityPrincipal() != null)) {
            clone.setSecurityPrincipal(this.getSecurityPrincipal().cloneObject());
        }
        if ((this.getSecurityCredentials() != null)) {
            clone.setSecurityCredentials(this.getSecurityCredentials().cloneObject());
        }
    }

    /**
     * Getter for the ReturnAttributesJPA.
     *
     * @return the List<StringProperty>.
     */
    List<StringProperty> getReturnAttributesJPA() {
        if ((this.returnAttributes == null)) {
            this.returnAttributes = new NabuccoListImpl<StringProperty>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<StringProperty>) this.returnAttributes).getDelegate();
    }

    /**
     * Setter for the ReturnAttributesJPA.
     *
     * @param returnAttributes the List<StringProperty>.
     */
    void setReturnAttributesJPA(List<StringProperty> returnAttributes) {
        if ((this.returnAttributes == null)) {
            this.returnAttributes = new NabuccoListImpl<StringProperty>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<StringProperty>) this.returnAttributes).setDelegate(returnAttributes);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(AuthenticationExtension.class).getPropertyMap());
        propertyMap.put(FACTORY, PropertyDescriptorSupport.createDatatype(FACTORY, EnumerationProperty.class, 3,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(URL, PropertyDescriptorSupport.createDatatype(URL, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(BASEDN, PropertyDescriptorSupport.createDatatype(BASEDN, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OBJECTFILTER, PropertyDescriptorSupport.createDatatype(OBJECTFILTER, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(RETURNATTRIBUTES, PropertyDescriptorSupport.createCollection(RETURNATTRIBUTES,
                StringProperty.class, 7, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SECURITYTYPE, PropertyDescriptorSupport.createDatatype(SECURITYTYPE, EnumerationProperty.class,
                8, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SECURITYPROTOCOL, PropertyDescriptorSupport.createDatatype(SECURITYPROTOCOL,
                EnumerationProperty.class, 9, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SECURITYPRINCIPAL, PropertyDescriptorSupport.createDatatype(SECURITYPRINCIPAL,
                StringProperty.class, 10, PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SECURITYCREDENTIALS, PropertyDescriptorSupport.createDatatype(SECURITYCREDENTIALS,
                StringProperty.class, 11, PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(FACTORY),
                this.getFactory(), null));
        properties
                .add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(URL), this.getUrl(), null));
        properties.add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(BASEDN),
                this.getBaseDn(), null));
        properties.add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(OBJECTFILTER),
                this.getObjectFilter(), null));
        properties.add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(RETURNATTRIBUTES),
                this.returnAttributes, null));
        properties.add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(SECURITYTYPE),
                this.getSecurityType(), null));
        properties.add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(SECURITYPROTOCOL),
                this.getSecurityProtocol(), null));
        properties.add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(SECURITYPRINCIPAL),
                this.getSecurityPrincipal(), null));
        properties.add(super.createProperty(LdapAuthenticationExtension.getPropertyDescriptor(SECURITYCREDENTIALS),
                this.getSecurityCredentials(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FACTORY) && (property.getType() == EnumerationProperty.class))) {
            this.setFactory(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(URL) && (property.getType() == StringProperty.class))) {
            this.setUrl(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BASEDN) && (property.getType() == StringProperty.class))) {
            this.setBaseDn(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OBJECTFILTER) && (property.getType() == StringProperty.class))) {
            this.setObjectFilter(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RETURNATTRIBUTES) && (property.getType() == StringProperty.class))) {
            this.returnAttributes = ((NabuccoList<StringProperty>) property.getInstance());
            return true;
        } else if ((property.getName().equals(SECURITYTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setSecurityType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SECURITYPROTOCOL) && (property.getType() == EnumerationProperty.class))) {
            this.setSecurityProtocol(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SECURITYPRINCIPAL) && (property.getType() == StringProperty.class))) {
            this.setSecurityPrincipal(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SECURITYCREDENTIALS) && (property.getType() == StringProperty.class))) {
            this.setSecurityCredentials(((StringProperty) property.getInstance()));
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
        final LdapAuthenticationExtension other = ((LdapAuthenticationExtension) obj);
        if ((this.factory == null)) {
            if ((other.factory != null))
                return false;
        } else if ((!this.factory.equals(other.factory)))
            return false;
        if ((this.url == null)) {
            if ((other.url != null))
                return false;
        } else if ((!this.url.equals(other.url)))
            return false;
        if ((this.baseDn == null)) {
            if ((other.baseDn != null))
                return false;
        } else if ((!this.baseDn.equals(other.baseDn)))
            return false;
        if ((this.objectFilter == null)) {
            if ((other.objectFilter != null))
                return false;
        } else if ((!this.objectFilter.equals(other.objectFilter)))
            return false;
        if ((this.securityType == null)) {
            if ((other.securityType != null))
                return false;
        } else if ((!this.securityType.equals(other.securityType)))
            return false;
        if ((this.securityProtocol == null)) {
            if ((other.securityProtocol != null))
                return false;
        } else if ((!this.securityProtocol.equals(other.securityProtocol)))
            return false;
        if ((this.securityPrincipal == null)) {
            if ((other.securityPrincipal != null))
                return false;
        } else if ((!this.securityPrincipal.equals(other.securityPrincipal)))
            return false;
        if ((this.securityCredentials == null)) {
            if ((other.securityCredentials != null))
                return false;
        } else if ((!this.securityCredentials.equals(other.securityCredentials)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.factory == null) ? 0 : this.factory.hashCode()));
        result = ((PRIME * result) + ((this.url == null) ? 0 : this.url.hashCode()));
        result = ((PRIME * result) + ((this.baseDn == null) ? 0 : this.baseDn.hashCode()));
        result = ((PRIME * result) + ((this.objectFilter == null) ? 0 : this.objectFilter.hashCode()));
        result = ((PRIME * result) + ((this.securityType == null) ? 0 : this.securityType.hashCode()));
        result = ((PRIME * result) + ((this.securityProtocol == null) ? 0 : this.securityProtocol.hashCode()));
        result = ((PRIME * result) + ((this.securityPrincipal == null) ? 0 : this.securityPrincipal.hashCode()));
        result = ((PRIME * result) + ((this.securityCredentials == null) ? 0 : this.securityCredentials.hashCode()));
        return result;
    }

    @Override
    public LdapAuthenticationExtension cloneObject() {
        LdapAuthenticationExtension clone = new LdapAuthenticationExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * LDAP Implementation Facatory
     *
     * @param factory the EnumerationProperty.
     */
    public void setFactory(EnumerationProperty factory) {
        this.factory = factory;
    }

    /**
     * LDAP Implementation Facatory
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getFactory() {
        return this.factory;
    }

    /**
     * URL of the LDAP
     *
     * @param url the StringProperty.
     */
    public void setUrl(StringProperty url) {
        this.url = url;
    }

    /**
     * URL of the LDAP
     *
     * @return the StringProperty.
     */
    public StringProperty getUrl() {
        return this.url;
    }

    /**
     * LDAP base Distinguished Name.
     *
     * @param baseDn the StringProperty.
     */
    public void setBaseDn(StringProperty baseDn) {
        this.baseDn = baseDn;
    }

    /**
     * LDAP base Distinguished Name.
     *
     * @return the StringProperty.
     */
    public StringProperty getBaseDn() {
        return this.baseDn;
    }

    /**
     * LDAP Object Filter.
     *
     * @param objectFilter the StringProperty.
     */
    public void setObjectFilter(StringProperty objectFilter) {
        this.objectFilter = objectFilter;
    }

    /**
     * LDAP Object Filter.
     *
     * @return the StringProperty.
     */
    public StringProperty getObjectFilter() {
        return this.objectFilter;
    }

    /**
     * List of return attributes.
     *
     * @return the NabuccoList<StringProperty>.
     */
    public NabuccoList<StringProperty> getReturnAttributes() {
        if ((this.returnAttributes == null)) {
            this.returnAttributes = new NabuccoListImpl<StringProperty>(NabuccoCollectionState.INITIALIZED);
        }
        return this.returnAttributes;
    }

    /**
     * Security Authentication Type of the LDAP
     *
     * @param securityType the EnumerationProperty.
     */
    public void setSecurityType(EnumerationProperty securityType) {
        this.securityType = securityType;
    }

    /**
     * Security Authentication Type of the LDAP
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getSecurityType() {
        return this.securityType;
    }

    /**
     * Security Protocol of the LDAP
     *
     * @param securityProtocol the EnumerationProperty.
     */
    public void setSecurityProtocol(EnumerationProperty securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    /**
     * Security Protocol of the LDAP
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getSecurityProtocol() {
        return this.securityProtocol;
    }

    /**
     * Security Principal of the LDAP
     *
     * @param securityPrincipal the StringProperty.
     */
    public void setSecurityPrincipal(StringProperty securityPrincipal) {
        this.securityPrincipal = securityPrincipal;
    }

    /**
     * Security Principal of the LDAP
     *
     * @return the StringProperty.
     */
    public StringProperty getSecurityPrincipal() {
        return this.securityPrincipal;
    }

    /**
     * Security Credentials of the LDAP
     *
     * @param securityCredentials the StringProperty.
     */
    public void setSecurityCredentials(StringProperty securityCredentials) {
        this.securityCredentials = securityCredentials;
    }

    /**
     * Security Credentials of the LDAP
     *
     * @return the StringProperty.
     */
    public StringProperty getSecurityCredentials() {
        return this.securityCredentials;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(LdapAuthenticationExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(LdapAuthenticationExtension.class).getAllProperties();
    }
}
