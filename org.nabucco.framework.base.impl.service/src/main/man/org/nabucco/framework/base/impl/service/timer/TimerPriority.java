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

/**
 * TimerPriority
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum TimerPriority {

    /**
     * Timer that is repeated in short intervals.
     */
    SHORT("Short Running", 1000l),

    /**
     * Timer that is repeated in medium intervals.
     */
    MEDIUM("Medium Running", 10000l),

    /**
     * Timer that is repeated in long intervals.
     */
    LONG("Long Running", 100000l);

    /**
     * Creates a new {@link TimerPriority} instance.
     * 
     * @param name
     *            name of the priority
     * @param interval
     *            the interval of the timer
     */
    private TimerPriority(String name, long interval) {
        this.name = name;
        this.interval = interval;
    }

    private String name;

    private long interval;

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the interval.
     * 
     * @return Returns the interval.
     */
    public long getInterval() {
        return this.interval;
    }

    /**
     * Parses the priority depending on the given string.
     * 
     * @param priority
     *            the string priority to parse
     * 
     * @return the timer priority literal, or null if not found
     */
    public static TimerPriority parsePriority(String priority) {
        for (TimerPriority literal : TimerPriority.values()) {
            if (literal.name().equalsIgnoreCase(priority)) {
                return literal;
            }
        }

        return null;
    }
}
