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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.colorsheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.colorsheme.ColorShemeItemExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ColorShemeExtension<p/>NABUCCO User Interface Color Sheme extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-10
 */
public class ColorShemeExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String COLORS = "colors";

    /** The colors of the sheme */
    public NabuccoList<ColorShemeItemExtension> colors;

    /** Constructs a new ColorShemeExtension instance. */
    public ColorShemeExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ColorShemeExtension.
     */
    protected void cloneObject(ColorShemeExtension clone) {
        super.cloneObject(clone);
        if ((this.colors != null)) {
            clone.colors = this.colors.cloneCollection();
        }
    }

    /**
     * Getter for the ColorsJPA.
     *
     * @return the List<ColorShemeItemExtension>.
     */
    List<ColorShemeItemExtension> getColorsJPA() {
        if ((this.colors == null)) {
            this.colors = new NabuccoListImpl<ColorShemeItemExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ColorShemeItemExtension>) this.colors).getDelegate();
    }

    /**
     * Setter for the ColorsJPA.
     *
     * @param colors the List<ColorShemeItemExtension>.
     */
    void setColorsJPA(List<ColorShemeItemExtension> colors) {
        if ((this.colors == null)) {
            this.colors = new NabuccoListImpl<ColorShemeItemExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ColorShemeItemExtension>) this.colors).setDelegate(colors);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(COLORS, PropertyDescriptorSupport.createCollection(COLORS, ColorShemeItemExtension.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ColorShemeExtension.getPropertyDescriptor(COLORS), this.colors, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(COLORS) && (property.getType() == ColorShemeItemExtension.class))) {
            this.colors = ((NabuccoList<ColorShemeItemExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public ColorShemeExtension cloneObject() {
        ColorShemeExtension clone = new ColorShemeExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The colors of the sheme
     *
     * @return the NabuccoList<ColorShemeItemExtension>.
     */
    public NabuccoList<ColorShemeItemExtension> getColors() {
        if ((this.colors == null)) {
            this.colors = new NabuccoListImpl<ColorShemeItemExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.colors;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ColorShemeExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ColorShemeExtension.class).getAllProperties();
    }
}
