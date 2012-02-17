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

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter.FilterReferenceExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.PickerDialogExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * PickerDialogExtensionParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class PickerDialogExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_LOAD_ACTION = "loadAction";

    private static final String ATTR_MESSAGE = "message";

    private static final String ATTR_TITLE = "title";

    private static final String ATTR_ID = "id";

    private static final String ATTR_PICKER_TYPE = "pickerType";

    private static final String ELEMENT_PICKER_DIALOG = "pickerDialog";

    private static final String ELEMENT_BUTTON = "button";

    private static final String ELEMENT_COLUMN = "column";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_REFID = "refId";

    private static final String ATTR_ACTIONID = "actionId";

    private static final String ATTR_PROPERTY = "property";

    private static final String ATTR_VISIBLE = "visible";

    private static final String ATTR_SORTABLE = "sortable";

    private static final String ATTR_ICON = "icon";

    private static final String ELEMENT_FILTER = "filter";

    private static final String ATTR_DEFAULT = "default";

    private static final String ATTR_WIDTH = "width";

    private static final String ATTR_LAYOUT = "layout";

    private static final String ATTR_DOUBLECLICK_ACTION = "doubleclickAction";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DialogExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing user interface editor configuration.");

            PickerDialogExtension extension = new PickerDialogExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element dialogElement = super.getElementsByTagName(element, ELEMENT_PICKER_DIALOG).get(0);
            extension.setPickerType(super.getStringProperty(dialogElement, ATTR_PICKER_TYPE));
            extension.setTitle(super.getStringProperty(dialogElement, ATTR_TITLE));
            extension.setMessage(super.getStringProperty(dialogElement, ATTR_MESSAGE));
            extension.setDoubleclickAction(super.getStringProperty(dialogElement, ATTR_DOUBLECLICK_ACTION));

            // Buttons
            List<Element> buttonElements = super.getElementsByTagName(dialogElement, ELEMENT_BUTTON);
            for (Element buttonElement : buttonElements) {
                ButtonExtension buttonExtension = this.parseButtonExtension(buttonElement);
                extension.getButtons().add(buttonExtension);
            }

            // Columns
            List<Element> columnElements = super.getElementsByTagName(dialogElement, ELEMENT_COLUMN);
            for (Element relationElement : columnElements) {
                ColumnExtension columnExtension = this.parseColumnExtension(relationElement);
                extension.getColumns().add(columnExtension);
            }

            // Filters
            List<Element> filterElements = super.getElementsByTagName(dialogElement, ELEMENT_FILTER);
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
        columnExtension.setLayout(super.getEnumerationProperty(columnElement, ATTR_LAYOUT));
        columnExtension.setWidth(super.getStringProperty(columnElement, ATTR_WIDTH));

        return columnExtension;
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
    private ButtonExtension parseButtonExtension(Element actionElement) throws ExtensionParserException,
            ExtensionException {

        ButtonExtension buttonExtension = new ButtonExtension();
        buttonExtension.setIdentifier(actionElement.getAttribute(ATTR_ID));
        buttonExtension.setLabel(super.getStringProperty(actionElement, ATTR_LABEL));
        buttonExtension.setTooltip(super.getStringProperty(actionElement, ATTR_TOOLTIP));
        buttonExtension.setIcon(super.getStringProperty(actionElement, ATTR_ICON));
        buttonExtension.setActionId(super.getStringProperty(actionElement, ATTR_ACTIONID));

        return buttonExtension;
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
