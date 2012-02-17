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
package org.nabucco.framework.base.ui.web.component.work.list;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * IsTableElement
 * 
 * Interface to work with table-based elements If the webelement works with TableModel, it can
 * implement this interface
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface TableElement {

    /**
     * Returns the tablemodel of the element
     * 
     * @param <T>
     *            datatype
     * @return table model
     */
    <T extends Datatype> TableModel<T> getTableModel();

    /**
     * Return the type of the element
     * 
     * @return type of the element
     */
    WebElementType getType();
}
