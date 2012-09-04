/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Amount;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * AmountCurrency<p/>Datatype holding a monetary value and its currency.<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2011-01-10
 */
public class AmountCurrency extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "m1,1;" };

    public static final String AMOUNT = "amount";

    public static final String CURRENCY = "currency";

    /** The amount of money. */
    private Amount amount;

    /** The currency of money. */
    private Code currency;

    protected static final String CURRENCY_CODEPATH = "nabucco.framework.currency";

    /** Constructs a new AmountCurrency instance. */
    public AmountCurrency() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AmountCurrency.
     */
    protected void cloneObject(AmountCurrency clone) {
        super.cloneObject(clone);
        if ((this.getAmount() != null)) {
            clone.setAmount(this.getAmount().cloneObject());
        }
        if ((this.getCurrency() != null)) {
            clone.setCurrency(this.getCurrency().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(AMOUNT,
                PropertyDescriptorSupport.createBasetype(AMOUNT, Amount.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(CURRENCY, PropertyDescriptorSupport.createDatatype(CURRENCY, Code.class, 4,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION, CURRENCY_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(AmountCurrency.getPropertyDescriptor(AMOUNT), this.amount, null));
        properties.add(super.createProperty(AmountCurrency.getPropertyDescriptor(CURRENCY), this.getCurrency(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(AMOUNT) && (property.getType() == Amount.class))) {
            this.setAmount(((Amount) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CURRENCY) && (property.getType() == Code.class))) {
            this.setCurrency(((Code) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final AmountCurrency other = ((AmountCurrency) obj);
        if ((this.amount == null)) {
            if ((other.amount != null))
                return false;
        } else if ((!this.amount.equals(other.amount)))
            return false;
        if ((this.currency == null)) {
            if ((other.currency != null))
                return false;
        } else if ((!this.currency.equals(other.currency)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.amount == null) ? 0 : this.amount.hashCode()));
        result = ((PRIME * result) + ((this.currency == null) ? 0 : this.currency.hashCode()));
        return result;
    }

    @Override
    public AmountCurrency cloneObject() {
        AmountCurrency clone = new AmountCurrency();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The amount of money.
     *
     * @return the Amount.
     */
    public Amount getAmount() {
        return this.amount;
    }

    /**
     * The amount of money.
     *
     * @param amount the Amount.
     */
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    /**
     * The amount of money.
     *
     * @param amount the java.math.BigDecimal.
     */
    public void setAmount(java.math.BigDecimal amount) {
        if ((this.amount == null)) {
            if ((amount == null)) {
                return;
            }
            this.amount = new Amount();
        }
        this.amount.setValue(amount);
    }

    /**
     * The currency of money.
     *
     * @param currency the Code.
     */
    public void setCurrency(Code currency) {
        this.currency = currency;
    }

    /**
     * The currency of money.
     *
     * @return the Code.
     */
    public Code getCurrency() {
        return this.currency;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AmountCurrency.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AmountCurrency.class).getAllProperties();
    }

    /**
     * Getter for the CurrencyCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getCurrencyCodePath() {
        return new CodePath(CURRENCY_CODEPATH);
    }
}
