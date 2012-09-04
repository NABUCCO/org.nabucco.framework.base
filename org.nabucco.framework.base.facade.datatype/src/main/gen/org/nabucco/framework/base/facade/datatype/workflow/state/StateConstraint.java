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
package org.nabucco.framework.base.facade.datatype.workflow.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Path;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * StateConstraint<p/>Constraints for a datatype (or datatype property) in the given state.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-29
 */
public class StateConstraint extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m0,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String PROPERTYPATH = "propertyPath";

    public static final String EDITABLE = "editable";

    public static final String VISIBLE = "visible";

    /** Path to a property (separated by dots '.') to add the constraints to. */
    private Path propertyPath;

    /** Whether the datatype/property should be editable or not. */
    private Flag editable;

    /** Whether the datatype/property should be visible or not. */
    private Flag visible;

    /** Constructs a new StateConstraint instance. */
    public StateConstraint() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the StateConstraint.
     */
    protected void cloneObject(StateConstraint clone) {
        super.cloneObject(clone);
        if ((this.getPropertyPath() != null)) {
            clone.setPropertyPath(this.getPropertyPath().cloneObject());
        }
        if ((this.getEditable() != null)) {
            clone.setEditable(this.getEditable().cloneObject());
        }
        if ((this.getVisible() != null)) {
            clone.setVisible(this.getVisible().cloneObject());
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
        propertyMap.put(PROPERTYPATH,
                PropertyDescriptorSupport.createBasetype(PROPERTYPATH, Path.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(EDITABLE,
                PropertyDescriptorSupport.createBasetype(EDITABLE, Flag.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(VISIBLE,
                PropertyDescriptorSupport.createBasetype(VISIBLE, Flag.class, 5, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(StateConstraint.getPropertyDescriptor(PROPERTYPATH), this.propertyPath,
                null));
        properties.add(super.createProperty(StateConstraint.getPropertyDescriptor(EDITABLE), this.editable, null));
        properties.add(super.createProperty(StateConstraint.getPropertyDescriptor(VISIBLE), this.visible, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PROPERTYPATH) && (property.getType() == Path.class))) {
            this.setPropertyPath(((Path) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EDITABLE) && (property.getType() == Flag.class))) {
            this.setEditable(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VISIBLE) && (property.getType() == Flag.class))) {
            this.setVisible(((Flag) property.getInstance()));
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
        final StateConstraint other = ((StateConstraint) obj);
        if ((this.propertyPath == null)) {
            if ((other.propertyPath != null))
                return false;
        } else if ((!this.propertyPath.equals(other.propertyPath)))
            return false;
        if ((this.editable == null)) {
            if ((other.editable != null))
                return false;
        } else if ((!this.editable.equals(other.editable)))
            return false;
        if ((this.visible == null)) {
            if ((other.visible != null))
                return false;
        } else if ((!this.visible.equals(other.visible)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.propertyPath == null) ? 0 : this.propertyPath.hashCode()));
        result = ((PRIME * result) + ((this.editable == null) ? 0 : this.editable.hashCode()));
        result = ((PRIME * result) + ((this.visible == null) ? 0 : this.visible.hashCode()));
        return result;
    }

    @Override
    public StateConstraint cloneObject() {
        StateConstraint clone = new StateConstraint();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Path to a property (separated by dots '.') to add the constraints to.
     *
     * @return the Path.
     */
    public Path getPropertyPath() {
        return this.propertyPath;
    }

    /**
     * Path to a property (separated by dots '.') to add the constraints to.
     *
     * @param propertyPath the Path.
     */
    public void setPropertyPath(Path propertyPath) {
        this.propertyPath = propertyPath;
    }

    /**
     * Path to a property (separated by dots '.') to add the constraints to.
     *
     * @param propertyPath the String.
     */
    public void setPropertyPath(String propertyPath) {
        if ((this.propertyPath == null)) {
            if ((propertyPath == null)) {
                return;
            }
            this.propertyPath = new Path();
        }
        this.propertyPath.setValue(propertyPath);
    }

    /**
     * Whether the datatype/property should be editable or not.
     *
     * @return the Flag.
     */
    public Flag getEditable() {
        return this.editable;
    }

    /**
     * Whether the datatype/property should be editable or not.
     *
     * @param editable the Flag.
     */
    public void setEditable(Flag editable) {
        this.editable = editable;
    }

    /**
     * Whether the datatype/property should be editable or not.
     *
     * @param editable the Boolean.
     */
    public void setEditable(Boolean editable) {
        if ((this.editable == null)) {
            if ((editable == null)) {
                return;
            }
            this.editable = new Flag();
        }
        this.editable.setValue(editable);
    }

    /**
     * Whether the datatype/property should be visible or not.
     *
     * @return the Flag.
     */
    public Flag getVisible() {
        return this.visible;
    }

    /**
     * Whether the datatype/property should be visible or not.
     *
     * @param visible the Flag.
     */
    public void setVisible(Flag visible) {
        this.visible = visible;
    }

    /**
     * Whether the datatype/property should be visible or not.
     *
     * @param visible the Boolean.
     */
    public void setVisible(Boolean visible) {
        if ((this.visible == null)) {
            if ((visible == null)) {
                return;
            }
            this.visible = new Flag();
        }
        this.visible.setValue(visible);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(StateConstraint.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(StateConstraint.class).getAllProperties();
    }
}
