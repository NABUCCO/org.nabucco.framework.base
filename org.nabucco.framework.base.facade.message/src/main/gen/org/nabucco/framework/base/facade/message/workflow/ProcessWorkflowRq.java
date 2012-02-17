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
package org.nabucco.framework.base.facade.message.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.documentation.Comment;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.Signal;
import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * ProcessWorkflowRq<p/>Request for processing an existing workflow.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-18
 */
public class ProcessWorkflowRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "m0,1;", "m1,1;", "m0,n;",
            "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "l0,100000;u0,n;m0,1;" };

    public static final String INSTANCEID = "instanceId";

    public static final String CONTEXT = "context";

    public static final String SIGNAL = "signal";

    public static final String SUBINSTANCES = "subInstances";

    public static final String NEWUSER = "newUser";

    public static final String NEWGROUP = "newGroup";

    public static final String NEWROLE = "newRole";

    public static final String COMMENT = "comment";

    /** The instance id of the workflow. */
    private Identifier instanceId;

    /** Context for the workflow transition. */
    private Context context;

    /** The triggered signal. */
    private Signal signal;

    /** Sub Workflow Instances. */
    private NabuccoList<Instance> subInstances;

    /** The new assigned user. */
    private Name newUser;

    /** The new assigned group. */
    private Name newGroup;

    /** The new assigned role. */
    private Name newRole;

    /** An optional workflow comment. */
    private Comment comment;

    /** Constructs a new ProcessWorkflowRq instance. */
    public ProcessWorkflowRq() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(INSTANCEID, PropertyDescriptorSupport.createBasetype(INSTANCEID, Identifier.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(CONTEXT, PropertyDescriptorSupport.createDatatype(CONTEXT, Context.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SIGNAL, PropertyDescriptorSupport.createDatatype(SIGNAL, Signal.class, 2,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SUBINSTANCES, PropertyDescriptorSupport.createCollection(SUBINSTANCES, Instance.class, 3,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NEWUSER,
                PropertyDescriptorSupport.createBasetype(NEWUSER, Name.class, 4, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(NEWGROUP,
                PropertyDescriptorSupport.createBasetype(NEWGROUP, Name.class, 5, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(NEWROLE,
                PropertyDescriptorSupport.createBasetype(NEWROLE, Name.class, 6, PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(COMMENT,
                PropertyDescriptorSupport.createBasetype(COMMENT, Comment.class, 7, PROPERTY_CONSTRAINTS[7], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ProcessWorkflowRq.getPropertyDescriptor(INSTANCEID), this.instanceId));
        properties.add(super.createProperty(ProcessWorkflowRq.getPropertyDescriptor(CONTEXT), this.getContext()));
        properties.add(super.createProperty(ProcessWorkflowRq.getPropertyDescriptor(SIGNAL), this.getSignal()));
        properties.add(super.createProperty(ProcessWorkflowRq.getPropertyDescriptor(SUBINSTANCES), this.subInstances));
        properties.add(super.createProperty(ProcessWorkflowRq.getPropertyDescriptor(NEWUSER), this.newUser));
        properties.add(super.createProperty(ProcessWorkflowRq.getPropertyDescriptor(NEWGROUP), this.newGroup));
        properties.add(super.createProperty(ProcessWorkflowRq.getPropertyDescriptor(NEWROLE), this.newRole));
        properties.add(super.createProperty(ProcessWorkflowRq.getPropertyDescriptor(COMMENT), this.comment));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(INSTANCEID) && (property.getType() == Identifier.class))) {
            this.setInstanceId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTEXT) && (property.getType() == Context.class))) {
            this.setContext(((Context) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SIGNAL) && (property.getType() == Signal.class))) {
            this.setSignal(((Signal) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUBINSTANCES) && (property.getType() == Instance.class))) {
            this.subInstances = ((NabuccoList<Instance>) property.getInstance());
            return true;
        } else if ((property.getName().equals(NEWUSER) && (property.getType() == Name.class))) {
            this.setNewUser(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEWGROUP) && (property.getType() == Name.class))) {
            this.setNewGroup(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEWROLE) && (property.getType() == Name.class))) {
            this.setNewRole(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMMENT) && (property.getType() == Comment.class))) {
            this.setComment(((Comment) property.getInstance()));
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
        final ProcessWorkflowRq other = ((ProcessWorkflowRq) obj);
        if ((this.instanceId == null)) {
            if ((other.instanceId != null))
                return false;
        } else if ((!this.instanceId.equals(other.instanceId)))
            return false;
        if ((this.context == null)) {
            if ((other.context != null))
                return false;
        } else if ((!this.context.equals(other.context)))
            return false;
        if ((this.signal == null)) {
            if ((other.signal != null))
                return false;
        } else if ((!this.signal.equals(other.signal)))
            return false;
        if ((this.subInstances == null)) {
            if ((other.subInstances != null))
                return false;
        } else if ((!this.subInstances.equals(other.subInstances)))
            return false;
        if ((this.newUser == null)) {
            if ((other.newUser != null))
                return false;
        } else if ((!this.newUser.equals(other.newUser)))
            return false;
        if ((this.newGroup == null)) {
            if ((other.newGroup != null))
                return false;
        } else if ((!this.newGroup.equals(other.newGroup)))
            return false;
        if ((this.newRole == null)) {
            if ((other.newRole != null))
                return false;
        } else if ((!this.newRole.equals(other.newRole)))
            return false;
        if ((this.comment == null)) {
            if ((other.comment != null))
                return false;
        } else if ((!this.comment.equals(other.comment)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.instanceId == null) ? 0 : this.instanceId.hashCode()));
        result = ((PRIME * result) + ((this.context == null) ? 0 : this.context.hashCode()));
        result = ((PRIME * result) + ((this.signal == null) ? 0 : this.signal.hashCode()));
        result = ((PRIME * result) + ((this.subInstances == null) ? 0 : this.subInstances.hashCode()));
        result = ((PRIME * result) + ((this.newUser == null) ? 0 : this.newUser.hashCode()));
        result = ((PRIME * result) + ((this.newGroup == null) ? 0 : this.newGroup.hashCode()));
        result = ((PRIME * result) + ((this.newRole == null) ? 0 : this.newRole.hashCode()));
        result = ((PRIME * result) + ((this.comment == null) ? 0 : this.comment.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The instance id of the workflow.
     *
     * @return the Identifier.
     */
    public Identifier getInstanceId() {
        return this.instanceId;
    }

    /**
     * The instance id of the workflow.
     *
     * @param instanceId the Identifier.
     */
    public void setInstanceId(Identifier instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * Context for the workflow transition.
     *
     * @return the Context.
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * Context for the workflow transition.
     *
     * @param context the Context.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * The triggered signal.
     *
     * @return the Signal.
     */
    public Signal getSignal() {
        return this.signal;
    }

    /**
     * The triggered signal.
     *
     * @param signal the Signal.
     */
    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    /**
     * Sub Workflow Instances.
     *
     * @return the NabuccoList<Instance>.
     */
    public NabuccoList<Instance> getSubInstances() {
        if ((this.subInstances == null)) {
            this.subInstances = new NabuccoListImpl<Instance>(NabuccoCollectionState.INITIALIZED);
        }
        return this.subInstances;
    }

    /**
     * The new assigned user.
     *
     * @return the Name.
     */
    public Name getNewUser() {
        return this.newUser;
    }

    /**
     * The new assigned user.
     *
     * @param newUser the Name.
     */
    public void setNewUser(Name newUser) {
        this.newUser = newUser;
    }

    /**
     * The new assigned group.
     *
     * @return the Name.
     */
    public Name getNewGroup() {
        return this.newGroup;
    }

    /**
     * The new assigned group.
     *
     * @param newGroup the Name.
     */
    public void setNewGroup(Name newGroup) {
        this.newGroup = newGroup;
    }

    /**
     * The new assigned role.
     *
     * @return the Name.
     */
    public Name getNewRole() {
        return this.newRole;
    }

    /**
     * The new assigned role.
     *
     * @param newRole the Name.
     */
    public void setNewRole(Name newRole) {
        this.newRole = newRole;
    }

    /**
     * An optional workflow comment.
     *
     * @return the Comment.
     */
    public Comment getComment() {
        return this.comment;
    }

    /**
     * An optional workflow comment.
     *
     * @param comment the Comment.
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ProcessWorkflowRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ProcessWorkflowRq.class).getAllProperties();
    }
}
