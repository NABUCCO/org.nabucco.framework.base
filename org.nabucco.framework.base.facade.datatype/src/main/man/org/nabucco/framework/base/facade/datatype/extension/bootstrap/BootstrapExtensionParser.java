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
package org.nabucco.framework.base.facade.datatype.extension.bootstrap;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.schema.ClassExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.bootstrap.ExtensionConfigurationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.bootstrap.NabuccoBootstrapExtension;
import org.w3c.dom.Element;

/**
 * Parser for extension point org.nabucco.bootstrap.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class BootstrapExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_EXTENSION_CONFIGURATION = "extensionConfiguration";

    private static final String ATTR_EXTENSION_POINT = "extensionPoint";

    private static final String ATTR_EXTENSION_CLASS = "extensionClass";

    private static final String ATTR_EXTENSION_RELOADABLE = "reloadable";

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {

        try {
            NabuccoBootstrapExtension bootstrapExtension = new NabuccoBootstrapExtension();
            bootstrapExtension.setIdentifier(super.getExtensionId(element));

            List<ExtensionConfigurationExtension> configurationExtensions = bootstrapExtension.getExtensionList();
            List<Element> configurationElements = super.getElementsByTagName(element, ELEMENT_EXTENSION_CONFIGURATION);

            for (Element configurationElement : configurationElements) {
                ExtensionConfigurationExtension configurationExtension = new ExtensionConfigurationExtension();
                this.parseExtensionConfigurationExtension(configurationElement, configurationExtension);
                configurationExtensions.add(configurationExtension);
            }

            return new NabucoExtensionContainer(bootstrapExtension);

        } catch (ExtensionException ee) {
            throw new ExtensionParserException(ee);
        }
    }

    /**
     * Parse the extension configuration.
     * 
     * @param element
     *            the xml element
     * @param ext
     *            the extension configuration
     * 
     * @throws ExtensionParserException
     *             when the xml element cannot be parsed
     */
    private void parseExtensionConfigurationExtension(Element element, ExtensionConfigurationExtension ext)
            throws ExtensionParserException {

        ClassExtension classExtension = new ClassExtension();
        classExtension.setImplClass(super.getClassProperty(element, ATTR_EXTENSION_CLASS));

        ext.setExtensionClass(classExtension);
        ext.setExtensionPoint(getStringProperty(element, ATTR_EXTENSION_POINT));
        ext.setReloadable(getBooleanProperty(element, ATTR_EXTENSION_RELOADABLE));
    }

}
