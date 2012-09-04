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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;

/**
 * Basic implementation of Table Filtering.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class TableFilter {

    private Map<String, TableColumnFilter> filterMap;

    /**
     * Creates a new {@link TableFilter} instance.
     * 
     * @param property
     *            the datatype property to filter
     */
    public TableFilter() {
        this.filterMap = new HashMap<String, TableColumnFilter>();
    }

    /**
     * Add a column filter to the table filter.
     * 
     * @param propertyPath
     *            the property path for the filter
     * @param filter
     *            the filter instance
     */
    public void add(String propertyPath, TableColumnFilter filter) {
        if (propertyPath == null) {
            throw new IllegalArgumentException("Cannot add filter to the propertypath 'null'");
        }

        this.filterMap.put(propertyPath, filter);
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
        NabuccoPropertyResolver<Datatype> resolver = new NabuccoPropertyResolver<Datatype>(datatype);

        for (String propertyPath : this.filterMap.keySet()) {
            TableColumnFilter filter = this.filterMap.get(propertyPath);

            NabuccoProperty property = resolver.resolveProperty(propertyPath);
            Object value = property.getInstance();

            accept = accept && filter.accept((NType) value);

            if (!accept) {
                break;
            }
        }

        return accept;
    }
}
