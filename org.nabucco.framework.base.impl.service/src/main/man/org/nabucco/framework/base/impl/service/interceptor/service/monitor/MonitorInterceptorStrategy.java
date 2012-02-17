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
package org.nabucco.framework.base.impl.service.interceptor.service.monitor;

import java.lang.reflect.Method;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntry;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntryFactory;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContext;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContextType;
import org.nabucco.framework.base.impl.service.interceptor.service.ServiceInterceptorStrategy;
import org.nabucco.framework.base.impl.service.monitor.MonitorFacade;
import org.nabucco.framework.base.impl.service.monitor.ServiceNameThreadLocal;
import org.nabucco.framework.base.impl.service.monitor.ServiceOperationNameThreadLocal;

/**
 * MonitorInterceptorStrategy
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MonitorInterceptorStrategy implements ServiceInterceptorStrategy {

    /** The Logger */
    private static NabuccoLogger monitoringLogger = NabuccoLoggingFactory.getInstance().getLogger(
            MonitorInterceptorStrategy.class);

    @Override
    public void beforeInvocation(InterceptorContext context, Service service, Method operation,
            ServiceRequest<?> request, NabuccoLogger logger) throws ServiceException {

        String serviceName = service.getName();
        String operationName = operation.getName();

        ServiceNameThreadLocal.setServiceName(serviceName);
        ServiceOperationNameThreadLocal.setOperationName(operationName);

        try {
            long start = NabuccoSystem.getCurrentTimeMillis();
            MonitorTimestamp timestamp = new MonitorTimestamp(start);
            context.put(InterceptorContextType.START_TIME, timestamp);

        } catch (Exception e) {
            monitoringLogger.warning(e, "Error in beforeInvocation(): " + e.getMessage());
        }
    }

    @Override
    public void afterInvocation(InterceptorContext context, Service service, Method operation,
            ServiceRequest<?> request, ServiceResponse<?> response, NabuccoLogger logger, Throwable exception)
            throws ServiceException {

        long end = NabuccoSystem.getCurrentTimeMillis();

        try {
            MonitorTimestamp startTimestamp = (MonitorTimestamp) context.get(InterceptorContextType.START_TIME);

            if (startTimestamp == null) {
                if (logger.isDebugEnabled()) {
                    monitoringLogger.debug("No start timestamp value found in context.");
                }
                return;
            }

            long start = startTimestamp.getTime();
            long duration = (end - start) / 10;

            String serviceName = service.getName();
            String operationName = operation.getName();
            String exceptionClassName = null;

            if (exception != null) {
                exceptionClassName = exception.getClass().getName();
                this.monitorException(serviceName, operationName, start, exceptionClassName);
            }

            this.monitor(serviceName, operationName, start, duration, exceptionClassName);

        } catch (Exception e) {
            monitoringLogger.error(e, "Error in afterInvocation(): " + e.getMessage());
        }
    }

    /**
     * Monitors the given service execution.
     * 
     * @param serviceName
     *            name of the executed service
     * @param operationName
     *            name of the executed operation
     * @param duration
     *            service execution duration in centiseconds
     * @param timestamp
     *            the starting timestamp
     * @param exceptionName
     *            exception name, or null if none was raised
     */
    private void monitor(String serviceName, String operationName, long timestamp, long duration, String exceptionName) {
        try {
            MonitorEntry entry = MonitorEntryFactory.getInstance().createServiceMonitorEntry(serviceName,
                    operationName, timestamp, duration, exceptionName);
            MonitorFacade.getInstance().putEntry(entry);
        } catch (Exception e) {
            monitoringLogger.warning(e, "Error monitoring ", serviceName, ".", operationName, "().");
        }
    }

    /**
     * Monitor a raised exception.
     * 
     * @param serviceName
     *            name of the executed service
     * @param operationName
     *            name of the executed operation
     * @param timestamp
     *            the exception timestamp
     * @param exceptionName
     *            name of the raised exception
     */
    private void monitorException(String serviceName, String operationName, long timestamp, String exceptionName) {
        try {
            MonitorEntry entry = MonitorEntryFactory.getInstance().createExceptionMonitorEntry(serviceName,
                    operationName, timestamp, exceptionName);
            MonitorFacade.getInstance().putEntry(entry);
        } catch (Exception e) {
            monitoringLogger.error(e, "Error monitoring exception ", exceptionName, " in service ", serviceName, ".",
                    operationName, "().");
        }
    }
}
