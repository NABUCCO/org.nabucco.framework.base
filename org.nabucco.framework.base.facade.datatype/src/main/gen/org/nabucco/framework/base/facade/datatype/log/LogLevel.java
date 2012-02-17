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
package org.nabucco.framework.base.facade.datatype.log;

import java.util.Collections;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * LogLevel<p/>The severity of a logging statement.<p/>
 *
 * @version 1.0
 * @author Lasse Asbach, PRODYNA AG, 2010-07-22
 */
public enum LogLevel implements Enumeration {
    /** Highest severity, should be used for serious errors. */
    FATAL("F"),
    /** Error severity, should be used for exceptions. */
    ERROR("E"),
    /** Warning severity, should be used for considerable state. */
    WARNING("W"),
    /** Information serverity, should be used for instructive information. */
    INFO("I"),
    /** Debug serverity, should be used for administration/monitoring information. */
    DEBUG("D"),
    /** Lowest serverity, should be used for monitoring information. */
    TRACE("T"), ;

    private String id;

    /**
     * Constructs a new LogLevel instance.
     *
     * @param id the String.
     */
    LogLevel(String id) {
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
     * @return the LogLevel.
     */
    public static LogLevel valueOfId(String literalId) {
        for (LogLevel enumeration : LogLevel.values()) {
            if (enumeration.getId().equalsIgnoreCase(literalId)) {
                return enumeration;
            }
        }
        return null;
    }
}
