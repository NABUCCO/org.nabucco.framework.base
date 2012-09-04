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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencyExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DependencySetExtension<p/>The set with dependencies<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-12-28
 */
public class DependencySetExtension extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,n;" };

    public static final String AFFECTEDCONSTRAINT = "affectedConstraint";

    public static final String CONNECTIONTYPE = "connectionType";

    public static final String DEPENDENCIES = "dependencies";

    /** The constraint to be manipulated (VISIBLE, EDITABLE etc.) */
    private EnumerationProperty affectedConstraint;

    /** The type of the connection between more then one dependency */
    private EnumerationProperty connectionType;

    /** The dependencies of the dependencySet */
    private NabuccoList<DependencyExtension> dependencies;

    /** Constructs a new DependencySetExtension instance. */
    public DependencySetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DependencySetExtension.
     */
    protected void cloneObject(DependencySetExtension clone) {
        super.cloneObject(clone);
        if ((this.getAffectedConstraint() != null)) {
            clone.setAffectedConstraint(this.getAffectedConstraint().cloneObject());
        }
        if ((this.getConnectionType() != null)) {
            clone.setConnectionType(this.getConnectionType().cloneObject());
        }
        if ((this.dependencies != null)) {
            clone.dependencies = this.dependencies.cloneCollection();
        }
    }

    /**
     * Getter for the DependenciesJPA.
     *
     * @return the List<DependencyExtension>.
     */
    List<DependencyExtension> getDependenciesJPA() {
        if ((this.dependencies == null)) {
            this.dependencies = new NabuccoListImpl<DependencyExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<DependencyExtension>) this.dependencies).getDelegate();
    }

    /**
     * Setter for the DependenciesJPA.
     *
     * @param dependencies the List<DependencyExtension>.
     */
    void setDependenciesJPA(List<DependencyExtension> dependencies) {
        if ((this.dependencies == null)) {
            this.dependencies = new NabuccoListImpl<DependencyExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<DependencyExtension>) this.dependencies).setDelegate(dependencies);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(AFFECTEDCONSTRAINT, PropertyDescriptorSupport.createDatatype(AFFECTEDCONSTRAINT,
                EnumerationProperty.class, 3, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONNECTIONTYPE, PropertyDescriptorSupport.createDatatype(CONNECTIONTYPE,
                EnumerationProperty.class, 4, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEPENDENCIES, PropertyDescriptorSupport.createCollection(DEPENDENCIES,
                DependencyExtension.class, 5, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DependencySetExtension.getPropertyDescriptor(AFFECTEDCONSTRAINT),
                this.getAffectedConstraint(), null));
        properties.add(super.createProperty(DependencySetExtension.getPropertyDescriptor(CONNECTIONTYPE),
                this.getConnectionType(), null));
        properties.add(super.createProperty(DependencySetExtension.getPropertyDescriptor(DEPENDENCIES),
                this.dependencies, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(AFFECTEDCONSTRAINT) && (property.getType() == EnumerationProperty.class))) {
            this.setAffectedConstraint(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONNECTIONTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setConnectionType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DEPENDENCIES) && (property.getType() == DependencyExtension.class))) {
            this.dependencies = ((NabuccoList<DependencyExtension>) property.getInstance());
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
        final DependencySetExtension other = ((DependencySetExtension) obj);
        if ((this.affectedConstraint == null)) {
            if ((other.affectedConstraint != null))
                return false;
        } else if ((!this.affectedConstraint.equals(other.affectedConstraint)))
            return false;
        if ((this.connectionType == null)) {
            if ((other.connectionType != null))
                return false;
        } else if ((!this.connectionType.equals(other.connectionType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.affectedConstraint == null) ? 0 : this.affectedConstraint.hashCode()));
        result = ((PRIME * result) + ((this.connectionType == null) ? 0 : this.connectionType.hashCode()));
        return result;
    }

    @Override
    public DependencySetExtension cloneObject() {
        DependencySetExtension clone = new DependencySetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The constraint to be manipulated (VISIBLE, EDITABLE etc.)
     *
     * @param affectedConstraint the EnumerationProperty.
     */
    public void setAffectedConstraint(EnumerationProperty affectedConstraint) {
        this.affectedConstraint = affectedConstraint;
    }

    /**
     * The constraint to be manipulated (VISIBLE, EDITABLE etc.)
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getAffectedConstraint() {
        return this.affectedConstraint;
    }

    /**
     * The type of the connection between more then one dependency
     *
     * @param connectionType the EnumerationProperty.
     */
    public void setConnectionType(EnumerationProperty connectionType) {
        this.connectionType = connectionType;
    }

    /**
     * The type of the connection between more then one dependency
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getConnectionType() {
        return this.connectionType;
    }

    /**
     * The dependencies of the dependencySet
     *
     * @return the NabuccoList<DependencyExtension>.
     */
    public NabuccoList<DependencyExtension> getDependencies() {
        if ((this.dependencies == null)) {
            this.dependencies = new NabuccoListImpl<DependencyExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.dependencies;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DependencySetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DependencySetExtension.class).getAllProperties();
    }
}
