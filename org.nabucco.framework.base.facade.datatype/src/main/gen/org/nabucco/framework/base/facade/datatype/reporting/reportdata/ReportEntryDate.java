/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.reporting.reportdata.ReportEntry;
import org.nabucco.framework.base.facade.datatype.reporting.reportdata.base.ReportDate;

/**
 * ReportEntryDate
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-10
 */
public class ReportEntryDate extends ReportEntry implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "value" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;" };

    private ReportDate value;

    /** Constructs a new ReportEntryDate instance. */
    public ReportEntryDate() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ReportEntryDate.
     */
    protected void cloneObject(ReportEntryDate clone) {
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
        properties.add(new BasetypeProperty<ReportDate>(PROPERTY_NAMES[0], ReportDate.class,
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
        final ReportEntryDate other = ((ReportEntryDate) obj);
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
        appendable.append("<ReportEntryDate>\n");
        appendable.append(super.toString());
        appendable.append((("<value>" + this.value) + "</value>\n"));
        appendable.append("</ReportEntryDate>\n");
        return appendable.toString();
    }

    @Override
    public ReportEntryDate cloneObject() {
        ReportEntryDate clone = new ReportEntryDate();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getValue.
     *
     * @return the ReportDate.
     */
    public ReportDate getValue() {
        return this.value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the ReportDate.
     */
    public void setValue(ReportDate value) {
        this.value = value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the java.util.Date.
     */
    public void setValue(java.util.Date value) {
        if ((this.value == null)) {
            this.value = new ReportDate();
        }
        this.value.setValue(value);
    }
}
