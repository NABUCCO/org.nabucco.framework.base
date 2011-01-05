/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.reporting.reportdata.ReportEntry;
import org.nabucco.framework.base.facade.datatype.reporting.reportdata.base.ReportInteger;

/**
 * ReportEntryInteger
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-10
 */
public class ReportEntryInteger extends ReportEntry implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "value" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;" };

    private ReportInteger value;

    /** Constructs a new ReportEntryInteger instance. */
    public ReportEntryInteger() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ReportEntryInteger.
     */
    protected void cloneObject(ReportEntryInteger clone) {
        super.cloneObject(clone);
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
        }
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<ReportInteger>(PROPERTY_NAMES[0], ReportInteger.class,
                PROPERTY_CONSTRAINTS[0], this.value));
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
        final ReportEntryInteger other = ((ReportEntryInteger) obj);
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<ReportEntryInteger>\n");
        appendable.append(super.toString());
        appendable.append((("<value>" + this.value) + "</value>\n"));
        appendable.append("</ReportEntryInteger>\n");
        return appendable.toString();
    }

    @Override
    public ReportEntryInteger cloneObject() {
        ReportEntryInteger clone = new ReportEntryInteger();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getValue.
     *
     * @return the ReportInteger.
     */
    public ReportInteger getValue() {
        return this.value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the ReportInteger.
     */
    public void setValue(ReportInteger value) {
        this.value = value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the Integer.
     */
    public void setValue(Integer value) {
        if ((this.value == null)) {
            this.value = new ReportInteger();
        }
        this.value.setValue(value);
    }
}
