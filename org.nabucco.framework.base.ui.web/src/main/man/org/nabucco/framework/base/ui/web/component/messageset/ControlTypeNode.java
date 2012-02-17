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

import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset.MessageSetMessageExtension;

/**
 * ControlTypeNode
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ControlTypeNode extends AbstractMessageSetNode {

    private MessageSetControlType type;

    /**
     * 
     * Creates a new {@link ControlTypeNode} instance.
     * 
     * @param type
     *            type of the control
     */
    ControlTypeNode(MessageSetControlType type) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot create control type node. Type is null");
        }

        this.type = type;
    }

    /**
     * Adds a Work Item extesnsion to the tree
     * 
     * @param ext
     *            extension to be added
     */
    public void addExtension(MessageSetMessageExtension ext) {

        String control = PropertyLoader.loadProperty(ext.getControl());

        if (!super.childrenMap.containsKey(control)) {
            ControlNode cNode = new ControlNode(control);
            super.childrenMap.put(control, cNode);
        }
        ControlNode controlNode = (ControlNode) super.childrenMap.get(control);
        controlNode.addExtension(ext);
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public String getType() {
        return this.type.getValue();
    }

}
