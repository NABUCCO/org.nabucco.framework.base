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
package org.nabucco.framework.base.ui.web.component.work;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * The Stack of currently open work items.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class WorkItemStack {

    /** The Stack */
    private Deque<WorkItemStackEntry> stack = new ArrayDeque<WorkItemStackEntry>();

    /**
     * Retrieves, but does not remove, the head of the stack. In other words, the first element of
     * this stack, or returns <tt>null</tt> if this stack is empty.
     * 
     * @return the head of the stack, or <tt>null</tt> if this stack is empty
     * 
     * @see java.util.Deque#peek()
     */
    public WorkItemStackEntry peek() {
        return this.stack.peek();
    }

    /**
     * Pushes a new created {@link WorkItemStackEntry} element onto the head of this stack. If the
     * element is already on the stack it is removed from its current position and pushed onto the
     * head of this stack.
     * 
     * @param itemId
     *            the element item ID
     * @param instanceId
     *            the element instance ID
     * @throws IllegalStateException
     *             if the element cannot be added at this time due to capacity restrictions
     * @throws ClassCastException
     *             if the class of the specified element prevents it from being added to this stack
     * @throws IllegalArgumentException
     *             if the itemId or instanceId is <tt>null</tt>
     * 
     * @see java.util.Deque#push(java.lang.Object)
     */
    public void push(String itemId, String instanceId) {
        this.push(new WorkItemStackEntry(itemId, instanceId));
    }

    /**
     * Pushes an existing {@link WorkItemStackEntry} element onto the head of this stack. If the
     * element is already on the stack it is removed from its current position and pushed onto the
     * head of this stack.
     * 
     * @param e
     *            the element to push
     * @throws IllegalStateException
     *             if the element cannot be added at this time due to capacity restrictions
     * @throws ClassCastException
     *             if the class of the specified element prevents it from being added to this stack
     * @throws NullPointerException
     *             if the specified element is null
     * @throws IllegalArgumentException
     *             if some property of the specified element prevents it from being added to this
     *             stack
     * 
     * @see java.util.Deque#push(java.lang.Object)
     */
    public void push(WorkItemStackEntry e) {
        if (this.stack.contains(e)) {
            this.stack.remove(e);
        }
        this.stack.push(e);
    }

    /**
     * Pops an element from the stack. In other words, removes and returns the first element of this
     * stack.
     * 
     * @return the top of the stack
     * 
     * @throws NoSuchElementException
     *             if this stack is empty
     * 
     * @see java.util.Deque#pop()
     */
    public WorkItemStackEntry pop() {
        return this.stack.pop();
    }

    /**
     * Returns true if this stack contains the specified element with item and instance id.
     * 
     * @param itemId
     *            the elements item id
     * @param instanceId
     *            the elements instance id
     * 
     * @return <tt>true</b> if the stack contains an element with the given ids, <tt>false</tt> if
     *         not
     */
    public boolean contains(String itemId, String instanceId) {
        return this.contains(new WorkItemStackEntry(itemId, instanceId));
    }

    /**
     * Returns true if this stack contains the specified entry. More formally, returns true if and
     * only if this stack contains at least one entry e such that (o==null ? e==null : o.equals(e)).
     * 
     * @param entry
     *            the stack entry to check
     * 
     * @return <tt>true</b> if the stack contains an element with the given ids, <tt>false</tt> if
     *         not
     */
    public boolean contains(WorkItemStackEntry entry) {
        return this.stack.contains(entry);
    }

    /**
     * Returns the element of this stack with the given item and instance id.
     * 
     * @param itemId
     *            the elements item id
     * @param instanceId
     *            the elements instance id
     * 
     * @return the stack entry if the stack contains an element with the given ids, or null if the
     *         stack does not contain the specified element
     */
    public WorkItemStackEntry get(String itemId, String instanceId) {
        for (WorkItemStackEntry entry : this.stack) {
            if (entry.getItemId().equals(itemId) && entry.getInstanceId().equals(instanceId)) {
                return entry;
            }
        }

        return null;
    }

    /**
     * Resolve the index of the work item in the stack.
     * 
     * @param item
     *            the work item to find
     * 
     * @return the index of the given work item in the stack.
     */
    public int indexOf(WorkItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot retrieve index of work item 'null'.");
        }

        int index = 0;
        for (WorkItemStackEntry entry : this.stack) {
            if (item.getInstanceId().equals(entry.getInstanceId()) && item.getId().equals(entry.getItemId())) {
                return index;
            }

            index++;
        }

        return -1;
    }

    /**
     * Removes the entry with the given item and instance id from this stack. If the stack does not
     * contain the element, it is unchanged.
     * 
     * @param itemId
     *            the elements item id
     * @param instanceId
     *            the elements instance id
     * 
     * @return <tt>true</tt> if an element was removed as a result of this call
     */
    public boolean remove(String itemId, String instanceId) {
        if (itemId == null || instanceId == null) {
            return false;
        }
        return this.remove(new WorkItemStackEntry(itemId, instanceId));
    }

    /**
     * Removes the specified element from this stack. If the stack does not contain the element, it
     * is unchanged.
     * 
     * @param entry
     *            element to be removed from this stack, if present
     * 
     * @return <tt>true</tt> if an element was removed as a result of this call
     */
    public boolean remove(WorkItemStackEntry entry) {
        if (entry == null) {
            return false;
        }
        return this.stack.remove(entry);
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

}
