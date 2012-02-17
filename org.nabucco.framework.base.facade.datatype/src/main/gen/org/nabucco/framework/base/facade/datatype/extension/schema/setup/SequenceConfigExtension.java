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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceTemplateExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SequenceConfigExtension<p/>Configuration for sequence generation.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceConfigExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "m1,n;" };

    public static final String GENERATORS = "generators";

    public static final String TEMPLATES = "templates";

    /** The list of configured generators. */
    private NabuccoList<SequenceGeneratorExtension> generators;

    /** The list of configured templates. */
    private NabuccoList<SequenceTemplateExtension> templates;

    /** Constructs a new SequenceConfigExtension instance. */
    public SequenceConfigExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SequenceConfigExtension.
     */
    protected void cloneObject(SequenceConfigExtension clone) {
        super.cloneObject(clone);
        if ((this.generators != null)) {
            clone.generators = this.generators.cloneCollection();
        }
        if ((this.templates != null)) {
            clone.templates = this.templates.cloneCollection();
        }
    }

    /**
     * Getter for the GeneratorsJPA.
     *
     * @return the List<SequenceGeneratorExtension>.
     */
    List<SequenceGeneratorExtension> getGeneratorsJPA() {
        if ((this.generators == null)) {
            this.generators = new NabuccoListImpl<SequenceGeneratorExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SequenceGeneratorExtension>) this.generators).getDelegate();
    }

    /**
     * Setter for the GeneratorsJPA.
     *
     * @param generators the List<SequenceGeneratorExtension>.
     */
    void setGeneratorsJPA(List<SequenceGeneratorExtension> generators) {
        if ((this.generators == null)) {
            this.generators = new NabuccoListImpl<SequenceGeneratorExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SequenceGeneratorExtension>) this.generators).setDelegate(generators);
    }

    /**
     * Getter for the TemplatesJPA.
     *
     * @return the List<SequenceTemplateExtension>.
     */
    List<SequenceTemplateExtension> getTemplatesJPA() {
        if ((this.templates == null)) {
            this.templates = new NabuccoListImpl<SequenceTemplateExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SequenceTemplateExtension>) this.templates).getDelegate();
    }

    /**
     * Setter for the TemplatesJPA.
     *
     * @param templates the List<SequenceTemplateExtension>.
     */
    void setTemplatesJPA(List<SequenceTemplateExtension> templates) {
        if ((this.templates == null)) {
            this.templates = new NabuccoListImpl<SequenceTemplateExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SequenceTemplateExtension>) this.templates).setDelegate(templates);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(GENERATORS, PropertyDescriptorSupport.createCollection(GENERATORS,
                SequenceGeneratorExtension.class, 2, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(TEMPLATES, PropertyDescriptorSupport
                .createCollection(TEMPLATES, SequenceTemplateExtension.class, 3, PROPERTY_CONSTRAINTS[1], false,
                        PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SequenceConfigExtension.getPropertyDescriptor(GENERATORS), this.generators,
                null));
        properties.add(super.createProperty(SequenceConfigExtension.getPropertyDescriptor(TEMPLATES), this.templates,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(GENERATORS) && (property.getType() == SequenceGeneratorExtension.class))) {
            this.generators = ((NabuccoList<SequenceGeneratorExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(TEMPLATES) && (property.getType() == SequenceTemplateExtension.class))) {
            this.templates = ((NabuccoList<SequenceTemplateExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public SequenceConfigExtension cloneObject() {
        SequenceConfigExtension clone = new SequenceConfigExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list of configured generators.
     *
     * @return the NabuccoList<SequenceGeneratorExtension>.
     */
    public NabuccoList<SequenceGeneratorExtension> getGenerators() {
        if ((this.generators == null)) {
            this.generators = new NabuccoListImpl<SequenceGeneratorExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.generators;
    }

    /**
     * The list of configured templates.
     *
     * @return the NabuccoList<SequenceTemplateExtension>.
     */
    public NabuccoList<SequenceTemplateExtension> getTemplates() {
        if ((this.templates == null)) {
            this.templates = new NabuccoListImpl<SequenceTemplateExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.templates;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SequenceConfigExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SequenceConfigExtension.class).getAllProperties();
    }
}
