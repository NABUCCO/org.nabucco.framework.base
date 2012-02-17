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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * NabuccoCollection
 * <p/>
 * Common interface for collections in the NABUCCO Framework. Holds the state whether the current
 * collection is loaded or not.
 * 
 * @param <E>
 *            the collection element type
 * 
 * @see NabuccoList
 * @see NabuccoSet
 * @see NabuccoMap
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoCollection<E extends NType> extends Iterable<E>, NType {

    /**
     * Getter for the current collection state. A new created collection is always in state
     * INITIALIZED, when the owning datatype is persistet the list is, depending on the fetch
     * strategy, either EAGER or LAZY.
     * 
     * @return the current state of the collection
     */
    NabuccoCollectionState getState();

    /**
     * Getter for the type of the collection implementation. Depending on the order type the type
     * can be <b>LIST</b> for ordered collections, <b>SET</b> for unordered collections or
     * <b>MAP</b> for mapped collections.
     * 
     * @return the collection implementation type
     */
    NabuccoCollectionType getType();

    /**
     * Getter for the list of assigned elements. Each element that is added to this collection is
     * automatically added to the assigned list.
     * 
     * @return an unmodifyable list of assigned elements
     */
    List<E> getAssignedElements();

    /**
     * Getter for the unassigned list. Each element that is removed from this collection is
     * automatically added to the unassigned list.
     * 
     * @return an unmodifyable list of unassigned elements
     */
    List<E> getUnassignedElements();

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * 
     * @param element
     *            the element to check
     * 
     * @return <b>true</b> if the collection contains the element, <b>false</b> if not.
     */
    boolean contains(Object element);

    /**
     * Indicates whether a {@link NabuccoCollection} is currently connected to the DB or not. A
     * managed collection can be eagerly loaded by iteration. When a non-managed collection in state
     * {@link NabuccoCollectionState#LAZY} is accessed a {@link LazyInitializationException} is
     * raised.
     * <p/>
     * <b>Attention:</b> A new select satement is executed when iterating over a lazy loaded but
     * managed collection.
     * 
     * @return <b>true</b> if the collection is managed, <b>false</b> if not.
     */
    boolean isManaged();

    /**
     * Indicates whether a {@link NabuccoCollection} is traversable (iterable). This is either
     * possible if the collection is not LAZY or managed (connected to the DB).
     * 
     * @see NabuccoCollection#getState()
     * @see NabuccoCollection#isManaged()
     * 
     * @return <b>true</b> when the collection is traversable, <b>false</b> if not
     */
    boolean isTraversable();

    /**
     * Returns <tt>true</tt> if this collection contains no elements. Lazy collections always return
     * <b>true</b>.
     * 
     * @return <tt>true</tt> if this collection contains no elements
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in this collection. If this collection contains more than
     * <tt>Integer.MAX_VALUE</tt> elements, returns <tt>Integer.MAX_VALUE</tt>.
     * 
     * @return the number of elements in this collection
     * 
     * @throws LazyInitializationException
     *             when the collection is in state {@link NabuccoCollectionState#LAZY} and not
     *             managed
     */
    int size();

    /**
     * When the collection is <b>eager</b> loaded it returns the number of elements in this
     * collection. If this collection contains more than <tt>Integer.MAX_VALUE</tt> elements,
     * returns <tt>Integer.MAX_VALUE</tt>.
     * 
     * <p/>
     * 
     * When the collection is <b>lazy</b> loaded it returns the counted number of elements. When the
     * collection count is not calculated a -1 is returned.
     * 
     * @return the number of elements in this collection
     */
    int count();

    /**
     * Removes all of the elements from this collection. The collection will be empty after this
     * method returns.
     * <p/>
     * Internal assignment and unassignment lists are not cleared.
     */
    void clear();

    /**
     * Removes all of the elements from this collection. The collection will be empty after this
     * method returns.
     * <p/>
     * Also the internal assignment and unassignment lists are cleared.
     */
    void clearAll();

    /**
     * Clones all elements of the collection deeply.
     * 
     * @return the cloned collection
     */
    NabuccoCollection<E> cloneCollection();

    /**
     * Filter the collection by the given {@link NabuccoCollectionFilter} implementation. Each
     * element of the list is forwarded to the filter for evaluation whether it is part of the list
     * or not. The result is returned in a new list instance, the original list is not touched at
     * all.
     * 
     * @param filter
     *            the filter to filter the collection
     * 
     * @return return a new list instance holding the filtered elements, the original list is not
     *         touched at all
     */
    NabuccoCollection<E> filter(NabuccoCollectionFilter<E> filter);

}
