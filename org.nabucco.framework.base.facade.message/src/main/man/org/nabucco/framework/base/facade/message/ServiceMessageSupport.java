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
package org.nabucco.framework.base.facade.message;

import java.util.LinkedHashSet;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.json.JsonSerializer;
import org.nabucco.framework.base.facade.datatype.validation.ValidationException;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.ValidationType;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.message.validation.MessageConstraintValidationVisitor;

/**
 * ServiceMessageSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private String dynamicConstraints;

    @Override
    public String toString() {
        try {
            JsonSerializer serializer = JsonSerializer.getInstance();
            return serializer.serialize(this).getContent();
        } catch (SerializationException se) {
            throw new RuntimeException(se);
        }
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
            MessageConstraintValidationVisitor visitor = new MessageConstraintValidationVisitor(result, depth);
            this.accept(visitor);
        } catch (VisitorException e) {
            throw new ValidationException("Error visiting message for validation.", e);
        }
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        visitor.visit(this);
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return new LinkedHashSet<NabuccoProperty>();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        return false;
    }

    @Override
    public NabuccoProperty getProperty(String name) {
        for (NabuccoProperty property : this.getProperties()) {
            if (property.getName().equals(name)) {
                return property;
            }
        }
        return null;
    }

    @Override
    public final void addConstraint(Constraint constraint, boolean derive) {
        throw new UnsupportedOperationException("Dynamic constraints are not supported for ServiceMessages yet.");
    }

    @Override
    public final void addConstraint(NabuccoPropertyDescriptor property, Constraint constraint) {
        throw new UnsupportedOperationException("Dynamic constraints are not supported for ServiceMessages yet.");
    }

    @Override
    public final void removeConstraint(Constraint constraint, boolean derive) {
        throw new UnsupportedOperationException("Dynamic constraints are not supported for ServiceMessages yet.");
    }

    @Override
    public final void removeConstraint(NabuccoPropertyDescriptor property, Constraint constraint) {
        throw new UnsupportedOperationException("Dynamic constraints are not supported for ServiceMessages yet.");
    }

    @Override
    public boolean editable() {
        throw new UnsupportedOperationException("Dynamic constraints are not supported for ServiceMessages yet.");
    }

    @Override
    public boolean visible() {
        throw new UnsupportedOperationException("Dynamic constraints are not supported for ServiceMessages yet.");
    }

    /**
     * Create the dynamic property depending on the property descriptor and instance.
     * 
     * @param descriptor
     *            the static property descriptor
     * @param instance
     *            the property instance
     * 
     * @return the new created property
     */
    protected final NabuccoProperty createProperty(NabuccoPropertyDescriptor descriptor, NType instance) {
        return descriptor.createProperty(this, instance, this.dynamicConstraints);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

}
