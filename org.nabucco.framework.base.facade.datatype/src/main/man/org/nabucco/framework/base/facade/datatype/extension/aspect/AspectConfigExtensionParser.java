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
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AspectConfigExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.AspectExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for an aspect configuration extension.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class AspectConfigExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_ASPECT = "aspect";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_POINT = "point";

    private static final String ATTR_ID = "id";

    private static final String ATTR_CLASS = "class";

    private static final String ATTR_INTERFACE = "interface";

    private static final String ATTR_SLOTS = "slots";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            AspectConfigExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Parsing aspect configuration.");

            AspectConfigExtension aspectConfig = new AspectConfigExtension();
            aspectConfig.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            List<Element> aspectElementList = getElementsByTagName(element, ELEMENT_ASPECT);
            List<AspectExtension> aspectList = aspectConfig.getAspectList();

            for (Element aspectElement : aspectElementList) {

                AspectExtension aspect = new AspectExtension();
                aspect.setName(getStringProperty(aspectElement, ATTR_NAME));
                aspect.setPoint(getStringProperty(aspectElement, ATTR_POINT));
                aspect.setImplClass(getClassProperty(aspectElement, ATTR_CLASS));
                aspect.setInterfaceClass(getClassProperty(aspectElement, ATTR_INTERFACE));

                String slots = aspectElement.getAttribute(ATTR_SLOTS);

                IntegerProperty slotsProperty = new IntegerProperty();
                try {
                    slotsProperty.setValue(Integer.parseInt(slots));
                } catch (Exception ex) {
                    if (slots != null && slots.equals("*")) {
                        slotsProperty.setValue(Integer.MAX_VALUE);
                    } else {
                        throw new ExtensionParserException("Cannot parse slots amount - unsupported value=["
                                + slots + "].");
                    }
                }

                aspect.setSlots(slotsProperty);
                if (logger.isDebugEnabled()) {
                    logger.debug("Adding aspect name=["
                            + aspect.getName().getValue() + "] with point=[" + aspect.getPoint().getValue() + "].");
                }
                aspectList.add(aspect);
            }

            return new NabucoExtensionContainer(aspectConfig);

        } catch (ExtensionException ex) {
            logger.error(ex, "Error while parsing the aspect config extensions.");
            throw new ExtensionParserException(ex);
        }
    }
}
