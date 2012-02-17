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

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;

import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.jmx.MBean;
import org.nabucco.framework.base.facade.service.jmx.MBeanReflectionException;

/**
 * MBeanWrapper
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MBeanWrapper implements DynamicMBean, MBean {

    private final MBeanDescriptor descriptor;

    private Object instance;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(MBeanWrapper.class);

    /**
     * Creates a new {@link MBeanWrapper} instance.
     * 
     * @param extension
     *            the management extension
     * 
     * @throws MBeanReflectionException
     *             when the configured attributes/operations does not fit to the MBean
     */
    public MBeanWrapper(ManagementExtension extension) throws MBeanReflectionException {
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create MBean for management extension [null].");
        }

        this.descriptor = new MBeanDescriptor(extension);

        try {
            this.instance = this.descriptor.getMbeanClass().newInstance();
        } catch (InstantiationException ie) {
            throw new MBeanReflectionException("The configured MBean class '"
                    + this.descriptor.getName() + "' cannot be instantiated.", ie);
        } catch (IllegalAccessException iae) {
            throw new MBeanReflectionException("The configured MBean class '"
                    + this.descriptor.getName() + "' is not accessable.", iae);
        } catch (Exception e) {
            throw new MBeanReflectionException("Unexpected error in initialization of MBean '"
                    + this.descriptor.getName() + "'.", e);
        }
    }

    @Override
    public Object getAttribute(String name) throws AttributeNotFoundException, MBeanException, ReflectionException {
        try {
            return this.findAttribute(name).getValue(this.instance);
        } catch (MBeanReflectionException re) {
            logger.error(re, "Error obtaining attribute value '", name, "' of MBean '", this.descriptor.getName(), "'.");
            throw new ReflectionException(re, "Error obtaining attribute value '"
                    + name + "' of MBean '" + this.descriptor.getName() + "'.");
        } catch (Exception e) {
            logger.error(e, "Error obtaining attribute value '", name, "' of MBean '", this.descriptor.getName(), "'.");
            throw new MBeanException(e, "Error obtaining attribute value '"
                    + name + "' of MBean '" + this.descriptor.getName() + "'.");
        }
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException,
            MBeanException, ReflectionException {

        String name = attribute.getName();
        Object value = attribute.getValue();

        try {
            this.findAttribute(name).setValue(this.instance, value);
        } catch (MBeanReflectionException re) {
            logger.error(re, "Error setting attribute value '", name, "' of MBean '", this.descriptor.getName(), "'.");
            throw new ReflectionException(re, "Error setting attribute value '"
                    + name + "' of MBean '" + this.descriptor.getName() + "'.");
        } catch (Exception e) {
            logger.error(e, "Error setting attribute value '", name, "' of MBean '", this.descriptor.getName(), "'.");
            throw new MBeanException(e, "Error setting attribute value '"
                    + name + "' of MBean '" + this.descriptor.getName() + "'.");
        }
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {

        try {
            AttributeList attributeList = new AttributeList(this.descriptor.getAttributes().size());

            for (String attributeName : attributes) {
                MBeanAttribute attribute = this.descriptor.getAttribute(attributeName);
                if (attribute != null) {
                    attributeList.add(new Attribute(attribute.getName(), attribute.getValue(this.instance)));
                }
            }

            return attributeList;
        } catch (Exception e) {
            logger.error(e, "Error obtaining attributes for MBean '", this.descriptor.getName(), "'.");
        }

        return new AttributeList(0);
    }

    @Override
    public AttributeList setAttributes(AttributeList attributeList) {

        for (Attribute attribute : attributeList.asList()) {
            try {
                this.findAttribute(attribute.getName()).setValue(this.instance, attribute.getValue());
            } catch (Exception e) {
                logger.error(e, "Error setting attributes for MBean '", this.descriptor.getName(), "'.");
            }
        }

        return attributeList;
    }

    @Override
    public Object invoke(String name, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        try {
            return this.findOperation(name, signature).invoke(this.instance, params);
        } catch (Exception e) {
            logger.error(e, "Error invoking operation '", name, "' of MBean '", this.descriptor.getName(), "'.");
            throw new MBeanException(e, "Error invoking operation '"
                    + name + "' of MBean '" + this.descriptor.getName() + "'.");
        }
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return this.descriptor.getInfo();
    }

    /**
     * Loads the MBean attribute with the given name from the descriptor.
     * 
     * @param name
     *            the attribute name
     * 
     * @return the mbean attribute
     * 
     * @throws AttributeNotFoundException
     *             when the attribute cannot be found in the MBean
     */
    private MBeanAttribute findAttribute(String name) throws AttributeNotFoundException {
        MBeanAttribute attribute = this.descriptor.getAttribute(name);
        if (attribute == null) {
            throw new AttributeNotFoundException("The attribute '"
                    + name + "' does not exist in MBean " + this.descriptor.getName() + ".");
        }
        return attribute;
    }

    /**
     * Loads the MBean operation with the given name from the descriptor.
     * 
     * @param name
     *            the operation name
     * 
     * @return the mbean operation
     * 
     * @throws MBeanReflectionException
     *             when the operation cannot be found in the MBean
     */
    private MBeanOperation findOperation(String name, String... arguments) throws ReflectionException {
        MBeanOperation operation = this.descriptor.getOperation(name, arguments);
        if (operation == null) {
            throw new ReflectionException(new NoSuchMethodException("The operation '"
                    + name + "' does not exist in MBean " + this.descriptor.getName() + "."));
        }
        return operation;
    }

}
