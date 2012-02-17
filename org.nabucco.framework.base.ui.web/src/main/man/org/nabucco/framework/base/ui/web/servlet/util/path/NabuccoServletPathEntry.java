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
package org.nabucco.framework.base.ui.web.servlet.util.path;

/**
 * NabuccoServletPathEntry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoServletPathEntry {

    /** The entry value */
    private final String value;

    /** The entry type */
    private final NabuccoServletPathType type;

    /** The previous entry */
    private final NabuccoServletPathEntry previous;

    /** The next entry */
    private NabuccoServletPathEntry next;

    /**
     * Creates a new {@link NabuccoServletPathEntry} instance.
     * 
     * @param value
     *            the path entry name
     */
    NabuccoServletPathEntry(String value) {
        this(value, null);
    }

    /**
     * Creates a new {@link NabuccoServletPathEntry} instance.
     * 
     * @param value
     *            the path entry value
     * @param previous
     *            the previous path entry
     */
    NabuccoServletPathEntry(String value, NabuccoServletPathEntry previous) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot creaet Path Entry for value [null].");
        }

        this.value = value;
        this.type = NabuccoServletPathType.type(value);

        this.previous = previous;

        if (previous != null) {
            previous.setNext(this);
        }
    }

    /**
     * Getter for the value.
     * 
     * @return Returns the value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public NabuccoServletPathType getType() {
        return this.type;
    }

    /**
     * Getter for the previous.
     * 
     * @return Returns the previous.
     */
    public NabuccoServletPathEntry getPrevious() {
        return this.previous;
    }

    /**
     * Getter for the next.
     * 
     * @return Returns the next.
     */
    public NabuccoServletPathEntry getNext() {
        return this.next;
    }

    /**
     * Setter for the next.
     * 
     * @param next
     *            The next to set.
     */
    private void setNext(NabuccoServletPathEntry next) {
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("/");
        result.append(this.value);
        if (this.next != null) {
            result.append(this.next.toString());
        }
        return result.toString();
    }

}
