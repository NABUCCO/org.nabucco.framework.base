/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.Date;

import org.nabucco.framework.base.facade.datatype.converter.NabuccoConverter;

/**
 * NDate
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public abstract class NDate extends BasetypeSupport implements Basetype, Comparable<NDate> {

    private static final long serialVersionUID = 1L;

    private Date value;

    /**
     * Default constructor
     */
    public NDate() {
        this(null);
    }

    /**
     * Constructor initializing the value.
     * 
     * @param value
     *            the value to initialize
     */
    public NDate(Date value) {
        super(BasetypeType.DATE);
        this.value = value;
    }

    /**
     * Setter for the date value.
     * 
     * @param value
     *            the date value to set
     */
    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public void setValue(Object value) throws IllegalArgumentException {
        if (value != null && !(value instanceof Date)) {
            throw new IllegalArgumentException("Cannot set value '" + value + "' to NDate.");
        }
        this.setValue((Date) value);
    }

    @Override
    public Date getValue() {
        return value;
    }

    /**
     * Getter for JPA implementation since mapped sql type is used.
     * 
     * @return the value as instance of {@link java.sql.Date}.
     */
    Date getValueJPA() {
        return this.value;
    }

    /**
     * Setter accepting the mapped JPA Type {@link java.sql.Date}.
     * 
     * @param date
     *            an sql Date from JPA implementation.
     */
    void setValueJPA(Date date) {
        if (date == null) {
            this.value = null;
        } else {
            this.value = new Date(date.getTime());
        }
    }

    @Override
    public String getValueAsString() {
        if (this.value == null) {
            return null;
        }
        return NabuccoConverter.getInstance().getDateConverter().convertDate(this.value);
    }

    /**
     * Returns a <code>String</code> object representing this <code>NDate</code>'s value.
     * 
     * @return a string representation of the value of this object in base&nbsp;10.
     */
    @Override
    public String toString() {
        return NabuccoConverter.getInstance().getDateConverter().convertDate(this.value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof NDate))
            return false;
        NDate other = (NDate) obj;
        if (this.value == null) {
            if (other.value != null)
                return false;
        } else if (!this.value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public int compareTo(NDate other) {
        if (other == null) {
            return -1;
        }
        if (getValue() == null) {
            if (other.getValue() == null) {
                return 0;
            }
            return 1;
        }
        if (other.getValue() == null) {
            return -1;
        }

        return getValue().compareTo(other.getValue());
    }

    @Override
    public abstract NDate cloneObject();

    /**
     * Clones the properties of this basetype into the given basetype.
     * 
     * @param clone
     *            the cloned basetype
     */
    protected void cloneObject(NDate clone) {
        if (this.value != null) {
            clone.value = (Date) this.value.clone();
        }
    }

}
