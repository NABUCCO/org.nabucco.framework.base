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
package org.nabucco.framework.base.ui.web.model.editor.util.parser;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.ui.web.model.editor.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionItem;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionProvider;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionProviderDelegate;

/**
 * NabuccoEnumerationParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class EnumerationControlParser<N extends NType> implements ControlParser<N> {

    private SelectionProvider<N> provider;

    private NabuccoProperty property;

    private PropertyControlModel<N> model;

    /**
     * 
     * Creates a new {@link EnumerationControlParser} instance.
     * 
     * @param model
     *            the model of the control
     * @param provider
     *            the provider to use for the selection
     */
    public EnumerationControlParser(PropertyControlModel<N> model, SelectionProvider<N> provider) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot create parser. The model is null");
        }

        if (provider == null) {
            throw new IllegalArgumentException("Cannot create parser. The provider is null");
        }
        this.provider = provider;
        this.model = model;
    }

    /**
     * 
     * Creates a new {@link EnumerationControlParser} instance.
     * 
     * @param property
     *            the path to the property
     * @param provider
     *            the provider to use
     */
    public EnumerationControlParser(NabuccoProperty property, SelectionProvider<N> provider) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot create parser. The property is null");
        }

        if (provider == null) {
            throw new IllegalArgumentException("Cannot create parser. The provider is null");
        }
        this.property = property;
        this.provider = provider;
    }

    /**
     * Creates a new control parser without binding to the property. By using of this constructor
     * only the method parseString(property,value) is allowed. The other one will throw an exception
     * 
     * 
     * @param provider
     *            the provider to use for resolving of selection
     */
    public EnumerationControlParser() {
        this.provider = new SelectionProviderDelegate<N>();
    }

    @Override
    public N parseString(String value) throws ControlParserException {

        if (model != null) {
            property = model.getProperty();
        }

        if (property == null) {
            throw new IllegalArgumentException("Cannot parse string. The property is null");
        }
        return this.parseString(property, value);
    }

    @Override
    public N parseString(NabuccoProperty property, String value) throws ControlParserException {
        List<SelectionItem<N>> selectionItems = this.provider.getSelectionItems(property);

        N retVal = null;

        for (SelectionItem<N> val : selectionItems) {
            String stringValue = val.getLiteralName();
            if (value.equalsIgnoreCase(stringValue)) {
                retVal = val.getItem();
                break;
            }
        }

        if (retVal == null) {
            throw new ControlParserException("Parce failed, given String not found in Selection");
        }

        return retVal;
    }

}
