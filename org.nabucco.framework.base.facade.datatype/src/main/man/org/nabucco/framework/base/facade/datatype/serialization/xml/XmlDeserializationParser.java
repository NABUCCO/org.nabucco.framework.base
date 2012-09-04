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
package org.nabucco.framework.base.facade.datatype.serialization.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.converter.BasetypeConverter;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.EnumProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationConstants;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.resource.DeserializationResourceContainer;
import org.nabucco.framework.base.facade.datatype.serialization.resource.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * XmlDeserializationParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
final class XmlDeserializationParser {

    private Map<String, Resource> resourceMap = new HashMap<String, Resource>();

    private Map<Integer, Datatype> datatypeMap = new HashMap<Integer, Datatype>();

    /**
     * Parse the XML document and instantiate the serialized datatypes.
     * 
     * @param xml
     *            the serialized datatype as XML
     * @param resourceData
     *            the resource data
     * 
     * @return the list of deserialized root datatypes
     * 
     * @throws SerializationException
     *             when a datatype cannot be deserialized, or the xml is not valid
     */
    public List<Datatype> parseXml(String xml, byte[] resourceData) throws SerializationException {

        if (resourceData != null) {
            this.loadResources(resourceData);
        }

        Document document;
        StringReader reader = new StringReader(xml);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputSource source = new InputSource();
            source.setCharacterStream(reader);
            source.setEncoding(SerializationConstants.CHARSET);

            document = builder.parse(source);

            if (document == null) {
                throw new SerializationException("Cannot deserialize XML. Content is not a valid Datatype ["
                        + xml + "].");
            }

        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize XML. Content is not a valid Datatype.", e);
        } finally {
            reader.close();
        }

        List<Datatype> datatypeList = new ArrayList<Datatype>();
        for (Element element : this.getChildren(document)) {
            this.parseDatatypeList(element, datatypeList);
        }

