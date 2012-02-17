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

import org.nabucco.framework.base.facade.datatype.exceptionmsg.MessageSeverityType;

/**
 * MessageElement
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class MessageElement extends MessageSearchElement {

    private String message;

    private MessageSeverityType severity;

    private boolean forceOriginalMessage;

    /**
     * Creates a new {@link MessageElement} instance.
     * 
     * @param parent
     *            the parent component
     * @param id
     *            the id
     */
    public MessageElement(MessageSearchComponent parent, String id) {
        super(parent, id);
    }

    /**
     * Getter for the message.
     * 
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the message.
     * 
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for the severity.
     * 
     * @return the severity
     */
    public MessageSeverityType getSeverity() {
        return severity;
    }

    /**
     * Setter for the severity.
     * 
     * @param severity
     *            the severity to set
     */
    public void setSeverity(String severity) {
        this.severity = MessageSeverityType.valueOf(severity);
    }

    /**
     * Getter for the forceOriginalMessage.
     * 
     * @return Returns the forceOriginalMessage.
     */
    public boolean isForceOriginalMessage() {
        return this.forceOriginalMessage;
    }

    /**
     * Setter for the forceOriginalMessage.
     * 
     * @param forceOriginalMessage
     *            The forceOriginalMessage to set.
     */
    public void setForceOriginalMessage(boolean forceOriginalMessage) {
        this.forceOriginalMessage = forceOriginalMessage;
    }

}
