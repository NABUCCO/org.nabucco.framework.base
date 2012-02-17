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

import org.apache.log4j.Logger;

/**
 * Log4JNabuccoLogger
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class Log4JNabuccoLogger implements NabuccoLogger {

    /** The delegated logger. */
    private Logger logger;

    /**
     * Creates a new {@link Log4JNabuccoLogger} instance.
     * 
     * @param clazz
     *            the logging class
     */
    public Log4JNabuccoLogger(Class<?> clazz) {
        if (clazz != null) {
            this.logger = Logger.getLogger(clazz);
        } else {
            this.logger = Logger.getLogger(NabuccoLogger.class);
            this.logger.warn("No logging class defined using default 'NabuccoLogger'!");
        }
    }

    @Override
    public void fatal(Throwable throwable, Object... message) {
        if (!this.isLogged(throwable)) {
            this.logger.fatal(this.appendMessages(message), throwable);
        }
    }

    @Override
    public void fatal(Object... message) {
        this.logger.fatal(this.appendMessages(message));
    }

    @Override
    public void error(Throwable throwable, Object... message) {
        if (!this.isLogged(throwable)) {
            this.logger.error(this.appendMessages(message), throwable);
        }
    }

    @Override
    public void error(Object... message) {
        this.logger.error(this.appendMessages(message));
    }

    @Override
    public void warning(Throwable throwable, Object... message) {
        if (!this.isLogged(throwable)) {
            this.logger.warn(this.appendMessages(message), throwable);
        }
    }

    @Override
    public void warning(Object... message) {
        this.logger.warn(this.appendMessages(message));
    }

    @Override
    public void info(Throwable throwable, Object... message) {
        if (this.logger.isInfoEnabled() && !this.isLogged(throwable)) {
            this.logger.error(this.appendMessages(message), throwable);
        }
    }

    @Override
    public void info(Object... message) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info(this.appendMessages(message));
        }
    }

    @Override
    public void debug(Throwable throwable, Object... message) {
        if (this.logger.isDebugEnabled() && !this.isLogged(throwable)) {
            this.logger.debug(this.appendMessages(message), throwable);
        }
    }

    @Override
    public void debug(Object... message) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.appendMessages(message));
        }
    }

    @Override
    public void trace(Object... message) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(this.appendMessages(message));
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    /**
     * Check whether the exception has already been logged and must not be logged again.
     * 
     * @param throwable
     *            the throwable to log
     * 
     * @return <b>true</b> if the exception must be logged, <b>false</b> if not
     */
    private boolean isLogged(Throwable throwable) {
        if (throwable == null) {
            return true;
        }

        if (throwable instanceof LoggingBehaviour) {
            LoggingBehaviour loggingBehaviour = (LoggingBehaviour) throwable;

            boolean isLogged = loggingBehaviour.isLogged();
            loggingBehaviour.setLogged(true);

            return isLogged;
        }

        return false;
    }

    /**
     * Append the message fragments into a common string.
     * 
     * @param messages
     *            the message tokens
     * 
     * @return the resulting message
     */
    private String appendMessages(Object... messages) {

        int size = messages.length;

        StringBuilder result = new StringBuilder();

        String iid = InvocationIdentifierThreadLocal.getInvocationIdentifier();
        String uid = UserIdThreadLocal.getUserId();

        result.append("IID=[").append(iid).append("]");
        result.append(" UID=[").append(uid).append("] ");

        for (int i = 0; i < size; i++) {
            String message = String.valueOf(messages[i]);
            if (message != null) {
                result.append(messages[i]);
            }
        }

        return result.toString();
    }
}
