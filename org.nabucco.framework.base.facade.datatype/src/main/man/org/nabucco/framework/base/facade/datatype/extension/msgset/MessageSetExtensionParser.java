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
package org.nabucco.framework.base.facade.datatype.extension.msgset;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageCodeExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageSetExtension;
import org.w3c.dom.Element;

/**
 * Parser for extension point org.nabucco.bootstrap.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class MessageSetExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_MESSAGESET = "messageset";

    private static final String ELEMENT_MESSAGE = "message";

    private static final String ELEMENT_CODELIST = "codelist";

    private static final String ELEMENT_CODE = "code";

    private static final String ELEMENT_TEXT = "text";

    private static final String ATTR_LANGUAGE = "language";

    private static final String ATTR_SERVICE = "service";

    private static final String ATTR_PACKAGE = "package";

    private static final String ATTR_METHOD = "method";

    private static final String ATTR_SEVERITY = "severity";

    private static final String ATTR_PATH = "exceptionPath";

    private static final String ATTR_ID = "id";

    private static final String ATTR_TEXT = "text";

    private static final String ATTR_ORIGIN = "forceOriginalMessage";

    @Override
    public NabucoExtensionContainer parseExtension(Element e) throws ExtensionParserException {

        try {
            MessageSetExtension ext = new MessageSetExtension();
            ext.setIdentifier(getExtensionId(e));

            Element messageSetElement = getElementsByTagName(e, ELEMENT_MESSAGESET).get(0);
            StringProperty language = getStringProperty(messageSetElement, ATTR_LANGUAGE);
            ext.setLanguage(language);

            StringProperty pkg = getStringProperty(messageSetElement, ATTR_PACKAGE);
            ext.setPkg(pkg);

            StringProperty service = getStringProperty(messageSetElement, ATTR_SERVICE);
            ext.setService(service);

            List<Element> msgElementList = getElementsByTagName(messageSetElement, ELEMENT_MESSAGE);
            List<MessageExtension> mextList = ext.getMessageList();

            for (Element msgElement : msgElementList) {

                StringProperty method = getStringProperty(msgElement, ATTR_METHOD);
                StringProperty path = getStringProperty(msgElement, ATTR_PATH);
                StringProperty text = getStringProperty(msgElement, ATTR_TEXT);
                StringProperty severity = getStringProperty(msgElement, ATTR_SEVERITY);
                BooleanProperty originalMessage = getBooleanProperty(msgElement, ATTR_ORIGIN);

                MessageExtension mext = new MessageExtension();
                mext.setMessageText(text);
                mext.setExceptionPath(path);
                mext.setMethod(method);
                mext.setSeverity(severity);
                mext.setForceOriginalMessage(originalMessage);

                List<MessageCodeExtension> codes = parseCodes(msgElement);
                mext.getCodeList().addAll(codes);

                mextList.add(mext);
            }

            List<MessageCodeExtension> codes = parseCodes(messageSetElement);
            ext.getCodeList().addAll(codes);

            return new NabucoExtensionContainer(ext);
        } catch (ExtensionException ee) {
            throw new ExtensionParserException(ee);
        }

    }

    /**
     * Parse the codes from a given xml element.
     * 
     * @param parentElement
     *            the parent xml element
     * 
     * @return the list of code extensions
     * 
     * @throws ExtensionException
     * @throws ExtensionParserException
     */
    private List<MessageCodeExtension> parseCodes(Element parentElement) throws ExtensionException,
            ExtensionParserException {

        List<MessageCodeExtension> codeExtList = new ArrayList<MessageCodeExtension>();

        List<Element> codeList = getElementsByTagName(parentElement, ELEMENT_CODELIST);
        if (!codeList.isEmpty()) {

            List<Element> codeElements = getElementsByTagName(codeList.get(0), ELEMENT_CODE);
            for (Element codeElement : codeElements) {

                List<Element> textElement = getElementsByTagName(codeElement, ELEMENT_TEXT);
                if (!textElement.isEmpty()) {
                    MessageCodeExtension codeExt = new MessageCodeExtension();

                    StringProperty id = getStringProperty(codeElement, ATTR_ID);
                    codeExt.setId(id);

                    StringProperty codeText = new StringProperty();
                    codeText.setValue(textElement.get(0).getTextContent());
                    codeExt.setText(codeText);

                    codeExtList.add(codeExt);
                }
            }
        }

        return codeExtList;
    }

}
