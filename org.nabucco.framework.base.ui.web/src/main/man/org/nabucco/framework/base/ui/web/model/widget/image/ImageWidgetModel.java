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
package org.nabucco.framework.base.ui.web.model.widget.image;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetModel;
import org.nabucco.framework.base.ui.web.model.widget.WidgetType;

/**
 * TextWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ImageWidgetModel extends WidgetModel {

    private static final String JSON_LINK = "link";

    private static final String JSON_NAME = "name";

    private String image;

    private String link;

    private String tooltip;

    private String name;

    /**
     * Creates a new {@link ImageWidgetModel} instance.
     * 
     * @param image
     *            text to show
     * @param link
     *            link for the text
     */
    public ImageWidgetModel(String image, String link, String name, String tooltip) {
        super(WidgetType.IMAGE);
        this.setImage(image);
        this.setTooltip(tooltip);
        this.setLink(link);
        this.setName(name);
    }

    /**
     * Setter for the link.
     * 
     * @param link
     *            The link to set.
     */
    private void setLink(String link) {
        this.link = link;
    }

    /**
     * Getter for the link.
     * 
     * @return Returns the link.
     */
    private String getLink() {
        return this.link;
    }

    /**
     * Setter for the image.
     * 
     * @param image
     *            The image to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Getter for the image.
     * 
     * @return Returns the image.
     */
    public String getImage() {
        return this.image;
    }

    /**
     * Setter for the tooltip.
     * 
     * @param tooltip
     *            The tooltip to set.
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Getter for the tooltip.
     * 
     * @return Returns the tooltip.
     */
    public String getTooltip() {
        return this.tooltip;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_IMAGE, this.getImage());
        retVal.add(JSON_TOOLTIP, this.getTooltip());
        retVal.add(JSON_LINK, this.getLink());
        retVal.add(JSON_NAME, this.getName());
        return retVal;
    }

    /**
     * Setter for the name.
     * 
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }
}
