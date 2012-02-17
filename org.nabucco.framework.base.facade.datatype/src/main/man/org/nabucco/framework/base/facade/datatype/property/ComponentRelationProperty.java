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

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.relation.RelationType;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * ComponentRelationProperty
 * <p/>
 * A property holding a single instance of a {@link ComponentRelation}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ComponentRelationProperty extends PropertySupport implements NabuccoProperty {

    private NabuccoList<ComponentRelation<?>> instance;

    private ComponentRelationType relationType;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance()
            .getLogger(ComponentRelationProperty.class);

    /**
     * Creates a new {@link ComponentRelationProperty} instance.
     * 
     * @param descriptor
     *            the property descriptor
     * @param parent
     *            the parent property holder
     * @param componentRelation
     *            the component realtion collection instance
     * @param constraints
     *            the dynamic constraints
     */
    ComponentRelationProperty(NabuccoPropertyDescriptor descriptor, PropertyOwner parent,
            NabuccoList<ComponentRelation<?>> instance, String constraints) {
        super(descriptor, parent, constraints, null);

        this.instance = instance;
    }

    /**
     * Getter for the component relation type.
     * 
     * @return the component relation type
     */
    public ComponentRelationType getComponentRelationType() {
        return this.relationType;
    }

    /**
     * Setter for the component relation type.
     * 
     * @param relationType
     *            the component relation type to set
     */
    public void setComponentRelationType(ComponentRelationType relationType) {
        if (this.relationType != null) {
            throw new IllegalStateException("Cannot modify component relation type.");
        }
        this.relationType = relationType;
    }

    /**
     * Create a new component relation instance.
     * 
     * @return a new created component relation instance
     */
    public ComponentRelation<?> newComponentRelationInstance() {

        Class<?> type = super.getType();

        try {

            Constructor<?> constructor = type.getConstructor(ComponentRelationType.class);
            ComponentRelation<?> relation = (ComponentRelation<?>) constructor.newInstance(this.relationType);

            relation.setDatatypeState(DatatypeState.INITIALIZED);
            relation.setFunctionalType(this.relationType);
            relation.setRelationType(RelationType.HAS);
            PropertyOwner parent = this.getParent();
            if (parent instanceof NabuccoDatatype) {
                relation.setSourceId(new Identifier(((NabuccoDatatype) parent).getId()));
            }

            return relation;

        } catch (Exception e) {
            logger.error(e, "Error creating component relation instance of '", type, "'.");
        }

        return null;
    }

    @Override
    public NabuccoProperty createProperty(Object instance) {
        ComponentRelationProperty property = (ComponentRelationProperty) super.createProperty(instance);
        property.setComponentRelationType(this.relationType);
        return property;
    }

    @Override
    public NabuccoList<ComponentRelation<?>> getInstance() {
        return this.instance;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        // Nothing to do here!
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return Collections.emptySet();
    }

}
