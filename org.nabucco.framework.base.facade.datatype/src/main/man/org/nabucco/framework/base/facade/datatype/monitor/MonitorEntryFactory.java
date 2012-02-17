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
package org.nabucco.framework.base.facade.datatype.monitor;

/**
 * MonitorEntryFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MonitorEntryFactory {

    /**
     * Singleton instance.
     */
    private static MonitorEntryFactory instance = new MonitorEntryFactory();

    /**
     * Private constructor.
     */
    private MonitorEntryFactory() {
    }

    /**
     * Singleton access.
     * 
     * @return the MonitorEntryFactory instance.
     */
    public static MonitorEntryFactory getInstance() {
        return instance;
    }

    /**
     * Create a new monitor entry for the given entry type.
     * 
     * @param type
     *            the entry type to create the entry for
     * @param serviceName
     *            the service name
     * @param operationName
     *            the operation name
     * @param timestamp
     *            the timestamp
     * 
     * @return the new created monitor entry
     */
    public MonitorEntry createMonitorEntry(MonitorEntryType type, String serviceName, String operationName,
            long timestamp) {

        MonitorEntry entry;

        switch (type) {

        case SERVICE_OPERATION:
            entry = new ServiceMonitorEntry();
            break;

        case PERSISTENCE_OPERATION:
            entry = new PersistenceMonitorEntry();
            break;

        case EXCEPTION:
            entry = new ExceptionMonitorEntry();
            break;

        default:
            throw new IllegalArgumentException("MonitorEntryType " + type + " is not supported.");

        }

        entry.setServiceName(serviceName);
        entry.setOperationName(operationName);
        entry.setTimestamp(timestamp);

        return entry;
    }

    /**
     * Create a new service monitor entry.
     * 
     * @param serviceName
     *            the service name
     * @param operationName
     *            the service operation name
     * @param timestamp
     *            the timestamp
     * @param duration
     *            the service duration
     * @param exceptionName
     *            the monitored exception
     * 
     * @return the new created monitor entry
     */
    public MonitorEntry createServiceMonitorEntry(String serviceName, String operationName, long timestamp,
            long duration, String exceptionName) {

        ServiceMonitorEntry entry = new ServiceMonitorEntry();
        entry.setServiceName(serviceName);
        entry.setOperationName(operationName);
        entry.setTimestamp(timestamp);
        entry.setDuration(duration);
        entry.setExceptionName(exceptionName);

        return entry;
    }

    /**
     * Create a new service monitor entry.
     * 
     * @param serviceName
     *            the service name
     * @param operationName
     *            the service operation name
     * @param timestamp
     *            the timestamp
     * @param duration
     *            the service duration
     * @param entityName
     *            the monitored entity
     * @param type
     *            the persistence operation type
     * @param query
     *            the executed query
     * 
     * @return the new created monitor entry
     */
    public MonitorEntry createPersistenceMonitorEntry(String serviceName, String operationName, long timestamp,
            long duration, String entityName, PersistenceOperationType type, String query) {

        PersistenceMonitorEntry entry = new PersistenceMonitorEntry();
        entry.setServiceName(serviceName);
        entry.setOperationName(operationName);
        entry.setTimestamp(timestamp);
        entry.setDuration(duration);
        entry.setEntityName(entityName);
        entry.setOperationType(type);
        entry.setQuery(query);

        return entry;
    }

    /**
     * Create a new exception monitor entry.
     * 
     * @param serviceName
     *            the service name
     * @param operationName
     *            the service operation name
     * @param timestamp
     *            the timestamp
     * @param exceptionName
     *            the monitored exception
     * 
     * @return the new created monitor entry
     */
    public MonitorEntry createExceptionMonitorEntry(String serviceName, String operationName, long timestamp,
            String exceptionName) {

        ExceptionMonitorEntry entry = new ExceptionMonitorEntry();
        entry.setServiceName(serviceName);
        entry.setOperationName(operationName);
        entry.setTimestamp(timestamp);
        entry.setExceptionName(exceptionName);

        return entry;
    }
}
