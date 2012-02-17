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
package org.nabucco.framework.base.ui.web.component.work.util;

import java.util.StringTokenizer;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterSetExtension;

/**
 * QueryFilterExtensionUtil
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public final class QueryFilterExtensionUtil {

    private static final String ACTION_DELIMITER = ".";

    /**
     * 
     * Creates a new {@link QueryFilterExtensionUtil} instance.
     * 
     */
    private QueryFilterExtensionUtil() {
        throw new IllegalStateException("Cannot instanciate a util class");
    }

    /**
     * Getter for the filter extension
     * 
     * @param refId
     *            the id of the filter to search
     * @return the filter extension or null if not found
     * @throws ExtensionException
     *             if not found
     */
    public static QueryFilterExtension getFilterExtension(String refId) throws ExtensionException {
        QueryFilterSetExtension filterSetExtension = getFilterSetExtension(refId);
        return getFilterExtension(filterSetExtension, refId);
    }

    /**
     * Getter for the filter extension
     * 
     * @param filterSetExtension
     *            the filter set where to search
     * @param refId
     *            the id of the filter to search
     * @return the filter extension or null if not found
     * @throws ExtensionException
     *             if not found
     */
    public static QueryFilterExtension getFilterExtension(QueryFilterSetExtension filterSetExtension, String refId) {
        QueryFilterExtension retVal = null;

        if (refId == null || refId.isEmpty()) {
            return null;
        }

        if (filterSetExtension == null) {
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(refId, ACTION_DELIMITER);

        if (tokenizer.countTokens() != 2) {
            return null;
        }

        tokenizer.nextToken();
        String searchingFilterId = tokenizer.nextToken();

        for (QueryFilterExtension filter : filterSetExtension.getFilters()) {
            String filterId = filter.getIdentifier().getValue();

            if (filterId == null) {
                return null;
            }
            if (filterId.equals(searchingFilterId)) {
                retVal = filter;
                break;
            }
        }

        return retVal;
    }

    /**
     * Search method for the filter set extension
     * 
     * @param refId
     *            the id of the filter to search
     * @return set extension or null if not found
     * @throws ExtensionException
     *             if not found
     */
    public static QueryFilterSetExtension getFilterSetExtension(String refId) throws ExtensionException {

        ExtensionMap filterSets = NabuccoSystem.getExtensionResolver().resolveExtensions(
                ExtensionPointType.ORG_NABUCCO_QUERYFILTER);

        if (refId == null || refId.isEmpty()) {
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(refId, ACTION_DELIMITER);

        if (tokenizer.countTokens() != 2) {
            return null;
        }

        String searchingSetId = tokenizer.nextToken();

        for (String extensionName : filterSets.getExtensionNames()) {
            QueryFilterSetExtension setExt = (QueryFilterSetExtension) filterSets.getExtension(extensionName);

            String setId = PropertyLoader.loadProperty(setExt.getFilterSetId());
            if (setId == null) {
                return null;
            }

            if (setId.equals(searchingSetId)) {
                return setExt;
            }
        }

        return null;
    }
}
