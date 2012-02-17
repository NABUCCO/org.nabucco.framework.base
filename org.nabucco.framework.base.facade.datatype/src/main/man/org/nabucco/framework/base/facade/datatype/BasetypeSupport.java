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

import java.util.LinkedHashSet;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.validation.ValidationException;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.ValidationType;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * BasetypeSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class BasetypeSupport implements Basetype {

    private static final long serialVersionUID = 1L;

    private BasetypeType type;

    /**
     * Creates a new {@link BasetypeSupport} instance.
     * 
     * @param type
     *            the basetype type
     */
    public BasetypeSupport(BasetypeType type) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot create Basetype for type [null].");
        }
        this.type = type;
    }

    @Override
    public final BasetypeType getType() {
        return this.type;
    }

    @Override
    public void validate(ValidationResult result, ValidationType depth) throws ValidationException {
        if (result == null) {
            throw new IllegalArgumentException("Validation result is not valid [null].");
        }

        Set<NabuccoProperty> properties = this.getProperties();
        for (NabuccoProperty property : properties) {
            property.getConstraints().check(this, property, result);
        }
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        visitor.visit(this);
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
    public boolean setProperty(NabuccoProperty property) {
        if (property.getPropertyType() == NabuccoPropertyType.BASETYPE) {
            Basetype basetype = ((BasetypeProperty) property).getInstance();
            if (basetype != null) {
                this.setValue(basetype.getValue());
                return true;
            }
        }
        return false;
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
        return descriptor.createProperty(this, instance, null, null);
    }

    @Override
    public final void addConstraint(Constraint constraint, boolean derive) {
        throw new UnsupportedOperationException("Basetypes must not have dynamic constraints.");
    }

    @Override
    public final void removeConstraint(Constraint constraint, boolean derive) {
        throw new UnsupportedOperationException("Basetypes must not have dynamic constraints.");
    }

    @Override
    public final void addConstraint(NabuccoPropertyDescriptor property, Constraint constraint) {
        throw new UnsupportedOperationException("Basetypes must not have dynamic constraints.");
    }

    @Override
    public final void removeConstraint(NabuccoPropertyDescriptor property, Constraint constraint) {
        throw new UnsupportedOperationException("Basetypes must not have dynamic constraints.");
    }

    @Override
    public boolean editable() {
        throw new UnsupportedOperationException("Basetypes must not have dynamic constraints.");
    }

    @Override
    public boolean visible() {
        throw new UnsupportedOperationException("Basetypes must not have dynamic constraints.");
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return new LinkedHashSet<NabuccoProperty>();
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(this.getValue());
    }

}
