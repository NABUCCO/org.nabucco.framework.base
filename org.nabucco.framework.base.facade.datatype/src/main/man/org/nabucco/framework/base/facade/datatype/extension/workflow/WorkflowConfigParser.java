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
package org.nabucco.framework.base.facade.datatype.extension.workflow;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowConditionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowConfigurationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowDefinitionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowEffectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowSignalExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowStateExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowTransitionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowTriggerExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect.ConstraintEffectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect.InstantiationEffectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect.LogEffectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect.SubWorkflowEffectExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * WorkflowConfigParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowConfigParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_MAX = "max";

    private static final String ATTR_MIN = "min";

    private static final String ATTR_VISIBILITY_CONSTRAINT = "visibilityConstraint";

    private static final String ATTR_EDITABILITY_CONSTRAINT = "editabilityConstraint";

    private static final String ATTR_LENGTH_CONSTRAINT = "lengthConstraint";

    private static final String ATTR_MULTIPLICITY_CONSTRAINT = "multiplicityConstraint";

    private static final String ATTR_PROPERTY = "property";

    private static final String ELEMENT_LOG = "log";

    private static final String ELEMENT_CONSTRAINT = "constraint";

    private static final String ELEMENT_INSTANTIABLE = "instantiable";

    /* XML Attributes */

    private static final String ELEMENT_SUB_WORKFLOW = "subWorkflow";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_OWNER = "owner";

    private static final String ATTR_COMMENT = "comment";

    private static final String ATTR_VALUE = "value";

    private static final String ATTR_DESCRIPTION = "description";

    private static final String ATTR_MESSAGE = "message";

    private static final String ATTR_CLASS = "class";

    private static final String ATTR_SOURCE = "source";

    private static final String ATTR_TARGET = "target";

    private static final String ATTR_TIMER = "timer";

    private static final String ATTR_SIGNAL = "signal" + "";

    private static final String ATTR_OPERATOR = "operator";

    private static final String ATTR_SUMMARY = "summary";

    private static final String ATTR_WORKFLOW_NAME = "workflowName";

    private static final String ATTR_ASSIGNED_GROUP = "assignedGroup";

    private static final String ATTR_ASSIGNED_USER = "assignedUser";

    private static final String ATTR_ASSIGNED_ROLE = "assignedRole";

    /* XML Tags */

    private static final String TAG_WORKFLOW = "workflow";

    private static final String TAG_STATE = "state";

    private static final String TAG_STATE_LIST = "stateList";

    private static final String TAG_TRANSITION = "transition";

    private static final String TAG_TRANSITION_LIST = "transitionList";

    private static final String TAG_SIGNAL = "signal";

    private static final String TAG_SIGNAL_LIST = "signalList";

    private static final String TAG_TRIGGER = "trigger";

    private static final String TAG_CONDITION = "condition";

    private static final String TAG_CONDITION_COMPOSITE = "conditionComposite";

    private static final String TAG_EFFECT_LIST = "effectList";

    private static final String ATTR_EDITABLE = "editable";

    private static final String ATTR_VISIBLE = "visible";

    /* Effect Types */

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WorkflowConfigParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing workflow configuration");

            WorkflowConfigurationExtension extension = new WorkflowConfigurationExtension();
            extension.setIdentifier(super.getExtensionId(element));

            Element workflowElement = this.getElementByTagName(element, TAG_WORKFLOW);
            WorkflowDefinitionExtension workflowExtension = new WorkflowDefinitionExtension();
            workflowExtension.setName(super.getStringProperty(workflowElement, ATTR_NAME));
            workflowExtension.setDescription(super.getStringProperty(workflowElement, ATTR_DESCRIPTION));
            workflowExtension.setOwner(super.getStringProperty(workflowElement, ATTR_OWNER));
            workflowExtension.setType(super.getEnumerationProperty(workflowElement, ATTR_TYPE));

            this.parseSignalList(workflowElement, workflowExtension);
            this.parseStateList(workflowElement, workflowExtension);
            this.parseTransitionList(workflowElement, workflowExtension);

            extension.setWorkflow(workflowExtension);

            return new NabucoExtensionContainer(extension);
        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

    /**
     * Parse the list of signals.
     * 
     * @param workflowElement
     *            the workflow xml element
     * @param workflowExtension
     *            the workflow extension
     * 
     * @throws ExtensionException
     * @throws ExtensionParserException
     */
    private void parseSignalList(Element workflowElement, WorkflowDefinitionExtension workflowExtension)
            throws ExtensionException, ExtensionParserException {

        Element signalListElement = this.getElementByTagName(workflowElement, TAG_SIGNAL_LIST);
        List<Element> signalList = super.getElementsByTagName(signalListElement, TAG_SIGNAL);

        for (Element element : signalList) {
            WorkflowSignalExtension signalExtension = new WorkflowSignalExtension();
            signalExtension.setName(super.getStringProperty(element, ATTR_NAME));
            signalExtension.setOwner(super.getStringProperty(element, ATTR_OWNER));
            signalExtension.setDescription(super.getStringProperty(element, ATTR_DESCRIPTION));

            workflowExtension.getSignalList().add(signalExtension);
        }
    }

    /**
     * Parse the list of states.
     * 
     * @param workflowElement
     *            the workflow xml element
     * @param workflowExtension
     *            the workflow extension
     * 
     * @throws ExtensionException
     * @throws ExtensionParserException
     */
    private void parseStateList(Element workflowElement, WorkflowDefinitionExtension workflowExtension)
            throws ExtensionException, ExtensionParserException {

        Element stateListElement = this.getElementByTagName(workflowElement, TAG_STATE_LIST);

        if (stateListElement == null) {
            throw new ExtensionParserException("Cannot find state list tag.");
        }

        List<Element> statesList = super.getElementsByTagName(stateListElement, TAG_STATE);

        if (statesList.size() == 0) {
            throw new ExtensionParserException("No states defined for stateList.");
        }

        for (Element element : statesList) {
            WorkflowStateExtension stateExtension = new WorkflowStateExtension();
            stateExtension.setName(super.getStringProperty(element, ATTR_NAME));
            stateExtension.setOwner(super.getStringProperty(element, ATTR_OWNER));
            stateExtension.setDescription(super.getStringProperty(element, ATTR_DESCRIPTION));
            stateExtension.setType(super.getEnumerationProperty(element, ATTR_TYPE));
            workflowExtension.getStateList().add(stateExtension);
        }
    }

    /**
     * Parse the list of transitions.
     * 
     * @param workflowElement
     *            the workflow xml element
     * @param workflowExtension
     *            the workflow extension
     * 
     * @throws ExtensionException
     * @throws ExtensionParserException
     */
    private void parseTransitionList(Element workflowElement, WorkflowDefinitionExtension workflowExtension)
            throws ExtensionException, ExtensionParserException {

        Element transitionListElement = this.getElementByTagName(workflowElement, TAG_TRANSITION_LIST);

        if (transitionListElement == null) {
            throw new ExtensionParserException("Cannot find transition list tag.");
        }

        List<Element> transitionsElements = super.getElementsByTagName(transitionListElement, TAG_TRANSITION);

        if (transitionsElements.size() == 0) {
            throw new ExtensionParserException("No transition defined for transitionList.");
        }

        for (Element element : transitionsElements) {
            WorkflowTransitionExtension extension = new WorkflowTransitionExtension();
            extension.setName(super.getStringProperty(element, ATTR_NAME));
            extension.setOwner(super.getStringProperty(element, ATTR_OWNER));
            extension.setCommentType(super.getEnumerationProperty(element, ATTR_COMMENT));
            extension.setDescription(super.getStringProperty(element, ATTR_DESCRIPTION));

            String source = super.getAttribute(element, ATTR_SOURCE);
            WorkflowStateExtension sourceState = this.getState(workflowExtension, source);
            extension.setSource(sourceState);

            String target = super.getAttribute(element, ATTR_TARGET);
            WorkflowStateExtension targetState = this.getState(workflowExtension, target);
            extension.setTarget(targetState);

            this.parseTrigger(element, extension, workflowExtension);
            this.parseCondition(element, extension);
            this.parseEffectList(element, extension);

            workflowExtension.getTransitionList().add(extension);
        }
    }

    /**
     * Parse a workflow trigger.
     * 
     * @param transitionElement
     *            the transition xml element
     * @param transitionExtension
     *            the transition extension
     * @param workflowExtension
     *            the workflow extension
     * 
     * @throws ExtensionException
     *             when the element cannot be resolved
     * @throws ExtensionParserException
     *             when the element cannot be parsed
     */
    private void parseTrigger(Element transitionElement, WorkflowTransitionExtension transitionExtension,
            WorkflowDefinitionExtension workflowExtension) throws ExtensionException, ExtensionParserException {

        Element element = this.getElementByTagName(transitionElement, TAG_TRIGGER);

        if (element == null) {
            throw new ExtensionParserException("Cannot find transition trigger tag.");
        }

        WorkflowTriggerExtension extension = new WorkflowTriggerExtension();
        extension.setName(super.getStringProperty(element, ATTR_NAME));
        extension.setOwner(super.getStringProperty(element, ATTR_OWNER));
        extension.setDescription(super.getStringProperty(element, ATTR_DESCRIPTION));
        extension.setType(super.getEnumerationProperty(element, ATTR_TYPE));

        String signal = super.getAttribute(element, ATTR_SIGNAL);
        extension.setSignal(this.getSignal(workflowExtension, signal));
        extension.setTimer(super.getStringProperty(element, ATTR_TIMER));

        transitionExtension.setTrigger(extension);
    }

    /**
     * Parse a sigle transition condition.
     * 
     * @param transitionElement
     *            the transition xml element
     * @param transitionExtension
     *            the transition extension
     * 
     * @throws ExtensionException
     *             when the element cannot be resolved
     * @throws ExtensionParserException
     *             when the element cannot be parsed
     */
    private void parseCondition(Element transitionElement, WorkflowTransitionExtension transitionExtension)
            throws ExtensionException, ExtensionParserException {

        Element conditionElement = this.getChildElement(transitionElement, TAG_CONDITION);
        Element compositeElement = this.getChildElement(transitionElement, TAG_CONDITION_COMPOSITE);

        if (conditionElement == null) {
            if (compositeElement != null) {

                // Only <conditionComposite> defined.
                transitionExtension.setCondition(this.parseConditionComposite(compositeElement));
            } else {

                // No <condition> or <conditionComposite> defined.
                return;
            }

        } else {
            if (compositeElement == null) {

                // Only <condition> defined.
                transitionExtension.setCondition(this.parseConditionComponent(conditionElement));
            } else {

                // Both <condition> and <conditionComposite> defined.
                throw new ExtensionParserException(
                        "Cannot have both <condition> and <conditionComposite> in one transition.");
            }
        }
    }

    /**
     * Parse a condition composite element.
     * 
     * @param conditionElement
     *            the condition xml element.
     * 
     * @return the condition extension
     * 
     * @throws ExtensionParserException
     *             when the condition composite cannot be parsed
     */
    private WorkflowConditionExtension parseConditionComposite(Element conditionElement)
            throws ExtensionParserException {

        WorkflowConditionExtension extension = new WorkflowConditionExtension();
        extension.setName(super.getStringProperty(conditionElement, ATTR_NAME));
        extension.setOwner(super.getStringProperty(conditionElement, ATTR_OWNER));
        extension.setDescription(super.getStringProperty(conditionElement, ATTR_DESCRIPTION));
        extension.setType(super.getEnumerationProperty(conditionElement, ATTR_TYPE));
        extension.setOperator(super.getEnumerationProperty(conditionElement, ATTR_OPERATOR));

        List<Element> childConditions = this.getChildElements(conditionElement, TAG_CONDITION);
        for (Element childCondition : childConditions) {
            extension.getChildren().add(this.parseConditionComponent(childCondition));
        }

        List<Element> childComposites = this.getChildElements(conditionElement, TAG_CONDITION_COMPOSITE);
        for (Element childComposite : childComposites) {
            extension.getChildren().add(this.parseConditionComposite(childComposite));
        }

        return extension;
    }

    /**
     * Parse a single condition element.
     * 
     * @param conditionElement
     *            the condition xml element.
     * 
     * @return the condition extension
     * 
     * @throws ExtensionParserException
     *             when the condition cannot be parsed
     */
    private WorkflowConditionExtension parseConditionComponent(Element conditionElement)
            throws ExtensionParserException {

        WorkflowConditionExtension extension = new WorkflowConditionExtension();
        extension.setName(super.getStringProperty(conditionElement, ATTR_NAME));
        extension.setOwner(super.getStringProperty(conditionElement, ATTR_OWNER));
        extension.setDescription(super.getStringProperty(conditionElement, ATTR_DESCRIPTION));
        extension.setType(super.getEnumerationProperty(conditionElement, ATTR_TYPE));
        extension.setValue(super.getStringProperty(conditionElement, ATTR_VALUE));

        return extension;
    }

    /**
     * Parse the list of effects.
     * 
     * @param transitionElement
     *            the transition xml element
     * @param transitionExtension
     *            the workflow extension
     * 
     * @throws ExtensionException
     *             when the element cannot be resolved
     * @throws ExtensionParserException
     *             when the element cannot be parsed
     */
    private void parseEffectList(Element transitionElement, WorkflowTransitionExtension transitionExtension)
            throws ExtensionException, ExtensionParserException {

        Element effectListElement = this.getElementByTagName(transitionElement, TAG_EFFECT_LIST);

        if (effectListElement == null) {
            return;
        }

        List<Element> effectsList = super.getChildren(effectListElement);

        for (Element element : effectsList) {

            String tagName = element.getTagName();
            WorkflowEffectExtension effectExtension = null;

            if (tagName.equals(ELEMENT_SUB_WORKFLOW)) {
                SubWorkflowEffectExtension subWorkflowExtension = new SubWorkflowEffectExtension();
                subWorkflowExtension.setSummary(super.getStringProperty(element, ATTR_SUMMARY));
                subWorkflowExtension.setDefinitionName(super.getStringProperty(element, ATTR_WORKFLOW_NAME));
                subWorkflowExtension.setAssignedGroup(super.getStringProperty(element, ATTR_ASSIGNED_GROUP));
                subWorkflowExtension.setAssignedUser(super.getStringProperty(element, ATTR_ASSIGNED_USER));
                subWorkflowExtension.setAssignedRole(super.getStringProperty(element, ATTR_ASSIGNED_ROLE));

                effectExtension = subWorkflowExtension;
            } else if (tagName.equals(ELEMENT_INSTANTIABLE)) {
                InstantiationEffectExtension instanceExtension = new InstantiationEffectExtension();
                instanceExtension.setClassName(super.getClassProperty(element, ATTR_CLASS));

                effectExtension = instanceExtension;
            } else if (tagName.equals(ELEMENT_CONSTRAINT)) {
                ConstraintEffectExtension constraintEffectExtension = new ConstraintEffectExtension();
                constraintEffectExtension.setPropertyName(super.getStringProperty(element, ATTR_PROPERTY));

                List<Element> constraintElements = super.getChildren(element);
                for (Element constraint : constraintElements) {
                    String constraintType = constraint.getTagName();

                    if (constraintType.equals(ATTR_MULTIPLICITY_CONSTRAINT)) {
                        constraintEffectExtension.setMinMultiplicity(super.getIntegerProperty(constraint, ATTR_MIN, 0));
                        constraintEffectExtension.setMaxMultiplicity(super.getIntegerProperty(constraint, ATTR_MAX, 1));
                    } else if (constraintType.equals(ATTR_LENGTH_CONSTRAINT)) {
                        constraintEffectExtension.setMinLength(super.getIntegerProperty(constraint, ATTR_MIN, 0));
                        constraintEffectExtension.setMaxLength(super.getIntegerProperty(constraint, ATTR_MAX, 255));
                    } else if (constraintType.equals(ATTR_EDITABILITY_CONSTRAINT)) {
                        constraintEffectExtension.setEditable(super.getBooleanProperty(constraint, ATTR_EDITABLE));
                    } else if (constraintType.equals(ATTR_VISIBILITY_CONSTRAINT)) {
                        constraintEffectExtension.setVisible(super.getBooleanProperty(constraint, ATTR_VISIBLE));
                    } else {
                        throw new ExtensionParserException("Not supported constraint, " + constraintType);
                    }
                }
                effectExtension = constraintEffectExtension;

            } else if (tagName.equals(ELEMENT_LOG)) {
                LogEffectExtension logExtension = new LogEffectExtension();
                logExtension.setMessage(super.getStringProperty(element, ATTR_MESSAGE));

                effectExtension = logExtension;
            } else {
                effectExtension = new WorkflowEffectExtension();
            }

            effectExtension.setName(super.getStringProperty(element, ATTR_NAME));
            effectExtension.setOwner(super.getStringProperty(element, ATTR_OWNER));
            effectExtension.setDescription(super.getStringProperty(element, ATTR_DESCRIPTION));
            EnumerationProperty type = new EnumerationProperty();
            type.setValue(tagName);
            effectExtension.setType(type);

            transitionExtension.getEffectList().add(effectExtension);
        }
    }

    /**
     * Resolve the state for the given name.
     * 
     * @param workflowExtension
     *            the workflow extension
     * @param name
     *            the state name
     * 
     * @return the state extension
     * 
     * @throws ExtensionParserException
     *             when the state cannot be resolved
     */
    private WorkflowStateExtension getState(WorkflowDefinitionExtension workflowExtension, String name)
            throws ExtensionParserException {
        for (WorkflowStateExtension state : workflowExtension.getStateList()) {
            if (state.getName().getValue().getValue().equals(name)) {
                return state;
            }
        }
        throw new ExtensionParserException("Cannot find state extension with name=[" + name + ".]");
    }

    /**
     * Resolve the signal for the given name.
     * 
     * @param workflowExtension
     *            the workflow extension
     * @param name
     *            the signal name
     * 
     * @return the signal extension
     */
    private WorkflowSignalExtension getSignal(WorkflowDefinitionExtension workflowExtension, String name) {
        for (WorkflowSignalExtension signal : workflowExtension.getSignalList()) {
            if (signal.getName().getValue().getValue().equals(name)) {
                return signal;
            }
        }
        return null;
    }

    /**
     * Getter for the first element with the given tag name or null if it does not exist.
     * 
     * @param parentElement
     *            the parent element
     * @param name
     *            the element name
     * 
     * @return the appropriate element
     * 
     * @throws ExtensionException
     *             when the element with the given name cannot be found
     */
    private Element getElementByTagName(Element parentElement, String name) throws ExtensionException {
        List<Element> elements = super.getElementsByTagName(parentElement, name);

        if (elements.size() < 1) {
            return null;
        }

        return elements.get(0);
    }

    /**
     * Load all child elements of the given parent xml element.
     * 
     * @param parent
     *            the parent xml element
     * 
     * @return the list of child elements
     */
    private List<Element> getChildElements(Element parent) {

        NodeList children = parent.getChildNodes();
        int length = children.getLength();

        List<Element> elements = new ArrayList<Element>();

        for (int i = 0; i < length; i++) {
            Node node = children.item(i);
            if (node instanceof Element) {
                elements.add((Element) node);
            }
        }

        return elements;
    }

    /**
     * Load all child elements with the given name of the given parent xml element.
     * 
     * @param parent
     *            the parent xml element
     * @param name
     *            the name of the child tag
     * 
     * @return the child elements with the given name
     */
    private List<Element> getChildElements(Element parent, String name) {

        List<Element> children = new ArrayList<Element>();
        List<Element> elements = this.getChildElements(parent);

        for (Element element : elements) {
            if (element.getNodeName().equals(name)) {
                children.add(element);
            }
        }

        return children;
    }

    /**
     * Load the first found child element with the given name of the given parent xml element.
     * 
     * @param parent
     *            the parent xml element
     * @param name
     *            the name of the child tag
     * 
     * @return the first found child element with the given name
     */
    private Element getChildElement(Element parent, String name) {
        List<Element> childElements = this.getChildElements(parent, name);

        if (childElements.isEmpty()) {
            return null;
        }

        return childElements.get(0);
    }

}
