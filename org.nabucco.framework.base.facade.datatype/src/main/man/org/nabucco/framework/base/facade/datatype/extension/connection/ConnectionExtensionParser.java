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
package org.nabucco.framework.base.facade.datatype.extension.connection;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.connection.ConnectionExtension;
import org.nabucco.framework.base.facade.datatype.extension.ui.ActionExtensionParser;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Extension parser for server connection configurations.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConnectionExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_CONNECTION = "connection";

    private static final String ATTR_ID = "id";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_ENVIRONMENT = "environment";

    private static final String ATTR_HOST = "host";

    private static final String ATTR_PORT = "port";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ActionExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing connection configuration.");

            ConnectionExtension connectionExtension = new ConnectionExtension();
            connectionExtension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            Element connectionElement = super.getElementsByTagName(element, ELEMENT_CONNECTION).get(0);
            connectionExtension.setServerType(super.getEnumerationProperty(connectionElement, ATTR_TYPE));
            connectionExtension.setEnvironmentType(super.getEnumerationProperty(connectionElement, ATTR_ENVIRONMENT));
            connectionExtension.setName(super.getStringProperty(connectionElement, ATTR_NAME));
            connectionExtension.setHost(super.getStringProperty(connectionElement, ATTR_HOST));
            connectionExtension.setPort(super.getStringProperty(connectionElement, ATTR_PORT));

            return new NabucoExtensionContainer(connectionExtension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Error parsing connection extension.", e);
        }
    }

}
