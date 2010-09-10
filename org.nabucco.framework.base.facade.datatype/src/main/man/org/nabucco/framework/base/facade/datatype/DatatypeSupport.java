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

import java.io.Serializable;

import org.nabucco.framework.base.facade.datatype.validation.DatatypeValidationVisitor;
import org.nabucco.framework.base.facade.datatype.validation.ValidationException;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatatypeSupport
 * 
 * @author Frank Ratschinski, Nicolas Moser, PRODYNA AG
 */
public abstract class DatatypeSupport implements Serializable, Datatype {

    private static final long serialVersionUID = 1L;

    private DatatypeState datatypeState;

    public DatatypeSupport() {
        this.datatypeState = DatatypeState.CONSTRUCTED;
    }

    @Override
    public DatatypeState getDatatypeState() {
        return this.datatypeState;
    }

    @Override
    public void setDatatypeState(DatatypeState newState) {

        switch (this.datatypeState) {

        case CONSTRUCTED: {
            if (newState == DatatypeState.INITIALIZED) {
                this.datatypeState = newState;
            }
            break;
        }

        case INITIALIZED: {
            if (newState == DatatypeState.PERSISTENT || newState == DatatypeState.TRANSIENT) {
                this.datatypeState = newState;
            }
            break;
        }

        case PERSISTENT: {
            if (newState == DatatypeState.DELETED || newState == DatatypeState.TRANSIENT) {
                this.datatypeState = newState;
            }
            break;
        }
        case TRANSIENT: {
            if (newState == DatatypeState.PERSISTENT || newState == DatatypeState.DELETED) {
                this.datatypeState = newState;
            }
            break;
        }
        case DELETED: {
            break;
        }
        }

        // TODO check state transitions
        this.datatypeState = newState;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<datatypeState>").append(this.datatypeState.toString())
                .append("</datatypeState>\n");
        return appendable.toString();

    }

    @Override
    public void validate(ValidationResult result) throws ValidationException {
        if (result == null) {
            throw new IllegalArgumentException("Validation result is not valid [null].");
        }

        try {
            DatatypeValidationVisitor visitor = new DatatypeValidationVisitor(result);
            this.accept(visitor);
        } catch (VisitorException e) {
            throw new ValidationException("Error visiting datatype for validation.", e);
        }
    }

    @Override
    public String[] getConstraints() {
        return null;
    }

    @Override
    public Object[] getProperties() {
        return null;
    }

    @Override
    public String[] getPropertyNames() {
        return null;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        if (!visitor.hasVisited(this)) {
            visitor.visit(this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result + ((this.datatypeState == null) ? 0 : this.datatypeState.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof DatatypeSupport))
            return false;
        DatatypeSupport other = (DatatypeSupport) obj;
        if (this.datatypeState == null) {
            if (other.datatypeState != null)
                return false;
        } else if (!this.datatypeState.equals(other.datatypeState))
            return false;
        return true;
    }

    /**
     * Clones the datatypes properties.
     * 
     * @param clone
     *            the datatype clone
     */
    protected void cloneObject(DatatypeSupport clone) {
        clone.datatypeState = this.datatypeState;
    }
}
