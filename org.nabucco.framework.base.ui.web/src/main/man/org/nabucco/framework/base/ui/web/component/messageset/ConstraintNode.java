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

/**
 * ControlTypeNode
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ConstraintNode extends AbstractMessageSetNode {

    private MessageSetConstraintType constraint;

    private String severity;

    private String text;

    /**
     * 
     * Creates a new {@link ConstraintNode} instance.
     * 
     * @param constraint
     *            contraint of the node
     * @param severity
     *            severity of the error
     * @param text
     *            the text to show
     */
    ConstraintNode(MessageSetConstraintType constraint, String severity, String text) {
        if (constraint == null) {
            throw new IllegalArgumentException("Cannot create constraint node. Constraint is null");
        }
        if (severity == null) {
            throw new IllegalArgumentException("Cannot create constraint node. Severity is null");
        }
        if (text == null) {
            throw new IllegalArgumentException("Cannot create constraintnode. Text is null");
        }

        this.constraint = constraint;
        this.severity = severity;
        this.text = text;
    }

    /**
     * Getter for the constraint.
     * 
     * @return Returns the constraint.
     */
    public MessageSetConstraintType getConstraint() {
        return this.constraint;
    }

    /**
     * Getter for the severity.
     * 
     * @return Returns the severity.
     */
    public String getSeverity() {
        return this.severity;
    }

    /**
     * Getter for the text.
     * 
     * @return Returns the text.
     */
    public String getText() {
        return this.text;
    }

}
