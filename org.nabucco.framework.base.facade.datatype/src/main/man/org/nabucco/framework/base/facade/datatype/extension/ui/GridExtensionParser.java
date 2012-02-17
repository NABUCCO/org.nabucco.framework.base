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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.GridWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetReferenceExtension;
import org.w3c.dom.Element;

/**
 * GridExtensionParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class GridExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_HINT = "hint";

    private static final String ATTR_POSITION = "position";

    private static final String ATTR_WIDGET = "widget";

    private static final String ELEMENT_WIDGET_REFERENCE = "widgetReference";

    private static final String ATTR_GRID = "grid";

    private static final String ATTR_ID = "id";

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {

            GridWidgetExtension gridExtension = new GridWidgetExtension();
            gridExtension.setGrid(super.getStringProperty(element, ATTR_GRID));
            gridExtension.setIdentifier(element.getAttribute(ATTR_ID));

            List<Element> widgetReferenceList = super.getElementsByTagName(element, ELEMENT_WIDGET_REFERENCE);
            for (Element widgetReference : widgetReferenceList) {
                WidgetReferenceExtension widgetReferenceExtension = new WidgetReferenceExtension();
                widgetReferenceExtension.setWidget(super.getStringProperty(widgetReference, ATTR_WIDGET));
                widgetReferenceExtension.setPosition(super.getStringProperty(widgetReference, ATTR_POSITION));
                widgetReferenceExtension.setHint(super.getStringProperty(widgetReference, ATTR_HINT));
                gridExtension.getWidgets().add(widgetReferenceExtension);
            }

            return new NabucoExtensionContainer(gridExtension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }

    }

}
