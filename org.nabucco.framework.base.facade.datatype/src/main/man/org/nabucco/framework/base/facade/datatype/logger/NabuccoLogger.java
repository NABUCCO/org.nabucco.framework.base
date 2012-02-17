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
package org.nabucco.framework.base.facade.datatype.logger;

/**
 * NabuccoLogger
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoLogger {

    /**
     * Severity 0.
     * 
     * @param e
     *            the cause
     * @param message
     *            the message
     */
    void fatal(Throwable e, Object... message);

    /**
     * Severity 1.
     * 
     * @param e
     *            the cause
     * @param message
     *            the message
     */
    void error(Throwable e, Object... message);

    /**
     * Severity 2.
     * 
     * @param e
     *            the cause
     * @param message
     *            the message
     */
    void warning(Throwable e, Object... message);

    /**
     * Severity 3.
     * 
     * @param e
     *            the cause
     * @param message
     *            the message
     */
    void info(Throwable e, Object... message);

    /**
     * Severity 4.
     * 
     * @param e
     *            the cause
     * @param message
     *            the message
     */
    void debug(Throwable e, Object... message);

    /**
     * Severity 0.
     * 
     * @param message
     */
    void fatal(Object... message);

    /**
     * Severity 1.
     * 
     * @param message
     *            the message
     */
    void error(Object... message);

    /**
     * Severity 2.
     * 
     * @param message
     *            the message
     */
    void warning(Object... message);

    /**
     * Severity 3.
     * 
     * @param message
     *            the message
     */
    void info(Object... message);

    /**
     * Severity 4.
     * 
     * @param message
     *            the message
     */
    void debug(Object... message);

    /**
     * Severity 5.
     * 
     * @param message
     *            the message
     */
    void trace(Object... message);

    /**
     * Checks whether debug is enabled.
     * 
     * @return <b>true</b> if the debug level is enabled, <b>false</b> if not
     */
    boolean isDebugEnabled();

    /**
     * Checks whether trace is enabled.
     * 
     * @return <b>true</b> if the trace level is enabled, <b>false</b> if not
     */
    boolean isTraceEnabled();

}
