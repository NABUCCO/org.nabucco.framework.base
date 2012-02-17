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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * ComponentRelationSearchRq
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class ComponentRelationSearchRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    /** Identifier of the source datatype to search for. */
    private Identifier sourceId;

    /** Identifier of additional source datatype to search for. */
    private List<Identifier> sourceIdList;

    /** Identifier of the target datatype to search for */
    private Identifier targetId;

    /** Identifier of additional target datatype to search for. */
    private List<Identifier> targetIdList;

    /** Type of the source datatype to search for */
    private ComponentRelationType relationType;

    /** Class of the componentRelation */
    private Name componentRelationClass;

    /** Constructs a new ComponentRelationSearchRq instance. */
    public ComponentRelationSearchRq() {
        super();
    }

    @Override
    public void init() {
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Identifier of the source datatype to search for
     * 
     * @return the Identifier.
     */
    public Identifier getSourceId() {
        return this.sourceId;
    }

    /**
     * Identifier of the source datatype to search for.
     * 
     * @param sourceId
     *            the Identifier.
     */
    public void setSourceId(Identifier sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Getter for additional source datatype ids to search for.
     * 
     * @return Returns the sourceIdList.
     */
    public List<Identifier> getSourceIdList() {
        if (this.sourceIdList == null) {
            this.sourceIdList = new ArrayList<Identifier>();
        }
        return this.sourceIdList;
    }

    /**
     * Identifier of the target datatype to search for.
     * 
     * @return the Identifier.
     */
    public Identifier getTargetId() {
        return this.targetId;
    }

    /**
     * Identifier of the target datatype to search for.
     * 
     * @param targetId
     *            the Identifier.
     */
    public void setTargetId(Identifier targetId) {
        this.targetId = targetId;
    }

    /**
     * Getter for additional target datatype ids to search for.
     * 
     * @return Returns the targetIdList.
     */
    public List<Identifier> getTargetIdList() {
        if (this.targetIdList == null) {
            this.targetIdList = new ArrayList<Identifier>();
        }
        return this.targetIdList;
    }

    /**
     * Type of the relation.
     * 
     * @return the ComponentRelationType.
     */
    public ComponentRelationType getRelationType() {
        return this.relationType;
    }

    /**
     * Type of the relation.
     * 
     * @param relationType
     *            the ComponentRelationType.
     */
    public void setRelationType(ComponentRelationType relationType) {
        this.relationType = relationType;
    }

    /**
     * Class of the componentRelation
     * 
     * @return the Name.
     */
    public Name getComponentRelationClass() {
        return this.componentRelationClass;
    }

    /**
     * Class of the componentRelation
     * 
     * @param componentRelationClass
     *            the Name.
     */
    public void setComponentRelationClass(Name componentRelationClass) {
        this.componentRelationClass = componentRelationClass;
    }
}
