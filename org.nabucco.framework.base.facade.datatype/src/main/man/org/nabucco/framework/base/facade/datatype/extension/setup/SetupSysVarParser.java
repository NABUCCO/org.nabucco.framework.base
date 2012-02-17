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
package org.nabucco.framework.base.facade.datatype.extension.setup;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SysVarExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SystemVariableExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

public class SetupSysVarParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_DEFAULT = "default";

    private static final String ELEMENT_SYSVAR = "sysvar";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SetupSysVarParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element e) throws ExtensionParserException {

        logger.debug("Parsing sysvar configuration");

        try {
            SysVarExtension ext = new SysVarExtension();
            ExtensionId id = new ExtensionId(e.getAttribute(ATTR_ID));
            ext.setIdentifier(id);

            List<Element> sysVarList = getElementsByTagName(e, ELEMENT_SYSVAR);
            List<SystemVariableExtension> resList = ext.getSysVarList();
            for (Element sysVarElement : sysVarList) {

                SystemVariableExtension sv = new SystemVariableExtension();
                sv.setName(getStringProperty(sysVarElement, ATTR_ID));
                sv.setType(getStringProperty(sysVarElement, ATTR_TYPE));
                sv.setDefaultValue(getStringProperty(sysVarElement, ATTR_DEFAULT));
                resList.add(sv);
            }

            return new NabucoExtensionContainer(ext);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }

    }

}
