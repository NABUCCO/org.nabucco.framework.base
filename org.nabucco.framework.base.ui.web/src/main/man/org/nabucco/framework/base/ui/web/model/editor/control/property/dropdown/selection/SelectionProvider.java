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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * SelectionProvider
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface SelectionProvider<N extends NType> {

    /**
     * Returns the List with all possible selections
     * 
     * @param Property
     *            Property to be evaluated
     * 
     * @return List List with Literals, Codes ...
     */
    List<SelectionItem<N>> getSelectionItems(NabuccoProperty property);

    /**
     * Return String value of one item
     * 
     * @param property
     *            the property where to search
     * @param item
     *            the item to get the string value
     * 
     * @return the string value
     * 
     */
    SelectionItem<N> getSelectionValue(NabuccoProperty property, N item);

}
