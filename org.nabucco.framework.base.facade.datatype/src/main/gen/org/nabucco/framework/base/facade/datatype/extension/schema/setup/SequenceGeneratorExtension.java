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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SequenceGeneratorExtension<p/>Configuration for sequence generators.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceGeneratorExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "m0,1;", "m0,1;", "m0,1;",
            "m0,1;", "m0,1;", "m0,1;" };

    public static final String TYPE = "type";

    public static final String ID = "id";

    public static final String START = "start";

    public static final String OVERFLOW = "overflow";

    public static final String STEPS = "steps";

    public static final String LENGTH = "length";

    public static final String LEADINGZEROS = "leadingZeros";

    public static final String AUTORESET = "autoReset";

    public static final String PATTERN = "pattern";

    /** The generator type. */
    private EnumerationProperty type;

    /** The generator id. */
    private StringProperty id;

    /** The sequence start value. */
    private StringProperty start;

    /** When max value is reached, reset to start value. */
    private BooleanProperty overflow;

    /** The sequence step size. */
    private StringProperty steps;

    /** The amount of numbers. */
    private StringProperty length;

    /** Fill the number with leading zeros. */
    private BooleanProperty leadingZeros;

    /** Reset to start value by a triggering event. */
    private EnumerationProperty autoReset;

    /** The date pattern. */
    private StringProperty pattern;

    /** Constructs a new SequenceGeneratorExtension instance. */
    public SequenceGeneratorExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SequenceGeneratorExtension.
     */
    protected void cloneObject(SequenceGeneratorExtension clone) {
        super.cloneObject(clone);
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getId() != null)) {
            clone.setId(this.getId().cloneObject());
        }
        if ((this.getStart() != null)) {
            clone.setStart(this.getStart().cloneObject());
        }
        if ((this.getOverflow() != null)) {
            clone.setOverflow(this.getOverflow().cloneObject());
        }
        if ((this.getSteps() != null)) {
            clone.setSteps(this.getSteps().cloneObject());
        }
        if ((this.getLength() != null)) {
            clone.setLength(this.getLength().cloneObject());
        }
        if ((this.getLeadingZeros() != null)) {
            clone.setLeadingZeros(this.getLeadingZeros().cloneObject());
        }
        if ((this.getAutoReset() != null)) {
            clone.setAutoReset(this.getAutoReset().cloneObject());
        }
        if ((this.getPattern() != null)) {
            clone.setPattern(this.getPattern().cloneObject());
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
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, EnumerationProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ID, PropertyDescriptorSupport.createDatatype(ID, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(START, PropertyDescriptorSupport.createDatatype(START, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OVERFLOW, PropertyDescriptorSupport.createDatatype(OVERFLOW, BooleanProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(STEPS, PropertyDescriptorSupport.createDatatype(STEPS, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LENGTH, PropertyDescriptorSupport.createDatatype(LENGTH, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LEADINGZEROS, PropertyDescriptorSupport.createDatatype(LEADINGZEROS, BooleanProperty.class, 8,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(AUTORESET, PropertyDescriptorSupport.createDatatype(AUTORESET, EnumerationProperty.class, 9,
                PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PATTERN, PropertyDescriptorSupport.createDatatype(PATTERN, StringProperty.class, 10,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(TYPE), this.getType(),
                null));
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(ID), this.getId(), null));
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(START), this.getStart(),
                null));
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(OVERFLOW),
                this.getOverflow(), null));
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(STEPS), this.getSteps(),
                null));
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(LENGTH), this.getLength(),
                null));
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(LEADINGZEROS),
                this.getLeadingZeros(), null));
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(AUTORESET),
                this.getAutoReset(), null));
        properties.add(super.createProperty(SequenceGeneratorExtension.getPropertyDescriptor(PATTERN),
                this.getPattern(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ID) && (property.getType() == StringProperty.class))) {
            this.setId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(START) && (property.getType() == StringProperty.class))) {
            this.setStart(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OVERFLOW) && (property.getType() == BooleanProperty.class))) {
            this.setOverflow(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STEPS) && (property.getType() == StringProperty.class))) {
            this.setSteps(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LENGTH) && (property.getType() == StringProperty.class))) {
            this.setLength(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LEADINGZEROS) && (property.getType() == BooleanProperty.class))) {
            this.setLeadingZeros(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(AUTORESET) && (property.getType() == EnumerationProperty.class))) {
            this.setAutoReset(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PATTERN) && (property.getType() == StringProperty.class))) {
            this.setPattern(((StringProperty) property.getInstance()));
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
        final SequenceGeneratorExtension other = ((SequenceGeneratorExtension) obj);
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.start == null)) {
            if ((other.start != null))
                return false;
        } else if ((!this.start.equals(other.start)))
            return false;
        if ((this.overflow == null)) {
            if ((other.overflow != null))
                return false;
        } else if ((!this.overflow.equals(other.overflow)))
            return false;
        if ((this.steps == null)) {
            if ((other.steps != null))
                return false;
        } else if ((!this.steps.equals(other.steps)))
            return false;
        if ((this.length == null)) {
            if ((other.length != null))
                return false;
        } else if ((!this.length.equals(other.length)))
            return false;
        if ((this.leadingZeros == null)) {
            if ((other.leadingZeros != null))
                return false;
        } else if ((!this.leadingZeros.equals(other.leadingZeros)))
            return false;
        if ((this.autoReset == null)) {
            if ((other.autoReset != null))
                return false;
        } else if ((!this.autoReset.equals(other.autoReset)))
            return false;
        if ((this.pattern == null)) {
            if ((other.pattern != null))
                return false;
        } else if ((!this.pattern.equals(other.pattern)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.start == null) ? 0 : this.start.hashCode()));
        result = ((PRIME * result) + ((this.overflow == null) ? 0 : this.overflow.hashCode()));
        result = ((PRIME * result) + ((this.steps == null) ? 0 : this.steps.hashCode()));
        result = ((PRIME * result) + ((this.length == null) ? 0 : this.length.hashCode()));
        result = ((PRIME * result) + ((this.leadingZeros == null) ? 0 : this.leadingZeros.hashCode()));
        result = ((PRIME * result) + ((this.autoReset == null) ? 0 : this.autoReset.hashCode()));
        result = ((PRIME * result) + ((this.pattern == null) ? 0 : this.pattern.hashCode()));
        return result;
    }

    @Override
    public SequenceGeneratorExtension cloneObject() {
        SequenceGeneratorExtension clone = new SequenceGeneratorExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The generator type.
     *
     * @param type the EnumerationProperty.
     */
    public void setType(EnumerationProperty type) {
        this.type = type;
    }

    /**
     * The generator type.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getType() {
        return this.type;
    }

    /**
     * The generator id.
     *
     * @param id the StringProperty.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * The generator id.
     *
     * @return the StringProperty.
     */
    public StringProperty getId() {
        return this.id;
    }

    /**
     * The sequence start value.
     *
     * @param start the StringProperty.
     */
    public void setStart(StringProperty start) {
        this.start = start;
    }

    /**
     * The sequence start value.
     *
     * @return the StringProperty.
     */
    public StringProperty getStart() {
        return this.start;
    }

    /**
     * When max value is reached, reset to start value.
     *
     * @param overflow the BooleanProperty.
     */
    public void setOverflow(BooleanProperty overflow) {
        this.overflow = overflow;
    }

    /**
     * When max value is reached, reset to start value.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getOverflow() {
        return this.overflow;
    }

    /**
     * The sequence step size.
     *
     * @param steps the StringProperty.
     */
    public void setSteps(StringProperty steps) {
        this.steps = steps;
    }

    /**
     * The sequence step size.
     *
     * @return the StringProperty.
     */
    public StringProperty getSteps() {
        return this.steps;
    }

    /**
     * The amount of numbers.
     *
     * @param length the StringProperty.
     */
    public void setLength(StringProperty length) {
        this.length = length;
    }

    /**
     * The amount of numbers.
     *
     * @return the StringProperty.
     */
    public StringProperty getLength() {
        return this.length;
    }

    /**
     * Fill the number with leading zeros.
     *
     * @param leadingZeros the BooleanProperty.
     */
    public void setLeadingZeros(BooleanProperty leadingZeros) {
        this.leadingZeros = leadingZeros;
    }

    /**
     * Fill the number with leading zeros.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getLeadingZeros() {
        return this.leadingZeros;
    }

    /**
     * Reset to start value by a triggering event.
     *
     * @param autoReset the EnumerationProperty.
     */
    public void setAutoReset(EnumerationProperty autoReset) {
        this.autoReset = autoReset;
    }

    /**
     * Reset to start value by a triggering event.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getAutoReset() {
        return this.autoReset;
    }

    /**
     * The date pattern.
     *
     * @param pattern the StringProperty.
     */
    public void setPattern(StringProperty pattern) {
        this.pattern = pattern;
    }

    /**
     * The date pattern.
     *
     * @return the StringProperty.
     */
    public StringProperty getPattern() {
        return this.pattern;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SequenceGeneratorExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SequenceGeneratorExtension.class).getAllProperties();
    }
}
