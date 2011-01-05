/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * ReportData
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-11
 */
public abstract class ReportData extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "key" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;" };

    private Name key;

    /** Constructs a new ReportData instance. */
    public ReportData() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ReportData.
     */
    protected void cloneObject(ReportData clone) {
        super.cloneObject(clone);
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Name>(PROPERTY_NAMES[0], Name.class,
                PROPERTY_CONSTRAINTS[0], this.key));
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
        final ReportData other = ((ReportData) obj);
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<ReportData>\n");
        appendable.append(super.toString());
        appendable.append((("<key>" + this.key) + "</key>\n"));
        appendable.append("</ReportData>\n");
        return appendable.toString();
    }

    @Override
    public abstract ReportData cloneObject();

    /**
     * Missing description at method getKey.
     *
     * @return the Name.
     */
    public Name getKey() {
        return this.key;
    }

    /**
     * Missing description at method setKey.
     *
     * @param key the Name.
     */
    public void setKey(Name key) {
        this.key = key;
    }

    /**
     * Missing description at method setKey.
     *
     * @param key the String.
     */
    public void setKey(String key) {
        if ((this.key == null)) {
            this.key = new Name();
        }
        this.key.setValue(key);
    }
}
