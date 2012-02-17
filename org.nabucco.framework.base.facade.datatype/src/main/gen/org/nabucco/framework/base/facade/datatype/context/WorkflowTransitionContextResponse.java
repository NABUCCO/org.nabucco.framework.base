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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoMap;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoMapImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;

/**
 * WorkflowTransitionContextResponse<p/>For transporting additional workflow response data.<p/>
 *
 * @version 1
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-09
 */
public class WorkflowTransitionContextResponse extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String TRANSITIONCONTEXTMAP = "transitionContextMap";

    /** The Transition Context holding the current WorkflowInstances. */
    private NabuccoMap<TransitionContext> transitionContextMap;

    /** Constructs a new WorkflowTransitionContextResponse instance. */
    public WorkflowTransitionContextResponse() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowTransitionContextResponse.
     */
    protected void cloneObject(WorkflowTransitionContextResponse clone) {
        super.cloneObject(clone);
        if ((this.transitionContextMap != null)) {
            clone.transitionContextMap = this.transitionContextMap.cloneCollection();
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TRANSITIONCONTEXTMAP, PropertyDescriptorSupport.createCollection(TRANSITIONCONTEXTMAP,
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
        properties.add(super.createProperty(
                WorkflowTransitionContextResponse.getPropertyDescriptor(TRANSITIONCONTEXTMAP),
                this.transitionContextMap, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TRANSITIONCONTEXTMAP) && (property.getType() == TransitionContext.class))) {
            this.transitionContextMap = ((NabuccoMap<TransitionContext>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public WorkflowTransitionContextResponse cloneObject() {
        WorkflowTransitionContextResponse clone = new WorkflowTransitionContextResponse();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Transition Context holding the current WorkflowInstances.
     *
     * @return the NabuccoMap<TransitionContext>.
     */
    public NabuccoMap<TransitionContext> getTransitionContextMap() {
        if ((this.transitionContextMap == null)) {
            this.transitionContextMap = new NabuccoMapImpl<TransitionContext>();
        }
        return this.transitionContextMap;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowTransitionContextResponse.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowTransitionContextResponse.class).getAllProperties();
    }
}
