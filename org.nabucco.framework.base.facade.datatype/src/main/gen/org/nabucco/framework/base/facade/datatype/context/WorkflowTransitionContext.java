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
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.context.WorkflowTransitionContextRequest;
import org.nabucco.framework.base.facade.datatype.context.WorkflowTransitionContextResponse;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkflowTransitionContext<p/>For transporting additional workflow data.<p/>
 *
 * @version 1
 * @author Nicolas Moser, PRODYNA AG, 2011-05-11
 */
public class WorkflowTransitionContext extends ServiceSubContext implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final ServiceSubContextType CONTEXTTYPE_DEFAULT = ServiceSubContextType.WORKFLOW_TRANSITION;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;" };

    public static final String REQUESTTRANSITIONCONTEXT = "requestTransitionContext";

    public static final String RESPONSETRANSITIONCONTEXT = "responseTransitionContext";

    /** The transition context that need to be proceeded */
    private WorkflowTransitionContextRequest requestTransitionContext;

    /** The map of transition contexts that are actually loaded */
    private WorkflowTransitionContextResponse responseTransitionContext;

    /** Constructs a new WorkflowTransitionContext instance. */
    public WorkflowTransitionContext() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        contextType = CONTEXTTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowTransitionContext.
     */
    protected void cloneObject(WorkflowTransitionContext clone) {
        super.cloneObject(clone);
        clone.setContextType(this.getContextType());
        if ((this.getRequestTransitionContext() != null)) {
            clone.setRequestTransitionContext(this.getRequestTransitionContext().cloneObject());
        }
        if ((this.getResponseTransitionContext() != null)) {
            clone.setResponseTransitionContext(this.getResponseTransitionContext().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ServiceSubContext.class).getPropertyMap());
        propertyMap.put(REQUESTTRANSITIONCONTEXT, PropertyDescriptorSupport.createDatatype(REQUESTTRANSITIONCONTEXT,
                WorkflowTransitionContextRequest.class, 4, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(RESPONSETRANSITIONCONTEXT, PropertyDescriptorSupport.createDatatype(RESPONSETRANSITIONCONTEXT,
                WorkflowTransitionContextResponse.class, 5, PROPERTY_CONSTRAINTS[1], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowTransitionContext.getPropertyDescriptor(REQUESTTRANSITIONCONTEXT),
                this.getRequestTransitionContext(), null));
        properties.add(super.createProperty(WorkflowTransitionContext.getPropertyDescriptor(RESPONSETRANSITIONCONTEXT),
                this.getResponseTransitionContext(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(REQUESTTRANSITIONCONTEXT) && (property.getType() == WorkflowTransitionContextRequest.class))) {
            this.setRequestTransitionContext(((WorkflowTransitionContextRequest) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RESPONSETRANSITIONCONTEXT) && (property.getType() == WorkflowTransitionContextResponse.class))) {
            this.setResponseTransitionContext(((WorkflowTransitionContextResponse) property.getInstance()));
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
        final WorkflowTransitionContext other = ((WorkflowTransitionContext) obj);
        if ((this.requestTransitionContext == null)) {
            if ((other.requestTransitionContext != null))
                return false;
        } else if ((!this.requestTransitionContext.equals(other.requestTransitionContext)))
            return false;
        if ((this.responseTransitionContext == null)) {
            if ((other.responseTransitionContext != null))
                return false;
        } else if ((!this.responseTransitionContext.equals(other.responseTransitionContext)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.requestTransitionContext == null) ? 0 : this.requestTransitionContext
                .hashCode()));
        result = ((PRIME * result) + ((this.responseTransitionContext == null) ? 0 : this.responseTransitionContext
                .hashCode()));
        return result;
    }

    @Override
    public WorkflowTransitionContext cloneObject() {
        WorkflowTransitionContext clone = new WorkflowTransitionContext();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The transition context that need to be proceeded
     *
     * @param requestTransitionContext the WorkflowTransitionContextRequest.
     */
    public void setRequestTransitionContext(WorkflowTransitionContextRequest requestTransitionContext) {
        this.requestTransitionContext = requestTransitionContext;
    }

    /**
     * The transition context that need to be proceeded
     *
     * @return the WorkflowTransitionContextRequest.
     */
    public WorkflowTransitionContextRequest getRequestTransitionContext() {
        return this.requestTransitionContext;
    }

    /**
     * The map of transition contexts that are actually loaded
     *
     * @param responseTransitionContext the WorkflowTransitionContextResponse.
     */
    public void setResponseTransitionContext(WorkflowTransitionContextResponse responseTransitionContext) {
        this.responseTransitionContext = responseTransitionContext;
    }

    /**
     * The map of transition contexts that are actually loaded
     *
     * @return the WorkflowTransitionContextResponse.
     */
    public WorkflowTransitionContextResponse getResponseTransitionContext() {
        return this.responseTransitionContext;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowTransitionContext.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowTransitionContext.class).getAllProperties();
    }
}
