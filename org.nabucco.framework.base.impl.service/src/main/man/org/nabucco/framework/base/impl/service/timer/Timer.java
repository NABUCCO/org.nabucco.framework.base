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
package org.nabucco.framework.base.impl.service.timer;

import java.io.Serializable;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * Timer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class Timer implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    private final TimerPriority priority;

    private final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(this.getClass());

    /**
     * Creates a new {@link Timer} instance.
     * 
     * @param name
     *            the timer name
     * @param priority
     *            the timer priority
     */
    public Timer(String name, TimerPriority priority) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create Timer for name [null].");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Cannot create Timer for priority [null].");
        }

        this.name = name;
        this.priority = priority;
    }

    /**
     * Getter for the logger.
     * 
     * @return Returns the logger.
     */
    protected NabuccoLogger getLogger() {
        return this.logger;
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Getter for the priority.
     * 
     * @return Returns the priority.
     */
    public final TimerPriority getPriority() {
        return this.priority;
    }

    /**
     * Method that executes the appropriate timer logic.
     * 
     * @throws TimerExecutionException
     *             when an exception during timer execution occurs
     */
    public abstract void execute() throws TimerExecutionException;

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Timer other = (Timer) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name);
        builder.append(" [");
        builder.append(this.getClass().getCanonicalName());
        builder.append(", ");
        builder.append(this.priority);
        builder.append("]");
        return builder.toString();
    }

}
