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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingFieldSet;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingFragment;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateMappingExtension<p/>Template Mapping Extension.<p/>
 *
 * @version 1.2
 * @author Nicolas Moser, PRODYNA AG, 2011-12-10
 */
public class TemplateMappingExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,n;" };

    public static final String TEMPLATE = "template";

    public static final String ROOT = "root";

    public static final String FRAGMENTS = "fragments";

    /** Name of the template to load. */
    private StringProperty template;

    /** The mapping fields. */
    private TemplateMappingFieldSet root;

    /** The mapping fragments. */
    private NabuccoList<TemplateMappingFragment> fragments;

    /** Constructs a new TemplateMappingExtension instance. */
    public TemplateMappingExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateMappingExtension.
     */
    protected void cloneObject(TemplateMappingExtension clone) {
        super.cloneObject(clone);
        if ((this.getTemplate() != null)) {
            clone.setTemplate(this.getTemplate().cloneObject());
        }
        if ((this.getRoot() != null)) {
            clone.setRoot(this.getRoot().cloneObject());
        }
        if ((this.fragments != null)) {
            clone.fragments = this.fragments.cloneCollection();
        }
    }

    /**
     * Getter for the FragmentsJPA.
     *
     * @return the List<TemplateMappingFragment>.
     */
    List<TemplateMappingFragment> getFragmentsJPA() {
        if ((this.fragments == null)) {
            this.fragments = new NabuccoListImpl<TemplateMappingFragment>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateMappingFragment>) this.fragments).getDelegate();
    }

    /**
     * Setter for the FragmentsJPA.
     *
     * @param fragments the List<TemplateMappingFragment>.
     */
    void setFragmentsJPA(List<TemplateMappingFragment> fragments) {
        if ((this.fragments == null)) {
            this.fragments = new NabuccoListImpl<TemplateMappingFragment>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateMappingFragment>) this.fragments).setDelegate(fragments);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(TEMPLATE, PropertyDescriptorSupport.createDatatype(TEMPLATE, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ROOT, PropertyDescriptorSupport.createDatatype(ROOT, TemplateMappingFieldSet.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FRAGMENTS, PropertyDescriptorSupport.createCollection(FRAGMENTS, TemplateMappingFragment.class,
                4, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateMappingExtension.getPropertyDescriptor(TEMPLATE),
                this.getTemplate(), null));
        properties
                .add(super.createProperty(TemplateMappingExtension.getPropertyDescriptor(ROOT), this.getRoot(), null));
        properties.add(super.createProperty(TemplateMappingExtension.getPropertyDescriptor(FRAGMENTS), this.fragments,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TEMPLATE) && (property.getType() == StringProperty.class))) {
            this.setTemplate(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ROOT) && (property.getType() == TemplateMappingFieldSet.class))) {
            this.setRoot(((TemplateMappingFieldSet) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FRAGMENTS) && (property.getType() == TemplateMappingFragment.class))) {
            this.fragments = ((NabuccoList<TemplateMappingFragment>) property.getInstance());
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
        final TemplateMappingExtension other = ((TemplateMappingExtension) obj);
        if ((this.template == null)) {
            if ((other.template != null))
                return false;
        } else if ((!this.template.equals(other.template)))
            return false;
        if ((this.root == null)) {
            if ((other.root != null))
                return false;
        } else if ((!this.root.equals(other.root)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.template == null) ? 0 : this.template.hashCode()));
        result = ((PRIME * result) + ((this.root == null) ? 0 : this.root.hashCode()));
        return result;
    }

    @Override
    public TemplateMappingExtension cloneObject() {
        TemplateMappingExtension clone = new TemplateMappingExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the template to load.
     *
     * @param template the StringProperty.
     */
    public void setTemplate(StringProperty template) {
        this.template = template;
    }

    /**
     * Name of the template to load.
     *
     * @return the StringProperty.
     */
    public StringProperty getTemplate() {
        return this.template;
    }

    /**
     * The mapping fields.
     *
     * @param root the TemplateMappingFieldSet.
     */
    public void setRoot(TemplateMappingFieldSet root) {
        this.root = root;
    }

    /**
     * The mapping fields.
     *
     * @return the TemplateMappingFieldSet.
     */
    public TemplateMappingFieldSet getRoot() {
        return this.root;
    }

    /**
     * The mapping fragments.
     *
     * @return the NabuccoList<TemplateMappingFragment>.
     */
    public NabuccoList<TemplateMappingFragment> getFragments() {
        if ((this.fragments == null)) {
            this.fragments = new NabuccoListImpl<TemplateMappingFragment>(NabuccoCollectionState.INITIALIZED);
        }
        return this.fragments;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateMappingExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateMappingExtension.class).getAllProperties();
    }
}
