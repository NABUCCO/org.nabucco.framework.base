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
package org.nabucco.framework.base.ui.web.component.perspective;

import java.util.Comparator;

/**
 * PerspectiveComparator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class PerspectiveComparator implements Comparator<Perspective> {

    /**
     * Singleton instance.
     */
    private static PerspectiveComparator instance = new PerspectiveComparator();

    /**
     * Private constructor.
     */
    private PerspectiveComparator() {
    }

    /**
     * Singleton access.
     * 
     * @return the PerspectiveComparator instance.
     */
    public static PerspectiveComparator getInstance() {
        return instance;
    }

    @Override
    public int compare(Perspective p1, Perspective p2) {
        if (p1 == null) {
            if (p2 == null) {
                return 0;
            }
            return -1;
        }
        if (p2 == null) {
            return 1;
        }

        Integer i1 = p1.getIndex();
        Integer i2 = p2.getIndex();

        if (i1 == null) {
            if (i2 == null) {
                return 0;
            }
            return -1;
        }
        if (i2 == null) {
            return 1;
        }

        return i1.compareTo(i2);
    }

}
