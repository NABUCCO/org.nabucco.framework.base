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
package org.nabucco.framework.base.facade.message.importing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.importing.ImportContainer;
import org.nabucco.framework.base.facade.datatype.importing.ImportContext;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * ImportRq<p/>Generic Request message of the Import services.<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2011-02-09
 */
public class ImportRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String IMPORTCONTEXT = "importContext";

    public static final String CONTAINER = "container";

    /** Import Context add remapped database id's while importing */
    private ImportContext importContext;

    /** Encapsulated Data used for import. */
    private ImportContainer container;

    /** Constructs a new ImportRq instance. */
    public ImportRq() {
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
        propertyMap.put(IMPORTCONTEXT, PropertyDescriptorSupport.createDatatype(IMPORTCONTEXT, ImportContext.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONTAINER, PropertyDescriptorSupport.createDatatype(CONTAINER, ImportContainer.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ImportRq.getPropertyDescriptor(IMPORTCONTEXT), this.getImportContext()));
        properties.add(super.createProperty(ImportRq.getPropertyDescriptor(CONTAINER), this.getContainer()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(IMPORTCONTEXT) && (property.getType() == ImportContext.class))) {
            this.setImportContext(((ImportContext) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTAINER) && (property.getType() == ImportContainer.class))) {
            this.setContainer(((ImportContainer) property.getInstance()));
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
        final ImportRq other = ((ImportRq) obj);
        if ((this.importContext == null)) {
            if ((other.importContext != null))
                return false;
        } else if ((!this.importContext.equals(other.importContext)))
            return false;
        if ((this.container == null)) {
            if ((other.container != null))
                return false;
        } else if ((!this.container.equals(other.container)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.importContext == null) ? 0 : this.importContext.hashCode()));
        result = ((PRIME * result) + ((this.container == null) ? 0 : this.container.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Import Context add remapped database id's while importing
     *
     * @return the ImportContext.
     */
    public ImportContext getImportContext() {
        return this.importContext;
    }

    /**
     * Import Context add remapped database id's while importing
     *
     * @param importContext the ImportContext.
     */
    public void setImportContext(ImportContext importContext) {
        this.importContext = importContext;
    }

    /**
     * Encapsulated Data used for import.
     *
     * @return the ImportContainer.
     */
    public ImportContainer getContainer() {
        return this.container;
    }

    /**
     * Encapsulated Data used for import.
     *
     * @param container the ImportContainer.
     */
    public void setContainer(ImportContainer container) {
        this.container = container;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ImportRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ImportRq.class).getAllProperties();
    }
}
