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
package org.nabucco.framework.base.ui.web.model.editor.util.formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.nabucco.framework.base.facade.datatype.AmountCurrency;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.work.editor.control.CurrencyControl;

/**
 * NabuccoCurrencyFormatter
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CurrencyControlFormatter implements ControlFormatter<AmountCurrency> {

    private WebComponent component;

    /**
     * Default private constructor Creates a new {@link CurrencyControlFormatter} instance.
     * 
     */
    public CurrencyControlFormatter(WebComponent component) {
        this.setComponent(component);
    }

    /**
     * Format value to a correct String
     */
    @Override
    public String format(AmountCurrency value) {
        if ((this.getComponent() instanceof CurrencyControl) == false) {
            throw new IllegalArgumentException("Validator is not able to validate the given component type.");
        }
        CurrencyControl control = ((CurrencyControl) this.getComponent());

        String retVal = "";

        BigDecimal amountValue = value.getAmount().getValue();

        if (!control.isSigned() && amountValue.doubleValue() < 0) {
            amountValue = BigDecimal.ZERO;
        }

        // Configure formatter "###,###,###,###,###,###,###.#################### "
        DecimalFormat formatter = new DecimalFormat();

        int minimumFractionDigits = control.getMinimumFractionDigits();
        int minimumIntegerDigits = control.getMinimumIntegerDigits();
        int maximumFractionDigits = control.getMaximumFractionDigits();
        int maximumIntegerDigits = control.getMaximumIntegerDigits();

        formatter.setMaximumFractionDigits(maximumFractionDigits);
        formatter.setMinimumFractionDigits(minimumFractionDigits);
        formatter.setMinimumIntegerDigits(minimumIntegerDigits);
        formatter.setMaximumIntegerDigits(maximumIntegerDigits);

        retVal = formatter.format(amountValue);
        return retVal + " " + value.getCurrency().getName().getValue();
    }

    /**
     * Set the component element to format
     * 
     * @param component
     */
    private void setComponent(WebComponent component) {
        this.component = component;
    }

    /**
     * Return WebComponent
     * 
     * @return
     */
    private WebComponent getComponent() {
        return this.component;
    }

    @Override
    public String removeFormat(String value) {
        return value;
    }

}
