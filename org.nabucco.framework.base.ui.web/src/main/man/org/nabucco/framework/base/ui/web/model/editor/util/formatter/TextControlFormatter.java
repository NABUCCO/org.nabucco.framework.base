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

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.TextControlExtension;
import org.nabucco.framework.base.ui.web.model.common.renderer.DefaultWebRenderer;

/**
 * TextControlFormatter
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TextControlFormatter implements ControlFormatter<Basetype> {

    private static final String PERCENT_FORMAT_STRING = " %";

    private static final String EMPTY_STRING = "";

    private TextControlExtension extension;

    private DefaultWebRenderer renderer = new DefaultWebRenderer();

    private TextInputFormatType type;

    /**
     * Default private constructor Creates a new {@link CurrencyControlFormatter} instance.
     * 
     */
    public TextControlFormatter(TextControlExtension extension) {

        this.extension = extension;
    }

    /**
     * Default private constructor Creates a new {@link CurrencyControlFormatter} instance.
     * 
     */
    public TextControlFormatter(TextInputFormatType type) {

        this.type = type;
    }

    /**
     * Returns the format type of text field
     * 
     * @return format type
     */
    private TextInputFormatType getFormatType() {
        if (this.type != null) {
            return type;
        }

        EnumerationProperty formatType = this.extension.getFormatType();
        TextInputFormatType retVal = null;
        if (formatType.getValue().getValue().length() > 0) {
            retVal = PropertyLoader.loadProperty(TextInputFormatType.class, this.extension.getFormatType());
        }
        if (retVal == null) {
            retVal = TextInputFormatType.NONE;
        }

        return retVal;
    }

    /**
     * Format value to a correct String
     */
    @Override
    public String format(Basetype value) {
        String retVal = EMPTY_STRING;

        if (value == null) {
            return retVal;
        }

        retVal = value.getValueAsString();

        switch (this.getFormatType()) {
        case NONE: {
            retVal = this.renderer.render(value);
            break;
        }
        case PERCENT: {
            retVal += PERCENT_FORMAT_STRING;
            break;
        }
        }

        return retVal;
    }

    /**
     * Clears format symbols. Can be used before parsing
     * 
     * @param value
     *            the value to be cleared from format symbols (%, $ etc)
     * @return cleared value
     */
    @Override
    public String removeFormat(String value) {
        String retVal = EMPTY_STRING;

        if (value == null) {
            return retVal;
        }

        switch (this.getFormatType()) {
        case NONE: {
            retVal = value;
            break;
        }
        case PERCENT: {
            if (value.contains(PERCENT_FORMAT_STRING)) {
                retVal = value.replaceFirst(PERCENT_FORMAT_STRING, EMPTY_STRING);
            } else {
                retVal = value;
            }
            break;
        }
        }

        return retVal;
    }

}
