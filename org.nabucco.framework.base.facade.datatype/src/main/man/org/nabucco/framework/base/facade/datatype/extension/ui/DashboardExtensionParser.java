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
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetReferenceExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.DashboardExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the dashboard work item extension of the nabucco user interface.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DashboardExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_DASHBOARD = "dashboard";

    private static final String ELEMENT_BROWSER = "browser";

    private static final String ATTR_ID = "id";

    private static final String ATTR_REFID = "refId";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_ICON = "icon";

    private static final String ELEMENT_WIDGET = "widget";

    private static final String ATTR_POSITION = "position";

    private static final String ATTR_HINT = "hint";

    private static final String ATTR_GRID = "grid";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DashboardExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing user interface dashboard configuration.");

            DashboardExtension extension = new DashboardExtension();
            String id = element.getAttribute(ATTR_ID);
            extension.setIdentifier(id);

            Element dashboardElement = super.getElementsByTagName(element, ELEMENT_DASHBOARD).get(0);

            extension.setLabel(super.getStringProperty(dashboardElement, ATTR_LABEL));
            extension.setTooltip(super.getStringProperty(dashboardElement, ATTR_TOOLTIP));
            extension.setIcon(super.getStringProperty(dashboardElement, ATTR_ICON));
            extension.setGrid(super.getStringProperty(dashboardElement, ATTR_GRID));
            
            List<Element> widgetElements = super.getElementsByTagName(dashboardElement, ELEMENT_WIDGET);
            for (Element widgetElement : widgetElements) {
                WidgetReferenceExtension widgetReference = new WidgetReferenceExtension();
                String widgetId = widgetElement.getAttribute(ATTR_ID);
                widgetReference.setIdentifier(widgetId);
                widgetReference.setWidget(super.getStringProperty(widgetElement, ATTR_REFID));
                widgetReference.setPosition(super.getStringProperty(widgetElement, ATTR_POSITION));
                widgetReference.setHint(super.getStringProperty(widgetElement, ATTR_HINT));
                extension.getWidgets().add(widgetReference);
            }
            
            List<Element> browserElements = super.getElementsByTagName(dashboardElement, ELEMENT_BROWSER);
            for (Element browserElement : browserElements) {
                WorkItemBrowserExtension browserExtension = this.parseBrowserExtension(browserElement);
                extension.getBrowsers().add(browserExtension);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
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
     */
    private WorkItemBrowserExtension parseBrowserExtension(Element browserElement) throws ExtensionParserException {
        WorkItemBrowserExtension browserExtension = new WorkItemBrowserExtension();
        browserExtension.setBrowserId(super.getStringProperty(browserElement, ATTR_REFID));
        return browserExtension;
    }

}
