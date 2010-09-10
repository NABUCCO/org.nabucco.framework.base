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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * ForwardingList
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class ForwardingList<E extends NType> implements List<E>, NabuccoCollection<E> {

    private static final long serialVersionUID = 1L;

    private List<E> delegate;

    /**
     * Creates a new forwarding list delegating to an empty list.
     */
    public ForwardingList() {
        this.delegate = new ArrayList<E>();
    }

    /**
     * Creates a new forwarding list delegating to an empty list with the given initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity of the delegating list
     */
    public ForwardingList(int initialCapacity) {
        this.delegate = new ArrayList<E>(initialCapacity);
    }

    /**
     * Creates a new forwarding list delegating to the given list.
     * 
     * @param list
     *            the list to delegate to
     */
    public ForwardingList(List<E> list) {
        if (list == null) {
            this.delegate = new ArrayList<E>();
        } else {
            this.delegate = list;
        }
    }

    @Override
    public boolean add(E e) {
        return this.delegate.add(e);
    }

    @Override
    public void add(int index, E element) {
        this.delegate.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.delegate.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return this.delegate.addAll(index, c);
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.delegate.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.delegate.containsAll(c);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof List<?>)) {
            return false;
        }
        if (this.size() != ((List<?>) o).size()) {
            return false;
        }

        return this.delegate.equals(o);
    }

    @Override
    public E get(int index) {
        return this.delegate.get(index);
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public int indexOf(Object o) {
        return this.delegate.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return this.delegate.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.delegate.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.delegate.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return this.delegate.listIterator(index);
    }

    @Override
    public E remove(int index) {
        return this.delegate.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return this.delegate.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.delegate.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.delegate.retainAll(c);
    }

    @Override
    public E set(int index, E element) {
        return this.delegate.set(index, element);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return this.delegate.subList(fromIndex, toIndex);
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
    public String toString() {
        return this.delegate.toString();
    }

    /**
     * Re-initializes the delegating list with an empty list.
     */
    void init() {
        this.delegate = new ArrayList<E>(0);
    }

    /**
     * Resets the delegating list implementation to an {@link ArrayList}.
     */
    void clean() {
        this.delegate = new ArrayList<E>(this.delegate);
    }
}
