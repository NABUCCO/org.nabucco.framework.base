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
package org.nabucco.framework.base.facade.datatype.property;

import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * EnumProperty
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class EnumProperty<N extends Enumeration> extends SingleProperty<N> implements
        NabuccoProperty<N> {

    /**
     * Creates a new {@link EnumProperty} instance.
     * 
     * @param name
     *            the enum name
     * @param type
     *            the enum type
     * @param constraints
     *            the property constraint string
     * @param instance
     *            the enum instance
     */
    public EnumProperty(String name, Class<N> type, String constraints, N instance) {
        super(name, type, constraints, PropertyType.ENUMERATION, instance);
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        // Enum Properties cannot be visited!
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        return Collections.emptyList();
    }

}
