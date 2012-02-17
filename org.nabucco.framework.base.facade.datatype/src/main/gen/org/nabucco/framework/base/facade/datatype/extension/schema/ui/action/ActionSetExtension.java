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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.action.ActionExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ActionSetExtension<p/>NABUCCO User Interface ActionSet extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-20
 */
public class ActionSetExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String ACTIONSETID = "actionSetId";

    public static final String ACTIONS = "actions";

    /** The Action Set ID. */
    private StringProperty actionSetId;

    /** The Action Implementation. */
    private NabuccoList<ActionExtension> actions;

    /** Constructs a new ActionSetExtension instance. */
    public ActionSetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ActionSetExtension.
     */
    protected void cloneObject(ActionSetExtension clone) {
        super.cloneObject(clone);
        if ((this.getActionSetId() != null)) {
            clone.setActionSetId(this.getActionSetId().cloneObject());
        }
        if ((this.actions != null)) {
            clone.actions = this.actions.cloneCollection();
        }
    }

    /**
     * Getter for the ActionsJPA.
     *
     * @return the List<ActionExtension>.
     */
    List<ActionExtension> getActionsJPA() {
        if ((this.actions == null)) {
            this.actions = new NabuccoListImpl<ActionExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ActionExtension>) this.actions).getDelegate();
    }

    /**
     * Setter for the ActionsJPA.
     *
     * @param actions the List<ActionExtension>.
     */
    void setActionsJPA(List<ActionExtension> actions) {
        if ((this.actions == null)) {
            this.actions = new NabuccoListImpl<ActionExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ActionExtension>) this.actions).setDelegate(actions);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(ACTIONSETID, PropertyDescriptorSupport.createDatatype(ACTIONSETID, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ACTIONS, PropertyDescriptorSupport.createCollection(ACTIONS, ActionExtension.class, 5,
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
        properties.add(super.createProperty(ActionSetExtension.getPropertyDescriptor(ACTIONSETID),
                this.getActionSetId(), null));
        properties.add(super.createProperty(ActionSetExtension.getPropertyDescriptor(ACTIONS), this.actions, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ACTIONSETID) && (property.getType() == StringProperty.class))) {
            this.setActionSetId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ACTIONS) && (property.getType() == ActionExtension.class))) {
            this.actions = ((NabuccoList<ActionExtension>) property.getInstance());
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
        final ActionSetExtension other = ((ActionSetExtension) obj);
        if ((this.actionSetId == null)) {
            if ((other.actionSetId != null))
                return false;
        } else if ((!this.actionSetId.equals(other.actionSetId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.actionSetId == null) ? 0 : this.actionSetId.hashCode()));
        return result;
    }

    @Override
    public ActionSetExtension cloneObject() {
        ActionSetExtension clone = new ActionSetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Action Set ID.
     *
     * @param actionSetId the StringProperty.
     */
    public void setActionSetId(StringProperty actionSetId) {
        this.actionSetId = actionSetId;
    }

    /**
     * The Action Set ID.
     *
     * @return the StringProperty.
     */
    public StringProperty getActionSetId() {
        return this.actionSetId;
    }

    /**
     * The Action Implementation.
     *
     * @return the NabuccoList<ActionExtension>.
     */
    public NabuccoList<ActionExtension> getActions() {
        if ((this.actions == null)) {
            this.actions = new NabuccoListImpl<ActionExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.actions;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ActionSetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ActionSetExtension.class).getAllProperties();
    }
}
