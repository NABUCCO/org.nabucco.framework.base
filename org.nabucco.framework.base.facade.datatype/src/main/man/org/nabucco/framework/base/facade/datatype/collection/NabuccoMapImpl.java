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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * NabuccoMapImpl
 * 
 * @param <E>
 *            the collection element type
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoMapImpl<E extends NType> extends ForwardingMap<E> implements NabuccoMap<E> {

    private static final long serialVersionUID = 1L;

    /** State of the collection. */
    private NabuccoCollectionState state;

    /** The map of elements added by the add() methods. */
    private Map<String, E> assignedMap;

    /** The map of elements removed by the remove() methods. */
    private Map<String, E> unassignedMap;

    /** Database synchronization status (whether the map is connected or not). */
    private boolean managed;

    /** Variable count of objects that are not (lazy) loaded. */
    private int count;

    /**
     * Creates a new {@link NabuccoMapImpl} instance in state
     * {@link NabuccoCollectionState#INITIALIZED}.
     */
    public NabuccoMapImpl() {
        this(null);
    }

    /**
     * Creates a new {@link NabuccoMapImpl} instance.
     * 
     * @param state
     *            state of the collection
     */
    public NabuccoMapImpl(NabuccoCollectionState state) {
        this(null, state);
    }

    /**
     * Creates a new {@link NabuccoMapImpl} instance.
     * 
     * @param delegate
     *            the delegating map
     * @param state
     *            state of the collection
     */
    private NabuccoMapImpl(Map<String, E> delegate, NabuccoCollectionState state) {
        super(delegate);
        this.setState(state);
    }

    /**
     * Creates a new {@link NabuccoMapImpl} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     */
    public NabuccoMapImpl(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Creates a new {@link NabuccoMapImpl} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     * @param state
     *            state of the collection
     */
    public NabuccoMapImpl(int initialCapacity, NabuccoCollectionState state) {
        super(initialCapacity);
        this.initState(state);
    }

    @Override
    public NabuccoCollectionState getState() {
        return this.state;
    }

    @Override
    public NabuccoCollectionType getType() {
        return NabuccoCollectionType.MAP;
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
        this.getAssignedMap().clear();
        this.getUnassignedMap().clear();
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
    public boolean containsKey(Object key) {
        if (this.state == NabuccoCollectionState.LAZY) {
            return false;
        }
        return super.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (this.state == NabuccoCollectionState.LAZY) {
            return false;
        }
        return super.containsValue(value);
    }

    @Override
    public boolean contains(Object value) {
        if (this.state == NabuccoCollectionState.LAZY) {
            return false;
        }
        return super.containsValue(value);
    }

    @Override
    public E get(Object key) {
        this.checkState(null);
        return super.get(key);
    }

    @Override
    public E put(String key, E value) {
        this.internalPut(key, value);
        return super.put(key, value);
    }

    @Override
    public E remove(Object key) {
        E oldElement = super.remove(key);
        this.internalRemove(key, oldElement);
        return oldElement;
    }

    @Override
    public void putAll(Map<? extends String, ? extends E> map) {
        this.internalPutAll(map);
        super.putAll(map);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public Set<String> keySet() {
        this.checkState(null);
        return super.keySet();
    }

    @Override
    public Collection<E> values() {
        this.checkState(null);
        return super.values();
    }

    @Override
    public Set<java.util.Map.Entry<String, E>> entrySet() {
        this.checkState(null);
        return super.entrySet();
    }

    @Override
    public Iterator<E> iterator() {
        this.checkState(null);
        return super.values().iterator();
    }

    @Override
    public void setDelegate(Map<String, E> delegate) {

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
        this.getAssignedMap().clear();
        this.getUnassignedMap().clear();

        super.setDelegate(delegate);
    }

    /**
     * Initializes the state of the NABUCCO map. For null value the default INITIALIZED is set.
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
     * Adds an element to the assigned map or removing it from the unassigned map.
     * 
     * @param key
     *            the element key
     * @param value
     *            the element to put
     */
    private void internalPut(String key, E value) {

        Class<? extends NType> type = value != null ? value.getClass() : null;
        this.checkState(type);

        if (this.getUnassignedMap().containsKey(key)) {
            this.getUnassignedMap().remove(key);
        } else {
            this.getAssignedMap().put(key, value);
        }
    }

    /**
     * Adds a collection of elements to the assigned list or removing it from the unassigned list.
     * 
     * @param map
     *            the map to add
     */
    private void internalPutAll(Map<? extends String, ? extends E> map) {
        if (map == null) {
            return;
        }
        for (Map.Entry<? extends String, ? extends E> element : map.entrySet()) {
            this.internalPut(element.getKey(), element.getValue());
        }
    }

    /**
     * Adds an element to the unassigned map or removing it from the assigned map.
     * 
     * @param key
     *            the element key
     * @param value
     *            the element to put
     */
    private void internalRemove(Object key, E value) {

        Class<? extends NType> type = value != null ? value.getClass() : null;
        this.checkState(type);

        if (this.getAssignedMap().containsKey(key)) {
            this.getAssignedMap().remove(key);
        } else {
            this.getUnassignedMap().put((String) key, value);
        }
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
     * Getter for the map of assigned elements.
     * 
     * @return the assigned map
     */
    Map<String, E> getAssignedMap() {
        if (this.assignedMap == null) {
            this.assignedMap = new HashMap<String, E>();
        }
        return this.assignedMap;
    }

    /**
     * Getter for the map of unassigned elements.
     * 
     * @return the unassigned map
     */
    Map<String, E> getUnassignedMap() {
        if (this.unassignedMap == null) {
            this.unassignedMap = new HashMap<String, E>();
        }
        return this.unassignedMap;
    }

    /**
     * Loads a map eager by creating a single select for each element (N+1 Problem). Clients are not
     * affected by this method.
     */
    void reload() {
        if (this.state != NabuccoCollectionState.EAGER) {
            super.clean();
            this.state = NabuccoCollectionState.EAGER;
        }
    }

    /**
     * Replaces the lazy initialized collection implementation by empty maps. If the collection is
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
        if (!(obj instanceof NabuccoMapImpl<?>)) {
            return false;
        }
        NabuccoMapImpl<?> other = (NabuccoMapImpl<?>) obj;
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
    public NabuccoMap<E> cloneCollection() {
        NabuccoMapImpl<E> clone = new NabuccoMapImpl<E>();

        if (this.state == NabuccoCollectionState.LAZY) {
            clone.state = NabuccoCollectionState.LAZY;
            return clone;
        }

        clone.state = this.state;

        return clone;
    }

    @Override
    public List<E> getAssignedElements() {
        return Collections.unmodifiableList(new ArrayList<E>(this.getAssignedMap().values()));
    }

    @Override
    public List<E> getUnassignedElements() {
        return Collections.unmodifiableList(new ArrayList<E>(this.getUnassignedMap().values()));
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
    public NabuccoMap<E> filter(NabuccoCollectionFilter<E> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Cannot apply filter 'null'.");
        }

        this.checkState(null);

        Map<String, E> filteredMap = new HashMap<String, E>();
        for (Entry<String, E> entry : this.entrySet()) {
            if (filter.filter(entry.getValue())) {
                filteredMap.put(entry.getKey(), entry.getValue());
            }
        }

        return new NabuccoMapImpl<E>(filteredMap, NabuccoCollectionState.INITIALIZED);
    }

}
