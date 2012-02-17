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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.adapter.Adapter;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * LookupUtility
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class LookupUtility {

    private static final String GET_INSTANCE = "getInstance";

    private static final String JNDI_NAME = "JNDI_NAME";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(LookupUtility.class);

    /**
     * Private constructor must not be invoked.
     */
    private LookupUtility() {
    }

    /**
     * Locate an adapter from JNDI.
     * 
     * @param <A>
     *            the adater type
     * @param adapterClass
     *            the adapter class
     * @param jndi
     *            the jndi name
     * @param connection
     *            the connection to use for remote connection or null for local connection
     * 
     * @return the located adapter
     * 
     * @throws ConnectionException
     *             when the adapter cannot be located
     */
    public static <A extends Adapter> A getAdapter(Class<A> adapterClass, String jndi, Connection connection)
            throws ConnectionException {
        LocatorSupport<A> locator = new LocatorSupport<A>(jndi, adapterClass);

        if (connection == null) {
            return locator.lookup();
        }
        return locator.lookup(connection);
    }

    /**
     * Looks up and returns the adapter for a specific connection. This lookup only returns EJBs
     * with Remote Interfaces.
     * 
     * @param locatorClass
     *            the adapter locator class
     * @param connection
     *            the connection to use for remote connection or null for local connection
     * 
     * @return the remote adapter EJB
     * 
     * @throws ConnectionException
     *             when the connection cannot be established
     */
    public static <A extends Adapter, L extends AdapterLocator<A>> A getAdapter(Class<L> locatorClass,
            Connection connection) throws ConnectionException {
        if (locatorClass == null) {
            throw new IllegalArgumentException("Cannot locate adapter from locator [null].");
        }

        try {
            Method getInstance = locatorClass.getMethod(GET_INSTANCE);

            @SuppressWarnings("unchecked")
            L locator = (L) getInstance.invoke(locatorClass);

            if (connection == null) {
                return locator.getAdapter();
            }
            return locator.getAdapter(connection);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error retrieving adapter locator instance '", locatorClass.getSimpleName(), "'.");
            throw new ConnectionException("Error retrieving adapter locator instance '"
                    + locatorClass.getSimpleName() + "'.", e);
        }
    }

    /**
     * Locate a local component from JNDI. The JNDI Name is extracted form the component interface.
     * 
     * @param <C>
     *            the component type
     * @param componentName
     *            the full qualified name of the component
     * 
     * @return the located component
     * 
     * @throws ConnectionException
     *             when the component cannot be located
     */
    @SuppressWarnings("unchecked")
    public static <C extends Component> C getComponentLocal(String componentName) throws ConnectionException {

        try {
            Class<C> componentClass = (Class<C>) Class.forName(componentName);
            return getComponentLocal(componentClass);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "No Component with name '", componentName, "' available.");
            throw new ConnectionException("No Component with name '" + componentName + "' available.", e);
        }
    }

    /**
     * Locate a local component from JNDI. The JNDI Name is extracted form the component interface.
     * 
     * @param <C>
     *            the component type
     * @param componentClass
     *            the component class
     * 
     * @return the located component
     * 
     * @throws ConnectionException
     *             when the component cannot be located
     */
    public static <C extends Component> C getComponentLocal(Class<C> componentClass) throws ConnectionException {

        try {
            Field field = componentClass.getField(JNDI_NAME);
            String jndi = (String) field.get(componentClass);

            return getComponentLocal(componentClass, jndi);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error retrieving component instance '", componentClass.getSimpleName(), "'.");
            throw new ConnectionException("Error retrieving component instance '"
                    + componentClass.getSimpleName() + "'.", e);
        }
    }

    /**
     * Locate a local component from JNDI.
     * 
     * @param <C>
     *            the component type
     * @param componentClass
     *            the component class
     * @param jndi
     *            the jndi name
     * 
     * @return the located component
     * 
     * @throws ConnectionException
     *             when the component cannot be located
     */
    public static <C extends Component> C getComponentLocal(Class<C> componentClass, String jndi)
            throws ConnectionException {

        try {
            return getComponent(componentClass, jndi, null);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error retrieving component instance '", componentClass.getSimpleName(), "'.");
            throw new ConnectionException("Error retrieving component instance '"
                    + componentClass.getSimpleName() + "'.", e);
        }
    }

    /**
     * Looks up and returns the component for a local connection.
     * 
     * @param locatorClass
     *            the component locator class
     * 
     * @return the component EJB
     * 
     * @throws ConnectionException
     *             when the connection cannot be established
     */
    public static <C extends Component, L extends ComponentLocator<C>> C getComponentLocalByLocator(
            Class<L> locatorClass) throws ConnectionException {

        try {
            return getComponentByLocator(locatorClass, null);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error retrieving component locator instance '", locatorClass.getSimpleName(), "'.");
            throw new ConnectionException("Error retrieving component locator instance '"
                    + locatorClass.getSimpleName() + "'.", e);
        }
    }

    /**
     * Locate a component from JNDI.
     * 
     * @param <C>
     *            the component type
     * @param componentName
     *            the full qualified name of the component
     * @param connection
     *            the connection to use for remote connection or null for local connection
     * 
     * @return the located component
     * 
     * @throws ConnectionException
     *             when the component cannot be located
     */
    @SuppressWarnings("unchecked")
    public static <C extends Component> C getComponent(String componentName, Connection connection)
            throws ConnectionException {

        try {
            Class<C> componentClass = (Class<C>) Class.forName(componentName);
            return getComponent(componentClass, connection);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "No Component with name '", componentName, "' available.");
            throw new ConnectionException("No Component with name '" + componentName + "' available.", e);
        }
    }

    /**
     * Locate a component from JNDI.
     * 
     * @param <C>
     *            the component type
     * @param componentClass
     *            the component class
     * @param connection
     *            the connection to use for remote connection or null for local connection
     * 
     * @return the located component
     * 
     * @throws ConnectionException
     *             when the component cannot be located
     */
    public static <C extends Component> C getComponent(Class<C> componentClass, Connection connection)
            throws ConnectionException {

        try {
            Field field = componentClass.getField(JNDI_NAME);
            String jndi = (String) field.get(componentClass);

            return getComponent(componentClass, jndi, connection);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error retrieving component instance '", componentClass.getSimpleName(), "'.");
            throw new ConnectionException("Error retrieving component instance '"
                    + componentClass.getSimpleName() + "'.", e);
        }
    }

    /**
     * Locate a component from JNDI.
     * 
     * @param <C>
     *            the component type
     * @param adapterClass
     *            the component class
     * @param connection
     *            the connection to use for remote connection or null for local connection
     * @param jndi
     *            the jndi name
     * 
     * @return the located component
     * 
     * @throws ConnectionException
     *             when the component cannot be located
     */
    public static <C extends Component> C getComponent(Class<C> componentClass, String jndi, Connection connection)
            throws ConnectionException {

        if (!Component.class.isAssignableFrom(componentClass)) {
            throw new IllegalArgumentException("Class '" + componentClass.getName() + "' is not of type Component.");
        }

        try {
            LocatorSupport<C> locator = new LocatorSupport<C>(jndi, componentClass);

            if (connection == null) {
                return locator.lookup();
            }

            return locator.lookup(connection);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error retrieving component instance '", componentClass.getSimpleName(), "'.");
            throw new ConnectionException("Error retrieving component instance '"
                    + componentClass.getSimpleName() + "'.", e);
        }
    }

    /**
     * Looks up and returns the component for a connection.
     * 
     * @param locatorClass
     *            the component locator class
     * @param connection
     *            the connection to use for remote connection or null for local connection
     * 
     * @return the component EJB
     * 
     * @throws ConnectionException
     *             when the connection cannot be established
     */
    public static <C extends Component, L extends ComponentLocator<C>> C getComponentByLocator(Class<L> locatorClass,
            Connection connection) throws ConnectionException {

        if (!ComponentLocator.class.isAssignableFrom(locatorClass)) {
            throw new IllegalArgumentException("Cannot locate adapter from locator [null].");
        }

        try {
            Method getInstance = locatorClass.getMethod(GET_INSTANCE);

            @SuppressWarnings("unchecked")
            L locator = (L) getInstance.invoke(locatorClass);

            if (connection == null) {
                return locator.getComponent();
            }
            return locator.getComponent(connection);

        } catch (ConnectionException ce) {
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error retrieving component locator instance '", locatorClass.getSimpleName(), "'.");
            throw new ConnectionException("Error retrieving component locator instance '"
                    + locatorClass.getSimpleName() + "'.", e);
        }
    }

}
