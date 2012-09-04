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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.shortcuts.ShortcutExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.shortcuts.ShortcutsExtension;
import org.w3c.dom.Element;

/**
 * MessageSetExtensionParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ShortcutsExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ALIAS = "alias";

    private static final String ATTR_FUNCTIONAL_TYPE = "functionalType";

    private static final String ATTR_ID = "id";

    private static final String ATTR_LANGUAGE = "language";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_PARAMETER = "parameter";

    private static final String ELEMENT_SHORTCUTS = "shortcuts";

    private static final String ELEMENT_SHORTCUT = "shortcut";

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            ShortcutsExtension extension = new ShortcutsExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            Element messageSetElement = super.getElementsByTagName(element, ELEMENT_SHORTCUTS).get(0);

            extension.setLanguage(this.getStringProperty(messageSetElement, ATTR_LANGUAGE));
            extension.setType(this.getEnumerationProperty(messageSetElement, ATTR_TYPE));

            List<Element> shortcutList = super.getElementsByTagName(messageSetElement, ELEMENT_SHORTCUT);
            for (Element messageItem : shortcutList) {
                ShortcutExtension shortcutExtension = new ShortcutExtension();
                shortcutExtension.setAlias(this.getStringProperty(messageItem, ATTR_ALIAS));
                shortcutExtension.setFunctionalType(this.getStringProperty(messageItem, ATTR_FUNCTIONAL_TYPE));
                shortcutExtension.setParameter(this.getEnumerationProperty(messageItem, ATTR_PARAMETER));
                extension.getShortcuts().add(shortcutExtension);
            }
            return new NabucoExtensionContainer(extension);
        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

}
