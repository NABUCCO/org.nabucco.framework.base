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
package org.nabucco.framework.base.facade.datatype.exceptionmsg.field;

import java.io.Serializable;

/**
 * FieldMessage
 * <p/>
 * Defines error codes for properties defined by a property path.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class FieldMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String path;

    private final FieldMessageCodeType code;

    /**
     * Creates a new {@link FieldMessage} instance.
     * 
     * @param path
     *            path to the marked field
     * @param code
     *            type of the message
     */
    FieldMessage(String path, FieldMessageCodeType code) {
        this.path = path;
        this.code = code;
    }

    /**
     * Getter for the path.
     * 
     * @return Returns the path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Getter for the code.
     * 
     * @return Returns the code.
     */
    public FieldMessageCodeType getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.code);
        result.append(": [");
        result.append(this.path);
        result.append("]");
        return result.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.code == null) ? 0 : this.code.hashCode());
        result = prime * result + ((this.path == null) ? 0 : this.path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FieldMessage other = (FieldMessage) obj;
        if (this.code != other.code)
            return false;
        if (this.path == null) {
            if (other.path != null)
                return false;
        } else if (!this.path.equals(other.path))
            return false;
        return true;
    }
}
