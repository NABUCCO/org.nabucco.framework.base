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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.visitor.Visitable;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * ListProperty
 * <p/>
 * PropertySupport object for properties of {@link java.util.List}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ListProperty<N extends NType> extends PropertySupport<N> implements
        NabuccoProperty<N> {

    /** The property instance. */
    private List<N> instance;

    /**
     * Creates a new {@link ListProperty} instance.
     * 
     * @param name
     *            the list name
     * @param type
     *            the list type
     * @param constraints
     *            the property constraint string
     * @param instance
     *            the list instance
     */
    public ListProperty(String name, Class<N> type, String constraints, List<N> instance) {
        super(name, type, constraints, PropertyType.LIST);

        this.instance = instance;
    }

    @Override
    public List<N> getInstance() {
        return this.instance;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        if (this.isVisitable()) {
            this.visitList(visitor);
        }
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        if (this.isVisitable()) {
            return this.collectProperties();
        }

        return Collections.emptyList();
    }

    /**
     * Checks whether the contained list is visitable or not.
     * 
     * @return <b>true</b> if the list is visitable, <b>false</b> if not
     */
    private boolean isVisitable() {
        if (this.instance == null) {
            return false;
        }
        if (!(this.instance instanceof NabuccoList<?>)) {
            return true;
        }

        NabuccoList<?> nabuccoList = (NabuccoList<?>) this.instance;
        if (nabuccoList.getState() != NabuccoCollectionState.LAZY) {
            return true;
        }
        return false;
    }

    /**
     * Visit each element of the list.
     * 
     * @param list
     *            the list to visit
     * 
     * @throws VisitorException
     */
    private void visitList(Visitor visitor) throws VisitorException {
        for (Object visitable : this.instance) {
            if (visitable instanceof Visitable) {
                ((Visitable) visitable).accept(visitor);
            }
        }
    }

    /**
     * Collect the properties of all elements in the list
     * 
     * @return the list of collected properties
     */
    private List<NabuccoProperty<?>> collectProperties() {
        List<NabuccoProperty<?>> properties = new ArrayList<NabuccoProperty<?>>();

        for (N visitable : this.instance) {
            if (visitable instanceof Visitable) {
                properties.addAll(((Visitable) visitable).getProperties());
            }
        }
        return properties;
    }

}
