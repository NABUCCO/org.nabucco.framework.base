/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.property.ListProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.reporting.reportdata.ReportData;

/**
 * ReportDataList
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-10
 */
public class ReportDataList extends ReportData implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "reportDataList" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    private List<ReportData> reportDataList;

    private Long reportDataListRefId;

    /** Constructs a new ReportDataList instance. */
    public ReportDataList() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ReportDataList.
     */
    protected void cloneObject(ReportDataList clone) {
        super.cloneObject(clone);
        if ((this.reportDataList instanceof NabuccoList<?>)) {
            clone.reportDataList = ((NabuccoList<ReportData>) this.reportDataList)
                    .cloneCollection();
        }
    }

    /**
     * Getter for the ReportDataListJPA.
     *
     * @return the List<ReportData>.
     */
    List<ReportData> getReportDataListJPA() {
        if ((this.reportDataList == null)) {
            this.reportDataList = new NabuccoList<ReportData>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoList<ReportData>) this.reportDataList).getDelegate();
    }

    /**
     * Setter for the ReportDataListJPA.
     *
     * @param reportDataList the List<ReportData>.
     */
    void setReportDataListJPA(List<ReportData> reportDataList) {
        if ((this.reportDataList == null)) {
            this.reportDataList = new NabuccoList<ReportData>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoList<ReportData>) this.reportDataList).setDelegate(reportDataList);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new ListProperty<ReportData>(PROPERTY_NAMES[0], ReportData.class,
                PROPERTY_CONSTRAINTS[0], this.reportDataList));
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
        final ReportDataList other = ((ReportDataList) obj);
        if ((this.reportDataListRefId == null)) {
            if ((other.reportDataListRefId != null))
                return false;
        } else if ((!this.reportDataListRefId.equals(other.reportDataListRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.reportDataListRefId == null) ? 0
                : this.reportDataListRefId.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<ReportDataList>\n");
        appendable.append(super.toString());
        appendable
                .append((("<reportDataListRefId>" + this.reportDataListRefId) + "</reportDataListRefId>\n"));
        appendable.append("</ReportDataList>\n");
        return appendable.toString();
    }

    @Override
    public ReportDataList cloneObject() {
        ReportDataList clone = new ReportDataList();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getReportDataList.
     *
     * @return the List<ReportData>.
     */
    public List<ReportData> getReportDataList() {
        if ((this.reportDataList == null)) {
            this.reportDataList = new NabuccoList<ReportData>(NabuccoCollectionState.INITIALIZED);
        }
        return this.reportDataList;
    }

    /**
     * Getter for the ReportDataListRefId.
     *
     * @return the Long.
     */
    public Long getReportDataListRefId() {
        return this.reportDataListRefId;
    }

    /**
     * Setter for the ReportDataListRefId.
     *
     * @param reportDataListRefId the Long.
     */
    public void setReportDataListRefId(Long reportDataListRefId) {
        this.reportDataListRefId = reportDataListRefId;
    }
}
