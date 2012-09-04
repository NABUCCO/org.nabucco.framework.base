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
package org.nabucco.framework.base.facade.datatype.extension.ui;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.EditorButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonGroupExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.MenuButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionsExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemWorkflowExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorRelationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorTabExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.CheckBoxControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.CurrencyControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.DateControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.DropDownControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.FileControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.ImageControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.LabelControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.LinkControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.PasswordControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.PickerControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.RadioControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.TextAreaControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.TextControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencyExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.queryfilter.QueryParameterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.queryfilter.QueryParameterMappingExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the editor work item extension of the nabucco user interface.
 * 
 * @author Nicolas Moser, PRODYNA AG
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class EditorExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String MODIFICATION_DEFAULT = "NONE";

    private static final String ELEMENT_PARAMETER = "parameter";

    private static final String ELEMENT_QUERY_PARAMETER_MAPPING = "queryParameterMapping";

    private static final String ELEMENT_EDITOR = "editor";

    private static final String ELEMENT_WORK_ITEM_ACTIONS = "workItemActions";

    private static final String ELEMENT_WORKFLOW = "workflow";

    private static final String ELEMENT_BROWSER = "browser";

    private static final String ELEMENT_BUTTON = "button";

    private static final String ELEMENT_BUTTON_GROUP = "buttonGroup";

    private static final String ELEMENT_TAB = "tab";

    private static final String ELEMENT_RELATION = "relation";

    private static final String ELEMENT_COLUMN = "column";

    private static final String ELEMENT_DEPENDENCY_SET = "dependencySet";

    private static final Object ELEMENT_MENU = "menu";

    private static final String ELEMENT_DEPENDENCY = "dependency";

    private static final String ATTR_ID = "id";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_REFID = "refId";

    private static final String ATTR_RESOLVER = "resolver";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_ACTIONID = "actionId";

    private static final String ATTR_PROPERTY = "property";

    private static final String ATTR_UPLOAD_PATH = "uploadPath";

    private static final String ATTR_UPLOAD_DIALOG_ID = "uploadDialogId";

    private static final String ATTR_DOUBLECLICK_ACTION = "doubleclickAction";

    private static final String ATTR_LOAD_ACTION = "loadAction";

    private static final String ATTR_OPEN_ACTION = "openAction";

    private static final String ATTR_REGEX = "regex";

    private static final String ATTR_MULTIPLE_SELECTION = "multipleSelection";

    private static final String ATTR_MINFRACT = "minimumFractionDigits";

    private static final String ATTR_MAXFRACT = "maximumFractionDigits";

    private static final String ATTR_MININT = "minimumIntegerDigits";

    private static final String ATTR_MAXINT = "maximumIntegerDigits";

    private static final String ATTR_SIGNED = "signed";

    private static final String ATTR_SEPARATOR = "separator";

    private static final String ATTR_VISIBLE = "visible";

    private static final String ATTR_SORTABLE = "sortable";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_ICON = "icon";

    private static final String ATTR_GRID = "grid";

    private static final String ATTR_POSITION = "position";

    private static final String ATTR_EDITABLE = "editable";

    private static final String ATTR_HINT = "hint";

    private static final String ATTR_GROUPING = "grouping";

    private static final String ATTR_PROPERTY_LABEL = "propertyLabel";

    private static final String ATTR_PROPERTY_TOOLTIP = "propertyTooltip";

    private static final String ATTR_RENDERER = "renderer";

    private static final String ATTR_LAYOUT = "layout";

    private static final String ATTR_PICKER_DIALOG = "pickerDialog";

    private static final String ATTR_DISPLAY_PATH = "displayPath";

    private static final String ATTR_WORKFLOW_ICON = "workflowIcon";

    private static final String ATTR_SIGNAL_ICON = "signalIcon";

    private static final String ATTR_WIDTH = "width";

    private static final String ATTR_FORMAT_TYPE = "formatType";

    private static final String ATTR_NO_WORKFLOW_LABEL = "noWorkflowLabel";

    private static final String ATTR_AFFECTED_CONSTRAINT = "affectedConstraint";

    private static final String ATTR_CONNECTION_TYPE = "connectionType";

    private static final String ATTR_CONDITION = "condition";

    private static final String ATTR_TARGET_PROPERTY = "targetProperty";

    private static final String ATTR_MODIFICATION = "modification";

    private static final String ATTR_SELECTION = "selection";

    private static final String ATTR_PARAMETER = "parameter";

    private static final String ATTR_AUTO_COMPLETION = "autoCompletionFilter";

    private static final String ATTR_DIRTY_STATE_NEEDED = "dirtyStateNeeded";

    private static final String ATTR_EXTENSION_FILTER = "extensionFilter";

    private static final String ATTR_IS_TECHNICAL = "isTechnical";

    private static final String ATTR_URL = "url";

    private static final String ATTR_LINK_TYPE = "linkType";

    private static final String CONTROL_TYPE_PICKER = "PICKER";

    private static final String CONTROL_TYPE_LINK = "LINK";

    private static final String CONTROL_TYPE_CURRENCY = "CURRENCY";

    private static final String CONTROL_TYPE_TEXT = "TEXT";

    private static final String CONTROL_TYPE_DROPDOWN = "DROPDOWN";

    private static final String CONTROL_TYPE_DATE = "DATE";

    private static final String CONTROL_TYPE_CHECKBOX = "CHECKBOX";

    private static final String CONTROL_TYPE_RADIO = "RADIO";

    private static final String CONTROL_LABEL = "LABEL";

    private static final String CONTROL_TYPE_IMAGE = "IMAGE";

    private static final String CONTROL_PASSWORD = "PASSWORD";

    private static final String CONTROL_TYPE_TEXTAREA = "TEXTAREA";

    private static final String CONTROL_TYPE_FILE = "FILE";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EditorExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing user interface editor configuration.");

            EditorExtension extension = new EditorExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element editorElement = super.getElementsByTagName(element, ELEMENT_EDITOR).get(0);

            extension.setResolver(super.getClassProperty(editorElement, ATTR_RESOLVER));
            extension.setLabel(super.getStringProperty(editorElement, ATTR_LABEL));
            extension.setTooltip(super.getStringProperty(editorElement, ATTR_TOOLTIP));
            extension.setIcon(super.getStringProperty(editorElement, ATTR_ICON));

            List<Element> workItemActionsElements = super
                    .getElementsByTagName(editorElement, ELEMENT_WORK_ITEM_ACTIONS);
            if (!workItemActionsElements.isEmpty()) {
                WorkItemActionsExtension workItemActionsExtension = this
                        .parseWorkItemActionsExtension(workItemActionsElements.get(0));
                extension.setActions(workItemActionsExtension);
            }

            List<Element> workflowElements = super.getElementsByTagName(editorElement, ELEMENT_WORKFLOW);
            if (!workflowElements.isEmpty()) {
                WorkItemWorkflowExtension workItemWorkflowExtension = this.parseWorkflowExtension(workflowElements
                        .get(0));
                extension.setWorkflowExtension(workItemWorkflowExtension);
            }

            List<Element> browserElements = super.getElementsByTagName(editorElement, ELEMENT_BROWSER);
            for (Element browserElement : browserElements) {
                WorkItemBrowserExtension browserExtension = this.parseBrowserExtension(browserElement);
                extension.getBrowsers().add(browserExtension);
            }

            List<Element> buttonElements = super.getChildren(editorElement, ELEMENT_BUTTON);
            for (Element buttonElement : buttonElements) {
                EditorButtonExtension buttonExtension = this.parseButtonExtension(buttonElement);
                extension.getButtons().add(buttonExtension);
            }

            List<Element> tabElements = super.getElementsByTagName(editorElement, ELEMENT_TAB);
            for (Element tabElement : tabElements) {
                EditorTabExtension tabExtension = this.parseTabExtension(tabElement);
                extension.getTabs().add(tabExtension);
            }

            List<Element> relationElements = super.getElementsByTagName(editorElement, ELEMENT_RELATION);
            for (Element relationElement : relationElements) {
                EditorRelationExtension relationExtension = this.parseRelationExtension(relationElement);
                extension.getRelations().add(relationExtension);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Parse the WorkItemActionsExtension element
     * 
     * @param element
     *            the element to be parsed
     * @return the parsed element
     */
    private WorkItemActionsExtension parseWorkItemActionsExtension(Element element) throws ExtensionParserException {
        WorkItemActionsExtension actionExtensions = new WorkItemActionsExtension();

        List<Element> actionElements = super.getChildren(element);
        for (Element action : actionElements) {
            WorkItemActionExtension actionExtension = new WorkItemActionExtension();
            actionExtension.setActionType(super.getEnumerationProperty(action, ATTR_TYPE));
            actionExtension.setActionId(super.getStringProperty(action, ATTR_ACTIONID));
            actionExtensions.getActions().add(actionExtension);
        }

        return actionExtensions;
    }

    /**
     * Parse the work item workflow element.
     * 
     * @param workflowElement
     *            the workflow element to parse
     * @return the parsed browser extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     */
    private WorkItemWorkflowExtension parseWorkflowExtension(Element workflowElement) throws ExtensionParserException,
            ExtensionException {

        WorkItemWorkflowExtension extension = new WorkItemWorkflowExtension();
        extension.setLabel(super.getStringProperty(workflowElement, ATTR_LABEL));
        extension.setTooltip(super.getStringProperty(workflowElement, ATTR_TOOLTIP));
        extension.setNoWorkflowLabel(super.getStringProperty(workflowElement, ATTR_NO_WORKFLOW_LABEL));
        extension.setSignalIcon(super.getStringProperty(workflowElement, ATTR_SIGNAL_ICON));
        extension.setWorkflowIcon(super.getStringProperty(workflowElement, ATTR_WORKFLOW_ICON));
        extension.setActionId(super.getStringProperty(workflowElement, ATTR_ACTIONID));

        return extension;
    }

    /**
     * Parse the work item browser element.
     * 
     * @param browserElement
     *            the browser element to parse
     * @return the parsed browser extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     */
    private WorkItemBrowserExtension parseBrowserExtension(Element browserElement) throws ExtensionParserException,
            ExtensionException {
        WorkItemBrowserExtension browserExtension = new WorkItemBrowserExtension();
        browserExtension.setIcon(super.getStringProperty(browserElement, ATTR_ICON));
        browserExtension.setLabel(super.getStringProperty(browserElement, ATTR_LABEL));
        browserExtension.setTooltip(super.getStringProperty(browserElement, ATTR_TOOLTIP));
        browserExtension.setBrowserId(super.getStringProperty(browserElement, ATTR_REFID));

        List<WorkItemBrowserEntryExtension> browserItemExtension = this.getBrowserItemExtension(browserElement);
        browserExtension.getElements().addAll(browserItemExtension);
        return browserExtension;
    }

    /**
     * Build the Tree of the Browser items recursive
     * 
     * @param browserElement
     *            parent element
     * 
     * @return List directly child extensions of given element
     * @throws ExtensionParserException
     * @throws ExtensionException
     */
    private List<WorkItemBrowserEntryExtension> getBrowserItemExtension(Element browserElement)
            throws ExtensionParserException, ExtensionException {
        // try to parse entries if any
        List<WorkItemBrowserEntryExtension> retVal = new ArrayList<WorkItemBrowserEntryExtension>();

        List<Element> browserElements = super.getChildren(browserElement);

        for (Element entryElement : browserElements) {
            WorkItemBrowserEntryExtension entryExtension = new WorkItemBrowserEntryExtension();
            entryExtension.setEntryId(super.getStringProperty(entryElement, ATTR_ID));
            entryExtension.setEntryLabel(super.getStringProperty(entryElement, ATTR_LABEL));
            entryExtension.setEntryTooltip(super.getStringProperty(entryElement, ATTR_TOOLTIP));
            entryExtension.setEntryIcon(super.getStringProperty(entryElement, ATTR_ICON));
            entryExtension.setProperty(super.getStringProperty(entryElement, ATTR_PROPERTY));
            entryExtension.setPropertyLabel(super.getStringProperty(entryElement, ATTR_PROPERTY_LABEL));
            entryExtension.setPropertyTooltip(super.getStringProperty(entryElement, ATTR_PROPERTY_TOOLTIP));
            entryExtension.setPropertyGrouping(super.getStringProperty(entryElement, ATTR_GROUPING));
            entryExtension.setAction(super.getStringProperty(entryElement, ATTR_ACTIONID));

            List<WorkItemBrowserEntryExtension> children = this.getBrowserItemExtension(entryElement);
            entryExtension.getElements().addAll(children);
            retVal.add(entryExtension);
        }

        return retVal;
    }

    /**
     * Parse the action reference element.
     * 
     * @param actionElement
     *            the action element to parse
     * @return the parsed action extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     *             when the tab fields cannot be parsed
     */
    private ListButtonGroupExtension parseListButtonGroupExtension(Element actionElement)
            throws ExtensionParserException, ExtensionException {

        ListButtonGroupExtension buttonGroupExtension = new ListButtonGroupExtension();

        String buttonId = actionElement.getAttribute(ATTR_ID);
        buttonGroupExtension.setIdentifier(buttonId);
        buttonGroupExtension.setLabel(super.getStringProperty(actionElement, ATTR_LABEL));
        buttonGroupExtension.setTooltip(super.getStringProperty(actionElement, ATTR_TOOLTIP));
        buttonGroupExtension.setIcon(super.getStringProperty(actionElement, ATTR_ICON));
        buttonGroupExtension.setSelection(super.getBooleanProperty(actionElement, ATTR_SELECTION, false));
        buttonGroupExtension.setModification(super.getEnumerationProperty(actionElement, ATTR_MODIFICATION,
                MODIFICATION_DEFAULT));

        List<Element> buttons = super.getChildren(actionElement, ELEMENT_BUTTON);
        for (Element button : buttons) {
            ListButtonExtension buttonExt = this.parseListButtonExtension(button);
            buttonGroupExtension.getButtonList().add(buttonExt);
        }

        return buttonGroupExtension;
    }

    /**
     * Parse the action reference element.
     * 
     * @param actionElement
     *            the action element to parse
     * @return the parsed action extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     *             when the tab fields cannot be parsed
     */
    private EditorButtonExtension parseButtonExtension(Element actionElement) throws ExtensionParserException,
            ExtensionException {

        EditorButtonExtension buttonExtension = new EditorButtonExtension();

        String buttonId = actionElement.getAttribute(ATTR_ID);
        buttonExtension.setIdentifier(buttonId);
        buttonExtension.setLabel(super.getStringProperty(actionElement, ATTR_LABEL));
        buttonExtension.setTooltip(super.getStringProperty(actionElement, ATTR_TOOLTIP));
        buttonExtension.setIcon(super.getStringProperty(actionElement, ATTR_ICON));
        buttonExtension.setActionId(super.getStringProperty(actionElement, ATTR_ACTIONID));
        buttonExtension.setParameter(super.getStringProperty(actionElement, ATTR_PARAMETER));
        buttonExtension.setDirtyStateNeeded(super.getBooleanProperty(actionElement, ATTR_DIRTY_STATE_NEEDED, false));

        return buttonExtension;
    }

    /**
     * Parse the relation action reference element.
     * 
     * @param actionElement
     *            the action element to parse
     * @return the parsed action extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     *             when the tab fields cannot be parsed
     */
    private ListButtonExtension parseListButtonExtension(Element actionElement) throws ExtensionParserException,
            ExtensionException {

        ListButtonExtension buttonExtension = new ListButtonExtension();

        String buttonId = actionElement.getAttribute(ATTR_ID);
        buttonExtension.setIdentifier(buttonId);
        buttonExtension.setLabel(super.getStringProperty(actionElement, ATTR_LABEL));
        buttonExtension.setTooltip(super.getStringProperty(actionElement, ATTR_TOOLTIP));
        buttonExtension.setIcon(super.getStringProperty(actionElement, ATTR_ICON));
        buttonExtension.setActionId(super.getStringProperty(actionElement, ATTR_ACTIONID));
        buttonExtension.setParameter(super.getStringProperty(actionElement, ATTR_PARAMETER));

        buttonExtension.setSelection(super.getBooleanProperty(actionElement, ATTR_SELECTION, false));
        buttonExtension.setModification(super.getEnumerationProperty(actionElement, ATTR_MODIFICATION,
                MODIFICATION_DEFAULT));

        return buttonExtension;
    }

    /**
     * Parse the editor tab element.
     * 
     * @param tabElement
     *            the tab element to parse
     * @return the parsed tab extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     *             when the tab fields cannot be parsed
     */
    private EditorTabExtension parseTabExtension(Element tabElement) throws ExtensionParserException,
            ExtensionException {

        EditorTabExtension tabExtension = new EditorTabExtension();
        String tabId = tabElement.getAttribute(ATTR_ID);
        tabExtension.setIdentifier(tabId);
        tabExtension.setLabel(super.getStringProperty(tabElement, ATTR_LABEL));
        tabExtension.setTooltip(super.getStringProperty(tabElement, ATTR_TOOLTIP));
        tabExtension.setIcon(super.getStringProperty(tabElement, ATTR_ICON));
        tabExtension.setGrid(super.getStringProperty(tabElement, ATTR_GRID));
        tabExtension.setIsTechnical(super.getBooleanProperty(tabElement, ATTR_IS_TECHNICAL));

        for (Element controlElement : super.getChildren(tabElement)) {
            String tagName = controlElement.getTagName();
            EditorControlExtension controlExtension = this.parseControlExtension(tagName, controlElement);
            tabExtension.getFields().add(controlExtension);
        }

        return tabExtension;
    }

    /**
     * Parse the editor control element.
     * 
     * @param tagName
     *            the name of the tag to parse
     * @param controlElement
     *            the control element to parse
     * 
     * @return the parsed editor control
     * 
     * @throws ExtensionParserException
     *             when the controls cannot be parsed
     * @throws ExtensionException
     *             if extension does not valid
     */
    private EditorControlExtension parseControlExtension(String tagName, Element controlElement)
            throws ExtensionParserException, ExtensionException {

        EditorControlExtension controlExtension = null;

        if (tagName.equalsIgnoreCase(CONTROL_TYPE_CURRENCY)) {
            controlExtension = this.parseCurrencyControlExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_LINK)) {
            controlExtension = this.parseLinkControlExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_PICKER)) {
            controlExtension = this.parsePickerControlExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_TEXT)) {
            controlExtension = this.parseTextControlExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_TEXTAREA)) {
            controlExtension = this.parseTextAreaControlExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_DATE)) {
            controlExtension = new DateControlExtension();
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_RADIO)) {
            controlExtension = new RadioControlExtension();
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_CHECKBOX)) {
            controlExtension = new CheckBoxControlExtension();
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_DROPDOWN)) {
            controlExtension = new DropDownControlExtension();
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_IMAGE)) {
            controlExtension = this.parseImageControlExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_FILE)) {
            controlExtension = this.parseFileControlExtension(controlElement);
        } else if (tagName.equalsIgnoreCase(CONTROL_PASSWORD)) {
            controlExtension = new PasswordControlExtension();
        } else if (tagName.equalsIgnoreCase(CONTROL_LABEL)) {
            controlExtension = new LabelControlExtension();
        } else {
            throw new ExtensionParserException("The tag name " + tagName + " is not supported yet");
        }

        // Parse general attributes
        controlExtension = this.parseDefaultAttributes(controlExtension, controlElement);

        // Set Type
        EnumerationProperty controlType = new EnumerationProperty();
        controlType.setValue(tagName);
        controlExtension.setType(controlType);

        return controlExtension;
    }

    /**
     * parses a new file control extension
     * 
     * @param controlElement
     *            element to parse
     * @return parsed extension
     * @throws ExtensionParserException
     *             by errors in parsing
     */
    private EditorControlExtension parseFileControlExtension(Element controlElement) throws ExtensionParserException {

        FileControlExtension fileExtension = new FileControlExtension();
        fileExtension.setUploadPath(super.getStringProperty(controlElement, ATTR_UPLOAD_PATH));
        fileExtension.setExtensionFilter(super.getStringProperty(controlElement, ATTR_EXTENSION_FILTER));

        return fileExtension;
    }

    /**
     * parses a new image control extension
     * 
     * @param controlElement
     *            element to parse
     * @return parsed extension
     * @throws ExtensionParserException
     *             by errors in parsing
     */
    private EditorControlExtension parseImageControlExtension(Element controlElement) throws ExtensionParserException {

        ImageControlExtension textControlExtension = new ImageControlExtension();
        textControlExtension.setUploaderDialogId(super.getStringProperty(controlElement, ATTR_UPLOAD_DIALOG_ID));
        textControlExtension.setUploadPath(super.getStringProperty(controlElement, ATTR_UPLOAD_PATH));

        return textControlExtension;
    }

    /**
     * Parses the text control extension
     * 
     * @param controlElement
     *            the element to be parsed
     * @return parsed extension
     * @throws ExtensionParserException
     *             if errors by parsing
     */
    private EditorControlExtension parseTextControlExtension(Element controlElement) throws ExtensionParserException {
        TextControlExtension textControlExtension = new TextControlExtension();
        textControlExtension.setRegex(super.getStringProperty(controlElement, ATTR_REGEX));
        textControlExtension.setFormatType(super.getEnumerationProperty(controlElement, ATTR_FORMAT_TYPE));

        return textControlExtension;
    }

    /**
     * Parses the text control extension
     * 
     * @param controlElement
     *            the element to be parsed
     * @return parsed extension
     * @throws ExtensionParserException
     *             if errors by parsing
     */
    private EditorControlExtension parseTextAreaControlExtension(Element controlElement)
            throws ExtensionParserException {
        TextAreaControlExtension textControlExtension = new TextAreaControlExtension();
        textControlExtension.setRegex(super.getStringProperty(controlElement, ATTR_REGEX));
        textControlExtension.setFormatType(super.getEnumerationProperty(controlElement, ATTR_FORMAT_TYPE));
        textControlExtension.setUploadPath(super.getStringProperty(controlElement, ATTR_UPLOAD_PATH));

        return textControlExtension;
    }

    /**
     * Parses the picker control extension
     * 
     * @param controlElement
     *            the element to be parsed
     * 
     * @return parsed extension
     * 
     * @throws ExtensionParserException
     *             if errors by parsing
     * @throws ExtensionException
     *             if cannot get filter parameter mapping
     */
    private EditorControlExtension parsePickerControlExtension(Element controlElement) throws ExtensionParserException,
            ExtensionException {

        PickerControlExtension pickerControlExtenstion = new PickerControlExtension();
        pickerControlExtenstion.setMultipleSelection(super.getBooleanProperty(controlElement, ATTR_MULTIPLE_SELECTION));
        pickerControlExtenstion.setDisplayPath(super.getStringProperty(controlElement, ATTR_DISPLAY_PATH));
        pickerControlExtenstion.setPickerDialog(super.getStringProperty(controlElement, ATTR_PICKER_DIALOG));
        pickerControlExtenstion.setOpenAction(super.getStringProperty(controlElement, ATTR_OPEN_ACTION));
        pickerControlExtenstion.setAutoCompletionFilter(super.getStringProperty(controlElement, ATTR_AUTO_COMPLETION));

        QueryParameterMappingExtension queryParameterMappingExtension = new QueryParameterMappingExtension();
        pickerControlExtenstion.setQueryParameterMapping(queryParameterMappingExtension);

        List<Element> mappingSet = super.getElementsByTagName(controlElement, ELEMENT_QUERY_PARAMETER_MAPPING);
        if (mappingSet != null && mappingSet.size() > 0) {

            List<Element> parameters = super.getElementsByTagName(mappingSet.get(0), ELEMENT_PARAMETER);
            for (Element parameter : parameters) {
                QueryParameterExtension queryParameter = new QueryParameterExtension();
                queryParameter.setName(super.getStringProperty(parameter, ATTR_NAME));
                queryParameter.setTargetProperty(super.getStringProperty(parameter, ATTR_TARGET_PROPERTY));
                queryParameterMappingExtension.getParameters().add(queryParameter);
            }
        }

        return pickerControlExtenstion;
    }

    /**
     * Parses the link control extension
     * 
     * @param controlElement
     *            the element to be parsed
     * @return parsed extension
     * @throws ExtensionParserException
     *             if errors by parsing
     */
    private EditorControlExtension parseLinkControlExtension(Element controlElement) throws ExtensionParserException {
        LinkControlExtension linkControlExtension = new LinkControlExtension();
        linkControlExtension.setAction(super.getStringProperty(controlElement, ATTR_ACTIONID));
        linkControlExtension.setIcon(super.getStringProperty(controlElement, ATTR_ICON));
        linkControlExtension.setUrl(super.getStringProperty(controlElement, ATTR_URL));
        linkControlExtension.setLinkType(super.getEnumerationProperty(controlElement, ATTR_LINK_TYPE));

        return linkControlExtension;
    }

    /**
     * Parses the currency control extension
     * 
     * @param controlElement
     *            the element to be parsed
     * @return parsed extension
     * @throws ExtensionParserException
     *             if errors by parsing
     */
    private EditorControlExtension parseCurrencyControlExtension(Element controlElement)
            throws ExtensionParserException {
        CurrencyControlExtension currencyControlExtension = new CurrencyControlExtension();
        currencyControlExtension.setMaximumFractionDigits(super.getIntegerProperty(controlElement, ATTR_MAXFRACT, 120));
        currencyControlExtension.setMinimumFractionDigits(super.getIntegerProperty(controlElement, ATTR_MINFRACT, 2));
        currencyControlExtension.setMinimumIntegerDigits(super.getIntegerProperty(controlElement, ATTR_MININT, 1));
        currencyControlExtension.setMaximumIntegerDigits(super.getIntegerProperty(controlElement, ATTR_MAXINT, 120));
        currencyControlExtension.setSigned(super.getBooleanProperty(controlElement, ATTR_SIGNED));
        currencyControlExtension.setSeparator(super.getStringProperty(controlElement, ATTR_SEPARATOR));

        return currencyControlExtension;
    }

    /**
     * Parses the default attributes that are valid for all controls
     * 
     * @param controlExtension
     *            the extension to parse to
     * @param controlElement
     *            the control element to parse from
     * @return the parsed control element
     * @throws ExtensionParserException
     *             if cannot parse property
     * @throws ExtensionException
     *             if extension is invalid
     */
    private EditorControlExtension parseDefaultAttributes(EditorControlExtension controlExtension,
            Element controlElement) throws ExtensionParserException, ExtensionException {
        String controlId = controlElement.getAttribute(ATTR_ID);
        controlExtension.setIdentifier(controlId);
        controlExtension.setEditable(super.getBooleanProperty(controlElement, ATTR_EDITABLE, true));
        controlExtension.setProperty(super.getStringProperty(controlElement, ATTR_PROPERTY));
        controlExtension.setLabel(super.getStringProperty(controlElement, ATTR_LABEL));
        controlExtension.setTooltip(super.getStringProperty(controlElement, ATTR_TOOLTIP));
        controlExtension.setPosition(super.getStringProperty(controlElement, ATTR_POSITION));
        controlExtension.setHint(super.getStringProperty(controlElement, ATTR_HINT));

        // Parse dependency set if any
        DependencySetExtension dependencySetExtension = this.parseDependencySetExtension(controlElement);
        controlExtension.setDependencySet(dependencySetExtension);

        return controlExtension;
    }

    /**
     * Parse the dependency set extension
     * 
     * @param controlElement
     *            the element
     * @return dependency set extension
     * 
     * @throws ExtensionParserException
     * @throws ExtensionException
     */
    private DependencySetExtension parseDependencySetExtension(Element controlElement) throws ExtensionParserException,
            ExtensionException {
        List<Element> dependencySetElements = super.getElementsByTagName(controlElement, ELEMENT_DEPENDENCY_SET);
        DependencySetExtension dependencySetExtension = new DependencySetExtension();

        if (dependencySetElements.size() > 0) {
            Element dependencySetElement = dependencySetElements.get(0);
            dependencySetExtension.setAffectedConstraint(super.getEnumerationProperty(dependencySetElement,
                    ATTR_AFFECTED_CONSTRAINT));
            dependencySetExtension.setConnectionType(super.getEnumerationProperty(dependencySetElement,
                    ATTR_CONNECTION_TYPE));

            List<Element> dependencyElements = super.getElementsByTagName(dependencySetElement, ELEMENT_DEPENDENCY);
            for (Element dependencyElement : dependencyElements) {
                DependencyExtension dependencyExtension = new DependencyExtension();
                dependencyExtension.setCondition(super.getEnumerationProperty(dependencyElement, ATTR_CONDITION));
                dependencyExtension.setTargetProperty(super.getStringProperty(dependencyElement, ATTR_TARGET_PROPERTY));
                dependencySetExtension.getDependencies().add(dependencyExtension);
            }
        }
        return dependencySetExtension;
    }

    /**
     * Parse the editor relation element.
     * 
     * @param relationElement
     *            the relation element to parse
     * @return the parsed relation extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     *             when the relation columns cannot be parsed
     */
    private EditorRelationExtension parseRelationExtension(Element relationElement) throws ExtensionParserException,
            ExtensionException {

        EditorRelationExtension relationExtension = new EditorRelationExtension();
        String relationId = relationElement.getAttribute(ATTR_ID);
        relationExtension.setIdentifier(relationId);
        relationExtension.setProperty(super.getStringProperty(relationElement, ATTR_PROPERTY));
        relationExtension.setLabel(super.getStringProperty(relationElement, ATTR_LABEL));
        relationExtension.setTooltip(super.getStringProperty(relationElement, ATTR_TOOLTIP));
        relationExtension.setDoubleclickAction(super.getStringProperty(relationElement, ATTR_DOUBLECLICK_ACTION));
        relationExtension.setLoadAction(super.getStringProperty(relationElement, ATTR_LOAD_ACTION));
        relationExtension.setIsTechnical(super.getBooleanProperty(relationElement, ATTR_IS_TECHNICAL));

        DependencySetExtension dependencyExtension = this.parseDependencySetExtension(relationElement);
        relationExtension.setDependencySet(dependencyExtension);

        List<Element> children = super.getChildren(relationElement);
        for (Element child : children) {
            String tagName = child.getTagName();
            if (tagName.equals(ELEMENT_BUTTON_GROUP)) {
                ListButtonGroupExtension buttonGroupExtension = this.parseListButtonGroupExtension(child);
                relationExtension.getButtons().add(buttonGroupExtension);
            } else if (tagName.equals(ELEMENT_BUTTON)) {
                ListButtonExtension buttonExtension = this.parseListButtonExtension(child);
                relationExtension.getButtons().add(buttonExtension);
            } else if (tagName.equals(ELEMENT_COLUMN)) {
                ColumnExtension columnExtension = this.parseColumnExtension(child);
                relationExtension.getColumns().add(columnExtension);
            } else if (tagName.equals(ELEMENT_MENU)) {
                MenuButtonExtension menuExtension = this.parseMenuExtension(child);
                relationExtension.setMenuButton(menuExtension);
            } else {
                throw new ExtensionParserException("Relation Tab has unallowed content '" + tagName + "'.");
            }
        }

        return relationExtension;
    }

    /**
     * Parses the given element and returns the list of buttons for the menu
     * 
     * @param element
     *            the element to be parsed
     * @return list with buttons and button groups
     * 
     * @throws ExtensionException
     * @throws ExtensionParserException
     */
    private MenuButtonExtension parseMenuExtension(Element element) throws ExtensionParserException, ExtensionException {
        MenuButtonExtension retVal = new MenuButtonExtension();

        List<Element> menuChildren = super.getChildren(element);
        for (Element menuElement : menuChildren) {
            String tagName = menuElement.getTagName();

            if (tagName.equals(ELEMENT_BUTTON)) {
                ListButtonExtension buttonExtension = this.parseListButtonExtension(menuElement);
                retVal.getButtons().add(buttonExtension);
            } else if (tagName.equals(ELEMENT_BUTTON_GROUP)) {
                ListButtonGroupExtension buttonGroupExtension = this.parseListButtonGroupExtension(menuElement);
                retVal.getButtons().add(buttonGroupExtension);
            }
        }
        return retVal;
    }

    /**
     * Parses the relation Column extension
     * 
     * @param columnElement
     *            element to be parsed
     * @return parsed column
     * @throws ExtensionParserException
     */
    private ColumnExtension parseColumnExtension(Element columnElement) throws ExtensionParserException {
        ColumnExtension columnExtension = new ColumnExtension();
        columnExtension.setIdentifier(columnElement.getAttribute(ATTR_ID));
        columnExtension.setLabel(super.getStringProperty(columnElement, ATTR_LABEL));
        columnExtension.setTooltip(super.getStringProperty(columnElement, ATTR_TOOLTIP));
        columnExtension.setProperty(super.getStringProperty(columnElement, ATTR_PROPERTY));
        columnExtension.setVisible(super.getBooleanProperty(columnElement, ATTR_VISIBLE));
        columnExtension.setSortable(super.getBooleanProperty(columnElement, ATTR_SORTABLE));
        columnExtension.setRenderer(super.getClassProperty(columnElement, ATTR_RENDERER));
        columnExtension.setLayout(super.getEnumerationProperty(columnElement, ATTR_LAYOUT));
        columnExtension.setWidth(super.getStringProperty(columnElement, ATTR_WIDTH));
        return columnExtension;
    }
}
