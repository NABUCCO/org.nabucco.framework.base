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
package org.nabucco.framework.base.ui.web.model.common.renderer;

import java.util.Date;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.converter.NabuccoConverter;

/**
 * WebRendererSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class WebRendererSupport {

    private static final String DEFAULT_VALUE = "";

    /**
     * Retrieves the value of a given ntype.
     * 
     * @param type
     *            the ntype to load the value of
     * 
     * @return the value of this type
     */
    protected String getValue(NType type) {
        if (type instanceof Basetype) {
            Basetype basetype = (Basetype) type;

            if (basetype.getType() == BasetypeType.TIMESTAMP) {
                Date date = new Date((Long) basetype.getValue());
                return NabuccoConverter.getInstance().getDateConverter().convertDateTime(date);
            }

            String text = basetype.getValueAsString();
            return text;
        }

        if (type instanceof Enumeration) {
            return String.valueOf(type);
        }

        if (type instanceof Code) {
            Code code = (Code) type;

            if (code.getName() != null && code.getName().getValue() != null) {
                return code.getName().getValue();
            }
        }

        return DEFAULT_VALUE;
    }
}
