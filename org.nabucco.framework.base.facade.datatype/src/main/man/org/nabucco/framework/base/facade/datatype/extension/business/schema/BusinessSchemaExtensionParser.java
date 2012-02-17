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
package org.nabucco.framework.base.facade.datatype.extension.business.schema;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociation;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociationRole;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaObject;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.relation.AssociationType;
import org.w3c.dom.Element;

/**
 * Extension Parser for Business Schemata.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BusinessSchemaExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_FETCH = "fetch";

    private static final String ATTR_CLASS = "class";

    private static final String ATTR_COMPONENT = "component";

    private static final String ATTR_DESCRIPTION = "description";

    private static final String ATTR_MULTIPLICITY = "multiplicity";

    private static final String ELEMENT_OBJECT = "businessObject";

    private static final String ELEMENT_ASSOCIATION = "association";

    private static final String ELEMENT_ROLE = "role";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            BusinessSchemaExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        logger.debug("Parsing Management Configuration.");

        try {
            BusinessSchemaExtension extension = new BusinessSchemaExtension();
            extension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            this.parseObjects(element, extension);
            this.parseAssociations(element, extension);

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Parse the business object configurations.
     * 
     * @param element
     *            the schema element
     * @param extension
     *            the schema extension
     * 
     * @throws ExtensionException
     *             when the business object cannot be parsed
     * @throws ExtensionParserException
     *             when the business object properties cannot be parsed
     */
    private void parseObjects(Element element, BusinessSchemaExtension extension) throws ExtensionException,
            ExtensionParserException {
        List<Element> businessObjects = super.getElementsByTagName(element, ELEMENT_OBJECT);

        if (businessObjects.size() < 1) {
            throw new ExtensionParserException("No businessObject configuration found in schema extension '"
                    + extension.getIdentifier() + "'.");
        }

        for (Element objectElement : businessObjects) {
            BusinessSchemaObject objectExtension = new BusinessSchemaObject();
            objectExtension.setId(super.getStringProperty(objectElement, ATTR_ID));
            objectExtension.setDescription(super.getStringProperty(objectElement, ATTR_DESCRIPTION));
            objectExtension.setBusinessClass(super.getClassProperty(objectElement, ATTR_CLASS));
            objectExtension.setComponent(super.getClassProperty(objectElement, ATTR_COMPONENT));

            this.validateObject(objectExtension, extension);

            extension.getObjects().add(objectExtension);
        }
    }

    /**
     * Parse the business object associations.
     * 
     * @param element
     *            the schema element
     * @param extension
     *            the schema extension
     * 
     * @throws ExtensionException
     *             when the association cannot be parsed
     * @throws ExtensionParserException
     *             when the association properties cannot be parsed
     */
    private void parseAssociations(Element element, BusinessSchemaExtension extension) throws ExtensionException,
            ExtensionParserException {
        List<Element> businessAssociations = super.getElementsByTagName(element, ELEMENT_ASSOCIATION);

        if (businessAssociations.size() < 1) {
            throw new ExtensionParserException("No association configuration found in schema extension '"
                    + extension.getIdentifier() + "'.");
        }

        for (Element associationElement : businessAssociations) {
            BusinessSchemaAssociation associationExtension = new BusinessSchemaAssociation();
            associationExtension.setName(super.getStringProperty(associationElement, ATTR_NAME));
            associationExtension.setType(super.getEnumerationProperty(associationElement, ATTR_TYPE));
            associationExtension.setFetchType(super.getEnumerationProperty(associationElement, ATTR_FETCH));
            associationExtension.setAssociationClass(super.getClassProperty(associationElement, ATTR_CLASS));

            this.validateAssociation(associationExtension, extension);

            List<Element> associationRoles = super.getElementsByTagName(associationElement, ELEMENT_ROLE);

            if (associationRoles.size() < 2) {
                throw new ExtensionParserException("Less than 2 association roles found for association '"
                        + associationExtension.getName() + "' in schema extension '" + extension.getIdentifier() + "'.");
            }

            if (associationRoles.size() > 2) {
                throw new ExtensionParserException("More than 2 association roles found for association '"
                        + associationExtension.getName() + "' in schema extension '" + extension.getIdentifier() + "'.");
            }

            {
                Element roleElement = associationRoles.get(0);
                BusinessSchemaAssociationRole role = new BusinessSchemaAssociationRole();
                role.setId(super.getStringProperty(roleElement, ATTR_ID));
                role.setMultiplicity(super.getEnumerationProperty(roleElement, ATTR_MULTIPLICITY));

                this.validateRole(role, extension);
                associationExtension.setSource(role);
            }

            {
                Element roleElement = associationRoles.get(1);
                BusinessSchemaAssociationRole role = new BusinessSchemaAssociationRole();
                role.setId(super.getStringProperty(roleElement, ATTR_ID));
                role.setMultiplicity(super.getEnumerationProperty(roleElement, ATTR_MULTIPLICITY));

                this.validateRole(role, extension);
                associationExtension.setTarget(role);
            }

            extension.getAssociations().add(associationExtension);
        }
    }

    /**
     * Validate the business object.
     * 
     * @param association
     *            the business object extension to validate
     * @param schema
     *            the parent schema
     * 
     * @throws ExtensionParserException
     *             when the business object extension is not valid
     */
    private void validateObject(BusinessSchemaObject object, BusinessSchemaExtension schema)
            throws ExtensionParserException {
        if (object == null || object.getBusinessClass() == null) {
            throw new ExtensionParserException("Type of association 'null' in schema extension '"
                    + schema.getIdentifier() + "' is not valid.");
        }
        if (object.getId() == null || object.getId().getValue() == null) {
            throw new ExtensionParserException("BusinessObject 'null' referenced in schema extension '"
                    + schema.getIdentifier() + "' is not valid.");
        }

        String id = object.getId().getValue().getValue();

        if (object.getBusinessClass() == null || object.getBusinessClass().getValue() == null) {
            throw new ExtensionParserException("BusinessObject '"
                    + id + "' class 'null' referenced in schema extension '" + schema.getIdentifier()
                    + "' does not exist.");
        }

        String className = object.getBusinessClass().getValue().getValue();

        try {
            BusinessSchemaExtensionParser.class.getClassLoader().loadClass(className);

        } catch (ClassNotFoundException cnfe) {
            throw new ExtensionParserException("BusinessObject '"
                    + id + "' class '" + className + "' referenced in schema extension '" + schema.getIdentifier()
                    + "' does not exist.", cnfe);
        } catch (Exception e) {
            throw new ExtensionParserException("BusinessObject '"
                    + id + "' class '" + className + "' referenced in schema extension '" + schema.getIdentifier()
                    + "' is not referencable.", e);
        }
    }

    /**
     * Validate the association.
     * 
     * @param association
     *            the association extension to validate
     * @param schema
     *            the parent schema
     * 
     * @throws ExtensionParserException
     *             when the association extension is not valid
     */
    private void validateAssociation(BusinessSchemaAssociation association, BusinessSchemaExtension schema)
            throws ExtensionParserException {
        if (association == null || association.getType() == null) {
            throw new ExtensionParserException("Type of association 'null' in schema extension '"
                    + schema.getIdentifier() + "' is not valid.");
        }
        if (association.getType().getValue() == null) {
            throw new ExtensionParserException("Type of association 'null' in schema extension '"
                    + schema.getIdentifier() + "' is not valid.");
        }

        for (AssociationType associationType : AssociationType.values()) {
            if (associationType.name().equalsIgnoreCase(association.getType().getValue().getValue())) {
                return;
            }
        }

        throw new ExtensionParserException("Type of association '"
                + association.getType().getValue() + "' in schema extension '" + schema.getIdentifier()
                + "' is not valid.");
    }

    /**
     * Validate the association role.
     * 
     * @param role
     *            the role extension to validate
     * @param schema
     *            the parent schema
     * 
     * @throws ExtensionParserException
     *             when the role extension is not valid
     */
    private void validateRole(BusinessSchemaAssociationRole role, BusinessSchemaExtension schema)
            throws ExtensionParserException {
        if (role == null) {
            throw new ExtensionParserException("Roles in schema extension '"
                    + schema.getIdentifier() + "' are not valid.");
        }
        if (role.getId() == null || role.getId().getValue() == null || role.getId().getValue().getValue().isEmpty()) {
            throw new ExtensionParserException("Role 'null' in schema extension '"
                    + schema.getIdentifier() + "' is not valid.");
        }

        String roleId = role.getId().getValue().getValue();

        for (BusinessSchemaObject object : schema.getObjects()) {
            if (object == null || object.getId() == null) {
                continue;
            }
            if (object.getId().getValue() == null || object.getId().getValue().getValue() == null) {
                continue;
            }

            if (roleId.equals(object.getId().getValue().getValue())) {
                return;
            }
        }

        throw new ExtensionParserException("Role '"
                + role.getId().getValue() + "' in schema extension '" + schema.getIdentifier()
                + "' references an undefined business object.");
    }
}
