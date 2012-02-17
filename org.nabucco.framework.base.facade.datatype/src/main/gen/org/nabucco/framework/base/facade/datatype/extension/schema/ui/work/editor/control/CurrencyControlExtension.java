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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * CurrencyControlExtension<p/>Currency control extension<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class CurrencyControlExtension extends EditorControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String SIGNED = "signed";

    public static final String SEPARATOR = "separator";

    public static final String MINIMUMFRACTIONDIGITS = "minimumFractionDigits";

    public static final String MAXIMUMFRACTIONDIGITS = "maximumFractionDigits";

    public static final String MINIMUMINTEGERDIGITS = "minimumIntegerDigits";

    public static final String MAXIMUMINTEGERDIGITS = "maximumIntegerDigits";

    /** Whether the currency is signed or not. */
    private BooleanProperty signed;

    /** Fraction separator */
    private StringProperty separator;

    /** Minimal Number of fraction digits */
    private IntegerProperty minimumFractionDigits;

    /** Maximal Number of fraction digits */
    private IntegerProperty maximumFractionDigits;

    /** Minimal Number of leading digits */
    private IntegerProperty minimumIntegerDigits;

    /** Minimal Number of leading digits */
    private IntegerProperty maximumIntegerDigits;

    /** Constructs a new CurrencyControlExtension instance. */
    public CurrencyControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the CurrencyControlExtension.
     */
    protected void cloneObject(CurrencyControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getSigned() != null)) {
            clone.setSigned(this.getSigned().cloneObject());
        }
        if ((this.getSeparator() != null)) {
            clone.setSeparator(this.getSeparator().cloneObject());
        }
        if ((this.getMinimumFractionDigits() != null)) {
            clone.setMinimumFractionDigits(this.getMinimumFractionDigits().cloneObject());
        }
        if ((this.getMaximumFractionDigits() != null)) {
            clone.setMaximumFractionDigits(this.getMaximumFractionDigits().cloneObject());
        }
        if ((this.getMinimumIntegerDigits() != null)) {
            clone.setMinimumIntegerDigits(this.getMinimumIntegerDigits().cloneObject());
        }
        if ((this.getMaximumIntegerDigits() != null)) {
            clone.setMaximumIntegerDigits(this.getMaximumIntegerDigits().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(EditorControlExtension.class).getPropertyMap());
        propertyMap.put(SIGNED, PropertyDescriptorSupport.createDatatype(SIGNED, BooleanProperty.class, 12,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SEPARATOR, PropertyDescriptorSupport.createDatatype(SEPARATOR, StringProperty.class, 13,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MINIMUMFRACTIONDIGITS, PropertyDescriptorSupport.createDatatype(MINIMUMFRACTIONDIGITS,
                IntegerProperty.class, 14, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MAXIMUMFRACTIONDIGITS, PropertyDescriptorSupport.createDatatype(MAXIMUMFRACTIONDIGITS,
                IntegerProperty.class, 15, PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MINIMUMINTEGERDIGITS, PropertyDescriptorSupport.createDatatype(MINIMUMINTEGERDIGITS,
                IntegerProperty.class, 16, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MAXIMUMINTEGERDIGITS, PropertyDescriptorSupport.createDatatype(MAXIMUMINTEGERDIGITS,
                IntegerProperty.class, 17, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(CurrencyControlExtension.getPropertyDescriptor(SIGNED), this.getSigned(),
                null));
        properties.add(super.createProperty(CurrencyControlExtension.getPropertyDescriptor(SEPARATOR),
                this.getSeparator(), null));
        properties.add(super.createProperty(CurrencyControlExtension.getPropertyDescriptor(MINIMUMFRACTIONDIGITS),
                this.getMinimumFractionDigits(), null));
        properties.add(super.createProperty(CurrencyControlExtension.getPropertyDescriptor(MAXIMUMFRACTIONDIGITS),
                this.getMaximumFractionDigits(), null));
        properties.add(super.createProperty(CurrencyControlExtension.getPropertyDescriptor(MINIMUMINTEGERDIGITS),
                this.getMinimumIntegerDigits(), null));
        properties.add(super.createProperty(CurrencyControlExtension.getPropertyDescriptor(MAXIMUMINTEGERDIGITS),
                this.getMaximumIntegerDigits(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SIGNED) && (property.getType() == BooleanProperty.class))) {
            this.setSigned(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SEPARATOR) && (property.getType() == StringProperty.class))) {
            this.setSeparator(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINIMUMFRACTIONDIGITS) && (property.getType() == IntegerProperty.class))) {
            this.setMinimumFractionDigits(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXIMUMFRACTIONDIGITS) && (property.getType() == IntegerProperty.class))) {
            this.setMaximumFractionDigits(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINIMUMINTEGERDIGITS) && (property.getType() == IntegerProperty.class))) {
            this.setMinimumIntegerDigits(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXIMUMINTEGERDIGITS) && (property.getType() == IntegerProperty.class))) {
            this.setMaximumIntegerDigits(((IntegerProperty) property.getInstance()));
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
        final CurrencyControlExtension other = ((CurrencyControlExtension) obj);
        if ((this.signed == null)) {
            if ((other.signed != null))
                return false;
        } else if ((!this.signed.equals(other.signed)))
            return false;
        if ((this.separator == null)) {
            if ((other.separator != null))
                return false;
        } else if ((!this.separator.equals(other.separator)))
            return false;
        if ((this.minimumFractionDigits == null)) {
            if ((other.minimumFractionDigits != null))
                return false;
        } else if ((!this.minimumFractionDigits.equals(other.minimumFractionDigits)))
            return false;
        if ((this.maximumFractionDigits == null)) {
            if ((other.maximumFractionDigits != null))
                return false;
        } else if ((!this.maximumFractionDigits.equals(other.maximumFractionDigits)))
            return false;
        if ((this.minimumIntegerDigits == null)) {
            if ((other.minimumIntegerDigits != null))
                return false;
        } else if ((!this.minimumIntegerDigits.equals(other.minimumIntegerDigits)))
            return false;
        if ((this.maximumIntegerDigits == null)) {
            if ((other.maximumIntegerDigits != null))
                return false;
        } else if ((!this.maximumIntegerDigits.equals(other.maximumIntegerDigits)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.signed == null) ? 0 : this.signed.hashCode()));
        result = ((PRIME * result) + ((this.separator == null) ? 0 : this.separator.hashCode()));
        result = ((PRIME * result) + ((this.minimumFractionDigits == null) ? 0 : this.minimumFractionDigits.hashCode()));
        result = ((PRIME * result) + ((this.maximumFractionDigits == null) ? 0 : this.maximumFractionDigits.hashCode()));
        result = ((PRIME * result) + ((this.minimumIntegerDigits == null) ? 0 : this.minimumIntegerDigits.hashCode()));
        result = ((PRIME * result) + ((this.maximumIntegerDigits == null) ? 0 : this.maximumIntegerDigits.hashCode()));
        return result;
    }

    @Override
    public CurrencyControlExtension cloneObject() {
        CurrencyControlExtension clone = new CurrencyControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Whether the currency is signed or not.
     *
     * @param signed the BooleanProperty.
     */
    public void setSigned(BooleanProperty signed) {
        this.signed = signed;
    }

    /**
     * Whether the currency is signed or not.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getSigned() {
        return this.signed;
    }

    /**
     * Fraction separator
     *
     * @param separator the StringProperty.
     */
    public void setSeparator(StringProperty separator) {
        this.separator = separator;
    }

    /**
     * Fraction separator
     *
     * @return the StringProperty.
     */
    public StringProperty getSeparator() {
        return this.separator;
    }

    /**
     * Minimal Number of fraction digits
     *
     * @param minimumFractionDigits the IntegerProperty.
     */
    public void setMinimumFractionDigits(IntegerProperty minimumFractionDigits) {
        this.minimumFractionDigits = minimumFractionDigits;
    }

    /**
     * Minimal Number of fraction digits
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMinimumFractionDigits() {
        return this.minimumFractionDigits;
    }

    /**
     * Maximal Number of fraction digits
     *
     * @param maximumFractionDigits the IntegerProperty.
     */
    public void setMaximumFractionDigits(IntegerProperty maximumFractionDigits) {
        this.maximumFractionDigits = maximumFractionDigits;
    }

    /**
     * Maximal Number of fraction digits
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMaximumFractionDigits() {
        return this.maximumFractionDigits;
    }

    /**
     * Minimal Number of leading digits
     *
     * @param minimumIntegerDigits the IntegerProperty.
     */
    public void setMinimumIntegerDigits(IntegerProperty minimumIntegerDigits) {
        this.minimumIntegerDigits = minimumIntegerDigits;
    }

    /**
     * Minimal Number of leading digits
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMinimumIntegerDigits() {
        return this.minimumIntegerDigits;
    }

    /**
     * Minimal Number of leading digits
     *
     * @param maximumIntegerDigits the IntegerProperty.
     */
    public void setMaximumIntegerDigits(IntegerProperty maximumIntegerDigits) {
        this.maximumIntegerDigits = maximumIntegerDigits;
    }

    /**
     * Minimal Number of leading digits
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMaximumIntegerDigits() {
        return this.maximumIntegerDigits;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(CurrencyControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(CurrencyControlExtension.class).getAllProperties();
    }
}
