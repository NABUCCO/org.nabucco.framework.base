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

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatatypeProperty
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class DatatypeProperty<N extends Datatype> extends SingleProperty<N> implements
        NabuccoProperty<N> {

    /**
     * Creates a new {@link DatatypeProperty} instance.
     * 
     * @param name
     *            the datatype name
     * @param type
     *            the datatype type
     * @param constraints
     *            the property constraint string
     * @param instance
     *            the datatype instance
     */
    public DatatypeProperty(String name, Class<N> type, String constraints, N instance) {
        super(name, type, constraints, PropertyType.DATATYPE, instance);
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        if (super.getInstance() != null) {
            super.getInstance().accept(visitor);
        }
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        if (super.getInstance() != null) {
            return super.getInstance().getProperties();
        }
        return Collections.emptyList();
    }

}
