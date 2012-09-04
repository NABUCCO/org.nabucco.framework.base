/*
 * Copyright 2012 PRODYNA AG
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
 */
package org.nabucco.framework.base.ui.web.component.common.controller.resource;

/**
 * ResourceContentType
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public enum ResourceContentType {

    XML("text/xml", "xml"),

    CSV("text/comma-separated-values", "csv");

    String contentType;

    String extension;

    /**
     * 
     * Creates a new {@link ResourceContentType} instance.
     * 
     * @param contentType
     *            type of content
     * @param extension
     *            extension to use
     */
    private ResourceContentType(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    /**
     * Getter for the content type
     * 
     * @return string to be set in a response header
     */
    public String getContentTypeString() {
        return contentType;
    }

    /**
     * Getter for the content extension
     * 
     * @return extension like xml
     */
    public String getTypeExtension() {
        return extension;
    }
}
