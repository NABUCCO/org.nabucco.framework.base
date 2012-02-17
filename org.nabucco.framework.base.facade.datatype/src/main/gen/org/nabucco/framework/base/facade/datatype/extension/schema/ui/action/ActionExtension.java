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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ActionExtension<p/>NABUCCO User Interface Action extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-20
 */
public class ActionExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String ACTIONID = "actionId";

    public static final String ICON = "icon";

    public static final String ACTIONCLASS = "actionClass";

    /** The Action ID. */
    private StringProperty actionId;

    /** The Action ID. */
    private StringProperty icon;

    /** The Action Implementation. */
    private ClassProperty actionClass;

    /** Constructs a new ActionExtension instance. */
    public ActionExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ActionExtension.
     */
    protected void cloneObject(ActionExtension clone) {
        super.cloneObject(clone);
        if ((this.getActionId() != null)) {
            clone.setActionId(this.getActionId().cloneObject());
        }
        if ((this.getIcon() != null)) {
            clone.setIcon(this.getIcon().cloneObject());
        }
        if ((this.getActionClass() != null)) {
            clone.setActionClass(this.getActionClass().cloneObject());
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
        propertyMap.put(ACTIONID, PropertyDescriptorSupport.createDatatype(ACTIONID, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ICON, PropertyDescriptorSupport.createDatatype(ICON, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ACTIONCLASS, PropertyDescriptorSupport.createDatatype(ACTIONCLASS, ClassProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ActionExtension.getPropertyDescriptor(ACTIONID), this.getActionId(), null));
        properties.add(super.createProperty(ActionExtension.getPropertyDescriptor(ICON), this.getIcon(), null));
        properties.add(super.createProperty(ActionExtension.getPropertyDescriptor(ACTIONCLASS), this.getActionClass(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ACTIONID) && (property.getType() == StringProperty.class))) {
            this.setActionId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ICON) && (property.getType() == StringProperty.class))) {
            this.setIcon(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ACTIONCLASS) && (property.getType() == ClassProperty.class))) {
            this.setActionClass(((ClassProperty) property.getInstance()));
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
        final ActionExtension other = ((ActionExtension) obj);
        if ((this.actionId == null)) {
            if ((other.actionId != null))
                return false;
        } else if ((!this.actionId.equals(other.actionId)))
            return false;
        if ((this.icon == null)) {
            if ((other.icon != null))
                return false;
        } else if ((!this.icon.equals(other.icon)))
            return false;
        if ((this.actionClass == null)) {
            if ((other.actionClass != null))
                return false;
        } else if ((!this.actionClass.equals(other.actionClass)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.actionId == null) ? 0 : this.actionId.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        result = ((PRIME * result) + ((this.actionClass == null) ? 0 : this.actionClass.hashCode()));
        return result;
    }

    @Override
    public ActionExtension cloneObject() {
        ActionExtension clone = new ActionExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Action ID.
     *
     * @param actionId the StringProperty.
     */
    public void setActionId(StringProperty actionId) {
        this.actionId = actionId;
    }

    /**
     * The Action ID.
     *
     * @return the StringProperty.
     */
    public StringProperty getActionId() {
        return this.actionId;
    }

    /**
     * The Action ID.
     *
     * @param icon the StringProperty.
     */
    public void setIcon(StringProperty icon) {
        this.icon = icon;
    }

    /**
     * The Action ID.
     *
     * @return the StringProperty.
     */
    public StringProperty getIcon() {
        return this.icon;
    }

    /**
     * The Action Implementation.
     *
     * @param actionClass the ClassProperty.
     */
    public void setActionClass(ClassProperty actionClass) {
        this.actionClass = actionClass;
    }

    /**
     * The Action Implementation.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getActionClass() {
        return this.actionClass;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ActionExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ActionExtension.class).getAllProperties();
    }
}
