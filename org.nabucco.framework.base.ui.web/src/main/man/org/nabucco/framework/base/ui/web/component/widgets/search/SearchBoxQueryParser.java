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
package org.nabucco.framework.base.ui.web.component.widgets.search;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchBoxExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchConfigurationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchFieldExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchIndexExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchIndexRefExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchPrefixExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchQualifierExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQuery;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQueryField;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQueryValueField;

/**
 * SearchBoxQueryParser
 * <p/>
 * Simple query parser for the searchbox of nabucco.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SearchBoxQueryParser {

    /** The query token delimiter. */
    private static final String DELIMITER = " ";

    /** The Logger */
    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            SearchBoxQueryParser.class);

    private SearchConfigurationExtension config;

    private ExtensionMap indexConfig;

    /**
     * Creates a new {@link SearchBoxQueryParser} instance.
     */
    public SearchBoxQueryParser() {
        try {
            this.config = (SearchConfigurationExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                    ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SEARCH_CONFIG, ExtensionResolver.DEFAULT_EXTENSION);

            this.indexConfig = NabuccoSystem.getExtensionResolver().resolveExtensions(
                    ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SEARCH_INDEX);

        } catch (ExtensionException ee) {
            logger.error(ee, "Cannot load Extension for configuring SearchBoxQueryParser.");
        } catch (Exception e) {
            logger.error(e, "Error loading extensions for configuring SearchBoxQueryParser.");
        }
    }

    /**
     * Parse a query string of a single search box to a searchable fulltext query.
     * 
     * @param query
     *            the query to parse
     * 
     * @return the parsed fulltext query
     */
    public FulltextQuery parseSearchBoxQuery(String query) {
        FulltextQuery ftQuery = new FulltextQuery();
        List<FulltextQueryField> fieldList = ftQuery.getFieldList();

        // setting some defaults
        ftQuery.setMaxResult(this.config.getSearchbox().getMaxResults().getValue().getValue());
        ftQuery.setIndexName(this.config.getSearchbox().getDefaultIndex().getValue().getValue());

        // Query has no content, returning empty (no fields) fulltextquery
        if (query == null || query.length() == 0) {
            return ftQuery;
        }

        query = query.trim();

        try {

            String[] queryTerms = query.split(DELIMITER);
            if (queryTerms.length > 0) {

                String firstTerm = this.getSaveTerm(queryTerms, 0);
                if (firstTerm.length() == 0) {
                    return ftQuery;
                }

                if (this.hasQualifier(firstTerm)) {
                    String termQualifier = this.getTermQualifier(firstTerm);
                    String termValue = this.getTermValue(firstTerm);

                    if (!this.isPrefixConfigured(termQualifier)) {
                        return ftQuery;
                    }

                    String indexName = this.getIndexNameForQualifier(termQualifier);
                    ftQuery.setIndexName(indexName);

                    FulltextQueryValueField firstField = new FulltextQueryValueField();
                    firstField.setFieldName(this.getFieldName(ftQuery.getIndexName().getValue(), 0));
                    firstField.setFieldValue(termValue);
                    firstField.setFieldType(BasetypeType.STRING);
                    fieldList.add(firstField);

                } else {
                    // term has no qualifier, using default configured index
                    ftQuery.setIndexName(this.config.getSearchbox().getDefaultIndex().getValue().getValue());
                    FulltextQueryValueField firstField = new FulltextQueryValueField();
                    firstField.setFieldName(this.getFieldName(ftQuery.getIndexName().getValue(), 0));
                    firstField.setFieldValue(firstTerm);
                    firstField.setFieldType(BasetypeType.STRING);
                    fieldList.add(firstField);
                }

                for (int i = 1; i < queryTerms.length; i++) {

                    String term = this.getSaveTerm(queryTerms, i);
                    if (term.length() == 0) {
                        continue;
                    }

                    if (this.hasQualifier(term)) {

                        String termQualifier = getTermQualifier(term);
                        String termValue = getTermValue(term);

                        FulltextQueryValueField ftField = new FulltextQueryValueField();
                        ftField.setFieldName(termQualifier);
                        ftField.setFieldValue(termValue);
                        ftField.setFieldType(BasetypeType.STRING);
                        fieldList.add(ftField);

                    } else {
                        // term has no qualifier, using default configured index

                        String fieldName = this.getFieldName(ftQuery.getIndexName().getValue(), i);
                        // String fieldName =
                        // this.getFieldNameFromIndex(ftQuery.getIndexName().getValue(), i);

                        FulltextQueryValueField ftField = new FulltextQueryValueField();
                        ftField.setFieldName(fieldName);
                        ftField.setFieldValue(term);
                        ftField.setFieldType(BasetypeType.STRING);
                        fieldList.add(ftField);
                    }
                }

            } else {
                // no elements, only empty characters
                return ftQuery;
            }

        } catch (Exception e) {
            logger.error(e, "Error during search query parsing.");
            return ftQuery;
        }

        return ftQuery;

    }

    /**
     * Retrieve the term at the given position. When no term is found an empty string is returned.
     * 
     * @param terms
     *            the list of terms
     * @param position
     *            the term position
     * 
     * @return the term at the given position, or an empty string if not term was found
     */
    private String getSaveTerm(String[] terms, int position) {

        if (terms == null || terms.length == 0 || terms.length < position || position < 0) {
            return "";
        }

        return terms[position].trim();
    }

    /**
     * Split a term and return its qualifier.
     * 
     * @param term
     *            the term to evaluate
     * 
     * @return the term's qualifier
     */
    private String getTermQualifier(String term) {
        return term.split(":")[0].trim();
    }

    /**
     * Split a term and return its value.
     * 
     * @param term
     *            the term to evaluate
     * 
     * @return the term's value
     */
    private String getTermValue(String term) {
        return term.split(":")[1].trim();
    }

    /**
     * Check whether the term starts with a qualifier.
     * 
     * @param term
     *            the term to check
     * 
     * @return <b>true</b>if the term has a qualifier, <b>false</b> if not
     */
    private boolean hasQualifier(String term) {
        if (term == null || term.length() <= 2) {
            return false;
        }

        String[] termParts = term.split(":");
        if (termParts.length > 2 || termParts.length <= 1) {
            return false;
        }

        return true;

    }

    /**
     * Retrieve the field name for the given position.
     * 
     * @param position
     *            the field position
     * 
     * @return the field name
     */
    private String getFieldName(String index, int position) {
        SearchBoxExtension extension = this.config.getSearchbox();
        if (extension == null || extension.getPrefixList().first() == null) {
            return "";
        }

        SearchIndexRefExtension indexExtension = null;
        for (SearchPrefixExtension prefixExtension : extension.getPrefixList()) {
            for (SearchIndexRefExtension currentIndex : prefixExtension.getIndexList()) {
                if (currentIndex.getName().getValue().getValue().equals(index)) {
                    indexExtension = currentIndex;
                }
            }
        }

        if (indexExtension == null || indexExtension.getQualifierList().size() <= position) {
            return "";
        }

        SearchQualifierExtension qualifierExtension = indexExtension.getQualifierList().get(position);
        if (qualifierExtension == null || qualifierExtension.getName() == null) {
            return "";
        }

        return qualifierExtension.getName().getValue().getValue();
    }

    /**
     * Retrieve the field name from the given index position.
     * 
     * @param index
     *            the index
     * @param position
     *            the position
     * 
     * @return the field name
     */
    @SuppressWarnings("unused")
    private String getFieldNameFromIndex(String index, int position) {
        try {
            SearchIndexExtension indexExt = (SearchIndexExtension) this.indexConfig.getExtension(index);
            SearchFieldExtension field = indexExt.getFieldList().get(position);
            return field.getName().getValue().getValue();
        } catch (Exception e) {
            return "NO_FIELD_FOR_POSITION:" + position + "INDEX:" + index;
        }
    }

    /**
     * Retrieve the index name for the given qualified.
     * 
     * @param qualifier
     *            the qualifier
     * 
     * @return the field name
     */
    private String getIndexNameForQualifier(String qualifier) {

        // Checking qualifier, using default if qualifier isnt valid
        if (qualifier == null || qualifier.length() == 0 || qualifier.isEmpty()) {
            return this.config.getSearchbox().getDefaultIndex().getValue().getValue();
        }

        List<SearchPrefixExtension> extensionList = this.config.getSearchbox().getPrefixList();
        for (SearchPrefixExtension prefixExtension : extensionList) {

            if (prefixExtension.getId().getValue().getValue().equals(qualifier)) {
                // Using the first indexName forces the search to one index
                return prefixExtension.getIndexList().first().getName().getValue().getValue();
            }
        }

        // No match found, using default index
        return this.config.getSearchbox().getDefaultIndex().getValue().getValue();
    }

    /**
     * Checks whether the the given prefix is configured.
     * 
     * @param prefix
     *            the prefix to check
     * 
     * @return <b>true</b> if a prefix is configured, <b>false</b> if not
     */
    private boolean isPrefixConfigured(String prefix) {
        for (SearchPrefixExtension prefixExtension : this.config.getSearchbox().getPrefixList()) {
            if (prefixExtension.getId().getValue().getValue().equals(prefix)) {
                return true;
            }
        }
        return false;
    }
}
