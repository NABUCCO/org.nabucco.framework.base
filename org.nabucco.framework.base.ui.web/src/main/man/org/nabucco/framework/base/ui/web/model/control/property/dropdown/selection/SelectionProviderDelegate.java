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
package org.nabucco.framework.base.ui.web.model.control.property.dropdown.selection;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * DefaultSelectionProvider
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SelectionProviderDelegate<N extends NType> implements SelectionProvider<N> {

    SelectionProvider<?> instance = null;

    /**
     * 
     * Selection Provider Delegate analyses the bound property to runtime and delates the selection
     * request to the correct implementation
     * 
     */
    public SelectionProviderDelegate() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<N> getSelection(NabuccoProperty property) {
        SelectionProvider<?> delegate = this.getDelegate(property);
        List<?> selection = delegate.getSelection(property);
        List<N> retVal = new ArrayList<N>();

        for (Object selectionItem : selection) {
            retVal.add((N) selectionItem);
        }

        return retVal;
    }

    @Override
    public List<String> getSelectionStrings(NabuccoProperty property) {
        SelectionProvider<?> delegate = this.getDelegate(property);

        return delegate.getSelectionStrings(property);
    }

    @SuppressWarnings("all")
    @Override
    public String getStringValue(NabuccoProperty property, NType item) {
        SelectionProvider<?> delegate = this.getDelegate(property);
        String retVal = delegate.getStringValue(property, item);
        return retVal;
    }
    

    private SelectionProvider<?> getDelegate(NabuccoProperty property) {
        if (this.instance == null) {
            if (property == null || property.getCodePath() == null) {
                this.instance = new EnumerationSelectionProvider();
            } else {
                this.instance = new CodeSelectionProvider();
            }
        }

        return this.instance;
    }

}
