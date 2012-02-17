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
package org.nabucco.framework.base.facade.datatype.extension.template.mapping;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingField;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingFieldSet;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingFragment;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * TemplateMappingExtensionParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TemplateMappingExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_MAPPING = "mapping";

    private static final String ELEMENT_FIELDSET = "fieldSet";

    private static final String ELEMENT_FIELD = "field";

    private static final String ELEMENT_FRAGMENT = "fragment";

    private static final String ATTR_KEY = "key";

    private static final String ATTR_DEFAULT = "default";

    private static final String ATTR_FRAGMENT_ID = "fragmentId";

    private static final String ATTR_TEMPLATE = "template";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            TemplateMappingExtensionParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {
        try {
            logger.debug("Parsing template mapping configuration.");

            TemplateMappingExtension extension = new TemplateMappingExtension();
            extension.setIdentifier(new ExtensionId(super.getAttribute(element, EXTENSION_ID)));

            List<Element> mappingList = super.getElementsByTagName(element, ELEMENT_MAPPING);
            if (mappingList.isEmpty()) {
                throw new ExtensionParserException("No Extension Mapping defined in extension.");
            }
            if (mappingList.size() > 1) {
                throw new ExtensionParserException("More than 1 Extension Mapping defined in extension.");
            }

            Element mappingElement = mappingList.get(0);
            extension.setTemplate(super.getStringProperty(mappingElement, ATTR_TEMPLATE));

            List<Element> rootSet = super.getChildren(mappingElement, ELEMENT_FIELDSET);
            if (rootSet.isEmpty()) {
                throw new ExtensionParserException("No Extension Mapping Root FieldSet defined in extension.");
            }
            if (rootSet.size() > 1) {
                throw new ExtensionParserException("More than 1 Extension Mapping Root FieldSet defined in extension.");
            }

            Element rootSetElement = rootSet.get(0);
            extension.setRoot(this.parseFieldSet(rootSetElement));

            for (Element fragmentElement : super.getElementsByTagName(mappingElement, ELEMENT_FRAGMENT)) {
                TemplateMappingFragment fragment = this.parseFragment(fragmentElement);
                extension.getFragments().add(fragment);
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Error parsing connection extension.", e);
        }
    }

    /**
     * Parse a XML field set to its extension.
     * 
     * @param fieldSetElement
     *            the xml element to parse
     * 
     * @return the field extension
     * 
     * @throws ExtensionParserException
     *             when the field cannot be parsed
     */
    private TemplateMappingFieldSet parseFieldSet(Element fieldSetElement) throws ExtensionParserException {

        TemplateMappingFieldSet fieldSet = new TemplateMappingFieldSet();
        fieldSet.setKey(super.getStringProperty(fieldSetElement, ATTR_KEY));
        fieldSet.setDefaultValue(super.getStringProperty(fieldSetElement, ATTR_DEFAULT));
        fieldSet.setFragmentId(super.getStringProperty(fieldSetElement, ATTR_FRAGMENT_ID));

        for (Element childElement : super.getChildren(fieldSetElement, ELEMENT_FIELDSET)) {
            TemplateMappingFieldSet child = this.parseFieldSet(childElement);
            fieldSet.getFieldList().add(child);
        }

        for (Element childElement : super.getChildren(fieldSetElement, ELEMENT_FIELD)) {
            TemplateMappingField child = this.parseField(childElement);
            fieldSet.getFieldList().add(child);
        }

        return fieldSet;
    }

    /**
     * Parse a single XML field to its extension.
     * 
     * @param fieldElement
     *            the xml element to parse
     * 
     * @return the field extension
     * 
     * @throws ExtensionParserException
     *             when the field cannot be parsed
     */
    private TemplateMappingField parseField(Element fieldElement) throws ExtensionParserException {

        TemplateMappingField field = new TemplateMappingField();
        field.setKey(super.getStringProperty(fieldElement, ATTR_KEY));
        field.setDefaultValue(super.getStringProperty(fieldElement, ATTR_DEFAULT));

        return field;
    }

    /**
     * Parses a single fragment element.
     * 
     * @param fragmentElement
     *            the xml fragment
     * 
     * @return the extension fragment
     * 
     * @throws ExtensionException
     *             when the fragment cannot be parsed
     * @throws ExtensionParserException
     *             when the text content cannot be parsed
     */
    private TemplateMappingFragment parseFragment(Element fragmentElement) throws ExtensionException,
            ExtensionParserException {
        TemplateMappingFragment fragment = new TemplateMappingFragment();

        fragment.setIdentifier(super.getAttribute(fragmentElement, EXTENSION_ID));
        fragment.setContent(super.getTextContent(fragmentElement));

        return fragment;
    }
}
