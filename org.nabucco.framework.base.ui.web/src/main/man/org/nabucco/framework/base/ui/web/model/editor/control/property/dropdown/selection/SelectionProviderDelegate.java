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
package org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection;

import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceChoiseEntry;

/**
 * DefaultSelectionProvider
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SelectionProviderDelegate<N extends NType> implements SelectionProvider<N> {

    SelectionProvider<N> instance = null;

    /**
     * 
     * Selection Provider Delegate analyses the bound property to runtime and delates the selection
     * request to the correct implementation
     * 
     */
    public SelectionProviderDelegate() {

    }

    @Override
    public List<SelectionItem<N>> getSelectionItems(NabuccoProperty property) {
        if (property == null) {
            return Collections.<SelectionItem<N>> emptyList();
        }
        SelectionProvider<N> delegate = this.getDelegate(property);
        List<SelectionItem<N>> selectionItems = delegate.getSelectionItems(property);

        return selectionItems;
    }

    @Override
    public SelectionItem<N> getSelectionValue(NabuccoProperty property, N item) {
        if (property == null) {
            return new SelectionItem<N>();
        }

        SelectionProvider<N> delegate = this.getDelegate(property);
        SelectionItem<N> retVal = delegate.getSelectionValue(property, item);
        return retVal;
    }

    @SuppressWarnings({ "unchecked" })
    private SelectionProvider<N> getDelegate(NabuccoProperty property) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot resolve delegate to property 'null'");
        }
        if (this.instance == null) {
            if (property.getParent().getProperty(TemplateDSInstanceChoiseEntry.CODEPATH) != null) {
                // TODO: Remove this workaround asfar we can set code path dynamicaly
                this.instance = (SelectionProvider<N>) new DatastructureCodeSelectionProvider();
            } else if (property == null || property.getCodePath() == null) {
                this.instance = (SelectionProvider<N>) new EnumerationSelectionProvider();
            } else {
                this.instance = (SelectionProvider<N>) new CodeSelectionProvider();
            }
        }

        return this.instance;
    }

}
