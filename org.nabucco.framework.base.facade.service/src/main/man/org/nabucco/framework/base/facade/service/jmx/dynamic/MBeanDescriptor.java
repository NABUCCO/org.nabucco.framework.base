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

import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;

import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementArgument;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementAttribute;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementOperation;
import org.nabucco.framework.base.facade.service.jmx.MBeanReflectionException;

/**
 * MBeanDescriptor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MBeanDescriptor extends MBeanDescriptorSupport {

    private final ManagementExtension extension;

    private String description;

    private Class<?> mbeanClass;

    private List<MBeanAttribute> attributes = new ArrayList<MBeanAttribute>();

    private List<MBeanOperation> operations = new ArrayList<MBeanOperation>();

    /**
     * Creates a new {@link MBeanDescriptor} instance.
     * 
     * @param extension
     *            the org.nabucco.management extension
     * 
     * @throws MBeanException
     *             when the mbean descriptor cannot be initialized
     */
    public MBeanDescriptor(ManagementExtension extension) throws MBeanReflectionException {
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create MBean Descriptor for ManagementExtension [null].");
        }

        this.extension = extension;
        this.init(extension);
    }

    /**
     * Initialize the management bean descriptor.
     * 
     * @param extension
     *            the management extension
     * 
     * @throws MBeanReflectionException
     *             when the descriptor is not configured correclty
     */
    private void init(ManagementExtension extension) throws MBeanReflectionException {
        this.mbeanClass = super.loadClass(extension.getBeanClass());
        this.description = super.getValue(extension.getDescription());

        for (ManagementAttribute attribute : this.extension.getAttributes()) {
            this.attributes.add(this.createAttribute(attribute));
        }

        for (ManagementOperation operation : this.extension.getOperations()) {
            this.operations.add(this.createOperation(operation));
        }
    }

    /**
     * Create an MBeanAttribute instance from the configured extension.
     * 
     * @param attribute
     *            the management attribute extension
     * 
     * @return the mbean attribute
     * 
     * @throws MBeanReflectionException
     *             when the attribute is not configured correctly
     */
    private MBeanAttribute createAttribute(ManagementAttribute attribute) throws MBeanReflectionException {
        String name = super.getValue(attribute.getName());
        String description = super.getValue(attribute.getDescription());
        String type = super.getValue(attribute.getType());
        boolean readOnly = super.getValue(attribute.getReadOnly());
        return new MBeanAttribute(name, type, description, this.mbeanClass, readOnly);
    }

    /**
     * Create an MBeanOperation instance from the configured extension.
     * 
     * @param attribute
     *            the management operation extension
     * 
     * @return the mbean operation
     * 
     * @throws MBeanReflectionException
     *             when the operation is not configured correctly
     */
    private MBeanOperation createOperation(ManagementOperation operation) throws MBeanReflectionException {
        String name = super.getValue(operation.getName());
        String returnType = super.getValue(operation.getReturnType());
        String description = super.getValue(operation.getDescription());
        String actionType = super.getValue(operation.getActionType());

        NabuccoList<ManagementArgument> arguments = operation.getArguments();
        MBeanArgument[] signature = new MBeanArgument[arguments.size()];
        for (int i = 0; i < signature.length; i++) {
            String argumentName = super.getValue(arguments.get(i).getName());
            String argumentType = super.getValue(arguments.get(i).getType());
            String argumentDescription = super.getValue(arguments.get(i).getDescription());
            signature[i] = new MBeanArgument(argumentName, argumentType, argumentDescription);
        }

        return new MBeanOperation(name, returnType, description, actionType, this.mbeanClass, signature);
    }

    /**
     * Getter for the canonical class name of the mbean.
     * 
     * @return the name as string
     */
    public String getName() {
        return this.mbeanClass.getCanonicalName();
    }

    /**
     * Getter for the mbeanClass.
     * 
     * @return Returns the mbeanClass.
     */
    public Class<?> getMbeanClass() {
        return this.mbeanClass;
    }

    /**
     * Getter for the attributes.
     * 
     * @return Returns the attributes.
     */
    public List<MBeanAttribute> getAttributes() {
        return new ArrayList<MBeanAttribute>(this.attributes);
    }

    /**
     * Getter for the attribute with the given name.
     * 
     * @return Returns the attribute.
     */
    public MBeanAttribute getAttribute(String name) {
        for (MBeanAttribute attribute : this.attributes) {
            if (attribute.getName().equals(name)) {
                return attribute;
            }
        }

        return null;
    }

    /**
     * Getter for the operations.
     * 
     * @return Returns the operations.
     */
    public List<MBeanOperation> getOperations() {
        return new ArrayList<MBeanOperation>(this.operations);
    }

    /**
     * Getter for the attribute with the given name.
     * 
     * @param name
     *            the operation name
     * @param arguments
     *            the operation arguments
     * 
     * @return Returns the attribute.
     */
    public MBeanOperation getOperation(String name, String... arguments) {
        for (MBeanOperation operation : this.operations) {
            if (operation.getName().equals(name)) {

                int length = operation.getArguments().length;
                if (length != arguments.length) {
                    break;
                }

                boolean signatureMatches = true;
                for (int i = 0; i < length; i++) {
                    if (operation.getArguments()[i].equals(arguments[i])) {
                        signatureMatches = false;
                        break;
                    }
                }

                if (signatureMatches) {
                    return operation;
                }
            }
        }

        return null;
    }

    /**
     * Getter for the attribute information.
     * 
     * @return the mbean attribute info
     */
    public MBeanInfo getInfo() {

        MBeanAttributeInfo[] attributes = new MBeanAttributeInfo[this.attributes.size()];
        for (int i = 0; i < this.attributes.size(); i++) {
            attributes[i] = this.attributes.get(i).getInfo();
        }

        MBeanOperationInfo[] operations = new MBeanOperationInfo[this.operations.size()];
        for (int i = 0; i < this.operations.size(); i++) {
            operations[i] = this.operations.get(i).getInfo();
        }

        MBeanConstructorInfo[] constructors = new MBeanConstructorInfo[0];
        MBeanNotificationInfo[] notifications = new MBeanNotificationInfo[0];

        return new MBeanInfo(this.getName(), this.description, attributes, constructors, operations, notifications);
    }
}
