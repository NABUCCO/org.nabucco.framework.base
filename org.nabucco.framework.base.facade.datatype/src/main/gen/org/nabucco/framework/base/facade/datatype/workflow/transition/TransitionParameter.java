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
package org.nabucco.framework.base.facade.datatype.workflow.transition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.Signal;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionCommentType;

/**
 * TransitionParameter<p/>The context of a workflow transition.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-03
 */
public class TransitionParameter extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String SIGNAL = "signal";

    public static final String COMMENTCARDINALITY = "commentCardinality";

    /** The signal triggering the transition. */
    private Signal signal;

    /** Whether the transition needs a comment or not. */
    private TransitionCommentType commentCardinality;

    /** Constructs a new TransitionParameter instance. */
    public TransitionParameter() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TransitionParameter.
     */
    protected void cloneObject(TransitionParameter clone) {
        super.cloneObject(clone);
        if ((this.getSignal() != null)) {
            clone.setSignal(this.getSignal().cloneObject());
        }
        clone.setCommentCardinality(this.getCommentCardinality());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(SIGNAL, PropertyDescriptorSupport.createDatatype(SIGNAL, Signal.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COMMENTCARDINALITY, PropertyDescriptorSupport.createEnumeration(COMMENTCARDINALITY,
                TransitionCommentType.class, 1, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TransitionParameter.getPropertyDescriptor(SIGNAL), this.getSignal(), null));
        properties.add(super.createProperty(TransitionParameter.getPropertyDescriptor(COMMENTCARDINALITY),
                this.getCommentCardinality(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SIGNAL) && (property.getType() == Signal.class))) {
            this.setSignal(((Signal) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMMENTCARDINALITY) && (property.getType() == TransitionCommentType.class))) {
            this.setCommentCardinality(((TransitionCommentType) property.getInstance()));
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
        final TransitionParameter other = ((TransitionParameter) obj);
        if ((this.signal == null)) {
            if ((other.signal != null))
                return false;
        } else if ((!this.signal.equals(other.signal)))
            return false;
        if ((this.commentCardinality == null)) {
            if ((other.commentCardinality != null))
                return false;
        } else if ((!this.commentCardinality.equals(other.commentCardinality)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.signal == null) ? 0 : this.signal.hashCode()));
        result = ((PRIME * result) + ((this.commentCardinality == null) ? 0 : this.commentCardinality.hashCode()));
        return result;
    }

    @Override
    public TransitionParameter cloneObject() {
        TransitionParameter clone = new TransitionParameter();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The signal triggering the transition.
     *
     * @param signal the Signal.
     */
    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    /**
     * The signal triggering the transition.
     *
     * @return the Signal.
     */
    public Signal getSignal() {
        return this.signal;
    }

    /**
     * Whether the transition needs a comment or not.
     *
     * @return the TransitionCommentType.
     */
    public TransitionCommentType getCommentCardinality() {
        return this.commentCardinality;
    }

    /**
     * Whether the transition needs a comment or not.
     *
     * @param commentCardinality the TransitionCommentType.
     */
    public void setCommentCardinality(TransitionCommentType commentCardinality) {
        this.commentCardinality = commentCardinality;
    }

    /**
     * Whether the transition needs a comment or not.
     *
     * @param commentCardinality the String.
     */
    public void setCommentCardinality(String commentCardinality) {
        if ((commentCardinality == null)) {
            this.commentCardinality = null;
        } else {
            this.commentCardinality = TransitionCommentType.valueOf(commentCardinality);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TransitionParameter.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TransitionParameter.class).getAllProperties();
    }
}
