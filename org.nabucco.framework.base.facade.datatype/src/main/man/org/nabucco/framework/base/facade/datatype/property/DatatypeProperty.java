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

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatatypeProperty
 * <p/>
 * A property holding a single instance of a {@link Datatype}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class DatatypeProperty extends PropertySupport implements NabuccoProperty {

    /** The datatype instance. */
    private Datatype instance;

    /**
     * Creates a new {@link DatatypeProperty} instance.
     * 
     * @param descriptor
     *            the property descriptor
     * @param parent
     *            the parent property holder
     * @param instance
     *            the datatype instance
     * @param constraints
     *            the dynamic constraints
     * @param refId
     *            the reference id
     */
    DatatypeProperty(NabuccoPropertyDescriptor descriptor, PropertyOwner parent, Datatype instance, String constraints,
            Long refId) {
        super(descriptor, parent, constraints, refId);

        this.instance = instance;
    }

    @Override
    public Datatype getInstance() {
        return this.instance;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        if (this.isAccessible()) {
            this.instance.accept(visitor);
        }
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        if (this.isAccessible()) {
            return this.instance.getProperties();
        }
        return Collections.emptySet();
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    /**
     * Check whether a datatype is a real NABUCCO datatype or only a reflection proxy. The original
     * generated NABUCCO datatypes have a direct interface realization of {@link Datatype}.
     * 
     * @param datatype
     *            the datatype to check for authenticity
     * 
     * @return <b>true</b> if the datatype is a real NABUCCO datatype, <b>false</b> if not (e.g. a
     *         {@link java.lang.reflect.Proxy}).
     */
    public boolean isAccessible() {
        if (this.instance == null) {
            return false;
        }

        Class<?> datatypeClass = this.instance.getClass();
        for (Class<?> intf : datatypeClass.getInterfaces()) {
            if (intf == Datatype.class) {
                return true;
            }
        }
        return false;
    }

}
