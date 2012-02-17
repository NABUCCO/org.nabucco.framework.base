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
 * NabuccoScheduler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TimerService {

    /**
     * Singleton instance.
     */
    private static TimerService instance = new TimerService();

    /**
     * Private constructor.
     */
    private TimerService() {
    }

    /**
     * Singleton access.
     * 
     * @return the NabuccoScheduler instance.
     */
    public static TimerService getInstance() {
        return instance;
    }

    /**
     * Adds the timer to the nabucco timer registry.
     * 
     * @param timer
     *            the timer to register
     * 
     * @return the previously scheduled timer with this name, or null if none existed
     * 
     * @throws TimerLookupException
     *             when the timer mbean cannot be located
     */
    public Timer schedule(Timer timer) throws TimerLookupException {
        try {
            return TimerRegistryLocator.getInstance().getRegistry().register(timer);
        } catch (TimerLookupException tle) {
            throw tle;
        } catch (Exception e) {
            throw new TimerLookupException("Error scheduling timer '" + timer + "' in TimerRegistry MBean!", e);
        }
    }

    /**
     * Removes the timer from the nabucco timer registry.
     * 
     * @param name
     *            the name of the timer to unschedule
     * 
     * @return the unscheduled timer
     * 
     * @throws TimerLookupException
     *             when the timer mbean cannot be located
     */
    public Timer unschedule(String name) throws TimerLookupException {
        try {
            return TimerRegistryLocator.getInstance().getRegistry().deregister(name);
        } catch (TimerLookupException tle) {
            throw tle;
        } catch (Exception e) {
            throw new TimerLookupException("Error unscheduling timer '" + name + "' from TimerRegistry MBean!", e);
        }
    }

    /**
     * Checks whether a timer with a given name is registered in the timer registry.
     * 
     * @param name
     *            the name of the timer to check
     * 
     * @return <b>true</b> if the timer is registered, <b>false</b> if not
     * 
     * @throws TimerLookupException
     *             when the timer mbean cannot be located
     */
    public boolean isScheduled(String name) throws TimerLookupException {
        try {
            return TimerRegistryLocator.getInstance().getRegistry().get(name) != null;
        } catch (TimerLookupException tle) {
            throw tle;
        } catch (Exception e) {
            throw new TimerLookupException("Error finding timer '" + name + "' in TimerRegistry MBean!", e);
        }
    }

    /**
     * Returns a registered timer for the given name from the timer registry.
     * 
     * @param name
     *            the name of the timer to check
     * 
     * @return the registered timer, or null if no timer with the given name is registered
     * 
     * @throws TimerLookupException
     *             when the timer mbean cannot be located
     */
    public Timer getTimer(String name) throws TimerLookupException {
        try {
            return TimerRegistryLocator.getInstance().getRegistry().get(name);
        } catch (TimerLookupException tle) {
            throw tle;
        } catch (Exception e) {
            throw new TimerLookupException("Error finding timer '" + name + "' in TimerRegistry MBean!", e);
        }
    }

}
