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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * NabuccoSetImpl
 * <p/>
 * Delegates to a {@link HashSet} with an extra {@link NabuccoCollectionState} attribute and
 * assigned and unassigned attribute Sets.
 * 
 * @param <E>
 *            the set element type
 * 
 * @see HashSet
 * @see ForwardingSet
 * @see NabuccoCollectionState
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoSetImpl<E extends NType> extends ForwardingSet<E> implements NabuccoCollection<E>, NabuccoSet<E> {

    private static final long serialVersionUID = 1L;

    /** State of the collection. */
    private NabuccoCollectionState state;

    /** The list of elements added by the add() methods. */
    private List<E> assignedList;

    /** The list of elements removed by the remove() methods. */
    private List<E> unassignedList;

    /** Database synchronization status (whether the set is connected or not). */
    private boolean managed;

    /** Variable count of objects that are not (lazy) loaded. */
    private int count;

    /**
     * Creates a new {@link NabuccoSetImpl} instance in state
     * {@link NabuccoCollectionState#INITIALIZED}.
     */
    public NabuccoSetImpl() {
        this(null);
    }

    /**
     * Creates a new {@link NabuccoSetImpl} instance.
     * 
     * @param state
     *            state of the set
     */
    public NabuccoSetImpl(NabuccoCollectionState state) {
        this(null, state);
    }

    /**
     * Creates a new {@link NabuccoSetImpl} instance.
     * 
     * @param delegate
     *            the delegating set
     * @param state
     *            state of the set
     */
    private NabuccoSetImpl(Set<E> delegate, NabuccoCollectionState state) {
        super(delegate);
        this.initState(state);
    }

    /**
     * Creates a new {@link NabuccoSetImpl} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     */
    public NabuccoSetImpl(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Creates a new {@link NabuccoSetImpl} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     * @param state
     *            state of the collection
     */
    public NabuccoSetImpl(int initialCapacity, NabuccoCollectionState state) {
        super(initialCapacity);
        this.initState(state);
    }

    @Override
    public NabuccoCollectionState getState() {
        return state;
    }

    @Override
    public NabuccoCollectionType getType() {
        return NabuccoCollectionType.SET;
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

    @Override
    public Set<E> getDelegate() {
        return super.getDelegate();
    }

    @Override
    public void setDelegate(Set<E> delegate) {

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
     * Adds an element to the assigned set or removing it from the unassigned set.
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
     * Adds a collection of elements to the assigned set or removing it from the unassigned set.
     * 
     * @param collection
     *            the collection to add
     */
    private void internalAddAll(Collection<? extends E> collection) {
        if (collection == null) {
            return;
        }
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

        Class<? extends Object> type = element != null ? element.getClass() : null;
        this.checkState(type);

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
        if (collection == null) {
            return;
        }
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
     * Loads a set eager by creating a single select for each element (N+1 Problem). Clients are not
     * affected by this method.
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
        if (!(obj instanceof NabuccoSetImpl<?>)) {
            return false;
        }
        NabuccoSetImpl<?> other = (NabuccoSetImpl<?>) obj;
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
        NabuccoSetImpl<E> clone = new NabuccoSetImpl<E>();
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
    public NabuccoSet<E> filter(NabuccoCollectionFilter<E> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Cannot apply filter 'null'.");
        }

        this.checkState(null);

        Set<E> filteredSet = new HashSet<E>();

        for (E element : this) {
            if (filter.filter(element)) {
                filteredSet.add(element);
            }
        }

        return new NabuccoSetImpl<E>(filteredSet, NabuccoCollectionState.INITIALIZED);
    }
}
