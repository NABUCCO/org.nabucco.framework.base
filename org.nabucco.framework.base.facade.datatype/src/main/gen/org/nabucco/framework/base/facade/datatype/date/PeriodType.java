/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Generated with NABUCCO Generator 
*/
package org.nabucco.framework.base.facade.datatype.date;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * PeriodType<p/>Defines a period of time<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-26
 */
public enum PeriodType implements Enumeration {
    YEARS("Years"), MONTHS("Months"), DAYS("Days"), HOURS("Hours"), MINUTES("Minutes"), SECONDS(
            "Seconds"), MILLISECONDS("Milliseconds"), ;

    private String id;

    /**
     * Constructs a new PeriodType instance.
     *
     * @param id the String.
     */
    PeriodType(String id) {
        this.id = id;
    }

    @Override
    public Enumeration cloneObject() {
        return this;
    }

    @Override
    public int getOrdinal() {
        return this.ordinal();
    }

    @Override
    public String getId() {
        return this.id;
    }
}
