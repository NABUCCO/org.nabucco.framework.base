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
package org.nabucco.framework.base.facade.datatype.exceptionmsg.field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * FieldMessageSet
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class FieldMessageSet implements Serializable {

    private static final long serialVersionUID = 1L;

    private Set<FieldMessage> fieldMessageSet;

    /**
     * Creates a new {@link FieldMessageSet} instance.
     */
    public FieldMessageSet() {
        this.fieldMessageSet = new HashSet<FieldMessage>();
    }

    /**
     * Returns the size of the set.
     * 
     * @return the amount of field messages
     */
    public int size() {
        return this.fieldMessageSet.size();
    }

    /**
     * Returns <b>true</b> if this set contains no elements.
     * 
     * @return <b>true</b> if this set contains no elements
     */
    public boolean isEmpty() {
        return this.fieldMessageSet.isEmpty();
    }

    /**
     * Checks whether a message is already in the set.
     * 
     * @param message
     *            the field message to check
     * 
     * @return <b>true</b> if the set contains the field, <b>false</b> if not
     */
    public boolean contains(FieldMessage message) {
        return this.fieldMessageSet.contains(message);
    }

    /**
     * Add a field message to the set.
     * 
     * @param message
     *            the field message to add
     */
    public void add(FieldMessage message) {
        this.fieldMessageSet.add(message);
    }

    /**
     * Getter for the field messages.
     * 
     * @return the field message set
     */
    public Set<FieldMessage> getAll() {
        return new HashSet<FieldMessage>(this.fieldMessageSet);
    }

    /**
     * Remove a field message from the set.
     * 
     * @param message
     *            the message to remove
     */
    public void remove(FieldMessage message) {
        this.fieldMessageSet.remove(message);
    }

    /**
     * Removes all field messages from the set.
     */
    public void clear() {
        this.fieldMessageSet.clear();
    }

}
