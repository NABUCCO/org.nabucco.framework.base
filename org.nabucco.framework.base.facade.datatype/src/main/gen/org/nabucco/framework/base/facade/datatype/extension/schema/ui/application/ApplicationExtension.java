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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.application.LocalizationExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ApplicationExtension<p/>NABUCCO User Interface Application extension.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-07-08
 */
public class ApplicationExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;",
            "m1,1;", "m1,1;" };

    public static final String RESOURCE = "resource";

    public static final String TITLE = "title";

    public static final String LAYOUT = "layout";

    public static final String TITLEBAR = "titleBar";

    public static final String PERSPECTIVEAREA = "perspectiveArea";

    public static final String STATUSBAR = "statusBar";

    public static final String DEBUGMODE = "debugMode";

    public static final String LOCALIZATION = "localization";

    /** The Application Resource. */
    private StringProperty resource;

    /** The Application Title. */
    private StringProperty title;

    /** The Application Layout. */
    private StringProperty layout;

    /** The Application Titlebar. */
    private StringProperty titleBar;

    /** The Application Perspective Area. */
    private StringProperty perspectiveArea;

    /** The Application Status Bar. */
    private StringProperty statusBar;

    /** Whether the application runs in debug mode or not. */
    private BooleanProperty debugMode;

    /** The localization settings of the application */
    private LocalizationExtension localization;

    /** Constructs a new ApplicationExtension instance. */
    public ApplicationExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ApplicationExtension.
     */
    protected void cloneObject(ApplicationExtension clone) {
        super.cloneObject(clone);
        if ((this.getResource() != null)) {
            clone.setResource(this.getResource().cloneObject());
        }
        if ((this.getTitle() != null)) {
            clone.setTitle(this.getTitle().cloneObject());
        }
        if ((this.getLayout() != null)) {
            clone.setLayout(this.getLayout().cloneObject());
        }
        if ((this.getTitleBar() != null)) {
            clone.setTitleBar(this.getTitleBar().cloneObject());
        }
        if ((this.getPerspectiveArea() != null)) {
            clone.setPerspectiveArea(this.getPerspectiveArea().cloneObject());
        }
        if ((this.getStatusBar() != null)) {
            clone.setStatusBar(this.getStatusBar().cloneObject());
        }
        if ((this.getDebugMode() != null)) {
            clone.setDebugMode(this.getDebugMode().cloneObject());
        }
        if ((this.getLocalization() != null)) {
            clone.setLocalization(this.getLocalization().cloneObject());
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
        propertyMap.put(RESOURCE, PropertyDescriptorSupport.createDatatype(RESOURCE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TITLE, PropertyDescriptorSupport.createDatatype(TITLE, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LAYOUT, PropertyDescriptorSupport.createDatatype(LAYOUT, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TITLEBAR, PropertyDescriptorSupport.createDatatype(TITLEBAR, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PERSPECTIVEAREA, PropertyDescriptorSupport.createDatatype(PERSPECTIVEAREA,
                StringProperty.class, 8, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(STATUSBAR, PropertyDescriptorSupport.createDatatype(STATUSBAR, StringProperty.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEBUGMODE, PropertyDescriptorSupport.createDatatype(DEBUGMODE, BooleanProperty.class, 10,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LOCALIZATION, PropertyDescriptorSupport.createDatatype(LOCALIZATION,
                LocalizationExtension.class, 11, PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ApplicationExtension.getPropertyDescriptor(RESOURCE), this.getResource(),
                null));
        properties.add(super.createProperty(ApplicationExtension.getPropertyDescriptor(TITLE), this.getTitle(), null));
        properties
                .add(super.createProperty(ApplicationExtension.getPropertyDescriptor(LAYOUT), this.getLayout(), null));
        properties.add(super.createProperty(ApplicationExtension.getPropertyDescriptor(TITLEBAR), this.getTitleBar(),
                null));
        properties.add(super.createProperty(ApplicationExtension.getPropertyDescriptor(PERSPECTIVEAREA),
                this.getPerspectiveArea(), null));
        properties.add(super.createProperty(ApplicationExtension.getPropertyDescriptor(STATUSBAR), this.getStatusBar(),
                null));
        properties.add(super.createProperty(ApplicationExtension.getPropertyDescriptor(DEBUGMODE), this.getDebugMode(),
                null));
        properties.add(super.createProperty(ApplicationExtension.getPropertyDescriptor(LOCALIZATION),
                this.getLocalization(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(RESOURCE) && (property.getType() == StringProperty.class))) {
            this.setResource(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TITLE) && (property.getType() == StringProperty.class))) {
            this.setTitle(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LAYOUT) && (property.getType() == StringProperty.class))) {
            this.setLayout(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TITLEBAR) && (property.getType() == StringProperty.class))) {
            this.setTitleBar(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PERSPECTIVEAREA) && (property.getType() == StringProperty.class))) {
            this.setPerspectiveArea(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATUSBAR) && (property.getType() == StringProperty.class))) {
            this.setStatusBar(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DEBUGMODE) && (property.getType() == BooleanProperty.class))) {
            this.setDebugMode(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCALIZATION) && (property.getType() == LocalizationExtension.class))) {
            this.setLocalization(((LocalizationExtension) property.getInstance()));
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
        final ApplicationExtension other = ((ApplicationExtension) obj);
        if ((this.resource == null)) {
            if ((other.resource != null))
                return false;
        } else if ((!this.resource.equals(other.resource)))
            return false;
        if ((this.title == null)) {
            if ((other.title != null))
                return false;
        } else if ((!this.title.equals(other.title)))
            return false;
        if ((this.layout == null)) {
            if ((other.layout != null))
                return false;
        } else if ((!this.layout.equals(other.layout)))
            return false;
        if ((this.titleBar == null)) {
            if ((other.titleBar != null))
                return false;
        } else if ((!this.titleBar.equals(other.titleBar)))
            return false;
        if ((this.perspectiveArea == null)) {
            if ((other.perspectiveArea != null))
                return false;
        } else if ((!this.perspectiveArea.equals(other.perspectiveArea)))
            return false;
        if ((this.statusBar == null)) {
            if ((other.statusBar != null))
                return false;
        } else if ((!this.statusBar.equals(other.statusBar)))
            return false;
        if ((this.debugMode == null)) {
            if ((other.debugMode != null))
                return false;
        } else if ((!this.debugMode.equals(other.debugMode)))
            return false;
        if ((this.localization == null)) {
            if ((other.localization != null))
                return false;
        } else if ((!this.localization.equals(other.localization)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.resource == null) ? 0 : this.resource.hashCode()));
        result = ((PRIME * result) + ((this.title == null) ? 0 : this.title.hashCode()));
        result = ((PRIME * result) + ((this.layout == null) ? 0 : this.layout.hashCode()));
        result = ((PRIME * result) + ((this.titleBar == null) ? 0 : this.titleBar.hashCode()));
        result = ((PRIME * result) + ((this.perspectiveArea == null) ? 0 : this.perspectiveArea.hashCode()));
        result = ((PRIME * result) + ((this.statusBar == null) ? 0 : this.statusBar.hashCode()));
        result = ((PRIME * result) + ((this.debugMode == null) ? 0 : this.debugMode.hashCode()));
        result = ((PRIME * result) + ((this.localization == null) ? 0 : this.localization.hashCode()));
        return result;
    }

    @Override
    public ApplicationExtension cloneObject() {
        ApplicationExtension clone = new ApplicationExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Application Resource.
     *
     * @param resource the StringProperty.
     */
    public void setResource(StringProperty resource) {
        this.resource = resource;
    }

    /**
     * The Application Resource.
     *
     * @return the StringProperty.
     */
    public StringProperty getResource() {
        return this.resource;
    }

    /**
     * The Application Title.
     *
     * @param title the StringProperty.
     */
    public void setTitle(StringProperty title) {
        this.title = title;
    }

    /**
     * The Application Title.
     *
     * @return the StringProperty.
     */
    public StringProperty getTitle() {
        return this.title;
    }

    /**
     * The Application Layout.
     *
     * @param layout the StringProperty.
     */
    public void setLayout(StringProperty layout) {
        this.layout = layout;
    }

    /**
     * The Application Layout.
     *
     * @return the StringProperty.
     */
    public StringProperty getLayout() {
        return this.layout;
    }

    /**
     * The Application Titlebar.
     *
     * @param titleBar the StringProperty.
     */
    public void setTitleBar(StringProperty titleBar) {
        this.titleBar = titleBar;
    }

    /**
     * The Application Titlebar.
     *
     * @return the StringProperty.
     */
    public StringProperty getTitleBar() {
        return this.titleBar;
    }

    /**
     * The Application Perspective Area.
     *
     * @param perspectiveArea the StringProperty.
     */
    public void setPerspectiveArea(StringProperty perspectiveArea) {
        this.perspectiveArea = perspectiveArea;
    }

    /**
     * The Application Perspective Area.
     *
     * @return the StringProperty.
     */
    public StringProperty getPerspectiveArea() {
        return this.perspectiveArea;
    }

    /**
     * The Application Status Bar.
     *
     * @param statusBar the StringProperty.
     */
    public void setStatusBar(StringProperty statusBar) {
        this.statusBar = statusBar;
    }

    /**
     * The Application Status Bar.
     *
     * @return the StringProperty.
     */
    public StringProperty getStatusBar() {
        return this.statusBar;
    }

    /**
     * Whether the application runs in debug mode or not.
     *
     * @param debugMode the BooleanProperty.
     */
    public void setDebugMode(BooleanProperty debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Whether the application runs in debug mode or not.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getDebugMode() {
        return this.debugMode;
    }

    /**
     * The localization settings of the application
     *
     * @param localization the LocalizationExtension.
     */
    public void setLocalization(LocalizationExtension localization) {
        this.localization = localization;
    }

    /**
     * The localization settings of the application
     *
     * @return the LocalizationExtension.
     */
    public LocalizationExtension getLocalization() {
        return this.localization;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ApplicationExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ApplicationExtension.class).getAllProperties();
    }
}
