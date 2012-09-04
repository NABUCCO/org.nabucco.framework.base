/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.component.common.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.model.dialog.DialogAccessor;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;
import org.nabucco.framework.base.ui.web.model.dialog.GridDialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogDateControlModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogDropDownControlModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogTextControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionItem;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionProviderDelegate;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.table.filter.EnumerationFilter;
import org.nabucco.framework.base.ui.web.model.table.filter.NDateFilter;
import org.nabucco.framework.base.ui.web.model.table.filter.NIntegerFilter;
import org.nabucco.framework.base.ui.web.model.table.filter.NStringFilter;
import org.nabucco.framework.base.ui.web.model.table.filter.TableColumnFilter;
import org.nabucco.framework.base.ui.web.model.table.filter.TableFilter;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * TableModelFilterManager
 * 
 * The tablemodel filter manager manages the webs server filtering on the given table model. It
 * produces a generical dialog according to the column of the table model and interprets the results
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TableModelFilterManager {

    Map<String, TableModel<Datatype>> dialogMap = new HashMap<String, TableModel<Datatype>>();

    private static final String LABEL_BIS = "bis";

    private static final String SUFFIX_SEPARATOR = "_";

    private static final String TO_SUFFIX = "to";

    private static final String FROM_SUFFIX = "from";

    private static final String EMPTY_STRING = "";

    private static final String GRIDDELIMITER = "-";

    private static final String D_COLUMN = "D";

    private static final String C_COLUMN = "C";

    private static final String B_COLUMN = "B";

    private static final String A_COLUMN = "A";

    private static TableModelFilterManager instance;

    private static String NODATADIALOG = "NoTableDataDialog";

    private static String DATADIALOG = "CreateTableFilterDialog";

    /**
     * 
     * Creates a new {@link TableModelFilterManager} instance.
     * 
     */
    private TableModelFilterManager() {

    }

    /**
     * Reads values from dialog and creates a new Filter
     * 
     * @param dialogInstanceid
     *            the dialog instance id
     */
    public void createFilter(String dialogInstanceid) {
        if (dialogInstanceid == null) {
            throw new IllegalArgumentException("Cannot create Filter to the given dialog. Instanceid is 'null'");
        }

        if (!dialogMap.containsKey(dialogInstanceid)) {
            throw new IllegalArgumentException(
                    "Cannot create Filter. The given dialog was not generated using generateFilterDialog() method.");
        }

        TableModel<Datatype> tableModel = dialogMap.get(dialogInstanceid);
        List<TableColumn> columnList = tableModel.getColumnList();

        Dialog dialog = NabuccoServletUtil.getDialogController().getDialog(dialogInstanceid);

        TableFilter filter = new TableFilter();

        for (TableColumn column : columnList) {
            String propertyPath = column.getPropertyPath();

            TableColumnFilter configuredFilter = this.getConfiguredFilter(dialog, column.getId());
            if (configuredFilter != null) {
                filter.add(propertyPath, configuredFilter);
            }
        }

        tableModel.setFilter(filter);
    }

    /**
     * Returns the configured filter or null if nothing setted
     * 
     * @param model
     *            the model of the dialog
     * @param columnId
     *            the column id to search for
     * @return filter or null
     */
    private TableColumnFilter getConfiguredFilter(Dialog dialog, String columnId) {

        DialogAccessor accessor = new DialogAccessor(dialog);

        TableColumnFilter retVal = null;

        List<String> controlIds = accessor.getControlIds();
        for (String controlId : controlIds) {
            if (controlId.equals(columnId)) {
                String value = accessor.getControlTextValue(controlId);

                if (value == null || value.isEmpty()) {
                    return null;
                }

                if (accessor.getControlType(controlId) == DialogControlType.DROPDOWN) {
                    retVal = new EnumerationFilter(value);
                } else {
                    // make * -> .* syntax
                    value = value.replaceAll("\\*", "\\.\\*");
                    retVal = new NStringFilter(value, true);
                }

            } else if (controlId.startsWith(columnId + SUFFIX_SEPARATOR)) {
                DialogControlType controlType = accessor.getControlType(columnId + SUFFIX_SEPARATOR + FROM_SUFFIX);

                if (controlType == null) {
                    return null;
                }

                if (controlType == DialogControlType.DATE) {
                    // Date from/to
                    Date fromValue = accessor.getControlDateValue(columnId + SUFFIX_SEPARATOR + FROM_SUFFIX);
                    Date toValue = accessor.getControlDateValue(columnId + SUFFIX_SEPARATOR + TO_SUFFIX);

                    if (fromValue == null && toValue == null) {
                        return null;
                    }

                    retVal = this.getDateFilter(fromValue, toValue);
                } else {
                    String fromValue = accessor.getControlTextValue(columnId + SUFFIX_SEPARATOR + FROM_SUFFIX);
                    String toValue = accessor.getControlTextValue(columnId + SUFFIX_SEPARATOR + TO_SUFFIX);

                    if (fromValue == null && toValue == null || fromValue.isEmpty() && toValue.isEmpty()) {
                        return null;
                    }
                    // Integer from/to
                    retVal = this.getIntegerFilter(fromValue, toValue);

                }
            }
        }

        return retVal;
    }

    /**
     * Creates a Date filter
     * 
     * @param fromDate
     *            the min Value
     * @param fromDate
     *            the max value
     * 
     * @return filter instance
     */
    private TableColumnFilter getDateFilter(Date fromDate, Date toDate) {
        TableColumnFilter retVal;

        if (fromDate == null) {
            fromDate = new Date(0L);
        }

        if (toDate == null) {
            toDate = new Date(Long.MAX_VALUE);
        }

        retVal = new NDateFilter(fromDate, toDate);
        return retVal;
    }

    /**
     * Creates a Date filter
     * 
     * @param fromValue
     *            the min Value
     * @param fromValue
     *            the max value
     * @return filter instance
     */
    private TableColumnFilter getIntegerFilter(String fromValue, String toValue) {
        TableColumnFilter retVal;

        int fromInt = Integer.MIN_VALUE;
        int toInt = Integer.MAX_VALUE;

        if (fromValue != null && fromValue.length() > 0) {
            fromInt = Integer.parseInt(fromValue);
        }

        if (toValue != null && toValue.length() > 0) {
            toInt = Integer.parseInt(toValue);
        }

        retVal = new NIntegerFilter(fromInt, toInt);
        return retVal;
    }

    /**
     * Generates a new Filter dialog instance according to the table model
     * 
     * @param tableModel
     *            table model to generate filter dialog for
     * @return instance id of the filter or NODATADIALOG if not possible
     */
    public String generateFilterDialog(TableModel<Datatype> tableModel) {
        if (tableModel == null) {
            throw new IllegalArgumentException(
                    "Cannot create Filter dialog to the given table model. Table Model is 'null'");
        }

        List<Datatype> content = tableModel.getContent();

        if (content.isEmpty()) {
            Dialog noDataDialog = NabuccoServletUtil.getDialogController().createDialog(NODATADIALOG);
            return noDataDialog.getInstanceId();
        }
        Dialog dialog = NabuccoServletUtil.getDialogController().createDialog(DATADIALOG);
        GridDialogModel model = (GridDialogModel) dialog.getModel();

        Datatype templateDatatype = content.get(0).cloneObject();
        NabuccoPropertyResolver<Datatype> resolver = new NabuccoPropertyResolver<Datatype>(templateDatatype);

        int currentRow = 0;

        List<TableColumn> columnList = tableModel.getColumnList();
        for (TableColumn column : columnList) {
            String propertyPath = column.getPropertyPath();
            NabuccoProperty resolveProperty = resolver.resolveProperty(propertyPath);
            Object instance = resolveProperty.getInstance();

            if (instance instanceof Basetype) {
                Basetype basetype = (Basetype) instance;

                if (basetype.getType() == BasetypeType.TIMESTAMP || basetype.getType() == BasetypeType.DATE) {
                    DialogGridModelElement fromControl = this.createDateControl(column.getId()
                            + SUFFIX_SEPARATOR + FROM_SUFFIX, column.getLabel(), column.getTooltip(), currentRow,
                            A_COLUMN, B_COLUMN);

                    DialogGridModelElement toControl = this.createDateControl(column.getId()
                            + SUFFIX_SEPARATOR + TO_SUFFIX, LABEL_BIS, column.getTooltip(), currentRow, C_COLUMN,
                            D_COLUMN);

                    model.addDialogControlModel(fromControl);
                    model.addDialogControlModel(toControl);

                } else if (basetype.getType() == BasetypeType.INTEGER) {
                    DialogGridModelElement fromControl = this.createTextControl(column.getId()
                            + SUFFIX_SEPARATOR + FROM_SUFFIX, column.getLabel(), column.getTooltip(), currentRow,
                            A_COLUMN, B_COLUMN);

                    DialogGridModelElement toControl = this.createTextControl(column.getId()
                            + SUFFIX_SEPARATOR + TO_SUFFIX, LABEL_BIS, column.getTooltip(), currentRow, C_COLUMN,
                            D_COLUMN);

                    model.addDialogControlModel(fromControl);
                    model.addDialogControlModel(toControl);
                } else {
                    DialogGridModelElement control = this.createTextControl(column.getId(), column.getLabel(),
                            column.getTooltip(), currentRow, A_COLUMN, D_COLUMN);

                    model.addDialogControlModel(control);
                }

            } else if (instance instanceof Enumeration) {
                SelectionProviderDelegate<Enumeration> delegate = new SelectionProviderDelegate<Enumeration>();

                List<SelectionItem<?>> selectionItems = new ArrayList<SelectionItem<?>>();
                selectionItems.addAll(delegate.getSelectionItems(resolveProperty));

                DialogGridModelElement control = this.createDrowDownControl(column.getId(), column.getLabel(),
                        column.getTooltip(), currentRow, A_COLUMN, D_COLUMN, selectionItems);

                model.addDialogControlModel(control);

            } else if (instance instanceof Code) {
                SelectionProviderDelegate<Code> delegate = new SelectionProviderDelegate<Code>();

                List<SelectionItem<?>> selectionItems = new ArrayList<SelectionItem<?>>();
                selectionItems.addAll(delegate.getSelectionItems(resolveProperty));

                DialogGridModelElement control = this.createDrowDownControl(column.getId(), column.getLabel(),
                        column.getTooltip(), currentRow, A_COLUMN, D_COLUMN, selectionItems);

                model.addDialogControlModel(control);

            }
            currentRow++;
        }

        dialogMap.put(dialog.getInstanceId(), tableModel);
        return dialog.getInstanceId();
    }

    /**
     * Creates a text control with given values
     * 
     * @param id
     *            the id of the control
     * @param label
     *            the label to show
     * @param tooltip
     *            the tooltip
     * @param row
     *            the row to place control
     * @param startColumn
     *            the start column of the control
     * @param endColumn
     *            last column of the control
     * @return grid instance
     */
    private DialogGridModelElement createTextControl(String id, String label, String tooltip, int row,
            String startColumn, String endColumn) {
        String position = startColumn + row + GRIDDELIMITER + endColumn + row;

        DialogTextControlModel retVal = new DialogTextControlModel(id, label, tooltip, DialogControlType.TEXT,
                position, EMPTY_STRING, false, true);

        return retVal;
    }

    /**
     * Creates a date control with standard values
     * 
     * @param id
     *            the id of the control
     * @param label
     *            the label to show
     * @param tooltip
     *            the tooltip
     * @param row
     *            the row to place control
     * @param startColumn
     *            the start column of the control
     * @param endColumn
     *            last column of the control
     * @return grid instance
     */
    private DialogGridModelElement createDateControl(String id, String label, String tooltip, int row,
            String startColumn, String endColumn) {
        String position = startColumn + row + GRIDDELIMITER + endColumn + row;

        DialogDateControlModel retVal = new DialogDateControlModel(id, label, tooltip, position, EMPTY_STRING, false,
                true);

        return retVal;
    }

    /**
     * Creates a drop down control with given values
     * 
     * @param id
     *            the id of the control
     * @param label
     *            the label to show
     * @param tooltip
     *            the tooltip
     * @param row
     *            the row to place control
     * @param startColumn
     *            the start column of the control
     * @param endColumn
     *            last column of the control
     * @return grid instance
     */
    private DialogGridModelElement createDrowDownControl(String id, String label, String tooltip, int row,
            String startColumn, String endColumn, List<SelectionItem<?>> selection) {
        String position = startColumn + row + GRIDDELIMITER + endColumn + row;

        DialogDropDownControlModel retVal = new DialogDropDownControlModel(id, label, tooltip, position, EMPTY_STRING,
                false, true, selection);

        return retVal;
    }

    /**
     * Returns the instance of the filter manager
     * 
     * @return instance
     */
    public static TableModelFilterManager getInstance() {
        if (instance == null) {
            instance = new TableModelFilterManager();
        }

        return instance;
    }

}
