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
package org.nabucco.framework.base.facade.service.injection;

import org.nabucco.framework.base.facade.service.jmx.MBean;

/**
 * InjectionProviderMBean
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface InjectionProviderMBean extends MBean {

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    String getId();

    /**
     * Getter for the fileName.
     * 
     * @return Returns the fileName.
     */
    String getFileName();

    /**
     * Getter for the injection implementation for the given name.
     * 
     * @param injectionId
     *            the injection id
     * @param implementation
     *            the full qualified class name of the injection implementation class
     */
    void addInjection(String injectionId, String implementation);

    /**
     * Getter for the injection implementation for the given name.
     * 
     * @param injectionId
     *            the injection id
     * 
     * @return the full qualified class name of the injection implementation
     */
    String getInjection(String injectionId);

    /**
     * Lists all injection properties.
     * 
     * @return the injection list
     */
    String listInjections();

    /**
     * Resets the injection provider. Properties are removed and MBean is unregistered.
     */
    void reset();

}
