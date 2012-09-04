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
package org.nabucco.framework.base.ui.web.model.editor.control.property.input;

import org.nabucco.framework.base.facade.datatype.Amount;
import org.nabucco.framework.base.facade.datatype.AmountCurrency;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.formatter.CurrencyControlFormatter;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.CurrencyControlParser;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoValidator;

/**
 * CurrencyControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CurrencyControlModel extends PropertyControlModel<AmountCurrency> {

    final String JSON_CURRENCY = "currency";

    /**
     * 
     * Creates a new {@link CurrencyControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            property path
     * @param formatter
     *            formatter
     * @param validator
     *            validator
     * @param dependencyController
     *            dependency controller
     * @param editable
     *            editable
     */
    public CurrencyControlModel(String id, String propertyPath, CurrencyControlFormatter formatter,
            NabuccoValidator<AmountCurrency> validator, DependencyController dependencyController,
            boolean editable) {
        super(id, propertyPath, ControlType.CURRENCY, validator, dependencyController, editable);
        this.setFormatter(formatter);
        this.setParser(new CurrencyControlParser());
    }

    /**
     * Getter for the basetype value object.
     * 
     * @return the input value
     */
    public Amount getAmount() {
        AmountCurrency ac = super.getValue();
        Amount retVal = null;
        if (ac != null) {
            retVal = ac.getAmount();
        }
        return retVal;
    }

    /**
     * Getter for the basetype value object.
     * 
     * @return the input value
     */
    public Code getCurrency() {
        AmountCurrency ac = super.getValue();
        Code retVal = null;
        if (ac != null) {
            retVal = ac.getCurrency();
        }
        return retVal;
    }

    /**
     * Getter for the value as string.
     * 
     * @return the string value
     */
    public String getStringValue() {
        AmountCurrency value = super.getValue();
        if (value != null) {
            return value.getAmount().getValueAsString() + "$";
            // TODO: Correct the string output on dynamic code
        }
        return null;
    }

    @Override
    public AmountCurrency parse(String value) throws ControlParserException {
        AmountCurrency parsedVal = this.getParser().parseString(value);

        AmountCurrency data = this.getValue();

        if (data == null) {
            data = this.instantiate();
        }

        AmountCurrency clone = data.cloneObject();
        clone.setCurrency(parsedVal.getCurrency());
        clone.setAmount(parsedVal.getAmount());

        return clone;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();
        String formattedString = this.getFormatter().format(this.getValue());

        json.add(JSON_VALUE, formattedString);

        return json;
    }

}
