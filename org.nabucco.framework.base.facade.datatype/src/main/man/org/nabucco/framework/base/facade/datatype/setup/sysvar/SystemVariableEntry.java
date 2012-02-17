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
package org.nabucco.framework.base.facade.datatype.setup.sysvar;

import java.util.List;

/**
 * SystemVariableEntry
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SystemVariableEntry {

    private SystemVariable actualValue;

    private List<SystemVariable> valueList;

    /**
     * Creates a new {@link SystemVariableEntry} instance.
     * 
     * @param valueList
     *            the variable list
     */
    SystemVariableEntry(List<SystemVariable> valueList) {
        this.valueList = valueList;
    }

    /**
     * Getter for the variable list.
     * 
     * @return the list
     */
    public List<SystemVariable> getList() {
        return this.valueList;
    }

    /**
     * Getter for the actual value.
     * 
     * @return the actual value
     */
    public SystemVariable getActualValue() {
        return this.actualValue;
    }

    /**
     * Setter for the actual value.
     * 
     * @param value
     *            the actual value
     */
    public void setActualValue(SystemVariable value) {
        this.actualValue = value;
    }

}
