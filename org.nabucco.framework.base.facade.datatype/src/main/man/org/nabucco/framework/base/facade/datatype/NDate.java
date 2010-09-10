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
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.Date;

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
    }

    /**
     * Constructor initializing the value.
     * 
     * @param value
     *            the value to initialize
     */
    public NDate(Date value) {
        this.value = value;
    }

    @Override
    public Date getValue() {
        return value;
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

    /**
     * Returns a <code>String</code> object representing this <code>NDate</code>'s value.
     * 
     * @return a string representation of the value of this object in base&nbsp;10.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
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
     * @param basetype
     *            the cloned basetype
     */
    protected void cloneObject(NDate clone) {
        clone.value = (Date) this.value.clone();
    }

}
