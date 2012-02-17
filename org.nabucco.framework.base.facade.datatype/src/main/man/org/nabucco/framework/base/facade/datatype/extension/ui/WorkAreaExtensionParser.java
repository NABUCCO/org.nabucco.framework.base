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
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkAreaExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for the work area extension of the nabucco user interface.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkAreaExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_WORKAREA = "workarea";

    private static final String ATTR_ID = "id";

    private static final String ATTR_LAYOUT = "layout";

    private static final String ATTR_CLOSEDIALOG = "closeDialogId";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WorkAreaExtensionParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing user interface work area configuration.");

            WorkAreaExtension extension = new WorkAreaExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element workAreaElement = super.getElementsByTagName(element, ELEMENT_WORKAREA).get(0);

            extension.setLayout(super.getStringProperty(workAreaElement, ATTR_LAYOUT));

            extension.setCloseDialog(super.getStringProperty(workAreaElement, ATTR_CLOSEDIALOG));

            NabucoExtensionContainer container = new NabucoExtensionContainer(extension);
            return container;

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

}
