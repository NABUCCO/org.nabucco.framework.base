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
import java.util.List;
import java.util.Set;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationTypeProxy;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociation;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.json.JsonSerializer;
import org.nabucco.framework.base.facade.datatype.validation.DatatypeValidationVisitor;
import org.nabucco.framework.base.facade.datatype.validation.ValidationException;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.ValidationType;
import org.nabucco.framework.base.facade.datatype.validation.constraint.ConstraintFacade;
import org.nabucco.framework.base.facade.datatype.validation.constraint.ConstraintVisitor;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * Abstract super class for all NABUCCO datatypes.
 * 
 * @see Datatype
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    /** State of the Datatype. */
    private DatatypeState state;

    /** Container for inter-component relations. */
    private ComponentRelationContainer componentRelationContainer;

    /** Dynamic Constraints of the datatype. */
    private String dynamicConstraints;

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DatatypeSupport.class);

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
        if (newState != null) {
            this.state = newState;
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
            DatatypeValidationVisitor visitor = new DatatypeValidationVisitor(result, depth);
            this.accept(visitor);
        } catch (VisitorException e) {
            throw new ValidationException("Error visiting datatype for validation.", e);
        }
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = new LinkedHashSet<NabuccoProperty>();
        this.addComponentRelationProperties(properties);
        return properties;
    }

    /**
     * Add the component relation properties configured in the business schema extension point.
     * 
     * @param properties
     *            the property list to add the component relation propeties
     */
    private void addComponentRelationProperties(Set<NabuccoProperty> properties) {

        String className = this.getClass().getSimpleName();

        try {
            ExtensionMap extensions = NabuccoSystem.getExtensionResolver().resolveExtensions(
                    ExtensionPointType.ORG_NABUCCO_BUSINESS_SCHEMA);

            for (String extensionId : extensions.getExtensionNames()) {

                BusinessSchemaExtension extension = (BusinessSchemaExtension) extensions.getExtension(extensionId);

                for (int i = 0; i < extension.getAssociations().size(); i++) {
                    BusinessSchemaAssociation association = extension.getAssociations().get(i);

                    // Create new Proxy!
                    ComponentRelationTypeProxy proxy = new ComponentRelationTypeProxy(extension, association);

                    if (proxy.getSource() != this.getClass()) {
                        continue;
                    }

                    // Load ComponentRelations List!
                    List<ComponentRelation<?>> relations = this.getComponentRelationContainer().getComponentRelations(
                            proxy);

                    // Resolve ComponentRelation Attributes!
                    String name = proxy.getName();
                    Class<? extends ComponentRelation<?>> type = proxy.getRelation();
                    String constraints = proxy.getConstraints();

                    // Shifting component relation index by 1000!
                    int index = 1000 + i;

                    PropertyAssociationType associationType = PropertyLoader.loadProperty(
                            PropertyAssociationType.class, association.getType());

                    // Create Property Descriptor!
                    PropertyDescriptorSupport descriptor = PropertyDescriptorSupport.createComponentRelation(name,
                            type, index, constraints, associationType);

                    // Create and Add Property!
                    ComponentRelationProperty property = (ComponentRelationProperty) descriptor.createProperty(this,
                            relations, this.dynamicConstraints);

                    property.setComponentRelationType(proxy);

                    properties.add(property);
                }
            }

        } catch (ExtensionException ee) {
            logger.debug("No business schema extension configured for '", className, "'.");
        } catch (Exception e) {
            logger.error(e, "Error resolving business schema extensions for '", className, "'.");
            throw new RuntimeException("Error resolving business schema extensions for '" + className + "'.", e);
        }
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
        if (property == null) {
            return false;
        }
        if (property.getPropertyType() != NabuccoPropertyType.COMPONENT_RELATION) {
            return false;
        }

        return this.setComponentRelationProperty((ComponentRelationProperty) property);
    }

    /**
     * Sets the component relation property.
     * 
     * @param property
     *            the component relation property to set
     */
    private boolean setComponentRelationProperty(ComponentRelationProperty property) {
        NabuccoList<ComponentRelation<?>> relations = property.getInstance();

        String className = this.getClass().getSimpleName();

        try {
            // Add ComponentRelation Properties configured in Business Schema Extension!
            BusinessSchemaExtension extension = (BusinessSchemaExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_BUSINESS_SCHEMA, className);

            for (BusinessSchemaAssociation association : extension.getAssociations()) {

                // Create new Proxy!
                ComponentRelationTypeProxy proxy = new ComponentRelationTypeProxy(extension, association);

                if (proxy.getName().equals(property.getName())) {

                    // Sets the Component Relations!
                    this.getComponentRelationContainer().setComponentRelations(proxy, relations);
                    return true;
                }
            }

        } catch (ExtensionException ee) {
            logger.debug("No business schema extension configured for '", className, "'.");
        } catch (Exception e) {
            logger.error(e, "Error resolving business schema extensions for '", className, "'.");
        }

        return false;
    }

    @Override
    public final void accept(Visitor visitor) throws VisitorException {
        if (!visitor.hasVisited(this)) {
            visitor.visit(this);
        }
    }

    @Override
    public final void addConstraint(Constraint constraint, boolean derive) throws VisitorException {
        this.dynamicConstraints = ConstraintFacade.getInstance().addConstraint(constraint, this.dynamicConstraints);

        if (derive) {
            this.accept(new ConstraintVisitor(constraint));
        }
    }

    @Override
    public final void addConstraint(NabuccoPropertyDescriptor property, Constraint constraint) {
        this.dynamicConstraints = ConstraintFacade.getInstance().addConstraint(property, constraint,
                this.dynamicConstraints);
    }

    @Override
    public final void removeConstraint(Constraint constraint, boolean derive) throws VisitorException {
        this.dynamicConstraints = ConstraintFacade.getInstance().removeConstraint(constraint, this.dynamicConstraints);

        if (derive) {
            this.accept(new ConstraintVisitor(constraint));
        }
    }

    @Override
    public final void removeConstraint(NabuccoPropertyDescriptor property, Constraint constraint) {
        this.dynamicConstraints = ConstraintFacade.getInstance().removeConstraint(property, constraint,
                this.dynamicConstraints);
    }

    @Override
    public boolean editable() {
        return ConstraintFacade.getInstance().isEditable(this.dynamicConstraints);
    }

    @Override
    public boolean visible() {
        return ConstraintFacade.getInstance().isVisible(this.dynamicConstraints);
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
     * Create the dynamic property depending on the property descriptor and instance.
     * 
     * @param descriptor
     *            the static property descriptor
     * @param instance
     *            the property instance
     * @param refId
     *            the reference id
     * 
     * @return the new created property
     */
    protected final NabuccoProperty createProperty(NabuccoPropertyDescriptor descriptor, NType instance, Long refId) {
        return descriptor.createProperty(this, instance, this.dynamicConstraints, refId);
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

    /**
     * Setter for the container of inter-component relations.
     * 
     * @param container
     *            the component relation conteiner
     */
    final void setComponentRelationContainer(ComponentRelationContainer container) {
        this.componentRelationContainer = container;
    }

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
        result = prime
                * result + ((this.componentRelationContainer == null) ? 0 : this.componentRelationContainer.hashCode());
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
        if (this.state != other.state)
            return false;
        if (this.componentRelationContainer == null) {
            if (other.componentRelationContainer != null)
                return false;
        } else if (!this.componentRelationContainer.equals(other.componentRelationContainer))
            return false;
        return true;
    }

}
