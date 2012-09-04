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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.datatype.AmountCurrency;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * NabuccoCurrencyParser
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CurrencyControlParser implements ControlParser<AmountCurrency> {

    @Override
    public AmountCurrency parseString(String value) throws ControlParserException {
        AmountCurrency retVal;

        BigDecimal parsedValue = this.parseCurrencyValue(value);
        Currency parsedCurrency = this.parseCurrency(value);

        retVal = new AmountCurrency();
        retVal.setAmount(parsedValue);
        Code currency = new Code();
        currency.setName(parsedCurrency.getCurrencyCode());
        retVal.setCurrency(currency);

        return retVal;
    }

    /**
     * Parce currency valie
     * 
     * @param inputValue
     *            String value
     * @return Big Decimal parced value
     * @throws NabuccoValidatorException
     */
    private BigDecimal parseCurrencyValue(String inputValue) throws ControlParserException {
        BigDecimal retVal = null;
        inputValue = inputValue.replaceAll("\\s", "");

        String regexString = "^([+-]?(\\d+([\\.\\s]\\d+)*)+(,\\d+)?([a-zA-Z]*)?)$";

        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(inputValue);

        boolean matchFound = matcher.find();
        if (matchFound) {
            String value = matcher.group();
            value = value.replaceAll("\\.", "");
            value = value.replaceAll("[a-zA-Z]", "");
            value = value.replaceAll(",", "\\.");

            try {
                retVal = new BigDecimal(value);
            } catch (NumberFormatException ex) {
                throw new ControlParserException("Given Value can not be parsed!", ex);
            }

        }

        if (retVal == null) {
            throw new ControlParserException("Given Value can not be parsed!");
        }

        return retVal;
    }

    /**
     * Parce Currency of the String value if any
     * 
     * @param inputValue
     *            input String
     * @return Currency object
     * @throws NabuccoValidatorException
     *             if parsing not succeded
     */
    private Currency parseCurrency(String inputValue) throws ControlParserException {
        Currency retVal = null;

        String regexString = "[a-zA-Z]+$";
        String regexSymbol = "\\p{Sc}$";

        Pattern patternString = Pattern.compile(regexString);
        Matcher matcherString = patternString.matcher(inputValue);
        boolean matchFound = matcherString.find();
        if (matchFound) {
            String value = matcherString.group();

            try {
                retVal = Currency.getInstance(value.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ControlParserException("Given Currency can not be parsed!", ex);
            }

        } else {
            Pattern patternSymbol = Pattern.compile(regexSymbol);
            Matcher matcherSymbol = patternSymbol.matcher(inputValue);
            boolean matchSymbolFound = matcherSymbol.find();
            if (matchSymbolFound) {
                String value = matcherSymbol.group();

                if (value.equals("$")) {
                    retVal = Currency.getInstance("USD");
                } else if (value.equals("�")) {
                    retVal = Currency.getInstance("EUR");
                } else if (value.equals("�")) {
                    retVal = Currency.getInstance("GBP");
                } else {
                    throw new ControlParserException("Given Currency can not be parsed!");
                }
            }
        }

        if (retVal == null) {
            retVal = Currency.getInstance("EUR");
        }

        return retVal;
    }

    @Override
    public AmountCurrency parseString(NabuccoProperty property, String value) throws ControlParserException {
        return this.parseString(value);
    }
}
