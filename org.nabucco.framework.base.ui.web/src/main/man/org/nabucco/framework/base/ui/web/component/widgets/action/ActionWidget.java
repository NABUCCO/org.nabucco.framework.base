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
package org.nabucco.framework.base.ui.web.component.widgets.action;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.ActionWidgetExtension;
import org.nabucco.framework.base.ui.web.component.widgets.WidgetElement;
import org.nabucco.framework.base.ui.web.component.widgets.text.DynamicTextWidget;
import org.nabucco.framework.base.ui.web.model.widget.action.ActionWidgetModel;

/**
 * TextWidget
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ActionWidget extends WidgetElement {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link DynamicTextWidget} instance.
     * 
     * @param type
     */
    public ActionWidget(ActionWidgetExtension widgetExtension) {
        super(widgetExtension);
    }

    @Override
    public void init() throws ExtensionException {
        super.init();

        String action = PropertyLoader.loadProperty(this.getWidgetExtension().getAction());
        String text = PropertyLoader.loadProperty(this.getWidgetExtension().getText());

        ActionWidgetModel model = new ActionWidgetModel(action, text);
        super.setModel(model);
    }

    /**
     * Getter for the widgetExtension.
     * 
     * @return Returns the widgetExtension.
     */
    public ActionWidgetExtension getWidgetExtension() {
        return (ActionWidgetExtension) super.getExtension();
    }

}
