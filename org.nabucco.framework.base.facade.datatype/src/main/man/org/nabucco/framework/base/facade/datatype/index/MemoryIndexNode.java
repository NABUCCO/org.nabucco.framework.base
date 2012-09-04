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

import java.util.Arrays;

/**
 * MemoryIndexNode
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class MemoryIndexNode extends NabuccoIndexNode {

    private final MemoryIndexNode[] nodes = new MemoryIndexNode[NabuccoIndexCharacterMapper.SIZE];

    private final long[] ids;

    private final String[] names;

    private final int maxSize;

    /**
     * Creates a new {@link MemoryIndexNode} instance.
     * 
     * @param maxSize
     *            the maximum memory node size
     */
    public MemoryIndexNode(int size) {
        this.maxSize = size;

        this.ids = new long[size];
        this.names = new String[size];
    }

    @Override
    public NabuccoIndexNode getNode(int index) {
        if (index >= NabuccoIndexCharacterMapper.SIZE) {
            throw new IllegalArgumentException("Cannot get node from index position '"
                    + index + "'. Larger than maximum index maxSize.");
        }
        MemoryIndexNode node = this.nodes[index];
        if (node == null) {
            node = new MemoryIndexNode(this.maxSize);
            this.nodes[index] = node;
        }
        return node;
    }

    @Override
    public NabuccoIndexNode getNode(char character) {
        int index = NabuccoIndexCharacterMapper.getIndex(character);
        return this.getNode(index);
    }

    @Override
    public NabuccoIndexNode searchNode(char character) {
        int index = NabuccoIndexCharacterMapper.getIndex(character);
        if (index >= NabuccoIndexCharacterMapper.SIZE) {
            throw new IllegalArgumentException("Cannot get node from index position '"
                    + index + "'. Larger than maximum index maxSize.");
        }

        return this.nodes[index];
    }

    @Override
    public int size() {
        for (int i = 0; i < this.names.length; i++) {
            if (this.names[i] == null) {
                return i;
            }
        }
        return this.maxSize;
    }

    @Override
    public int maxSize() {
        return this.maxSize;
    }

    @Override
    public boolean addValue(long id, String name) {
        for (int i = 0; i < this.names.length; i++) {
            if (this.names[i] == null) {
                this.ids[i] = id;
                this.names[i] = name;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateValue(long id, String name) {
        for (int i = 0; i < this.ids.length; i++) {
            if (this.ids[i] == id) {
                this.names[i] = name;
                return true;
            }
        }

        return false;
    }

    @Override
    public String getName(long id) {
        for (int i = 0; i < this.ids.length; i++) {
            if (this.ids[i] == id) {
                return this.names[i];
            }
        }

        return null;
    }

    @Override
    public String getId(String name) {
        for (int i = 0; i < this.names.length; i++) {
            if (this.names[i].equals(name)) {
                return this.names[i];
            }
        }

        return null;
    }

    @Override
    public long[] ids() {
        return Arrays.copyOf(this.ids, this.ids.length);
    }

    @Override
    public String[] names() {
        return Arrays.copyOf(this.names, this.names.length);
    }

    @Override
    public boolean contains(String name, long id) {
        for (int i = 0; i < this.ids.length; i++) {
            if (this.ids[i] == id) {
                return true;
            }
            if (this.names[i] != null && this.names[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean isComplete() {
        return this.size() == this.maxSize;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        this.print(0, result);

        return result.toString();
    }

    /**
     * Print the index node to the string builder.
     * 
     * @param offset
     *            the print offset
     * @param result
     *            the print result
     */
    protected void print(int offset, StringBuilder result) {
        for (int i = 0; i < this.names.length; i++) {
            if (this.names[i] != null) {
                result.append(this.names[i].substring(0, offset) + ": ");
                result.append("\t\t\t");
                if (offset < 6) {
                    result.append("\t");
                }
                break;
            }
        }

        for (int i = 0; i < this.names.length; i++) {
            if (this.names[i] != null) {
                result.append(this.names[i]);
                result.append(" (");
                result.append(this.ids[i]);
                result.append("), ");
            }
        }

        result.append("\n");

        for (int i = 0; i < this.nodes.length; i++) {
            if (this.nodes[i] != null) {
                this.nodes[i].print(offset + 1, result);
            }
        }
    }
}
