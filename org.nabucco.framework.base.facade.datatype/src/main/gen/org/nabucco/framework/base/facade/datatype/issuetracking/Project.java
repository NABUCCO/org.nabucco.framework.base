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
package org.nabucco.framework.base.facade.datatype.issuetracking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.issuetracking.IssueType;
import org.nabucco.framework.base.facade.datatype.net.Url;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * Project<p/>A Project<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-01-04
 */
public class Project extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,n;u0,n;m1,1;", "l3,12;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "m0,n;" };

    public static final String PROJECTID = "projectId";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String KEY = "key";

    public static final String OWNER = "owner";

    public static final String URL = "url";

    public static final String ISSUETYPES = "issueTypes";

    private Identifier projectId;

    private Name name;

    private Description description;

    private Key key;

    private Owner owner;

    private Url url;

    private NabuccoList<IssueType> issueTypes;

    /** Constructs a new Project instance. */
    public Project() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Project.
     */
    protected void cloneObject(Project clone) {
        super.cloneObject(clone);
        if ((this.getProjectId() != null)) {
            clone.setProjectId(this.getProjectId().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getUrl() != null)) {
            clone.setUrl(this.getUrl().cloneObject());
        }
        if ((this.issueTypes != null)) {
            clone.issueTypes = this.issueTypes.cloneCollection();
        }
    }

    /**
     * Getter for the IssueTypesJPA.
     *
     * @return the List<IssueType>.
     */
    List<IssueType> getIssueTypesJPA() {
        if ((this.issueTypes == null)) {
            this.issueTypes = new NabuccoListImpl<IssueType>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<IssueType>) this.issueTypes).getDelegate();
    }

    /**
     * Setter for the IssueTypesJPA.
     *
     * @param issueTypes the List<IssueType>.
     */
    void setIssueTypesJPA(List<IssueType> issueTypes) {
        if ((this.issueTypes == null)) {
            this.issueTypes = new NabuccoListImpl<IssueType>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<IssueType>) this.issueTypes).setDelegate(issueTypes);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(PROJECTID, PropertyDescriptorSupport.createBasetype(PROJECTID, Identifier.class, 3,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 5,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(KEY,
                PropertyDescriptorSupport.createBasetype(KEY, Key.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(URL,
                PropertyDescriptorSupport.createBasetype(URL, Url.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(ISSUETYPES, PropertyDescriptorSupport.createCollection(ISSUETYPES, IssueType.class, 9,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Project.getPropertyDescriptor(PROJECTID), this.projectId, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(KEY), this.key, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(URL), this.url, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(ISSUETYPES), this.issueTypes, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PROJECTID) && (property.getType() == Identifier.class))) {
            this.setProjectId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(KEY) && (property.getType() == Key.class))) {
            this.setKey(((Key) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(URL) && (property.getType() == Url.class))) {
            this.setUrl(((Url) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ISSUETYPES) && (property.getType() == IssueType.class))) {
            this.issueTypes = ((NabuccoList<IssueType>) property.getInstance());
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
        final Project other = ((Project) obj);
        if ((this.projectId == null)) {
            if ((other.projectId != null))
                return false;
        } else if ((!this.projectId.equals(other.projectId)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.url == null)) {
            if ((other.url != null))
                return false;
        } else if ((!this.url.equals(other.url)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.projectId == null) ? 0 : this.projectId.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.url == null) ? 0 : this.url.hashCode()));
        return result;
    }

    @Override
    public Project cloneObject() {
        Project clone = new Project();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getProjectId.
     *
     * @return the Identifier.
     */
    public Identifier getProjectId() {
        return this.projectId;
    }

    /**
     * Missing description at method setProjectId.
     *
     * @param projectId the Identifier.
     */
    public void setProjectId(Identifier projectId) {
        this.projectId = projectId;
    }

    /**
     * Missing description at method setProjectId.
     *
     * @param projectId the Long.
     */
    public void setProjectId(Long projectId) {
        if ((this.projectId == null)) {
            if ((projectId == null)) {
                return;
            }
            this.projectId = new Identifier();
        }
        this.projectId.setValue(projectId);
    }

    /**
     * Missing description at method getName.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Missing description at method setName.
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
     * Missing description at method getDescription.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Missing description at method setDescription.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Missing description at method setDescription.
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
     * Missing description at method getKey.
     *
     * @return the Key.
     */
    public Key getKey() {
        return this.key;
    }

    /**
     * Missing description at method setKey.
     *
     * @param key the Key.
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Missing description at method setKey.
     *
     * @param key the String.
     */
    public void setKey(String key) {
        if ((this.key == null)) {
            if ((key == null)) {
                return;
            }
            this.key = new Key();
        }
        this.key.setValue(key);
    }

    /**
     * Missing description at method getOwner.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Missing description at method setOwner.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Missing description at method setOwner.
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
     * Missing description at method getUrl.
     *
     * @return the Url.
     */
    public Url getUrl() {
        return this.url;
    }

    /**
     * Missing description at method setUrl.
     *
     * @param url the Url.
     */
    public void setUrl(Url url) {
        this.url = url;
    }

    /**
     * Missing description at method setUrl.
     *
     * @param url the String.
     */
    public void setUrl(String url) {
        if ((this.url == null)) {
            if ((url == null)) {
                return;
            }
            this.url = new Url();
        }
        this.url.setValue(url);
    }

    /**
     * Missing description at method getIssueTypes.
     *
     * @return the NabuccoList<IssueType>.
     */
    public NabuccoList<IssueType> getIssueTypes() {
        if ((this.issueTypes == null)) {
            this.issueTypes = new NabuccoListImpl<IssueType>(NabuccoCollectionState.INITIALIZED);
        }
        return this.issueTypes;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Project.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Project.class).getAllProperties();
    }
}
