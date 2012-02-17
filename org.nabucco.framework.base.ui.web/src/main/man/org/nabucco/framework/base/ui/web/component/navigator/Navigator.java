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
package org.nabucco.framework.base.ui.web.component.navigator;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator.NavigatorEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator.NavigatorExtension;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * A {@link WebComponent} holding perspective specific navigations.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class Navigator extends WebComponent {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_PATH = "path";

    private static final String JSON_ENTRIES = "entries";

    /** The Navigator Extension. */
    private NavigatorExtension extension;

    /**
     * Creates a new {@link Navigator} instance.
     * 
     * @param extension
     *            the navigator extension
     */
    public Navigator(NavigatorExtension extension) {
        super(WebElementType.NAVIGATOR, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create navigator for extension 'null'.");
        }

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
    }

    /**
     * Getter for the navigator label.
     * 
     * @return the navigator label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap(JSON_LABEL, this.getLabel());

        json.add(JSON_ENTRIES, this.printEntries(this.extension.getNavigatorEntryList()));

        return json;
    }

    /**
     * Recursively prints a list of navigator entries into a json list.
     * 
     * @param entryList
     *            the list of navigator entry extensions
     * 
     * @return the json list
     */
    private JsonList printEntries(NabuccoList<NavigatorEntryExtension> entryList) {
        JsonList jsonEntryList = new JsonList();

        for (NavigatorEntryExtension entry : entryList) {
            JsonMap jsonEntry = new JsonMap();
            jsonEntry.add(JSON_ID, entry.getIdentifier().getValue());
            jsonEntry.add(JSON_LABEL, PropertyLoader.loadProperty(entry.getLabel()));
            jsonEntry.add(JSON_IMAGE, PropertyLoader.loadProperty(entry.getImage()));
            jsonEntry.add(JSON_TOOLTIP, PropertyLoader.loadProperty(entry.getTooltip()));
            jsonEntry.add(JSON_PATH, PropertyLoader.loadProperty(entry.getPath()));
            jsonEntry.add(JSON_ACTION, PropertyLoader.loadProperty(entry.getAction()));
            jsonEntry.add(JSON_ENTRIES, this.printEntries(entry.getNavigatorEntryList()));

            jsonEntryList.add(jsonEntry);
        }

        return jsonEntryList;
    }

}
