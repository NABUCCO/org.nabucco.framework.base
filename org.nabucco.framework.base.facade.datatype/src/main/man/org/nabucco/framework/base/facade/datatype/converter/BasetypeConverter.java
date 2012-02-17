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
package org.nabucco.framework.base.facade.datatype.converter;

import java.math.BigDecimal;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.NBoolean;
import org.nabucco.framework.base.facade.datatype.NByte;
import org.nabucco.framework.base.facade.datatype.NByteArray;
import org.nabucco.framework.base.facade.datatype.NChar;
import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.facade.datatype.NDecimal;
import org.nabucco.framework.base.facade.datatype.NDouble;
import org.nabucco.framework.base.facade.datatype.NFloat;
import org.nabucco.framework.base.facade.datatype.NInteger;
import org.nabucco.framework.base.facade.datatype.NLong;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.NText;
import org.nabucco.framework.base.facade.datatype.NTime;
import org.nabucco.framework.base.facade.datatype.NTimestamp;

/**
 * BasetypeConverter
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BasetypeConverter {

    /**
     * Private constructor must not be invoked.
     */
    private BasetypeConverter() {
    }

    /**
     * Parses a string value and set it into the given basetype. Depending on the
     * {@link BasetypeType} different parsing strategies are used.
     * 
     * @param basetype
     *            the basetype to change
     * @param value
     *            the value to parse
     * 
     * @throws ConverterException
     *             when the value cannot be parsed to the specified type
     */
    public static final void setValueAsString(Basetype basetype, String value) throws ConverterException {

        if (basetype == null) {
            throw new IllegalArgumentException("Cannot set value for basetype [null].");
        }

        // Return empty Basetype.
        if (value == null) {
            return;
        }

        try {

            switch (basetype.getType()) {

            case BOOLEAN: {
                NBoolean nBoolean = (NBoolean) basetype;
                nBoolean.setValue(Boolean.parseBoolean(value));
                break;
            }

            case BYTE: {
                NByte nByte = (NByte) basetype;
                nByte.setValue(Byte.parseByte(value));
                break;
            }

            case BYTE_ARRAY: {
                NByteArray nByteArray = (NByteArray) basetype;
                nByteArray.setValue(value.getBytes());
                break;
            }

            case CHAR: {
                NChar nChar = (NChar) basetype;
                nChar.setValue(value.charAt(0));
                break;
            }

            case DATE: {
                NDate nDate = (NDate) basetype;
                nDate.setValue(NabuccoConverter.getInstance().getDateConverter().convertDate(value));
                break;
            }

            case DECIMAL: {
                NDecimal nDecimal = (NDecimal) basetype;
                nDecimal.setValue(new BigDecimal(value));
                break;
            }

            case DOUBLE: {
                NDouble nDouble = (NDouble) basetype;
                nDouble.setValue(Double.parseDouble(value));
                break;
            }

            case FLOAT: {
                NFloat nFloat = (NFloat) basetype;
                nFloat.setValue(Float.parseFloat(value));
                break;
            }

            case INTEGER: {
                NInteger nInteger = (NInteger) basetype;
                nInteger.setValue(Integer.parseInt(value));
                break;
            }

            case LONG: {
                NLong nLong = (NLong) basetype;
                nLong.setValue(Long.parseLong(value));
                break;
            }

            case STRING: {
                NString nString = (NString) basetype;
                nString.setValue(value);
                break;
            }

            case TEXT: {
                NText nText = (NText) basetype;
                nText.setValue(value);
                break;
            }

            case TIME: {
                NTime nTime = (NTime) basetype;
                nTime.setValue(NabuccoConverter.getInstance().getDateConverter().convertTime(value));
                break;
            }

            case TIMESTAMP: {
                NTimestamp nTimestamp = (NTimestamp) basetype;
                nTimestamp.setValue(Long.parseLong(value));
                break;
            }

            }

        } catch (NumberFormatException e) {
            throw new ConverterException("Error parsing number " + value + " for " + basetype.getType() + ".", e);
        } catch (Exception e) {
            throw new ConverterException("Error parsing value " + value + " for " + basetype.getType() + ".", e);
        }

    }

}
