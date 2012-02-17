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
package org.nabucco.framework.base.ui.web.component.work.editor.control;

import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.CurrencyControlExtension;
import org.nabucco.framework.base.ui.web.model.control.property.input.CurrencyControlModel;
import org.nabucco.framework.base.ui.web.model.control.util.formatter.CurrencyControlFormatter;
import org.nabucco.framework.base.ui.web.model.control.util.validator.NabuccoCurrencyValidator;

/**
 * CurrencyControl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class CurrencyControl extends EditorControl {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link CurrencyControl} instance.
     * 
     * @param extension
     *            the currency control extension
     */
    public CurrencyControl(CurrencyControlExtension extension) {
        super(extension);
    }

    @Override
    public CurrencyControlModel instantiateModel(String id, String propertyPath) {

        CurrencyControlFormatter formatter = new CurrencyControlFormatter(this);
        NabuccoCurrencyValidator validator = new NabuccoCurrencyValidator(this);
        CurrencyControlModel model = new CurrencyControlModel(id, propertyPath, formatter, validator,
                super.getDependencySet(), this.getEditable());

        return model;
    }

    /**
     * Minimum fraction Digits
     * 
     * @return int num digits
     */
    public int getMinimumFractionDigits() {
        return PropertyLoader.loadProperty(this.getExtension().getMinimumFractionDigits());
    }

    /**
     * Maximum fraction digits
     * 
     * @return int num digits
     */
    public int getMaximumFractionDigits() {
        return PropertyLoader.loadProperty(this.getExtension().getMaximumFractionDigits());
    }

    /**
     * Minimum Integer digits
     * 
     * @return int num digits
     */
    public int getMinimumIntegerDigits() {
        return PropertyLoader.loadProperty(this.getExtension().getMinimumIntegerDigits());
    }

    /**
     * Maximum Integer digits
     * 
     * @return int num digits
     */
    public int getMaximumIntegerDigits() {
        return PropertyLoader.loadProperty(this.getExtension().getMaximumIntegerDigits());
    }

    /**
     * Returns if the number can be signed
     * 
     * @return boolean singed
     */
    public boolean isSigned() {
        return PropertyLoader.loadProperty(this.getExtension().getSigned());
    }

    /**
     * Getter for the serperator.
     * 
     * @return the separator
     */
    public String getSeparator() {
        return PropertyLoader.loadProperty(this.getExtension().getSeparator());
    }

    @Override
    protected CurrencyControlExtension getExtension() {
        return (CurrencyControlExtension) super.getExtension();
    }

    @Override
    public CurrencyControlModel getModel() {
        return (CurrencyControlModel) super.getModel();
    }
}
