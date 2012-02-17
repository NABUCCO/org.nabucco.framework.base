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

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;

/**
 * Default filter for NABUCCO String Basetypes.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class NStringFilter implements TableColumnFilter {

    /** The regular expression of the string filter. */
    private String regex;

    /**
     * Creates a new {@link NStringFilter} instance.
     * 
     * @param regex
     *            the string regex to match against
     */
    public NStringFilter(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean accept(Basetype basetype) {
        if (!(basetype instanceof NString)) {
            return false;
        }

        String value = ((NString) basetype).getValue();

        if (value == null) {
            return false;
        }

        if (this.regex == null || this.regex.length() == 0) {
            return true;
        }

        return value.matches(this.regex);
    }

}
