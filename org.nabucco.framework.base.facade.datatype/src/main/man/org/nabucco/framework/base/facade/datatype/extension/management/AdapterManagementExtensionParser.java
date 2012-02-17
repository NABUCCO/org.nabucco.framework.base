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
package org.nabucco.framework.base.facade.datatype.extension.management;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.AdapterManagementExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * ManagementExtensionParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AdapterManagementExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_INTERFACE = "interface";

    private static final String ELEMENT_ADAPTER = "adapter";

    private static final String ELEMENT_JNDI = "jndi";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            AdapterManagementExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        logger.debug("Parsing Adapter Management Configuration.");

        try {
            AdapterManagementExtension extension = new AdapterManagementExtension();
            extension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            List<Element> adapters = super.getElementsByTagName(element, ELEMENT_ADAPTER);

            if (adapters.size() < 1) {
                throw new ExtensionParserException("No adapter configuration found in management extension '"
                        + extension.getIdentifier() + "'.");
            }

            if (adapters.size() > 1) {
                throw new ExtensionParserException("More than 1 adapter configuration found in management extension '"
                        + extension.getIdentifier() + "'.");
            }

            Element adapter = adapters.get(0);

            extension.setAdapterInterface(super.getClassProperty(adapter, ATTR_INTERFACE));

            for (Element jndi : super.getElementsByTagName(adapter, ELEMENT_JNDI)) {
                extension.getJndiNames().add(super.getStringProperty(jndi, ATTR_NAME));
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

}
