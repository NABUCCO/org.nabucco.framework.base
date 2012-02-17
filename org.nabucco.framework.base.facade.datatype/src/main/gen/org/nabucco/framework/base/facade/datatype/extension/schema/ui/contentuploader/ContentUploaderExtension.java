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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.contentuploader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.DialogExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ContentUploaderExtension<p/>extension for content uploader element<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-01-04
 */
public class ContentUploaderExtension extends DialogExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String EXTENSIONFILTER = "extensionFilter";

    /** The extension filter to use by the browsing */
    private StringProperty extensionFilter;

    /** Constructs a new ContentUploaderExtension instance. */
    public ContentUploaderExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ContentUploaderExtension.
     */
    protected void cloneObject(ContentUploaderExtension clone) {
        super.cloneObject(clone);
        if ((this.getExtensionFilter() != null)) {
            clone.setExtensionFilter(this.getExtensionFilter().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(DialogExtension.class).getPropertyMap());
        propertyMap.put(EXTENSIONFILTER, PropertyDescriptorSupport.createDatatype(EXTENSIONFILTER,
                StringProperty.class, 9, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentUploaderExtension.getPropertyDescriptor(EXTENSIONFILTER),
                this.getExtensionFilter(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXTENSIONFILTER) && (property.getType() == StringProperty.class))) {
            this.setExtensionFilter(((StringProperty) property.getInstance()));
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
        final ContentUploaderExtension other = ((ContentUploaderExtension) obj);
        if ((this.extensionFilter == null)) {
            if ((other.extensionFilter != null))
                return false;
        } else if ((!this.extensionFilter.equals(other.extensionFilter)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.extensionFilter == null) ? 0 : this.extensionFilter.hashCode()));
        return result;
    }

    @Override
    public ContentUploaderExtension cloneObject() {
        ContentUploaderExtension clone = new ContentUploaderExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The extension filter to use by the browsing
     *
     * @param extensionFilter the StringProperty.
     */
    public void setExtensionFilter(StringProperty extensionFilter) {
        this.extensionFilter = extensionFilter;
    }

    /**
     * The extension filter to use by the browsing
     *
     * @return the StringProperty.
     */
    public StringProperty getExtensionFilter() {
        return this.extensionFilter;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentUploaderExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentUploaderExtension.class).getAllProperties();
    }
}
