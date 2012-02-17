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
package org.nabucco.framework.base.facade.message.ping;

import java.io.Serializable;

/**
 * PingResponse
 * <p/>
 * The response from third party system pings.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PingResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final PingStatus status;

    private final long timestamp;

    private final long duration;

    /**
     * Creates a new {@link PingResponse} instance.
     * 
     * @param status
     *            the ping status
     * @param timestamp
     *            the ping starting timestamp
     * @param duration
     *            the ping duration
     */
    public PingResponse(PingStatus status, long timestamp, long duration) {
        if (status == null) {
            throw new IllegalArgumentException("PingStatus must not be [null].");
        }

        this.status = status;
        this.timestamp = timestamp;
        this.duration = duration;
    }

    /**
     * Getter for the status.
     * 
     * @return Returns the status.
     */
    public PingStatus getStatus() {
        return this.status;
    }

    /**
     * Getter for the timestamp.
     * 
     * @return Returns the timestamp.
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Getter for the duration.
     * 
     * @return Returns the duration.
     */
    public long getDuration() {
        return this.duration;
    }

}
