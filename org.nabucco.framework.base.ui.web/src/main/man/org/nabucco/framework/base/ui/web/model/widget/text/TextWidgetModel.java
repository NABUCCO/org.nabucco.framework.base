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
package org.nabucco.framework.base.ui.web.model.widget.text;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetModel;
import org.nabucco.framework.base.ui.web.model.widget.WidgetType;

/**
 * TextWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TextWidgetModel extends WidgetModel {

    private static final String JSON_TEXT = "text";

    private static final String JSON_LINK = "link";

    private String text;

    private String link;

    /**
     * Creates a new {@link TextWidgetModel} instance.
     * 
     * @param text
     *            text to show
     * @param link
     *            link for the text
     */
    public TextWidgetModel(String text, String link) {
        super(WidgetType.TEXT);
        this.setText(text);
        this.setLink(link);
    }

    /**
     * Setter for the text.
     * 
     * @param text
     *            The text to set.
     */
    private void setText(String text) {
        this.text = text;
    }

    /**
     * Getter for the text.
     * 
     * @return Returns the text.
     */
    private String getText() {
        return this.text;
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

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_TEXT, this.getText());
        retVal.add(JSON_LINK, this.getLink());
        return retVal;
    }
}
