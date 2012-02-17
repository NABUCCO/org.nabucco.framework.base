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
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchFieldExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchIndexExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * SearchIndexParser
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SearchIndexParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String TAG_INDEX = "index";

    private static final String TAG_FIELD = "field";

    private static final String ATTR_ID = "id";

    private static final String ATTR_KEY = "key";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_SEARCH_QUALIFIER = "searchQualifier";

    private static final String ATTR_SEARCH_SEARCH = "search";

    private static final String DEFAULT_SEARCH_QUALIFIER = "";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SearchIndexParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element element) throws ExtensionParserException {

        logger.debug("Parsing index configuration");

        try {
            SearchIndexExtension extension = new SearchIndexExtension();
            ExtensionId id = new ExtensionId(element.getAttribute(ATTR_ID));
            extension.setIdentifier(id);

            StringProperty name = new StringProperty();
            name.setValue(getAttribute(element, ATTR_NAME));
            extension.setName(name);

            StringProperty key = new StringProperty();
            Element indexElemenet = super.getElementsByTagName(element, TAG_INDEX).get(0);
            key.setValue(super.getAttribute(indexElemenet, ATTR_KEY));
            extension.setKey(key);

            List<Element> fieldList = getElementsByTagName(element, TAG_FIELD);
            List<SearchFieldExtension> list = extension.getFieldList();

            for (Element field : fieldList) {
                list.add(this.parseField(field));
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }

    }

    /**
     * Parse the search field extension.
     * 
     * @param element
     *            the search field element
     * 
     * @return the search field extension
     * 
     * @throws ExtensionParserException
     */
    private SearchFieldExtension parseField(Element element) throws ExtensionParserException {

        SearchFieldExtension field = new SearchFieldExtension();

        field.setName(super.getStringProperty(element, ATTR_NAME));
        field.setSearchQualifier(super.getStringProperty(element, ATTR_SEARCH_QUALIFIER, DEFAULT_SEARCH_QUALIFIER));

        field.setSearchField(super.getBooleanProperty(element, ATTR_SEARCH_SEARCH));

        return field;

    }

}
