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
package org.nabucco.framework.base.ui.web.model.table.comparator;

import java.util.Comparator;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NBoolean;
import org.nabucco.framework.base.facade.datatype.NByte;
import org.nabucco.framework.base.facade.datatype.NByteArray;
import org.nabucco.framework.base.facade.datatype.NChar;
import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.facade.datatype.NDecimal;
import org.nabucco.framework.base.facade.datatype.NDouble;
import org.nabucco.framework.base.facade.datatype.NFloat;
import org.nabucco.framework.base.facade.datatype.NInteger;
import org.nabucco.framework.base.facade.datatype.NLong;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.NText;
import org.nabucco.framework.base.facade.datatype.NTime;
import org.nabucco.framework.base.facade.datatype.NTimestamp;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;

/**
 * Generic Table Comparator for all NABUCCO datatypes.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class TableComparator implements Comparator<Datatype> {

    /** The name of the property to compare. */
    private String property;

    /** An optional nested comparator. */
    private TableComparator nestedComparator;

    /** Whether the comparator is comparing in normal or reverse order. */
    private boolean reverse = false;

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(TableComparator.class);

    /**
     * Creates a comparator instance for one property.
     * 
     * Note: Only properties of basetypes are supported, all other property types will be ignored
     * 
     * @param property
     *            The name of the property.
     */
    public TableComparator(String property) {
        this(property, null);
    }

    /**
     * Creates a comparator instance with a nested comparator. The nested comparator will be
     * consulted id the result is 0.
     * 
     * Note: Only properties of basetypes are supported, all other property types will be ignored
     * 
     * @param property
     *            The name of the property.
     * @param nestedComparator
     *            The nested comparator (should use a different property than the actual
     *            comparator).
     */
    public TableComparator(String property, TableComparator nestedComparator) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot compare property [null].");
        }

        this.property = property;
        this.nestedComparator = nestedComparator;
    }

    /**
     * Getter for the property.
     * 
     * @return Returns the property.
     */
    public String getProperty() {
        return this.property;
    }

    /**
     * Getter for the reverse.
     * 
     * @return Returns the reverse.
     */
    public boolean isReverse() {
        return this.reverse;
    }

    /**
     * Getter for the nestedComparator.
     * 
     * @return Returns the nestedComparator.
     */
    public TableComparator getNestedComparator() {
        return this.nestedComparator;
    }

    /**
     * Compares to datatypes with the given property name.
     */
    @Override
    public int compare(Datatype d1, Datatype d2) {

        if (d1 == null || d2 == null) {
            logger.warning("Cannot compare Nullpointer");
            return 0;
        }

        NabuccoPropertyResolver<Datatype> resolver1 = new NabuccoPropertyResolver<Datatype>(d1);
        NabuccoPropertyResolver<Datatype> resolver2 = new NabuccoPropertyResolver<Datatype>(d2);
        
        NabuccoProperty p1 = resolver1.resolveProperty(this.property);
        NabuccoProperty p2 = resolver2.resolveProperty(this.property);

        if (p1 == null) {
            logger.warning("Property ", this.property, " is not defined in datatype '" + d1.getClass().getName(), "'.");
            return 0;
        }

        if (!p1.getType().isAssignableFrom(p2.getType()) && !p2.getType().isAssignableFrom(p1.getType())) {
            logger.warning("Cannot compare different Datatypes '", d1.getClass().getName(), "' and '", d2.getClass()
                    .getName(), "'.");
            return 0;
        }

        int result = 0;

        switch (p1.getPropertyType()) {

        case BASETYPE: {
            result = this.compareBasetypes((BasetypeProperty) p1, (BasetypeProperty) p2);
        }

        }

        if (this.nestedComparator != null && result == 0) {
            return nestedComparator.compare(d1, d2);
        }

        if (this.reverse) {
            return -result;
        }
        return result;
    }

    /**
     * Compare two basetype properties.
     * 
     * @param bp1
     *            first basetype property
     * @param bp2
     *            second basetype property
     * 
     * @return the comparison result
     */
    private int compareBasetypes(BasetypeProperty bp1, BasetypeProperty bp2) {

        Basetype b1 = bp1.getInstance();
        Basetype b2 = bp2.getInstance();

        if (b1 == null) {
            if (b2 == null) {
                return 0;
            }
            return 1;
        }

        if (b2 == null) {
            return -1;
        }

        if (b1.getType() != b2.getType()) {
            logger.warning("Cannot compare different Basetypes '", b1.getType(), "' and '", b2.getType(), "'.");
            return 0;
        }

        BasetypeType type = b1.getType();

        int result;

        switch (type) {
        case STRING: {
            result = this.compare((NString) b1, (NString) b2);
            break;
        }
        case DATE: {
            result = this.compare((NDate) b1, (NDate) b2);
            break;
        }
        case INTEGER: {
            result = this.compare((NInteger) b1, (NInteger) b2);
            break;
        }
        case LONG: {
            result = this.compare((NLong) b1, (NLong) b2);
            break;
        }
        case TIME: {
            result = this.compare((NTime) b1, (NTime) b2);
            break;
        }
        case TIMESTAMP: {
            result = this.compare((NTimestamp) b1, (NTimestamp) b2);
            break;
        }
        case BOOLEAN: {
            result = this.compare((NBoolean) b1, (NBoolean) b2);
            break;
        }
        case BYTE: {
            result = this.compare((NByte) b1, (NByte) b2);
            break;
        }
        case BYTE_ARRAY: {
            result = this.compare((NByteArray) b1, (NByteArray) b2);
            break;
        }
        case CHAR: {
            result = this.compare((NChar) b1, (NChar) b2);
            break;
        }
        case DECIMAL: {
            result = this.compare((NDecimal) b1, (NDecimal) b2);
            break;
        }
        case DOUBLE: {
            result = this.compare((NDouble) b1, (NDouble) b2);
            break;
        }
        case FLOAT: {
            result = this.compare((NFloat) b1, (NFloat) b2);
            break;
        }
        case TEXT: {
            result = this.compare((NText) b1, (NText) b2);
            break;
        }
        default: {
            logger.error("Cannot compare unsupported basetype [" + type + "].");
            result = 0;
        }
        }
        return result;
    }

    /**
     * Compares to {@link NString} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NString n1, NString n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NDate} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NDate n1, NDate n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NText} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NText n1, NText n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NTime} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NTime n1, NTime n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NTimestamp} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NTimestamp n1, NTimestamp n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NDecimal} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NDecimal n1, NDecimal n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NLong} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NLong n1, NLong n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NInteger} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NInteger n1, NInteger n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NBoolean} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NBoolean n1, NBoolean n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NChar} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NChar n1, NChar n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NDouble} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NDouble n1, NDouble n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NFloat} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NFloat n1, NFloat n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NByte} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NByte n1, NByte n2) {
        return n1.compareTo(n2);
    }

    /**
     * Compares to {@link NByteArray} instances for order.
     * 
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     *         to, or greater than the specified object.
     */
    private int compare(NByteArray n1, NByteArray n2) {
        return n1.compareTo(n2);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.nestedComparator == null) ? 0 : this.nestedComparator.hashCode());
        result = prime * result + ((this.property == null) ? 0 : this.property.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TableComparator))
            return false;
        TableComparator other = (TableComparator) obj;
        if (this.nestedComparator == null) {
            if (other.nestedComparator != null)
                return false;
        } else if (!this.nestedComparator.equals(other.nestedComparator))
            return false;
        if (this.property == null) {
            if (other.property != null)
                return false;
        } else if (!this.property.equals(other.property))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.property + ((this.nestedComparator != null) ? (", " + this.nestedComparator) : "");
    }

    /**
     * Reverse the comparation.
     */
    public void reverse() {
        this.reverse = !this.reverse;
    }
}
