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
import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * NabuccoCollectionAccessor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoCollectionAccessor {

    /**
     * Singleton instance.
     */
    private static NabuccoCollectionAccessor instance = new NabuccoCollectionAccessor();

    /**
     * Private constructor.
     */
    private NabuccoCollectionAccessor() {
    }

    /**
     * Singleton access.
     * 
     * @return the NabuccoCollectionAccessor instance.
     */
    public static NabuccoCollectionAccessor getInstance() {
        return instance;
    }

    /**
     * Adds an element to the list without adding it to the assigned or removing from the removed
     * list.
     * 
     * @param <E>
     *            the collection element type
     * @param target
     *            the target collection
     * @param source
     *            the source element
     * 
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public <E extends NType> boolean addWithoutAssignment(Collection<E> target, E source) {
        if (target instanceof NabuccoListImpl<?>) {
            return ((NabuccoListImpl<E>) target).addWithoutAssignment(source);
        }
        return target.add(source);
    }

    /**
     * Adds a list to the list without adding it to the assigned or removing from the removed list.
     * 
     * @param <E>
     *            the collection element type
     * @param target
     *            the target collection
     * @param source
     *            the source list
     * 
     * @return <tt>true</tt> (as specified by {@link Collection#addAll})
     */
    public <E extends NType> boolean addAllWithoutAssignment(Collection<E> target, Collection<E> source) {
        if (target instanceof NabuccoListImpl<?>) {
            return ((NabuccoListImpl<E>) target).addAllWithoutAssignment(source);
        }
        return target.addAll(source);
    }

    /**
     * Replaces the old element in all lists (delegate, assigned, unassigned) with the new one.
     * 
     * @param <E>
     *            the collection element type
     * @param target
     *            the target collection
     * @param oldElement
     *            the element to replace
     * @param newElement
     *            the new element
     * 
     * @return <tt>true</tt> if this collection changed as a result of the call
     */
    public <E extends NType> boolean replace(Collection<E> target, E oldElement, E newElement) {
        if (target instanceof NabuccoListImpl<?>) {
            return ((NabuccoListImpl<E>) target).replace(oldElement, newElement);
        }
        return false;
    }

    /**
     * Returns the list of assigned elements.
     * 
     * @param <E>
     *            the collection element type
     * @param collection
     *            the collection containing the assigned list
     * 
     * @return the assigned list or an empty list if the parent list is not of type
     *         {@link NabuccoCollection}
     */
    public <E extends NType> List<E> getAssignedList(Collection<E> collection) {
        if (collection instanceof NabuccoListImpl<?>) {
            return Collections.unmodifiableList(new ArrayList<E>(((NabuccoListImpl<E>) collection).getAssignedList()));
        }
        return Collections.emptyList();
    }

    /**
     * Returns the list of unassigned elements.
     * 
     * @param <E>
     *            the collection element type
     * @param collection
     *            the collection containing the unassigned list
     * 
     * @return the unassigned list or an empty list if the parent list is not of type
     *         {@link NabuccoCollection}
     */
    public <E extends NType> List<E> getUnassignedList(Collection<E> collection) {
        if (collection instanceof NabuccoListImpl<?>) {
            return Collections
                    .unmodifiableList(new ArrayList<E>(((NabuccoListImpl<E>) collection).getUnassignedList()));
        }
        return Collections.emptyList();
    }

    /**
     * Clears the assignment and unassignment lists.
     * 
     * @param <E>
     *            the collection element type
     * @param collection
     *            the collection containing the assignment, unassignment lists
     */
    public <E extends NType> void clearAssignments(Collection<E> collection) {
        if (collection instanceof NabuccoListImpl<?>) {
            NabuccoListImpl<E> nabuccoList = (NabuccoListImpl<E>) collection;
            nabuccoList.getAssignedList().clear();
            nabuccoList.getUnassignedList().clear();
        }
    }

    /**
     * Returns the current state of the collection. Only {@link NabuccoListImpl} instances are
     * considered.
     * 
     * @param list
     *            the list to check
     * 
     * @return return the nabucco collection state or null if list is no {@link NabuccoListImpl}
     */
    public NabuccoCollectionState getCollectionState(Collection<?> list) {
        if (list instanceof NabuccoListImpl<?>) {
            return ((NabuccoListImpl<?>) list).getState();
        }
        return null;
    }

    /**
     * Reloads a collection eagerly.
     * 
     * @param list
     *            the list to load
     */
    public void loadCollection(Collection<?> list) {
        if (list instanceof NabuccoListImpl<?>) {
            ((NabuccoListImpl<?>) list).reload();
        }
    }

    /**
     * Connects a collection to the database.
     * 
     * @param <E>
     *            the collection element type
     * @param collection
     *            the collection to clean
     */
    public <E extends NType> void attachCollection(Collection<E> collection) {
        if (collection instanceof NabuccoListImpl) {
            ((NabuccoListImpl<?>) collection).setManaged(true);
        }
    }

    /**
     * Disconnects a collection from the database. Removes a persistent provider specific lazy
     * initialized list and replaces it by an empty list.
     * 
     * @param <E>
     *            the collection element type
     * @param collection
     *            the collection to clean
     */
    public <E extends NType> void detachCollection(Collection<E> collection) {
        if (collection instanceof NabuccoListImpl) {
            ((NabuccoListImpl<?>) collection).detach();
        }
    }

    /**
     * Disconnects a collection from the database. Removes a persistent provider specific lazy
     * initialized list and replaces it by an empty list.
     * 
     * @param <E>
     *            the collection element type
     * @param collection
     *            the collection to clean
     */
    public <E extends NType> void detachCollection(NabuccoCollection<E> collection) {
        if (collection instanceof NabuccoListImpl) {
            ((NabuccoListImpl<?>) collection).detach();
        }
    }

}
