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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkItemActionExtension<p/>NABUCCO The special action used for working items<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-09-27
 */
public class WorkItemActionExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String ACTIONTYPE = "actionType";

    public static final String ACTIONID = "actionId";

    /** The type of the action (SAVE,DELETE etc.) */
    private EnumerationProperty actionType;

    /** the actionId to use */
    private StringProperty actionId;

    /** Constructs a new WorkItemActionExtension instance. */
    public WorkItemActionExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkItemActionExtension.
     */
    protected void cloneObject(WorkItemActionExtension clone) {
        super.cloneObject(clone);
        if ((this.getActionType() != null)) {
            clone.setActionType(this.getActionType().cloneObject());
        }
        if ((this.getActionId() != null)) {
            clone.setActionId(this.getActionId().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(ACTIONTYPE, PropertyDescriptorSupport.createDatatype(ACTIONTYPE, EnumerationProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ACTIONID, PropertyDescriptorSupport.createDatatype(ACTIONID, StringProperty.class, 5,
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
        properties.add(super.createProperty(WorkItemActionExtension.getPropertyDescriptor(ACTIONTYPE),
                this.getActionType(), null));
        properties.add(super.createProperty(WorkItemActionExtension.getPropertyDescriptor(ACTIONID),
                this.getActionId(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ACTIONTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setActionType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ACTIONID) && (property.getType() == StringProperty.class))) {
            this.setActionId(((StringProperty) property.getInstance()));
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
        final WorkItemActionExtension other = ((WorkItemActionExtension) obj);
        if ((this.actionType == null)) {
            if ((other.actionType != null))
                return false;
        } else if ((!this.actionType.equals(other.actionType)))
            return false;
        if ((this.actionId == null)) {
            if ((other.actionId != null))
                return false;
        } else if ((!this.actionId.equals(other.actionId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.actionType == null) ? 0 : this.actionType.hashCode()));
        result = ((PRIME * result) + ((this.actionId == null) ? 0 : this.actionId.hashCode()));
        return result;
    }

    @Override
    public WorkItemActionExtension cloneObject() {
        WorkItemActionExtension clone = new WorkItemActionExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The type of the action (SAVE,DELETE etc.)
     *
     * @param actionType the EnumerationProperty.
     */
    public void setActionType(EnumerationProperty actionType) {
        this.actionType = actionType;
    }

    /**
     * The type of the action (SAVE,DELETE etc.)
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getActionType() {
        return this.actionType;
    }

    /**
     * the actionId to use
     *
     * @param actionId the StringProperty.
     */
    public void setActionId(StringProperty actionId) {
        this.actionId = actionId;
    }

    /**
     * the actionId to use
     *
     * @return the StringProperty.
     */
    public StringProperty getActionId() {
        return this.actionId;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkItemActionExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkItemActionExtension.class).getAllProperties();
    }
}
