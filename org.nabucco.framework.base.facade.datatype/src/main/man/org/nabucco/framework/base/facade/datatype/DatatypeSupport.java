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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.validation.DatatypeValidationVisitor;
import org.nabucco.framework.base.facade.datatype.validation.ValidationException;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.ValidationType;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatatypeSupport
 * 
 * @author Frank Ratschinski, Nicolas Moser, PRODYNA AG
 */
public abstract class DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    /** State of the Datatype. */
    private DatatypeState state;

    /** Container for inter-component relations. */
    private ComponentRelationContainer componentRelationContainer;

    /**
     * Creates a new {@link DatatypeSupport} instance.
     */
    public DatatypeSupport() {
        this.state = DatatypeState.CONSTRUCTED;
    }

    @Override
    public final DatatypeState getDatatypeState() {
        return this.state;
    }

    @Override
    public final void setDatatypeState(DatatypeState newState) {

        // TODO check state transitions

        if (newState != null) {
            this.state = newState;
        }
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<state>").append(this.state.toString()).append("</state>\n");
        return appendable.toString();

    }

    @Override
    public void validate(ValidationResult result, ValidationType depth) throws ValidationException {
        if (result == null) {
            throw new IllegalArgumentException("Validation result is not valid [null].");
        }
        if (depth == null) {
            depth = ValidationType.DEEP;
        }

        try {
            DatatypeValidationVisitor visitor = new DatatypeValidationVisitor(result, depth);
            this.accept(visitor);
        } catch (VisitorException e) {
            throw new ValidationException("Error visiting datatype for validation.", e);
        }
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        return new ArrayList<NabuccoProperty<?>>();
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
        result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
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
        if (this.state == null) {
            if (other.state != null)
                return false;
        } else if (!this.state.equals(other.state))
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
        clone.state = this.state;

        if (this.componentRelationContainer != null) {
            clone.componentRelationContainer = this.componentRelationContainer.cloneObject();
        }
    }

    /**
     * Getter for the container of inter-component relations.
     * 
     * @return the component relation conteiner
     */
    final ComponentRelationContainer getComponentRelationContainer() {
        if (this.componentRelationContainer == null) {
            this.componentRelationContainer = new ComponentRelationContainer();
        }
        return this.componentRelationContainer;
    }
}
