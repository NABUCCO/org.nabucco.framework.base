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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * EditorButtonExtension<p/>NABUCCO Editor button extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-02
 */
public class EditorButtonExtension extends ButtonExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String DIRTYSTATENEEDED = "dirtyStateNeeded";

    /** The model must be in dirty state to enable the button */
    private BooleanProperty dirtyStateNeeded;

    /** Constructs a new EditorButtonExtension instance. */
    public EditorButtonExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the EditorButtonExtension.
     */
    protected void cloneObject(EditorButtonExtension clone) {
        super.cloneObject(clone);
        if ((this.getDirtyStateNeeded() != null)) {
            clone.setDirtyStateNeeded(this.getDirtyStateNeeded().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ButtonExtension.class).getPropertyMap());
        propertyMap.put(DIRTYSTATENEEDED, PropertyDescriptorSupport.createDatatype(DIRTYSTATENEEDED,
                BooleanProperty.class, 9, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(EditorButtonExtension.getPropertyDescriptor(DIRTYSTATENEEDED),
                this.getDirtyStateNeeded(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DIRTYSTATENEEDED) && (property.getType() == BooleanProperty.class))) {
            this.setDirtyStateNeeded(((BooleanProperty) property.getInstance()));
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
        final EditorButtonExtension other = ((EditorButtonExtension) obj);
        if ((this.dirtyStateNeeded == null)) {
            if ((other.dirtyStateNeeded != null))
                return false;
        } else if ((!this.dirtyStateNeeded.equals(other.dirtyStateNeeded)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.dirtyStateNeeded == null) ? 0 : this.dirtyStateNeeded.hashCode()));
        return result;
    }

    @Override
    public EditorButtonExtension cloneObject() {
        EditorButtonExtension clone = new EditorButtonExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The model must be in dirty state to enable the button
     *
     * @param dirtyStateNeeded the BooleanProperty.
     */
    public void setDirtyStateNeeded(BooleanProperty dirtyStateNeeded) {
        this.dirtyStateNeeded = dirtyStateNeeded;
    }

    /**
     * The model must be in dirty state to enable the button
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getDirtyStateNeeded() {
        return this.dirtyStateNeeded;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(EditorButtonExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(EditorButtonExtension.class).getAllProperties();
    }
}
