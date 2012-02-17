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
package org.nabucco.framework.base.ui.web.model.table.content;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;

/**
 * Content Provider for Table Content.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class TableContentProvider<T extends Datatype> {

    /**
     * Getter for the entries at the given table position.
     * 
     * @param datatype
     *            the business object
     * @param index
     *            the index number
     * @param amount
     *            the amount of entries to return
     * 
     * @return the list of table entries.
     * 
     * @throws ClientException
     *             when the entries cannot be retrieved
     */
    public abstract List<T> getEntries(Datatype datatype, int index, int amount) throws ClientException;

    /**
     * Getter for the amount of table entries.
     * 
     * @param datatype
     *            the business object
     * 
     * @return the table size
     * 
     * @throws ClientException
     *             when the size cannot be determined
     */
    public abstract int getSize(Datatype datatype) throws ClientException;

}
