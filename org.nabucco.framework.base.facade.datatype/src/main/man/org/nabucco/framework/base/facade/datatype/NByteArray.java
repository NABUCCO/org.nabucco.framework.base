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

import java.util.Arrays;

/**
 * NByteArray
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class NByteArray extends BasetypeSupport implements Basetype, Comparable<NByteArray> {

    private static final long serialVersionUID = 1L;

    private byte[] value;

    /**
     * Default constructor
     */
    public NByteArray() {
        this(null);
    }

    /**
     * Constructor initializing the value.
     * 
     * @param value
     *            the value to initialize
     */
    public NByteArray(byte[] value) {
        super(BasetypeType.BYTE_ARRAY);
        this.value = value;
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        if (this.value == null) {
            return null;
        }
        return new String(this.value);
    }

    @Override
    public void setValue(Object value) throws IllegalArgumentException {
        if (value != null && !(value instanceof byte[])) {
            throw new IllegalArgumentException("Cannot set value '" + value + "' to NByteArray.");
        }
        this.setValue((byte[]) value);
    }

    /**
     * Setter for the byte[] value.
     * 
     * @param value
     *            the byte[] value to set.
     */
    public void setValue(byte[] value) {
        this.value = value;
    }

    /**
     * Returns a <code>String</code> object representing this <code>NByteArray</code>'s value.
     * 
     * @return a string representation of the value of this object in base&nbsp;10.
     */
    @Override
    public String toString() {
        if (this.value == null) {
            return null;
        }
        return new String(this.value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.value);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof NByteArray))
            return false;
        NByteArray other = (NByteArray) obj;
        if (!Arrays.equals(this.value, other.value))
            return false;
        return true;
    }

    @Override
    public int compareTo(NByteArray other) {
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

        if (this.value.length != other.value.length) {
            if (this.value.length > other.value.length) {
                return 1;
            }
            if (this.value.length < other.value.length) {
                return -1;
            }
        }

        for (int i = 0; i < this.value.length; i++) {
            if (this.value[i] != other.value[i]) {
                if (this.value[i] > other.value[i]) {
                    return 1;
                }
                if (this.value[i] < other.value[i]) {
                    return -1;
                }
            }
        }

        return 0;
    }

    @Override
    public abstract NByteArray cloneObject();

    /**
     * Clones the properties of this basetype into the given basetype.
     * 
     * @param clone
     *            the cloned basetype
     */
    protected void cloneObject(NByteArray clone) {
        if (this.value != null) {
            clone.value = Arrays.copyOf(this.value, this.value.length);
        }
    }

}
