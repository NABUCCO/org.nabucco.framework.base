/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Generated with NABUCCO Generator 
*/
package org.nabucco.framework.base.facade.datatype.message;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Value;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorUtility;


/**
 * MessageProperty<p/>TODO<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-04-07
 */
public class MessageProperty extends NabuccoDatatype implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;" };

    private static final long serialVersionUID = 1L;

    private Name key;

    private Value value;

    /** Constructs a new MessageProperty instance. */
    public MessageProperty() {
        super();
        this.initDefaults();
    }

    /**
     * CloneObject.
     *
     * @param clone the MessageProperty.
     */
    protected void cloneObject(MessageProperty clone) {
        super.cloneObject(clone);
        if ((this.key != null)) {
            clone.key = this.key.cloneObject();
        }
        if ((this.value != null)) {
            clone.value = this.value.cloneObject();
        }
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    @Override
    public MessageProperty cloneObject() {
        MessageProperty clone = new MessageProperty();
        this.cloneObject(clone);
        return clone;
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public String[] getConstraints() {
        return CONSTRAINTS.clone();
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
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(), new String[] { "key", "value" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(super.getProperties(),
                new Object[] { this.getKey(), this.getValue() });
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
