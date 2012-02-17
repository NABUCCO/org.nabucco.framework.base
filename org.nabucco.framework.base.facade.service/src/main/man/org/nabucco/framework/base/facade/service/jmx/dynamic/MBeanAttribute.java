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

import javax.management.MBeanAttributeInfo;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.jmx.MBeanException;
import org.nabucco.framework.base.facade.service.jmx.MBeanReflectionException;

/**
 * MBeanAttribute
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MBeanAttribute extends MBeanDescriptorSupport {

    private final String name;

    private final String type;

    private final String description;

    private final MBeanOperation getter;

    private final MBeanOperation setter;

    private static final String PREFIX_GETTER = "get";

    private static final String PREFIX_SETTER = "set";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(MBeanAttribute.class);

    /**
     * Creates a new {@link MBeanAttribute} instance.
     * 
     * @param name
     *            the attribute name
     * @param type
     *            the attribute type
     * @param description
     *            the attribute description
     * @param mbeanClass
     *            the mbean class
     * @param readOnly
     *            whether the attribute is read only or not
     * 
     * @throws MBeanReflectionException
     *             when the mbean attribute cannot be created
     */
    MBeanAttribute(String name, String type, String description, Class<?> mbeanClass, boolean readOnly)
            throws MBeanReflectionException {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create MBeanAttribute for attribute name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create MBeanAttribute for attribute type [null].");
        }
        if (mbeanClass == null) {
            throw new IllegalArgumentException("Cannot create MBeanAttribute for class [null].");
        }

        this.name = name;
        this.type = type;
        this.description = description;

        String getterName = this.toOperationName(name, PREFIX_GETTER);
        this.getter = new MBeanOperation(getterName, type, mbeanClass);

        if (readOnly) {
            this.setter = null;
        } else {
            String setterName = this.toOperationName(name, PREFIX_SETTER);
            this.setter = new MBeanOperation(setterName, type, mbeanClass);
        }
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
     * Check whether the attribute is read only or not.
     * 
     * @return <b>true</b> if the attribute is readonly, <b>false</b> if not
     */
    public boolean isReadOnly() {
        return this.setter == null;
    }

    /**
     * Getter for the mbean attributes value.
     * 
     * @param instance
     *            the mbean instance
     * 
     * @return the mbean attribute value
     * 
     * @throws MBeanReflectionException
     *             when the getter is not accessible
     * @throws MBeanException
     *             when the getter raised an exception
     */
    public Object getValue(Object instance) throws MBeanReflectionException, MBeanException {
        return this.getter.invoke(instance);
    }

    /**
     * Getter for the mbean attributes value.
     * 
     * @param instance
     *            the mbean instance
     * @param value
     *            the mbean attribute value
     * 
     * @throws MBeanReflectionException
     *             when the setter is not accessible
     * @throws MBeanException
     *             when the setter raised an exception
     */
    public void setValue(Object instance, Object value) throws MBeanReflectionException, MBeanException {
        if (this.setter != null) {
            this.setter.invoke(instance, value);
        } else {
            logger.warning("MBean Attribute '", this.name, "' is readonly.");
        }
    }

    /**
     * Convert the attribute name to the operation name.
     * 
     * @param attributeName
     *            the attribute name
     * @param prefix
     *            the appropriate getter, setter prefix
     * 
     * @return the operation name
     */
    private String toOperationName(String attributeName, String prefix) {
        if (attributeName == null || attributeName.isEmpty()) {
            return prefix;
        }

        StringBuilder name = new StringBuilder();
        name.append(prefix);
        name.append(Character.toUpperCase(attributeName.charAt(0)));
        name.append(attributeName.substring(1));

        return name.toString();
    }

    /**
     * Getter for the attribute information.
     * 
     * @return the mbean attribute info
     */
    public MBeanAttributeInfo getInfo() {
        boolean isReadable = this.getter != null;
        boolean isWritable = this.setter != null;
        return new MBeanAttributeInfo(this.name, this.type, this.description, isReadable, isWritable, false);
    }

}
