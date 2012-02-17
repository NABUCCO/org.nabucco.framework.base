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
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.perspective.PerspectiveExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the perspective extension of the nabucco user interface.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class PerspectiveExtensionParser extends UiExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_PERSPECTIVE = "perspective";

    private static final String ATTR_ID = "id";

    private static final String ATTR_LABEL = "label";
    
    private static final String ATTR_INDEX = "index";
    
    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_NAVIGATIONAREA = "navigationarea";

    private static final String ATTR_BROWSERAREA = "browserarea";

    private static final String ATTR_WORKAREA = "workarea";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            PerspectiveExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing user interface perspective configuration");

            PerspectiveExtension extension = new PerspectiveExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element perspectiveElement = super.getElementsByTagName(element, ELEMENT_PERSPECTIVE).get(0);

            extension.setLabel(super.getStringProperty(perspectiveElement, ATTR_LABEL));
            extension.setTooltip(super.getStringProperty(perspectiveElement, ATTR_TOOLTIP));
            extension.setIndex(super.getIntegerProperty(perspectiveElement, ATTR_INDEX, 0));
            extension.setNavigationArea(super.getStringProperty(perspectiveElement, ATTR_NAVIGATIONAREA));
            extension.setBrowserArea(super.getStringProperty(perspectiveElement, ATTR_BROWSERAREA));
            extension.setWorkArea(super.getStringProperty(perspectiveElement, ATTR_WORKAREA));

            // Permissions
            super.parsePermissionExtension(perspectiveElement, extension);

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

}
