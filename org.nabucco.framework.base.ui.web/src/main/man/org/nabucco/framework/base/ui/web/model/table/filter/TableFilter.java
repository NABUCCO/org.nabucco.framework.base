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
package org.nabucco.framework.base.ui.web.model.table.filter;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;

/**
 * Basic implementation of Table Filtering.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class TableFilter {

    private String property;

    private List<TableColumnFilter> filterList;

    /**
     * Creates a new {@link TableFilter} instance.
     * 
     * @param property
     *            the datatype property to filter
     */
    public TableFilter(String property) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot filter property [null].");
        }
        this.property = property;
        this.filterList = new ArrayList<TableColumnFilter>();
    }

    /**
     * Add a column filter to the table filter.
     * 
     * @param filter
     */
    public void add(TableColumnFilter filter) {
        this.filterList.add(filter);
    }

    /**
     * Accepts the datatype.
     * 
     * @param datatype
     *            The datatype to accept.
     * 
     * @return <b>true</b> if datatype is accepted, otherwise <b>false</b>.
     */
    public boolean accept(Datatype datatype) {
        boolean accept = true;

        NabuccoProperty np = datatype.getProperty(this.property);
        if (np == null || np.getPropertyType() != NabuccoPropertyType.BASETYPE) {
            return false;
        }

        Basetype value = ((BasetypeProperty) np).getInstance();
        for (TableColumnFilter filter : this.filterList) {
            accept = accept && filter.accept(value);
            if (!accept) {
                break;
            }
        }

        return accept;
    }
}
