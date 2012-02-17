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
import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.EnumProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;

/**
 * EnumerationSelectionProvider
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class EnumerationSelectionProvider implements SelectionProvider<Enumeration> {

    private static final String DEFAULT_LABEL = "";

    /**
     * Creates a new {@link EnumerationSelectionProvider} instance.
     */
    public EnumerationSelectionProvider() {
    }

    @Override
    public List<Enumeration> getSelection(NabuccoProperty property) {
        if (property.getPropertyType() == NabuccoPropertyType.ENUMERATION) {
            EnumProperty enumProperty = (EnumProperty) property;

            return enumProperty.getLiterals();
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getSelectionStrings(NabuccoProperty property) {
        List<String> selectionStrings = new ArrayList<String>();

        if (property == null) {
            return selectionStrings;
        }

        List<Enumeration> selection = this.getSelection(property);
        for (Enumeration literal : selection) {
            selectionStrings.add(literal.toString());
        }

        return selectionStrings;
    }

    @Override
    public String getStringValue(NabuccoProperty property, NType item) {
        if (item == null) {
            return DEFAULT_LABEL;
        }
        return item.toString();
    }

}
