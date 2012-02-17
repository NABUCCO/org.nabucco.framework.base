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
package org.nabucco.framework.base.ui.web.model.dialog.controls;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;

/**
 * DialogLinkModel
 * 
 * The link model for the dialog. The model that allow dialog to have a link AND change it to
 * runtime if nessecery.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogLinkModel extends DialogGridModelElement {

    private static final String JSON_URL = "url";

    private String url;

    /**
     * Creates a new {@link DialogLinkModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param label
     *            the label of the control
     * @param url
     *            the url to link to
     * @param tooltip
     *            the tooltip for the link
     * @param position
     *            the position of the link
     */
    public DialogLinkModel(String id, String label, String url, String tooltip, String position) {
        super(id, label, tooltip, DialogControlType.TEXTLINK, position);

        this.url = url;
    }

    /**
     * Setter for the url link
     * 
     * @param url
     *            url to load
     */
    public void setUrl(String url) {

        if (url == null) {
            throw new IllegalArgumentException("Cannot set url 'null' to the dialog link model.");
        }

        this.url = url;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_URL, this.url);
        return retVal;
    }

}
