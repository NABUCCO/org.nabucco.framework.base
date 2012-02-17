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

import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.PermissionExtension;
import org.w3c.dom.Element;

/**
 * UiExtensionParserSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class UiExtensionParserSupport extends NabuccoExtensionParserSupport {

    private static final String ELEMENT_PERMISSION = "permission";

    private static final String ATTR_ID = "id";

    private static final String ATTR_GRANT = "grant";

    /**
     * Parse all child permission tags of the given xml element and add all parsed permission
     * extensions to the given UI element.
     * 
     * @param parent
     *            the parent UI element holding child permission elements
     * @param extension
     *            the UI extension to add the permissions
     * 
     * @throws ExtensionParserException
     *             when parsing of a permission fails
     */
    protected void parsePermissionExtension(Element parent, UiExtension extension) throws ExtensionParserException {

        extension.setGrant(super.getEnumerationProperty(parent, ATTR_GRANT));

        for (Element permissionElement : super.getChildren(parent, ELEMENT_PERMISSION)) {
            PermissionExtension permissionExtension = this.parsePermissionExtension(permissionElement);
            extension.getPermissions().add(permissionExtension);
        }
    }

    /**
     * Parse a single permission extension.
     * 
     * @param element
     *            the permission extension to parse
     * 
     * @return the parsed permission extension
     * 
     * @throws ExtensionParserException
     *             when the permission extension cannot be parsed
     */
    protected PermissionExtension parsePermissionExtension(Element element) throws ExtensionParserException {

        PermissionExtension extension = new PermissionExtension();
        extension.setPermissionId(super.getStringProperty(element, ATTR_ID));
        extension.setGrant(super.getEnumerationProperty(element, ATTR_GRANT));

        return extension;
    }

}
