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
package org.nabucco.framework.base.facade.exception.exceptionmsg;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.exceptionmsg.MessageSeverityType;

/**
 * ExceptionMessage
 * <p/>
 * Holding information about exception messages.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class ExceptionMessage {

    private Exception matchedException;

    private String message;

    private Timestamp timestamp;

    private MessageSeverityType severity;

    private List<ExceptionCodeMessage> codeMessages;

    public ExceptionMessage() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Getter for the matched exception.
     * 
     * @return the exception
     */
    public Exception getMatchedException() {
        return matchedException;
    }

    /**
     * Setter for the matched exception.
     * 
     * @param matchedException
     *            the exception to set
     */
    public void setMatchedException(Exception matchedException) {
        this.matchedException = matchedException;
    }

    /**
     * Getter for the error message.
     * 
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for the error message.
     * 
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for the error severity.
     * 
     * @return the severity
     */
    public MessageSeverityType getSeverity() {
        return severity;
    }

    /**
     * Setter for the error severity.
     * 
     * @param severity
     *            the severity to set
     */
    public void setSeverity(MessageSeverityType severity) {
        this.severity = severity;
    }

    /**
     * Getter for the error code messages.
     * 
     * @return the list of error code messages
     */
    public List<ExceptionCodeMessage> getCodeMessages() {
        if (this.codeMessages == null) {
            this.codeMessages = new ArrayList<ExceptionCodeMessage>();
        }

        return this.codeMessages;
    }

    /**
     * @return Returns the timestamp.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

}
