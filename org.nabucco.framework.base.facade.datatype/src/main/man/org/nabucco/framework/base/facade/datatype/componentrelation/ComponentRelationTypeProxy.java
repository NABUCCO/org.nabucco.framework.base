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
package org.nabucco.framework.base.facade.datatype.componentrelation;

import java.util.Collections;
import java.util.Set;
import java.util.StringTokenizer;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.extension.business.schema.BusinessSchemaResolver;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociation;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaObject;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.MultiplicityType;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * Proxy for component relation type objects that are configured via extension point.
 * 
 * @see BusinessSchemaExtension
 * @see BusinessSchemaAssociation
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationTypeProxy implements ComponentRelationType {

    private static final long serialVersionUID = 1L;

    /** Id Seperator */
    private static final String SEPERATOR = ".";

    /** The Schema Extension */
    private final BusinessSchemaExtension extension;

    /** The Schema Association Extension */
    private final BusinessSchemaAssociation association;

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ComponentRelationTypeProxy.class);

    /**
     * Creates a new {@link ComponentRelationTypeProxy} instance.
     * 
     * @param extension
     *            the schema extension
     */
    public ComponentRelationTypeProxy(BusinessSchemaExtension extension, BusinessSchemaAssociation association) {
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create component relation for extension [null].");
        }
        if (association == null) {
            throw new IllegalArgumentException("Cannot create component relation for association [null].");
        }

        this.extension = extension;
        this.association = association;
    }

    /**
     * Getter for the business association name.
     * 
     * @return the business association name
     */
    public String getName() {
        return PropertyLoader.loadProperty(this.association.getName());
    }

    /**
     * Getter for the business association constraints.
     * 
     * @return the business association constraints
     */
    public String getConstraints() {
        MultiplicityType multiplicity = MultiplicityType.parse(PropertyLoader.loadProperty(this.association.getTarget()
                .getMultiplicity()));

        return multiplicity.toConstraintString();
    }

    @Override
    public Class<? extends NabuccoDatatype> getSource() {
        return this.loadClass(this.association.getSource().getId());
    }

    @Override
    public Class<? extends NabuccoDatatype> getTarget() {
        return this.loadClass(this.association.getTarget().getId());
    }

    /**
     * Load the class for the given {@link StringProperty}.
     * 
     * @param property
     *            the property holding the schema object id
     * 
     * @return the loaded datatype class
     */
    private Class<? extends NabuccoDatatype> loadClass(StringProperty property) {
        String id = PropertyLoader.loadProperty(property);

        for (BusinessSchemaObject schemaObject : this.extension.getObjects()) {

            String objectId = PropertyLoader.loadProperty(schemaObject.getId());

            if (objectId != null && objectId.equals(id)) {
                return PropertyLoader.loadProperty(schemaObject.getBusinessClass());
            }
        }
        return null;
    }

    @Override
    public Class<? extends ComponentRelation<?>> getRelation() {
        return PropertyLoader.loadProperty(this.association.getAssociationClass());
    }

    @Override
    public String getId() {
        StringBuilder id = new StringBuilder();
        id.append(PropertyLoader.loadProperty(this.extension.getIdentifier()));
        id.append(SEPERATOR);
        id.append(PropertyLoader.loadProperty(this.association.getName()));
        return id.toString();
    }

    @Override
    public int getOrdinal() {
        return 0;
    }

    @Override
    public Enumeration cloneObject() {
        return new ComponentRelationTypeProxy(this.extension, this.association);
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        // Nothing to visit!
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return Collections.emptySet();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.association == null) ? 0 : this.association.hashCode());
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
        if (!(obj instanceof ComponentRelationTypeProxy)) {
            return false;
        }

        ComponentRelationTypeProxy other = (ComponentRelationTypeProxy) obj;
        return this.association.equals(other.association);
    }

    /**
     * Creates a component relation type for the given id.
     * 
     * @param id
     *            the component relation type id
     * 
     * @return the component relation proxy
     */
    public static ComponentRelationTypeProxy valueOf(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalStateException("ComponentRelationType id 'null' is not valid.");
        }

        StringTokenizer tokenizer = new StringTokenizer(id, SEPERATOR);
        if (tokenizer.countTokens() != 2) {
            throw new IllegalStateException("ComponentRelationType id '" + id + "' is not valid.");
        }

        try {
            String extensionName = tokenizer.nextToken();
            String associationName = tokenizer.nextToken();
            BusinessSchemaResolver resolver = new BusinessSchemaResolver(extensionName);

            BusinessSchemaExtension extension = resolver.getExtension();
            BusinessSchemaAssociation association = resolver.getAssociation(associationName);

            return new ComponentRelationTypeProxy(extension, association);

        } catch (ExtensionException ee) {
            logger.error("ComponentRelationType id '", id, "' is not valid.", ee);
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("CR-Proxy '");
        result.append(this.getName());
        result.append("' (");
        result.append("Source: ");
        result.append(this.getSource().getSimpleName());
        result.append(", Target: ");
        result.append(this.getTarget().getSimpleName());
        result.append(")");
        return result.toString();
    }
}
