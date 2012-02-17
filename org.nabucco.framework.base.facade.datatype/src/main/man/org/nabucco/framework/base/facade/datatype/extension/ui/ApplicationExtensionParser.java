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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.application.ApplicationExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the application extension of the nabucco user interface.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class ApplicationExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_APPLICATION = "application";

    private static final String ATTR_ID = "id";

    private static final String ATTR_LAYOUT = "layout";

    private static final String ATTR_RESOURCE = "resource";

    private static final String ATTR_TITLE = "title";

    private static final String ATTR_TITLEBAR = "titlebar";

    private static final String ATTR_PERSPECTIVEAREA = "perspectivearea";

    private static final String ATTR_STATUSBAR = "statusbar";
    
    private static final String ATTR_DEBUGMODE = "debugmode";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ApplicationExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing user interface application configuration.");

            ApplicationExtension extension = new ApplicationExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element applicationElement = super.getElementsByTagName(element, ELEMENT_APPLICATION).get(0);

            extension.setLayout(super.getStringProperty(applicationElement, ATTR_LAYOUT));
            extension.setTitle(super.getStringProperty(applicationElement, ATTR_TITLE));
            extension.setResource(super.getStringProperty(applicationElement, ATTR_RESOURCE));
            extension.setTitleBar(super.getStringProperty(applicationElement, ATTR_TITLEBAR));
            extension.setPerspectiveArea(super.getStringProperty(applicationElement, ATTR_PERSPECTIVEAREA));
            extension.setStatusBar(super.getStringProperty(applicationElement, ATTR_STATUSBAR));
            extension.setDebugMode(super.getBooleanProperty(applicationElement, ATTR_DEBUGMODE));

            NabucoExtensionContainer container = new NabucoExtensionContainer(extension);
            return container;

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

}
