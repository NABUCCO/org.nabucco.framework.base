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
package org.nabucco.framework.base.facade.component.connection;

/**
 * ConnectionConstants
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface ConnectionConstants {

    /** Type of the connection (jboss, glassfish, etc.). */
    final String CONNECTION_TYPE = "connection.type";

    /** Custom Name of the connection (testA1). */
    final String CONNECTION_NAME = "connection.name";

    /** Environment of the connection (dev, test, prod, etc.). */
    final String CONNECTION_ENVIRONMENT = "connection.env";

    /** Constant for connection host address. */
    final String CONNECTION_HOST = "connection.host";

    /** Constant for connection port. */
    final String CONNECTION_PORT = "connection.port";

    /** Constant for localhost. */
    final String LOCALHOST = "localhost";

    /** Constant for IIOP protocol. */
    final String PROTOCOL_IIOP = "iiop";

    /** Constant for JNP protocol. */
    final String PROTOCOL_JNP = "jnp";

}
