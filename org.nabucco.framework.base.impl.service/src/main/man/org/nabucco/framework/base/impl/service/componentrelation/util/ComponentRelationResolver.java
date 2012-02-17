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
package org.nabucco.framework.base.impl.service.componentrelation.util;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationTypeProxy;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.business.schema.BusinessSchemaResolver;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * Resolves component relations by their business schema definition.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationResolver {

    private NabuccoDatatype datatype;

    private ServiceMessageContext context;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance()
            .getLogger(ComponentRelationResolver.class);

    /**
     * Creates a new {@link ComponentRelationResolver} instance.
     * 
     * @param datatype
     *            the datatype to resolve component relations for
     */
    public ComponentRelationResolver(NabuccoDatatype datatype, ServiceMessageContext context) {
        if (datatype == null) {
            throw new IllegalArgumentException("Cannot resolve component relations for datatype 'null'.");
        }
        if (context == null) {
            throw new IllegalArgumentException("Cannot resolve component relations for service context 'null'.");
        }
        this.context = context;
        this.datatype = datatype;
    }

    /**
     * Resolve component relations defined in the business schema extension which are pointing to
     * the given datatype from a source datatype with the given property name. Independed from the
     * source type, all relations with the given property name will be returned.
     * 
     * @param propertyName
     *            name of the association defined in the business schema extension
     * 
     * @return the list of resolved relations
     * 
     * @throws SearchException
     *             when the component relation service does not end up normally
     */
    public List<ComponentRelation<?>> resolveIncomingRelations(String propertyName) throws SearchException {
        return this.resolveIncomingRelations(propertyName, null);
    }

    /**
     * Resolve component relations defined in the business schema extension which are pointing to
     * the given datatype from a source datatype with the given property name. Only relations with
     * the given source type will be returned.
     * 
     * @param propertyName
     *            name of the association defined in the business schema extension
     * @param sourceType
     *            source type of the association
     * 
     * @return the list of resolved relations
     * 
     * @throws SearchException
     *             when the component relation service does not end up normally
     */
    public List<ComponentRelation<?>> resolveIncomingRelations(String propertyName,
            Class<? extends NabuccoDatatype> sourceType) throws SearchException {

        List<ComponentRelation<?>> relations = new ArrayList<ComponentRelation<?>>();

        try {
            ExtensionMap extensions = NabuccoSystem.getExtensionResolver().resolveExtensions(
                    ExtensionPointType.ORG_NABUCCO_BUSINESS_SCHEMA);

            for (String extensionId : extensions.getExtensionNames()) {

                BusinessSchemaResolver schemaResolver = new BusinessSchemaResolver(extensionId);

                ComponentRelationTypeProxy relationType = schemaResolver.getComponentRelationType(propertyName);
                if (relationType == null || !relationType.getTarget().isAssignableFrom(this.datatype.getClass())) {
                    continue;
                }
                if (sourceType != null && relationType.getSource() != sourceType) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                Class<Component> component = (Class<Component>) schemaResolver.getTargetComponent(propertyName);
                if (component == null) {
                    continue;
                }

                ComponentRelationServiceDelegate componentRelationService = new ComponentRelationServiceDelegate(
                        component, this.context);

                relations.addAll(componentRelationService.findComponentRelationsByTarget(relationType.getRelation(),
                        relationType, this.datatype.getId()));
            }

            return relations;

        } catch (ConnectionException ce) {
            logger.error(ce, "Error connecting to source component.");
            throw new SearchException("Error connecting to source component.", ce);
        } catch (SearchException se) {
            logger.error(se, "Error resolving incoming component relations.");
            throw se;
        } catch (ExtensionException ee) {
            logger.error(ee, "Error resolving business schema extension.");
            throw new SearchException("Error resolving business schema extension.", ee);
        } catch (Exception e) {
            logger.error(e, "Error resolving incoming component relations.");
            throw new SearchException("Error resolving incoming component relations.", e);
        }
    }

    /**
     * Resolve component relations defined in the business schema extension which are pointing from
     * the given datatype to a target datatype with the given property name.
     * 
     * @param propertyName
     *            name of the association defined in the business schema extension
     * 
     * @return the list of resolved relations
     * 
     * @throws SearchException
     *             when the component relation service does not end up normally
     */
    public List<ComponentRelation<?>> resolveOutgoingRelations(String propertyName) throws SearchException {
        return this.resolveOutgoingRelations(propertyName, null);
    }

    /**
     * Resolve component relations defined in the business schema extension which are pointing from
     * the given datatype to a target datatype with the given property name.
     * 
     * @param propertyName
     *            name of the association defined in the business schema extension
     * @param targetType
     *            target type of the association
     * 
     * @return the list of resolved relations
     * 
     * @throws SearchException
     *             when the component relation service does not end up normally
     */
    public List<ComponentRelation<?>> resolveOutgoingRelations(String propertyName,
            Class<? extends NabuccoDatatype> targetType) throws SearchException {

        List<ComponentRelation<?>> relations = new ArrayList<ComponentRelation<?>>();

        try {
            ExtensionMap extensions = NabuccoSystem.getExtensionResolver().resolveExtensions(
                    ExtensionPointType.ORG_NABUCCO_BUSINESS_SCHEMA);

            for (String extensionId : extensions.getExtensionNames()) {

                BusinessSchemaResolver schemaResolver = new BusinessSchemaResolver(extensionId);

                ComponentRelationTypeProxy relationType = schemaResolver.getComponentRelationType(propertyName);
                if (relationType == null || !relationType.getSource().isAssignableFrom(this.datatype.getClass())) {
                    continue;
                }
                if (targetType != null && relationType.getTarget() != targetType) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                Class<Component> component = (Class<Component>) schemaResolver.getTargetComponent(propertyName);
                if (component == null) {
                    continue;
                }

                ComponentRelationServiceDelegate componentRelationService = new ComponentRelationServiceDelegate(
                        component, this.context);

                relations.addAll(componentRelationService.findComponentRelationsBySource(relationType.getRelation(),
                        relationType, this.datatype.getId()));
            }

            return relations;

        } catch (ConnectionException ce) {
            logger.error(ce, "Error connecting to target component.");
            throw new SearchException("Error connecting to target component.", ce);
        } catch (SearchException se) {
            logger.error(se, "Error resolving outgoing component relations.");
            throw se;
        } catch (ExtensionException ee) {
            logger.error(ee, "Error resolving business schema extension.");
            throw new SearchException("Error resolving business schema extension.", ee);
        } catch (Exception e) {
            logger.error(e, "Error resolving outgoing component relations.");
            throw new SearchException("Error resolving outgoing component relations.", e);
        }
    }
}
