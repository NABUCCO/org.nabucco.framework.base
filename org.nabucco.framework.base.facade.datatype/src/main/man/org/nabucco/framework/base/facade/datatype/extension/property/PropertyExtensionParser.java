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
package org.nabucco.framework.base.facade.datatype.extension.property;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoMap;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionElement;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Parser for PropertyExtensions.
 * 
 * Supported types are
 * <ul>
 * <li>STRING</li> - StringProperty
 * <li>INTEGER</li> - IntegerProperty
 * </ul>
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class PropertyExtensionParser extends NabuccoExtensionParserSupport {

    private static final String ATTR_VALUE = "value";

    private static final String ATTR_ID = "id";

    private static final String ATTR_TYPE = "type";

    private static final String ELEMENT_PROPERTY = "property";

    private static final String ELEMENT_PROPERTIES = "properties";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(PropertyExtensionParser.class);

    public PropertyExtension parseProperties(Element e) throws ExtensionParserException {

        logger.debug("Parsing properties");

        try {
            PropertyExtension ext = new PropertyExtension();
            NabuccoMap<NabuccoExtensionElement> propertyMap = ext.getPropertyMap();

            Element propertiesElement = getElementsByTagName(e, ELEMENT_PROPERTIES).get(0);
            List<Element> propertyElementList = getElementsByTagName(propertiesElement, ELEMENT_PROPERTY);

            for (Element propertyElement : propertyElementList) {

                String type = propertyElement.getAttribute(ATTR_TYPE);
                String id = propertyElement.getAttribute(ATTR_ID);

                if (type.equals("STRING")) {
                    StringProperty sp = getStringProperty(propertyElement);
                    propertyMap.put(id, sp);
                } else if (type.equals("INTEGER")) {
                    IntegerProperty ip = getIntegerProperty(propertyElement);
                    propertyMap.put(id, ip);
                } else {
                    logger.warning("type=[" + type + "] is unsupported for PropertyExtension, skipping this property");
                }

            }

            return ext;

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }

    }

    private IntegerProperty getIntegerProperty(Element e) throws ExtensionException {
        try {
            IntegerProperty p = new IntegerProperty();
            Integer i = Integer.parseInt(e.getAttribute(ATTR_VALUE));
            p.setValue(i);

            return p;
        } catch (Exception ex) {
            throw new ExtensionException(ex);
        }
    }

    private StringProperty getStringProperty(Element e) throws ExtensionException {
        try {
            StringProperty p = new StringProperty();
            p.setValue(e.getAttribute(ATTR_VALUE));

            return p;
        } catch (Exception ex) {
            throw new ExtensionException(ex);
        }
    }

}
