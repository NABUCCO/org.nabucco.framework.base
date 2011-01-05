/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.issuetracking;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DateTime;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.issuetracking.IssueType;
import org.nabucco.framework.base.facade.datatype.issuetracking.Project;
import org.nabucco.framework.base.facade.datatype.issuetracking.ProjectComponent;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * Issue<p/>An Issue<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-01-04
 */
public class Issue extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "name", "project", "issueType", "component",
            "summary", "dueDate", "key", "description" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "m1,1;", "m1,1;", "m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;" };

    private Name name;

    private Project project;

    private Long projectRefId;

    private IssueType issueType;

    private Long issueTypeRefId;

    private ProjectComponent component;

    private Long componentRefId;

    private Description summary;

    private DateTime dueDate;

    private Key key;

    private Description description;

    /** Constructs a new Issue instance. */
    public Issue() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Issue.
     */
    protected void cloneObject(Issue clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getProject() != null)) {
            clone.setProject(this.getProject().cloneObject());
        }
        if ((this.getIssueType() != null)) {
            clone.setIssueType(this.getIssueType().cloneObject());
        }
        if ((this.getComponent() != null)) {
            clone.setComponent(this.getComponent().cloneObject());
        }
        if ((this.getSummary() != null)) {
            clone.setSummary(this.getSummary().cloneObject());
        }
        if ((this.getDueDate() != null)) {
            clone.setDueDate(this.getDueDate().cloneObject());
        }
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
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
        properties.add(new DatatypeProperty<Project>(PROPERTY_NAMES[1], Project.class,
                PROPERTY_CONSTRAINTS[1], this.project));
        properties.add(new DatatypeProperty<IssueType>(PROPERTY_NAMES[2], IssueType.class,
                PROPERTY_CONSTRAINTS[2], this.issueType));
        properties.add(new DatatypeProperty<ProjectComponent>(PROPERTY_NAMES[3],
                ProjectComponent.class, PROPERTY_CONSTRAINTS[3], this.component));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[4], Description.class,
                PROPERTY_CONSTRAINTS[4], this.summary));
        properties.add(new BasetypeProperty<DateTime>(PROPERTY_NAMES[5], DateTime.class,
                PROPERTY_CONSTRAINTS[5], this.dueDate));
        properties.add(new BasetypeProperty<Key>(PROPERTY_NAMES[6], Key.class,
                PROPERTY_CONSTRAINTS[6], this.key));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[7], Description.class,
                PROPERTY_CONSTRAINTS[7], this.description));
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
        final Issue other = ((Issue) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.project == null)) {
            if ((other.project != null))
                return false;
        } else if ((!this.project.equals(other.project)))
            return false;
        if ((this.projectRefId == null)) {
            if ((other.projectRefId != null))
                return false;
        } else if ((!this.projectRefId.equals(other.projectRefId)))
            return false;
        if ((this.issueType == null)) {
            if ((other.issueType != null))
                return false;
        } else if ((!this.issueType.equals(other.issueType)))
            return false;
        if ((this.issueTypeRefId == null)) {
            if ((other.issueTypeRefId != null))
                return false;
        } else if ((!this.issueTypeRefId.equals(other.issueTypeRefId)))
            return false;
        if ((this.component == null)) {
            if ((other.component != null))
                return false;
        } else if ((!this.component.equals(other.component)))
            return false;
        if ((this.componentRefId == null)) {
            if ((other.componentRefId != null))
                return false;
        } else if ((!this.componentRefId.equals(other.componentRefId)))
            return false;
        if ((this.summary == null)) {
            if ((other.summary != null))
                return false;
        } else if ((!this.summary.equals(other.summary)))
            return false;
        if ((this.dueDate == null)) {
            if ((other.dueDate != null))
                return false;
        } else if ((!this.dueDate.equals(other.dueDate)))
            return false;
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.project == null) ? 0 : this.project.hashCode()));
        result = ((PRIME * result) + ((this.projectRefId == null) ? 0 : this.projectRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.issueType == null) ? 0 : this.issueType.hashCode()));
        result = ((PRIME * result) + ((this.issueTypeRefId == null) ? 0 : this.issueTypeRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.component == null) ? 0 : this.component.hashCode()));
        result = ((PRIME * result) + ((this.componentRefId == null) ? 0 : this.componentRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.summary == null) ? 0 : this.summary.hashCode()));
        result = ((PRIME * result) + ((this.dueDate == null) ? 0 : this.dueDate.hashCode()));
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Issue>\n");
        appendable.append(super.toString());
        appendable.append((("<name>" + this.name) + "</name>\n"));
        appendable.append((("<project>" + this.project) + "</project>\n"));
        appendable.append((("<projectRefId>" + this.projectRefId) + "</projectRefId>\n"));
        appendable.append((("<issueType>" + this.issueType) + "</issueType>\n"));
        appendable.append((("<issueTypeRefId>" + this.issueTypeRefId) + "</issueTypeRefId>\n"));
        appendable.append((("<component>" + this.component) + "</component>\n"));
        appendable.append((("<componentRefId>" + this.componentRefId) + "</componentRefId>\n"));
        appendable.append((("<summary>" + this.summary) + "</summary>\n"));
        appendable.append((("<dueDate>" + this.dueDate) + "</dueDate>\n"));
        appendable.append((("<key>" + this.key) + "</key>\n"));
        appendable.append((("<description>" + this.description) + "</description>\n"));
        appendable.append("</Issue>\n");
        return appendable.toString();
    }

    @Override
    public Issue cloneObject() {
        Issue clone = new Issue();
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
     * Missing description at method setProject.
     *
     * @param project the Project.
     */
    public void setProject(Project project) {
        this.project = project;
        if ((project != null)) {
            this.setProjectRefId(project.getId());
        } else {
            this.setProjectRefId(null);
        }
    }

    /**
     * Missing description at method getProject.
     *
     * @return the Project.
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Getter for the ProjectRefId.
     *
     * @return the Long.
     */
    public Long getProjectRefId() {
        return this.projectRefId;
    }

    /**
     * Setter for the ProjectRefId.
     *
     * @param projectRefId the Long.
     */
    public void setProjectRefId(Long projectRefId) {
        this.projectRefId = projectRefId;
    }

    /**
     * Missing description at method setIssueType.
     *
     * @param issueType the IssueType.
     */
    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
        if ((issueType != null)) {
            this.setIssueTypeRefId(issueType.getId());
        } else {
            this.setIssueTypeRefId(null);
        }
    }

    /**
     * Missing description at method getIssueType.
     *
     * @return the IssueType.
     */
    public IssueType getIssueType() {
        return this.issueType;
    }

    /**
     * Getter for the IssueTypeRefId.
     *
     * @return the Long.
     */
    public Long getIssueTypeRefId() {
        return this.issueTypeRefId;
    }

    /**
     * Setter for the IssueTypeRefId.
     *
     * @param issueTypeRefId the Long.
     */
    public void setIssueTypeRefId(Long issueTypeRefId) {
        this.issueTypeRefId = issueTypeRefId;
    }

    /**
     * Missing description at method setComponent.
     *
     * @param component the ProjectComponent.
     */
    public void setComponent(ProjectComponent component) {
        this.component = component;
        if ((component != null)) {
            this.setComponentRefId(component.getId());
        } else {
            this.setComponentRefId(null);
        }
    }

    /**
     * Missing description at method getComponent.
     *
     * @return the ProjectComponent.
     */
    public ProjectComponent getComponent() {
        return this.component;
    }

    /**
     * Getter for the ComponentRefId.
     *
     * @return the Long.
     */
    public Long getComponentRefId() {
        return this.componentRefId;
    }

    /**
     * Setter for the ComponentRefId.
     *
     * @param componentRefId the Long.
     */
    public void setComponentRefId(Long componentRefId) {
        this.componentRefId = componentRefId;
    }

    /**
     * Missing description at method getSummary.
     *
     * @return the Description.
     */
    public Description getSummary() {
        return this.summary;
    }

    /**
     * Missing description at method setSummary.
     *
     * @param summary the Description.
     */
    public void setSummary(Description summary) {
        this.summary = summary;
    }

    /**
     * Missing description at method setSummary.
     *
     * @param summary the String.
     */
    public void setSummary(String summary) {
        if ((this.summary == null)) {
            this.summary = new Description();
        }
        this.summary.setValue(summary);
    }

    /**
     * Missing description at method getDueDate.
     *
     * @return the DateTime.
     */
    public DateTime getDueDate() {
        return this.dueDate;
    }

    /**
     * Missing description at method setDueDate.
     *
     * @param dueDate the DateTime.
     */
    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Missing description at method setDueDate.
     *
     * @param dueDate the Long.
     */
    public void setDueDate(Long dueDate) {
        if ((this.dueDate == null)) {
            this.dueDate = new DateTime();
        }
        this.dueDate.setValue(dueDate);
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
}
