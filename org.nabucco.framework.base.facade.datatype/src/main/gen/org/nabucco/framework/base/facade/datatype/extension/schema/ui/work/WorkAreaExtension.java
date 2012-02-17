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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkAreaExtension<p/>NABUCCO User Interface Work Area extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class WorkAreaExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String LAYOUT = "layout";

    public static final String CLOSEDIALOG = "closeDialog";

    /** The Work Area layout. */
    private StringProperty layout;

    /** The close dialog id */
    private StringProperty closeDialog;

    /** Constructs a new WorkAreaExtension instance. */
    public WorkAreaExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkAreaExtension.
     */
    protected void cloneObject(WorkAreaExtension clone) {
        super.cloneObject(clone);
        if ((this.getLayout() != null)) {
            clone.setLayout(this.getLayout().cloneObject());
        }
        if ((this.getCloseDialog() != null)) {
            clone.setCloseDialog(this.getCloseDialog().cloneObject());
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
        propertyMap.put(LAYOUT, PropertyDescriptorSupport.createDatatype(LAYOUT, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CLOSEDIALOG, PropertyDescriptorSupport.createDatatype(CLOSEDIALOG, StringProperty.class, 5,
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
        properties.add(super.createProperty(WorkAreaExtension.getPropertyDescriptor(LAYOUT), this.getLayout(), null));
        properties.add(super.createProperty(WorkAreaExtension.getPropertyDescriptor(CLOSEDIALOG),
                this.getCloseDialog(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LAYOUT) && (property.getType() == StringProperty.class))) {
            this.setLayout(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CLOSEDIALOG) && (property.getType() == StringProperty.class))) {
            this.setCloseDialog(((StringProperty) property.getInstance()));
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
        final WorkAreaExtension other = ((WorkAreaExtension) obj);
        if ((this.layout == null)) {
            if ((other.layout != null))
                return false;
        } else if ((!this.layout.equals(other.layout)))
            return false;
        if ((this.closeDialog == null)) {
            if ((other.closeDialog != null))
                return false;
        } else if ((!this.closeDialog.equals(other.closeDialog)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.layout == null) ? 0 : this.layout.hashCode()));
        result = ((PRIME * result) + ((this.closeDialog == null) ? 0 : this.closeDialog.hashCode()));
        return result;
    }

    @Override
    public WorkAreaExtension cloneObject() {
        WorkAreaExtension clone = new WorkAreaExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Work Area layout.
     *
     * @param layout the StringProperty.
     */
    public void setLayout(StringProperty layout) {
        this.layout = layout;
    }

    /**
     * The Work Area layout.
     *
     * @return the StringProperty.
     */
    public StringProperty getLayout() {
        return this.layout;
    }

    /**
     * The close dialog id
     *
     * @param closeDialog the StringProperty.
     */
    public void setCloseDialog(StringProperty closeDialog) {
        this.closeDialog = closeDialog;
    }

    /**
     * The close dialog id
     *
     * @return the StringProperty.
     */
    public StringProperty getCloseDialog() {
        return this.closeDialog;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkAreaExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkAreaExtension.class).getAllProperties();
    }
}
