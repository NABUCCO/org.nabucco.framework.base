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
package org.nabucco.framework.base.facade.datatype.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * ForwardingMap
 * 
 * @param <E>
 *            the map element type
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class ForwardingMap<E extends NType> implements Map<String, E>, ForwardingCollection<E> {

    private static final long serialVersionUID = 1L;

    private Map<String, E> delegate;

    /**
     * Creates a new forwarding map delegating to an empty map.
     */
    public ForwardingMap() {
        this.delegate = new HashMap<String, E>();
    }

    /**
     * Creates a new forwarding map delegating to an empty map with the given initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity of the delegating map
     */
    public ForwardingMap(int initialCapacity) {
        this.delegate = new HashMap<String, E>(initialCapacity);
    }

    /**
     * Creates a new forwarding map delegating to the given map.
     * 
     * @param delegate
     *            the map to delegate to
     */
    public ForwardingMap(Map<String, E> delegate) {
        if (delegate == null) {
            this.delegate = new HashMap<String, E>();
        } else {
            this.delegate = delegate;
        }
    }

    /**
     * Creates a new forwarding map delegating to an empty map with the given initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity of the delegating map
     * @param loadFactor
     *            the load factor of the delegating map
     */
    public ForwardingMap(int initialCapacity, float loadFactor) {
        this.delegate = new HashMap<String, E>(initialCapacity, loadFactor);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.delegate.containsValue(value);
    }

    @Override
    public E get(Object key) {
        return this.delegate.get(key);
    }

    @Override
    public E put(String key, E value) {
        return this.delegate.put(key, value);
    }

    @Override
    public E remove(Object key) {
        return this.delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends E> m) {
        this.delegate.putAll(m);
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.delegate.keySet();
    }

    @Override
    public Collection<E> values() {
        return this.delegate.values();
    }

    @Override
    public Set<java.util.Map.Entry<String, E>> entrySet() {
        return this.delegate.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return this.delegate.equals(o);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    /**
     * Get the delegating map of this instance of {@link ForwardingList}.
     * 
     * @return The delegate map.
     */
    public Map<String, E> getDelegate() {
        return this.delegate;
    }

    /**
     * Set the delegating map of this instance of {@link ForwardingList}.
     * 
     * @param newDelegate
     *            The new map to set.
     */
    public void setDelegate(Map<String, E> newDelegate) {
        this.delegate = newDelegate;
    }

    /**
     * Re-initializes the delegating map with an empty map.
     */
    void init() {
        this.delegate = new HashMap<String, E>(0);
    }

    /**
     * Resets the delegating map implementation to an {@link HashMap}.
     */
    void clean() {
        this.delegate = new HashMap<String, E>(this.delegate);
    }
}
