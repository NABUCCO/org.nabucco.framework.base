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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.action.ActionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.action.ActionSetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * ActionExtensionParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ActionExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_ACTIONSET = "actionSet";

    private static final String ELEMENT_ACTION = "action";

    private static final String ATTR_ID = "id";

    private static final String ATTR_CLASS = "class";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ActionExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing user interface application configuration.");

            ActionSetExtension actionSetExtension = new ActionSetExtension();
            actionSetExtension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            Element actionSetElement = super.getElementsByTagName(element, ELEMENT_ACTIONSET).get(0);
            actionSetExtension.setActionSetId(super.getStringProperty(actionSetElement, ATTR_ID));

            List<Element> actionElements = super.getElementsByTagName(actionSetElement, ELEMENT_ACTION);
            for (Element actionElement : actionElements) {
                ActionExtension actionExtension = new ActionExtension();
                actionExtension.setActionId(super.getStringProperty(actionElement, ATTR_ID));
                actionExtension.setActionClass(super.getClassProperty(actionElement, ATTR_CLASS));
                actionSetExtension.getActions().add(actionExtension);
            }

            return new NabucoExtensionContainer(actionSetExtension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Error parsing action extensions.", e);
        }
    }
}
