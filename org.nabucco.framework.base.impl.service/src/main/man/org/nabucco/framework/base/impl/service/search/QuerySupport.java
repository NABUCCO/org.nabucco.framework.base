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
 */
package org.nabucco.framework.base.impl.service.search;

import org.nabucco.framework.base.facade.datatype.NString;

/**
 * QuerySupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class QuerySupport {

    /**
     * Private constructor must not be invoked.
     */
    private QuerySupport() {
        throw new IllegalStateException("Private constructor must not be invoked.");
    }

    /**
     * Converts an {@link NString} basetype to a search parameter.
     * 
     * @param <N>
     *            the NString sub-class
     * @param nString
     *            the orginal nString
     * 
     * @return the nString copy
     */
    public static <N extends NString> N searchParameter(N nString) {
        if (nString == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        N clone = (N) nString.cloneObject();
        clone.setValue("%" + nString.getValue() + "%");
        return clone;
    }

}
