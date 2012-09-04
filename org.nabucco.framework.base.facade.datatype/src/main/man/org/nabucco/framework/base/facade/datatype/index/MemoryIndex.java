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

import java.util.Map;

/**
 * MemoryIndex
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class MemoryIndex extends NabuccoIndex {

    private NabuccoIndexNode rootNode;

    /**
     * Creates a new {@link MemoryIndex} instance.
     * 
     * @param name
     *            the index name
     * @param size
     *            the amount of values that are stored per index node
     */
    public MemoryIndex(String name, int size) {
        super(name, size);

        this.rootNode = new MemoryIndexNode(size);
    }

    @Override
    protected void addToIndex(long id, String name) {

        NabuccoIndexNode node = this.rootNode;

        for (int i = 0; i < name.length(); i++) {

            char character = name.charAt(i);
            node = node.getNode(character);

            if (!node.isComplete()) {

                if (node.isEmpty() || !node.contains(name, id)) {
                    node.addValue(id, name);
                } else {
                    node.updateValue(id, name);
                }

            } else {
                if (node.contains(name, id)) {
                    node.updateValue(id, name);
                }
            }
        }

    }

    @Override
    protected void searchIndex(String searchString, Map<String, Long> result) {
        NabuccoIndexNode node = this.rootNode;
        for (int i = 0; i < searchString.length(); i++) {
            char character = searchString.charAt(i);
            node = node.searchNode(character);

            if (node == null) {
                return;
            }
        }

        long[] ids = node.ids();
        String[] names = node.names();

        for (int i = 0; i < names.length; i++) {
            if (names[i] != null) {
                result.put(names[i], ids[i]);
            }
        }
    }

    @Override
    protected void resetIndex() {
    }

    @Override
    public String toString() {
        return this.rootNode.toString();
    }

}
