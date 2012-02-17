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

import java.math.BigDecimal;

/**
 * NDecimal
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class NDecimal extends BasetypeSupport implements Basetype, Comparable<NDecimal> {

    private static final long serialVersionUID = 1L;

    private BigDecimal value;

    /**
     * Creates a new {@link NDecimal} instance.
     */
    public NDecimal() {
        this(null);
    }

    /**
     * Creates a new {@link NDecimal} instance.
     * 
     * @param value
     */
    public NDecimal(BigDecimal value) {
        super(BasetypeType.DECIMAL);
        this.value = value;
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) throws IllegalArgumentException {
        if (value != null && !(value instanceof BigDecimal)) {
            throw new IllegalArgumentException("Cannot set value '" + value + "' to NDecimal.");
        }
        this.setValue((BigDecimal) value);
    }

    /**
     * @param value
     *            The value to set.
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Returns a <code>String</code> object representing this <code>NDecimal</code>'s value.
     * 
     * @return a string representation of the value of this object in base&nbsp;10.
     */
    @Override
    public String toString() {
        return String.valueOf(this.value);
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NDecimal)) {
            return false;
        }
        NDecimal other = (NDecimal) obj;
        if (this.value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!this.value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(NDecimal other) {
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

        return this.getValue().compareTo(other.getValue());
    }

    @Override
    public abstract NDecimal cloneObject();

    /**
     * Clones the properties of this basetype into the given basetype.
     * 
     * @param clone
     *            the cloned basetype
     */
    protected void cloneObject(NDecimal clone) {
        clone.value = this.value;
    }

}
