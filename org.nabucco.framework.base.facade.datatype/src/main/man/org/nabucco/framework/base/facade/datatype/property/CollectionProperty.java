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
import java.util.LinkedHashSet;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * CollectionProperty
 * <p/>
 * Property object for properties of {@link java.util.List}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class CollectionProperty extends PropertySupport implements NabuccoProperty {

    /** The list instance. */
    private NabuccoCollection<? extends NType> instance;

    /**
     * Creates a new {@link CollectionProperty} instance.
     * 
     * @param descriptor
     *            the property descriptor
     * @param parent
     *            the parent property holder
     * @param instance
     *            the collection instance
     * @param constraints
     *            the dynamic constraints
     */
    CollectionProperty(NabuccoPropertyDescriptor descriptor, PropertyOwner parent,
            NabuccoCollection<? extends NType> instance, String constraints) {
        super(descriptor, parent, constraints, null);

        this.instance = instance;
    }

    @Override
    public NabuccoCollection<? extends NType> getInstance() {
        return this.instance;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        if (this.isTraversable()) {
            this.visitList(visitor);
        }
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        if (this.isTraversable()) {
            return this.collectProperties();
        }

        return Collections.emptySet();
    }

    /**
     * Checks whether the contained list is visitable or not.
     * 
     * @return <b>true</b> if the list is visitable, <b>false</b> if not
     */
    public boolean isTraversable() {
        if (this.instance == null) {
            return false;
        }
        return this.instance.isTraversable();
    }

    /**
     * Visit each element of the list.
     * 
     * @param visitor
     *            the visitor
     * 
     * @throws VisitorException
     */
    private void visitList(Visitor visitor) throws VisitorException {
        for (NType entry : this.instance) {
            if (entry != null) {
                entry.accept(visitor);
            }
        }
    }

    /**
     * Collect the properties of all elements in the list
     * 
     * @return the list of collected properties
     */
    private Set<NabuccoProperty> collectProperties() {
        Set<NabuccoProperty> properties = new LinkedHashSet<NabuccoProperty>();
        for (NType entry : this.instance) {
            if (entry != null) {
                properties.addAll(entry.getProperties());
            }
        }
        return properties;
    }

}
