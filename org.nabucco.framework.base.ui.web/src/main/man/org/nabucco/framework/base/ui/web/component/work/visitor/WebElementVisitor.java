/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License") throws VisitorException;
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
package org.nabucco.framework.base.ui.web.component.work.visitor;

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.WorkItemWorkflow;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.BulkEditorItem;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorColumn;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorDateColumn;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorDropDownColumn;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorPickerColumn;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorTextColumn;
import org.nabucco.framework.base.ui.web.component.work.dashboard.DashboardItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditArea;
import org.nabucco.framework.base.ui.web.component.work.editor.EditTab;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorGridElement;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationArea;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.component.work.editor.control.CheckBoxControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.CurrencyControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.DateControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.DropDownControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.FileControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.ImageControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PasswordControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.RadioButtonControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.SimpleLabelControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.TextAreaInputControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.TextInputControl;
import org.nabucco.framework.base.ui.web.component.work.editor.gridelement.LinkElement;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;

/**
 * WebElementVisitor
 * 
 * Visitor pattern that is able to visit the web element tree
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface WebElementVisitor<T extends WebElementVisitorContext> {

    /**
     * Visits the Web element
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(WebElement element, T context) throws VisitorException;

    /**
     * Visits the Web composite
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(WebComposite element, T context) throws VisitorException;

    /**
     * Visits the Web component
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(WebComponent element, T context) throws VisitorException;

    /**
     * Visits the BulkEditor Column
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(BulkEditorColumn element, T context) throws VisitorException;

    /**
     * Visits the BulkEditor Date Column
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(BulkEditorDateColumn element, T context) throws VisitorException;

    /**
     * Visits the Bulk Editor DropDown Column
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(BulkEditorDropDownColumn element, T context) throws VisitorException;

    /**
     * Visits the Bulk Editor Picker Column
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(BulkEditorPickerColumn element, T context) throws VisitorException;

    /**
     * Visits the Bulk Editor Text Column
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(BulkEditorTextColumn element, T context) throws VisitorException;

    /**
     * Visits the Editor Grid Element
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(EditorGridElement element, T context) throws VisitorException;

    /**
     * Visits the Editor Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(EditorControl element, T context) throws VisitorException;

    /**
     * Visits the CheckBox Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(CheckBoxControl element, T context) throws VisitorException;

    /**
     * Visits the Currency Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(CurrencyControl element, T context) throws VisitorException;

    /**
     * Visits the Date Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(DateControl element, T context) throws VisitorException;

    /**
     * Visits the DropDown Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(DropDownControl element, T context) throws VisitorException;

    /**
     * Visits the File Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(FileControl element, T context) throws VisitorException;

    /**
     * Visits the Image Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(ImageControl element, T context) throws VisitorException;

    /**
     * Visits the Password Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(PasswordControl element, T context) throws VisitorException;

    /**
     * Visits the Picker Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(PickerControl element, T context) throws VisitorException;

    /**
     * Visits the Radio Button Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(RadioButtonControl element, T context) throws VisitorException;

    /**
     * Visits the Text Area Input Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(TextAreaInputControl element, T context) throws VisitorException;

    /**
     * Visits the Text Input Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(TextInputControl element, T context) throws VisitorException;

    /**
     * Visits the Link Element
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(LinkElement element, T context) throws VisitorException;

    /**
     * Visits the Simple Label Control
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(SimpleLabelControl element, T context) throws VisitorException;

    /**
     * Visits the Edit Area
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(EditArea element, T context) throws VisitorException;

    /**
     * Visits the Edit Tab
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(EditTab element, T context) throws VisitorException;

    /**
     * Visits the Relation Area
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(RelationArea element, T context) throws VisitorException;

    /**
     * Visits the Relation Tab
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(RelationTab element, T context) throws VisitorException;

    /**
     * Visits the WorkArea
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(WorkArea element, T context) throws VisitorException;

    /**
     * Visits the Bulk Editor Item
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(BulkEditorItem element, T context) throws VisitorException;

    /**
     * Visits the Editor Item
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(EditorItem element, T context) throws VisitorException;

    /**
     * Visits the Dashboard Item
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(DashboardItem element, T context) throws VisitorException;

    /**
     * Visits the List item
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(ListItem element, T context) throws VisitorException;

    /**
     * Visits the WorkItem Workflow element
     * 
     * @param element
     *            the element to be visited
     * @throws VisitorException
     *             if problem by visiting
     */
    void visit(WorkItemWorkflow element, T context) throws VisitorException;
}
