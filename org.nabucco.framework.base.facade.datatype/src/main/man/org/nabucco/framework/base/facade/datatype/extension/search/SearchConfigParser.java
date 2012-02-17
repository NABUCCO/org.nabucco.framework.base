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
package org.nabucco.framework.base.facade.datatype.extension.search;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchBoxExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchConfigurationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchIndexRefExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchPrefixExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchQualifierExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * SearchConfigParser
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SearchConfigParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_QUALIFIER = "qualifier";

    private static final String ELEMENT_PREFIX = "prefix";

    private static final String ELEMENT_INDEX = "index";

    private static final String ELEMENT_SEARCHBOX = "searchbox";

    private static final String ATTR_DEFAULT_INDEX = "defaultIndex";

    private static final String ATTR_MAX_RESULTS = "maxResults";

    private static final String ATTR_ID = "id";

    private static final String ATTR_NAME = "name";

    private static final int DEFAULT_MAX_RESULT = 100;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SearchConfigParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing search configuration");

            SearchConfigurationExtension extenstion = new SearchConfigurationExtension();
            extenstion.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            List<Element> searchBoxes = getElementsByTagName(element, ELEMENT_SEARCHBOX);
            if (searchBoxes.isEmpty()) {
                throw new ExtensionParserException("No 'searchBox' element defined.");
            }
            Element searchBoxElement = searchBoxes.get(0);
            SearchBoxExtension searchBoxExtension = new SearchBoxExtension();
            extenstion.setSearchbox(searchBoxExtension);
            searchBoxExtension.setDefaultIndex(super.getStringProperty(searchBoxElement, ATTR_DEFAULT_INDEX));
            searchBoxExtension.setMaxResults(super.getIntegerProperty(searchBoxElement, ATTR_MAX_RESULTS,
                    DEFAULT_MAX_RESULT));

            List<Element> prefixElementList = super.getElementsByTagName(searchBoxElement, ELEMENT_PREFIX);
            for (Element prefixElement : prefixElementList) {
                SearchPrefixExtension prefix = this.parsePrefix(prefixElement);
                searchBoxExtension.getPrefixList().add(prefix);
            }

            return new NabucoExtensionContainer(extenstion);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }
    }

    /**
     * Parse the search prefix.
     * 
     * @param element
     *            the element to parse
     * @return the search prefix extension
     * 
     * @throws ExtensionParserException
     *             when the attributes cannot be parsed
     * @throws ExtensionException
     *             when the children cannot be parsed
     */
    private SearchPrefixExtension parsePrefix(Element element) throws ExtensionParserException, ExtensionException {
        SearchPrefixExtension prefix = new SearchPrefixExtension();
        prefix.setId(super.getStringProperty(element, ATTR_ID));

        List<Element> indexList = getElementsByTagName(element, ELEMENT_INDEX);
        for (Element indexElement : indexList) {
            SearchIndexRefExtension indexRef = new SearchIndexRefExtension();
            indexRef.setName(getStringProperty(indexElement, ATTR_NAME));
            prefix.getIndexList().add(indexRef);
            this.parseQualifier(indexElement, indexRef);
        }

        return prefix;
    }

    /**
     * Parse the qualifier list.
     * 
     * @param element
     *            the element to parse
     * @param indexRef
     *            the index reference extension
     * 
     * @throws ExtensionParserException
     *             when the attributes cannot be parsed
     * @throws ExtensionException
     *             when the children cannot be parsed
     */
    private void parseQualifier(Element element, SearchIndexRefExtension indexRef) throws ExtensionException,
            ExtensionParserException {

        List<Element> qualifierList = super.getElementsByTagName(element, ELEMENT_QUALIFIER);
        for (Element qualifierElement : qualifierList) {
            SearchQualifierExtension qualifierExtension = new SearchQualifierExtension();
            qualifierExtension.setName(getStringProperty(qualifierElement, ATTR_NAME));
            indexRef.getQualifierList().add(qualifierExtension);
        }

    }
}
