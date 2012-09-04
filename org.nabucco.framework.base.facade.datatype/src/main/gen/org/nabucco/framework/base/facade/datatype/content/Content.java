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
package org.nabucco.framework.base.facade.datatype.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.FunctionalIdentifier;
import org.nabucco.framework.base.facade.datatype.MultiTenantDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * Content<p/>A object holding content information.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-10
 */
public class Content extends MultiTenantDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l3,12;u0,n;m1,1;",
            "l0,255;u0,n;m0,1;", "m0,1;", "l0,n;u0,n;m0,1;" };

    public static final String NAME = "name";

    public static final String OWNER = "owner";

    public static final String DESCRIPTION = "description";

    public static final String CONTENTTYPE = "contentType";

    public static final String CONTENTID = "contentId";

    /** Name of the content. */
    private Name name;

    /** Owner of the content. */
    private Owner owner;

    /** Description of the content. */
    private Description description;

    /** Type of the content. */
    private Code contentType;

    protected static final String CONTENTTYPE_CODEPATH = "org.nabucco.framework.content.contenttype";

    /** Functional ID of the content. */
    private FunctionalIdentifier contentId;

    /** Constructs a new Content instance. */
    public Content() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Content.
     */
    protected void cloneObject(Content clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getContentType() != null)) {
            clone.setContentType(this.getContentType().cloneObject());
        }
        if ((this.getContentId() != null)) {
            clone.setContentId(this.getContentId().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(MultiTenantDatatype.class).getPropertyMap());
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 4, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 5, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 6,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(CONTENTTYPE, PropertyDescriptorSupport.createDatatype(CONTENTTYPE, Code.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION, CONTENTTYPE_CODEPATH));
        propertyMap.put(CONTENTID, PropertyDescriptorSupport.createBasetype(CONTENTID, FunctionalIdentifier.class, 8,
                PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Content.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(Content.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Content.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Content.getPropertyDescriptor(CONTENTTYPE), this.getContentType(), null));
        properties.add(super.createProperty(Content.getPropertyDescriptor(CONTENTID), this.contentId, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTTYPE) && (property.getType() == Code.class))) {
            this.setContentType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTID) && (property.getType() == FunctionalIdentifier.class))) {
            this.setContentId(((FunctionalIdentifier) property.getInstance()));
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
        final Content other = ((Content) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.contentType == null)) {
            if ((other.contentType != null))
                return false;
        } else if ((!this.contentType.equals(other.contentType)))
            return false;
        if ((this.contentId == null)) {
            if ((other.contentId != null))
                return false;
        } else if ((!this.contentId.equals(other.contentId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.contentType == null) ? 0 : this.contentType.hashCode()));
        result = ((PRIME * result) + ((this.contentId == null) ? 0 : this.contentId.hashCode()));
        return result;
    }

    @Override
    public Content cloneObject() {
        Content clone = new Content();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the content.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Name of the content.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Name of the content.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * Owner of the content.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the content.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the content.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * Description of the content.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Description of the content.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Description of the content.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * Type of the content.
     *
     * @param contentType the Code.
     */
    public void setContentType(Code contentType) {
        this.contentType = contentType;
    }

    /**
     * Type of the content.
     *
     * @return the Code.
     */
    public Code getContentType() {
        return this.contentType;
    }

    /**
     * Functional ID of the content.
     *
     * @return the FunctionalIdentifier.
     */
    public FunctionalIdentifier getContentId() {
        return this.contentId;
    }

    /**
     * Functional ID of the content.
     *
     * @param contentId the FunctionalIdentifier.
     */
    public void setContentId(FunctionalIdentifier contentId) {
        this.contentId = contentId;
    }

    /**
     * Functional ID of the content.
     *
     * @param contentId the String.
     */
    public void setContentId(String contentId) {
        if ((this.contentId == null)) {
            if ((contentId == null)) {
                return;
            }
            this.contentId = new FunctionalIdentifier();
        }
        this.contentId.setValue(contentId);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Content.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Content.class).getAllProperties();
    }

    /**
     * Getter for the ContentTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getContentTypeCodePath() {
        return new CodePath(CONTENTTYPE_CODEPATH);
    }
}
