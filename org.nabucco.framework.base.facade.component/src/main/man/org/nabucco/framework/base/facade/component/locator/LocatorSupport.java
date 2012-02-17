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
package org.nabucco.framework.base.facade.component.locator;

import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionSupport;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * LookupConnection
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class LocatorSupport<L extends Locatable> extends ConnectionSupport<L> {

    /** Suffix for remote EJBs. */
    private static final String SUFFIX_REMOTE = "/remote";

    /** Suffix for local EJBs. */
    private static final String SUFFIX_LOCAL = "/local";

    /** The locatable JNDI name. */
    private final String jndiName;

    /** The locatable class. */
    private final Class<L> locatableClass;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(LocatorSupport.class);

    /**
     * Creates a new {@link LookupConnection} instance.
     * 
     * @param jndiName
     *            the JNDI nam
     * @param locatableClass
     *            the class to locate
     */
    protected LocatorSupport(String jndiName, Class<L> locatableClass) {
        if (jndiName == null) {
            throw new IllegalArgumentException("Cannot create locator for JNDI [null].");
        }
        if (locatableClass == null) {
            throw new IllegalArgumentException("Cannot create locator for class [null].");
        }
        this.locatableClass = locatableClass;
        this.jndiName = jndiName;
    }

    /**
     * Lookup a local locatable from JNDI.
     * 
     * @return the looked-up locatable
     * 
     * @throws ConnectionException
     *             when the locatable cannot be found in the JNDI tree
     */
    public L lookup() throws ConnectionException {
        String jndiName = this.jndiName + SUFFIX_LOCAL;

        if (logger.isDebugEnabled()) {
            logger.debug("Try locating local '", this.locatableClass.getSimpleName(), "' from JNDI '", jndiName, "'.");
        }

        try {
            return super.lookupLocatable(jndiName, this.locatableClass);
        } catch (ConnectionException ce) {
            logger.error("Error locating local '", this.locatableClass.getCanonicalName(), "' from JNDI '", jndiName,
                    "'.");
            throw ce;
        } catch (Exception e) {
            logger.error("Error locating local '", locatableClass.getCanonicalName(), "' from JNDI '", jndiName, "'.");
            throw new ConnectionException("Error locating local "
                    + this.locatableClass.getCanonicalName() + " from JNDI '" + jndiName + ".", e);
        }
    }

    /**
     * Lookup a remote locatable from JNDI.
     * 
     * @return the looked-up locatable
     * 
     * @throws ConnectionException
     *             when the locatable cannot be found in the JNDI tree
     */
    public L lookup(Connection connection) throws ConnectionException {

        if (connection == null) {
            throw new IllegalArgumentException("Connection is not established [null].");
        }

        String jndiName = this.jndiName + SUFFIX_REMOTE;

        if (logger.isDebugEnabled()) {
            logger.debug("Try locating remote '", this.locatableClass.getSimpleName(), "' from JNDI '", jndiName, "'.");
        }

        try {
            return super.lookupLocatable(connection, jndiName, this.locatableClass);
        } catch (ConnectionException ce) {
            logger.error("Error locating remote '", this.locatableClass.getCanonicalName(), "' from JNDI '", jndiName,
                    "'.");
            throw ce;
        } catch (Exception e) {
            logger.error("Error locating remote '", this.locatableClass.getCanonicalName(), "' from JNDI '", jndiName,
                    "'.");
            throw new ConnectionException("Error locating remote "
                    + this.locatableClass.getCanonicalName() + " from JNDI '" + jndiName + ".", e);
        }
    }

}
