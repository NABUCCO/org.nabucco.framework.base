/*
 * Copyright 2012 PRODYNA AG
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
package org.nabucco.framework.base.ui.web.model.work.workflow;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeAccessor;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;

/**
 * WorkflowEntryVisitor
 * 
 * Visits the datatype and analyses if it relates to the workflowinstance with given id
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class WorkflowInstanceReferenceVisitor extends DatatypeVisitor implements Visitor {

    private Identifier instanceId;

    private boolean retVal = false;

    private boolean executed = false;

    private List<Instance> workflowInstanceList = new ArrayList<Instance>();

    /**
     * 
     * Creates a new {@link WorkflowInstanceReferenceVisitor} instance.
     * 
     * @param instanceId
     *            the id to be checked for
     */
    public WorkflowInstanceReferenceVisitor(Identifier instanceId) {
        this.instanceId = instanceId;
        workflowInstanceList.clear();
    }

    /**
     * 
     * Creates a new {@link WorkflowInstanceReferenceVisitor} instance.
     * 
     */
    public WorkflowInstanceReferenceVisitor() {
        workflowInstanceList.clear();
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        if (executed == false) {
            executed = true;
        }

        if (datatype instanceof NabuccoDatatype == false) {
            return;
        }

        // Search for the direct reference to workflow
        for (NabuccoProperty property : datatype.getProperties()) {

            if (property.getInstance() instanceof NabuccoDatatype) {

                NabuccoDatatype relatedDatatype = (NabuccoDatatype) property.getInstance();

                boolean references = this.analyseRelatedDatatype(relatedDatatype);
                if (references) {
                    retVal = true;
                    break;
                }
            }

        }

        if (retVal == true) {
            return;
        }

        // Search for the component relation reference
        ComponentRelationContainer container = DatatypeAccessor.getInstance().getComponentRelation(datatype);

        if (!container.isEmpty()) {
            for (ComponentRelation<?> relation : container.getAllComponentRelations()) {

                // Skip when relation is null
                if (relation == null) {
                    continue;
                }

                // Skip when relation target is null
                NabuccoDatatype targetDatatype = relation.getTarget();

                boolean references = this.analyseRelatedDatatype(targetDatatype);
                if (references) {
                    retVal = true;
                    break;
                }
            }
        }

        if (retVal == true) {
            return;
        }

        // super.visit(datatype);
    }

    /**
     * Analyses the related datatype if it has a needed id and is a instance of the workflow
     * 
     * @param datatype
     *            the datatype to be analysed
     * @return true if it has or false if not
     */
    private boolean analyseRelatedDatatype(NabuccoDatatype datatype) {
        if (datatype == null || datatype.getId() == null) {
            return false;
        }

        if (datatype instanceof Instance == false) {
            return false;
        }

        workflowInstanceList.add((Instance) datatype);

        // Target holds the instance with the given ID!
        if (instanceId != null) {
            if (datatype.getId().equals(instanceId.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of visited workflow instances
     * 
     * @return list on instances
     */
    public List<Instance> getVisitedWorkflows() {
        return workflowInstanceList;
    }

    /**
     * Returns the result of the visitong of the datatype.
     * 
     * @return true if visited datatype references the given instance or false if not
     */
    public boolean hasReference() {
        if (instanceId == null) {
            throw new IllegalStateException("The visitor was not configured to check instance id.");
        }
        if (executed == false) {
            throw new IllegalStateException("The visitor have not visited a datatype. No valid result is avaliable.");
        }
        return retVal;
    }
}
