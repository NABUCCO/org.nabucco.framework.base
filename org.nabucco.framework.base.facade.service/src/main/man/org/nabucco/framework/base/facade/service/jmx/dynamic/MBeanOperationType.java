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

import javax.management.MBeanOperationInfo;

/**
 * MBeanOperationType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum MBeanOperationType {

    INFO(MBeanOperationInfo.INFO),

    ACTION(MBeanOperationInfo.ACTION),

    ACTION_INFO(MBeanOperationInfo.ACTION_INFO),

    UNKNOWN(MBeanOperationInfo.UNKNOWN);

    private final int value;

    /**
     * Creates a new {@link MBeanOperationType} instance.
     * 
     * @param value
     *            the action value
     */
    private MBeanOperationType(int value) {
        this.value = value;
    }

    /**
     * Getter for the value.
     * 
     * @return Returns the value.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Resolve the operation type from the given string.
     * 
     * @param value
     *            the string value to resolve
     * 
     * @return the resolved operation type
     */
    public static MBeanOperationType getType(String value) {
        if (value == null) {
            return MBeanOperationType.UNKNOWN;
        }

        for (MBeanOperationType type : MBeanOperationType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }

        return MBeanOperationType.UNKNOWN;
    }
}
