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

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.ActionWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.CalendarWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.DialogWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.DynamicTextWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.ImageWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.IndicatorWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.SearchWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.TextWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * WidgetExtensionParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class WidgetExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_CALENDAR = "CALENDAR";

    private static final String ELEMENT_SEARCH = "SEARCH";

    private static final String ELEMENT_ACTION = "ACTION";

    private static final String ELEMENT_IMAGE = "IMAGE";

    private static final String ELEMENT_WIDGET = "widget";

    private static final String ELEMENT_INDICATOR = "INDICATOR";

    private static final String ELEMENT_DYNAMICTEXT = "DYNAMICTEXT";

    private static final String ELEMENT_DIALOG = "DIALOG";

    private static final String ELEMENT_TEXT = "TEXT";

    private static final String ATTR_CONTENT_PROVIDER = "contentProvider";

    private static final String ATTR_DISPLAY_TYPE = "displayType";

    private static final String ATTR_LINK = "link";

    private static final String ATTR_ACTION = "action";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_IMAGE = "image";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_INDICATION = "indication";

    private static final String ATTR_TEXT = "text";

    private static final String ATTR_ID = "id";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_DIALOG_ID = "dialogId";

    private static final String ATTR_ICON = "icon";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(StatusBarExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing widget configuration");

            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));

            Element widgetElement = super.getElementsByTagName(element, ELEMENT_WIDGET).get(0);

            // type specific element (dynamictext, text etc.)
            Element widget = super.getChildren(widgetElement).get(0);

            WidgetExtension extension = this.parseWidgetExtension(widget);
            extension.setIdentifier(id);

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

    /**
     * Parses the element according to the tag name
     * 
     * @param tagName
     *            tag name of the element
     * @param widgetElement
     *            the element to be parsed
     * @return widget extension
     * @throws ExtensionParserException
     *             if cannot parse
     */
    private WidgetExtension parseWidgetExtension(Element widgetElement) throws ExtensionParserException {
        String tagName = widgetElement.getTagName();

        WidgetExtension extension;

        if (tagName.equalsIgnoreCase(ELEMENT_TEXT)) {
            TextWidgetExtension ext = new TextWidgetExtension();
            ext.setLink(super.getStringProperty(widgetElement, ATTR_LINK));
            ext.setText(super.getStringProperty(widgetElement, ATTR_TEXT));
            extension = ext;
        } else if (tagName.equalsIgnoreCase(ELEMENT_DYNAMICTEXT)) {
            DynamicTextWidgetExtension ext = new DynamicTextWidgetExtension();
            ext.setContentProvider(super.getClassProperty(widgetElement, ATTR_CONTENT_PROVIDER));
            extension = ext;
        } else if (tagName.equalsIgnoreCase(ELEMENT_DIALOG)) {
            DialogWidgetExtension ext = new DialogWidgetExtension();
            ext.setText(super.getStringProperty(widgetElement, ATTR_TEXT));
            ext.setDialogId(super.getStringProperty(widgetElement, ATTR_DIALOG_ID));
            extension = ext;
        } else if (tagName.equalsIgnoreCase(ELEMENT_INDICATOR)) {
            IndicatorWidgetExtension ext = new IndicatorWidgetExtension();
            ext.setIndication(super.getEnumerationProperty(widgetElement, ATTR_INDICATION));
            extension = ext;
        } else if (tagName.equalsIgnoreCase(ELEMENT_IMAGE)) {
            ImageWidgetExtension ext = new ImageWidgetExtension();
            ext.setName(super.getStringProperty(widgetElement, ATTR_NAME));
            ext.setImage(super.getStringProperty(widgetElement, ATTR_IMAGE));
            ext.setTooltip(super.getStringProperty(widgetElement, ATTR_TOOLTIP));
            ext.setLink(super.getStringProperty(widgetElement, ATTR_LINK));
            extension = ext;
        } else if (tagName.equalsIgnoreCase(ELEMENT_ACTION)) {
            ActionWidgetExtension ext = new ActionWidgetExtension();
            ext.setAction(super.getStringProperty(widgetElement, ATTR_ACTION));
            ext.setText(super.getStringProperty(widgetElement, ATTR_TEXT));
            ext.setTooltip(super.getStringProperty(widgetElement, ATTR_TOOLTIP));
            ext.setIcon(super.getStringProperty(widgetElement, ATTR_ICON));
            extension = ext;
        } else if (tagName.equalsIgnoreCase(ELEMENT_SEARCH)) {
            SearchWidgetExtension ext = new SearchWidgetExtension();
            ext.setAction(super.getStringProperty(widgetElement, ATTR_ACTION));
            extension = ext;
        } else if (tagName.equalsIgnoreCase(ELEMENT_CALENDAR)) {
            CalendarWidgetExtension ext = new CalendarWidgetExtension();
            ext.setDisplayType(super.getEnumerationProperty(widgetElement, ATTR_DISPLAY_TYPE));
            extension = ext;
        } else {
            TextWidgetExtension ext = new TextWidgetExtension();
            ext.setLink(super.getStringProperty(widgetElement, ATTR_LINK));
            ext.setText(super.getStringProperty(widgetElement, ATTR_TEXT));
            extension = ext;
        }

        // Set Type
        EnumerationProperty widgetType = new EnumerationProperty();
        widgetType.setValue(tagName);
        extension.setType(widgetType);

        extension.setLabel(super.getStringProperty(widgetElement, ATTR_LABEL));

        return extension;
    }
}
