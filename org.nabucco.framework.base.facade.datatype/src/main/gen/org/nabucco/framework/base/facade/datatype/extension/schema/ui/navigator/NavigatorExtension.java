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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator;

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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator.NavigatorEntryExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * NavigatorExtension<p/>NABUCCO User Interface Navigator extension.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-07-08
 */
public class NavigatorExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,n;" };

    public static final String LABEL = "label";

    public static final String PERSPECTIVE = "perspective";

    public static final String NAVIGATORENTRYLIST = "navigatorEntryList";

    /** Tha Navigator label. */
    private StringProperty label;

    /** The perspective for this navigator */
    private StringProperty perspective;

    /** The list of navigator entries. */
    private NabuccoList<NavigatorEntryExtension> navigatorEntryList;

    /** Constructs a new NavigatorExtension instance. */
    public NavigatorExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the NavigatorExtension.
     */
    protected void cloneObject(NavigatorExtension clone) {
        super.cloneObject(clone);
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getPerspective() != null)) {
            clone.setPerspective(this.getPerspective().cloneObject());
        }
        if ((this.navigatorEntryList != null)) {
            clone.navigatorEntryList = this.navigatorEntryList.cloneCollection();
        }
    }

    /**
     * Getter for the NavigatorEntryListJPA.
     *
     * @return the List<NavigatorEntryExtension>.
     */
    List<NavigatorEntryExtension> getNavigatorEntryListJPA() {
        if ((this.navigatorEntryList == null)) {
            this.navigatorEntryList = new NabuccoListImpl<NavigatorEntryExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<NavigatorEntryExtension>) this.navigatorEntryList).getDelegate();
    }

    /**
     * Setter for the NavigatorEntryListJPA.
     *
     * @param navigatorEntryList the List<NavigatorEntryExtension>.
     */
    void setNavigatorEntryListJPA(List<NavigatorEntryExtension> navigatorEntryList) {
        if ((this.navigatorEntryList == null)) {
            this.navigatorEntryList = new NabuccoListImpl<NavigatorEntryExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<NavigatorEntryExtension>) this.navigatorEntryList).setDelegate(navigatorEntryList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PERSPECTIVE, PropertyDescriptorSupport.createDatatype(PERSPECTIVE, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NAVIGATORENTRYLIST, PropertyDescriptorSupport.createCollection(NAVIGATORENTRYLIST,
                NavigatorEntryExtension.class, 6, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(NavigatorExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties.add(super.createProperty(NavigatorExtension.getPropertyDescriptor(PERSPECTIVE),
                this.getPerspective(), null));
        properties.add(super.createProperty(NavigatorExtension.getPropertyDescriptor(NAVIGATORENTRYLIST),
                this.navigatorEntryList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PERSPECTIVE) && (property.getType() == StringProperty.class))) {
            this.setPerspective(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAVIGATORENTRYLIST) && (property.getType() == NavigatorEntryExtension.class))) {
            this.navigatorEntryList = ((NabuccoList<NavigatorEntryExtension>) property.getInstance());
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
        final NavigatorExtension other = ((NavigatorExtension) obj);
        if ((this.label == null)) {
            if ((other.label != null))
                return false;
        } else if ((!this.label.equals(other.label)))
            return false;
        if ((this.perspective == null)) {
            if ((other.perspective != null))
                return false;
        } else if ((!this.perspective.equals(other.perspective)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.perspective == null) ? 0 : this.perspective.hashCode()));
        return result;
    }

    @Override
    public NavigatorExtension cloneObject() {
        NavigatorExtension clone = new NavigatorExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Tha Navigator label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * Tha Navigator label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The perspective for this navigator
     *
     * @param perspective the StringProperty.
     */
    public void setPerspective(StringProperty perspective) {
        this.perspective = perspective;
    }

    /**
     * The perspective for this navigator
     *
     * @return the StringProperty.
     */
    public StringProperty getPerspective() {
        return this.perspective;
    }

    /**
     * The list of navigator entries.
     *
     * @return the NabuccoList<NavigatorEntryExtension>.
     */
    public NabuccoList<NavigatorEntryExtension> getNavigatorEntryList() {
        if ((this.navigatorEntryList == null)) {
            this.navigatorEntryList = new NabuccoListImpl<NavigatorEntryExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.navigatorEntryList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(NavigatorExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(NavigatorExtension.class).getAllProperties();
    }
}
