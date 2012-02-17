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
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset.MessageSetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset.MessageSetMessageExtension;
import org.w3c.dom.Element;

/**
 * MessageSetExtensionParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MessageSetExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_TEXT = "text";

    private static final String ATTR_SEVERITY = "severity";

    private static final String ATTR_CONSTRAINT = "constraint";

    private static final String ATTR_CONTROL = "control";

    private static final String ATTR_CONTROL_TYPE = "controlType";

    private static final String ATTR_ID = "id";

    private static final String ATTR_LANGUAGE = "language";

    private static final String ELEMENT_MESSAGE_SET = "messageset";

    private static final String ATTR_WORKITEM_ID = "workitem";

    private static final String ELEMENT_MESSAGE = "message";

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            MessageSetExtension extension = new MessageSetExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element messageSetElement = super.getElementsByTagName(element, ELEMENT_MESSAGE_SET).get(0);
            extension.setLanguage(this.getStringProperty(messageSetElement, ATTR_LANGUAGE));
            extension.setWorkItemId(this.getStringProperty(messageSetElement, ATTR_WORKITEM_ID));

            List<Element> messageElement = super.getElementsByTagName(messageSetElement, ELEMENT_MESSAGE);
            for (Element messageItem : messageElement) {
                MessageSetMessageExtension messageExt = new MessageSetMessageExtension();
                messageExt.setControl(this.getStringProperty(messageItem, ATTR_CONTROL));
                messageExt.setControlType(this.getEnumerationProperty(messageItem, ATTR_CONTROL_TYPE));
                messageExt.setConstraint(this.getEnumerationProperty(messageItem, ATTR_CONSTRAINT));
                messageExt.setSeverity(this.getEnumerationProperty(messageItem, ATTR_SEVERITY));
                messageExt.setText(this.getStringProperty(messageItem, ATTR_TEXT));
                extension.getMessages().add(messageExt);
            }
            return new NabucoExtensionContainer(extension);
        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

}
