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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.property.EnumProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;

/**
 * EnumerationSelectionProvider
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class EnumerationSelectionProvider implements SelectionProvider<Enumeration> {

    /**
     * Creates a new {@link EnumerationSelectionProvider} instance.
     */
    public EnumerationSelectionProvider() {
    }

    @Override
    public List<SelectionItem<Enumeration>> getSelectionItems(NabuccoProperty property) {
        List<SelectionItem<Enumeration>> retVal = new ArrayList<SelectionItem<Enumeration>>();

        List<Enumeration> selection = this.getSelection(property);
        for (Enumeration item : selection) {
            String path = item.getClass().getCanonicalName();
            SelectionItem<Enumeration> selectionItem = new SelectionItem<Enumeration>(item, item.toString(), path);

            retVal.add(selectionItem);
        }
        return retVal;
    }

    /**
     * Returns the enumeration literals
     * 
     * @param property
     *            property to use
     * @return
     */
    private List<Enumeration> getSelection(NabuccoProperty property) {
        if (property == null) {
            return Collections.emptyList();
        }
        if (property.getPropertyType() == NabuccoPropertyType.ENUMERATION) {
            EnumProperty enumProperty = (EnumProperty) property;

            return enumProperty.getLiterals();
        }
        return Collections.emptyList();
    }

    @Override
    public SelectionItem<Enumeration> getSelectionValue(NabuccoProperty property, Enumeration item) {
        if (item == null) {
            return new SelectionItem<Enumeration>();
        }

        String literalName = item.toString();
        String path = item.getClass().getCanonicalName();
        SelectionItem<Enumeration> retVal = new SelectionItem<Enumeration>(item, literalName, path);
        return retVal;
    }

}
