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
package org.nabucco.framework.base.facade.message.componentrelation;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * 
 * ComponentRelationMsg
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class ComponentRelationMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private ComponentRelation<?> componentRelation;

    /** Constructs a new ComponentRelationMsg instance. */
    public ComponentRelationMsg() {
        super();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
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
        final ComponentRelationMsg other = ((ComponentRelationMsg) obj);
        if ((this.componentRelation == null)) {
            if ((other.componentRelation != null))
                return false;
        } else if ((!this.componentRelation.equals(other.componentRelation)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.componentRelation == null) ? 0 : this.componentRelation
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<ComponentRelationMsg>\n");
        appendable.append(super.toString());
        appendable
                .append((("<componentRelation>" + this.componentRelation) + "</componentRelation>\n"));
        appendable.append("</ComponentRelationMsg>\n");
        return appendable.toString();
    }

    /**
     * Missing description at method getComponentRelation.
     * 
     * @return the ComponentRelation.
     */
    public ComponentRelation<?> getComponentRelation() {
        return this.componentRelation;
    }

    /**
     * Missing description at method setComponentRelation.
     * 
     * @param componentRelation
     *            the ComponentRelation.
     */
    public void setComponentRelation(ComponentRelation<?> componentRelation) {
        this.componentRelation = componentRelation;
    }
}
