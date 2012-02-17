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
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.error.ErrorLogExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * ErrorExtensionParser
 * 
 * The parser for the error container with server errors
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ErrorLogExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ELEMENT_ERROR_CONTAINER = "errorLog";

    private static final String ATTR_TITLE = "title";

    private static final String ATTR_LAYOUT = "layout";

    private static final String ATTR_ALLOW_CLEAR = "allowClear";

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ErrorLogExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            this.logger.debug("Parsing error container interface configuration.");
            ErrorLogExtension extension = new ErrorLogExtension();

            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element dialogElement = super.getElementsByTagName(element, ELEMENT_ERROR_CONTAINER).get(0);

            extension.setTitle(super.getStringProperty(dialogElement, ATTR_TITLE));
            extension.setLayout(super.getEnumerationProperty(dialogElement, ATTR_LAYOUT));
            extension.setAllowClear(super.getBooleanProperty(dialogElement, ATTR_ALLOW_CLEAR));

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

}
