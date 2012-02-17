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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;

/**
 * InvocationIdentifierFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class InvocationIdentifierFactory {

    private long invocationCount;

    private String nodeName;

    private String nodeAddress;

    /**
     * Singleton instance.
     */
    private static InvocationIdentifierFactory instance;

    /**
     * Private constructor.
     */
    private InvocationIdentifierFactory() {
        this.invocationCount = 0;

        try {
            this.nodeName = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            this.nodeName = "unknownhost";
        }

        try {
            this.nodeAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.nodeAddress = "unknownhost";
        }
    }

    /**
     * Singleton access.
     * 
     * @return the InvocationIdentifierFactory instance.
     */
    public static synchronized InvocationIdentifierFactory getInstance() {
        if (instance == null) {
            instance = new InvocationIdentifierFactory();
        }
        return instance;
    }

    /**
     * Creates a new {@link InvocationIdentifier} instance depending on the current node, address
     * and invocation count.
     * 
     * @return the identifier
     */
    public InvocationIdentifier createInvocationIdentifier() {
        long count = nextCount();
        long time = NabuccoSystem.getCurrentTimeMillis();
        long id = this.generateID(count, this.nodeName, this.nodeAddress, time);

        return new InvocationIdentifier(id, count, this.nodeName, this.nodeAddress, time);
    }

    private synchronized long nextCount() {
        long nextCount = this.invocationCount;
        this.invocationCount = this.invocationCount + 1;
        if (nextCount > this.invocationCount) {
            // invocationCount overflow set new invocationCount to 0
            this.invocationCount = 0;
        }
        return nextCount;
    }

    private long generateID(long counter, String name, String address, long time) {

        long id = counter;

        if (name == null) {
            Random rand = new Random(NabuccoSystem.getCurrentTimeMillis());
            id = id + rand.nextInt();
        } else {
            id = id + name.hashCode();
        }

        if (address == null) {
            Random rand = new Random(NabuccoSystem.getCurrentTimeMillis());
            id = id + rand.nextInt();
        } else {
            id = id + address.hashCode();
        }
        id = id + time;

        return id;
    }

}
