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
package org.nabucco.framework.base.ui.web.component.messageset;

import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset.MessageSetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset.MessageSetMessageExtension;

/**
 * LanguageNode
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class LanguageNode extends AbstractMessageSetNode {

    private String language;

    /**
     * 
     * Creates a new {@link LanguageNode} instance.
     * 
     * @param language
     * @param workItemsExtensions
     */
    LanguageNode(String language) {
        this.language = language;
    }

    /**
     * Adds a Work Item extesnsion to the tree
     * 
     * @param ext
     *            extension to be added
     */
    public void addExtension(MessageSetExtension ext) {
        String workItemId = PropertyLoader.loadProperty(ext.getWorkItemId());
        NabuccoList<MessageSetMessageExtension> messages = ext.getMessages();

        if (!super.childrenMap.containsKey(workItemId)) {
            WorkItemNode node = new WorkItemNode(workItemId);
            super.childrenMap.put(workItemId, node);
        }
        WorkItemNode node = (WorkItemNode) super.childrenMap.get(workItemId);

        for (MessageSetMessageExtension message : messages) {
            node.addExtension(message);
        }
    }

    /**
     * Getter for the language.
     * 
     * @return Returns the language.
     */
    public String getLanguage() {
        return this.language;
    }


}
