/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Generated with NABUCCO Generator 
*/
package org.nabucco.framework.base.facade.datatype;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorUtility;

/**
 * NabuccoDatatype<p/>Common datatype for all NABUCCO datatypes, defines id and version<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-15
 */
public abstract class NabuccoDatatype extends DatatypeSupport implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m0,1;", "l0,n;m0,1;" };

    private static final long serialVersionUID = 1L;

    /** Identifier for all datatypes, represents DB foreign key column */
    private Identifier id;

    /** Version for all datatypes, represents DB version column */
    private Version version;

    /** Constructs a new NabuccoDatatype instance. */
    public NabuccoDatatype() {
        super();
        this.initDefaults();
    }

    /**
     * CloneObject.
     *
     * @param clone the NabuccoDatatype.
     */
    protected void cloneObject(NabuccoDatatype clone) {
        super.cloneObject(clone);
        if ((this.id != null)) {
            clone.id = this.id.cloneObject();
        }
        if ((this.version != null)) {
            clone.version = this.version.cloneObject();
        }
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    @Override
    public abstract NabuccoDatatype cloneObject();

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public String[] getConstraints() {
        return VisitorUtility.merge(super.getConstraints(), CONSTRAINTS);
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
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(), new String[] { "id", "version" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(super.getProperties(),
                new Object[] { this.getId(), this.getVersion() });
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
