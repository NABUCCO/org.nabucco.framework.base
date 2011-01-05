/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.reporting.reportdata.ReportEntry;
import org.nabucco.framework.base.facade.datatype.reporting.reportdata.base.ReportBoolean;

/**
 * ReportEntryBoolean
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-10
 */
public class ReportEntryBoolean extends ReportEntry implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "value" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;" };

    private ReportBoolean value;

    /** Constructs a new ReportEntryBoolean instance. */
    public ReportEntryBoolean() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ReportEntryBoolean.
     */
    protected void cloneObject(ReportEntryBoolean clone) {
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
        properties.add(new BasetypeProperty<ReportBoolean>(PROPERTY_NAMES[0], ReportBoolean.class,
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
        final ReportEntryBoolean other = ((ReportEntryBoolean) obj);
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
        appendable.append("<ReportEntryBoolean>\n");
        appendable.append(super.toString());
        appendable.append((("<value>" + this.value) + "</value>\n"));
        appendable.append("</ReportEntryBoolean>\n");
        return appendable.toString();
    }

    @Override
    public ReportEntryBoolean cloneObject() {
        ReportEntryBoolean clone = new ReportEntryBoolean();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getValue.
     *
     * @return the ReportBoolean.
     */
    public ReportBoolean getValue() {
        return this.value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the ReportBoolean.
     */
    public void setValue(ReportBoolean value) {
        this.value = value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the Boolean.
     */
    public void setValue(Boolean value) {
        if ((this.value == null)) {
            this.value = new ReportBoolean();
        }
        this.value.setValue(value);
    }
}
