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
package org.nabucco.framework.base.facade.datatype.context;

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
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;

/**
 * WorkflowTransitionContextRequest<p/>For transporting additional workflow request data.<p/>
 *
 * @version 1
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-09
 */
public class WorkflowTransitionContextRequest extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;" };

    public static final String TRANSITIONCONTEXT = "transitionContext";

    /** The Transition Context holding the current WorkflowInstances. */
    private TransitionContext transitionContext;

    /** Constructs a new WorkflowTransitionContextRequest instance. */
    public WorkflowTransitionContextRequest() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowTransitionContextRequest.
     */
    protected void cloneObject(WorkflowTransitionContextRequest clone) {
        super.cloneObject(clone);
        if ((this.getTransitionContext() != null)) {
            clone.setTransitionContext(this.getTransitionContext().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TRANSITIONCONTEXT, PropertyDescriptorSupport.createDatatype(TRANSITIONCONTEXT,
                TransitionContext.class, 0, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowTransitionContextRequest.getPropertyDescriptor(TRANSITIONCONTEXT),
                this.getTransitionContext(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TRANSITIONCONTEXT) && (property.getType() == TransitionContext.class))) {
            this.setTransitionContext(((TransitionContext) property.getInstance()));
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
        final WorkflowTransitionContextRequest other = ((WorkflowTransitionContextRequest) obj);
        if ((this.transitionContext == null)) {
            if ((other.transitionContext != null))
                return false;
        } else if ((!this.transitionContext.equals(other.transitionContext)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.transitionContext == null) ? 0 : this.transitionContext.hashCode()));
        return result;
    }

    @Override
    public WorkflowTransitionContextRequest cloneObject() {
        WorkflowTransitionContextRequest clone = new WorkflowTransitionContextRequest();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Transition Context holding the current WorkflowInstances.
     *
     * @param transitionContext the TransitionContext.
     */
    public void setTransitionContext(TransitionContext transitionContext) {
        this.transitionContext = transitionContext;
    }

    /**
     * The Transition Context holding the current WorkflowInstances.
     *
     * @return the TransitionContext.
     */
    public TransitionContext getTransitionContext() {
        return this.transitionContext;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowTransitionContextRequest.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowTransitionContextRequest.class).getAllProperties();
    }
}
