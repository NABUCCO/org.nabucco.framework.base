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

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationTypeProxy;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociation;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociationRole;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaObject;

/**
 * BusinessSchemaResolver
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BusinessSchemaResolver {

    private final BusinessSchemaExtension extension;

    /**
     * Creates a new {@link BusinessSchemaResolver} instance.
     * 
     * @param extensionName
     *            the name of the {@link BusinessSchemaExtension}
     * 
     * @throws ExtensionException
     *             when no business schema with the given extensionName exists
     */
    public BusinessSchemaResolver(String extensionName) throws ExtensionException {
        this.extension = (BusinessSchemaExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                ExtensionPointType.ORG_NABUCCO_BUSINESS_SCHEMA, extensionName);
    }

    /**
     * Getter for the extension.
     * 
     * @return Returns the extension.
     */
    public BusinessSchemaExtension getExtension() {
        return this.extension;
    }

    /**
     * Resolve the busines object with the given name from the business schema.
     * 
     * @param name
     *            the business object name
     * 
     * @return the business object
     */
    public BusinessSchemaObject getObject(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (BusinessSchemaObject object : extension.getObjects()) {
            String objectName = PropertyLoader.loadProperty(object.getId());

            if (name.equals(objectName)) {
                return object;
            }
        }
        return null;
    }

    /**
     * Resolve the source busines objectfrom the business schema association with the given name.
     * 
     * @param associationName
     *            the association name
     * 
     * @return the source business object
     */
    public BusinessSchemaObject getSource(String associationName) {
        BusinessSchemaAssociation association = this.getAssociation(associationName);

        BusinessSchemaAssociationRole sourceRole = association.getSource();
        if (sourceRole == null) {
            return null;
        }

        String source = PropertyLoader.loadProperty(sourceRole.getId());
        if (source == null) {
            return null;
        }

        return this.getObject(source);
    }

    /**
     * Loads the source component class.
     * 
     * @param associationName
     *            the association name
     * 
     * @return the source component
     */
    public Class<?> getSourceComponent(String associationName) {
        BusinessSchemaObject source = this.getSource(associationName);
        if (source == null) {
            return null;
        }

        return PropertyLoader.loadProperty(source.getComponent());
    }

    /**
     * Resolve the target busines objectfrom the business schema association with the given name.
     * 
     * @param associationName
     *            the association name
     * 
     * @return the target business object
     */
    public BusinessSchemaObject getTarget(String associationName) {
        BusinessSchemaAssociation association = this.getAssociation(associationName);

        BusinessSchemaAssociationRole targetRole = association.getTarget();
        if (targetRole == null) {
            return null;
        }

        String target = PropertyLoader.loadProperty(targetRole.getId());
        if (target == null) {
            return null;
        }

        return this.getObject(target);
    }

    /**
     * Loads the target component class.
     * 
     * @param associationName
     *            the association name
     * 
     * @return the target component
     */
    public Class<?> getTargetComponent(String associationName) {
        BusinessSchemaObject target = this.getTarget(associationName);
        if (target == null) {
            return null;
        }

        return PropertyLoader.loadProperty(target.getComponent());
    }

    /**
     * Resolve the association with the given name from the business schema.
     * 
     * @param name
     *            the association name
     * 
     * @return the association
     */
    public BusinessSchemaAssociation getAssociation(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (BusinessSchemaAssociation association : extension.getAssociations()) {
            String associationName = PropertyLoader.loadProperty(association.getName());

            if (name.equals(associationName)) {
                return association;
            }
        }
        return null;
    }

    /**
     * Resolve the component relation type for the given association name.
     * 
     * @param name
     *            name of the association
     * 
     * @return the component relation type
     */
    public ComponentRelationTypeProxy getComponentRelationType(String name) {
        BusinessSchemaAssociation association = this.getAssociation(name);
        if (association == null) {
            return null;
        }

        return new ComponentRelationTypeProxy(extension, association);
    }
}
