/*
 * Copyright 2012 PRODYNA AG
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
 */
package org.nabucco.framework.base.ui.web.model.table.filter;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.code.Code;

/**
 * EnumerationFilter
 * 
 * Filter filters enumerations and codes according to the gived value
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class EnumerationFilter implements TableColumnFilter {

    List<String> literalIdList = new ArrayList<String>();

    /**
     * 
     * Creates a new {@link EnumerationFilter} instance.
     * 
     * @param literalId
     *            the literal to accept
     */
    public EnumerationFilter(String literalId) {
        literalIdList.add(literalId);
    }

    /**
     * 
     * Creates a new {@link EnumerationFilter} instance.
     * 
     * @param literalIdList
     *            the list of literals to accept
     */
    public EnumerationFilter(List<String> literalIdList) {
        literalIdList.addAll(literalIdList);
    }

    @Override
    public boolean accept(NType type) {
        if (!(type instanceof Enumeration || type instanceof Code)) {
            return false;
        }

        String literal = null;

        if (type instanceof Enumeration) {
            Enumeration value = (Enumeration) type;
            literal = value.toString();
        } else if (type instanceof Code) {
            Code value = (Code) type;
            literal = value.getName().getValue();
        }

        if (literal == null) {
            return false;
        }

        for (String id : literalIdList) {
            if (literal.equals(id)) {
                return true;
            }
        }

        return false;
    }
}
