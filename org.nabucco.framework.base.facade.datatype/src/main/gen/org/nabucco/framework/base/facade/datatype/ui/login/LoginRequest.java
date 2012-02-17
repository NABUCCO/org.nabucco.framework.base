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
package org.nabucco.framework.base.facade.datatype.ui.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.security.credential.Password;

/**
 * LoginRequest<p/>A field in the NABUCCO User Interface.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-05
 */
public class LoginRequest extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,32;u0,n;m1,1;", "l3,128;u0,n;m1,1;", "l3,12;u0,n;m1,1;" };

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String TENANT = "tenant";

    /** The identifier of the user. */
    private UserId username;

    /** The password of the user. */
    private Password password;

    /** The login tenant. */
    private Tenant tenant;

    /** Constructs a new LoginRequest instance. */
    public LoginRequest() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the LoginRequest.
     */
    protected void cloneObject(LoginRequest clone) {
        super.cloneObject(clone);
        if ((this.getUsername() != null)) {
            clone.setUsername(this.getUsername().cloneObject());
        }
        if ((this.getPassword() != null)) {
            clone.setPassword(this.getPassword().cloneObject());
        }
        if ((this.getTenant() != null)) {
            clone.setTenant(this.getTenant().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(USERNAME,
                PropertyDescriptorSupport.createBasetype(USERNAME, UserId.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(PASSWORD,
                PropertyDescriptorSupport.createBasetype(PASSWORD, Password.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(TENANT,
                PropertyDescriptorSupport.createBasetype(TENANT, Tenant.class, 2, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(LoginRequest.getPropertyDescriptor(USERNAME), this.username, null));
        properties.add(super.createProperty(LoginRequest.getPropertyDescriptor(PASSWORD), this.password, null));
        properties.add(super.createProperty(LoginRequest.getPropertyDescriptor(TENANT), this.tenant, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(USERNAME) && (property.getType() == UserId.class))) {
            this.setUsername(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PASSWORD) && (property.getType() == Password.class))) {
            this.setPassword(((Password) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TENANT) && (property.getType() == Tenant.class))) {
            this.setTenant(((Tenant) property.getInstance()));
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
        final LoginRequest other = ((LoginRequest) obj);
        if ((this.username == null)) {
            if ((other.username != null))
                return false;
        } else if ((!this.username.equals(other.username)))
            return false;
        if ((this.password == null)) {
            if ((other.password != null))
                return false;
        } else if ((!this.password.equals(other.password)))
            return false;
        if ((this.tenant == null)) {
            if ((other.tenant != null))
                return false;
        } else if ((!this.tenant.equals(other.tenant)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.username == null) ? 0 : this.username.hashCode()));
        result = ((PRIME * result) + ((this.password == null) ? 0 : this.password.hashCode()));
        result = ((PRIME * result) + ((this.tenant == null) ? 0 : this.tenant.hashCode()));
        return result;
    }

    @Override
    public LoginRequest cloneObject() {
        LoginRequest clone = new LoginRequest();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The identifier of the user.
     *
     * @return the UserId.
     */
    public UserId getUsername() {
        return this.username;
    }

    /**
     * The identifier of the user.
     *
     * @param username the UserId.
     */
    public void setUsername(UserId username) {
        this.username = username;
    }

    /**
     * The identifier of the user.
     *
     * @param username the String.
     */
    public void setUsername(String username) {
        if ((this.username == null)) {
            if ((username == null)) {
                return;
            }
            this.username = new UserId();
        }
        this.username.setValue(username);
    }

    /**
     * The password of the user.
     *
     * @return the Password.
     */
    public Password getPassword() {
        return this.password;
    }

    /**
     * The password of the user.
     *
     * @param password the Password.
     */
    public void setPassword(Password password) {
        this.password = password;
    }

    /**
     * The password of the user.
     *
     * @param password the String.
     */
    public void setPassword(String password) {
        if ((this.password == null)) {
            if ((password == null)) {
                return;
            }
            this.password = new Password();
        }
        this.password.setValue(password);
    }

    /**
     * The login tenant.
     *
     * @return the Tenant.
     */
    public Tenant getTenant() {
        return this.tenant;
    }

    /**
     * The login tenant.
     *
     * @param tenant the Tenant.
     */
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    /**
     * The login tenant.
     *
     * @param tenant the String.
     */
    public void setTenant(String tenant) {
        if ((this.tenant == null)) {
            if ((tenant == null)) {
                return;
            }
            this.tenant = new Tenant();
        }
        this.tenant.setValue(tenant);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(LoginRequest.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(LoginRequest.class).getAllProperties();
    }
}
