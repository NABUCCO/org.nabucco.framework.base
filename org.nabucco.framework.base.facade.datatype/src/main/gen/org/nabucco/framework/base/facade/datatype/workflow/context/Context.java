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
package org.nabucco.framework.base.facade.datatype.workflow.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * Context<p/>The context of a workflow interaction.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-03
 */
public abstract class Context extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "l0,255;u0,n;m0,1;" };

    public static final String DATATYPE = "datatype";

    public static final String PROPERTYNAME = "propertyName";

    /** The datatype in focus. */
    private NabuccoDatatype datatype;

    /** Name of the property in focus. */
    private Name propertyName;

    /** Constructs a new Context instance. */
    public Context() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Context.
     */
    protected void cloneObject(Context clone) {
        super.cloneObject(clone);
        if ((this.getDatatype() != null)) {
            clone.setDatatype(this.getDatatype().cloneObject());
        }
        if ((this.getPropertyName() != null)) {
            clone.setPropertyName(this.getPropertyName().cloneObject());
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
        propertyMap.put(DATATYPE, PropertyDescriptorSupport.createDatatype(DATATYPE, NabuccoDatatype.class, 3,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PROPERTYNAME,
                PropertyDescriptorSupport.createBasetype(PROPERTYNAME, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Context.getPropertyDescriptor(DATATYPE), this.getDatatype(), null));
        properties.add(super.createProperty(Context.getPropertyDescriptor(PROPERTYNAME), this.propertyName, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DATATYPE) && (property.getType() == NabuccoDatatype.class))) {
            this.setDatatype(((NabuccoDatatype) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROPERTYNAME) && (property.getType() == Name.class))) {
            this.setPropertyName(((Name) property.getInstance()));
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
        final Context other = ((Context) obj);
        if ((this.datatype == null)) {
            if ((other.datatype != null))
                return false;
        } else if ((!this.datatype.equals(other.datatype)))
            return false;
        if ((this.propertyName == null)) {
            if ((other.propertyName != null))
                return false;
        } else if ((!this.propertyName.equals(other.propertyName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.datatype == null) ? 0 : this.datatype.hashCode()));
        result = ((PRIME * result) + ((this.propertyName == null) ? 0 : this.propertyName.hashCode()));
        return result;
    }

    @Override
    public abstract Context cloneObject();

    /**
     * The datatype in focus.
     *
     * @param datatype the NabuccoDatatype.
     */
    public void setDatatype(NabuccoDatatype datatype) {
        this.datatype = datatype;
    }

    /**
     * The datatype in focus.
     *
     * @return the NabuccoDatatype.
     */
    public NabuccoDatatype getDatatype() {
        return this.datatype;
    }

    /**
     * Name of the property in focus.
     *
     * @return the Name.
     */
    public Name getPropertyName() {
        return this.propertyName;
    }

    /**
     * Name of the property in focus.
     *
     * @param propertyName the Name.
     */
    public void setPropertyName(Name propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Name of the property in focus.
     *
     * @param propertyName the String.
     */
    public void setPropertyName(String propertyName) {
        if ((this.propertyName == null)) {
            if ((propertyName == null)) {
                return;
            }
            this.propertyName = new Name();
        }
        this.propertyName.setValue(propertyName);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Context.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Context.class).getAllProperties();
    }
}
