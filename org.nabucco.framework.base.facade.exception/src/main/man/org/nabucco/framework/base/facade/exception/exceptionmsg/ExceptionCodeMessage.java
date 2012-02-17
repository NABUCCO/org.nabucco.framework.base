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

/**
 * ExceptionCodeMessage
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ExceptionCodeMessage {

    private String message;

    private String path;

    /**
     * Getter for the message.
     * 
     * @return Returns the message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Setter for the message.
     * 
     * @param message
     *            The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for the path.
     * 
     * @return Returns the path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Setter for the path.
     * 
     * @param path
     *            The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

}
