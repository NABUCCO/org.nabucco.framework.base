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
package org.nabucco.framework.base.facade.datatype.extension.authorization.password.policy;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.authorization.password.policy.PasswordPolicyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * PasswordPolicyExtensionParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PasswordPolicyExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_PATTERN = "passwordPattern";

    private static final String ELEMENT_CASE_SENSITIVE = "caseSensitive";

    private static final String ELEMENT_REUSE_COUNT = "reuseCount";

    private static final String ELEMENT_DURATION = "duration";

    private static final String ATTR_ID = "id";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            PasswordPolicyExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing password policy configuration.");

            PasswordPolicyExtension extension = new PasswordPolicyExtension();
            extension.setIdentifier(super.getAttribute(element, ATTR_ID));

            List<Element> patternList = super.getElementsByTagName(element, ELEMENT_PATTERN);
            if (!patternList.isEmpty()) {
                extension.setPasswordPattern(super.getTextContent(patternList.get(0)));
            }

            List<Element> caseList = super.getElementsByTagName(element, ELEMENT_CASE_SENSITIVE);
            if (!caseList.isEmpty()) {
                BooleanProperty property = new BooleanProperty();
                String propertyValue = PropertyLoader.loadProperty(super.getTextContent(caseList.get(0)));
                property.setValue(Boolean.parseBoolean(propertyValue));
                extension.setCaseSensitive(property);
            }

            List<Element> reuseList = super.getElementsByTagName(element, ELEMENT_REUSE_COUNT);
            if (!reuseList.isEmpty()) {
                IntegerProperty property = new IntegerProperty();
                String propertyValue = PropertyLoader.loadProperty(super.getTextContent(reuseList.get(0)));
                property.setValue(Integer.parseInt(propertyValue));
                extension.setReuseCount(property);
            }

            List<Element> durationList = super.getElementsByTagName(element, ELEMENT_DURATION);
            if (!durationList.isEmpty()) {
                IntegerProperty property = new IntegerProperty();
                String propertyValue = PropertyLoader.loadProperty(super.getTextContent(durationList.get(0)));
                property.setValue(Integer.parseInt(propertyValue));
                extension.setDuration(property);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Error parsing PasswordPolicyExtension.", e);
        }

    }
}
