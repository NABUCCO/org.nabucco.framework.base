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
package org.nabucco.framework.base.facade.datatype.extension.ui;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.DashboardFilterViewExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.DashboardWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.DashboardWidgetSkaleItemExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.GraphDashboardWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.TableDashboardWidgetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * DashboardWidgetExtensionParser
 * 
 * The parser for dashboard widgets
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DashboardWidgetExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_VIEW_NAME = "viewName";

    private static final String ELEMENT_SKALE_ITEM = "skaleItem";

    private static final String ELEMENT_SKALE = "skale";

    private static final String ATTR_ID = "id";

    private static final String ELEMENT_WIDGET = "widget";

    private static final String ELEMENT_FILTER = "filter";

    private static final String ATTR_ANALYSER = "analyserClass";

    private static final String ATTR_ICON = "icon";

    private static final String ATTR_LABEL = "label";

    private static final String ATTR_TOOLTIP = "tooltip";

    private static final String ATTR_WIDGET_TYPE = "type";

    private static final String ATTR_REFID = "refId";

    private static final String ATTR_DEFAULT = "default";

    private static final String TABLE_WIDGET = "TABLE";

    private static final String PIECHART_WIDGET = "PIECHART";

    private static final String BARGRAPH_WIDGET = "BARGRAPH";

    private static final String ATTR_COLORSHEME = "colorSheme";

    private static final String ATTR_MIN_VALUE = "minPercentValue";

    private static final String ATTR_VALUE = "value";

    private static final String ATTR_PROPERTY = "property";

    private static final String ELEMENT_COLUMN = "column";

    private static final String ATTR_VISIBLE = "visible";

    private static final String ATTR_SORTABLE = "sortable";

    private static final String ATTR_RENDERER = "renderer";

    private static final String ATTR_LAYOUT = "layout";

    private static final String ATTR_WIDTH = "width";

    private static final String ATTR_LOAD_ACTION = "loadAction";

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DashboardWidgetExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        this.logger.debug("Parsing dashboard wirdget configuration.");

        try {

            Element shemeElement = super.getElementsByTagName(element, ELEMENT_WIDGET).get(0);
            EnumerationProperty widgetType = super.getEnumerationProperty(shemeElement, ATTR_WIDGET_TYPE);
            String shemeId = element.getAttribute(ATTR_ID);

            DashboardWidgetExtension extension = null;
            if (widgetType.getValue().getValueAsString().equalsIgnoreCase(TABLE_WIDGET)) {

                TableDashboardWidgetExtension ext = new TableDashboardWidgetExtension();

                List<ColumnExtension> columns = this.parseColumnExtensions(shemeId, shemeElement);
                ext.getColumns().addAll(columns);

                extension = ext;

            } else if (widgetType.getValue().getValueAsString().equalsIgnoreCase(PIECHART_WIDGET)) {
                GraphDashboardWidgetExtension ext = new GraphDashboardWidgetExtension();

                ext.setColorShemeId(super.getStringProperty(shemeElement, ATTR_COLORSHEME));
                ext.setMinPercentValue(super.getIntegerProperty(shemeElement, ATTR_MIN_VALUE, 0));
                ext.setAnalyserClass(super.getClassProperty(shemeElement, ATTR_ANALYSER));

                extension = ext;

            } else if (widgetType.getValue().getValueAsString().equalsIgnoreCase(BARGRAPH_WIDGET)) {
                GraphDashboardWidgetExtension ext = new GraphDashboardWidgetExtension();

                ext.setColorShemeId(super.getStringProperty(shemeElement, ATTR_COLORSHEME));
                ext.setMinPercentValue(super.getIntegerProperty(shemeElement, ATTR_MIN_VALUE, 0));
                ext.setAnalyserClass(super.getClassProperty(shemeElement, ATTR_ANALYSER));

                // Parse skale
                Element skaleElement = super.getElementsByTagName(shemeElement, ELEMENT_SKALE).get(0);
                List<Element> skaleItems = super.getElementsByTagName(skaleElement, ELEMENT_SKALE_ITEM);
                for (Element skaleItem : skaleItems) {
                    DashboardWidgetSkaleItemExtension skaleItemExt = new DashboardWidgetSkaleItemExtension();

                    skaleItemExt.setLabel(super.getStringProperty(skaleItem, ATTR_LABEL));
                    skaleItemExt.setValue(super.getStringProperty(skaleItem, ATTR_VALUE));

                    ext.getSkaleItems().add(skaleItemExt);
                }

                extension = ext;

            } else {
                throw new ExtensionParserException("The type of the dashboard widget is not supported yet.");
            }

            extension.setIdentifier(shemeId);
            extension.setWidgetType(widgetType);

            extension.setIcon(super.getStringProperty(shemeElement, ATTR_ICON));
            extension.setLabel(super.getStringProperty(shemeElement, ATTR_LABEL));
            extension.setTooltip(super.getStringProperty(shemeElement, ATTR_TOOLTIP));

            List<Element> filterElements = super.getElementsByTagName(shemeElement, ELEMENT_FILTER);
            for (Element filterElement : filterElements) {
                DashboardFilterViewExtension filterExtension = this.parseFilterReferenceExtension(filterElement);
                extension.getFilters().add(filterExtension);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Cannot parse color sheme", e);
        }
    }

    /**
     * Parse the list of referenced filters
     * 
     * @param filterElement
     *            the group of filters to be parsed
     * @return QueryFilterReferenceExtension instance
     */
    private DashboardFilterViewExtension parseFilterReferenceExtension(Element filterElement)
            throws ExtensionParserException, ExtensionException {

        DashboardFilterViewExtension retVal = new DashboardFilterViewExtension();

        retVal.setId(super.getStringProperty(filterElement, ATTR_ID));
        retVal.setViewName(super.getStringProperty(filterElement, ATTR_VIEW_NAME));
        retVal.setRefId(super.getStringProperty(filterElement, ATTR_REFID));
        retVal.setTooltip(super.getStringProperty(filterElement, ATTR_TOOLTIP));
        retVal.setLabel(super.getStringProperty(filterElement, ATTR_LABEL));
        retVal.setIsDefault(super.getBooleanProperty(filterElement, ATTR_DEFAULT, false));
        retVal.setLoadAction(super.getStringProperty(filterElement, ATTR_LOAD_ACTION));

        return retVal;
    }

    /**
     * Parse the editor relation element.
     * 
     * @param element
     *            the relation element to parse
     * @return the parsed relation extension
     * 
     * @throws ExtensionParserException
     *             when the xml attributes cannot be parsed
     * @throws ExtensionException
     *             when the relation columns cannot be parsed
     */
    private List<ColumnExtension> parseColumnExtensions(String parentId, Element element)
            throws ExtensionParserException, ExtensionException {

        List<ColumnExtension> retVal = new ArrayList<ColumnExtension>();
        List<Element> columnElements = super.getElementsByTagName(element, ELEMENT_COLUMN);
        for (Element columnElement : columnElements) {
            ColumnExtension columnExtension = new ColumnExtension();
            columnExtension.setIdentifier(columnElement.getAttribute(ATTR_ID));
            columnExtension.setLabel(super.getStringProperty(columnElement, ATTR_LABEL));
            columnExtension.setTooltip(super.getStringProperty(columnElement, ATTR_TOOLTIP));
            columnExtension.setProperty(super.getStringProperty(columnElement, ATTR_PROPERTY));
            columnExtension.setVisible(super.getBooleanProperty(columnElement, ATTR_VISIBLE));
            columnExtension.setSortable(super.getBooleanProperty(columnElement, ATTR_SORTABLE));
            columnExtension.setRenderer(super.getClassProperty(columnElement, ATTR_RENDERER));
            columnExtension.setLayout(super.getEnumerationProperty(columnElement, ATTR_LAYOUT));
            columnExtension.setWidth(super.getStringProperty(columnElement, ATTR_WIDTH));
            retVal.add(columnExtension);
        }

        return retVal;
    }
}
