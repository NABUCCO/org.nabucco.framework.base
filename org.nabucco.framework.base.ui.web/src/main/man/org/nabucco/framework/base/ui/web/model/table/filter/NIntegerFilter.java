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

import org.nabucco.framework.base.facade.datatype.NInteger;
import org.nabucco.framework.base.facade.datatype.NType;

/**
 * Default filter for NABUCCO Date Basetypes.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class NIntegerFilter implements TableColumnFilter {

    private int minValue;

    private int maxValue;

    /**
     * Creates a new {@link NIntegerFilter} instance.
     * 
     * @param minDate
     *            the minimum date
     * @param maxDate
     *            the maximum date
     */
    public NIntegerFilter(int from, int to) {
        this.minValue = from;
        this.maxValue = to;
    }

    @Override
    public boolean accept(NType basetype) {
        if (!(basetype instanceof NInteger)) {
            return false;
        }

        int value = ((NInteger) basetype).getValue();

        if (this.minValue > this.maxValue) {
            return true;
        }

        return (value > minValue && value < maxValue);
    }
}
