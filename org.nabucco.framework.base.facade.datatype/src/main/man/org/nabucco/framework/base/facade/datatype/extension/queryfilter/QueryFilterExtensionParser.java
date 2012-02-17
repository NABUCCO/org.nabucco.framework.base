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
package org.nabucco.framework.base.facade.datatype.extension.queryfilter;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterParameterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterSetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Extension Parser for Query Filter and FilterSet Extensions.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class QueryFilterExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_FILTER_SET = "filterSet";

    private static final String ELEMENT_FILTER = "filter";

    private static final String ELEMENT_QUERY = "query";

    private static final String ELEMENT_PARAMETER = "parameter";

    private static final String ATTR_COMPONENT = "component";

    private static final String ATTR_ID = "id";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_DESCRIPTION = "description";

    private static final String ATTR_DEFAULT = "default";

    private static final String ATTR_TYPE = "type";
    
    private static final String ATTR_SYSTEM = "system";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            QueryFilterExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing query filter configuration.");

            QueryFilterSetExtension filterSetExtension = new QueryFilterSetExtension();
            filterSetExtension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            Element filterSetElement = super.getElementsByTagName(element, ELEMENT_FILTER_SET).get(0);
            
            filterSetExtension.setFilterSetId(super.getStringProperty(filterSetElement, ATTR_ID));
            filterSetExtension.setName(super.getStringProperty(filterSetElement, ATTR_NAME));
            filterSetExtension.setDescription(super.getStringProperty(filterSetElement, ATTR_DESCRIPTION));
            filterSetExtension.setComponent(super.getClassProperty(filterSetElement, ATTR_COMPONENT));

            List<Element> filterElements = super.getElementsByTagName(filterSetElement, ELEMENT_FILTER);
            for (Element filterElement : filterElements) {
                QueryFilterExtension filterExtension = this.parseFilter(filterElement);
                filterSetExtension.getFilters().add(filterExtension);
            }

            return new NabucoExtensionContainer(filterSetExtension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Error parsing connection extension.", e);
        }
    }

    /**
     * Parse the filter xml element to an extension.
     * 
     * @param filterElement
     *            the filter element
     * 
     * @return the filter extension
     * 
     * @throws ExtensionParserException
     *             when the filter element cannot be parsed
     * @throws ExtensionException
     *             when the filter parameters cannot be parsed
     */
    private QueryFilterExtension parseFilter(Element filterElement) throws ExtensionParserException, ExtensionException {
        QueryFilterExtension filterExtension = new QueryFilterExtension();
        filterExtension.setIdentifier(new ExtensionId(filterElement.getAttribute(ATTR_ID)));
        filterExtension.setName(super.getStringProperty(filterElement, ATTR_NAME));
        filterExtension.setDescription(super.getStringProperty(filterElement, ATTR_DESCRIPTION));

        List<Element> queries = super.getElementsByTagName(filterElement, ELEMENT_QUERY);
        if (queries.isEmpty()) {
            throw new ExtensionParserException("No query defined for filter '" + filterExtension.getIdentifier() + "'.");
        }
        if (queries.size() > 1) {
            throw new ExtensionParserException("More than 1 query defined for filter '"
                    + filterExtension.getIdentifier() + "'.");
        }
        filterExtension.setQuery(super.getTextContent(queries.get(0)));

        List<Element> parameterElements = super.getElementsByTagName(filterElement, ELEMENT_PARAMETER);
        for (Element parameterElement : parameterElements) {
            QueryFilterParameterExtension parameter = new QueryFilterParameterExtension();
            parameter.setName(super.getStringProperty(parameterElement, ATTR_NAME));
            parameter.setDescription(super.getStringProperty(parameterElement, ATTR_DESCRIPTION));
            parameter.setType(super.getClassProperty(parameterElement, ATTR_TYPE));
            parameter.setValue(super.getStringProperty(parameterElement, ATTR_DEFAULT));
            parameter.setSystemValue(super.getEnumerationProperty(parameterElement, ATTR_SYSTEM));
            filterExtension.getParameters().add(parameter);
        }

        return filterExtension;
    }
}
