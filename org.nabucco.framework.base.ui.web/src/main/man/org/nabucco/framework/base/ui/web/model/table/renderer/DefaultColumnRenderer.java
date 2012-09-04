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
package org.nabucco.framework.base.ui.web.model.table.renderer;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * DefaultColumnRenderer
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DefaultColumnRenderer extends ColumnRenderer {

    /**
     * Creates a new {@link DefaultColumnRenderer} instance.
     * 
     * @param layout
     *            the column layout
     */
    DefaultColumnRenderer(ColumnLayoutType layout) {
        super(layout);
    }

    @Override
    public String render(NType type, NabuccoProperty property) {

        String value = super.getValue(type, property);
        ColumnLayoutType layout = super.getLayout();

        switch (layout) {

        case PERCENT: {
            BigDecimal decimalValue = new BigDecimal(value);
            DecimalFormat formatter = new DecimalFormat("###.#%");
            formatter.setMultiplier(1);
            return formatter.format(decimalValue);
        }

        case TEXT:
            // Dont send the whole text if the text is too long
            if (value.length() > 100) {
                value = value.substring(0, 100) + "..";
            }
            return value;
        }

        throw new IllegalStateException("Cannot render layout '" + layout + "'.");
    }

    @Override
    public String render(NType value) {
        return this.render(value, null);
    }

}
