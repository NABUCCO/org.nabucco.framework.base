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
import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * NabuccoList
 * <p/>
 * Delegates to a {@link java.util.ArrayList} with an extra {@link NabuccoCollectionState} attribute
 * and assigned and unassigned attribute lists.
 * 
 * @see ArrayList
 * @see ForwardingList
 * @see NabuccoCollectionState
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class NabuccoList<E extends NType> extends ForwardingList<E> implements
        NabuccoCollection<E> {

    private static final long serialVersionUID = 1L;

    private NabuccoCollectionState state;

    private List<E> assignedList;

    private List<E> unassignedList;

    private boolean managed;

    /**
     * Creates a new {@link NabuccoList} instance with {@link NabuccoCollectionState#INITIALIZED}.
     */
    public NabuccoList() {
        super();
        this.initState(null);
    }

    /**
     * Creates a new {@link NabuccoList} instance.
     * 
     * @param state
     *            state of the collection
     */
    public NabuccoList(NabuccoCollectionState state) {
        super();
        this.initState(state);
    }

    /**
     * Creates a new {@link NabuccoList} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     */
    public NabuccoList(int initialCapacity) {
        super(initialCapacity);
        this.initState(null);
    }

    /**
     * Creates a new {@link NabuccoList} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     * @param state
     *            state of the collection
     */
    public NabuccoList(int initialCapacity, NabuccoCollectionState state) {
        super(initialCapacity);
        this.initState(state);
    }

    /**
     * Creates a new {@link NabuccoList} based on an existing list.
     * 
     * @param list
     *            the existing list, a delegate is created for the existing list
     * @param state
     *            state of the collection
     */
    public NabuccoList(List<E> list, NabuccoCollectionState state) {
        super(list);
        this.initState(state);
        if (state != NabuccoCollectionState.LAZY) {
            super.clean();
        } else {
            this.setManaged(true);
        }
    }

    @Override
    public NabuccoCollectionState getState() {
        return state;
    }

    @Override
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
    public boolean isEmpty() {
        if (this.state == NabuccoCollectionState.LAZY) {
            return true;
        }
        return super.isEmpty();
    }

    /**
     * Adds an element to the assigned list or removing it from the unassigned list.
     * 
     * @param element
     *            the element to add
     */
    private void internalAdd(E element) {
        this.checkState(element.getClass());

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
        for (E element : collection) {
            internalAdd(element);
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
        this.checkState(element.getClass());
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
            throw new LazyInitializationException(type);
        }
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
     * @param element
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
        for (int index = 0; index < this.assignedList.size(); index++) {
            if (this.assignedList.get(index).equals(oldElement)) {
                this.assignedList.set(index, newElement);
                change = true;
            }
        }
        for (int index = 0; index < this.unassignedList.size(); index++) {
            if (this.unassignedList.get(index).equals(oldElement)) {
                this.unassignedList.set(index, newElement);
                change = true;
            }
        }

        return change;
    }

    @Override
    public boolean isManaged() {
        return this.managed;
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
    void removeLazyCollection() {
        if (this.state == NabuccoCollectionState.LAZY) {
            super.init();
        }
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
        if (!(obj instanceof NabuccoList<?>)) {
            return false;
        }
        NabuccoList<?> other = (NabuccoList<?>) obj;
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
        NabuccoList<E> clone = new NabuccoList<E>();
        clone.state = this.state;

        if (this.state == NabuccoCollectionState.LAZY) {
            return clone;
        }

        for (E element : this) {
            @SuppressWarnings("unchecked")
            E cloneElement = (E) element.cloneObject();
            NabuccoCollectionAccessor.getInstance().addWithoutAssignment(clone, cloneElement);
        }

        return clone;
    }
}
