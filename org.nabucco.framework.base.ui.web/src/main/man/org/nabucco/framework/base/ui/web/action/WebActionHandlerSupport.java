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
package org.nabucco.framework.base.ui.web.action;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeAccessor;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.relation.RelationType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;

/**
 * WebActionHandler
 * <p/>
 * The support methods to work with properties and relations
 * 
 * @author Leonid Agranovskiy PRODYNA AG
 */
public abstract class WebActionHandlerSupport extends WebActionHandler {

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WebActionHandlerSupport.class);

    /**
     * Adds a new item to the given property
     * 
     * @param newElement
     *            the new element to be added
     * @param source
     *            the source datatype to be modified
     * @param propertyName
     *            the name of the property
     */
    protected void addProperty(Datatype newElement, Datatype source, String propertyName) {
        NabuccoProperty property = source.getProperty(propertyName);

        if (property == null) {
            throw new IllegalArgumentException("The Datatype has no property with name '" + propertyName + "'.");
        }

        switch (property.getPropertyType()) {

        case COMPONENT_RELATION: {
            this.createComponentRelation(newElement, source, (ComponentRelationProperty) property);
            break;
        }

        case COLLECTION: {
            this.createCollection(newElement, source, (CollectionProperty) property);
            break;
        }

        }

        if (property.getPropertyType() != NabuccoPropertyType.COMPONENT_RELATION) {
            return;
        }

    }

    /**
     * Add the new element to the source datatype collection.
     * 
     * @param newElement
     *            the new element to add
     * @param source
     *            the source element to add the new element to
     * @param property
     *            the property
     */
    protected final void createCollection(Datatype newElement, Datatype source, CollectionProperty property) {

        @SuppressWarnings("unchecked")
        NabuccoCollection<Datatype> collection = (NabuccoCollection<Datatype>) property.getInstance();

        if (collection == null) {
            collection = new NabuccoListImpl<Datatype>();
        }

        switch (collection.getType()) {

        case LIST: {
            NabuccoList<Datatype> list = (NabuccoList<Datatype>) collection;
            list.add(newElement);
        }

        }

        NabuccoProperty newProperty = property.createProperty(collection);
        property.getParent().setProperty(newProperty);
    }

    /**
     * Create a new component relation property
     * 
     * @param newElement
     *            the new element to add
     * @param source
     *            the source element to add the new element to
     * @param property
     *            the property
     */
    protected final void createComponentRelation(Datatype newElement, Datatype source,
            ComponentRelationProperty property) {

        // Existing relation!
        for (ComponentRelation<?> relation : property.getInstance()) {
            if (relation.getTarget() != null && relation.getTarget().getId() != null) {
                if (newElement instanceof NabuccoDatatype) {
                    if (relation.getTarget().getId().equals(((NabuccoDatatype) newElement).getId())) {
                        relation.setTarget(newElement);
                        return;
                    }
                }
            }
        }

        // New relation!
        ComponentRelation<?> relation = property.newComponentRelationInstance();
        relation.setDatatypeState(DatatypeState.INITIALIZED);
        relation.setRelationType(RelationType.HAS);
        relation.setTarget(newElement);

        ComponentRelationContainer container = DatatypeAccessor.getInstance().getComponentRelation(source);
        container.putComponentRelation(relation);
    }

    /**
     * Execute the action with the given action id.
     * 
     * @param actionId
     *            id of the action to execute
     * @param parameter
     *            web action parameter for the action
     * 
     * @return the action result of the executed action
     * 
     * @throws ClientException
     *             when the action cannot be executed or raises an exception
     */
    public final WebActionResult executeAction(String actionId, WebActionParameter parameter) throws ClientException {

        if (actionId == null || actionId.isEmpty()) {
            logger.warning("Cannot execute action with id 'null'.");
        }

        try {
            WebAction action = WebActionRegistry.getInstance().newAction(actionId);

            if (action == null) {
                logger.warning("No action configured for ID '", actionId, "'.");
                return null;
            }

            WebActionResult result = action.execute(parameter);

            return result;

        } catch (ClientException ce) {
            logger.error(ce, "Error executing NABUCCO Action '", actionId, "'.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error executing NABUCCO Action '", actionId, "'.");
            throw new ClientException("Error executing NABUCCO Action '" + actionId + "'.", e);
        }
    }

}
