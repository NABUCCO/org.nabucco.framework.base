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
package org.nabucco.framework.base.ui.web.action.handler.search;

import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProviderFactory;

/**
 * SearchResultLabelProviderFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SearchResultLabelProviderFactory extends WebLabelProviderFactory<FulltextDocument> {

    /**
     * Creates a new {@link SearchResultLabelProviderFactory} instance.
     */
    SearchResultLabelProviderFactory() {
    }

    @Override
    public WebLabelProvider<FulltextDocument> createLabelProvider(FulltextDocument fulltextDocument) {
        return new SearchResultLabelProvider(fulltextDocument);
    }

}
