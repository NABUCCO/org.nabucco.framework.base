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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * NabuccoListImpl
 * <p/>
 * Delegates to a {@link java.util.ArrayList} with an extra {@link NabuccoCollectionState} attribute
 * and assigned and unassigned attribute lists.
 * 
 * @param <E>
 *            the collection element type
 * 
 * @see ArrayList
 * @see ForwardingList
 * @see NabuccoCollectionState
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class NabuccoListImpl<E extends NType> extends ForwardingList<E> implements NabuccoList<E> {

    private static final long serialVersionUID = 1L;

    /** State of the collection. */
    private NabuccoCollectionState state;

    /** The list of elements added by the add() methods. */
    private List<E> assignedList;

    /** The list of elements removed by the remove() methods. */
    private List<E> unassignedList;

    /** Database synchronization status (whether the list is connected or not). */
    private boolean managed;

    /** Variable count of objects that are not (lazy) loaded. */
    private int count;

    /**
     * Creates a new {@link NabuccoListImpl} instance in state
     * {@link NabuccoCollectionState#INITIALIZED}.
     */
    public NabuccoListImpl() {
        this(null);
    }

    /**
     * Creates a new {@link NabuccoListImpl} instance.
     * 
     * @param state
     *            state of the collection
     */
    public NabuccoListImpl(NabuccoCollectionState state) {
        this(null, state);
    }

    /**
     * Creates a new {@link NabuccoListImpl} instance.
     * 
     * @param delegate
     *            the delegating list
     */
    private NabuccoListImpl(List<E> delegate, NabuccoCollectionState state) {
        super(delegate);
        this.initState(state);
    }

    /**
     * Creates a new {@link NabuccoListImpl} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     */
    public NabuccoListImpl(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Creates a new {@link NabuccoListImpl} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     * @param state
     *            state of the collection
     */
    public NabuccoListImpl(int initialCapacity, NabuccoCollectionState state) {
        super(initialCapacity);
        this.initState(state);
    }

    @Override
    public NabuccoCollectionState getState() {
        return this.state;
    }

    @Override
    public NabuccoCollectionType getType() {
        return NabuccoCollectionType.LIST;
    }

    /**
     * Setter for the current collection state.
     * 
     * @param state
     *            the state to set
     */
    public void setState(NabuccoCollectionState state) {
        if (state != null) {
            this.state = state;
        }
    }

    @Override
    public void clearAll() {
        super.clear();
        this.getAssignedList().clear();
        this.getUnassignedList().clear();
    }

    @Override
    public boolean add(E element) {
        this.internalAdd(element);
        return super.add(element);
    }

    @Override
    public void add(int index, E element) {
        this.internalAdd(element);
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        this.internalAddAll(collection);
        return super.addAll(collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        this.internalAddAll(collection);
        return super.addAll(index, collection);
    }

    @Override
    public void clear() {
        this.internalRemoveAll(this);
        super.clear();
    }

    @Override
    public boolean remove(Object object) {
        this.internalRemove(object);
        return super.remove(object);
    }

    @Override
    public E remove(int index) {
        E e = super.remove(index);
        this.internalRemove(e);
        return e;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        this.internalRemoveAll(collection);
        return super.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        this.internalRetainAll(collection);
        return super.retainAll(collection);
    }

    @Override
    public E set(int index, E element) {
        this.internalAdd(element);
        E removedElement = super.set(index, element);
        this.internalRemove(removedElement);
        return removedElement;
    }

    @Override
    public boolean contains(Object o) {
        if (this.state == NabuccoCollectionState.LAZY) {
            return false;
        }
        return super.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (this.state == NabuccoCollectionState.LAZY) {
            return false;
        }
        return super.containsAll(c);
    }

    @Override
    public boolean isEmpty() {
        if (this.state == NabuccoCollectionState.LAZY) {
            return true;
        }
        return super.isEmpty();
    }

    @Override
    public int size() {
        this.checkState(null);
        return super.size();
    }

    @Override
    public int count() {
        if (this.state != NabuccoCollectionState.LAZY) {
            this.setCount(this.size());
        }
        return this.count;
    }

    /**
     * Setter for the count.
     * 
     * @param count
     *            The count to set.
     */
    public void setCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Cannot set count smaller than 0.");
        }
        this.count = count;
    }

    @Override
    public E get(int index) {
        this.checkState(null);
        return super.get(index);
    }

    @Override
    public Iterator<E> iterator() {
        this.checkState(null);
        return super.iterator();
    }

    @Override
    public Object[] toArray() {
        this.checkState(null);
        return super.toArray();
    }

    @Override
    public int indexOf(Object o) {
        this.checkState(o != null ? o.getClass() : null);
        return super.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        this.checkState(o != null ? o.getClass() : null);
        return super.lastIndexOf(o);
    }

    @Override
    public <T extends Object> T[] toArray(T[] a) {
        this.checkState(null);
        return super.toArray(a);
    }

    @Override
    public ListIterator<E> listIterator() {
        this.checkState(null);
        return super.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        this.checkState(null);
        return super.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        this.checkState(null);
        return super.subList(fromIndex, toIndex);
    }

    @Override
    public List<E> getDelegate() {
        return super.getDelegate();
    }

    @Override
    public void setDelegate(List<E> delegate) {

        if (this.state == NabuccoCollectionState.INITIALIZED) {

            // Manually initialized collections are eager loaded after database synchronization!
            this.state = NabuccoCollectionState.EAGER;

            // Collections initialized manually are not managed!
            this.setManaged(false);

        } else {

            // Collections initialized by JPA are always managed!
            this.setManaged(true);
        }

        // Clear assignments after delegate is changed!
        this.getAssignedList().clear();
        this.getUnassignedList().clear();

        super.setDelegate(delegate);
    }

    /**
     * Adds an element to the assigned list or removing it from the unassigned list.
     * 
     * @param element
     *            the element to add
     */
    private void internalAdd(E element) {

        Class<? extends NType> type = element != null ? element.getClass() : null;
        this.checkState(type);

        if (this.getUnassignedList().contains(element)) {
            this.getUnassignedList().remove(element);
        } else {
            this.getAssignedList().add(element);
        }
    }

    /**
     * Adds a collection of elements to the assigned list or removing it from the unassigned list.
     * 
     * @param collection
     *            the collection to add
     */
    private void internalAddAll(Collection<? extends E> collection) {
        if (collection == null) {
            return;
        }
        for (E element : collection) {
            this.internalAdd(element);
        }
    }

    /**
     * Removes an element from the assigned list or adds it to the unassigned list.
     * 
     * @param element
     *            the element to remove
     */
    @SuppressWarnings("unchecked")
    private void internalRemove(Object element) {

        Class<? extends Object> type = element != null ? element.getClass() : null;
        this.checkState(type);

        if (this.getAssignedList().contains(element)) {
            this.getAssignedList().remove(element);
        } else {
            this.getUnassignedList().add((E) element);
        }
    }

    /**
     * Removes a collection of element from the assigned list or adds it to the unassigned list.
     * 
     * @param collection
     *            the collection to remove
     */
    private void internalRemoveAll(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        for (Object object : collection) {
            this.internalRemove(object);
        }
    }

    /**
     * Retains a collection from the assigned list or adds them to the removed list.
     * 
     * @param collection
     *            the collection to retain
     */
    private void internalRetainAll(Collection<?> collection) {
        if (collection == null) {
            return;
        }
        for (Object object : this) {
            if (!collection.contains(object)) {
                this.internalRemove(object);
            }
        }
    }

    /**
     * Initializes the state of the NABUCCO list. For null value the default INITIALIZED is set.
     * 
     * @param state
     *            the state of the collection
     */
    private void initState(NabuccoCollectionState state) {
        if (state == null) {
            state = NabuccoCollectionState.INITIALIZED;
        }
        this.state = state;

        if (this.state == NabuccoCollectionState.INITIALIZED) {
            // Collections initialized manually are not managed!
            this.setManaged(false);
        } else {
            // Collections initialized by JPA are always managed!
            this.setManaged(true);
        }
    }

    /**
     * Checks whether a collection can be modified or not.
     * 
     * @param type
     *            type of the collection elements
     * 
     * @throws LazyInitializationException
     *             if the collection state is LAZY.
     */
    private void checkState(Class<?> type) throws LazyInitializationException {
        if (!this.managed && this.state == NabuccoCollectionState.LAZY) {
            if (type == null) {
                throw new LazyInitializationException();
            }
            throw new LazyInitializationException(type);
        }

        this.setState(NabuccoCollectionState.EAGER);
    }

    /**
     * Adds an element to the list without adding it to the assigned or removing from the removed
     * list.
     * 
     * @param element
     *            the element to add
     * 
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    boolean addWithoutAssignment(E element) {
        return super.add(element);
    }

    /**
     * Adds a list to the list without adding it to the assigned or removing from the removed list.
     * 
     * @param list
     *            the list to add
     * 
     * @return <tt>true</tt> (as specified by {@link Collection#addAll})
     */
    boolean addAllWithoutAssignment(Collection<E> list) {
        return super.addAll(list);
    }

    /**
     * Replaces the old element in all lists (delegate, assigned, unassigned) with the new one.
     * 
     * @param oldElement
     *            the element to replace
     * @param newElement
     *            the new element
     * 
     * @return <tt>true</tt> if this collection changed as a result of the call
     */
    boolean replace(E oldElement, E newElement) {
        if (oldElement == null) {
            throw new IllegalArgumentException("Cannot replace old element [null].");
        }
        if (newElement == null) {
            throw new IllegalArgumentException("Cannot replace new element [null].");
        }

        boolean change = false;

        for (int index = 0; index < super.size(); index++) {
            if (this.get(index).equals(oldElement)) {
                super.set(index, newElement);
                change = true;
            }
        }
        for (int index = 0; index < this.getAssignedList().size(); index++) {
            if (this.getAssignedList().get(index).equals(oldElement)) {
                this.getAssignedList().set(index, newElement);
                change = true;
            }
        }
        for (int index = 0; index < this.getUnassignedList().size(); index++) {
            if (this.getUnassignedList().get(index).equals(oldElement)) {
                this.getUnassignedList().set(index, newElement);
                change = true;
            }
        }

        return change;
    }

    @Override
    public boolean isManaged() {
        return this.managed;
    }

    @Override
    public boolean isTraversable() {
        if (this.getState() != NabuccoCollectionState.LAZY) {
            return true;
        }
        if (this.isManaged()) {
            return true;
        }
        return false;
    }

    /**
     * Getter for the list of assigned elements.
     * 
     * @return the assigned list
     */
    List<E> getAssignedList() {
        if (this.assignedList == null) {
            this.assignedList = new ArrayList<E>();
        }
        return this.assignedList;
    }

    /**
     * Getter for the list of unassigned elements.
     * 
     * @return the unassigned list
     */
    List<E> getUnassignedList() {
        if (this.unassignedList == null) {
            this.unassignedList = new ArrayList<E>();
        }
        return this.unassignedList;
    }

    /**
     * Loads a list eager by creating a single select for each element (N+1 Problem). Clients are
     * not affected by this method.
     */
    void reload() {
        if (this.state != NabuccoCollectionState.EAGER) {
            super.clean();
            this.state = NabuccoCollectionState.EAGER;
        }
    }

    /**
     * Replaces the lazy initialized collection implementation by empty lists. If the collection is
     * not LAZY, nothing changes.
     */
    void detach() {
        if (this.state == NabuccoCollectionState.LAZY) {
            super.init();
        } else {
            super.clean();
        }
        this.setManaged(false);
    }

    /**
     * Setter for the collection database connection status. Whether the collection is managed by
     * the entity manager (connected) or not.
     * 
     * @param managed
     *            the connection status
     */
    void setManaged(boolean managed) {
        this.managed = managed;
    }

    @Override
    public String toString() {
        if (this.state == NabuccoCollectionState.LAZY) {
            return NabuccoCollectionState.LAZY.name();
        }
        return super.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NabuccoListImpl<?>)) {
            return false;
        }
        NabuccoListImpl<?> other = (NabuccoListImpl<?>) obj;
        if (this.state == null) {
            if (other.state != null) {
                return false;
            }
        } else if (!this.state.equals(other.state)) {
            return false;
        }
        if (this.state == NabuccoCollectionState.LAZY) {
            return true;
        }
        return super.equals(other);
    }

    @Override
    public NabuccoList<E> cloneCollection() {
        NabuccoListImpl<E> clone = new NabuccoListImpl<E>();

        if (this.state == NabuccoCollectionState.LAZY) {
            clone.state = NabuccoCollectionState.LAZY;
            return clone;
        }

        for (E element : this) {
            @SuppressWarnings("unchecked")
            E cloneElement = (E) element.cloneObject();
            NabuccoCollectionAccessor.getInstance().addWithoutAssignment(clone, cloneElement);
        }
        clone.state = this.state;

        return clone;
    }

    @Override
    public E first() {
        if (this.isEmpty()) {
            return null;
        }
        return this.get(0);
    }

    @Override
    public E last() {
        if (this.isEmpty()) {
            return null;
        }
        return this.get(this.size() - 1);
    }

    @Override
    public List<E> getAssignedElements() {
        return Collections.unmodifiableList(this.getAssignedList());
    }

    @Override
    public List<E> getUnassignedElements() {
        return Collections.unmodifiableList(this.getUnassignedList());
    }

    @Override
    public NType cloneObject() {
        return this.cloneCollection();
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        this.checkState(null);
        for (E element : this) {
            visitor.visit(element);
        }
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        this.checkState(null);
        Set<NabuccoProperty> properties = new LinkedHashSet<NabuccoProperty>();
        for (E entry : this) {
            properties.addAll((entry).getProperties());
        }
        return properties;
    }

    @Override
    public NabuccoList<E> filter(NabuccoCollectionFilter<E> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Cannot apply filter 'null'.");
        }

        this.checkState(null);

        List<E> filteredList = new ArrayList<E>();

        for (E element : this) {
            if (filter.filter(element)) {
                filteredList.add(element);
            }
        }

        return new NabuccoListImpl<E>(filteredList, NabuccoCollectionState.INITIALIZED);
    }
}
