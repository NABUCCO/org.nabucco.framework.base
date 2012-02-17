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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * EnumProperty
 * <p/>
 * A property holding a single instance of a {@link Enumeration}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class EnumProperty extends PropertySupport implements NabuccoProperty {

    /** The enumeration instance. */
    private Enumeration instance;

    /**
     * Creates a new {@link EnumProperty} instance.
     * 
     * @param descriptor
     *            the property descriptor
     * @param parent
     *            the parent property holder
     * @param instance
     *            the enumeration instance
     * @param constraints
     *            the dynamic constraints
     */
    EnumProperty(NabuccoPropertyDescriptor descriptor, PropertyOwner parent, Enumeration instance, String constraints) {
        super(descriptor, parent, constraints, null);

        this.instance = instance;
    }

    @Override
    public Enumeration getInstance() {
        return this.instance;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        // Enum Properties cannot be visited!
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return Collections.emptySet();
    }

    /**
     * Getter for the enumeration literals.
     * 
     * @return the unmodifiable list of enumeration literals
     */
    public List<Enumeration> getLiterals() {

        @SuppressWarnings("unchecked")
        Class<Enumeration> enumClass = (Class<Enumeration>) this.getType();
        Enumeration[] enumConstants = enumClass.getEnumConstants();

        if (enumConstants == null) {
            return Collections.emptyList();
        }
        return Arrays.<Enumeration> asList(enumConstants);
    }

}
