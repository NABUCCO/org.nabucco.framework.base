/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.date;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.date.PeriodType;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.EnumProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * Period<p/>A specific time<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-26
 */
public class Period extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "count", "periodType" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "m1,1;" };

    /** The count of units specified by periodType */
    private Number count;

    /** The type of period units */
    private PeriodType periodType;

    /** Constructs a new Period instance. */
    public Period() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Period.
     */
    protected void cloneObject(Period clone) {
        super.cloneObject(clone);
        if ((this.getCount() != null)) {
            clone.setCount(this.getCount().cloneObject());
        }
        clone.setPeriodType(this.getPeriodType());
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Number>(PROPERTY_NAMES[0], Number.class,
                PROPERTY_CONSTRAINTS[0], this.count));
        properties.add(new EnumProperty<PeriodType>(PROPERTY_NAMES[1], PeriodType.class,
                PROPERTY_CONSTRAINTS[1], this.periodType));
        return properties;
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
        final Period other = ((Period) obj);
        if ((this.count == null)) {
            if ((other.count != null))
                return false;
        } else if ((!this.count.equals(other.count)))
            return false;
        if ((this.periodType == null)) {
            if ((other.periodType != null))
                return false;
        } else if ((!this.periodType.equals(other.periodType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.count == null) ? 0 : this.count.hashCode()));
        result = ((PRIME * result) + ((this.periodType == null) ? 0 : this.periodType.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Period>\n");
        appendable.append(super.toString());
        appendable.append((("<count>" + this.count) + "</count>\n"));
        appendable.append((("<periodType>" + this.periodType) + "</periodType>\n"));
        appendable.append("</Period>\n");
        return appendable.toString();
    }

    @Override
    public Period cloneObject() {
        Period clone = new Period();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The count of units specified by periodType
     *
     * @return the Number.
     */
    public Number getCount() {
        return this.count;
    }

    /**
     * The count of units specified by periodType
     *
     * @param count the Number.
     */
    public void setCount(Number count) {
        this.count = count;
    }

    /**
     * The count of units specified by periodType
     *
     * @param count the Integer.
     */
    public void setCount(Integer count) {
        if ((this.count == null)) {
            this.count = new Number();
        }
        this.count.setValue(count);
    }

    /**
     * The type of period units
     *
     * @return the PeriodType.
     */
    public PeriodType getPeriodType() {
        return this.periodType;
    }

    /**
     * The type of period units
     *
     * @param periodType the PeriodType.
     */
    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    /**
     * The type of period units
     *
     * @param periodType the String.
     */
    public void setPeriodType(String periodType) {
        if ((periodType == null)) {
            this.periodType = null;
        } else {
            this.periodType = PeriodType.valueOf(periodType);
        }
    }
}
