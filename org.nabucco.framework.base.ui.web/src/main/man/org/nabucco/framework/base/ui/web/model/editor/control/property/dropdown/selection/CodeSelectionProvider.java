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
package org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodeFacade;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * CodeSelectionProvider
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CodeSelectionProvider implements SelectionProvider<Code> {

    /**
     * Creates a new {@link CodeSelectionProvider} instance.
     * 
     */
    CodeSelectionProvider() {
    }

    @Override
    public List<SelectionItem<Code>> getSelectionItems(NabuccoProperty property) {
        List<SelectionItem<Code>> retVal = new ArrayList<SelectionItem<Code>>();

        List<Code> selection = this.getSelection(property);
        for (Code item : selection) {
            String literalName = item.getName().getValue();
            SelectionItem<Code> selectionItem = new SelectionItem<Code>(item, literalName, property.getCodePath());

            retVal.add(selectionItem);
        }
        return retVal;
    }

    /**
     * Return loaded codes
     * 
     * @param property
     *            the property to use
     * @return list of codes
     */
    private List<Code> getSelection(NabuccoProperty property) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot return selection of property 'null'");
        }
        CodeFacade facade = CodeFacade.getInstance();
        String codePath = property.getCodePath();
        List<Code> codes = facade.getCodes(new CodePath(codePath));
        return codes;
    }

    @Override
    public SelectionItem<Code> getSelectionValue(NabuccoProperty property, Code item) {
        if (item == null || property == null) {
            return new SelectionItem<Code>();
        }

        String path = property.getCodePath();
        if (item.getName() == null) {
            SelectionItem<Code> retVal = new SelectionItem<Code>();
            return retVal;
        }
        String literalName = item.getName().getValue();
        SelectionItem<Code> retVal = new SelectionItem<Code>(item, literalName, path);
        return retVal;
    }

}
