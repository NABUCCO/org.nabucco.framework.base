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
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.result.SearchResultExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.result.SearchResultFieldExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * SearchResultParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SearchResultParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_RESULT = "searchResult";

    private static final String ELEMENT_FIELD = "field";

    private static final String ATTR_ID = "id";

    private static final String ATTR_INDEX = "index";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_DESCRIPTION = "description";

    private static final String ATTR_VISIBLE = "visible";

    private static final String ATTR_SORTABLE = "sortable";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SearchResultParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        logger.debug("Parsing search result configuration");

        try {
            SearchResultExtension extension = new SearchResultExtension();
            extension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            List<Element> searchResults = super.getElementsByTagName(element, ELEMENT_RESULT);
            if (searchResults.isEmpty()) {
                throw new ExtensionParserException("No element 'searchResult' defined.");
            }

            Element searchResultElement = searchResults.get(0);
            extension.setIndexName(super.getStringProperty(searchResultElement, ATTR_INDEX));

            List<Element> searchFields = super.getElementsByTagName(searchResultElement, ELEMENT_FIELD);
            for (Element searchFieldElement : searchFields) {
                SearchResultFieldExtension fieldExtension = new SearchResultFieldExtension();
                fieldExtension.setFieldName(super.getStringProperty(searchFieldElement, ATTR_NAME));
                fieldExtension.setFieldLabel(super.getStringProperty(searchFieldElement, ATTR_LABEL));
                fieldExtension.setFieldDescription(super.getStringProperty(searchFieldElement, ATTR_DESCRIPTION));
                fieldExtension.setSortable(super.getBooleanProperty(searchFieldElement, ATTR_SORTABLE));
                fieldExtension.setVisible(super.getBooleanProperty(searchFieldElement, ATTR_VISIBLE));
                extension.getFieldList().add(fieldExtension);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }
}
