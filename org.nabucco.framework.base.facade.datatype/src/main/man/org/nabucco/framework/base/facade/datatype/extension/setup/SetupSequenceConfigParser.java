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
import org.nabucco.common.extension.NabuccoExtension;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceConfigExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceGeneratorExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceTemplateEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceTemplateExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;

/**
 * SetupSequenceConfigParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SetupSequenceConfigParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ATTR_ID = "id";

    private static final String ATTR_TYPE = "type";

    private static final String ATTR_START = "start";

    private static final String ATTR_OVERFLOW = "overflow";

    private static final String ATTR_STEPS = "steps";

    private static final String ATTR_LENGTH = "length";

    private static final String ATTR_LEADINGZEROS = "leadingZeros";

    private static final String ATTR_AUTORESET = "autoReset";

    private static final String ATTR_PATTERN = "pattern";

    private static final String ATTR_LOCALE = "locale";

    private static final String ATTR_VALUE = "value";

    private static final String TAG_GENERATOR = "generator";

    private static final String TAG_TEMPLATE = "template";

    private static final String TAG_GENERATOR_REF = "generator-ref";

    private static final String TAG_FIXEDTEXT = "fixed-text";

    private static final String DEFAULT_LOCALE = "EN";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance()
            .getLogger(SetupSequenceConfigParser.class);

    @Override
    public NabuccoExtension parseExtension(Element element) throws ExtensionParserException {

        logger.debug("Parsing sequence configuration.");

        try {
            SequenceConfigExtension extension = new SequenceConfigExtension();
            extension.setIdentifier(new ExtensionId(element.getAttribute(ATTR_ID)));

            List<Element> generators = super.getElementsByTagName(element, TAG_GENERATOR);
            for (Element generator : generators) {
                extension.getGenerators().add(this.parseGeneratorExtension(generator));
            }

            List<Element> templates = super.getElementsByTagName(element, TAG_TEMPLATE);
            for (Element template : templates) {
                extension.getTemplates().add(this.parseTemplateExtension(template));
            }

            return new NabucoExtensionContainer(extension);

        } catch (ExtensionException e) {
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Parse the <generator> extension.
     * 
     * @param generator
     *            the generator xml element
     * 
     * @return the generator extension
     * 
     * @throws ExtensionParserException
     *             when the generator extension cannot be parsed
     */
    private SequenceGeneratorExtension parseGeneratorExtension(Element generator) throws ExtensionParserException {
        SequenceGeneratorExtension generatorExtension = new SequenceGeneratorExtension();
        generatorExtension.setId(super.getStringProperty(generator, ATTR_ID));
        generatorExtension.setType(super.getEnumerationProperty(generator, ATTR_TYPE));
        generatorExtension.setStart(super.getStringProperty(generator, ATTR_START));
        generatorExtension.setOverflow(super.getBooleanProperty(generator, ATTR_OVERFLOW));
        generatorExtension.setSteps(super.getStringProperty(generator, ATTR_STEPS));
        generatorExtension.setLength(super.getStringProperty(generator, ATTR_LENGTH));
        generatorExtension.setLeadingZeros(super.getBooleanProperty(generator, ATTR_LEADINGZEROS));
        generatorExtension.setAutoReset(super.getEnumerationProperty(generator, ATTR_AUTORESET));
        generatorExtension.setPattern(super.getStringProperty(generator, ATTR_PATTERN));
        return generatorExtension;
    }

    /**
     * Parse the <template> extension.
     * 
     * @param template
     *            the template xml element
     * 
     * @return the template extension
     * 
     * @throws ExtensionParserException
     *             when the template extension cannot be parsed
     */
    private SequenceTemplateExtension parseTemplateExtension(Element template) throws ExtensionParserException {
        SequenceTemplateExtension templateExtension = new SequenceTemplateExtension();
        templateExtension.setLocale(super.getStringProperty(template, ATTR_LOCALE, DEFAULT_LOCALE));

        List<Element> children = this.getChildren(template);

        for (Element child : children) {
            SequenceTemplateEntryExtension entryExtension = new SequenceTemplateEntryExtension();

            if (child.getTagName().equalsIgnoreCase(TAG_GENERATOR_REF)) {
                entryExtension.setGeneratorId(super.getStringProperty(child, ATTR_ID));
            } else if (child.getTagName().equalsIgnoreCase(TAG_FIXEDTEXT)) {
                entryExtension.setValue(super.getStringProperty(child, ATTR_VALUE));
            } else {
                throw new ExtensionParserException("Cannot resolve sequence template type <"
                        + child.getTagName() + ">.");
            }

            templateExtension.getEntries().add(entryExtension);
        }

        return templateExtension;
    }

}
