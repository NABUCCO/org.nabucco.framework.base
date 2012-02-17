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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog;

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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.DialogButtonExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DialogExtension<p/>NABUCCO User Interface Dialog extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-08-05
 */
public class DialogExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m0,n;", "m1,1;" };

    public static final String TITLE = "title";

    public static final String MESSAGE = "message";

    public static final String ICON = "icon";

    public static final String BUTTONS = "buttons";

    public static final String TYPE = "type";

    /** The Dialog title. */
    private StringProperty title;

    /** The Dialog message. */
    private StringProperty message;

    /** The icon of the dialog */
    private StringProperty icon;

    /** The Action References. */
    private NabuccoList<DialogButtonExtension> buttons;

    /** The type of the dialog */
    private EnumerationProperty type;

    /** Constructs a new DialogExtension instance. */
    public DialogExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DialogExtension.
     */
    protected void cloneObject(DialogExtension clone) {
        super.cloneObject(clone);
        if ((this.getTitle() != null)) {
            clone.setTitle(this.getTitle().cloneObject());
        }
        if ((this.getMessage() != null)) {
            clone.setMessage(this.getMessage().cloneObject());
        }
        if ((this.getIcon() != null)) {
            clone.setIcon(this.getIcon().cloneObject());
        }
        if ((this.buttons != null)) {
            clone.buttons = this.buttons.cloneCollection();
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
    }

    /**
     * Getter for the ButtonsJPA.
     *
     * @return the List<DialogButtonExtension>.
     */
    List<DialogButtonExtension> getButtonsJPA() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<DialogButtonExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<DialogButtonExtension>) this.buttons).getDelegate();
    }

    /**
     * Setter for the ButtonsJPA.
     *
     * @param buttons the List<DialogButtonExtension>.
     */
    void setButtonsJPA(List<DialogButtonExtension> buttons) {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<DialogButtonExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<DialogButtonExtension>) this.buttons).setDelegate(buttons);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(TITLE, PropertyDescriptorSupport.createDatatype(TITLE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MESSAGE, PropertyDescriptorSupport.createDatatype(MESSAGE, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ICON, PropertyDescriptorSupport.createDatatype(ICON, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(BUTTONS, PropertyDescriptorSupport.createCollection(BUTTONS, DialogButtonExtension.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, EnumerationProperty.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DialogExtension.getPropertyDescriptor(TITLE), this.getTitle(), null));
        properties.add(super.createProperty(DialogExtension.getPropertyDescriptor(MESSAGE), this.getMessage(), null));
        properties.add(super.createProperty(DialogExtension.getPropertyDescriptor(ICON), this.getIcon(), null));
        properties.add(super.createProperty(DialogExtension.getPropertyDescriptor(BUTTONS), this.buttons, null));
        properties.add(super.createProperty(DialogExtension.getPropertyDescriptor(TYPE), this.getType(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TITLE) && (property.getType() == StringProperty.class))) {
            this.setTitle(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MESSAGE) && (property.getType() == StringProperty.class))) {
            this.setMessage(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ICON) && (property.getType() == StringProperty.class))) {
            this.setIcon(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BUTTONS) && (property.getType() == DialogButtonExtension.class))) {
            this.buttons = ((NabuccoList<DialogButtonExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setType(((EnumerationProperty) property.getInstance()));
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
        final DialogExtension other = ((DialogExtension) obj);
        if ((this.title == null)) {
            if ((other.title != null))
                return false;
        } else if ((!this.title.equals(other.title)))
            return false;
        if ((this.message == null)) {
            if ((other.message != null))
                return false;
        } else if ((!this.message.equals(other.message)))
            return false;
        if ((this.icon == null)) {
            if ((other.icon != null))
                return false;
        } else if ((!this.icon.equals(other.icon)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.title == null) ? 0 : this.title.hashCode()));
        result = ((PRIME * result) + ((this.message == null) ? 0 : this.message.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        return result;
    }

    @Override
    public DialogExtension cloneObject() {
        DialogExtension clone = new DialogExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Dialog title.
     *
     * @param title the StringProperty.
     */
    public void setTitle(StringProperty title) {
        this.title = title;
    }

    /**
     * The Dialog title.
     *
     * @return the StringProperty.
     */
    public StringProperty getTitle() {
        return this.title;
    }

    /**
     * The Dialog message.
     *
     * @param message the StringProperty.
     */
    public void setMessage(StringProperty message) {
        this.message = message;
    }

    /**
     * The Dialog message.
     *
     * @return the StringProperty.
     */
    public StringProperty getMessage() {
        return this.message;
    }

    /**
     * The icon of the dialog
     *
     * @param icon the StringProperty.
     */
    public void setIcon(StringProperty icon) {
        this.icon = icon;
    }

    /**
     * The icon of the dialog
     *
     * @return the StringProperty.
     */
    public StringProperty getIcon() {
        return this.icon;
    }

    /**
     * The Action References.
     *
     * @return the NabuccoList<DialogButtonExtension>.
     */
    public NabuccoList<DialogButtonExtension> getButtons() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<DialogButtonExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.buttons;
    }

    /**
     * The type of the dialog
     *
     * @param type the EnumerationProperty.
     */
    public void setType(EnumerationProperty type) {
        this.type = type;
    }

    /**
     * The type of the dialog
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getType() {
        return this.type;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DialogExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DialogExtension.class).getAllProperties();
    }
}
