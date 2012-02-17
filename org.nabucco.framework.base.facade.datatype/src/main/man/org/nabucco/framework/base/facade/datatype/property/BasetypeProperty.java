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
package org.nabucco.framework.base.facade.datatype.property;

import java.util.Collections;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * BasetypeProperty
 * <p/>
 * A property holding a single instance of a {@link Basetype}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class BasetypeProperty extends PropertySupport implements NabuccoProperty {

    /** The basetype instance. */
    private Basetype instance;

    /**
     * Creates a new {@link BasetypeProperty} instance.
     * 
     * @param descriptor
     *            the basetype descriptor
     * @param parent
     *            the parent property holder
     * @param instance
     *            the basetype instance
     * @param constraints
     *            the dynamic constraints
     */
    BasetypeProperty(NabuccoPropertyDescriptor descriptor, PropertyOwner parent, Basetype instance, String constraints) {
        super(descriptor, parent, constraints, null);

        this.instance = instance;
    }

    @Override
    public Basetype getInstance() {
        return this.instance;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        if (this.instance != null) {
            this.instance.accept(visitor);
        }
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        if (this.instance != null) {
            return this.instance.getProperties();
        }
        return Collections.emptySet();
    }

}
