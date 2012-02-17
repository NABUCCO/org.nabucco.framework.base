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
package org.nabucco.framework.base.ui.web.model.control.util.parser;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.ui.web.model.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.control.property.dropdown.selection.SelectionProvider;

/**
 * NabuccoEnumerationParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class EnumerationControlParser<N extends NType> implements ControlParser<N> {

    private PropertyControlModel<N> model;

    private SelectionProvider<N> provider;

    /**
     * 
     * Creates a new {@link EnumerationControlParser} instance.
     * 
     * @param model
     */
    public EnumerationControlParser(PropertyControlModel<N> model, SelectionProvider<N> provider) {
        this.model = model;
        this.provider = provider;
    }

    @Override
    public N parseString(String value) throws ControlParserException {
        List<N> values = this.provider.getSelection(this.model.getProperty());
        N retVal = null;

        for (N val : values) {
            String stringValue = this.provider.getStringValue(this.model.getProperty(), val);
            if (value.equalsIgnoreCase(stringValue)) {
                retVal = val;
                break;
            }
        }

        if (retVal == null) {
            throw new ControlParserException("Parce failed, given String not found in Selection");
        }

        return retVal;
    }

}
