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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * ForwardingSet
 * 
 * @param <E>
 *            the set element type
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class ForwardingSet<E extends NType> implements Set<E>, ForwardingCollection<E> {

    private static final long serialVersionUID = 1L;

    private Set<E> delegate;

    /**
     * Creates a new forwarding Set delegating to an empty Set.
     */
    public ForwardingSet() {
        this.delegate = new HashSet<E>();
    }

    /**
     * Creates a new forwarding Set delegating to an empty Set with the given initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity of the delegating Set
     */
    public ForwardingSet(int initialCapacity) {
        this.delegate = new HashSet<E>(initialCapacity);
    }

    /**
     * Creates a new forwarding Set delegating to the given Set.
     * 
     * @param Set
     *            the Set to delegate to
     */
    public ForwardingSet(Set<E> Set) {
        if (Set == null) {
            this.delegate = new HashSet<E>();
        } else {
            this.delegate = Set;
        }
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
    public boolean contains(Object o) {
        return this.delegate.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.delegate.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.delegate.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.delegate.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return this.delegate.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.delegate.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.delegate.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.delegate.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.delegate.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.delegate.removeAll(c);
    }

    @Override
    public void clear() {
        this.delegate.clear();
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
     * Get the delegating set of this instance of {@link ForwardingSet}.
     * 
     * @return The delegate set.
     */
    public Set<E> getDelegate() {
        return this.delegate;
    }

    /**
     * Set the delegating set of this instance of {@link ForwardingSet}.
     * 
     * @param newDelegate
     *            The new set to set.
     */
    public void setDelegate(Set<E> newDelegate) {
        this.delegate = newDelegate;
    }

    /**
     * Re-initializes the delegating set with an empty set.
     */
    void init() {
        this.delegate = new HashSet<E>(0);
    }

    /**
     * Resets the delegating set implementation to an {@link HashSet}.
     */
    void clean() {
        this.delegate = new HashSet<E>(this.delegate);
    }

}
