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

import java.util.List;

import org.nabucco.framework.base.facade.service.jmx.MBean;

/**
 * TimerMBean
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface TimerRegistry extends MBean {

    /**
     * Number of registered timers.
     * 
     * @return the number of registered timers.
     */
    int size();

    /**
     * Sends a notification to all registered timers.
     */
    void flush();

    /**
     * Removes all registered timers.
     */
    void clear();

    /**
     * List the registered timers.
     * 
     * @return the registered timers
     */
    String listTimers();

    /**
     * Register a timer in the timer registry.
     * 
     * @param timer
     *            the timer to register
     * 
     * @return the previously registered timer with this name, or null if none existed
     */
    Timer register(Timer timer);

    /**
     * Retrieve the timer with the given name, or null if not registered.
     * 
     * @param name
     *            the name of the timer
     * 
     * @return the registered timer, or null
     */
    Timer get(String name);

    /**
     * Getter for all registered timers of a given priority.
     * 
     * @param priority
     *            the timer priority
     * 
     * @return the list of registered of a given
     */
    List<Timer> get(TimerPriority priority);

    /**
     * Getter for all registered timers.
     * 
     * @return the list of registered timers
     */
    List<Timer> getAll();

    /**
     * Removes the timer with the given name from the registry.
     * 
     * @param name
     *            name of the timer
     * 
     * @return the removed timer, or null if no timer with the given name was registered
     */
    Timer deregister(String name);

    /**
     * Set whether the clock should be logged or not.
     * 
     * @param enable
     *            <b>true</b> enables logging, <b>false</b>disables logging.
     */
    void setLogging(boolean enable);

}
