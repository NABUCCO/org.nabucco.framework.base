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
package org.nabucco.framework.base.ui.web.action.result;

import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * WebActionResultItem
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class WebActionResultItem implements Jsonable {

    private static final String JSON_ELEMENT_TYPE = "elementType";

    private WebActionResultItemType type;

    private WebElementType elementType;

    private String elementId;

    /**
     * Creates a new {@link WebActionResultItem} instance.
     * 
     * @param type
     *            the result type
     * @param elementType
     *            the element type
     */
    public WebActionResultItem(WebActionResultItemType type, WebElementType elementType) {
        this(type, elementType, null);
    }

    /**
     * Creates a new {@link WebActionResultItem} instance.
     * 
     * @param type
     *            the result type
     * @param elementType
     *            the element type
     * @param elementId
     *            the element id
     */
    public WebActionResultItem(WebActionResultItemType type, WebElementType elementType, String elementId) {
        this.type = type;
        this.elementType = elementType;
        this.elementId = elementId;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public final WebActionResultItemType getType() {
        return this.type;
    }

    /**
     * Getter for the elementType.
     * 
     * @return Returns the elementType.
     */
    public final WebElementType getElementType() {
        return this.elementType;
    }

    /**
     * Getter for the elementId.
     * 
     * @return Returns the elementId.
     */
    public final String getElementId() {
        return this.elementId;
    }

    /**
     * Setter for the elementId.
     * 
     * @param elementId
     *            The elementId to set.
     */
    public final void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Checks if the item is valid (For example if the editor that should be refreshed is already
     * closed)
     * 
     * @return true if valid
     */
    public boolean isValid() {
        return true;
    }


    @Override
    public JsonMap toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getElementId());
        json.add(JSON_TYPE, String.valueOf(this.getType()));
        json.add(JSON_ELEMENT_TYPE, String.valueOf(this.getElementType()));
        json.add(JSON_ACTION, this.getType());
        return json;
    }

    @Override
    public String toString() {
        return this.toJson().print();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        WebActionResultItem other = (WebActionResultItem) obj;
        if (this.elementId == null) {
            if (other.elementId != null) {
                return false;
            }
        } else if (!this.elementId.equals(other.elementId)) {
            return false;
        }
        if (this.elementType != other.elementType) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.elementId == null) ? 0 : this.elementId.hashCode());
        result = prime * result + ((this.elementType == null) ? 0 : this.elementType.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

}
