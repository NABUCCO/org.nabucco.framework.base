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
package org.nabucco.framework.base.facade.datatype.property;

import org.nabucco.framework.base.facade.datatype.validation.constraint.ConstraintFacade;
import org.nabucco.framework.base.facade.datatype.validation.constraint.dynamic.DynamicConstraintProperty;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintType;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.EditableConstraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.VisibilityConstraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintContainer;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintParser;

/**
 * PropertySupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class PropertySupport implements NabuccoPropertyDescriptor, NabuccoProperty, DynamicConstraintProperty {

    /** The static information of this property. */
    private NabuccoPropertyDescriptor descriptor;

    /** The dynamic constraints of the datatype instance. */
    private String dynamicConstraints;

    /** The parent of this property. */
    private PropertyOwner parent;

    /** The reference ID of the component relation. */
    private Long refId;

    /**
     * Creates a new {@link PropertySupport} instance.
     * 
     * @param descriptor
     *            the delegating descriptor
     * @param parent
     *            the property owner
     * @param dynamicConstraints
     *            the dynamic constraints of the property instance.
     * @param refId
     *            the component ref id
     */
    PropertySupport(NabuccoPropertyDescriptor descriptor, PropertyOwner parent, String dynamicConstraints, Long refId) {
        if (descriptor == null) {
            throw new IllegalArgumentException("Cannot create Property for descriptor [null].");
        }

        this.parent = parent;
        this.descriptor = descriptor;
        this.dynamicConstraints = dynamicConstraints;
        this.refId = refId;
    }

    @Override
    public PropertyOwner getParent() {
        return this.parent;
    }

    @Override
    public final String getName() {
        return this.descriptor.getName();
    }

    @Override
    public final Class<?> getType() {
        return this.descriptor.getType();
    }

    @Override
    public int getIndex() {
        return this.descriptor.getIndex();
    }

    @Override
    public final ConstraintContainer getConstraints() {
        ConstraintContainer staticConstraints = this.descriptor.getConstraints();
        ConstraintContainer dynamicConstraints = ConstraintParser.getInstance()
                .parseConstraintsWithIndex(this.dynamicConstraints).get(this.descriptor.getIndex());
        ConstraintContainer parentConstraints = ConstraintParser.getInstance()
                .parseConstraintsWithIndex(this.dynamicConstraints).get(ConstraintFacade.ROOT_INDEX);

        if (dynamicConstraints != null) {
            staticConstraints.addAll(dynamicConstraints);
        }

        if (parentConstraints != null) {
            staticConstraints.addAll(parentConstraints);
        }

        return staticConstraints;
    }

    @Override
    public final NabuccoPropertyType getPropertyType() {
        return this.descriptor.getPropertyType();
    }

    @Override
    public Long getReferenceId() {
        return this.refId;
    }

    @Override
    public final PropertyAssociationType getAssociationType() {
        return this.descriptor.getAssociationType();
    }

    @Override
    public String getCodePath() {
        return this.descriptor.getCodePath();
    }

    @Override
    public boolean isTechnical() {
        return this.descriptor.isTechnical();
    }

    @Override
    public boolean isCollection() {
        return this.descriptor.isCollection();
    }

    @Override
    public NabuccoProperty createProperty(Object instance) {
        return this.createProperty(this.parent, instance, this.dynamicConstraints, this.refId);
    }

    @Override
    public NabuccoProperty createProperty(PropertyOwner parent, Object instance, String dynamicConstraints) {
        return this.descriptor.createProperty(parent, instance, dynamicConstraints);
    }

    @Override
    public NabuccoProperty createProperty(PropertyOwner parent, Object instance, String dynamicConstraints, Long refId) {
        return this.descriptor.createProperty(parent, instance, dynamicConstraints, refId);
    }

    @Override
    public boolean isEditable() {
        ConstraintContainer constraintContainer = this.getDynamicConstraints();

        if (constraintContainer == null) {
            return true;
        }

        for (Constraint constraint : constraintContainer) {
            if (constraint.getType() == ConstraintType.EDIT) {
                return ((EditableConstraint) constraint).isEditable();
            }
        }
        return true;
    }

    @Override
    public boolean isVisible() {
        ConstraintContainer constraintContainer = this.getDynamicConstraints();

        if (constraintContainer == null) {
            return true;
        }

        for (Constraint constraint : constraintContainer) {
            if (constraint.getType() == ConstraintType.VISIBILITY) {
                return ((VisibilityConstraint) constraint).isVisible();
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(this.descriptor.toString() + " : " + this.getInstance());
    }

    /**
     * Resolve the dynamic constraints for the given string.
     * 
     * @return the constraint container holding the dynamic constraints
     */
    private ConstraintContainer getDynamicConstraints() {
        if (this.dynamicConstraints == null) {
            return null;
        }
        return ConstraintParser.getInstance().parseConstraints(this.dynamicConstraints);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.descriptor == null) ? 0 : this.descriptor.hashCode());
//        result = prime * result + ((this.parent == null) ? 0 : this.parent.hashCode());
        result = prime * result + ((this.refId == null) ? 0 : this.refId.hashCode());
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
        PropertySupport other = (PropertySupport) obj;
        if (this.descriptor == null) {
            if (other.descriptor != null)
                return false;
        } else if (!this.descriptor.equals(other.descriptor))
            return false;
//        if (this.parent == null) {
//            if (other.parent != null)
//                return false;
//        } else if (!this.parent.equals(other.parent))
//            return false;
        if (this.refId == null) {
            if (other.refId != null)
                return false;
        } else if (!this.refId.equals(other.refId))
            return false;
        return true;
    }

}
