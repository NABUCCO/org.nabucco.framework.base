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
package org.nabucco.framework.base.ui.web.component.dialog.controls;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.LinkDialogControlExtension;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogLinkModel;

/**
 * DialogControl
 * 
 * Link is the text link with specified url that can be clicked on the ui
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogLinkControl extends DialogControl {

    private static final long serialVersionUID = 1L;

    private LinkDialogControlExtension extension;

    private DialogLinkModel model;

    /**
     * Creates a new {@link DialogLinkControl} instance.
     * 
     * @param extension
     *            the extension
     */
    public DialogLinkControl(LinkDialogControlExtension extension) {
        super(extension);

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        String id = this.getId();
        String label = this.getLabel();
        String tooltip = this.getTooltip();
        String position = this.getPosition();

        this.model = new DialogLinkModel(id, label, this.getUrl(), tooltip, position);

    }

    /**
     * Getter for the dialog control model
     */
    @Override
    public DialogLinkModel getModel() {
        return this.model;
    }

    /**
     * Returns the configured link url
     * 
     * @return
     */
    public String getUrl() {
        return PropertyLoader.loadProperty(this.extension.getUrl());
    }

}
