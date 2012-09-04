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
package org.nabucco.framework.base.ui.web.component.work.dashboard.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.DashboardFilterViewExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.DashboardWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.DashboardWidgetSkaleItemExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.GraphDashboardWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.TableDashboardWidgetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.color.ColorScheme;
import org.nabucco.framework.base.ui.web.component.common.color.ColorSchemeLocator;
import org.nabucco.framework.base.ui.web.component.work.list.FilterItem;
import org.nabucco.framework.base.ui.web.component.work.util.QueryFilterExtensionUtil;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.BargraphWidgetModel;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.DashboardWidgetModel;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.PieChartWidgetModel;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.TableWidgetModel;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.common.WidgetAnalyser;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnLayoutType;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnRenderer;

/**
 * DashboardWidget
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DashboardWidget extends WebComponent {

    private static final String JSON_FILTERS = "filters";

    private static final String JSON_ACTIVE_FILTER = "activeFilter";

    private static final long serialVersionUID = 1L;

    private DashboardWidgetModel model;

    private DashboardWidgetExtension ext;

    private Map<String, FilterItem> filterMap = new LinkedHashMap<String, FilterItem>();

    private String activeFilterId;

    public NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DashboardWidget.class);

    /**
     * 
     * Creates a new {@link DashboardWidget} instance.
     * 
     * @param extension
     */
    public DashboardWidget(DashboardWidgetExtension extension) {
        super(WebElementType.WIDGET, extension);

        this.ext = extension;
    }

    @Override
    public void init() throws ExtensionException {
        try {
            DashboardWidgetType widgetType = PropertyLoader.loadProperty(DashboardWidgetType.class,
                    this.ext.getWidgetType());

            if (widgetType == null) {
                throw new ExtensionException("The dashboard widget type is 'null'");
            }

            switch (widgetType) {
            case BARGRAPH: {
                GraphDashboardWidgetExtension extension = (GraphDashboardWidgetExtension) this.ext;
                // Analyser class
                Class<WidgetAnalyser> analyserClass = PropertyLoader.loadProperty(extension.getAnalyserClass());
                WidgetAnalyser analyser = analyserClass.newInstance();

                // Color sheme
                String colorShemeId = PropertyLoader.loadProperty(extension.getColorShemeId());
                ColorScheme colorSheme = ColorSchemeLocator.getColorSheme(colorShemeId);

                // Min Percent
                Integer minPercentValue = PropertyLoader.loadProperty(extension.getMinPercentValue());

                BargraphWidgetModel model = new BargraphWidgetModel(analyser, colorSheme, minPercentValue);
                this.model = model;

                // Add skale if any
                for (DashboardWidgetSkaleItemExtension skaleItem : extension.getSkaleItems()) {
                    String label = PropertyLoader.loadProperty(skaleItem.getLabel());
                    String value = PropertyLoader.loadProperty(skaleItem.getValue());
                    model.addSkaleItem(value, label);
                }

                break;
            }
            case PIECHART: {

                GraphDashboardWidgetExtension extension = (GraphDashboardWidgetExtension) this.ext;
                // Analyser class
                Class<WidgetAnalyser> analyserClass = PropertyLoader.loadProperty(extension.getAnalyserClass());
                WidgetAnalyser analyser = analyserClass.newInstance();

                // Color sheme
                String colorShemeId = PropertyLoader.loadProperty(extension.getColorShemeId());
                ColorScheme colorSheme = ColorSchemeLocator.getColorSheme(colorShemeId);

                // Min Percent
                Integer minPercentValue = PropertyLoader.loadProperty(extension.getMinPercentValue());

                PieChartWidgetModel model = new PieChartWidgetModel(analyser, colorSheme, minPercentValue);
                this.model = model;
                break;
            }
            case TABLE: {
                TableDashboardWidgetExtension extension = (TableDashboardWidgetExtension) this.ext;

                TableModel<NabuccoDatatype> tableModel = new TableModel<NabuccoDatatype>(
                        Collections.<NabuccoDatatype> emptyList(), 50);

                for (ColumnExtension columnExtension : extension.getColumns()) {
                    String id = PropertyLoader.loadProperty(columnExtension.getIdentifier());
                    String property = PropertyLoader.loadProperty(columnExtension.getProperty());

                    TableColumn column = new TableColumn(id, property);
                    column.setLabel(PropertyLoader.loadProperty(columnExtension.getLabel()));
                    column.setTooltip(PropertyLoader.loadProperty(columnExtension.getTooltip()));
                    column.setVisible(PropertyLoader.loadProperty(columnExtension.getVisible()));
                    column.setSortable(PropertyLoader.loadProperty(columnExtension.getSortable()));
                    column.setWidth(PropertyLoader.loadProperty(columnExtension.getWidth()));

                    this.initRenderer(columnExtension, column);
                    tableModel.addColumn(column);
                }

                TableWidgetModel model = new TableWidgetModel(tableModel);
                this.model = model;
                break;
            }
            default: {
                throw new ExtensionException("The given dashboard widget type is not supported yet");
            }
            }

            if (this.model == null) {
                throw new ExtensionException("Cannot instanciate Dashboard widget.");
            }

            this.model.init();

        } catch (InstantiationException e) {
            throw new ExtensionException("Cannot instanciate analyser class", e);
        } catch (IllegalAccessException e) {
            throw new ExtensionException("Cannot access analyser class", e);
        }
    }

    /**
     * Setter selected filter id. Validates input.
     * 
     * @param filterId
     *            the id of the selected filter
     */
    public void setActiveFilterId(String filterId) {
        if (filterId == null) {
            throw new IllegalArgumentException("Cannot set active filter. The filterid is 'null'");
        }

        // Validate if the filter exists
        for (FilterItem item : this.getFilters()) {
            if (item.getId().equals(filterId)) {
                this.activeFilterId = filterId;
                break;
            }
        }
    }

    /**
     * Getter for the active filter or default filter if no custom filter selected
     * 
     * @return filter id or null if no filter exist
     */
    public String getCurrentFilterId() {
        if (this.activeFilterId != null) {
            return this.activeFilterId;
        }

        // Search for default filter
        List<FilterItem> filters = this.getFilters();

        for (FilterItem item : filters) {
            if (item.isDefault()) {
                this.setActiveFilterId(item.getRefId());
                break;
            }
        }

        // If no default filter set, take simply the first one if any
        if (this.activeFilterId == null && !filters.isEmpty()) {
            this.setActiveFilterId(filters.get(0).getId());
        }

        return this.activeFilterId;
    }

    /**
     * Getter for the active filter or default filter if no custom filter selected
     * 
     * @return filter id or null if no filter exist
     */
    public String getQueryFilterId(String id) {
        FilterItem filterItem = this.filterMap.get(id);

        if (filterItem != null) {
            return filterItem.getRefId();
        }

        return null;
    }

    /**
     * Returns the optional view name -> the type of evaluating of data
     * 
     * @return
     */
    public String getActiveFilterViewName() {
        String fitlerId = this.getCurrentFilterId();
        FilterItem filterItem = this.filterMap.get(fitlerId);
        if (filterItem != null) {
            String viewName = filterItem.getViewName();
            return viewName;
        }
        return null;
    }

    /**
     * Returns the list of filters configured for the table
     * 
     * @return list of filter items
     */
    private List<FilterItem> getFilters() {

        if (this.filterMap.isEmpty()) {

            NabuccoList<DashboardFilterViewExtension> filterExtList = this.ext.getFilters();

            for (DashboardFilterViewExtension filterExt : filterExtList) {
                String refId = PropertyLoader.loadProperty(filterExt.getRefId());
                try {
                    QueryFilterExtension filterExtension = QueryFilterExtensionUtil.getFilterExtension(refId);
                    FilterItem filterItem = null;

                    String id = PropertyLoader.loadProperty(filterExt.getId());

                    if (filterExtension == null) {
                        filterItem = new FilterItem(id, filterExt);
                    } else {
                        filterItem = new FilterItem(id, filterExt, filterExtension);
                    }

                    // Add view name if any
                    if (filterExt instanceof DashboardFilterViewExtension) {
                        DashboardFilterViewExtension view = filterExt;
                        String viewName = PropertyLoader.loadProperty(view.getViewName());
                        filterItem.setViewName(viewName);
                    }

                    this.filterMap.put(id, filterItem);

                } catch (ExtensionException e) {
                    this.logger.debug("Cannot initialize the filter with id " + refId, e);
                }
            }
        }

        return new ArrayList<FilterItem>(this.filterMap.values());
    }

    /**
     * Initialize renderer
     * 
     * @param columnExtension
     *            the extension of the column
     * @param column
     *            the column instance
     */
    private void initRenderer(ColumnExtension columnExtension, TableColumn column) {
        Class<ColumnRenderer> rendererClass = null;
        ColumnRenderer renderer = null;

        if (columnExtension.getRenderer() != null && !columnExtension.getRenderer().getValue().getValue().isEmpty()) {
            rendererClass = PropertyLoader.loadProperty(columnExtension.getRenderer());
        }

        if (rendererClass != null) {
            try {
                renderer = rendererClass.newInstance();
                column.setRenderer(renderer);
            } catch (InstantiationException e) {
                this.logger.error("Cannot instanciate renderer ", rendererClass.getName(), e);
            } catch (IllegalAccessException e) {
                this.logger.error("Cannot access renderer ", rendererClass.getName(), e);
            }
        } else {
            if (columnExtension.getLayout() != null && !columnExtension.getLayout().getValue().getValue().isEmpty()) {
                ColumnLayoutType loadProperty = PropertyLoader.loadProperty(ColumnLayoutType.class,
                        columnExtension.getLayout());
                renderer = ColumnRenderer.getDefaultRenderer(loadProperty);
            }
        }

        if (renderer == null) {
            renderer = ColumnRenderer.getDefaultRenderer(null);
        }

        column.setRenderer(renderer);
    }

    /**
     * Getter for the label
     * 
     * @return label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.ext.getLabel());
    }

    /**
     * Getter for the icon
     * 
     * @return icon
     */
    public String getIcon() {
        return PropertyLoader.loadProperty(this.ext.getIcon());
    }

    /**
     * Getter for the tooltip
     * 
     * @return the tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.ext.getTooltip());
    }

    /**
     * Getter for the id of the widget
     * 
     * @return the id
     */
    @Override
    public String getId() {
        return this.ext.getIdentifier().getValue();
    }

    /**
     * Returns the model of the Dashboard Widget
     * 
     * @return the model instance
     */
    public DashboardWidgetModel getModel() {
        return this.model;
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
    public JsonElement toJson() {
        this.model.init();

        JsonMap retVal = new JsonMap();
        retVal.add(JSON_ID, this.getId());
        retVal.add(JSON_LABEL, this.getLabel());
        retVal.add(JSON_TOOLTIP, this.getTooltip());

        JsonList filters = new JsonList();
        for (FilterItem filterItem : this.getFilters()) {
            filters.add(filterItem.toJson());
        }
        retVal.add(JSON_FILTERS, filters);
        retVal.add(JSON_ACTIVE_FILTER, this.getCurrentFilterId());
        retVal.add(JSON_TYPE, this.getModel().getType());

        return retVal;
    }
}
