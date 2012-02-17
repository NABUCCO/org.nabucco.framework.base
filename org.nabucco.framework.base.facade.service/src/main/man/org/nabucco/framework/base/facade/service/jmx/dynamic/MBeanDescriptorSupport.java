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
package org.nabucco.framework.base.facade.service.jmx.dynamic;

import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.service.jmx.MBeanReflectionException;

/**
 * MBeanDescriptorSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class MBeanDescriptorSupport {

    /**
     * Resolves the value of a class property.
     * 
     * @param property
     *            the class property
     * 
     * @return the class properties value, or null if none exists
     */
    protected String getValue(ClassProperty property) {
        if (property == null || property.getValue() == null) {
            return null;
        }

        return property.getValue().getValue();
    }

    /**
     * Resolves the value of a string property.
     * 
     * @param property
     *            the string property
     * 
     * @return the string properties value, or null if none exists
     */
    protected String getValue(StringProperty property) {
        if (property == null || property.getValue() == null) {
            return null;
        }

        return property.getValue().getValue();
    }

    /**
     * Resolves the value of an enumeration property.
     * 
     * @param property
     *            the enumeration property
     * 
     * @return the enumeration properties value, or null if none exists
     */
    protected String getValue(EnumerationProperty property) {
        if (property == null || property.getValue() == null) {
            return null;
        }

        return property.getValue().getValue();
    }

    /**
     * Resolves the value of a boolean property.
     * 
     * @param property
     *            the boolean property
     * 
     * @return the boolean properties value, or null if none exists
     */
    protected boolean getValue(BooleanProperty property) {
        if (property == null || property.getValue() == null) {
            return false;
        }

        return property.getValue().getValue();
    }

    /**
     * Load the given class from the classloader.
     * 
     * @param <T>
     *            the type of the class
     * @param type
     *            the class to load as string
     * 
     * @return the loaded class
     * 
     * @throws MBeanReflectionException
     *             when the class cannot be loaded
     */
    protected <T> Class<T> loadClass(ClassProperty property) throws MBeanReflectionException {
        if (property == null || property.getValue() == null || property.getValue().getValue() == null) {
            throw new MBeanReflectionException("Cannot load class from property [null].");
        }

        return this.loadClass(property.getValue().getValue());
    }

    /**
     * Load the given class from the classloader.
     * 
     * @param <T>
     *            the type of the class
     * @param type
     *            the class to load as string
     * 
     * @return the loaded class
     * 
     * @throws MBeanReflectionException
     *             when the class cannot be loaded
     */
    @SuppressWarnings("unchecked")
    protected <T> Class<T> loadClass(String type) throws MBeanReflectionException {
        try {
            return (Class<T>) MBeanWrapper.class.getClassLoader().loadClass(type);

        } catch (ClassNotFoundException cnfe) {
            throw new MBeanReflectionException("Cannot find class '" + type + "' in class loader.");
        } catch (Exception e) {
            throw new MBeanReflectionException("Cannot load class '" + type + "' from class loader.");
        }
    }
}
