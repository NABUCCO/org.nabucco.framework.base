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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * NabuccoSet
 * <p/>
 * Delegates to a {@link HashSet} with an extra {@link NabuccoCollectionState} attribute and
 * assigned and unassigned attribute Sets.
 * 
 * @see HashSet
 * @see ForwardingSet
 * @see NabuccoCollectionState
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoSet<E extends NType> extends ForwardingSet<E> implements NabuccoCollection<E> {

    private static final long serialVersionUID = 1L;

    /** State of the collection. */
    private NabuccoCollectionState state;

    /** The list of elements added by the add() methods. */
    private List<E> assignedList;

    /** The list of elements removed by the remove() methods. */
    private List<E> unassignedList;

    /** Database synchronization status (whether the set is connected or not). */
    private boolean managed;

    /**
     * Creates a new {@link NabuccoSet} instance with {@link NabuccoCollectionState#INITIALIZED}.
     */
    public NabuccoSet() {
        super();
        this.initState(null);
    }

    /**
     * Creates a new {@link NabuccoSet} instance.
     * 
     * @param state
     *            state of the collection
     */
    public NabuccoSet(NabuccoCollectionState state) {
        super();
        this.initState(state);
    }

    /**
     * Creates a new {@link NabuccoSet} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     */
    public NabuccoSet(int initialCapacity) {
        super(initialCapacity);
        this.initState(null);
    }

    /**
     * Creates a new {@link NabuccoSet} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     * @param state
     *            state of the collection
     */
    public NabuccoSet(int initialCapacity, NabuccoCollectionState state) {
        super(initialCapacity);
        this.initState(state);
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
    public boolean addAll(Collection<? extends E> collection) {
        this.internalAddAll(collection);
        return super.addAll(collection);
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
    public <T> T[] toArray(T[] a) {
        this.checkState(null);
        return super.toArray(a);
    }

    /**
     * Adds an element to the assigned set or removing it from the unassigned set.
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
     * Adds a collection of elements to the assigned set or removing it from the unassigned set.
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
     * Removes an element from the assigned set or adds it to the unassigned set.
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
     * Removes a collection of element from the assigned set or adds it to the unassigned set.
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
     * Retains a collection from the assigned set or adds them to the removed set.
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
     * Initializes the state of the NABUCCO set. For null value the default INITIALIZED is set.
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
     * Adds an element to the set without adding it to the assigned or removing from the removed
     * set.
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
     * Adds a set to the set without adding it to the assigned or removing from the removed set.
     * 
     * @param set
     *            the set to add
     * 
     * @return <tt>true</tt> (as specified by {@link Collection#addAll})
     */
    boolean addAllWithoutAssignment(Collection<E> set) {
        return super.addAll(set);
    }

    @Override
    public boolean isManaged() {
        return this.managed;
    }

    /**
     * Getter for the set of assigned elements.
     * 
     * @return the assigned set
     */
    List<E> getAssignedList() {
        if (this.assignedList == null) {
            this.assignedList = new ArrayList<E>();
        }
        return this.assignedList;
    }

    /**
     * Getter for the set of unassigned elements.
     * 
     * @return the unassigned set
     */
    List<E> getUnassignedList() {
        if (this.unassignedList == null) {
            this.unassignedList = new ArrayList<E>();
        }
        return this.unassignedList;
    }

    /**
     * Loads a set eager by creating a single select for each element (N+1 Problem). Clients are
     * not affected by this method.
     */
    void reload() {
        if (this.state != NabuccoCollectionState.EAGER) {
            super.clean();
            this.state = NabuccoCollectionState.EAGER;
        }
    }

    /**
     * Replaces the lazy initialized collection implementation by empty sets. If the collection is
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
        if (!(obj instanceof NabuccoSet<?>)) {
            return false;
        }
        NabuccoSet<?> other = (NabuccoSet<?>) obj;
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
    public NabuccoSet<E> cloneCollection() {
        NabuccoSet<E> clone = new NabuccoSet<E>();
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
