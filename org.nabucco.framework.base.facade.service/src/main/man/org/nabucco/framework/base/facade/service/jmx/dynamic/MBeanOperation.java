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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import org.nabucco.framework.base.facade.service.jmx.MBeanException;
import org.nabucco.framework.base.facade.service.jmx.MBeanReflectionException;

/**
 * MBeanOperation
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MBeanOperation extends MBeanDescriptorSupport {

    private final String name;

    private final String returnType;

    private final String description;

    private final MBeanOperationType actionType;

    private final MBeanArgument[] arguments;

    private final Method method;

    private static final String VOID = "void";

    /**
     * Creates a new {@link MBeanOperation} instance.
     * 
     * @param name
     *            the operation name
     * @param type
     *            the operation return type
     * @param mbeanClass
     *            the mbean class
     * @param arguments
     *            the operation arguments
     * 
     * @throws MBeanReflectionException
     *             when the mbean method cannot be resolved from the class
     */
    MBeanOperation(String name, String type, Class<?> mbeanClass, MBeanArgument... arguments)
            throws MBeanReflectionException {
        this(name, type, null, null, mbeanClass, arguments);
    }

    /**
     * Creates a new {@link MBeanOperation} instance.
     * 
     * @param name
     *            the operation name
     * @param type
     *            the operation return type
     * @param description
     *            the operation description
     * @param mbeanClass
     *            the mbean class
     * @param arguments
     *            the operation arguments
     * 
     * @throws MBeanReflectionException
     *             when the mbean method cannot be resolved from the class
     */
    MBeanOperation(String name, String type, String description, String actionType, Class<?> mbeanClass,
            MBeanArgument... arguments) throws MBeanReflectionException {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create MBeanOperation for operation name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create MBeanOperation for operation type [null].");
        }
        if (mbeanClass == null) {
            throw new IllegalArgumentException("Cannot create MBeanOperation for class [null].");
        }

        this.name = name;
        this.actionType = MBeanOperationType.getType(actionType);
        this.returnType = type.isEmpty() ? VOID : type;
        this.description = description;
        this.arguments = arguments;

        this.method = this.getMethod(mbeanClass);
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the arguments.
     * 
     * @return Returns the arguments.
     */
    public MBeanArgument[] getArguments() {
        return this.arguments;
    }

    /**
     * Resolves the method of the given mbean class.
     * 
     * @param mbeanClass
     *            the mbean class holding the operation
     * 
     * @throws MBeanReflectionException
     *             when the method or method parameter cannot be found
     */
    private Method getMethod(Class<?> mbeanClass) throws MBeanReflectionException {

        try {
            Class<?>[] parameterTypes = new Class[this.arguments.length];

            for (int i = 0; i < this.arguments.length; i++) {
                String argument = this.arguments[i].getType();
                parameterTypes[i] = super.loadClass(argument);
            }

            return mbeanClass.getMethod(this.name, parameterTypes);

        } catch (NoSuchMethodException nsme) {
            throw new MBeanReflectionException("Cannot find method '"
                    + this.name + "' in mbean " + mbeanClass.getSimpleName() + ".", nsme);
        } catch (Exception e) {
            throw new MBeanReflectionException("Cannot resolve method '"
                    + this.name + "(" + this.arguments() + ")' in mbean " + mbeanClass.getSimpleName() + ".", e);
        }
    }

    /**
     * Invoke the mbean operation.
     * 
     * @param instance
     *            the mbean instance
     * @param arguments
     *            the arguments
     * 
     * @return the invocation result
     * 
     * @throws MBeanReflectionException
     *             when the mbean operation cannot be invoked successfully
     * @throws MBeanException
     *             when the mbean operation raised an exception
     */
    public Object invoke(Object instance, Object... arguments) throws MBeanReflectionException, MBeanException {
        try {
            return this.method.invoke(instance, arguments);
        } catch (IllegalAccessException iae) {
            throw new MBeanReflectionException("The defined MBean operation '" + this.name + "' is not accessable.",
                    iae);
        } catch (IllegalArgumentException iae) {
            throw new MBeanReflectionException(
                    "Given MBean arguments does not match the configured operation signature.", iae);
        } catch (InvocationTargetException ite) {
            throw new MBeanException("The called MBean operation '" + this.name + "' raised an exception.", ite);
        } catch (Exception e) {
            throw new MBeanException("Unexpected error during invocation of MBean Operation '" + this.name + "'.", e);
        }
    }

    /**
     * Print the arguments as comma separated values.
     * 
     * @return the arguments as string
     */
    private String arguments() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < this.arguments.length; i++) {
            result.append(this.arguments[i].getType());
            if (i < this.arguments.length) {
                result.append(", ");
            }
        }

        return result.toString();
    }

    /**
     * Getter for the attribute information.
     * 
     * @return the mbean attribute info
     */
    public MBeanOperationInfo getInfo() {
        MBeanParameterInfo[] paramInfos = new MBeanParameterInfo[this.arguments.length];
        for (int i = 0; i < paramInfos.length; i++) {
            MBeanArgument argument = this.arguments[i];
            paramInfos[i] = new MBeanParameterInfo(argument.getName(), argument.getType(), argument.getDescription());
        }

        return new MBeanOperationInfo(this.name, this.description, paramInfos, this.returnType,
                this.actionType.getValue());
    }
}
