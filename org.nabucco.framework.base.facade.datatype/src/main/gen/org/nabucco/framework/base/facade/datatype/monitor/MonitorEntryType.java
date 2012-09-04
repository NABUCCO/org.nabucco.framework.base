/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.monitor;

import java.util.Collections;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * MonitorEntryType<p/>Type of the monitor entry.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-06
 */
public enum MonitorEntryType implements Enumeration {
    /** Monitored service operation */
    SERVICE_OPERATION("S"),
    /** Monitored persistence operation */
    PERSISTENCE_OPERATION("P"),
    /** Monitored exception */
    EXCEPTION("E"), ;

    private String id;

    /**
     * Constructs a new MonitorEntryType instance.
     *
     * @param id the String.
     */
    MonitorEntryType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getOrdinal() {
        return this.ordinal();
    }

    @Override
    public Enumeration cloneObject() {
        return this;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        return Collections.emptySet();
    }

    /**
     * ValueOfId.
     *
     * @param literalId the String.
     * @return the MonitorEntryType.
     */
    public static MonitorEntryType valueOfId(String literalId) {
        for (MonitorEntryType enumeration : MonitorEntryType.values()) {
            if (enumeration.getId().equalsIgnoreCase(literalId)) {
                return enumeration;
            }
        }
        return null;
    }
}
