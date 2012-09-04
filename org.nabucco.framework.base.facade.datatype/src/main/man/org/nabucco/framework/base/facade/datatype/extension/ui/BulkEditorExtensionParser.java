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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.BulkEditorButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.BulkEditorButtonGroupExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.MenuButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionsExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.BulkEditorColumnsExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.BulkEditorExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.BulkEditorColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.DateColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.DropDownColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.PickerColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.TextColumnExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the editor work item extension of the nabucco user interface.
 * 
 * @author Nicolas Moser, PRODYNA AG
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BulkEditorExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_DISPLAY_PATH = "displayPath";

    private static final String ATTR_AUTO_COMPLETION_FILTER = "autoCompletionFilter";

    private static final String MODIFICATION_DEFAULT = "NONE";

    private static final String ATTR_PAGING_SIZE = "pagingSize";

    private static final String ATTR_PAGING = "paging";

    private static final String ELEMENT_BULKEDITOR = "bulkEditor";

    private static final String ELEMENT_WORK_ITEM_ACTIONS = "workItemActions";

    private static final String ELEMENT_BROWSER = "browser";

    private static final String ELEMENT_BUTTON = "button";

    private static final String ELEMENT_BUTTON_GROUP = "buttonGroup";

    private static final String ELEMENT_COLUMNS = "columns";

    private static final String ATTR_ID = "id";

    private static final String ATTR_REFID = "refId";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_ACTIONID = "actionId";

    private static final String ATTR_PROPERTY = "property";

    private static final String ATTR_SORTABLE = "sortable";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_ICON = "icon";

    private static final String ATTR_EDITABLE = "editable";

    private static final String ATTR_GROUPING = "grouping";

    private static final String ATTR_PROPERTY_LABEL = "propertyLabel";

    private static final String ATTR_PROPERTY_TOOLTIP = "propertyTooltip";

    private static final String ATTR_MODIFICATION = "modification";

    private static final String ATTR_SELECTION = "selection";

    private static final String ATTR_PARAMETER = "parameter";

    private static final String CONTROL_TYPE_TEXT = "TEXT";

    private static final String CONTROL_TYPE_DROPDOWN = "DROPDOWN";

    private static final String CONTROL_TYPE_DATE = "DATE";

    private static final Object ELEMENT_MENU = "menu";

    private static final String ATTR_WIDTH = "width";

    private static final String ATTR_DIRTY_STATE_NEEDED = "dirtyStateNeeded";

    private static final String CONTROL_TYPE_PICKER = "picker";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance()
            .getLogger(BulkEditorExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing user interface editor configuration.");

            BulkEditorExtension extension = new BulkEditorExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element bulkEditorElement = super.getElementsByTagName(element, ELEMENT_BULKEDITOR).get(0);

            extension.setLabel(super.getStringProperty(bulkEditorElement, ATTR_LABEL));
            extension.setTooltip(super.getStringProperty(bulkEditorElement, ATTR_TOOLTIP));
            extension.setIcon(super.getStringProperty(bulkEditorElement, ATTR_ICON));

            List<Element> bulkEditorChildren = super.getChildren(bulkEditorElement);
            for (Element childElement : bulkEditorChildren) {
                String tagName = childElement.getTagName();

                if (tagName.equals(ELEMENT_WORK_ITEM_ACTIONS)) {
                    WorkItemActionsExtension actionExtension = this.parseWorkItemActionsExtension(childElement);
                    extension.setActions(actionExtension);
                } else if (tagName.equals(ELEMENT_BROWSER)) {
                    WorkItemBrowserExtension browserExtension = this.parseBrowserExtension(childElement);
                    extension.getBrowsers().add(browserExtension);
                } else if (tagName.equals(ELEMENT_BUTTON)) {
                    BulkEditorButtonExtension buttonExtension = this.parseListButtonExtension(childElement);
                    extension.getButtons().add(buttonExtension);
                } else if (tagName.equals(ELEMENT_BUTTON_GROUP)) {
                    BulkEditorButtonGroupExtension buttonGroupExtension = this
                            .parseListButtonGroupExtension(childElement);
                    extension.getButtons().add(buttonGroupExtension);
                } else if (tagName.equals(ELEMENT_MENU)) {
                    MenuButtonExtension menuExt = this.parseMenuExtension(childElement);
                    extension.setMenuButton(menuExt);
                } else if (tagName.equals(ELEMENT_COLUMNS)) {
                    BulkEditorColumnsExtension columnExtension = this.parseColumnsExtension(childElement);
                    extension.setColumns(columnExtension);
                } else {
                    throw new ExtensionParserException("Bulk editor has unallowed content '" + tagName + "'.");
                }

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
    private BulkEditorButtonGroupExtension parseListButtonGroupExtension(Element actionElement)
            throws ExtensionParserException, ExtensionException {

        BulkEditorButtonGroupExtension buttonGroupExtension = new BulkEditorButtonGroupExtension();

        String buttonId = actionElement.getAttribute(ATTR_ID);
        buttonGroupExtension.setIdentifier(buttonId);
        buttonGroupExtension.setLabel(super.getStringProperty(actionElement, ATTR_LABEL));
        buttonGroupExtension.setTooltip(super.getStringProperty(actionElement, ATTR_TOOLTIP));
        buttonGroupExtension.setIcon(super.getStringProperty(actionElement, ATTR_ICON));
        buttonGroupExtension.setSelection(super.getBooleanProperty(actionElement, ATTR_SELECTION, false));
        buttonGroupExtension.setModification(super.getEnumerationProperty(actionElement, ATTR_MODIFICATION,
                MODIFICATION_DEFAULT));
        buttonGroupExtension.setDirtyStateNeeded(super
                .getBooleanProperty(actionElement, ATTR_DIRTY_STATE_NEEDED, false));

        List<Element> buttons = super.getChildren(actionElement, ELEMENT_BUTTON);
        for (Element button : buttons) {
            BulkEditorButtonExtension buttonExt = this.parseListButtonExtension(button);
            buttonGroupExtension.getButtonList().add(buttonExt);
        }

        return buttonGroupExtension;
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
    private BulkEditorButtonExtension parseListButtonExtension(Element actionElement) throws ExtensionParserException,
            ExtensionException {

        BulkEditorButtonExtension buttonExtension = new BulkEditorButtonExtension();

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
        buttonExtension.setDirtyStateNeeded(super.getBooleanProperty(actionElement, ATTR_DIRTY_STATE_NEEDED, false));

        return buttonExtension;
    }

    /**
     * Parses the default attributes that are valid for all controls
     * 
     * @param columnExtension
     *            the extension to parse to
     * @param columnElement
     *            the control element to parse from
     * @return the parsed control element
     * @throws ExtensionParserException
     *             if cannot parse property
     * @throws ExtensionException
     *             if extension is invalid
     */
    private BulkEditorColumnExtension parseDefaultAttributes(BulkEditorColumnExtension columnExtension,
            Element columnElement) throws ExtensionParserException {
        String controlId = columnElement.getAttribute(ATTR_ID);
        columnExtension.setIdentifier(controlId);
        columnExtension.setEditable(super.getBooleanProperty(columnElement, ATTR_EDITABLE, false));
        columnExtension.setProperty(super.getStringProperty(columnElement, ATTR_PROPERTY));
        columnExtension.setLabel(super.getStringProperty(columnElement, ATTR_LABEL));
        columnExtension.setTooltip(super.getStringProperty(columnElement, ATTR_TOOLTIP));
        columnExtension.setSortable(super.getBooleanProperty(columnElement, ATTR_SORTABLE));
        columnExtension.setWidth(super.getIntegerProperty(columnElement, ATTR_WIDTH, 20));
        return columnExtension;
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
                BulkEditorButtonExtension buttonExtension = this.parseListButtonExtension(menuElement);
                retVal.getButtons().add(buttonExtension);
            } else if (tagName.equals(ELEMENT_BUTTON_GROUP)) {
                BulkEditorButtonGroupExtension buttonGroupExtension = this.parseListButtonGroupExtension(menuElement);
                retVal.getButtons().add(buttonGroupExtension);
            }
        }
        return retVal;
    }

    /**
     * Parses the Columns extension
     * 
     * @param columnsElement
     *            element to be parsed
     * @return parsed column
     * @throws ExtensionParserException
     */
    private BulkEditorColumnsExtension parseColumnsExtension(Element columnsElement) throws ExtensionParserException {
        BulkEditorColumnsExtension extension = new BulkEditorColumnsExtension();

        extension.setPaging(super.getBooleanProperty(columnsElement, ATTR_PAGING));
        extension.setPagingSize(super.getIntegerProperty(columnsElement, ATTR_PAGING_SIZE, 20));

        List<Element> columns = super.getChildren(columnsElement);

        for (Element column : columns) {
            String tagName = column.getTagName();
            BulkEditorColumnExtension bulkColumnExtension = null;

            if (tagName.equalsIgnoreCase(CONTROL_TYPE_TEXT)) {
                TextColumnExtension columnExtension = this.parseTextColumnExtension(column);
                bulkColumnExtension = columnExtension;

            } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_DATE)) {
                DateColumnExtension columnExtension = this.parseDateColumnExtension(column);
                bulkColumnExtension = columnExtension;

            } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_DROPDOWN)) {
                DropDownColumnExtension columnExtension = this.parseDropDownColumnExtension(column);
                bulkColumnExtension = columnExtension;
            } else if (tagName.equalsIgnoreCase(CONTROL_TYPE_PICKER)) {
                PickerColumnExtension columnExtension = this.parsePickerColumnExtension(column);
                bulkColumnExtension = columnExtension;
            } else {
                throw new IllegalArgumentException("Bulk editor contains column elements that are not supported yet.");
            }

            if (bulkColumnExtension == null) {
                throw new IllegalStateException("Cannot parse bulc column extension");
            }

            EnumerationProperty controlType = new EnumerationProperty();
            controlType.setValue(tagName);
            bulkColumnExtension.setType(controlType);

            extension.getColumns().add(bulkColumnExtension);
        }

        return extension;
    }

    /**
     * Parses given element and creates a text column extension
     * 
     * @param columnElement
     *            the element to be parsed
     * @return the parsed extension
     */
    private TextColumnExtension parseTextColumnExtension(Element columnElement) throws ExtensionParserException {
        TextColumnExtension extension = new TextColumnExtension();
        this.parseDefaultAttributes(extension, columnElement);

        return extension;
    }

    /**
     * Parses given element and creates a date column extension
     * 
     * @param columnElement
     *            the element to be parsed
     * @return the parsed extension
     */
    private DateColumnExtension parseDateColumnExtension(Element columnElement) throws ExtensionParserException {
        DateColumnExtension extension = new DateColumnExtension();
        this.parseDefaultAttributes(extension, columnElement);

        return extension;
    }

    /**
     * Parses given element and creates a date column extension
     * 
     * @param columnElement
     *            the element to be parsed
     * @return the parsed extension
     */
    private PickerColumnExtension parsePickerColumnExtension(Element columnElement) throws ExtensionParserException {
        PickerColumnExtension extension = new PickerColumnExtension();

        this.parseDefaultAttributes(extension, columnElement);

        extension.setAutoCompletionFilter(super.getStringProperty(columnElement, ATTR_AUTO_COMPLETION_FILTER));
        extension.setDisplayPath(super.getStringProperty(columnElement, ATTR_DISPLAY_PATH));

        return extension;
    }

    /**
     * Parses given element and creates a drop down column extension
     * 
     * @param columnElement
     *            the element to be parsed
     * @return the parsed extension
     */
    private DropDownColumnExtension parseDropDownColumnExtension(Element columnElement) throws ExtensionParserException {
        DropDownColumnExtension extension = new DropDownColumnExtension();
        this.parseDefaultAttributes(extension, columnElement);

        return extension;
    }
}
