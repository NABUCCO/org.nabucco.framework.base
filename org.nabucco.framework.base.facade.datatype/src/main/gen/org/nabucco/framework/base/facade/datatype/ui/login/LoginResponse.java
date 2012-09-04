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
package org.nabucco.framework.base.facade.datatype.ui.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.ui.UiErrorMessage;

/**
 * LoginResponse<p/>A field in the NABUCCO User Interface.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-05
 */
public class LoginResponse extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,255;u0,n;m0,1;" };

    public static final String SUCCESS = "success";

    public static final String ERRORMESSAGE = "errorMessage";

    /** Whether the login was successful or not. */
    private Flag success;

    /** The optional error message. */
    private UiErrorMessage errorMessage;

    /** Constructs a new LoginResponse instance. */
    public LoginResponse() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the LoginResponse.
     */
    protected void cloneObject(LoginResponse clone) {
        super.cloneObject(clone);
        if ((this.getSuccess() != null)) {
            clone.setSuccess(this.getSuccess().cloneObject());
        }
        if ((this.getErrorMessage() != null)) {
            clone.setErrorMessage(this.getErrorMessage().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(SUCCESS,
                PropertyDescriptorSupport.createBasetype(SUCCESS, Flag.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(ERRORMESSAGE, PropertyDescriptorSupport.createBasetype(ERRORMESSAGE, UiErrorMessage.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(LoginResponse.getPropertyDescriptor(SUCCESS), this.success, null));
        properties
                .add(super.createProperty(LoginResponse.getPropertyDescriptor(ERRORMESSAGE), this.errorMessage, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SUCCESS) && (property.getType() == Flag.class))) {
            this.setSuccess(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ERRORMESSAGE) && (property.getType() == UiErrorMessage.class))) {
            this.setErrorMessage(((UiErrorMessage) property.getInstance()));
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
        final LoginResponse other = ((LoginResponse) obj);
        if ((this.success == null)) {
            if ((other.success != null))
                return false;
        } else if ((!this.success.equals(other.success)))
            return false;
        if ((this.errorMessage == null)) {
            if ((other.errorMessage != null))
                return false;
        } else if ((!this.errorMessage.equals(other.errorMessage)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.success == null) ? 0 : this.success.hashCode()));
        result = ((PRIME * result) + ((this.errorMessage == null) ? 0 : this.errorMessage.hashCode()));
        return result;
    }

    @Override
    public LoginResponse cloneObject() {
        LoginResponse clone = new LoginResponse();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Whether the login was successful or not.
     *
     * @return the Flag.
     */
    public Flag getSuccess() {
        return this.success;
    }

    /**
     * Whether the login was successful or not.
     *
     * @param success the Flag.
     */
    public void setSuccess(Flag success) {
        this.success = success;
    }

    /**
     * Whether the login was successful or not.
     *
     * @param success the Boolean.
     */
    public void setSuccess(Boolean success) {
        if ((this.success == null)) {
            if ((success == null)) {
                return;
            }
            this.success = new Flag();
        }
        this.success.setValue(success);
    }

    /**
     * The optional error message.
     *
     * @return the UiErrorMessage.
     */
    public UiErrorMessage getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * The optional error message.
     *
     * @param errorMessage the UiErrorMessage.
     */
    public void setErrorMessage(UiErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * The optional error message.
     *
     * @param errorMessage the String.
     */
    public void setErrorMessage(String errorMessage) {
        if ((this.errorMessage == null)) {
            if ((errorMessage == null)) {
                return;
            }
            this.errorMessage = new UiErrorMessage();
        }
        this.errorMessage.setValue(errorMessage);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(LoginResponse.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(LoginResponse.class).getAllProperties();
    }
}
