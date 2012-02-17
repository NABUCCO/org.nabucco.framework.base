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
package org.nabucco.framework.base.ui.web.model.widget.dynamictext;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetModel;
import org.nabucco.framework.base.ui.web.model.widget.WidgetType;

/**
 * DynamicTextWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DynamicTextWidgetModel extends WidgetModel {

    private static final String JSON_TEXT = "text";

    private DynamicTextWidgetContentProvider contentProvider;

    /**
     * Creates a new {@link DynamicTextWidgetModel} instance.
     * 
     * @param infoType
     *            type of the information to be dynamic loaded
     */
    public DynamicTextWidgetModel(DynamicTextWidgetContentProvider contentProvider) {
        super(WidgetType.DYNAMICTEXT);
        this.setContentProvider(contentProvider);
    }

    /**
     * Setter for the contentProvider.
     * 
     * @param contentProvider
     *            The contentProvider to set.
     */
    public void setContentProvider(DynamicTextWidgetContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }

    /**
     * Getter for the contentProvider.
     * 
     * @return Returns the contentProvider.
     */
    public DynamicTextWidgetContentProvider getContentProvider() {
        return this.contentProvider;
    }

    @Override
    public JsonMap toJson() {

        JsonMap retVal = super.toJson();
        retVal.add(JSON_TEXT, this.getContentProvider().getContent());
        return retVal;
    }
}
