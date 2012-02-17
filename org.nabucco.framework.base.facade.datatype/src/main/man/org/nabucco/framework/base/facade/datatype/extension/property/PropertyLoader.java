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
package org.nabucco.framework.base.facade.datatype.extension.property;

import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * PropertyLoader
 * <p/>
 * Utility for loading extension property values.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PropertyLoader {

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(PropertyLoader.class);

    /**
     * Private constructor must not be invoked.
     */
    private PropertyLoader() {
    }

    /**
     * Load the string of a configured extension id property.
     * 
     * @param id
     *            the extension id to load
     * 
     * @return the loaded identifier string
     */
    public static String loadProperty(ExtensionId id) {
        if (id == null || id.getValue() == null) {
            return null;
        }

        return id.getValue();
    }

    /**
     * Load the boolean of a configured extension boolean property.
     * 
     * @param property
     *            the boolean property to load
     * 
     * @return the loaded boolean
     */
    public static Boolean loadProperty(BooleanProperty property) {
        if (property == null || property.getValue() == null) {
            return false;
        }

        Boolean value = property.getValue().getValue();

        if (value == null) {
            return false;
        }

        return value;
    }

    /**
     * Load the string of a configured extension string property.
     * 
     * @param property
     *            the string property to load
     * 
     * @return the loaded string
     */
    public static String loadProperty(StringProperty property) {
        if (property == null || property.getValue() == null) {
            return null;
        }

        return property.getValue().getValue();
    }

    /**
     * Load the number of a configured extension integer property.
     * 
     * @param property
     *            the integer property to load
     * 
     * @return the loaded integer
     */
    public static Integer loadProperty(IntegerProperty property) {
        if (property == null || property.getValue() == null) {
            return null;
        }

        return property.getValue().getValue();
    }

    /**
     * Load the string of a configured extension enum property.
     * 
     * @param property
     *            the enum property to load
     * 
     * @return the loaded string
     */
    public static String loadProperty(EnumerationProperty property) {
        if (property == null || property.getValue() == null) {
            return null;
        }

        return property.getValue().getValue();
    }

    /**
     * Load the enum literal of a configured extension enumeration property.
     * 
     * @param property
     *            the enumeration property to load
     * 
     * @return the loaded enum literal
     */
    public static <E extends Enum<E>> E loadProperty(Class<E> enumType, EnumerationProperty property) {
        if (enumType == null) {
            logger.warning("Cannot resolve enum property for type [null].");
            return null;
        }
        if (property == null || property.getValue() == null || property.getValue().getValue() == null) {
            logger.warning("Cannot resolve literal [null] for enum property ", enumType.getCanonicalName(), ".");
            return null;
        }

        String literal = property.getValue().getValue().toUpperCase();

        if (literal.isEmpty()) {
            logger.warning("Cannot resolve literal [] for enum property ", enumType.getCanonicalName(), ".");
            return null;
        }

        try {
            return Enum.valueOf(enumType, literal);
        } catch (Exception e) {
            logger.error(e, "Cannot resolve [", literal, "] enum property ", enumType.getCanonicalName(), ".");
            return null;
        }
    }

    /**
     * Load the class of a configured extension class property.
     * 
     * @param property
     *            the class property to load
     * 
     * @return the loaded class
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadProperty(ClassProperty property) {
        if (property == null || property.getValue() == null) {
            return null;
        }

        String className = property.getValue().getValue();

        if (className == null || className.isEmpty()) {
            logger.warning("Cannot load configured property class 'null'.");
            return null;
        }

        try {
            return (Class<T>) PropertyLoader.class.getClassLoader().loadClass(className);
        } catch (ClassNotFoundException cnfe) {
            logger.error(cnfe, "Cannot find configured property class '", className, "'.");
            return null;
        } catch (ClassCastException cce) {
            logger.error(cce, "Cannot cast configured property class '", className, "' to the specified type.");
            return null;
        }
    }

}
