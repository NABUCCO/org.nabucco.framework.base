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
package org.nabucco.framework.base.facade.datatype.serialization.resource;

import org.nabucco.framework.base.facade.datatype.serialization.SerializationConstants;

/**
 * Resource
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class Resource {

    private static final char SEPARATOR = '/';

    private String name;

    private String path;

    private String comment;

    private byte[] content;

    private ResourceType type;

    /**
     * Creates a new {@link Resource} instance.
     * 
     * @param name
     *            the resource name
     * @param content
     *            the resource content
     * @param type
     *            the resource type
     */
    public Resource(String name, byte[] content, ResourceType type) {
        this.name = name;
        this.content = content;
        this.type = type;
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
     * Getter for the content.
     * 
     * @return Returns the content.
     */
    public byte[] getContent() {
        return this.content;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public ResourceType getType() {
        return this.type;
    }

    /**
     * Getter for the comment.
     * 
     * @return Returns the comment.
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Setter for the comment.
     * 
     * @param comment
     *            The comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter for the path.
     * 
     * @return Returns the path.
     */
    public String getPath() {
        if (this.path == null) {
            this.path = this.createResourcePath();
        }
        return this.path;
    }

    /**
     * Create the archive path for the appropriate resource.
     * 
     * @return the archive path
     */
    private String createResourcePath() {
        if (this.type == ResourceType.XML) {
            StringBuilder path = new StringBuilder();
            path.append(this.name);
            path.append('.');
            path.append(this.type.getName());
            return path.toString();
        }

        StringBuilder path = new StringBuilder();
        path.append(SerializationConstants.RESOURCE_PREFIX);
        path.append(SEPARATOR);
        path.append(this.type.getName());
        path.append(SEPARATOR);
        path.append(this.name);
        path.append('.');
        path.append(SerializationConstants.RESOURCE_SUFFIX);
        return path.toString();
    }

}
