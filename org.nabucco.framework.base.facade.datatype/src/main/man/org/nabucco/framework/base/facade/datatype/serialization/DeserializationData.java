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
package org.nabucco.framework.base.facade.datatype.serialization;

/**
 * DeserializationData
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DeserializationData {

    private String content;

    private byte[] resourceData;

    /**
     * Creates a new {@link DeserializationData} instance.
     * 
     * @param content
     *            the serialized content to deserialize
     */
    public DeserializationData(String content) {
        if (content == null) {
            throw new IllegalArgumentException("Cannot create DeserializationData without content.");
        }
        this.content = content;
    }

    /**
     * Getter for the content.
     * 
     * @return Returns the content.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Getter for the resourceData.
     * 
     * @return Returns the resourceData.
     */
    public byte[] getResourceData() {
        return this.resourceData;
    }

    /**
     * Setter for the resourceData.
     * 
     * @param resourceData
     *            The resourceData to set.
     */
    public void setResourceData(byte[] resourceData) {
        this.resourceData = resourceData;
    }

}
