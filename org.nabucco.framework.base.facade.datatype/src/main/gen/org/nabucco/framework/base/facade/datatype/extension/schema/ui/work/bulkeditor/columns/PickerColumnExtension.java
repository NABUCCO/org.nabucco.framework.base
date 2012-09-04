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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.BulkEditorColumnExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PickerColumnExtension<p/>NABUCCO User Interface dropdown column<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-04-11
 */
public class PickerColumnExtension extends BulkEditorColumnExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String AUTOCOMPLETIONFILTER = "autoCompletionFilter";

    public static final String DISPLAYPATH = "displayPath";

    /** The autocompletion filter to use */
    private StringProperty autoCompletionFilter;

    /** The display path */
    private StringProperty displayPath;

    /** Constructs a new PickerColumnExtension instance. */
    public PickerColumnExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PickerColumnExtension.
     */
    protected void cloneObject(PickerColumnExtension clone) {
        super.cloneObject(clone);
        if ((this.getAutoCompletionFilter() != null)) {
            clone.setAutoCompletionFilter(this.getAutoCompletionFilter().cloneObject());
        }
        if ((this.getDisplayPath() != null)) {
            clone.setDisplayPath(this.getDisplayPath().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(BulkEditorColumnExtension.class).getPropertyMap());
        propertyMap.put(AUTOCOMPLETIONFILTER, PropertyDescriptorSupport.createDatatype(AUTOCOMPLETIONFILTER,
                StringProperty.class, 12, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DISPLAYPATH, PropertyDescriptorSupport.createDatatype(DISPLAYPATH, StringProperty.class, 13,
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
        properties.add(super.createProperty(PickerColumnExtension.getPropertyDescriptor(AUTOCOMPLETIONFILTER),
                this.getAutoCompletionFilter(), null));
        properties.add(super.createProperty(PickerColumnExtension.getPropertyDescriptor(DISPLAYPATH),
                this.getDisplayPath(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(AUTOCOMPLETIONFILTER) && (property.getType() == StringProperty.class))) {
            this.setAutoCompletionFilter(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DISPLAYPATH) && (property.getType() == StringProperty.class))) {
            this.setDisplayPath(((StringProperty) property.getInstance()));
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
        final PickerColumnExtension other = ((PickerColumnExtension) obj);
        if ((this.autoCompletionFilter == null)) {
            if ((other.autoCompletionFilter != null))
                return false;
        } else if ((!this.autoCompletionFilter.equals(other.autoCompletionFilter)))
            return false;
        if ((this.displayPath == null)) {
            if ((other.displayPath != null))
                return false;
        } else if ((!this.displayPath.equals(other.displayPath)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.autoCompletionFilter == null) ? 0 : this.autoCompletionFilter.hashCode()));
        result = ((PRIME * result) + ((this.displayPath == null) ? 0 : this.displayPath.hashCode()));
        return result;
    }

    @Override
    public PickerColumnExtension cloneObject() {
        PickerColumnExtension clone = new PickerColumnExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The autocompletion filter to use
     *
     * @param autoCompletionFilter the StringProperty.
     */
    public void setAutoCompletionFilter(StringProperty autoCompletionFilter) {
        this.autoCompletionFilter = autoCompletionFilter;
    }

    /**
     * The autocompletion filter to use
     *
     * @return the StringProperty.
     */
    public StringProperty getAutoCompletionFilter() {
        return this.autoCompletionFilter;
    }

    /**
     * The display path
     *
     * @param displayPath the StringProperty.
     */
    public void setDisplayPath(StringProperty displayPath) {
        this.displayPath = displayPath;
    }

    /**
     * The display path
     *
     * @return the StringProperty.
     */
    public StringProperty getDisplayPath() {
        return this.displayPath;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PickerColumnExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PickerColumnExtension.class).getAllProperties();
    }
}
