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
package org.nabucco.framework.base.facade.datatype.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MasterDetailContext<p/>For transporting additional Data.<p/>
 *
 * @version 1
 * @author Silas Schwarz, PRODYNA AG, 2011-05-11
 */
public class MasterDetailContext extends ServiceSubContext implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final ServiceSubContextType CONTEXTTYPE_DEFAULT = ServiceSubContextType.MASTER_DETAIL;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m0,1;", "l0,n;u0,n;m0,1;" };

    public static final String MASTERTYPENAME = "masterTypeName";

    public static final String MASTERID = "masterId";

    /** The Master Element-type */
    private Name masterTypeName;

    /** The Master Element identifier */
    private Identifier masterId;

    /** Constructs a new MasterDetailContext instance. */
    public MasterDetailContext() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        contextType = CONTEXTTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the MasterDetailContext.
     */
    protected void cloneObject(MasterDetailContext clone) {
        super.cloneObject(clone);
        clone.setContextType(this.getContextType());
        if ((this.getMasterTypeName() != null)) {
            clone.setMasterTypeName(this.getMasterTypeName().cloneObject());
        }
        if ((this.getMasterId() != null)) {
            clone.setMasterId(this.getMasterId().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ServiceSubContext.class).getPropertyMap());
        propertyMap
                .put(MASTERTYPENAME, PropertyDescriptorSupport.createBasetype(MASTERTYPENAME, Name.class, 4,
                        PROPERTY_CONSTRAINTS[0], false));
        propertyMap
                .put(MASTERID, PropertyDescriptorSupport.createBasetype(MASTERID, Identifier.class, 5,
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
        properties.add(super.createProperty(MasterDetailContext.getPropertyDescriptor(MASTERTYPENAME),
                this.masterTypeName, null));
        properties.add(super.createProperty(MasterDetailContext.getPropertyDescriptor(MASTERID), this.masterId, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MASTERTYPENAME) && (property.getType() == Name.class))) {
            this.setMasterTypeName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MASTERID) && (property.getType() == Identifier.class))) {
            this.setMasterId(((Identifier) property.getInstance()));
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
        final MasterDetailContext other = ((MasterDetailContext) obj);
        if ((this.masterTypeName == null)) {
            if ((other.masterTypeName != null))
                return false;
        } else if ((!this.masterTypeName.equals(other.masterTypeName)))
            return false;
        if ((this.masterId == null)) {
            if ((other.masterId != null))
                return false;
        } else if ((!this.masterId.equals(other.masterId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.masterTypeName == null) ? 0 : this.masterTypeName.hashCode()));
        result = ((PRIME * result) + ((this.masterId == null) ? 0 : this.masterId.hashCode()));
        return result;
    }

    @Override
    public MasterDetailContext cloneObject() {
        MasterDetailContext clone = new MasterDetailContext();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Master Element-type
     *
     * @return the Name.
     */
    public Name getMasterTypeName() {
        return this.masterTypeName;
    }

    /**
     * The Master Element-type
     *
     * @param masterTypeName the Name.
     */
    public void setMasterTypeName(Name masterTypeName) {
        this.masterTypeName = masterTypeName;
    }

    /**
     * The Master Element-type
     *
     * @param masterTypeName the String.
     */
    public void setMasterTypeName(String masterTypeName) {
        if ((this.masterTypeName == null)) {
            if ((masterTypeName == null)) {
                return;
            }
            this.masterTypeName = new Name();
        }
        this.masterTypeName.setValue(masterTypeName);
    }

    /**
     * The Master Element identifier
     *
     * @return the Identifier.
     */
    public Identifier getMasterId() {
        return this.masterId;
    }

    /**
     * The Master Element identifier
     *
     * @param masterId the Identifier.
     */
    public void setMasterId(Identifier masterId) {
        this.masterId = masterId;
    }

    /**
     * The Master Element identifier
     *
     * @param masterId the Long.
     */
    public void setMasterId(Long masterId) {
        if ((this.masterId == null)) {
            if ((masterId == null)) {
                return;
            }
            this.masterId = new Identifier();
        }
        this.masterId.setValue(masterId);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(MasterDetailContext.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(MasterDetailContext.class).getAllProperties();
    }
}
