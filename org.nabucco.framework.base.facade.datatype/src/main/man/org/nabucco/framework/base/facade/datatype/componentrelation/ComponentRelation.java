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
package org.nabucco.framework.base.facade.datatype.componentrelation;

import java.io.Serializable;

import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Version;

/**
 * ComponentRelation
 * <p/>
 * Inter-component datatype relation.
 * 
 * @author Nicolas Moser, PRODYNA AG
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public abstract class ComponentRelation<T extends NabuccoDatatype> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identifier, represents DB foreign key column */
    private Identifier id;

    /** Version, represents DB version column */
    private Version version;

    /** Type of the relation. */
    private ComponentRelationType relationType;

    /** State of the relation. */
    private ComponentRelationState relationState;

    /** Identifier of the source datatype */
    private Identifier sourceId;

    /** The target datatype */
    private T target;

    private static final String ENUM_SEPARATOR = "#";

    /**
     * Creates a new {@link ComponentRelation} instance.
     * <p/>
     * <i>This constructor must not be called explicitly since it is called implicitly by the
     * persistence provider.</i>
     */
    protected ComponentRelation() {
        this.relationState = ComponentRelationState.INITIALIZED;
    }

    /**
     * Creates a new {@link ComponentRelation} instance.
     * 
     * @param relationType
     *            the type of the relation
     */
    public ComponentRelation(ComponentRelationType relationType) {
        this.relationType = relationType;
        this.relationState = ComponentRelationState.INITIALIZED;
    }

    /**
     * Setter for the relation type.
     * 
     * @param relationType
     *            The relation type to set.
     */
    public void setRelationType(ComponentRelationType relationType) {
        this.relationType = relationType;
    }

    /**
     * Getter for the relation type.
     * 
     * @return Returns the relationType.
     */
    public ComponentRelationType getRelationType() {
        return this.relationType;
    }

    /**
     * Setter for the relation type as string.
     * 
     * @param relationType
     *            The relation type to set.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    void setType(String relationType) {

        try {
            String enumName = relationType.substring(0, relationType.indexOf(ENUM_SEPARATOR));
            String literal = relationType.substring(relationType.indexOf(ENUM_SEPARATOR) + 1,
                    relationType.length());

            Class<Enum> enumType = (Class<Enum>) this.getClass().getClassLoader()
                    .loadClass(enumName);
            Enum value = Enum.valueOf(enumType, literal);

            this.relationType = (ComponentRelationType) value;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter for the relation type string.
     * 
     * @return Returns the relationType.
     */
    String getType() {
        return this.relationType.getClass().getName() + ENUM_SEPARATOR + this.relationType.getId();
    }

    /**
     * Getter for the relation relationState.
     * 
     * @return Returns the relationState.
     */
    public ComponentRelationState getRelationState() {
        return this.relationState;
    }

    /**
     * Setter for the relation relationState.
     * 
     * @param relationState
     *            The relationState to set.
     */
    public void setRelationState(ComponentRelationState state) {
        if (state != null) {
            this.relationState = state;
        }
    }

    /**
     * Identifier, represents DB foreign key column
     * 
     * @return the Long.
     */
    public Long getId() {
        if ((this.id == null)) {
            this.id = new Identifier();
        }
        return this.id.getValue();
    }

    /**
     * Identifier, represents DB foreign key column
     * 
     * @param id
     *            the Long.
     */
    public void setId(Long id) {
        if ((this.id == null)) {
            this.id = new Identifier();
        }
        this.id.setValue(id);
    }

    /**
     * Version, represents DB version column
     * 
     * @return the Long.
     */
    public Long getVersion() {
        if ((this.version == null)) {
            this.version = new Version();
        }
        return this.version.getValue();
    }

    /**
     * Version, represents DB version column
     * 
     * @param version
     *            the Long.
     */
    public void setVersion(Long version) {
        if ((this.version == null)) {
            this.version = new Version();
        }
        this.version.setValue(version);
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
        clone.relationState = this.relationState;
        clone.relationType = this.relationType;
    }
}
