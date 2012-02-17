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
import org.nabucco.framework.base.facade.datatype.image.ImageData;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * Priority
 *
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-01-04
 */
public class Priority extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String PRIORITYID = "priorityId";

    public static final String KEY = "key";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String ICON = "icon";

    private Identifier priorityId;

    private Key key;

    private Name name;

    private Description description;

    private ImageData icon;

    /** Constructs a new Priority instance. */
    public Priority() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Priority.
     */
    protected void cloneObject(Priority clone) {
        super.cloneObject(clone);
        if ((this.getPriorityId() != null)) {
            clone.setPriorityId(this.getPriorityId().cloneObject());
        }
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getIcon() != null)) {
            clone.setIcon(this.getIcon().cloneObject());
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
        propertyMap.put(PRIORITYID, PropertyDescriptorSupport.createBasetype(PRIORITYID, Identifier.class, 3,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(KEY,
                PropertyDescriptorSupport.createBasetype(KEY, Key.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(ICON,
                PropertyDescriptorSupport.createBasetype(ICON, ImageData.class, 7, PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Priority.getPropertyDescriptor(PRIORITYID), this.priorityId, null));
        properties.add(super.createProperty(Priority.getPropertyDescriptor(KEY), this.key, null));
        properties.add(super.createProperty(Priority.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(Priority.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Priority.getPropertyDescriptor(ICON), this.icon, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PRIORITYID) && (property.getType() == Identifier.class))) {
            this.setPriorityId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(KEY) && (property.getType() == Key.class))) {
            this.setKey(((Key) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ICON) && (property.getType() == ImageData.class))) {
            this.setIcon(((ImageData) property.getInstance()));
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
        final Priority other = ((Priority) obj);
        if ((this.priorityId == null)) {
            if ((other.priorityId != null))
                return false;
        } else if ((!this.priorityId.equals(other.priorityId)))
            return false;
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
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
        if ((this.icon == null)) {
            if ((other.icon != null))
                return false;
        } else if ((!this.icon.equals(other.icon)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.priorityId == null) ? 0 : this.priorityId.hashCode()));
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        return result;
    }

    @Override
    public Priority cloneObject() {
        Priority clone = new Priority();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getPriorityId.
     *
     * @return the Identifier.
     */
    public Identifier getPriorityId() {
        return this.priorityId;
    }

    /**
     * Missing description at method setPriorityId.
     *
     * @param priorityId the Identifier.
     */
    public void setPriorityId(Identifier priorityId) {
        this.priorityId = priorityId;
    }

    /**
     * Missing description at method setPriorityId.
     *
     * @param priorityId the Long.
     */
    public void setPriorityId(Long priorityId) {
        if ((this.priorityId == null)) {
            if ((priorityId == null)) {
                return;
            }
            this.priorityId = new Identifier();
        }
        this.priorityId.setValue(priorityId);
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
     * Missing description at method getIcon.
     *
     * @return the ImageData.
     */
    public ImageData getIcon() {
        return this.icon;
    }

    /**
     * Missing description at method setIcon.
     *
     * @param icon the ImageData.
     */
    public void setIcon(ImageData icon) {
        this.icon = icon;
    }

    /**
     * Missing description at method setIcon.
     *
     * @param icon the byte[].
     */
    public void setIcon(byte[] icon) {
        if ((this.icon == null)) {
            if ((icon == null)) {
                return;
            }
            this.icon = new ImageData();
        }
        this.icon.setValue(icon);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Priority.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Priority.class).getAllProperties();
    }
}
