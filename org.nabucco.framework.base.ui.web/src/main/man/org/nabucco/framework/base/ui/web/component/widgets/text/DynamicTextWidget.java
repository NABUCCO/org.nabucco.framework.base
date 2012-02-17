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
package org.nabucco.framework.base.ui.web.component.widgets.text;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.DynamicTextWidgetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.component.widgets.WidgetElement;
import org.nabucco.framework.base.ui.web.model.widget.dynamictext.DefaultDynamicTextWidgetContentProvider;
import org.nabucco.framework.base.ui.web.model.widget.dynamictext.DynamicTextWidgetContentProvider;
import org.nabucco.framework.base.ui.web.model.widget.dynamictext.DynamicTextWidgetModel;

/**
 * DynamicTextWidget
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DynamicTextWidget extends WidgetElement {

    private static final long serialVersionUID = 1L;

    /** Logger */
    protected static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DynamicTextWidget.class);

    /**
     * Creates a new {@link DynamicTextWidget} instance.
     * 
     * @param type
     */
    public DynamicTextWidget(DynamicTextWidgetExtension widgetExtension) {
        super(widgetExtension);
    }

    @Override
    public void init() throws ExtensionException {
        super.init();

        DynamicTextWidgetExtension extension = this.getWidgetExtension();

        Class<DynamicTextWidgetContentProvider> contentProviderClass = PropertyLoader.loadProperty(extension
                .getContentProvider());

        DynamicTextWidgetContentProvider contentProvider = null;

        if (contentProviderClass == null) {
            contentProvider = new DefaultDynamicTextWidgetContentProvider();
            logger.warning("No Content provider is defined for the dynamictext widget"
                    + extension.getIdentifier().getValue());
        } else {
            try {
                contentProvider = contentProviderClass.newInstance();

            } catch (InstantiationException e) {
                throw new ExtensionException("Cannot instanciate given dynamic text content provider '"
                        + contentProviderClass.getName() + "'", e);
            } catch (IllegalAccessException e) {
                throw new ExtensionException("Cannot instanciate given dynamic text content provider '"
                        + contentProviderClass.getName() + "', illegal access", e);
            }
        }

        DynamicTextWidgetModel model = new DynamicTextWidgetModel(contentProvider);
        super.setModel(model);
    }

    /**
     * Getter for the widgetExtension.
     * 
     * @return Returns the widgetExtension.
     */
    private DynamicTextWidgetExtension getWidgetExtension() {
        return (DynamicTextWidgetExtension) super.getExtension();
    }

}
