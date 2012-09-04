/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse public <T extends NabuccoDatatype> License (EPL), Version 1.0 (the "License");
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

import java.util.List;

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
 * DefaultWebElementVisitor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DefaultWebElementVisitor<T extends WebElementVisitorContext> implements WebElementVisitor<T> {

    /**
     * 
     * Creates a new {@link DefaultWebElementVisitor} instance.
     * 
     */
    public DefaultWebElementVisitor() {

    }

    @Override
    public void visit(WebElement element, T context) throws VisitorException {
        if (element == null) {
            throw new VisitorException("Cannot visit web element 'null'");
        }

        List<WebElement> elements = element.getElements();
        if (elements == null) {
            // Do nothing
            return;
        }

        for (WebElement webElement : elements) {
            webElement.accept(this, context);
        }
    }

    @Override
    public void visit(WebComposite element, T context) throws VisitorException {
        this.visit((WebElement) element, context);
    }

    @Override
    public void visit(WebComponent element, T context) throws VisitorException {
        this.visit((WebElement) element, context);
    }

    @Override
    public void visit(BulkEditorColumn element, T context) throws VisitorException {
        this.visit((WebElement) element, context);
    }

    @Override
    public void visit(BulkEditorDateColumn element, T context) throws VisitorException {
        this.visit((WebElement) element, context);
    }

    @Override
    public void visit(BulkEditorDropDownColumn element, T context) throws VisitorException {
        this.visit((WebElement) element, context);
    }

    @Override
    public void visit(BulkEditorPickerColumn element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(BulkEditorTextColumn element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(EditorGridElement element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(EditorControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(CheckBoxControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(CurrencyControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(DateControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(DropDownControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(FileControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(ImageControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(PasswordControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(PickerControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(RadioButtonControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(TextAreaInputControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(TextInputControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(LinkElement element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(SimpleLabelControl element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(EditArea element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(EditTab element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(RelationArea element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(RelationTab element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(WorkArea element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(BulkEditorItem element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(EditorItem element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(DashboardItem element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(ListItem element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }

    @Override
    public void visit(WorkItemWorkflow element, T context) throws VisitorException {
        this.visit((WebElement) element, context);

    }
}
