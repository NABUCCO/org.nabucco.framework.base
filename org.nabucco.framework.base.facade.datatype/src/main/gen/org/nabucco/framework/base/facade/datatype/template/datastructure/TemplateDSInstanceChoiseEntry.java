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
package org.nabucco.framework.base.facade.datatype.template.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceItem;

/**
 * TemplateDSInstanceChoiseEntry<p/>The instance of the composite datatastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-05
 */
public class TemplateDSInstanceChoiseEntry extends TemplateDSInstanceItem implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "l1,1024;u0,n;m1,1;" };

    public static final String VALUE = "value";

    public static final String CODEPATH = "codePath";

    /** The value of the instance item */
    private Code value;

    /** The code path of the code */
    private CodePath codePath;

    /** Constructs a new TemplateDSInstanceChoiseEntry instance. */
    public TemplateDSInstanceChoiseEntry() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDSInstanceChoiseEntry.
     */
    protected void cloneObject(TemplateDSInstanceChoiseEntry clone) {
        super.cloneObject(clone);
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
        }
        if ((this.getCodePath() != null)) {
            clone.setCodePath(this.getCodePath().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateDSInstanceItem.class).getPropertyMap());
        propertyMap.put(VALUE, PropertyDescriptorSupport.createDatatype(VALUE, Code.class, 4, PROPERTY_CONSTRAINTS[0],
                false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CODEPATH,
                PropertyDescriptorSupport.createBasetype(CODEPATH, CodePath.class, 5, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateDSInstanceChoiseEntry.getPropertyDescriptor(VALUE),
                this.getValue(), null));
        properties.add(super.createProperty(TemplateDSInstanceChoiseEntry.getPropertyDescriptor(CODEPATH),
                this.codePath, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(VALUE) && (property.getType() == Code.class))) {
            this.setValue(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CODEPATH) && (property.getType() == CodePath.class))) {
            this.setCodePath(((CodePath) property.getInstance()));
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
        final TemplateDSInstanceChoiseEntry other = ((TemplateDSInstanceChoiseEntry) obj);
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        if ((this.codePath == null)) {
            if ((other.codePath != null))
                return false;
        } else if ((!this.codePath.equals(other.codePath)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        result = ((PRIME * result) + ((this.codePath == null) ? 0 : this.codePath.hashCode()));
        return result;
    }

    @Override
    public TemplateDSInstanceChoiseEntry cloneObject() {
        TemplateDSInstanceChoiseEntry clone = new TemplateDSInstanceChoiseEntry();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The value of the instance item
     *
     * @param value the Code.
     */
    public void setValue(Code value) {
        this.value = value;
    }

    /**
     * The value of the instance item
     *
     * @return the Code.
     */
    public Code getValue() {
        return this.value;
    }

    /**
     * The code path of the code
     *
     * @return the CodePath.
     */
    public CodePath getCodePath() {
        return this.codePath;
    }

    /**
     * The code path of the code
     *
     * @param codePath the CodePath.
     */
    public void setCodePath(CodePath codePath) {
        this.codePath = codePath;
    }

    /**
     * The code path of the code
     *
     * @param codePath the String.
     */
    public void setCodePath(String codePath) {
        if ((this.codePath == null)) {
            if ((codePath == null)) {
                return;
            }
            this.codePath = new CodePath();
        }
        this.codePath.setValue(codePath);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateDSInstanceChoiseEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDSInstanceChoiseEntry.class).getAllProperties();
    }
}
