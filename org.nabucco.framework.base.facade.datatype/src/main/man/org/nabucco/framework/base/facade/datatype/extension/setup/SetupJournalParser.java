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

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.JournalExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

public class SetupJournalParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final int DEFAULT_MAX_RESULT = 30;

    private static final String ATTR_ID = "id";

    private static final String ATTR_MAX_RESULTS = "maxEntries";

    private static final String ELEMENT_JOURNAL = "journal";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SetupJournalParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element e) throws ExtensionParserException {

        logger.debug("Parsing index configuration");

        try {
            JournalExtension ext = new JournalExtension();
            ExtensionId id = new ExtensionId(e.getAttribute(ATTR_ID));
            ext.setIdentifier(id);

            Element journalElement = getElementsByTagName(e, ELEMENT_JOURNAL).get(0);

            ext.setMaxEntries(getIntegerProperty(journalElement, ATTR_MAX_RESULTS, DEFAULT_MAX_RESULT));

            return new NabucoExtensionContainer(ext);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }

    }

}
