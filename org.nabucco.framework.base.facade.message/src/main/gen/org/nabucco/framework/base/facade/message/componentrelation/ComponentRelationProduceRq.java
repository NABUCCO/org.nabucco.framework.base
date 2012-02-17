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
package org.nabucco.framework.base.facade.message.componentrelation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.FullQualifiedClassName;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.relation.RelationType;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * ComponentRelationProduceRq<p/>Request message for a Component Relation production.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-09-27
 */
public class ComponentRelationProduceRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final RelationType RELATIONTYPE_DEFAULT = RelationType.HAS;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,1000;u0,n;m1,1;", "l0,n;u0,n;m0,1;", "m1,1;", "m1,1;" };

    public static final String TYPE = "type";

    public static final String SOURCEID = "sourceId";

    public static final String TARGET = "target";

    public static final String RELATIONTYPE = "relationType";

    /** The full qualified relation type name. */
    private FullQualifiedClassName type;

    /** The component relation source ID. */
    private Identifier sourceId;

    /** The component relation target. */
    private NabuccoDatatype target;

    /** The relation type. */
    private RelationType relationType;

    /** Constructs a new ComponentRelationProduceRq instance. */
    public ComponentRelationProduceRq() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        relationType = RELATIONTYPE_DEFAULT;
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TYPE, PropertyDescriptorSupport.createBasetype(TYPE, FullQualifiedClassName.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap
                .put(SOURCEID, PropertyDescriptorSupport.createBasetype(SOURCEID, Identifier.class, 1,
                        PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(TARGET, PropertyDescriptorSupport.createDatatype(TARGET, NabuccoDatatype.class, 2,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(RELATIONTYPE, PropertyDescriptorSupport.createEnumeration(RELATIONTYPE, RelationType.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ComponentRelationProduceRq.getPropertyDescriptor(TYPE), this.type));
        properties.add(super.createProperty(ComponentRelationProduceRq.getPropertyDescriptor(SOURCEID), this.sourceId));
        properties
                .add(super.createProperty(ComponentRelationProduceRq.getPropertyDescriptor(TARGET), this.getTarget()));
        properties.add(super.createProperty(ComponentRelationProduceRq.getPropertyDescriptor(RELATIONTYPE),
                this.getRelationType()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TYPE) && (property.getType() == FullQualifiedClassName.class))) {
            this.setType(((FullQualifiedClassName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SOURCEID) && (property.getType() == Identifier.class))) {
            this.setSourceId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TARGET) && (property.getType() == NabuccoDatatype.class))) {
            this.setTarget(((NabuccoDatatype) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RELATIONTYPE) && (property.getType() == RelationType.class))) {
            this.setRelationType(((RelationType) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final ComponentRelationProduceRq other = ((ComponentRelationProduceRq) obj);
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.sourceId == null)) {
            if ((other.sourceId != null))
                return false;
        } else if ((!this.sourceId.equals(other.sourceId)))
            return false;
        if ((this.target == null)) {
            if ((other.target != null))
                return false;
        } else if ((!this.target.equals(other.target)))
            return false;
        if ((this.relationType == null)) {
            if ((other.relationType != null))
                return false;
        } else if ((!this.relationType.equals(other.relationType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.sourceId == null) ? 0 : this.sourceId.hashCode()));
        result = ((PRIME * result) + ((this.target == null) ? 0 : this.target.hashCode()));
        result = ((PRIME * result) + ((this.relationType == null) ? 0 : this.relationType.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The full qualified relation type name.
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getType() {
        return this.type;
    }

    /**
     * The full qualified relation type name.
     *
     * @param type the FullQualifiedClassName.
     */
    public void setType(FullQualifiedClassName type) {
        this.type = type;
    }

    /**
     * The component relation source ID.
     *
     * @return the Identifier.
     */
    public Identifier getSourceId() {
        return this.sourceId;
    }

    /**
     * The component relation source ID.
     *
     * @param sourceId the Identifier.
     */
    public void setSourceId(Identifier sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * The component relation target.
     *
     * @return the NabuccoDatatype.
     */
    public NabuccoDatatype getTarget() {
        return this.target;
    }

    /**
     * The component relation target.
     *
     * @param target the NabuccoDatatype.
     */
    public void setTarget(NabuccoDatatype target) {
        this.target = target;
    }

    /**
     * The relation type.
     *
     * @return the RelationType.
     */
    public RelationType getRelationType() {
        return this.relationType;
    }

    /**
     * The relation type.
     *
     * @param relationType the RelationType.
     */
    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ComponentRelationProduceRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ComponentRelationProduceRq.class).getAllProperties();
    }
}
