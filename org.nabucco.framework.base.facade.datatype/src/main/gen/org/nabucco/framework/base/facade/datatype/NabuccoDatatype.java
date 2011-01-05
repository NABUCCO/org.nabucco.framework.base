/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Version;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * NabuccoDatatype<p/>Common datatype for all NABUCCO datatypes, defines id and version<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-15
 */
public abstract class NabuccoDatatype extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "id", "version" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m0,1;", "l0,n;m0,1;" };

    /** Identifier for all datatypes, represents DB foreign key column */
    private Identifier id;

    /** Version for all datatypes, represents DB version column */
    private Version version;

    /** Constructs a new NabuccoDatatype instance. */
    public NabuccoDatatype() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the NabuccoDatatype.
     */
    protected void cloneObject(NabuccoDatatype clone) {
        super.cloneObject(clone);
        clone.setId(this.getId());
        clone.setVersion(this.getVersion());
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Identifier>(PROPERTY_NAMES[0], Identifier.class,
                PROPERTY_CONSTRAINTS[0], this.id));
        properties.add(new BasetypeProperty<Version>(PROPERTY_NAMES[1], Version.class,
                PROPERTY_CONSTRAINTS[1], this.version));
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
        final NabuccoDatatype other = ((NabuccoDatatype) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.version == null)) {
            if ((other.version != null))
                return false;
        } else if ((!this.version.equals(other.version)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.version == null) ? 0 : this.version.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<NabuccoDatatype>\n");
        appendable.append(super.toString());
        appendable.append((("<id>" + this.id) + "</id>\n"));
        appendable.append((("<version>" + this.version) + "</version>\n"));
        appendable.append("</NabuccoDatatype>\n");
        return appendable.toString();
    }

    @Override
    public abstract NabuccoDatatype cloneObject();

    /**
     * Identifier for all datatypes, represents DB foreign key column
     *
     * @return the Long.
     */
    public Long getId() {
        if ((this.id == null)) {
            this.id = new Identifier();
        }
        return this.id.getValue();
    }

    /**
     * Identifier for all datatypes, represents DB foreign key column
     *
     * @param id the Long.
     */
    public void setId(Long id) {
        if ((this.id == null)) {
            this.id = new Identifier();
        }
        this.id.setValue(id);
    }

    /**
     * Version for all datatypes, represents DB version column
     *
     * @return the Long.
     */
    public Long getVersion() {
        if ((this.version == null)) {
            this.version = new Version();
        }
        return this.version.getValue();
    }

    /**
     * Version for all datatypes, represents DB version column
     *
     * @param version the Long.
     */
    public void setVersion(Long version) {
        if ((this.version == null)) {
            this.version = new Version();
        }
        this.version.setValue(version);
    }
}
