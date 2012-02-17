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
package org.nabucco.framework.base.ui.web.model.common.renderer;

import org.nabucco.framework.base.facade.datatype.Datatype;

/**
 * WebLabelProviderFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WebLabelProviderFactory<T extends Datatype> {

    /**
     * Create the label provider for the given datatype. This implementation always return a
     * {@link PropertyLabelProvider} instance. Subclass for different implmentations.
     * 
     * @return the label provider for the given datatype
     */
    public WebLabelProvider<T> createLabelProvider(T datatype) {
        return new PropertyLabelProvider<T>(datatype);
    }

}
