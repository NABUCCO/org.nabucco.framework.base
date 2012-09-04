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
package org.nabucco.framework.base.facade.datatype.exporting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.text.TextContent;

/**
 * ExportContainer<p/>Data Container as counterpart to ExportRs<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2011-02-18
 */
public class ExportContainer extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,2147483647;u0,n;m1,1;", "l0,n;u0,n;m0,1;" };

    public static final String RESULT = "result";

    public static final String RESOURCEDATA = "resourceData";

    /** The serialized result as text */
    private TextContent result;

    /** The archived resources as byte array. */
    private Data resourceData;

    /** Constructs a new ExportContainer instance. */
    public ExportContainer() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ExportContainer.
     */
    protected void cloneObject(ExportContainer clone) {
        super.cloneObject(clone);
        if ((this.getResult() != null)) {
            clone.setResult(this.getResult().cloneObject());
        }
        if ((this.getResourceData() != null)) {
            clone.setResourceData(this.getResourceData().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(RESULT,
                PropertyDescriptorSupport.createBasetype(RESULT, TextContent.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(RESOURCEDATA,
                PropertyDescriptorSupport.createBasetype(RESOURCEDATA, Data.class, 4, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ExportContainer.getPropertyDescriptor(RESULT), this.result, null));
        properties.add(super.createProperty(ExportContainer.getPropertyDescriptor(RESOURCEDATA), this.resourceData,
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(RESULT) && (property.getType() == TextContent.class))) {
            this.setResult(((TextContent) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RESOURCEDATA) && (property.getType() == Data.class))) {
            this.setResourceData(((Data) property.getInstance()));
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
        final ExportContainer other = ((ExportContainer) obj);
        if ((this.result == null)) {
            if ((other.result != null))
                return false;
        } else if ((!this.result.equals(other.result)))
            return false;
        if ((this.resourceData == null)) {
            if ((other.resourceData != null))
                return false;
        } else if ((!this.resourceData.equals(other.resourceData)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.result == null) ? 0 : this.result.hashCode()));
        result = ((PRIME * result) + ((this.resourceData == null) ? 0 : this.resourceData.hashCode()));
        return result;
    }

    @Override
    public ExportContainer cloneObject() {
        ExportContainer clone = new ExportContainer();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The serialized result as text
     *
     * @return the TextContent.
     */
    public TextContent getResult() {
        return this.result;
    }

    /**
     * The serialized result as text
     *
     * @param result the TextContent.
     */
    public void setResult(TextContent result) {
        this.result = result;
    }

    /**
     * The serialized result as text
     *
     * @param result the String.
     */
    public void setResult(String result) {
        if ((this.result == null)) {
            if ((result == null)) {
                return;
            }
            this.result = new TextContent();
        }
        this.result.setValue(result);
    }

    /**
     * The archived resources as byte array.
     *
     * @return the Data.
     */
    public Data getResourceData() {
        return this.resourceData;
    }

    /**
     * The archived resources as byte array.
     *
     * @param resourceData the Data.
     */
    public void setResourceData(Data resourceData) {
        this.resourceData = resourceData;
    }

    /**
     * The archived resources as byte array.
     *
     * @param resourceData the byte[].
     */
    public void setResourceData(byte[] resourceData) {
        if ((this.resourceData == null)) {
            if ((resourceData == null)) {
                return;
            }
            this.resourceData = new Data();
        }
        this.resourceData.setValue(resourceData);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ExportContainer.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ExportContainer.class).getAllProperties();
    }
}
