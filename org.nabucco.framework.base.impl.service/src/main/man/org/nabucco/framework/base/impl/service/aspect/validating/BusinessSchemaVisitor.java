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
package org.nabucco.framework.base.impl.service.aspect.validating;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeAccessor;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociation;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociationRole;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaObject;
import org.nabucco.framework.base.facade.datatype.relation.MultiplicityType;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.message.visitor.ServiceMessageVisitor;

/**
 * BusinessSchemaVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class BusinessSchemaVisitor extends ServiceMessageVisitor {

    public BusinessSchemaExtension extension;

    /**
     * Creates a new {@link BusinessSchemaVisitor} instance.
     * 
     * @param extension
     *            the business schema extension
     */
    public BusinessSchemaVisitor(BusinessSchemaExtension extension) {
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create Business Schema Visitor for extension [null].");
        }
        this.extension = extension;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {

        this.validate(datatype);

        super.visit(datatype);
    }

    /**
     * Validate the datatype.
     * 
     * @param datatype
     *            the datatype to validate
     */
    private void validate(Datatype datatype) throws VisitorException {

        Map<String, Integer> associationCount = new HashMap<String, Integer>();

        String sourceClass = datatype.getClass().getCanonicalName();
        String sourceId = this.getId(sourceClass);

        if (sourceId == null) {
            throw new VisitorException("Datatype '"
                    + sourceClass + "' is not configured in schema " + this.extension.getIdentifier() + ".");
        }

        for (ComponentRelation<?> relation : DatatypeAccessor.getInstance().getComponentRelation(datatype)
                .getAllComponentRelations()) {

            if (relation == null || relation.getTarget() == null) {
                continue;
            }

            String targetClass = relation.getTarget().getClass().getCanonicalName();
            String targetId = this.getId(targetClass);

            if (targetId == null) {
                throw new VisitorException("Datatype '"
                        + targetClass + "' is not configured in schema " + this.extension.getIdentifier() + ".");
            }

            BusinessSchemaAssociation association = this.findAssociation(sourceId, targetId);
            if (association == null) {
                throw new VisitorException("No association defined between '"
                        + sourceClass + "' and '" + targetClass + "' in schema " + this.extension.getIdentifier() + ".");
            }

            this.validate(association, associationCount, sourceClass, targetClass);
        }

    }

    /**
     * Validate the multiplicity against the configured schema.
     * 
     * @param association
     *            the association
     * @param associationCount
     *            the association count
     * @param source
     *            the source class
     * @param target
     *            the target class
     * 
     * @throws VisitorException
     *             when the validation fails
     */
    private void validate(BusinessSchemaAssociation association, Map<String, Integer> associationCount, String source,
            String target) throws VisitorException {

        int max = this.getMaxMultiplicity(association);

        Integer count = associationCount.get(association.getName().getValue().getValue());
        if (count == null) {
            count = 0;
        }
        count++;

        if (count > max) {
            throw new VisitorException("Maximum multiplicity exceeded for component-relation between '"
                    + source + "' and '" + target + "'. Only a multiplicity of  '" + max
                    + "' relations defined in schema " + this.extension.getIdentifier() + ".");
        }

        associationCount.put(association.getName().getValue().getValue(), count);
    }

    /**
     * Getter for the configured associations with the given source and target id.
     * 
     * @param sourceId
     *            the source id
     * @param targetId
     *            the target id
     * 
     * @return the configured association
     */
    private BusinessSchemaAssociation findAssociation(String sourceId, String targetId) {

        for (BusinessSchemaAssociation association : this.extension.getAssociations()) {

            BusinessSchemaAssociationRole source = association.getSource();
            BusinessSchemaAssociationRole target = association.getTarget();

            if (source.getId().getValue().getValue().equals(sourceId)
                    && target.getId().getValue().getValue().equals(targetId)) {
                return association;
            }
        }

        return null;
    }

    /**
     * Getter for the ID of the given datatype.
     * 
     * @param datatypeClass
     *            the datatype class
     * 
     * @return the configured ID, or null if the business object is not configured
     */
    private String getId(String datatypeClass) {
        for (BusinessSchemaObject object : this.extension.getObjects()) {
            String businessClass = object.getBusinessClass().getValue().getValue();
            if (businessClass.equalsIgnoreCase(datatypeClass)) {
                return object.getId().getValue().getValue();
            }
        }

        return null;
    }

    private int getMaxMultiplicity(BusinessSchemaAssociation association) {
        MultiplicityType multiplicity = this.getMultiplicity(association);

        if (multiplicity == null) {
            return 0;
        }

        switch (multiplicity) {

        case ONE:
            return 1;

        case ZERO_TO_ONE:
            return 1;

        case ONE_TO_MANY:
            return Integer.MAX_VALUE;

        case ZERO_TO_MANY:
            return Integer.MAX_VALUE;

        default:
            return 0;

        }
    }

    /**
     * Resolve the multiplicity of the association.
     * 
     * @param association
     *            the association
     * 
     * @return the associations multiplicity, or null if not defined
     */
    private MultiplicityType getMultiplicity(BusinessSchemaAssociation association) {
        if (association == null || association.getTarget() == null) {
            return null;
        }

        EnumerationProperty property = association.getTarget().getMultiplicity();
        if (property == null || property.getValue() == null || property.getValue().getValue() == null) {
            return null;
        }

        String multiplicity = property.getValue().getValue();

        for (MultiplicityType type : MultiplicityType.values()) {
            if (type.name().equalsIgnoreCase(multiplicity)) {
                return type;
            }
        }

        return null;
    }

}
