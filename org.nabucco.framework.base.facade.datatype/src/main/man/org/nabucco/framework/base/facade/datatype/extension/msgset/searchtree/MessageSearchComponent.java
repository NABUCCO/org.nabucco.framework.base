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
package org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree;

/**
 * MessageSearchComponent
 * <p/>
 * Super node of all message search tree components.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public interface MessageSearchComponent {

    /**
     * Getter for the component id.
     * 
     * @return the id of this component
     */
    String getId();

    /**
     * Getter for the parent component.
     * 
     * @return the parent component
     */
    MessageSearchComponent getParent();

    /**
     * Add a component to the child components.
     * 
     * @param component
     *            the component to add
     */
    void add(MessageSearchComponent component);

    /**
     * Getter for all child components.
     * 
     * @return the child components
     */
    MessageSearchComponent[] getComponents();

    /**
     * Getter for the child component with the given id.
     * 
     * @param id
     *            the id of the child component
     * 
     * @return the child component
     */
    MessageSearchComponent get(String id);
}
