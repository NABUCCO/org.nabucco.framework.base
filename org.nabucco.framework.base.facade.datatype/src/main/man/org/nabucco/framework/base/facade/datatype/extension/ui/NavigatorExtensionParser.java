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
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator.NavigatorEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator.NavigatorExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the navigator extension of the nabucco user interface.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class NavigatorExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_NAVIGATOR = "navigator";

    private static final String ATTR_ID = "id";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_IMAGE = "image";

    private static final String ATTR_PATH = "path";

    private static final String ATTR_ACTION = "action";

    private static final String ATTR_PERSPECTIVE = "perspective";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NavigatorExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing user interface navigator configuration.");

            NavigatorExtension extension = new NavigatorExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element navigatorElement = super.getElementsByTagName(element, ELEMENT_NAVIGATOR).get(0);

            extension.setLabel(super.getStringProperty(navigatorElement, ATTR_LABEL));
            extension.setPerspective(super.getStringProperty(navigatorElement, ATTR_PERSPECTIVE));

            List<Element> entries = this.getChildren(navigatorElement);

            for (Element entry : entries) {
                NavigatorEntryExtension entryExtension = this.parseEntry(entry);
                extension.getNavigatorEntryList().add(entryExtension);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

    /**
     * Parse a navigator entry.
     * 
     * @param element
     *            the xml element to parse
     * 
     * @return the parsed navigator entry extension
     * 
     * @throws ExtensionParserException
     *             when the element cannot be parsed to a navigator entry extension
     * @throws ExtensionException
     *             when the child elements cannot be extracted
     */
    private NavigatorEntryExtension parseEntry(Element element) throws ExtensionParserException, ExtensionException {
        NavigatorEntryExtension extension = new NavigatorEntryExtension();
        extension.setIdentifier(element.getAttribute(ATTR_ID));
        extension.setLabel(super.getStringProperty(element, ATTR_LABEL));
        extension.setTooltip(super.getStringProperty(element, ATTR_TOOLTIP));
        extension.setImage(super.getStringProperty(element, ATTR_IMAGE));
        extension.setPath(super.getStringProperty(element, ATTR_PATH));
        extension.setAction(super.getStringProperty(element, ATTR_ACTION));

        // Child Navigation Entries
        List<Element> entries = super.getChildren(element);
        for (Element entry : entries) {
            NavigatorEntryExtension subExtension = this.parseEntry(entry);
            extension.getNavigatorEntryList().add(subExtension);
        }

        return extension;
    }

}
