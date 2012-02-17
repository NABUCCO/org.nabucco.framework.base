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
package org.nabucco.framework.base.ui.web.model.control.property.dropdown.selection;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;
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
    public List<Code> getSelection(NabuccoProperty property) {
        CodeFacade facade = CodeFacade.getInstance();
        String codePath = property.getCodePath();
        List<Code> codes = facade.getCodes(new CodePath(codePath));
        return codes;
    }

    @Override
    public List<String> getSelectionStrings(NabuccoProperty property) {
        List<String> retVal = new ArrayList<String>();

        for (Code code : this.getSelection(property)) {
            retVal.add(code.getName().getValue());
        }

        return retVal;
    }

    @Override
    public String getStringValue(NabuccoProperty property, NType item) {

        return this.getStringValue((Code) item);
    }

    /**
     * Getter fot the string value
     * 
     * @param item
     *            the item to search fot
     * @return string value
     */
    public String getStringValue(Code item) {
        if (item == null) {
            return "";
        }
        return item.getName().getValue();
    }

}
