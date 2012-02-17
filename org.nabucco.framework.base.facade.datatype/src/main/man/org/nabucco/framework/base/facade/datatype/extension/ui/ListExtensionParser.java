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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter.FilterReferenceExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionsExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.list.ListExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the list work item extension of the nabucco user interface.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ListExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_LIST = "list";

    private static final String ELEMENT_WORK_ITEM_ACTIONS = "workItemActions";

    private static final String ELEMENT_BROWSER = "browser";

    private static final String ELEMENT_BUTTON = "button";

    private static final String ELEMENT_COLUMN = "column";

    private static final String ELEMENT_FILTER = "filter";

    private static final String ATTR_GROUPING = "grouping";

    private static final String ATTR_PROPERTY_LABEL = "propertyLabel";

    private static final String ATTR_PROPERTY_TOOLTIP = "propertyTooltip";

    private static final String ATTR_RENDERER = "renderer";

    private static final String ATTR_LAYOUT = "layout";

    private static final String ATTR_ID = "id";
    
    private static final String ATTR_TYPE = "type";

    private static final String ATTR_REFID = "refId";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_ICON = "icon";

    private static final String ATTR_ACTIONID = "actionId";

    private static final String ATTR_PROPERTY = "property";

    private static final String ATTR_VISIBLE = "visible";

    private static final String ATTR_SORTABLE = "sortable";

    private static final String ATTR_DEFAULT = "default";

    private static final String ATTR_WIDTH = "width";
    
    private static final String ATTR_DOUBLECLICK_ACTION = "doubleclickAction";

    private static final String ATTR_LOAD_ACTION = "loadAction";

    private static final String ATTR_PARAMETER = "parameter";

    private static final String ATTR_MODIFICATION = "modification";

    private static final String ATTR_SELECTION = "selection";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ListExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing user interface list configuration.");

            ListExtension extension = new ListExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element listElement = super.getElementsByTagName(element, ELEMENT_LIST).get(0);

            extension.setLabel(super.getStringProperty(listElement, ATTR_LABEL));
            extension.setTooltip(super.getStringProperty(listElement, ATTR_TOOLTIP));
            extension.setIcon(super.getStringProperty(listElement, ATTR_ICON));
            extension.setDoubleclickAction(super.getStringProperty(listElement, ATTR_DOUBLECLICK_ACTION));

            List<Element> workItemActionsElements = super.getElementsByTagName(listElement, ELEMENT_WORK_ITEM_ACTIONS);

            if (!workItemActionsElements.isEmpty()) {
                Element actionElement = workItemActionsElements.get(0);
                WorkItemActionsExtension actionExtension = this.parseWorkItemActionsExtension(actionElement);
                extension.setActions(actionExtension);
            }

            List<Element> browserElements = super.getElementsByTagName(listElement, ELEMENT_BROWSER);
            for (Element browserElement : browserElements) {
                WorkItemBrowserExtension browserExtension = this.parseBrowserExtension(browserElement);
                extension.getBrowsers().add(browserExtension);
            }

            List<Element> buttonElements = super.getElementsByTagName(listElement, ELEMENT_BUTTON);
            for (Element buttonElement : buttonElements) {
                ListButtonExtension buttonExtension = this.parseActionExtension(buttonElement);
                extension.getButtons().add(buttonExtension);
            }

            List<Element> columnElements = super.getElementsByTagName(listElement, ELEMENT_COLUMN);
            for (Element relationElement : columnElements) {
                ColumnExtension columnExtension = this.parseColumnExtension(relationElement);
                extension.getColumns().add(columnExtension);
            }

            List<Element> filterElements = super.getElementsByTagName(listElement, ELEMENT_FILTER);
            for (Element filterElement : filterElements) {
                FilterReferenceExtension filterExtension = this.parseFilterReferenceExtension(filterElement);
                extension.getFilters().add(filterExtension);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
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
    private ListButtonExtension parseActionExtension(Element actionElement) throws ExtensionParserException,
            ExtensionException {

        ListButtonExtension buttonExtension = new ListButtonExtension();
        buttonExtension.setIdentifier(actionElement.getAttribute(ATTR_ID));
        buttonExtension.setLabel(super.getStringProperty(actionElement, ATTR_LABEL));
        buttonExtension.setTooltip(super.getStringProperty(actionElement, ATTR_TOOLTIP));
        buttonExtension.setIcon(super.getStringProperty(actionElement, ATTR_ICON));
        buttonExtension.setActionId(super.getStringProperty(actionElement, ATTR_ACTIONID));
        buttonExtension.setParameter(super.getStringProperty(actionElement, ATTR_PARAMETER));

        buttonExtension.setSelection(super.getBooleanProperty(actionElement, ATTR_SELECTION, false));
        buttonExtension.setModification(super.getEnumerationProperty(actionElement, ATTR_MODIFICATION));

        return buttonExtension;
    }

    /**
     * Parse the list column element.
     * 
     * @param columnElement
     *            the column element to parse
     * 
     * @return the parsed relation extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     *             when the relation columns cannot be parsed
     */
    private ColumnExtension parseColumnExtension(Element columnElement) throws ExtensionParserException,
            ExtensionException {
        ColumnExtension columnExtension = new ColumnExtension();
        columnExtension.setIdentifier(columnElement.getAttribute(ATTR_ID));
        columnExtension.setProperty(super.getStringProperty(columnElement, ATTR_PROPERTY));
        columnExtension.setLabel(super.getStringProperty(columnElement, ATTR_LABEL));
        columnExtension.setTooltip(super.getStringProperty(columnElement, ATTR_TOOLTIP));
        columnExtension.setVisible(super.getBooleanProperty(columnElement, ATTR_VISIBLE));
        columnExtension.setSortable(super.getBooleanProperty(columnElement, ATTR_SORTABLE));
        columnExtension.setRenderer(super.getClassProperty(columnElement, ATTR_RENDERER));
        columnExtension.setLayout(super.getEnumerationProperty(columnElement, ATTR_LAYOUT));
        columnExtension.setWidth(super.getStringProperty(columnElement, ATTR_WIDTH));
        return columnExtension;
    }

    /**
     * Parse the list of referenced filters
     * 
     * @param filterElement
     *            the group of filters to be parsed
     * @return QueryFilterReferenceExtension instance
     */
    private FilterReferenceExtension parseFilterReferenceExtension(Element filterElement)
            throws ExtensionParserException, ExtensionException {
        FilterReferenceExtension retVal = new FilterReferenceExtension();
        retVal.setRefId(super.getStringProperty(filterElement, ATTR_REFID));
        retVal.setTooltip(super.getStringProperty(filterElement, ATTR_TOOLTIP));
        retVal.setLabel(super.getStringProperty(filterElement, ATTR_LABEL));
        retVal.setIsDefault(super.getBooleanProperty(filterElement, ATTR_DEFAULT, false));
        retVal.setLoadAction(super.getStringProperty(filterElement, ATTR_LOAD_ACTION));

        return retVal;
    }

}
