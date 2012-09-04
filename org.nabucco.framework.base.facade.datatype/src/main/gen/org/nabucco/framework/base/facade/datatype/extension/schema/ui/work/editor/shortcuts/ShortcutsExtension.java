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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.shortcuts;

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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.shortcuts.ShortcutExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ShortcutsExtension<p/>NABUCCO User Interface Shortcut extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranosvkiy, PRODYNA AG, 2012-04-20
 */
public class ShortcutsExtension extends WorkItemExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "m1,1;", "m1,1;" };

    public static final String SHORTCUTS = "shortcuts";

    public static final String TYPE = "type";

    public static final String LANGUAGE = "language";

    /** The list with shortcuts */
    private NabuccoList<ShortcutExtension> shortcuts;

    /** Type of the element */
    private EnumerationProperty type;

    /** The language to use shortcuts for */
    private StringProperty language;

    /** Constructs a new ShortcutsExtension instance. */
    public ShortcutsExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ShortcutsExtension.
     */
    protected void cloneObject(ShortcutsExtension clone) {
        super.cloneObject(clone);
        if ((this.shortcuts != null)) {
            clone.shortcuts = this.shortcuts.cloneCollection();
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getLanguage() != null)) {
            clone.setLanguage(this.getLanguage().cloneObject());
        }
    }

    /**
     * Getter for the ShortcutsJPA.
     *
     * @return the List<ShortcutExtension>.
     */
    List<ShortcutExtension> getShortcutsJPA() {
        if ((this.shortcuts == null)) {
            this.shortcuts = new NabuccoListImpl<ShortcutExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ShortcutExtension>) this.shortcuts).getDelegate();
    }

    /**
     * Setter for the ShortcutsJPA.
     *
     * @param shortcuts the List<ShortcutExtension>.
     */
    void setShortcutsJPA(List<ShortcutExtension> shortcuts) {
        if ((this.shortcuts == null)) {
            this.shortcuts = new NabuccoListImpl<ShortcutExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ShortcutExtension>) this.shortcuts).setDelegate(shortcuts);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkItemExtension.class).getPropertyMap());
        propertyMap.put(SHORTCUTS, PropertyDescriptorSupport.createCollection(SHORTCUTS, ShortcutExtension.class, 11,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, EnumerationProperty.class, 12,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LANGUAGE, PropertyDescriptorSupport.createDatatype(LANGUAGE, StringProperty.class, 13,
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
        properties.add(super.createProperty(ShortcutsExtension.getPropertyDescriptor(SHORTCUTS), this.shortcuts, null));
        properties.add(super.createProperty(ShortcutsExtension.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(ShortcutsExtension.getPropertyDescriptor(LANGUAGE), this.getLanguage(),
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SHORTCUTS) && (property.getType() == ShortcutExtension.class))) {
            this.shortcuts = ((NabuccoList<ShortcutExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LANGUAGE) && (property.getType() == StringProperty.class))) {
            this.setLanguage(((StringProperty) property.getInstance()));
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
        final ShortcutsExtension other = ((ShortcutsExtension) obj);
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.language == null)) {
            if ((other.language != null))
                return false;
        } else if ((!this.language.equals(other.language)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.language == null) ? 0 : this.language.hashCode()));
        return result;
    }

    @Override
    public ShortcutsExtension cloneObject() {
        ShortcutsExtension clone = new ShortcutsExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list with shortcuts
     *
     * @return the NabuccoList<ShortcutExtension>.
     */
    public NabuccoList<ShortcutExtension> getShortcuts() {
        if ((this.shortcuts == null)) {
            this.shortcuts = new NabuccoListImpl<ShortcutExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.shortcuts;
    }

    /**
     * Type of the element
     *
     * @param type the EnumerationProperty.
     */
    public void setType(EnumerationProperty type) {
        this.type = type;
    }

    /**
     * Type of the element
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getType() {
        return this.type;
    }

    /**
     * The language to use shortcuts for
     *
     * @param language the StringProperty.
     */
    public void setLanguage(StringProperty language) {
        this.language = language;
    }

    /**
     * The language to use shortcuts for
     *
     * @return the StringProperty.
     */
    public StringProperty getLanguage() {
        return this.language;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ShortcutsExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ShortcutsExtension.class).getAllProperties();
    }
}
