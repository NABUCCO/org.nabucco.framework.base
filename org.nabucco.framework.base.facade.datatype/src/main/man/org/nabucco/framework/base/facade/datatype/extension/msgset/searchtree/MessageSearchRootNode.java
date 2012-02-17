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
package org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree;

/**
 * MessageSearchRootNode
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class MessageSearchRootNode extends MessageSearchComposite {

    /**
     * Creates a new {@link MessageSearchRootNode} instance.
     * 
     * @param id
     *            the component id
     */
    public MessageSearchRootNode(String id) {
        super(null, id);
    }

    /**
     * Getter for the language node.
     * 
     * @param language
     *            the language code to resolve
     * 
     * @return the language node
     */
    public MessageSearchLanguageNode getLanguageNode(String language) {
        return (MessageSearchLanguageNode) get(language);
    }

}
