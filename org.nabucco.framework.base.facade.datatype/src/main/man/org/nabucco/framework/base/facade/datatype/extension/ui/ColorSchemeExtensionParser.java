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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.colorsheme.ColorShemeExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.colorsheme.ColorShemeItemExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * ColorShemeExtensionParser
 * 
 * The parser for the color sheme's
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ColorSchemeExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ELEMENT_SHEME = "sheme";

    private static final String ELEMENT_COLOR = "color";

    private static final String ATTR_VALUE = "value";

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ColorSchemeExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        this.logger.debug("Parsing color sheme configuration.");

        try {
            ColorShemeExtension extension = new ColorShemeExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element shemeElement;
            shemeElement = super.getElementsByTagName(element, ELEMENT_SHEME).get(0);

            List<Element> colorList = super.getElementsByTagName(shemeElement, ELEMENT_COLOR);

            for (Element colorElement : colorList) {
                ColorShemeItemExtension colorExt = new ColorShemeItemExtension();

                colorExt.setIdentifier(colorElement.getAttribute(ATTR_ID));
                colorExt.setValue(super.getStringProperty(colorElement, ATTR_VALUE));

                extension.getColors().add(colorExt);

            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Cannot parse color sheme", e);
        }

    }

}
