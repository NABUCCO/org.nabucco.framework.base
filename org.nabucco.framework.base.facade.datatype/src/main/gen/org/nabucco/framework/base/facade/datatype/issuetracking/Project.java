/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.issuetracking;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.issuetracking.IssueType;
import org.nabucco.framework.base.facade.datatype.net.Url;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.ListProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * Project<p/>A Project<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-01-04
 */
public class Project extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "name", "description", "key", "owner", "url",
            "issueTypes" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;", "m0,n;" };

    private Name name;

    private Description description;

    private Key key;

    private Owner owner;

    private Url url;

    private List<IssueType> issueTypes;

    private Long issueTypesRefId;

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
        if ((this.issueTypes instanceof NabuccoList<?>)) {
            clone.issueTypes = ((NabuccoList<IssueType>) this.issueTypes).cloneCollection();
        }
    }

    /**
     * Getter for the IssueTypesJPA.
     *
     * @return the List<IssueType>.
     */
    List<IssueType> getIssueTypesJPA() {
        if ((this.issueTypes == null)) {
            this.issueTypes = new NabuccoList<IssueType>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoList<IssueType>) this.issueTypes).getDelegate();
    }

    /**
     * Setter for the IssueTypesJPA.
     *
     * @param issueTypes the List<IssueType>.
     */
    void setIssueTypesJPA(List<IssueType> issueTypes) {
        if ((this.issueTypes == null)) {
            this.issueTypes = new NabuccoList<IssueType>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoList<IssueType>) this.issueTypes).setDelegate(issueTypes);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Name>(PROPERTY_NAMES[0], Name.class,
                PROPERTY_CONSTRAINTS[0], this.name));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[1], Description.class,
                PROPERTY_CONSTRAINTS[1], this.description));
        properties.add(new BasetypeProperty<Key>(PROPERTY_NAMES[2], Key.class,
                PROPERTY_CONSTRAINTS[2], this.key));
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[3], Owner.class,
                PROPERTY_CONSTRAINTS[3], this.owner));
        properties.add(new BasetypeProperty<Url>(PROPERTY_NAMES[4], Url.class,
                PROPERTY_CONSTRAINTS[4], this.url));
        properties.add(new ListProperty<IssueType>(PROPERTY_NAMES[5], IssueType.class,
                PROPERTY_CONSTRAINTS[5], this.issueTypes));
        return properties;
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
        if ((this.issueTypesRefId == null)) {
            if ((other.issueTypesRefId != null))
                return false;
        } else if ((!this.issueTypesRefId.equals(other.issueTypesRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.url == null) ? 0 : this.url.hashCode()));
        result = ((PRIME * result) + ((this.issueTypesRefId == null) ? 0 : this.issueTypesRefId
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Project>\n");
        appendable.append(super.toString());
        appendable.append((("<name>" + this.name) + "</name>\n"));
        appendable.append((("<description>" + this.description) + "</description>\n"));
        appendable.append((("<key>" + this.key) + "</key>\n"));
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<url>" + this.url) + "</url>\n"));
        appendable.append((("<issueTypesRefId>" + this.issueTypesRefId) + "</issueTypesRefId>\n"));
        appendable.append("</Project>\n");
        return appendable.toString();
    }

    @Override
    public Project cloneObject() {
        Project clone = new Project();
        this.cloneObject(clone);
        return clone;
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
            this.url = new Url();
        }
        this.url.setValue(url);
    }

    /**
     * Missing description at method getIssueTypes.
     *
     * @return the List<IssueType>.
     */
    public List<IssueType> getIssueTypes() {
        if ((this.issueTypes == null)) {
            this.issueTypes = new NabuccoList<IssueType>(NabuccoCollectionState.INITIALIZED);
        }
        return this.issueTypes;
    }

    /**
     * Getter for the IssueTypesRefId.
     *
     * @return the Long.
     */
    public Long getIssueTypesRefId() {
        return this.issueTypesRefId;
    }

    /**
     * Setter for the IssueTypesRefId.
     *
     * @param issueTypesRefId the Long.
     */
    public void setIssueTypesRefId(Long issueTypesRefId) {
        this.issueTypesRefId = issueTypesRefId;
    }
}
