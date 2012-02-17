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
public class ControlNode extends AbstractMessageSetNode {

    private String controlName;

    ControlNode(String controlName) {
        this.controlName = controlName;
    }

    /**
     * Adds a Work Item extesnsion to the tree
     * 
     * @param ext
     *            extension to be added
     */
    public void addExtension(MessageSetMessageExtension ext) {

        String constraintTypeString = PropertyLoader.loadProperty(ext.getConstraint());
        
        MessageSetConstraintType constraintType = this.evaluateConstraintType(constraintTypeString);

        String severity = PropertyLoader.loadProperty(ext.getSeverity());
        String text = PropertyLoader.loadProperty(ext.getText());

        if (!super.childrenMap.containsKey(constraintType.getValue())) {
            ConstraintNode cNode = new ConstraintNode(constraintType, severity, text);
            super.childrenMap.put(constraintType.getValue(), cNode);
        } else {
            throw new IllegalArgumentException("Duplicate constraint declaration.");
        }

    }

    /**
     * Evaluates the contraint type
     * 
     * @param value
     *            string value
     * @return mapped value
     */
    private MessageSetConstraintType evaluateConstraintType(String value) {
        if (value == null || value.equals(STAR_SYMBOL)) {
            return MessageSetConstraintType.NONE;
        }

        MessageSetConstraintType retVal = Enum.valueOf(MessageSetConstraintType.class, value);

        if (retVal == null) {
            throw new IllegalArgumentException("Constraint type " + value + "connot be parsed to a contraint type.");
        }
        return retVal;
    }

    /**
     * Getter for the controlName.
     * 
     * @return Returns the controlName.
     */
    public String getControlName() {
        return this.controlName;
    }
}
