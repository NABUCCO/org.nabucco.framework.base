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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messagequeue.MessageQueueExtension;
import org.w3c.dom.Element;

/**
 * MessageQueueExtensionParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MessageQueueExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ELEMENT_MESSAGE_QUEUE = "messagequeue";

    private static final String ATTR_PERIOD = "period";

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            MessageQueueExtension extension = new MessageQueueExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element messageQueueElement = super.getElementsByTagName(element, ELEMENT_MESSAGE_QUEUE).get(0);
            extension.setPeriod(super.getIntegerProperty(messageQueueElement, ATTR_PERIOD, 5000));

            return new NabucoExtensionContainer(extension);
        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

}
