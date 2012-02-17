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
package org.nabucco.framework.base.facade.datatype.geo;

import java.util.Collections;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * LocationType<p/>Enumeration of all supported types of a geo location.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-05-04
 */
public enum LocationType implements Enumeration {
    /** Continent Geo Location */
    CONTINENT("CN"),
    /** State Geo Location */
    STATE("ST"),
    /** ZipCode Geo Location */
    ZIP("ZC"),
    /** City Geo Location */
    CITY("CI"),
    /** Country Geo Location */
    COUNTRY("CO"),
    /** Street Geo Location */
    STREET("SR"),
    /** Point of interrest Geo Location */
    POINT_OF_INTERREST("PO"), ;

    private String id;

    /**
     * Constructs a new LocationType instance.
     *
     * @param id the String.
     */
    LocationType(String id) {
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
     * @return the LocationType.
     */
    public static LocationType valueOfId(String literalId) {
        for (LocationType enumeration : LocationType.values()) {
            if (enumeration.getId().equalsIgnoreCase(literalId)) {
                return enumeration;
            }
        }
        return null;
    }
}
