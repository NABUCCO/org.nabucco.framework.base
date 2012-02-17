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

import java.util.Date;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NDate;

/**
 * Default filter for NABUCCO Date Basetypes.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class NDateFilter implements TableColumnFilter {

    private Date minDate = null;

    private Date maxDate = null;

    /**
     * Creates a new {@link NDateFilter} instance.
     * 
     * @param minDate
     *            the minimum date
     * @param maxDate
     *            the maximum date
     */
    public NDateFilter(Date minDate, Date maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    @Override
    public boolean accept(Basetype basetype) {
        if (!(basetype instanceof NDate)) {
            return false;
        }

        Date date = ((NDate) basetype).getValue();

        if (date == null) {
            return false;
        }

        if (this.minDate == null && this.maxDate == null) {
            return true;
        }

        if (this.minDate == null) {
            return date.before(this.maxDate) || date.equals(this.maxDate);
        }

        if (this.maxDate == null) {
            return date.after(this.minDate) || date.equals(this.minDate);
        }

        return (date.before(this.maxDate) || date.equals(this.maxDate))
                && (date.after(this.minDate) || date.equals(this.minDate));
    }
}
