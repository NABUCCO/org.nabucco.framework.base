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
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementArgument;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementAttribute;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementOperation;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * ManagementExtensionParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ManagementExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_CLASS = "class";

    private static final String ATTR_ACTION = "actionType";

    private static final String ATTR_READONLY = "readOnly";

    private static final String ATTR_DESCRIPTION = "description";

    private static final String ELEMENT_MBEAN = "mbean";

    private static final String ELEMENT_ATTRIBUTE = "attribute";

    private static final String ELEMENT_OPERATION = "operation";

    private static final String ELEMENT_ARGUMENT = "argument";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance()
            .getLogger(ManagementExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        logger.debug("Parsing Management Configuration.");

        try {
            ManagementExtension extension = new ManagementExtension();
            extension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            List<Element> mbeans = super.getElementsByTagName(element, ELEMENT_MBEAN);

            if (mbeans.size() < 1) {
                throw new ExtensionParserException("No mbean configuration found in management extension '"
                        + extension.getIdentifier() + "'.");
            }

            if (mbeans.size() > 1) {
                throw new ExtensionParserException("More than 1 mbean configuration found in management extension '"
                        + extension.getIdentifier() + "'.");
            }

            Element mbean = mbeans.get(0);

            extension.setBeanClass(super.getClassProperty(mbean, ATTR_CLASS));
            extension.setDescription(super.getStringProperty(mbean, ATTR_DESCRIPTION));

            this.parseAttributes(extension, mbean);
            this.parseOperations(extension, mbean);

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Parse the managment attributes.
     * 
     * @param extension
     *            the management extension
     * @param mbean
     *            the mbean element
     * 
     * @throws ExtensionException
     *             when the xml is not valid
     * @throws ExtensionParserException
     *             when an xml attribute cannot be parsed
     */
    private void parseAttributes(ManagementExtension extension, Element mbean) throws ExtensionException,
            ExtensionParserException {

        for (Element attribute : super.getElementsByTagName(mbean, ELEMENT_ATTRIBUTE)) {

            ManagementAttribute attributeExtension = new ManagementAttribute();
            attributeExtension.setName(super.getStringProperty(attribute, ATTR_NAME));
            attributeExtension.setType(super.getClassProperty(attribute, ATTR_TYPE));
            attributeExtension.setReadOnly(super.getBooleanProperty(attribute, ATTR_READONLY));
            attributeExtension.setDescription(super.getStringProperty(attribute, ATTR_DESCRIPTION));

            extension.getAttributes().add(attributeExtension);
        }
    }

    /**
     * Parse the managment operations.
     * 
     * @param extension
     *            the management extension
     * @param mbean
     *            the mbean element
     * 
     * @throws ExtensionException
     *             when the xml is not valid
     * @throws ExtensionParserException
     *             when an xml attribute cannot be parsed
     */
    private void parseOperations(ManagementExtension extension, Element mbean) throws ExtensionException,
            ExtensionParserException {

        for (Element operation : super.getElementsByTagName(mbean, ELEMENT_OPERATION)) {

            ManagementOperation operationExtension = new ManagementOperation();
            operationExtension.setName(super.getStringProperty(operation, ATTR_NAME));
            operationExtension.setReturnType(super.getClassProperty(operation, ATTR_TYPE));
            operationExtension.setDescription(super.getStringProperty(operation, ATTR_DESCRIPTION));
            operationExtension.setActionType(super.getEnumerationProperty(operation, ATTR_ACTION));

            for (Element argument : super.getElementsByTagName(operation, ELEMENT_ARGUMENT)) {

                ManagementArgument argumentExtension = new ManagementArgument();
                argumentExtension.setName(super.getStringProperty(argument, ATTR_NAME));
                argumentExtension.setType(super.getClassProperty(argument, ATTR_TYPE));
                argumentExtension.setDescription(super.getStringProperty(argument, ATTR_DESCRIPTION));

                operationExtension.getArguments().add(argumentExtension);
            }

            extension.getOperations().add(operationExtension);
        }
    }
}
