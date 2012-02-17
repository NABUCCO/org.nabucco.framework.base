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
package org.nabucco.framework.base.ui.web.component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonValue;

/**
 * A web composite is a {@link WebElement} that may have one or more child elements.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class WebComposite extends WebElement {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    /** The child element map (linked for predictable iteration order). */
    private final Map<String, WebElement> elementMap = new LinkedHashMap<String, WebElement>();

    /**
     * Creates a new {@link WebComposite} instance.
     * 
     * @param type
     *            the web element type
     * @param extension
     *            the UI extension
     */
    public WebComposite(WebElementType type, UiExtension extension) {
        super(type, extension);
    }

    /**
     * Add a child element to the current element.
     * 
     * @param id
     *            the id of the child element
     * @param element
     *            the child element to add
     */
    protected void addElement(String id, WebElement element) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot add child element with id 'null'.");
        }
        if (element == null) {
            throw new IllegalArgumentException("Cannot add child element 'null'.");
        }

        switch (element.getAccessType()) {

        case ACTIVE: {
            this.elementMap.put(id, element);
            break;
        }
        case DISABLED: {
            this.elementMap.put(id, element);
            break;
        }
        case OBFUSCATED: {
            this.elementMap.put(id, element);
            break;
        }

        }

    }

    /**
     * Remove the child element with the given id.
     * 
     * @param id
     *            the id of the child elment to remove
     * 
     * @return the removed child element, or null if the element with the given id does not exist in
     *         this element
     */
    protected WebElement removeElement(String id) {
        return this.elementMap.remove(id);
    }

    /**
     * Returns if the id is already in map
     * 
     * @param id
     * @return
     */
    protected boolean containsElement(String id) {
        return this.elementMap.containsKey(id);
    }

    @Override
    public String[] getElementIds() {
        return this.elementMap.keySet().toArray(new String[this.elementMap.size()]);
    }

    @Override
    public List<WebElement> getElements() {
        return new ArrayList<WebElement>(this.elementMap.values());
    }

    @Override
    public WebElement getElement(String id) {
        return this.elementMap.get(id);
    }

    @Override
    public String toString() {
        return this.elementMap.toString();
    }

    @Override
    public JsonElement toJson() {
        return new JsonValue(null);
    }

}
