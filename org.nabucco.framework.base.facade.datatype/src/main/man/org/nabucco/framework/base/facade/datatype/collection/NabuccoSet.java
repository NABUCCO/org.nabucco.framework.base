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
package org.nabucco.framework.base.facade.datatype.collection;

import java.util.Collection;
import java.util.HashSet;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * NabuccoSet
 * <p/>
 * Sub class of {@link java.util.HashSet} with an extra {@link NabuccoCollectionState} attribute.
 * 
 * @see HashSet
 * 
 * @deprecated use {@link NabuccoList} instead.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
@Deprecated
public class NabuccoSet<E extends NType> extends HashSet<E> implements NabuccoCollection<E> {

    private static final long serialVersionUID = 1L;

    private NabuccoCollectionState state;

    /**
     * Creates a new {@link NabuccoSet} instance with {@link NabuccoCollectionState} INITIALIZED.
     */
    public NabuccoSet() {
        super();
        this.initState(null);
    }

    /**
     * Creates a new {@link NabuccoSet} instance.
     * 
     * @param state
     *            state of the collection
     */
    public NabuccoSet(NabuccoCollectionState state) {
        super();
        this.initState(state);
    }

    /**
     * Creates a new {@link NabuccoSet} with initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity
     * @param state
     *            state of the collection
     */
    public NabuccoSet(int initialCapacity, NabuccoCollectionState state) {
        super(initialCapacity);
        this.initState(state);
    }

    /**
     * Creates a new {@link NabuccoSet} based on an existing collection.
     * 
     * @param collection
     *            the existing collection, all elements are copied in the existing order
     * @param state
     *            state of the collection
     */
    public NabuccoSet(Collection<E> collection, NabuccoCollectionState state) {
        super(collection);
        this.initState(state);
    }

    @Override
    public NabuccoCollectionState getState() {
        return state;
    }

    @Override
    public void setState(NabuccoCollectionState state) {
        if (state != null) {
            this.state = state;
        }
    }

    private void initState(NabuccoCollectionState state) {
        if (state == null) {
            state = NabuccoCollectionState.INITIALIZED;
        }
        this.state = state;
    }

    @Override
    public void clearAll() {
        super.clear();
    }

    @Override
    public NabuccoSet<E> cloneCollection() {
        NabuccoSet<E> clone = new NabuccoSet<E>();
        clone.state = this.state;

        if (this.state == NabuccoCollectionState.LAZY) {
            return clone;
        }

        for (E element : this) {
            @SuppressWarnings("unchecked")
            E cloneElement = (E) element.cloneObject();
            NabuccoCollectionAccessor.getInstance().addWithoutAssignment(clone, cloneElement);
        }

        return clone;
    }

    @Override
    public boolean isManaged() {
        return false;
    }
}
