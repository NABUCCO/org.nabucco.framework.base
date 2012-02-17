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
package org.nabucco.framework.base.facade.datatype.extension;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionParserSupport;
import org.nabucco.common.extension.parser.ExtensionParserException;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationPropertyValue;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Adds NABUCCO specific methdos to the ExtensionParserSupport.
 * 
 * @author Frank Ratschinski
 */
public abstract class NabuccoExtensionParserSupport extends ExtensionParserSupport {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            NabuccoExtensionParserSupport.class);

    /**
     * Resolve an attribute of an XML Element as a class property.
     * 
     * @param element
     *            the element to parse
     * @param attr
     *            the attribute name
     * 
     * @return the parsed class property
     * 
     * @throws ExtensionParserException
     *             when the attribute cannot be parsed
     */
    protected ClassProperty getClassProperty(Element element, String attribute) throws ExtensionParserException {
        try {

            ClassProperty property = new ClassProperty();

            String clazz = super.getAttribute(element, attribute);
            property.setValue(clazz);
            return property;

        } catch (Exception e) {
            logger.warning("Parsing of class property [", attribute, "] fails.");
            throw new ExtensionParserException(e);
        }
    }



    /**
     * Load all child elements of the given parent xml element.
     * 
     * @param parent
     *            the parent xml element
     * 
     * @return the list of child elements
     */
    protected List<Element> getChildren(Element parent) {
        return this.getChildren(parent, null);
    }

    /**
     * Load all child elements of the given parent xml element.
     * 
     * @param parent
     *            the parent xml element
     * @param name
     *            the tag name to filter for, no filter for null
     * 
     * @return the list of child elements
     */
    protected List<Element> getChildren(Element parent, String name) {
        List<Element> elements = new ArrayList<Element>();

        NodeList children = parent.getChildNodes();
        int length = children.getLength();

        for (int i = 0; i < length; i++) {
            Node node = children.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (name == null || node.getNodeName().equalsIgnoreCase(name)) {
                    elements.add((Element) node);
                }
            }
        }

        return elements;
    }

    /**
     * Resolve the text content of an XML Element as string property.
     * 
     * @param element
     *            the element to parse
     * 
     * @return the parsed string property
     * 
     * @throws ExtensionParserException
     *             when the text content cannot be parsed
     */
    protected StringProperty getTextContent(Element element) throws ExtensionParserException {
        if (element == null) {
            return null;
        }

        try {
            String value = element.getTextContent();

            if (value != null) {
                value = value.replaceAll("\\t", " ");
                value = value.replaceAll("\\r\\n", " ");
                value = value.replaceAll("\\r", " ");
                value = value.replaceAll("\\n", " ");
                value = value.replaceAll("(\\s)+", " ");
                value = value.trim();
            }

            StringProperty sp = new StringProperty();
            sp.setValue(value);
            return sp;
        } catch (Exception e) {
            logger.warning("Parsing of string property fails.");
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Resolve an attribute of an XML Element as a string property.
     * 
     * @param element
     *            the element to parse
     * @param attribute
     *            the attribute name
     * 
     * @return the parsed class property
     * 
     * @throws ExtensionParserException
     *             when the attribute cannot be parsed
     */
    protected StringProperty getStringProperty(Element element, String attribute) throws ExtensionParserException {
        try {

            StringProperty sp = new StringProperty();
            String string = super.getAttribute(element, attribute);
            sp.setValue(string);
            return sp;

        } catch (Exception e) {
            logger.warning("Parsing of string property [", attribute, "] fails.");
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Resolve an attribute of an XML Element as a string property.
     * 
     * @param element
     *            the element to parse
     * @param attribute
     *            the attribute name
     * 
     * @return the parsed string property
     * 
     * @throws ExtensionParserException
     *             when the attribute cannot be parsed
     */
    protected StringProperty getStringProperty(Element element, String attribute, String defaultValue)
            throws ExtensionParserException {

        try {
            if (element.hasAttribute(attribute)) {
                StringProperty property = new StringProperty();
                String string = super.getAttribute(element, attribute);
                property.setValue(string);
                return property;
            }

            StringProperty property = new StringProperty();
            property.setValue(defaultValue);
            return property;

        } catch (Exception e) {
            logger.warning("Parsing of string property [", attribute, "] fails.");
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Resolve an attribute of an XML Element as an integer property.
     * 
     * @param element
     *            the element to parse
     * @param attribute
     *            the attribute name
     * @param defaultValue
     *            the attribute default value
     * 
     * @return the parsed integer property
     * 
     * @throws ExtensionParserException
     *             when the attribute cannot be parsed
     */
    protected IntegerProperty getIntegerProperty(Element element, String attribute, int defaultValue)
            throws ExtensionParserException {

        try {
            IntegerProperty property = new IntegerProperty();
            String i = super.getAttribute(element, attribute);
            Integer value = Integer.parseInt(i);
            property.setValue(value);
            return property;

        } catch (Exception e) {
            logger.warning("Parsing of integer property [", attribute, "] fails.");
            IntegerProperty property = new IntegerProperty();
            property.setValue(defaultValue);
            return property;
        }
    }

    /**
     * Resolve an attribute of an XML Element as a boolean property.
     * 
     * @param element
     *            the element to parse
     * @param attribute
     *            the attribute name
     * 
     * @return the parsed boolean property
     * 
     * @throws ExtensionParserException
     *             when the attribute cannot be parsed
     */
    protected BooleanProperty getBooleanProperty(Element element, String attribute) throws ExtensionParserException {
        return this.getBooleanProperty(element, attribute, false);
    }

    /**
     * Resolve an attribute of an XML Element as a boolean property.
     * 
     * @param element
     *            the element to parse
     * @param attribute
     *            the attribute name
     * 
     * @return the parsed boolean property
     * 
     * @throws ExtensionParserException
     *             when the attribute cannot be parsed
     */
    protected BooleanProperty getBooleanProperty(Element element, String attribute, boolean defaultValue)
            throws ExtensionParserException {

        try {
            BooleanProperty property = new BooleanProperty();
            String bool = super.getAttribute(element, attribute);

            if (bool == null || bool.isEmpty()) {
                property.setValue(defaultValue);
            } else {
                Boolean value = Boolean.parseBoolean(bool);
                property.setValue(value);
            }

            return property;

        } catch (Exception e) {
            logger.warning("Parsing of boolean property [", attribute, "] fails.");
            throw new ExtensionParserException(e);
        }
    }

    /**
     * Resolve an attribute of an XML Element as a enumeration property.
     * 
     * @param element
     *            the element to parse
     * @param attribute
     *            the attribute name
     * 
     * @return the parsed enumeration property
     * 
     * @throws ExtensionParserException
     *             when the attribute cannot be parsed
     */
    protected EnumerationProperty getEnumerationProperty(Element element, String attribute)
            throws ExtensionParserException {

        try {
            String literal = super.getAttribute(element, attribute);
            EnumerationProperty property = new EnumerationProperty();
            property.setValue(new EnumerationPropertyValue(literal));
            return property;
        } catch (Exception e) {
            logger.warning("Parsing of enum property [", attribute, "] fails.");
            throw new ExtensionParserException(e);
        }
    }

}
