/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.extension.template.datastructure;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSChoiseItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSDateItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSDescriptionItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElementGroup;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElementItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSFlagItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSKeyExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSTextItem;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * Template Datastrcuture parser. Parses the extension of dynamical datastructure.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TemplateDatastructureExtensionParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            TemplateDatastructureExtensionParser.class);

    private static final String ELEMENT_DATE = "date";

    private static final String ELEMENT_FLAG = "flag";

    private static final String ELEMENT_CHOISE = "choise";

    private static final String ELEMENT_TEXT = "text";

    private static final String ELEMENT_DATASTRUCTURE = "datastructure";

    private static final String ELEMENT_DESCRIPTION = "description";

    private static final String ATTR_MANDATORY = "mandatory";

    private static final String ATTR_DEFAULT_VALUE = "defaultValue";

    private static final String ATTR_DESCRIPTION = "description";

    private static final String ATTR_NAME = "name";

    private static final String ATTR_SELECTION_PATH = "selectionPath";

    private static final String ATTR_VERSION = "version";

    private static final String ATTR_ID = "id";

    private static final String ELEMENT_GROUP = "group";

    private static final String ELEMENT_KEYS = "keys";

    private static final String ATTR_KEY = "key";

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        try {
            logger.debug("Start loading of datastructure template extensions");

            TemplateDSExtension extension = new TemplateDSExtension();
            String extName = super.getAttribute(element, EXTENSION_ID);
            extension.setIdentifier(new ExtensionId(extName));
            extension.setName(extName);

            List<Element> mappingList = super.getElementsByTagName(element, ELEMENT_DATASTRUCTURE);
            if (mappingList.isEmpty()) {
                throw new ExtensionParserException("No Extension Datastructure defined in extension.");
            }
            if (mappingList.size() > 1) {
                throw new ExtensionParserException("More than 1 Extension Datastructure defined in extension.");
            }

            Element datatemplateElement = mappingList.get(0);
            String version = super.getAttribute(datatemplateElement, ATTR_VERSION);
            if (version == null || version.isEmpty()) {
                throw new ExtensionParserException("Cannot parse extension. Version is not set.");
            }
            Long versionNumber = Long.parseLong(version);
            extension.setVersion(versionNumber);

            List<Element> children = super.getChildren(datatemplateElement);

            for (Element child : children) {
                if (child.getTagName().equals(ELEMENT_GROUP)) {
                    TemplateDSElement structure = this.parseTemplateEntry(child);
                    extension.setStructure(structure);
                } else if (child.getTagName().equals(ELEMENT_KEYS)) {
                    List<TemplateDSKeyExtension> templateKeys = this.parseTemplateKeys(child);
                    extension.getKeyList().addAll(templateKeys);
                }
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException("Error parsing connection extension.", e);
        }
    }

    /**
     * Parses the template datatstructure
     * 
     * @param element
     *            the root element to be parsed
     * @return parsed ds element
     * @throws ExtensionParserException
     */
    public TemplateDSElement parseTemplateEntry(Element element) throws ExtensionParserException {
        TemplateDSElement retVal = null;
        String tagName = element.getTagName();

        if (tagName.equals(ELEMENT_GROUP)) {
            retVal = this.parseTemplateEntryGroup(element);
        } else {
            retVal = this.parseTemplateEntryItem(element);
        }

        return retVal;
    }

    /**
     * Parses the template entry group
     * 
     * @param element
     *            the element to be parsed
     * @return the parsed group extension
     * @throws ExtensionParserException
     */
    public TemplateDSElementGroup parseTemplateEntryGroup(Element element) throws ExtensionParserException {
        TemplateDSElementGroup retVal = new TemplateDSElementGroup();
        retVal.setName(super.getStringProperty(element, ATTR_NAME));

        List<Element> children = super.getChildren(element);
        for (Element child : children) {
            TemplateDSElement childElement = this.parseTemplateEntry(child);
            retVal.getChildrenList().add(childElement);
        }

        return retVal;
    }

    /**
     * Parses the template keys
     * 
     * @param element
     *            the element to be parsed
     * @return the list of parsed key extensions
     * @throws ExtensionParserException
     */
    public List<TemplateDSKeyExtension> parseTemplateKeys(Element element) throws ExtensionParserException {
        List<TemplateDSKeyExtension> retVal = new ArrayList<TemplateDSKeyExtension>();

        List<Element> children = super.getChildren(element);
        for (Element child : children) {
            TemplateDSKeyExtension key = new TemplateDSKeyExtension();
            key.setId(super.getStringProperty(child, ATTR_ID));
            retVal.add(key);
        }

        return retVal;
    }

    /**
     * parses template datatstructure entry item
     * 
     * @param element
     *            the element to be parsed
     * @return
     * @throws ExtensionParserException
     */
    public TemplateDSElementItem parseTemplateEntryItem(Element element) throws ExtensionParserException {
        TemplateDSElementItem retVal;
        String tagName = element.getTagName();

        if (tagName.equals(ELEMENT_TEXT)) {
            retVal = new TemplateDSTextItem();
        } else if (tagName.equals(ELEMENT_DESCRIPTION)) {
            retVal = new TemplateDSDescriptionItem();
        } else if (tagName.equals(ELEMENT_CHOISE)) {
            TemplateDSChoiseItem choise = new TemplateDSChoiseItem();
            choise.setSelectionPath(super.getStringProperty(element, ATTR_SELECTION_PATH));
            retVal = choise;
        } else if (tagName.equals(ELEMENT_FLAG)) {
            retVal = new TemplateDSFlagItem();
        } else if (tagName.equals(ELEMENT_DATE)) {
            retVal = new TemplateDSDateItem();
        } else {
            logger.debug("Cannot find datastructure element with name ", tagName,
                    ". Default interpreted as text element");
            retVal = new TemplateDSTextItem();
        }

        retVal.setName(super.getStringProperty(element, ATTR_NAME));
        retVal.setMandatory(super.getBooleanProperty(element, ATTR_MANDATORY, false));
        retVal.setDefaultValue(super.getStringProperty(element, ATTR_DEFAULT_VALUE));
        retVal.setDescription(super.getStringProperty(element, ATTR_DESCRIPTION));
        retVal.setKey(super.getStringProperty(element, ATTR_KEY));

        return retVal;
    }
}
