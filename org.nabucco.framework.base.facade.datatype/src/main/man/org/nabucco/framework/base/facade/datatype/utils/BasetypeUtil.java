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
package org.nabucco.framework.base.facade.datatype.utils;

import org.nabucco.framework.base.facade.datatype.Basetype;

/**
 * BasetypeUtil
 * <p/>
 * Utility class for {@link Basetype} verification.
 * 
 * @author Juergen Weismueller, PRODYNA AG
 */
public final class BasetypeUtil {

    /**
     * Private constructor must not be invoked.
     */
    private BasetypeUtil() {
    }

    /**
     * Checks if a given basetype has content in its value. It uses <code>getValueAsString</code>
     * for content checking and <b>ignores</b> whitespaces which will be cut away by
     * <code>String.trim()</code>.
     * 
     * @param basetype
     *            the input basetype
     * 
     * @return <b>true</b> if conditions above are true
     */
    public static boolean isEmpty(Basetype basetype) {
        if (basetype == null) {
            return true;
        }
        if (basetype.getValue() == null) {
            return true;
        }
        if (basetype.getValueAsString() == null) {
            return true;
        }
        if (basetype.getValueAsString().length() == 0) {
            return true;
        }
        if (basetype.getValueAsString().trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a given basetype has a non-null value or not.
     * 
     * @param basetype
     *            the input basetype
     * 
     * @return <b>true</b> if the value is set, <b>false</b> if not
     */
    public boolean hasValue(Basetype basetype) {
        if (basetype == null) {
            return false;
        }
        if (basetype.getValue() == null) {
            return false;
        }
        return true;
    }

}
