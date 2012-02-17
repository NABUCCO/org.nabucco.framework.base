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
package org.nabucco.framework.base.facade.message.tracing;

import java.io.Serializable;

/**
 * InvocationIdentifier
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class InvocationIdentifier implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long id;

    private final long count;

    private final String nodeName;

    private final String nodeAddress;

    private final long time;

    /**
     * Creates a new Invocation Identifier instance.
     * 
     * @param id
     *            the invocation id
     * @param count
     *            the invocation count
     * @param nodeName
     *            name of the node
     * @param nodeAddress
     *            address of the node
     * @param time
     *            invocation time
     */
    public InvocationIdentifier(long id, long count, String nodeName, String nodeAddress, long time) {
        this.id = id;
        this.count = count;
        this.nodeName = nodeName;
        this.nodeAddress = nodeAddress;
        this.time = time;
    }

    /**
     * Getter for the invocation id.
     * 
     * @return Returns the id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Getter for the invocation count.
     * 
     * @return Returns the count.
     */
    public long getCount() {
        return this.count;
    }

    /**
     * Getter for the node name.
     * 
     * @return Returns the nodeName.
     */
    public String getNodeName() {
        return this.nodeName;
    }

    /**
     * Getter for the node ip address.
     * 
     * @return Returns the nodeAddress.
     */
    public String getNodeAddress() {
        return this.nodeAddress;
    }

    /**
     * Getter for the invocation time.
     * 
     * @return Returns the time.
     */
    public long getTime() {
        return this.time;
    }

}
