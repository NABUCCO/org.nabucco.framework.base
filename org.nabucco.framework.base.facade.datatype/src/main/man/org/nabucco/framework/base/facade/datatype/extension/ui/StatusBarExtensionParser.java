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
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.GridWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.statusbar.StatusBarExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetReferenceExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the perspective extension of the nabucco user interface.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class StatusBarExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_GRID = "grid";

    private static final String ELEMENT_STATUSBAR = "statusbar";

    private static final String ARRT_HEIGHT = "height";

    private static final String ATTR_ID = "id";

    private static final String ATTR_HINT = "hint";

    private static final String ATTR_POSITION = "position";

    private static final String ATTR_WIDGET = "widget";

    private static final String ELEMENT_WIDGET_REFERENCE = "widgetReference";

    private static final String ATTR_GRID = "grid";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(StatusBarExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing user interface statusbar configuration");

            StatusBarExtension extension = new StatusBarExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element statusBarElement = super.getElementsByTagName(element, ELEMENT_STATUSBAR).get(0);
            extension.setHeight(super.getIntegerProperty(statusBarElement, ARRT_HEIGHT, 20));

            List<Element> statusBarGridList = super.getElementsByTagName(element, ELEMENT_GRID);
            for (Element grid : statusBarGridList) {
                GridWidgetExtension parsedGrid = this.parseGrid(grid);
                extension.getGrids().add(parsedGrid);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

    /**
     * Grid parser
     * 
     * @param element
     *            The element to be parsed
     * @return
     * @throws ExtensionParserException
     */
    private GridWidgetExtension parseGrid(Element element) throws ExtensionParserException {
        try {
            GridWidgetExtension gridExtension = new GridWidgetExtension();
            gridExtension.setGrid(super.getStringProperty(element, ATTR_GRID));
            String gridId = element.getAttribute(ATTR_ID);
            gridExtension.setIdentifier(gridId);

            List<Element> widgetReferenceList = super.getElementsByTagName(element, ELEMENT_WIDGET_REFERENCE);

            for (Element widgetReference : widgetReferenceList) {
                WidgetReferenceExtension widgetReferenceExtension = new WidgetReferenceExtension();
                String widget = widgetReference.getAttribute(ATTR_WIDGET);
                widgetReferenceExtension.setIdentifier(widget);
                widgetReferenceExtension.setWidget(super.getStringProperty(widgetReference, ATTR_WIDGET));
                widgetReferenceExtension.setPosition(super.getStringProperty(widgetReference, ATTR_POSITION));
                widgetReferenceExtension.setHint(super.getStringProperty(widgetReference, ATTR_HINT));
                gridExtension.getWidgets().add(widgetReferenceExtension);
            }
            return gridExtension;

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }


}
