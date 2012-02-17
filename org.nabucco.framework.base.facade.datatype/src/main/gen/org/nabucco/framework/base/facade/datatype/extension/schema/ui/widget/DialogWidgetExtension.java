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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DialogWidgetExtension<p/>NABUCCO Dialog widget that opens a configured dialog on click<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-15-09
 */
public class DialogWidgetExtension extends WidgetExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String TEXT = "text";

    public static final String DIALOGID = "dialogId";

    /** The Widget text. */
    private StringProperty text;

    /** The id of the dialog to open */
    private StringProperty dialogId;

    /** Constructs a new DialogWidgetExtension instance. */
    public DialogWidgetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DialogWidgetExtension.
     */
    protected void cloneObject(DialogWidgetExtension clone) {
        super.cloneObject(clone);
        if ((this.getText() != null)) {
            clone.setText(this.getText().cloneObject());
        }
        if ((this.getDialogId() != null)) {
            clone.setDialogId(this.getDialogId().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WidgetExtension.class).getPropertyMap());
        propertyMap.put(TEXT, PropertyDescriptorSupport.createDatatype(TEXT, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DIALOGID, PropertyDescriptorSupport.createDatatype(DIALOGID, StringProperty.class, 7,
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
        properties.add(super.createProperty(DialogWidgetExtension.getPropertyDescriptor(TEXT), this.getText(), null));
        properties.add(super.createProperty(DialogWidgetExtension.getPropertyDescriptor(DIALOGID), this.getDialogId(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TEXT) && (property.getType() == StringProperty.class))) {
            this.setText(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DIALOGID) && (property.getType() == StringProperty.class))) {
            this.setDialogId(((StringProperty) property.getInstance()));
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
        final DialogWidgetExtension other = ((DialogWidgetExtension) obj);
        if ((this.text == null)) {
            if ((other.text != null))
                return false;
        } else if ((!this.text.equals(other.text)))
            return false;
        if ((this.dialogId == null)) {
            if ((other.dialogId != null))
                return false;
        } else if ((!this.dialogId.equals(other.dialogId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.text == null) ? 0 : this.text.hashCode()));
        result = ((PRIME * result) + ((this.dialogId == null) ? 0 : this.dialogId.hashCode()));
        return result;
    }

    @Override
    public DialogWidgetExtension cloneObject() {
        DialogWidgetExtension clone = new DialogWidgetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Widget text.
     *
     * @param text the StringProperty.
     */
    public void setText(StringProperty text) {
        this.text = text;
    }

    /**
     * The Widget text.
     *
     * @return the StringProperty.
     */
    public StringProperty getText() {
        return this.text;
    }

    /**
     * The id of the dialog to open
     *
     * @param dialogId the StringProperty.
     */
    public void setDialogId(StringProperty dialogId) {
        this.dialogId = dialogId;
    }

    /**
     * The id of the dialog to open
     *
     * @return the StringProperty.
     */
    public StringProperty getDialogId() {
        return this.dialogId;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DialogWidgetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DialogWidgetExtension.class).getAllProperties();
    }
}
