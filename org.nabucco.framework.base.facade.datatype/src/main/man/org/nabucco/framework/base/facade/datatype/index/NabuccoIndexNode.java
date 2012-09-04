/*
 * Copyright 2012 PRODYNA AG
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
package org.nabucco.framework.base.facade.datatype.index;

/**
 * NabuccoIndexNode
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class NabuccoIndexNode {

    /**
     * Getter for the node at the given character. A new node is created if it does not already
     * exist.
     * 
     * @param character
     *            the index position as character
     * 
     * @return the node for the given character
     */
    public abstract NabuccoIndexNode getNode(char character);

    /**
     * Getter for the node at the given index position. A new node is created if it does not already
     * exist.
     * 
     * @param index
     *            the index position as integer
     * 
     * @return the node at the given position
     */
    public abstract NabuccoIndexNode getNode(int index);

    /**
     * Search for the node with the given character.
     * 
     * @param character
     *            the character to search for
     * 
     * @return the node for the given character or null if no node with the given character exists
     */
    public abstract NabuccoIndexNode searchNode(char character);

    /**
     * Add the id and value to the given index node.
     * 
     * @param id
     *            id of the value
     * @param name
     *            name of the value
     * 
     * @return <b>true</b> it the value has been successfully added, <b>false</b> if not
     */
    public abstract boolean addValue(long id, String name);

    /**
     * Update the value of the index node with the given id.
     * 
     * @param id
     *            id of the value
     * @param name
     *            name of the value
     * 
     * @return <b>true</b> it the value has been successfully added, <b>false</b> if not
     */
    public abstract boolean updateValue(long id, String name);

    /**
     * Getter for the id for the given value name.
     * 
     * 
     * @param name
     *            the name to resolve the id for
     * 
     * @return the node id for the given name
     */
    public abstract String getId(String name);

    /**
     * Getter for the name for the given value id.
     * 
     * @param id
     *            the id to resolve the name for
     * 
     * @return the node name for the given id
     */
    public abstract String getName(long id);

    /**
     * Getter for a copy of the index node ids.
     * 
     * @return the array of node ids
     */
    public abstract long[] ids();

    /**
     * Getter for a copy of the index node names.
     * 
     * @return the array of node names
     */
    public abstract String[] names();

    /**
     * Getter for the size.
     * 
     * @return Returns the current node size
     */
    public abstract int size();

    /**
     * Getter for the maximum node size.
     * 
     * @return Returns the maximum node size.
     */
    public abstract int maxSize();

    /**
     * Checks whether the node contains the value with the given id.
     * 
     * @param id
     *            the id to check for
     * 
     * @return <b>true</b> if the node contains the value, <b>false</b> if not
     */
    public abstract boolean contains(String name, long id);

    /**
     * Checks whether the node is empty or not.
     * 
     * @return <b>true</b> if the node holds no data, <b>false</b> if it does
     */
    public abstract boolean isEmpty();

    /**
     * Checks whether the node is already full or not.
     * 
     * @return <b>true</b> if the node holds alrady the maximum amount of data, <b>false</b> if not
     */
    public abstract boolean isComplete();

}
