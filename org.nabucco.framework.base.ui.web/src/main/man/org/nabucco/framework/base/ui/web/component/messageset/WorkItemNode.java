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
 * WorkItemNode
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class WorkItemNode extends AbstractMessageSetNode {

    private String workItemId;

    /**
     * 
     * Creates a new {@link WorkItemNode} instance.
     * 
     * @param id
     *            id of working item
     * @param controlItemsExtensions
     *            control configurations
     */
    WorkItemNode(String id) {
        this.workItemId = id;
    }

    /**
     * Adds a Work Item extesnsion to the tree
     * 
     * @param ext
     *            extension to be added
     */
    public void addExtension(MessageSetMessageExtension ext) {
        String controlTypeString = PropertyLoader.loadProperty(ext.getControlType());
        MessageSetControlType controlType = this.evaluateControlType(controlTypeString);

        if (controlType != null) {
            if (!super.childrenMap.containsKey(controlType.getValue())) {
                ControlTypeNode cNode = new ControlTypeNode(controlType);
                super.childrenMap.put(controlType.getValue(), cNode);
            }
            ControlTypeNode cNode = (ControlTypeNode) super.childrenMap.get(controlType.getValue());
            cNode.addExtension(ext);
        }
    }

    /**
     * Evaluates the control type
     * 
     * @param value
     *            string value
     * @return mapped value
     */
    private MessageSetControlType evaluateControlType(String value) {
        if (value == null || value.equals(STAR_SYMBOL)) {
            return MessageSetControlType.NONE;
        }

        MessageSetControlType retVal = Enum.valueOf(MessageSetControlType.class, value);

        if (retVal == null) {
            throw new IllegalArgumentException("Value " + value + "connot be parsed to a control type.");
        }
        return retVal;
    }

    /**
     * Getter for the workItemId.
     * 
     * @return Returns the workItemId.
     */
    public String getWorkItemId() {
        return this.workItemId;
    }

}
