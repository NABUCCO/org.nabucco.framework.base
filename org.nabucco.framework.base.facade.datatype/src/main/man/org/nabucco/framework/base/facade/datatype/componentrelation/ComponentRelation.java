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
package org.nabucco.framework.base.facade.datatype.componentrelation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.relation.RelationType;

/**
 * ComponentRelation
 * <p/>
 * Inter-component datatype relation.
 * 
 * @param <T>
 *            the target datatype
 * 
 * @author Nicolas Moser, PRODYNA AG
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public abstract class ComponentRelation<T extends NabuccoDatatype> extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    /** The target datatype. */
    public static final String TARGET = "target";

    /** Type of the relation. */
    private RelationType relationType;

    /** Functional type of the relation. */
    private ComponentRelationType functionalType;

    /** Identifier of the source datatype */
    private Identifier sourceId;

    /** The target datatype */
    private T target;

    /**
     * Creates a new {@link ComponentRelation} instance.
     * <p/>
     * <i>This constructor must not be called explicitly since it is called implicitly by the
     * persistence provider.</i>
     */
    protected ComponentRelation() {
        super();
    }

    /**
     * Creates a new {@link ComponentRelation} instance.
     * 
     * @param relationType
     *            the type of the relation
     */
    public ComponentRelation(ComponentRelationType relationType) {
        this.functionalType = relationType;
    }

    @Override
    public void init() {
        // Nothing to initialize in component relation.
    }

    /**
     * Getter for the relationType.
     * 
     * @return Returns the relationType.
     */
    public RelationType getRelationType() {
        return this.relationType;
    }

    /**
     * Setter for the relationType.
     * 
     * @param relationType
     *            The relationType to set.
     */
    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    /**
     * Getter for the functional type.
     * 
     * @return Returns the functionalType.
     */
    public ComponentRelationType getFunctionalType() {
        return this.functionalType;
    }

    /**
     * Setter for the functional type.
     * 
     * @param relationType
     *            The relation type to set.
     */
    public void setFunctionalType(ComponentRelationType relationType) {
        this.functionalType = relationType;
    }

    /**
     * Setter for the relation type as string.
     * 
     * @param relationType
     *            The relation type to set.
     */
    void setType(String relationType) {
        this.functionalType = ComponentRelationTypeMapper.getInstance().valueOf(relationType);
    }

    /**
     * Getter for the relation type string.
     * 
     * @return Returns the functionalType.
     */
    public String getType() {
        return ComponentRelationTypeMapper.getInstance().toString(this.functionalType);
    }

    /**
     * Getter for the target datatype.
     * 
     * @return Returns the target datatype.
     */
    public T getTarget() {
        return this.target;
    }

    /**
     * Setter for the target datatype.
     * 
     * @param target
     *            The target datatype to set.
     */
    public void setTarget(T target) {
        this.target = target;
    }

    /**
     * Setter for the target datatype.
     * 
     * @param target
     *            The target datatype to set.
     */
    @SuppressWarnings("unchecked")
    public void setTarget(Object target) {
        this.target = (T) target;
    }

    /**
     * Getter for the source datatype id.
     * 
     * @return Returns the source datatype id.
     */
    public Identifier getSourceId() {
        return this.sourceId;
    }

    /**
     * Setter for the source datatype id.
     * 
     * @param sourceId
     *            The source datatype id to set.
     */
    public void setSourceId(Identifier sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Clones the component relation.
     * 
     * @return the cloned component relation
     */
    public abstract ComponentRelation<T> cloneObject();

    /**
     * Clones the component relation properties.
     * 
     * @param clone
     *            the component relation clone
     */
    @SuppressWarnings("unchecked")
    protected void cloneObject(ComponentRelation<T> clone) {

        T target = this.getTarget();
        if (target != null) {
            T cloneObject = (T) target.cloneObject();
            clone.setTarget(cloneObject);
        }

        Identifier sourceId = this.getSourceId();
        if (sourceId != null) {
            clone.setSourceId(sourceId.cloneObject());
        }

        clone.setId(this.getId());
        clone.setVersion(this.getVersion());
        clone.functionalType = this.functionalType;
        clone.relationType = this.relationType;
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ComponentRelation.getPropertyDescriptor(TARGET), this.target, null));
        return properties;
    }

    /**
     * Getter for the PropertyDescriptor.
     * 
     * @param propertyName
     *            the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ComponentRelation.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     * 
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ComponentRelation.class).getAllProperties();
    }

    /**
     * CreatePropertyContainer.
     * 
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();

        propertyMap.put(TARGET, PropertyDescriptorSupport.createDatatype(TARGET, NabuccoDatatype.class, 0, "m0,1;",
                false, PropertyAssociationType.COMPOSITION));

        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.relationType == null) ? 0 : this.relationType.hashCode());
        result = prime * result + ((this.sourceId == null) ? 0 : this.sourceId.hashCode());
        result = prime * result + ((this.target == null) ? 0 : this.target.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof ComponentRelation))
            return false;
        ComponentRelation<?> other = (ComponentRelation<?>) obj;
        if (this.relationType != other.relationType)
            return false;
        if (this.sourceId == null) {
            if (other.sourceId != null)
                return false;
        } else if (!this.sourceId.equals(other.sourceId))
            return false;
        if (this.target == null) {
            if (other.target != null)
                return false;
        } else if (!this.target.equals(other.target))
            return false;
        return true;
    }
    
    
}
