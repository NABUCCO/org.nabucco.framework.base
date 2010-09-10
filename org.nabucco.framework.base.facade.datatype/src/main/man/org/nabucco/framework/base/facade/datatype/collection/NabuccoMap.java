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
package org.nabucco.framework.base.facade.datatype.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * NabuccoMap
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs an empty <tt>NabuccoMap</tt> with the default initial capacity (16) and the
     * default load factor (0.75).
     */
    public NabuccoMap() {
        super();
    }

    /**
     * Constructs an empty <tt>NabuccoMap</tt> with the specified initial capacity and the default
     * load factor (0.75).
     * 
     * @param initialCapacity
     *            the initial capacity.
     * 
     * @throws IllegalArgumentException
     *             if the initial capacity is negative.
     */
    public NabuccoMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a new <tt>NabuccoMap</tt> with the same mappings as the specified <tt>Map</tt>.
     * The <tt>NabuccoMap</tt> is created with default load factor (0.75) and an initial capacity
     * sufficient to hold the mappings in the specified <tt>Map</tt>.
     * 
     * @param map
     *            the map whose mappings are to be placed in this map
     * 
     * @throws NullPointerException
     *             if the specified map is null
     */
    public NabuccoMap(Map<K, V> map) {
        super(map);
    }

}
