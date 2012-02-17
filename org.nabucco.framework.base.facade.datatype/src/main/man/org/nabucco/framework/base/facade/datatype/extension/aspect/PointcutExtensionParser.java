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
package org.nabucco.framework.base.facade.datatype.extension.aspect;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyExtensionParser;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.PointcutExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.PointcutListExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for an aspect extension.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class PointcutExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_POINTCUT = "pointcut";

    private static final String ELEMENT_PROPERTIES = "properties";

    private static final String ATTR_SERVICE = "service";

    private static final String ATTR_OPERATION = "operation";

    private static final String ATTR_ID = "id";

    private static final String ATTR_ADVICE = "advice";

    private static final String ATTR_CLASS = "class";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(PointcutExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing Pointcut Configuration.");

            PointcutListExtension pointcutListExtension = new PointcutListExtension();
            pointcutListExtension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            PropertyExtension propertyExtension = null;

            if (getElementsByTagName(element, ELEMENT_PROPERTIES).size() > 0) {
                PropertyExtensionParser propExtParser = new PropertyExtensionParser();
                propertyExtension = propExtParser.parseProperties(element);
            }

            List<Element> pointcutElements = getElementsByTagName(element, ELEMENT_POINTCUT);

            for (Element pointcutElement : pointcutElements) {
                PointcutExtension pointcutExtension = new PointcutExtension();
                pointcutExtension.setPropertyExtension(propertyExtension);
                pointcutExtension.setService(getStringProperty(pointcutElement, ATTR_SERVICE));
                pointcutExtension.setOperation(getStringProperty(pointcutElement, ATTR_OPERATION));
                pointcutExtension.setAdvice(getEnumerationProperty(pointcutElement, ATTR_ADVICE));
                pointcutExtension.setImplClass(getClassProperty(pointcutElement, ATTR_CLASS));
                pointcutListExtension.getPointcutExtensionList().add(pointcutExtension);
            }

            return new NabucoExtensionContainer(pointcutListExtension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

}
