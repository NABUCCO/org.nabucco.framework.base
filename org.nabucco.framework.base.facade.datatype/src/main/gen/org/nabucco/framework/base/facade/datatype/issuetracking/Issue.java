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
package org.nabucco.framework.base.facade.datatype.issuetracking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DateTime;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.documentation.Documentation;
import org.nabucco.framework.base.facade.datatype.issuetracking.IssueType;
import org.nabucco.framework.base.facade.datatype.issuetracking.Priority;
import org.nabucco.framework.base.facade.datatype.issuetracking.Project;
import org.nabucco.framework.base.facade.datatype.issuetracking.ProjectComponent;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * Issue<p/>An Issue<p/>
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-01-04
 */
public class Issue extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "m0,1;", "m0,1;",
            "m0,n;", "m0,1;", "l0,255;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "l0,100000;u0,n;m0,1;",
            "m0,n;", "m0,n;", "m0,n;" };

    public static final String ISSUEID = "issueId";

    public static final String NAME = "name";

    public static final String PROJECT = "project";

    public static final String ISSUETYPE = "issueType";

    public static final String COMPONENTS = "components";

    public static final String PRIORITY = "priority";

    public static final String SUMMARY = "summary";

    public static final String DUEDATE = "dueDate";

    public static final String KEY = "key";

    public static final String DESCRIPTION = "description";

    public static final String SCREENSHOTATTACHMENTS = "screenshotAttachments";

    public static final String FILEATTACHMENTS = "fileAttachments";

    public static final String AFFECTEDVERSIONS = "affectedVersions";

    private Identifier issueId;

    private Name name;

    private Project project;

    private IssueType issueType;

    private NabuccoList<ProjectComponent> components;

    private Priority priority;

    private Description summary;

    private DateTime dueDate;

    private Key key;

    private Documentation description;

    private NabuccoList<ScreenshotAttachment> screenshotAttachments;

    private NabuccoList<FileAttachment> fileAttachments;

    private NabuccoList<Version> affectedVersions;

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
        if ((this.getIssueId() != null)) {
            clone.setIssueId(this.getIssueId().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getProject() != null)) {
            clone.setProject(this.getProject().cloneObject());
        }
        if ((this.getIssueType() != null)) {
            clone.setIssueType(this.getIssueType().cloneObject());
        }
        if ((this.components != null)) {
            clone.components = this.components.cloneCollection();
        }
        if ((this.getPriority() != null)) {
            clone.setPriority(this.getPriority().cloneObject());
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
        if ((this.screenshotAttachments != null)) {
            clone.screenshotAttachments = this.screenshotAttachments.cloneCollection();
        }
        if ((this.fileAttachments != null)) {
            clone.fileAttachments = this.fileAttachments.cloneCollection();
        }
        if ((this.affectedVersions != null)) {
            clone.affectedVersions = this.affectedVersions.cloneCollection();
        }
    }

    /**
     * Getter for the ComponentsJPA.
     *
     * @return the List<ProjectComponent>.
     */
    List<ProjectComponent> getComponentsJPA() {
        if ((this.components == null)) {
            this.components = new NabuccoListImpl<ProjectComponent>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ProjectComponent>) this.components).getDelegate();
    }

    /**
     * Setter for the ComponentsJPA.
     *
     * @param components the List<ProjectComponent>.
     */
    void setComponentsJPA(List<ProjectComponent> components) {
        if ((this.components == null)) {
            this.components = new NabuccoListImpl<ProjectComponent>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ProjectComponent>) this.components).setDelegate(components);
    }

    /**
     * Getter for the ScreenshotAttachmentsJPA.
     *
     * @return the List<ScreenshotAttachment>.
     */
    List<ScreenshotAttachment> getScreenshotAttachmentsJPA() {
        if ((this.screenshotAttachments == null)) {
            this.screenshotAttachments = new NabuccoListImpl<ScreenshotAttachment>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ScreenshotAttachment>) this.screenshotAttachments).getDelegate();
    }

    /**
     * Setter for the ScreenshotAttachmentsJPA.
     *
     * @param screenshotAttachments the List<ScreenshotAttachment>.
     */
    void setScreenshotAttachmentsJPA(List<ScreenshotAttachment> screenshotAttachments) {
        if ((this.screenshotAttachments == null)) {
            this.screenshotAttachments = new NabuccoListImpl<ScreenshotAttachment>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ScreenshotAttachment>) this.screenshotAttachments).setDelegate(screenshotAttachments);
    }

    /**
     * Getter for the FileAttachmentsJPA.
     *
     * @return the List<FileAttachment>.
     */
    List<FileAttachment> getFileAttachmentsJPA() {
        if ((this.fileAttachments == null)) {
            this.fileAttachments = new NabuccoListImpl<FileAttachment>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<FileAttachment>) this.fileAttachments).getDelegate();
    }

    /**
     * Setter for the FileAttachmentsJPA.
     *
     * @param fileAttachments the List<FileAttachment>.
     */
    void setFileAttachmentsJPA(List<FileAttachment> fileAttachments) {
        if ((this.fileAttachments == null)) {
            this.fileAttachments = new NabuccoListImpl<FileAttachment>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<FileAttachment>) this.fileAttachments).setDelegate(fileAttachments);
    }

    /**
     * Getter for the AffectedVersionsJPA.
     *
     * @return the List<Version>.
     */
    List<Version> getAffectedVersionsJPA() {
        if ((this.affectedVersions == null)) {
            this.affectedVersions = new NabuccoListImpl<Version>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Version>) this.affectedVersions).getDelegate();
    }

    /**
     * Setter for the AffectedVersionsJPA.
     *
     * @param affectedVersions the List<Version>.
     */
    void setAffectedVersionsJPA(List<Version> affectedVersions) {
        if ((this.affectedVersions == null)) {
            this.affectedVersions = new NabuccoListImpl<Version>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Version>) this.affectedVersions).setDelegate(affectedVersions);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(ISSUEID,
                PropertyDescriptorSupport.createBasetype(ISSUEID, Identifier.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(PROJECT, PropertyDescriptorSupport.createDatatype(PROJECT, Project.class, 5,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ISSUETYPE, PropertyDescriptorSupport.createDatatype(ISSUETYPE, IssueType.class, 6,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COMPONENTS, PropertyDescriptorSupport.createCollection(COMPONENTS, ProjectComponent.class, 7,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PRIORITY, PropertyDescriptorSupport.createDatatype(PRIORITY, Priority.class, 8,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap
                .put(SUMMARY, PropertyDescriptorSupport.createBasetype(SUMMARY, Description.class, 9,
                        PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(DUEDATE,
                PropertyDescriptorSupport.createBasetype(DUEDATE, DateTime.class, 10, PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(KEY,
                PropertyDescriptorSupport.createBasetype(KEY, Key.class, 11, PROPERTY_CONSTRAINTS[8], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Documentation.class, 12,
                PROPERTY_CONSTRAINTS[9], false));
        propertyMap.put(SCREENSHOTATTACHMENTS, PropertyDescriptorSupport.createCollection(SCREENSHOTATTACHMENTS,
                ScreenshotAttachment.class, 13, PROPERTY_CONSTRAINTS[10], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FILEATTACHMENTS, PropertyDescriptorSupport.createCollection(FILEATTACHMENTS,
                FileAttachment.class, 14, PROPERTY_CONSTRAINTS[11], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(AFFECTEDVERSIONS, PropertyDescriptorSupport.createCollection(AFFECTEDVERSIONS, Version.class,
                15, PROPERTY_CONSTRAINTS[12], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Issue.getPropertyDescriptor(ISSUEID), this.issueId, null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(PROJECT), this.getProject(), null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(ISSUETYPE), this.getIssueType(), null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(COMPONENTS), this.components, null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(PRIORITY), this.getPriority(), null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(SUMMARY), this.summary, null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(DUEDATE), this.dueDate, null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(KEY), this.key, null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(SCREENSHOTATTACHMENTS),
                this.screenshotAttachments, null));
        properties.add(super.createProperty(Issue.getPropertyDescriptor(FILEATTACHMENTS), this.fileAttachments, null));
        properties
                .add(super.createProperty(Issue.getPropertyDescriptor(AFFECTEDVERSIONS), this.affectedVersions, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ISSUEID) && (property.getType() == Identifier.class))) {
            this.setIssueId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROJECT) && (property.getType() == Project.class))) {
            this.setProject(((Project) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ISSUETYPE) && (property.getType() == IssueType.class))) {
            this.setIssueType(((IssueType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMPONENTS) && (property.getType() == ProjectComponent.class))) {
            this.components = ((NabuccoList<ProjectComponent>) property.getInstance());
            return true;
        } else if ((property.getName().equals(PRIORITY) && (property.getType() == Priority.class))) {
            this.setPriority(((Priority) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUMMARY) && (property.getType() == Description.class))) {
            this.setSummary(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DUEDATE) && (property.getType() == DateTime.class))) {
            this.setDueDate(((DateTime) property.getInstance()));
            return true;
        } else if ((property.getName().equals(KEY) && (property.getType() == Key.class))) {
            this.setKey(((Key) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Documentation.class))) {
            this.setDescription(((Documentation) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SCREENSHOTATTACHMENTS) && (property.getType() == ScreenshotAttachment.class))) {
            this.screenshotAttachments = ((NabuccoList<ScreenshotAttachment>) property.getInstance());
            return true;
        } else if ((property.getName().equals(FILEATTACHMENTS) && (property.getType() == FileAttachment.class))) {
            this.fileAttachments = ((NabuccoList<FileAttachment>) property.getInstance());
            return true;
        } else if ((property.getName().equals(AFFECTEDVERSIONS) && (property.getType() == Version.class))) {
            this.affectedVersions = ((NabuccoList<Version>) property.getInstance());
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
        final Issue other = ((Issue) obj);
        if ((this.issueId == null)) {
            if ((other.issueId != null))
                return false;
        } else if ((!this.issueId.equals(other.issueId)))
            return false;
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
        if ((this.issueType == null)) {
            if ((other.issueType != null))
                return false;
        } else if ((!this.issueType.equals(other.issueType)))
            return false;
        if ((this.priority == null)) {
            if ((other.priority != null))
                return false;
        } else if ((!this.priority.equals(other.priority)))
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
        result = ((PRIME * result) + ((this.issueId == null) ? 0 : this.issueId.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.project == null) ? 0 : this.project.hashCode()));
        result = ((PRIME * result) + ((this.issueType == null) ? 0 : this.issueType.hashCode()));
        result = ((PRIME * result) + ((this.priority == null) ? 0 : this.priority.hashCode()));
        result = ((PRIME * result) + ((this.summary == null) ? 0 : this.summary.hashCode()));
        result = ((PRIME * result) + ((this.dueDate == null) ? 0 : this.dueDate.hashCode()));
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        return result;
    }

    @Override
    public Issue cloneObject() {
        Issue clone = new Issue();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getIssueId.
     *
     * @return the Identifier.
     */
    public Identifier getIssueId() {
        return this.issueId;
    }

    /**
     * Missing description at method setIssueId.
     *
     * @param issueId the Identifier.
     */
    public void setIssueId(Identifier issueId) {
        this.issueId = issueId;
    }

    /**
     * Missing description at method setIssueId.
     *
     * @param issueId the Long.
     */
    public void setIssueId(Long issueId) {
        if ((this.issueId == null)) {
            if ((issueId == null)) {
                return;
            }
            this.issueId = new Identifier();
        }
        this.issueId.setValue(issueId);
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
     * Missing description at method setProject.
     *
     * @param project the Project.
     */
    public void setProject(Project project) {
        this.project = project;
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
     * Missing description at method setIssueType.
     *
     * @param issueType the IssueType.
     */
    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
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
     * Missing description at method getComponents.
     *
     * @return the NabuccoList<ProjectComponent>.
     */
    public NabuccoList<ProjectComponent> getComponents() {
        if ((this.components == null)) {
            this.components = new NabuccoListImpl<ProjectComponent>(NabuccoCollectionState.INITIALIZED);
        }
        return this.components;
    }

    /**
     * Missing description at method setPriority.
     *
     * @param priority the Priority.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Missing description at method getPriority.
     *
     * @return the Priority.
     */
    public Priority getPriority() {
        return this.priority;
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
            if ((summary == null)) {
                return;
            }
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
            if ((dueDate == null)) {
                return;
            }
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
            if ((key == null)) {
                return;
            }
            this.key = new Key();
        }
        this.key.setValue(key);
    }

    /**
     * Missing description at method getDescription.
     *
     * @return the Documentation.
     */
    public Documentation getDescription() {
        return this.description;
    }

    /**
     * Missing description at method setDescription.
     *
     * @param description the Documentation.
     */
    public void setDescription(Documentation description) {
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
            this.description = new Documentation();
        }
        this.description.setValue(description);
    }

    /**
     * Missing description at method getScreenshotAttachments.
     *
     * @return the NabuccoList<ScreenshotAttachment>.
     */
    public NabuccoList<ScreenshotAttachment> getScreenshotAttachments() {
        if ((this.screenshotAttachments == null)) {
            this.screenshotAttachments = new NabuccoListImpl<ScreenshotAttachment>(NabuccoCollectionState.INITIALIZED);
        }
        return this.screenshotAttachments;
    }

    /**
     * Missing description at method getFileAttachments.
     *
     * @return the NabuccoList<FileAttachment>.
     */
    public NabuccoList<FileAttachment> getFileAttachments() {
        if ((this.fileAttachments == null)) {
            this.fileAttachments = new NabuccoListImpl<FileAttachment>(NabuccoCollectionState.INITIALIZED);
        }
        return this.fileAttachments;
    }

    /**
     * Missing description at method getAffectedVersions.
     *
     * @return the NabuccoList<Version>.
     */
    public NabuccoList<Version> getAffectedVersions() {
        if ((this.affectedVersions == null)) {
            this.affectedVersions = new NabuccoListImpl<Version>(NabuccoCollectionState.INITIALIZED);
        }
        return this.affectedVersions;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Issue.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Issue.class).getAllProperties();
    }
}
