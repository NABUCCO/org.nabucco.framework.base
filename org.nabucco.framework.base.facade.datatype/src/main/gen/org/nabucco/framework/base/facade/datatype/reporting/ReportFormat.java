/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * ReportFormat<p/>Format of a Report<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-10
 */
public enum ReportFormat implements Enumeration {
    HTML("html"),
    PDF("pdf"),
    RTF("rtf"),
    DOC("doc"),
    DOCX("docx"),
    PNG("png"),
    CSV("cvs"),
    XLS("xls"),
    XLSX("xlsx"), ;

    private String id;

    /**
     * Constructs a new ReportFormat instance.
     *
     * @param id the String.
     */
    ReportFormat(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getOrdinal() {
        return this.ordinal();
    }

    @Override
    public Enumeration cloneObject() {
        return this;
    }
}
