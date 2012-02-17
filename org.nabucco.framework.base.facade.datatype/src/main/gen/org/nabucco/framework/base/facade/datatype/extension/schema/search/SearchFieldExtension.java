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
package org.nabucco.framework.base.facade.datatype.extension.schema.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SearchFieldExtension<p/>Extension for one search field.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class SearchFieldExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;", "m1,1;" };

    public static final String NAME = "name";

    public static final String SEARCHQUALIFIER = "searchQualifier";

    public static final String SEARCHFIELD = "searchField";

    /** The name of the field. */
    private StringProperty name;

    /** Defines the qualifier of the search field (SQ1, SQ2, SQ3). */
    private StringProperty searchQualifier;

    /** The field is stored as search field (for special characters) */
    private BooleanProperty searchField;

    /** Constructs a new SearchFieldExtension instance. */
    public SearchFieldExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SearchFieldExtension.
     */
    protected void cloneObject(SearchFieldExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getSearchQualifier() != null)) {
            clone.setSearchQualifier(this.getSearchQualifier().cloneObject());
        }
        if ((this.getSearchField() != null)) {
            clone.setSearchField(this.getSearchField().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(NAME, PropertyDescriptorSupport.createDatatype(NAME, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SEARCHQUALIFIER, PropertyDescriptorSupport.createDatatype(SEARCHQUALIFIER,
                StringProperty.class, 3, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SEARCHFIELD, PropertyDescriptorSupport.createDatatype(SEARCHFIELD, BooleanProperty.class, 4,
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
        properties.add(super.createProperty(SearchFieldExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(SearchFieldExtension.getPropertyDescriptor(SEARCHQUALIFIER),
                this.getSearchQualifier(), null));
        properties.add(super.createProperty(SearchFieldExtension.getPropertyDescriptor(SEARCHFIELD),
                this.getSearchField(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SEARCHQUALIFIER) && (property.getType() == StringProperty.class))) {
            this.setSearchQualifier(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SEARCHFIELD) && (property.getType() == BooleanProperty.class))) {
            this.setSearchField(((BooleanProperty) property.getInstance()));
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
        final SearchFieldExtension other = ((SearchFieldExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.searchQualifier == null)) {
            if ((other.searchQualifier != null))
                return false;
        } else if ((!this.searchQualifier.equals(other.searchQualifier)))
            return false;
        if ((this.searchField == null)) {
            if ((other.searchField != null))
                return false;
        } else if ((!this.searchField.equals(other.searchField)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.searchQualifier == null) ? 0 : this.searchQualifier.hashCode()));
        result = ((PRIME * result) + ((this.searchField == null) ? 0 : this.searchField.hashCode()));
        return result;
    }

    @Override
    public SearchFieldExtension cloneObject() {
        SearchFieldExtension clone = new SearchFieldExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the field.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the field.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * Defines the qualifier of the search field (SQ1, SQ2, SQ3).
     *
     * @param searchQualifier the StringProperty.
     */
    public void setSearchQualifier(StringProperty searchQualifier) {
        this.searchQualifier = searchQualifier;
    }

    /**
     * Defines the qualifier of the search field (SQ1, SQ2, SQ3).
     *
     * @return the StringProperty.
     */
    public StringProperty getSearchQualifier() {
        return this.searchQualifier;
    }

    /**
     * The field is stored as search field (for special characters)
     *
     * @param searchField the BooleanProperty.
     */
    public void setSearchField(BooleanProperty searchField) {
        this.searchField = searchField;
    }

    /**
     * The field is stored as search field (for special characters)
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getSearchField() {
        return this.searchField;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SearchFieldExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SearchFieldExtension.class).getAllProperties();
    }
}
