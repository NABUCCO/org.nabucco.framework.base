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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;
import org.nabucco.framework.base.facade.datatype.utils.MessageFormatter;
import org.nabucco.framework.base.ui.web.model.control.util.PropertyStringParser;

/**
 * Label Provider for labels created from property paths.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PropertyLabelProvider<T extends Datatype> extends WebLabelProvider<T> {

    /** The property resolver. */
    private NabuccoPropertyResolver<T> resolver;

    /**
     * Creates a new {@link PropertyLabelProvider} instance.
     * 
     * @param datatype
     *            the datatype holding the properties
     */
    public PropertyLabelProvider(T datatype) {
        this.resolver = new NabuccoPropertyResolver<T>(datatype);
    }

    @Override
    public String getLabel(String propertyPath, WebRenderer renderer) {
        NabuccoProperty property = this.resolver.resolveProperty(propertyPath);

        if (property == null) {
            return DEFAULT_LABEL;
        }

        Object instance = property.getInstance();

        if (!(instance instanceof NType)) {
            return DEFAULT_LABEL;
        }

        return renderer.render((NType) instance);
    }

    @Override
    public String getLabelFromMessage(String message, WebRenderer renderer) {
        Map<String, String> propertyMap = new HashMap<String, String>();

        PropertyStringParser propertyParser = new PropertyStringParser(message);

        // Build the map with resolved properties
        Set<String> parsedProperties = propertyParser.parseProperties();

        for (String propertyName : parsedProperties) {
            NabuccoProperty resolvedProperty = this.resolver.resolveProperty(propertyName);

            if (resolvedProperty == null) {
                propertyMap.put(propertyName, DEFAULT_LABEL);
                continue;
            }

            Object propertyInstance = resolvedProperty.getInstance();

            if (propertyInstance instanceof Basetype) {
                String value = renderer.render((NType) propertyInstance);
                propertyMap.put(propertyName, value);
            } else if (propertyInstance instanceof Enumeration) {
                String value = renderer.render((NType) propertyInstance);
                propertyMap.put(propertyName, value);
            } else if (propertyInstance instanceof Code) {
                String value = renderer.render((NType) propertyInstance);
                propertyMap.put(propertyName, value);
            } else {
                propertyMap.put(propertyName, DEFAULT_LABEL);
            }
        }

        String retVal = MessageFormatter.format(message, propertyMap);

        return retVal;
    }

}
