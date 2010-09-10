/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.visitor;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * Visitable
 * <p/>
 * Marks a class for getting visited by a {@link Visitor} instance.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Visitable extends NType {

    /**
     * Accepts a {@link Visitor} instance.
     * 
     * @param visitor
     *            the visitor which is visiting
     * 
     * @throws VisitorException
     *             if an error occurs during the visitation
     */
    void accept(Visitor visitor) throws VisitorException;

    /**
     * Returns the visitable properties of a Visitable.
     * 
     * @return all visitable properties
     */
    Object[] getProperties();

    /**
     * Returns the property names of a Visitable according to {@link Visitable#getProperties()}.
     * 
     * @return all visitable property names
     */
    String[] getPropertyNames();
}
