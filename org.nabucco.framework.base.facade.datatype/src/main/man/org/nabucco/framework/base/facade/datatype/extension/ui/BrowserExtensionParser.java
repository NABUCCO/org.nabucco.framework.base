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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.browser.BrowserExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the perspective area extension of the nabucco user iterface.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class BrowserExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_BROWSER = "browser";

    private static final String ATTR_ID = "id";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_CLASS = "class";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(BrowserExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing user interface browser configuration.");

            BrowserExtension extension = new BrowserExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element browserElement = super.getElementsByTagName(element, ELEMENT_BROWSER).get(0);

            extension.setLabel(super.getStringProperty(browserElement, ATTR_LABEL));
            extension.setTooltip(super.getStringProperty(browserElement, ATTR_TOOLTIP));
            extension.setBrowserClass(super.getClassProperty(browserElement, ATTR_CLASS));

            NabucoExtensionContainer container = new NabucoExtensionContainer(extension);
            return container;

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

}
