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
package org.nabucco.framework.base.facade.datatype.date;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.date.PeriodType;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorUtility;


/**
 * Period<p/>A specific time<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-26
 */
public class Period extends NabuccoDatatype implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m1,1;", "m1,1;" };

    private static final long serialVersionUID = 1L;

    /** The count of units specified by periodType */
    private Number count;

    /** The type of period units */
    private PeriodType periodType;

    /** Constructs a new Period instance. */
    public Period() {
        super();
        this.initDefaults();
    }

    /**
     * CloneObject.
     *
     * @param clone the Period.
     */
    protected void cloneObject(Period clone) {
        super.cloneObject(clone);
        if ((this.count != null)) {
            clone.count = this.count.cloneObject();
        }
        clone.periodType = this.periodType;
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    @Override
    public Period cloneObject() {
        Period clone = new Period();
        this.cloneObject(clone);
        return clone;
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public String[] getConstraints() {
        return CONSTRAINTS.clone();
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
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(),
                new String[] { "count", "periodType" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(super.getProperties(),
                new Object[] { this.getCount(), this.getPeriodType() });
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
