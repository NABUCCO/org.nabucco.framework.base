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
package org.nabucco.framework.base.facade.datatype.visitor;

import java.util.HashSet;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;

/**
 * DatatypeVisitor
 * <p/>
 * Datatype Visitor that visits children last. Each instance is only visited once, when calling the
 * <code>reset()</code> Method the cache will be cleaned.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeVisitor implements Visitor {

    /** Traverse over component borders. */
    private boolean traverseComponents;

    /** Set of visited object ids. */
    private Set<Integer> visited = new HashSet<Integer>();

    /**
     * Creates a new {@link DatatypeVisitor} instance that traverses over component borders.
     * <p/>
     * This constructor is equivalent to {@link DatatypeVisitor#DatatypeVisitor(boolean)}.
     */
    public DatatypeVisitor() {
        this(true);
    }

    /**
     * Creates a new {@link DatatypeVisitor} instance.
     * 
     * @param traverseComponents
     *            <b>true</b> when component borders should be traversed, <b>false</b> if not
     */
    public DatatypeVisitor(boolean traverseComponents) {
        this.traverseComponents = traverseComponents;
    }

    @Override
    public final boolean hasVisited(Visitable visitable) {
        int identity = System.identityHashCode(visitable);
        return !visited.add(identity);
    }

    @Override
    public void visit(Visitable visitable) throws VisitorException {
        if (visitable instanceof Datatype) {
            visit((Datatype) visitable);
        } else if (visitable instanceof Basetype) {
            visit((Basetype) visitable);
        }
    }

    /**
     * Visit a {@link Datatype} instance.
     * 
     * @param datatype
     *            the datatype to visit
     * 
     * @throws VisitorException
     */
    public void visit(Datatype datatype) throws VisitorException {
        this.visitChildren(datatype);
    }

    /**
     * Visit a {@link Basetype} instance.
     * 
     * @param basetype
     *            the basetype to visit
     * 
     * @throws VisitorException
     */
    public void visit(Basetype basetype) throws VisitorException {
        this.visitChildren(basetype);
    }

    /**
     * Visits all sub-properties of a visitable.
     * 
     * @param visitable
     *            the visitable containing the properties
     * 
     * @throws VisitorException
     */
    protected void visitChildren(Visitable visitable) throws VisitorException {
        for (NabuccoProperty property : visitable.getProperties()) {

            switch (property.getPropertyType()) {
            case DATATYPE:
                DatatypeProperty datatypeProperty = (DatatypeProperty) property;
                if (datatypeProperty.getAssociationType() != PropertyAssociationType.COMPONENT
                        || this.traverseComponents) {
                    datatypeProperty.accept(this);
                }

                break;

            case COLLECTION:
                CollectionProperty listProperty = (CollectionProperty) property;
                if (listProperty.getAssociationType() != PropertyAssociationType.COMPONENT || this.traverseComponents) {
                    listProperty.accept(this);
                }
                break;

            default:
                property.accept(this);
            }

        }
    }

    /**
     * Cleans the cache of already visited instances.
     */
    public void reset() {
        this.visited.clear();
    }

}
