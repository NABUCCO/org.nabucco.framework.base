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
package org.nabucco.framework.base.ui.web.component.common.grid;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.GridWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.ActionWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.CalendarWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.DialogWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.DynamicTextWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.ImageWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.IndicatorWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.SearchWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.TextWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetReferenceExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.grid.gridcontroller.GridController;
import org.nabucco.framework.base.ui.web.component.widgets.WidgetElement;
import org.nabucco.framework.base.ui.web.component.widgets.action.ActionWidget;
import org.nabucco.framework.base.ui.web.component.widgets.calendar.CalendarWidget;
import org.nabucco.framework.base.ui.web.component.widgets.dialog.DialogWidget;
import org.nabucco.framework.base.ui.web.component.widgets.image.ImageWidget;
import org.nabucco.framework.base.ui.web.component.widgets.indicator.IndicatorWidget;
import org.nabucco.framework.base.ui.web.component.widgets.search.SearchWidget;
import org.nabucco.framework.base.ui.web.component.widgets.text.DynamicTextWidget;
import org.nabucco.framework.base.ui.web.component.widgets.text.TextWidget;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetType;

/**
 * GridElement
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class GridWidgetElement extends WebComposite {

    private static final String JSON_WIDGETS = "widgets";

    private static final String JSON_WIDGET = "widget";

    private static final String JSON_GRID = "grid";

    private static final long serialVersionUID = 1L;

    private GridWidgetExtension extension;

    private GridController gridController;

    private String gridId;

    /**
     * Creates a new {@link GridWidgetElement} instance.
     * 
     * @param extension
     *            the grid extension
     */
    public GridWidgetElement(GridWidgetExtension extension) {
        super(WebElementType.GRID, extension);
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {

        String gridParameter = PropertyLoader.loadProperty(this.extension.getGrid());

        this.gridId = PropertyLoader.loadProperty(this.extension.getIdentifier());
        this.setGridController(new GridController(this.gridId, gridParameter));

        for (WidgetReferenceExtension widgetReferenceExtension : this.extension.getWidgets()) {
            String hint = PropertyLoader.loadProperty(widgetReferenceExtension.getHint());
            String widgetIdentifier = PropertyLoader.loadProperty(widgetReferenceExtension.getIdentifier());
            String referencedWidgetId = PropertyLoader.loadProperty(widgetReferenceExtension.getWidget());
            String widgetPosition = PropertyLoader.loadProperty(widgetReferenceExtension.getPosition());

            this.getGridController().addElement(widgetIdentifier, widgetPosition, hint);

            WidgetExtension widgetExtension = this.getWidgetExtension(referencedWidgetId);
            WidgetType widgetType = PropertyLoader.loadProperty(WidgetType.class, widgetExtension.getType());

            WidgetElement widget = null;
            switch (widgetType) {
            case DYNAMICTEXT: {
                widget = new DynamicTextWidget((DynamicTextWidgetExtension) widgetExtension);
                break;
            }
            case INDICATOR: {
                widget = new IndicatorWidget((IndicatorWidgetExtension) widgetExtension);
                break;
            }
            case TEXT: {
                widget = new TextWidget((TextWidgetExtension) widgetExtension);
                break;
            }
            case ACTION: {
                widget = new ActionWidget((ActionWidgetExtension) widgetExtension);
                break;
            }
            case IMAGE: {
                widget = new ImageWidget((ImageWidgetExtension) widgetExtension);
                break;
            }
            case SEARCH: {
                widget = new SearchWidget((SearchWidgetExtension) widgetExtension);
                break;
            }
            case CALENDAR: {
                widget = new CalendarWidget((CalendarWidgetExtension) widgetExtension);
                break;
            }
            case DIALOG:{
                widget = new DialogWidget((DialogWidgetExtension) widgetExtension);
                break;
            }
            default: {
                throw new ExtensionException("Error by instantiating of Widget '"
                        + widgetType + "'. The widget type is not supported");
            }
            }
            widget.init();
            this.addElement(widgetIdentifier, widget);

        }
    }

    /**
     * Returns a Widget Extension to a given wigdet id
     * 
     * @param widgetId
     *            the widget id
     * 
     * @return the widget extension
     * 
     * @throws ExtensionException
     *             when the extension cannot be resolved
     */
    private WidgetExtension getWidgetExtension(String widgetId) throws ExtensionException {
        return (WidgetExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                ExtensionPointType.ORG_NABUCCO_UI_WIDGET, widgetId);
    }

    /**
     * Setter for the gridController.
     * 
     * @param gridController
     *            The gridController to set.
     */
    private void setGridController(GridController gridController) {
        this.gridController = gridController;
    }

    /**
     * Getter for the gridController.
     * 
     * @return Returns the gridController.
     */
    private GridController getGridController() {
        return this.gridController;
    }

    /**
     * Getter for all contained widgets.
     * 
     * @return all child widgets
     */
    public List<WidgetElement> getWidgets() {
        List<WidgetElement> widgets = new ArrayList<WidgetElement>();
        for (WebElement element : super.getElements()) {
            if (element instanceof WidgetElement) {
                widgets.add((WidgetElement) element);
            }
        }
        return widgets;
    }

    /**
     * Getter for the widget with the given id.
     * 
     * @param id
     *            the id of the widget to resolve
     * 
     * @return the widget with the given id, or null if no widget with the given id is contained
     */
    public WidgetElement getWidget(String id) {
        WebElement element = super.getElement(id);
        if (element instanceof WidgetElement) {
            return (WidgetElement) element;
        }
        return null;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        retVal.add(JSON_GRID, this.getGridController().toJson());

        JsonList gridList = new JsonList();
        for (String widgetId : this.getElementIds()) {
            WebElement widget = this.getElement(widgetId);
            JsonMap widgetJson = new JsonMap();
            widgetJson.add(JSON_ID, widgetId);
            widgetJson.add(JSON_WIDGET, widget.toJson());
            gridList.add(widgetJson);
        }
        retVal.add(JSON_WIDGETS, gridList);

        return retVal;
    }
}
