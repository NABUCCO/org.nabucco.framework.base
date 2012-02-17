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
package org.nabucco.framework.base.facade.datatype.extension.authorization.authentication;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.authorization.authentication.AuthenticationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.authorization.authentication.DatabaseAuthenticationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.authorization.authentication.LdapAuthenticationExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * AuthenticationExtensionParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class AuthenticationExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_AUTHENTICATION = "authentication";

    private static final String ELEMENT_DATABASE = "database";

    private static final String ELEMENT_LDAP = "ldap";

    private static final String ELEMENT_RETURN_ATTRIBUTE = "returnAttribute";

    private static final String ELEMENT_SECURITY = "security";

    private static final String ATTRIBUTE_ID = "id";

    private static final String ATTRIBUTE_VALUE = "value";

    private static final String ATTRIBUTE_URL = "url";

    private static final String ATTRIBUTE_BASE_DN = "baseDn";

    private static final String ATTRIBUTE_FACTORY = "factory";

    private static final String ATTRIBUTE_OBJECT_FILTER = "objectFilter";

    private static final String ATTRIBUTE_AUTHENTICATION = "authentication";

    private static final String ATTRIBUTE_PRINCIPAL = "principal";

    private static final String ATTRIBUTE_PROTOCOL = "protocol";

    private static final String ATTRIBUTE_CREDENTIALS = "credentials";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            AuthenticationExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing authentication configuration.");

            List<Element> authenticationList = super.getElementsByTagName(element, ELEMENT_AUTHENTICATION);
            if (authenticationList.isEmpty() || authenticationList.get(0) == null) {
                throw new ExtensionParserException(
                        "Cannot resolve <authentication> tag in authentication configuration.");
            }

            Element authenticationElement = authenticationList.get(0);

            List<Element> authenticationTypeList = super.getChildren(authenticationElement);
            if (authenticationTypeList.isEmpty() || authenticationTypeList.get(0) == null) {
                throw new ExtensionParserException(
                        "Cannot resolve children of <authentication> tag in authentication configuration.");
            }

            Element authenticationTypeElement = authenticationTypeList.get(0);

            AuthenticationExtension extension = null;
            if (authenticationTypeElement.getNodeName().equals(ELEMENT_LDAP)) {
                extension = this.parseLdapExtension(authenticationTypeElement);
            } else if (authenticationTypeElement.getNodeName().equals(ELEMENT_DATABASE)) {
                extension = this.parseDatabaseExtension(authenticationTypeElement);
            }

            if (extension == null) {
                throw new ExtensionParserException("Extension type '"
                        + authenticationTypeElement.getNodeName() + "' not supported.");
            }

            extension.setIdentifier(super.getAttribute(element, ATTRIBUTE_ID));

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Error parsing PasswordPolicyExtension.", e);
        }
    }

    /**
     * Parse the Database extension configuration.
     * 
     * @param databaseElement
     *            the database element to parse
     * 
     * @return the database authentication extension
     */
    private AuthenticationExtension parseDatabaseExtension(Element databaseElement) {
        return new DatabaseAuthenticationExtension();
    }

    /**
     * Parse the LDAP extension configuration.
     * 
     * @param ldapElement
     *            the LDAP element to parse
     * 
     * @return the LDAP authentication extension
     * 
     * @throws ExtensionParserException
     *             when the LDAP element cannot be parsed
     * @throws ExtensionException
     *             when the XML is not valid
     */
    private AuthenticationExtension parseLdapExtension(Element ldapElement) throws ExtensionParserException,
            ExtensionException {

        LdapAuthenticationExtension extension = new LdapAuthenticationExtension();

        extension.setFactory(super.getEnumerationProperty(ldapElement, ATTRIBUTE_FACTORY));
        extension.setUrl(super.getStringProperty(ldapElement, ATTRIBUTE_URL));
        extension.setBaseDn(super.getStringProperty(ldapElement, ATTRIBUTE_BASE_DN));
        extension.setObjectFilter(super.getStringProperty(ldapElement, ATTRIBUTE_OBJECT_FILTER));

        List<Element> returnAttributes = super.getElementsByTagName(ldapElement, ELEMENT_RETURN_ATTRIBUTE);
        for (Element returnElement : returnAttributes) {
            extension.getReturnAttributes().add(super.getStringProperty(returnElement, ATTRIBUTE_VALUE));
        }

        List<Element> securityElements = super.getElementsByTagName(ldapElement, ELEMENT_SECURITY);
        if (securityElements.isEmpty() || securityElements.get(0) == null) {
            throw new ExtensionParserException("Cannot resolve <security> tag in ldap authentication configuration.");
        }

        Element securityElement = securityElements.get(0);
        extension.setSecurityCredentials(super.getStringProperty(securityElement, ATTRIBUTE_CREDENTIALS));
        extension.setSecurityType(super.getEnumerationProperty(securityElement, ATTRIBUTE_AUTHENTICATION));
        extension.setSecurityPrincipal(super.getStringProperty(securityElement, ATTRIBUTE_PRINCIPAL));
        extension.setSecurityProtocol(super.getEnumerationProperty(securityElement, ATTRIBUTE_PROTOCOL));

        return extension;
    }
}