        return datatypeList;
    }

    /**
     * Load the archived resources.
     * 
     * @param resourceData
     *            the resources zip as byte array
     * 
     * @throws SerializationException
     *             when the resources cannot be unzipped
     */
    private void loadResources(byte[] resourceData) throws SerializationException {
        try {
            DeserializationResourceContainer container = null;
            try {
                container = new DeserializationResourceContainer(resourceData);
                this.resourceMap = container.getAllResources();
            } finally {
                if (container != null) {
                    container.close();
                }
            }
        } catch (IOException ioe) {
            throw new SerializationException("Cannot deserialize XML resources. Resources are not accessable.");
        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize XML resources.", e);
        }
    }

    /**
     * Parse the list of datatypes from a given root XML node.
     * 
     * @param parent
     *            the parent node
     * @param datatypeList
     *            the list of datatypes to add the deserlized datatypes
     * 
     * @throws SerializationException
     *             when the datatype list cannot be deserialized
     */
    private void parseDatatypeList(Element parent, List<Datatype> datatypeList) throws SerializationException {

        Element links = null;

        for (Element child : this.getChildren(parent)) {
            if (child.getNodeName().equals(SerializationConstants.TAG_LINKS)) {
                if (links != null) {
                    throw new SerializationException("Multiple <Links> tags found.");
                }
                links = child;
            } else {
                Datatype datatype = this.parseDatatype(child);
                datatypeList.add(datatype);
            }
        }

        // Links must be parsed last!
        if (links != null) {
            this.parseLinks(links);
        }
    }

    /**
     * Parse a single datatype XML fragment.
     * 
     * @param element
     *            the XML element
     * 
     * @return the parsed datatype
     * 
     * @throws SerializationException
     *             when the datatype cannot be deserialized
     */
    private Datatype parseDatatype(Element element) throws SerializationException {
        String type = element.getAttribute(SerializationConstants.ATTRIBUTE_TYPE);
        if (type == null) {
            throw new SerializationException("Cannot deserialize datatype "
                    + element.getNodeName() + " with type [null].");
        }

        String id = element.getAttribute(SerializationConstants.ATTRIBUTE_ID);

        if (id == null) {
            throw new SerializationException("Cannot deserialize datatype "
                    + element.getNodeName() + " with id [null].");
        }

        try {
            Integer instanceId = Integer.parseInt(id);
            Datatype datatype = this.datatypeMap.get(instanceId);

            if (datatype == null) {
                datatype = this.instantiate(type);
                this.datatypeMap.put(instanceId, datatype);
                this.parseChildren(element, datatype);
            }

            return datatype;

        } catch (NumberFormatException nfe) {
            throw new SerializationException("Cannot deserialize id ["
                    + id + "] of element " + element.getNodeName() + ".", nfe);
        }
    }

    /**
     * Parse the basetype and enumeration properties of a datatype XML fragment.
     * 
     * @param element
     *            the datatype XML fragment
     * @param parent
     *            the datatype
     * 
     * @throws SerializationException
     */
    private void parseChildren(Element element, Datatype parent) throws SerializationException {

        // DatatypeState
        DatatypeState state = this.parseState(element);
        parent.setDatatypeState(state);

        // Other Properties
        for (NabuccoProperty property : parent.getProperties()) {

            switch (property.getPropertyType()) {

            case BASETYPE: {
                BasetypeProperty basetypeProperty = (BasetypeProperty) property;
                Element child = this.getElement(element, property.getName());
                basetypeProperty = this.parseBasetype(basetypeProperty, child);
                parent.setProperty(basetypeProperty);
                break;
            }

            case ENUMERATION: {
                EnumProperty enumProperty = (EnumProperty) property;
                Element child = this.getElement(element, property.getName());
                enumProperty = this.parseEnum(enumProperty, child);
                parent.setProperty(enumProperty);
                break;
            }

            case DATATYPE: {
                DatatypeProperty datatypeProperty = (DatatypeProperty) property;
                if (datatypeProperty.getAssociationType() == PropertyAssociationType.COMPONENT) {
                    Element child = this.getElement(element, property.getName() + SerializationConstants.REFERENCE_ID);
                    this.parseRefId(datatypeProperty, child, parent);
                }
            }

                // Datatypes are connected by links!
            }
        }
    }

    /**
     * Parse the datatype state of a given datatype.
     * 
     * @param parent
     *            the parent element
     * 
     * @return the datatype state
     * 
     * @throws SerializationException
     *             when the state cannot be deserialized
     */
    private DatatypeState parseState(Element parent) throws SerializationException {

        Element child = this.getElement(parent, SerializationConstants.TAG_STATE);
        String value = this.parseValue(child);

        try {
            return DatatypeState.valueOf(value);
        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize DatatypeState value [" + value + "].", e);
        }
    }

    /**
     * Parse a {@link Basetype} from XML.
     * 
     * @param property
     *            the basetype property.
     * @param child
     *            the XML element
     * 
     * @return the parsed basetype
     * 
     * @throws SerializationException
     *             when the basetype cannot be deserialized
     */
    private BasetypeProperty parseBasetype(BasetypeProperty property, Element child) throws SerializationException {

        String value = this.parseValue(child);

        try {
            if (value != null && !value.equals(SerializationConstants.NULL_VALUE)) {
                Basetype basetype = (Basetype) property.getType().newInstance();

                switch (basetype.getType()) {

                case BYTE_ARRAY: {
                    Resource resource = this.resourceMap.get(value);
                    if (resource != null) {
                        basetype.setValue(resource.getContent());
                    }
                    break;
                }
                case TEXT: {
                    Resource resource = this.resourceMap.get(value);
                    if (resource != null) {
                        basetype.setValue(new String(resource.getContent(), SerializationConstants.CHARSET));
                    }
                    break;
                }
                case STRING: {
                    BasetypeConverter.setValueAsString(basetype, XmlSpecialCharacterHandling.unquote(value));
                    break;
                }
                default: {
                    BasetypeConverter.setValueAsString(basetype, value);
                    break;
                }
                }
                return (BasetypeProperty) property.createProperty(basetype);
            }

        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize Basetype '" + property.getName() + "'.", e);
        }

        return property;
    }

    /**
     * Parse a {@link Enumeration} from XML.
     * 
     * @param property
     *            the enumeration property.
     * @param child
     *            the XML element
     * 
     * @return the parsed enumeration
     * 
     * @throws SerializationException
     *             when the enumeration cannot be deserialized
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private EnumProperty parseEnum(EnumProperty property, Element child) throws SerializationException {

        String value = this.parseValue(child);

        if (value == null || value.equals(SerializationConstants.NULL_VALUE)) {
            return (EnumProperty) property.createProperty(null);
        }

        try {
            Class<?> enumType = property.getType();
            Class<Enum> type = (Class<Enum>) enumType;
            Enumeration enumeration = (Enumeration) Enum.valueOf(type, value);
            EnumProperty newInstance = (EnumProperty) property.createProperty(enumeration);
            return newInstance;
        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize Enumeration literal ["
                    + value + "] for property " + property.getName() + ".", e);
        }
    }

    /**
     * Parse a reference ID from XML.
     * 
     * @param property
     *            the datatype property
     * @param child
     *            the child
     * @param parent
     *            the parent object
     * 
     * @throws SerializationException
     */
    private void parseRefId(DatatypeProperty property, Element child, Datatype parent) throws SerializationException {

        String refId = this.parseValue(child);

        if (refId == null || refId.isEmpty() || refId.equals(SerializationConstants.NULL_VALUE)) {
            return;
        }

        try {

            if (!NabuccoDatatype.class.isAssignableFrom(property.getType())) {
                throw new IllegalStateException(property.getType() + " is not a subclass of NabuccoDatatype.");
            }

            NabuccoDatatype reference = (NabuccoDatatype) property.getType().newInstance();
            reference.setId(Long.parseLong(refId));

            DatatypeProperty newInstance = (DatatypeProperty) property.createProperty(reference);
            parent.setProperty(newInstance);

        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize reference id "
                    + refId + "of property " + property.getName() + ".", e);
        }
    }

    /**
     * Parse the text content of an XML element.
     * 
     * @param element
     *            the element to parse
     * 
     * @return the text content
     * 
     * @throws SerializationException
     *             when the text cannot be deserialized
     */
    private String parseValue(Element element) throws SerializationException {
        if (element != null && element.hasChildNodes()) {
            return element.getTextContent();
        }
        return null;
    }

    /**
     * Create a new instance for the given type.
     * 
     * @param type
     *            the full qualified class name of a datatype
     * 
     * @return the datatype instance
     * 
     * @throws SerializationException
     *             when the datatype cannot be instantiated
     */
    private Datatype instantiate(String type) throws SerializationException {
        try {
            Class<?> clazz = Class.forName(type);
            if (!Datatype.class.isAssignableFrom(clazz)) {
                throw new SerializationException("Cannot deserialize datatype ["
                        + type + "]. It is not a valid implementation of "
                        + "'org.nabucco.framework.base.facade.datatype.Datatype'.");
            }

            return (Datatype) clazz.newInstance();

        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize datatype [" + type + "].", e);
        }
    }

    /**
     * Resolve a child element by its tag name.
     * 
     * @param parent
     *            the parent element holding the child
     * @param name
     *            name of the child
     * 
     * @return the child element
     * 
     * @throws SerializationException
     *             when the child cannot be resolved
     */
    private Element getElement(Element parent, String name) throws SerializationException {
        NodeList children = parent.getElementsByTagName(name);

        if (children.getLength() < 1) {
            return null;
            // throw new SerializationException("Cannot deserialize property ["
            // + name + "]. Property not found in " + parent.getNodeName() + " with id "
            // + parent.getAttribute(SerializationConstants.ATTRIBUTE_ID) + ".");
        }
        if (children.getLength() > 1) {
            throw new SerializationException("Cannot deserialize property ["
                    + name + "]. Property exists multiple times in " + parent.getNodeName() + " with id "
                    + parent.getAttribute(SerializationConstants.ATTRIBUTE_ID) + ".");
        }

        Node item = children.item(0);

        if (!(item instanceof Element)) {
            throw new SerializationException("Cannot deserialize property ["
                    + name + "]. Property in " + parent.getNodeName() + " with id "
                    + parent.getAttribute(SerializationConstants.ATTRIBUTE_ID) + " is not valid.");
        }

        return (Element) item;
    }

    /**
     * Parse the list of datatype links and create the relation between the related source and
     * target datatypes.
     * 
     * @param links
     *            the links tag
     * 
     * @throws SerializationException
     *             when the links cannot be deserialized
     */
    private void parseLinks(Element links) throws SerializationException {

        for (Element link : this.getChildren(links)) {
            String propertyName = link.getAttribute(SerializationConstants.ATTRIBUTE_NAME);
            if (propertyName == null) {
                throw new SerializationException("Cannot deserialize link with name [null].");
            }

            Integer sourceId = this.getId(link, SerializationConstants.ATTRIBUTE_SOURCE);
            Integer targetId = this.getId(link, SerializationConstants.ATTRIBUTE_TARGET);

            Datatype source = this.datatypeMap.get(sourceId);
            if (source == null) {
                throw new SerializationException("Cannot deserialize link with source ["
                        + sourceId + "]. Cannot find datatype with related ID.");
            }

            Datatype target = this.datatypeMap.get(targetId);
            if (target == null) {
                throw new SerializationException("Cannot deserialize link with target ["
                        + targetId + "]. Cannot find datatype with related ID.");
            }

            this.setProperty(propertyName, source, target);
        }

    }

    /**
     * Create the datatype relation.
     * 
     * @param propertyName
     *            the property name
     * @param source
     *            the source datatype
     * @param target
     *            the target datatype
     * 
     * @throws SerializationException
     *             when the property cannot be set
     */
    private void setProperty(String propertyName, Datatype source, Datatype target) throws SerializationException {

        for (NabuccoProperty property : source.getProperties()) {
            if (!propertyName.equals(property.getName())) {
                continue;
            }

            boolean succeeded;

            switch (property.getPropertyType()) {

            case DATATYPE:
                DatatypeProperty datatypeProperty = (DatatypeProperty) property;
                datatypeProperty = this.createDatatypeProperty(datatypeProperty, target);
                succeeded = source.setProperty(datatypeProperty);
                break;

            case COLLECTION:
                CollectionProperty collectionProperty = (CollectionProperty) property;
                collectionProperty = this.createCollectionProperty(collectionProperty, target);
                succeeded = source.setProperty(collectionProperty);
                break;

            default:
                throw new SerializationException("Cannot deserialize link for property "
                        + propertyName + ". Only Datatypes can be connected by links.");

                // Only Datatypes are connected by links!

            }

            if (!succeeded) {
                throw new SerializationException("Cannot deserialize property "
                        + propertyName + ". Property not found.");
            }
        }
    }

    /**
     * Create a new datatype property from the given property and target instance.
     * 
     * @param datatypeProperty
     *            the existing property
     * @param target
     *            the target datatype
     * 
     * @return the new property instance holding the target datatype
     */
    private DatatypeProperty createDatatypeProperty(DatatypeProperty datatypeProperty, Datatype target) {
        return (DatatypeProperty) datatypeProperty.createProperty(target);
    }

    /**
     * Create a new list property from the given property and target instance. When the existing
     * list has not alrady been initialized, a new {@link NabuccoListImpl} instance is created.
     * 
     * @param <N>
     *            the collection type
     * @param collectionProperty
     *            the existing property
     * @param target
     *            the target datatype
     * 
     * @return the new property instance holding the target datatype
     */
    private <N extends Datatype> CollectionProperty createCollectionProperty(CollectionProperty collectionProperty,
            N target) {

        @SuppressWarnings("unchecked")
        Collection<N> collection = (Collection<N>) collectionProperty.getInstance();

        if (collection == null) {
            collection = new NabuccoListImpl<N>();
        }

        collection.add(target);

        return (CollectionProperty) collectionProperty.createProperty(collection);
    }

    /**
     * List all child elements of an XML node.
     * 
     * @param node
     *            the parent node
     * 
     * @return the child elements
     */
    private List<Element> getChildren(Node node) {
        List<Element> elements = new ArrayList<Element>();

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element) {
                elements.add((Element) child);
            }
        }
        return elements;
    }

    /**
     * Resolves an integer ID from an XML elmement.
     * 
     * @param element
     *            the XML elment holding the ID attribute
     * @param name
     *            the name of the ID
     * @return the resolved ID
     * 
     * @throws SerializationException
     *             when the ID cannot be parsed to an integer
     */
    private Integer getId(Element element, String name) throws SerializationException {
        String attribute = element.getAttribute(name);

        if (attribute == null) {
            throw new SerializationException("Cannot deserialize " + name + " [null].");
        }

        try {
            int id = Integer.parseInt(attribute);
            return id;
        } catch (NumberFormatException nfe) {
            throw new SerializationException("Cannot deserialize " + name + " [" + attribute + "].", nfe);
        }
    }

}
