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
package org.nabucco.framework.base.facade.datatype.serialization.xml;

/**
 * XmlLink
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
final class XmlLink {

    private String name;

    private String source;

    private String target;

    /**
     * Creates a new {@link XmlLink} instance.
     * 
     * @param name
     *            the link name
     * @param source
     *            the source id
     * @param target
     *            the target id
     */
    public XmlLink(String name, String source, String target) {
        this.name = name;
        this.source = source;
        this.target = target;
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the source.
     * 
     * @return Returns the source.
     */
    public String getSource() {
        return this.source;
    }

    /**
     * Getter for the target.
     * 
     * @return Returns the target.
     */
    public String getTarget() {
        return this.target;
    }

    @Override
    public String toString() {
        return "<link name=\"" + this.name + "\" source=\"" + this.source + "\" target=\"" + this.target + "\" />";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.source == null) ? 0 : this.source.hashCode());
        result = prime * result + ((this.target == null) ? 0 : this.target.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        XmlLink other = (XmlLink) obj;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        if (this.source == null) {
            if (other.source != null)
                return false;
        } else if (!this.source.equals(other.source))
            return false;
        if (this.target == null) {
            if (other.target != null)
                return false;
        } else if (!this.target.equals(other.target))
            return false;
        return true;
    }

}
