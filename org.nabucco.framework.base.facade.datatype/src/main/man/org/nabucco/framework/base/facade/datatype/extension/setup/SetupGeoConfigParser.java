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
package org.nabucco.framework.base.facade.datatype.extension.setup;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.GeoConfigExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.GeoRegionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.GeoSchemaExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Extension configuration parser for geo config.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class SetupGeoConfigParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_OPTIONAL = "optional";

    private static final String ATTR_LOCALE = "locale";

    private static final String ELEMENT_GEOSCHEMA = "geoschema";

    private static final String ELEMENT_GEOREGION = "georegion";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SetupGeoConfigParser.class);

    @Override
    public NabucoExtensionContainer parseExtension(Element e) throws ExtensionParserException {

        logger.debug("Parsing GeoConfig configuration");

        try {
            GeoConfigExtension ext = new GeoConfigExtension();
            ExtensionId id = new ExtensionId(e.getAttribute(ATTR_ID));
            ext.setIdentifier(id);

            List<Element> schmemaList = getElementsByTagName(e, ELEMENT_GEOSCHEMA);
            List<GeoSchemaExtension> resList = ext.getGeoSchemaList();

            for (Element schemaElement : schmemaList) {

                GeoSchemaExtension gs = new GeoSchemaExtension();
                gs.setLocale(getStringProperty(schemaElement, ATTR_LOCALE));

                resList.add(gs);

                List<Element> geoRegionList = getElementsByTagName(schemaElement, ELEMENT_GEOREGION);
                for (Element geoRegionElement : geoRegionList) {
                    GeoRegionExtension gr = new GeoRegionExtension();
                    gr.setId(getStringProperty(geoRegionElement, ATTR_ID));
                    gr.setType(getStringProperty(geoRegionElement, ATTR_TYPE));
                    gr.setOptional(getBooleanProperty(geoRegionElement, ATTR_OPTIONAL));
                    gs.getGeoRegionList().add(gr);

                }
            }

            return new NabucoExtensionContainer(ext);

        } catch (ExtensionException ex) {
            throw new ExtensionParserException(ex);
        }

    }
}
