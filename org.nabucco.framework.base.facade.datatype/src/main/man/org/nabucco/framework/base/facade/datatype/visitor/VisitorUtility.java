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
package org.nabucco.framework.base.facade.datatype.visitor;

import java.util.Arrays;

/**
 * VisitorUtility
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class VisitorUtility {

    /**
     * Private constructor must not be invoked.
     */
    private VisitorUtility() {
        throw new IllegalStateException("Private constructor must not be invoked.");
    }

    /**
     * Merges two Arrays into one common array.
     * 
     * @param <T>
     *            type of the array
     * @param first
     *            the first array
     * @param second
     *            the second array
     * 
     * @return the merged array (first, second)
     */
    public static <T> T[] merge(T[] first, T[] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }

        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

}
