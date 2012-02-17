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

import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * SimpleProperty
 * <p/>
 * PropertySupport object for simple types, like {@link java.lang.String} or
 * {@link java.lang.Integer}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class SimpleProperty extends PropertySupport implements NabuccoProperty {

    /** The property instance. */
    private Object instance;

    /**
     * Creates a new {@link SimpleProperty} instance.
     * 
     * @param descriptor
     *            the simple descriptor
     * @param instance
     *            the simple instance
     */
    public SimpleProperty(NabuccoPropertyDescriptor descriptor, Object instance) {
        super(descriptor, null, null, null);

        this.instance = instance;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        // Simple Properties cannot be visited!
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return Collections.emptySet();
    }

    @Override
    public Object getInstance() {
        return this.instance;
    }

}
