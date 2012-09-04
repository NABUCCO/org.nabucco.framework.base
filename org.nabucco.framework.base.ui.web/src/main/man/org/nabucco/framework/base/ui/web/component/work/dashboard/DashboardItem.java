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
package org.nabucco.framework.base.ui.web.component.work.dashboard;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetReferenceExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.DashboardExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.DashboardWidgetExtension;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.WorkItemType;
import org.nabucco.framework.base.ui.web.component.work.dashboard.widget.DashboardWidget;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dashboard.DashboardItemModel;

/**
 * An overview page with optional statistics for a perspective.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public final class DashboardItem extends WorkItem {

    /**
     * Creates a new {@link DashboardItem} instance.
     * 
     * @param instanceId
     *            the dashboard instance id
     * @param extension
     *            the dashboard extension
     */
    public DashboardItem(String instanceId, DashboardExtension extension) {
        super(instanceId, extension, WorkItemType.DASHBOARD);
    }

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_WIDGETS = "widgets";

    private static final String JSON_WIDGET = "widget";

    @Override
    public void init() throws ExtensionException {
        // Inilialize model
        super.init();

        // Add widgets
        DashboardExtension ext = this.getExtension();
        NabuccoList<WidgetReferenceExtension> widgets = ext.getWidgets();

        for (WidgetReferenceExtension widget : widgets) {
            String id = PropertyLoader.loadProperty(widget.getIdentifier());
            String widgetId = PropertyLoader.loadProperty(widget.getWidget());
            String position = PropertyLoader.loadProperty(widget.getPosition());
            String hint = PropertyLoader.loadProperty(widget.getHint());

            DashboardWidgetExtension widgetExtension = (DashboardWidgetExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_DASHBOARD_WIDGET, widgetId);

            DashboardWidget referencedWidget = new DashboardWidget(widgetExtension);
            referencedWidget.init();
            this.addElement(id, referencedWidget);

            this.getModel().addWidget(id, referencedWidget.getModel(), position, hint);
        }
    }

    @Override
    protected DashboardExtension getExtension() {
        return (DashboardExtension) super.getExtension();
    }

    @Override
    public DashboardItemModel getModel() {
        return (DashboardItemModel) super.getModel();
    }

    /**
     * Accepts the web element visitor. Overload this function to let element be visited
     * 
     * @param visitor
     *            visitor to be accepted
     * @param context
     *            context of the visitor
     */
    @Override
    public <T extends WebElementVisitorContext> void accept(WebElementVisitor<T> visitor, T context)
            throws VisitorException {
        if (visitor != null) {
            visitor.visit(this, context);
        }
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        JsonList widgets = new JsonList();

        for (String widgetId : this.getElementIds()) {
            JsonMap widgetJson = new JsonMap();
            WebElement widget = this.getElement(widgetId);

            widgetJson.add(JSON_ID, widgetId);
            widgetJson.add(JSON_WIDGET, widget.toJson());

            widgets.add(widgetJson);
        }

        json.add(JSON_WIDGETS, widgets);
        json.add(JSON_MODEL, this.getModel().toJson());
        return json;
    }
}
