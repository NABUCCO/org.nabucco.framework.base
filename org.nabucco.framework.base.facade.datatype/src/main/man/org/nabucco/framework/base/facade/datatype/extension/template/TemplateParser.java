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
package org.nabucco.framework.base.facade.datatype.extension.template;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.common.extension.parser.ExtensionParser;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.NabucoExtensionContainer;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionParserSupport;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateStructureItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.DynamicElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.GroupComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.StaticTextElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.TemplateRootComposite;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TemplateParser extends NabuccoExtensionParserSupport implements ExtensionParser {

    private static final String ELEMENT_TEMPLATE = "template";

    private static final String ELEMENT_GROUP = "group";

    private static final String ELEMENT_STATICTEXT = "statictext";

    private static final String ELEMENT_DYNAMIC = "dynamic";

    private static final String ATTR_ID = "id";

    private static final String ATTR_TRANSIENT = "transient";

    private static final String ATTR_VALUE = "value";

    private static final String ATTR_VALUE_TRUE = "true";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(TemplateParser.class);

    /**
     * Specific implementation for Template extension parsing.
     * 
     */
    @Override
    public NabucoExtensionContainer parseExtension(Element e) throws ExtensionParserException {
        try {
            logger.debug("Parsing template structure ");

            TemplateExtension ext = new TemplateExtension();
            ext.setIdentifier(e.getAttribute(ATTR_ID));

            // there is only support for one template element per extension definition
            List<Element> foundRootElements = getElementsByTagName(e, ELEMENT_TEMPLATE);
            int numberOfFoundRootElements = foundRootElements.size();

            if (numberOfFoundRootElements == 0) {
                logger.error("No root element ('" + ELEMENT_TEMPLATE + "') found!");
                throw new ExtensionParserException("No root element ('" + ELEMENT_TEMPLATE + "') found!");
            }

            if (numberOfFoundRootElements > 1) {
                logger.warning("Currently only one template root element is allowed! (Found: #"
                        + numberOfFoundRootElements + ")");
            }

            Element templateRootXmlElement = foundRootElements.get(0);
            TemplateRootComposite templateRoot = buildRootElement(templateRootXmlElement);
            ext.getItemList().add(templateRoot);

            return new NabucoExtensionContainer(ext);
        } catch (ExtensionParserException ex) {
            logger.error(ex, "Caught extension parser exception during Template extension parsing.");
            throw new ExtensionParserException(ex);
        } catch (ExtensionException ex) {
            logger.error(ex, "Caught Extension exception during Template extension parsing");
            throw new ExtensionParserException(ex);
        }
    }

    /**
     * Iterates over child nodes of the root element, given in e.
     * 
     * @param e
     *            Root element in XML
     * @return The prepared root composite.
     * 
     * @throws ExtensionParserException
     * @throws ExtensionException
     */
    private TemplateRootComposite buildRootElement(Element e) throws ExtensionParserException, ExtensionException {
        TemplateRootComposite rootComposite = new TemplateRootComposite();

        StringProperty rootElementId = new StringProperty();
        rootElementId.setValue(e.getAttribute(ATTR_ID));
        rootComposite.setItemId(rootElementId);
        logger.trace("Found template root element with ID '"
                + rootComposite.getItemId().getValue().getValueAsString() + "'");

        // iterate thru "direct" child elements
        NabuccoList<TemplateStructureItem> templateItemList = rootComposite.getItemList();
        NodeList rootChildItemList = e.getChildNodes();
        int rootChildItemCount = rootChildItemList.getLength();

        for (int i = 0; i < rootChildItemCount; i++) {
            Node currentRootChildNode = rootChildItemList.item(i);
            if (currentRootChildNode.getNodeType() == Node.ELEMENT_NODE) {
                String curNodeName = currentRootChildNode.getNodeName();
                logger.trace("Found element node. Node name: '" + curNodeName + "'");
                Element currentRootChild = (Element) currentRootChildNode;
                templateItemList.add(buildElement(currentRootChild));
                logger.trace("Added element node '" + currentRootChildNode.getNodeName() + "' to root child list");
            }
        }

        return rootComposite;
    }

    /**
     * Iterates thru the complete node tree under e and generates composite and leaf representations
     * in our Template datatypes.
     * 
     * @param e
     *            The XML element to iterate over
     * @return Filled composite with all items found in the element's child nodes.
     * @throws ExtensionParserException
     */
    private TemplateStructureItem buildElement(Element e) throws ExtensionParserException {
        TemplateStructureItem result = null;

        String nodeName = e.getNodeName().toLowerCase();

        if (nodeName == ELEMENT_GROUP) {
            logger.trace("Found group composite element in XML tree.");
            result = buildElement(new GroupComposite(), e);
        }
        if (nodeName == ELEMENT_STATICTEXT) {
            logger.trace("Found static text leaf element in XML tree.");
            result = buildElement(new StaticTextElement(), e);
        }
        if (nodeName == ELEMENT_DYNAMIC) {
            logger.trace("Found dynamic leaf element in XML tree.");
            result = buildElement(new DynamicElement(), e);
        }

        return result;
    }

    /**
     * Creates an &lt;dynamic&gt; leaf, based on the XML element e.
     * 
     * @param leaf
     *            The dynamic leaf instance
     * @param e
     *            The XML element, describing the dynamic element
     * @return leaf, filled with information found in e.
     * @throws ExtensionParserException
     *             Is e.g. thrown if a mandatory attribute is missing
     */
    private TemplateStructureItem buildElement(DynamicElement leaf, Element e) throws ExtensionParserException {
        logger.trace("Building DynamicElement leaf.");

        // retrieve ID, if available
        leaf = this.getIdAttribute(leaf, e);

        String isTransientAsString = e.getAttribute(ATTR_TRANSIENT).toLowerCase();
        if (isTransientAsString.equals(ATTR_VALUE_TRUE)) {
            leaf.setIsTransient(true);
        } else {
            leaf.setIsTransient(true);
        }
        logger.trace(ATTR_TRANSIENT
                + " attribute for dynamic leaf is set to '" + leaf.getIsTransient().getValueAsString() + "'");

        if (e.hasAttribute(ATTR_VALUE)) {
            leaf.setValue(e.getAttribute(ATTR_VALUE));
            logger.trace("Set value to '"
                    + leaf.getValue().getValueAsString() + "' on element " + e.getNodeName() + "'");
        } else {
            ExtensionParserException epe = new ExtensionParserException("Missing 'value' attribute on element '"
                    + e.getNodeName() + "'");
            logger.error(epe.getMessage());
            throw epe;
        }

        return leaf;
    }

    /**
     * Creates &lt;staticText&gt; element representation, based on information found in e.
     * 
     * @param leaf
     *            The static text element to fill
     * @param e
     *            The XML element, describing the static text
     * @return leaf, filled with information foudn in e
     */
    private TemplateStructureItem buildElement(StaticTextElement leaf, Element e) {
        logger.trace("Building StaticTextElement leaf.");

        // retrieve ID, if available
        leaf = this.getIdAttribute(leaf, e);

        StringProperty textContent = new StringProperty();
        textContent.setValue(e.getTextContent());
        leaf.setContent(textContent);
        logger.trace("Static text content is: '" + leaf.getContent().getValue().getValueAsString() + "'");

        return leaf;
    }

    /**
     * Fills the given composite element with information found in e and e's child nodes.
     * 
     * FIXME abstract it to "composites" to avoid copy&paste this for any composite element.
     * 
     * @param composite
     *            The composite instance to fill
     * @param e
     *            The XML element with information
     * @return composite, filled with information of e and e's child nodes.
     */
    private TemplateStructureItem buildElement(GroupComposite composite, Element e) {
        // set element's id - if available

        // retrieve ID, if available
        composite = this.getIdAttribute(composite, e);

        NabuccoList<TemplateStructureItem> itemList = composite.getItemList();

        NodeList nodeList = e.getChildNodes();
        int nodeListCount = nodeList.getLength();
        for (int i = 0; i < nodeListCount; i++) {
            Node currentNode = nodeList.item(i);

            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element currentChildElement = (Element) currentNode;
                logger.trace("Found child element '" + currentChildElement.getNodeName() + "' in composite.");
                try {
                    TemplateStructureItem childElement = buildElement(currentChildElement);
                    if (childElement != null) {
                        itemList.add(childElement);
                        logger.trace("Added found child element '"
                                + currentChildElement.getNodeName() + "' to composite.");
                    } else {
                        logger.debug("Child element '"
                                + currentChildElement.getNodeName() + "' is currently not supported");
                    }
                } catch (ExtensionParserException epe) {
                    /*
                     * This is only an extension, so this exception is caught here. Just logging
                     * would do it for the moment
                     */
                    logger.warning(epe, "Could not create template structure. See attached exception.");
                }
            }
        }

        return composite;
    }

    /**
     * Retrieves optional ID attribute of a given element e and put it in the instance element
     * 'element'.
     * 
     * @param element
     *            An template structure item
     * @param e
     *            The XML node the ID attribute is searched on
     * @return element with the ID set, if available
     */
    private <T extends TemplateStructureItem> T getIdAttribute(T element, Element e) {
        try {
            element.setItemId(getStringProperty(e, ATTR_ID));
            logger.trace("Found "
                    + ATTR_ID + " attribute on '" + e.getNodeName() + "' element. Value: '"
                    + element.getItemId().getValue().getValueAsString() + "'");
        } catch (ExtensionParserException epe) {
            // ID is optional on static text elements. So we just log that out...
            logger.info("No " + ATTR_ID + " attribute found on '" + e.getNodeName() + "'.");
        }

        return element;
    }
}
