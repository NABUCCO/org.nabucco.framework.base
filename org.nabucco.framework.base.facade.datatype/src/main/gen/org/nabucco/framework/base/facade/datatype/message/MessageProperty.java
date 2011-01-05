/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Value;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * MessageProperty<p/>TODO<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-04-07
 */
public class MessageProperty extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "key", "value" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;" };

    private Name key;

    private Value value;

    /** Constructs a new MessageProperty instance. */
    public MessageProperty() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the MessageProperty.
     */
    protected void cloneObject(MessageProperty clone) {
        super.cloneObject(clone);
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
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
                PROPERTY_CONSTRAINTS[0], this.key));
        properties.add(new BasetypeProperty<Value>(PROPERTY_NAMES[1], Value.class,
                PROPERTY_CONSTRAINTS[1], this.value));
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
        final MessageProperty other = ((MessageProperty) obj);
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
            return false;
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<MessageProperty>\n");
        appendable.append(super.toString());
        appendable.append((("<key>" + this.key) + "</key>\n"));
        appendable.append((("<value>" + this.value) + "</value>\n"));
        appendable.append("</MessageProperty>\n");
        return appendable.toString();
    }

    @Override
    public MessageProperty cloneObject() {
        MessageProperty clone = new MessageProperty();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getKey.
     *
     * @return the Name.
     */
    public Name getKey() {
        return this.key;
    }

    /**
     * Missing description at method setKey.
     *
     * @param key the Name.
     */
    public void setKey(Name key) {
        this.key = key;
    }

    /**
     * Missing description at method setKey.
     *
     * @param key the String.
     */
    public void setKey(String key) {
        if ((this.key == null)) {
            this.key = new Name();
        }
        this.key.setValue(key);
    }

    /**
     * Missing description at method getValue.
     *
     * @return the Value.
     */
    public Value getValue() {
        return this.value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the Value.
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * Missing description at method setValue.
     *
     * @param value the String.
     */
    public void setValue(String value) {
        if ((this.value == null)) {
            this.value = new Value();
        }
        this.value.setValue(value);
    }
}
