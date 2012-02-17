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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.NByteArray;
import org.nabucco.framework.base.facade.datatype.NText;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.EnumProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationConstants;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationResult;
import org.nabucco.framework.base.facade.datatype.serialization.resource.Resource;
import org.nabucco.framework.base.facade.datatype.serialization.resource.ResourceType;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * XmlSerializationVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
final class XmlSerializationVisitor extends DatatypeVisitor {

    /* Class Constants */

    private static final char TAG_START = '<';

    private static final char TAG_END = '>';

    private static final char TAG_SLASH = '/';

    private static final int INDENT = 4;

    /* Instance Constants */

    private final SerializationResult result;

    private final Appendable xml;

    private final String name = "Datatype";

    private final boolean includeResources;

    /* Instance Variables */

    private int indent;

    private boolean isFinished = false;

    private Set<String> alreadyPrinted = new HashSet<String>();

    private List<XmlLink> linkList = new ArrayList<XmlLink>();

    /**
     * Creates a new {@link XmlSerializationVisitor} instance.
     * 
     * @param indent
     *            the common xml indent
     * @param includeResources
     *            include external resources or not
     */
    XmlSerializationVisitor(int indent, boolean includeResources) {
        this(indent, includeResources, new StringBuilder());
    }

    /**
     * Creates a new {@link XmlSerializationVisitor} instance.
     * 
     * @param indent
     *            the common xml indent
     * @param includeResources
     *            include external resources or not
     * @param appendable
     *            the appendable to write the content to
     */
    XmlSerializationVisitor(int indent, boolean includeResources, Appendable appendable) {
        super(false);

        if (appendable == null) {
            throw new IllegalArgumentException("Cannot create XmlSerializationVisitor for appendable [null].");
        }

        this.result = new SerializationResult();
        this.includeResources = includeResources;
        this.indent = indent;
        this.xml = appendable;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        try {
            Class<?> type = datatype.getClass();
            String typeName = type.getCanonicalName();
            String name = type.getSimpleName();
            String id = this.generateId(datatype);

            Map<String, String> attributes = new LinkedHashMap<String, String>();
            attributes.put(SerializationConstants.ATTRIBUTE_TYPE, typeName);
            attributes.put(SerializationConstants.ATTRIBUTE_ID, id);

            this.printIndent(INDENT);
            this.printXmlOpen(name, false, attributes);
            this.printNewLine();

            this.printIndent(INDENT);
            this.printState(datatype.getDatatypeState(), INDENT);

            for (NabuccoProperty property : datatype.getProperties()) {

                switch (property.getPropertyType()) {

                case BASETYPE:
                    this.printIndent(INDENT);
                    this.print((BasetypeProperty) property, INDENT);
                    break;

                case ENUMERATION:
                    this.printIndent(INDENT);
                    this.print((EnumProperty) property, INDENT);
                    break;

                case DATATYPE:
                    DatatypeProperty datatypeProperty = (DatatypeProperty) property;

                    if (datatypeProperty.getAssociationType() == PropertyAssociationType.COMPONENT) {
                        this.printIndent(INDENT);
                        this.printRefId(datatypeProperty, INDENT);
                    } else {
                        this.createLink(id, datatypeProperty);
                    }

                    break;

                case COLLECTION:
                    CollectionProperty collectionProperty = (CollectionProperty) property;

                    // Component References must not have a multiplicity > 1
                    if (collectionProperty.getAssociationType() != PropertyAssociationType.COMPONENT) {
                        this.createLinks(id, collectionProperty);
                    }

                    break;
                }
            }

            this.printIndent(INDENT);
            this.printXmlClose(name);
            this.printNewLine();

            if (this.alreadyPrinted.add(id)) {
                super.visit(datatype);
            }

        } catch (IOException e) {
            throw new VisitorException(e);
        }
    }

    /**
     * Creates a link between a datatype and its child property.
     * 
     * @param id
     *            the datatype id
     * @param datatypeProperty
     *            the datatype property
     */
    private void createLink(String id, DatatypeProperty datatypeProperty) {
        String targetId = this.generateId(datatypeProperty.getInstance());

        if (targetId == null) {
            return;
        }

        XmlLink link = new XmlLink(datatypeProperty.getName(), id, targetId);
        this.linkList.add(link);
    }

    /**
     * Creates the links between a datatype and its collection properties.
     * 
     * @param id
     *            the datatype id
     * @param collectionProperty
     *            the list property
     */
    private void createLinks(String id, CollectionProperty collectionProperty) {
        if (!collectionProperty.isTraversable()) {
            return;
        }

        for (NType child : collectionProperty.getInstance()) {
            if (child instanceof Datatype) {
                String targetId = this.generateId((Datatype) child);

                if (targetId == null) {
                    continue;
                }

                XmlLink link = new XmlLink(collectionProperty.getName(), id, targetId);
                this.linkList.add(link);
            }
        }
    }

    /**
     * Generates an instance ID.
     * 
     * @param datatype
     *            the datatype to generate an id for
     * 
     * @return the ID as String
     */
    private String generateId(Datatype datatype) {
        if (datatype == null) {
            return null;
        }
        return String.valueOf(System.identityHashCode(datatype));
    }

    /**
     * Print the datatype state.
     * 
     * @param state
     *            the datatype state
     * @param indent
     *            the indent to print
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    private void printState(DatatypeState state, int indent) throws IOException {
        this.printIndent(indent);
        this.printXmlOpen(SerializationConstants.TAG_STATE, false, null);

        this.xml.append(String.valueOf(state));

        this.printXmlClose(SerializationConstants.TAG_STATE);
        this.printNewLine();
    }

    /**
     * Prints the given basetype property.
     * 
     * @param property
     *            the value to print
     * @param indent
     *            the indent to print
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    private void print(BasetypeProperty property, int indent) throws IOException {
        this.printIndent(indent);

        if (property.getInstance() == null) {
            printXmlNullValue(property.getName());
            printNewLine();
            return;
        }

        this.printXmlOpen(property.getName(), false, null);

        Basetype basetype = property.getInstance();

        if (basetype == null || basetype.getValue() == null) {
            this.xml.append(null);
        } else {

            switch (basetype.getType()) {

            case BYTE_ARRAY: {

                if (this.includeResources) {
                    NByteArray byteArray = (NByteArray) basetype;

                    byte[] content = byteArray.getValue();
                    String resourceName = String.valueOf(Math.abs(basetype.hashCode()));
                    ResourceType type = ResourceType.BYTE_ARRAY;

                    Resource resource = new Resource(resourceName, content, type);
                    this.result.getResourceContainer().addResource(resource);
                    this.xml.append(resource.getPath());
                }

                break;
            }

            case TEXT: {

                if (this.includeResources) {
                    NText text = (NText) basetype;

                    byte[] content = text.getValue().getBytes(SerializationConstants.CHARSET);
                    String resourceName = String.valueOf(Math.abs(basetype.hashCode()));
                    ResourceType type = ResourceType.TEXT;

                    Resource resource = new Resource(resourceName, content, type);
                    this.result.getResourceContainer().addResource(resource);
                    this.xml.append(resource.getPath());
                }

                break;
            }

            case STRING: {
                this.xml.append(XmlSpecialCharacterHandling.quote(basetype.getValueAsString()));
                break;
            }

            default:
                this.xml.append(basetype.getValueAsString());
            }

        }

        this.printXmlClose(property.getName());
        this.printNewLine();
    }

    /**
     * Prints the given enumeration property.
     * 
     * @param property
     *            the value to print
     * @param indent
     *            the indent to print
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    private void print(EnumProperty property, int indent) throws IOException {
        this.printIndent(indent);
        this.printXmlOpen(property.getName(), false, null);

        Enumeration value = property.getInstance();
        this.xml.append(String.valueOf(value));

        this.printXmlClose(property.getName());
        this.printNewLine();
    }

    /**
     * Prints the given reference ID for the given datatype property.
     * 
     * @param property
     *            the property to print
     * @param indent
     *            the indent to print
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    private void printRefId(DatatypeProperty property, int indent) throws IOException {
        String propertyName = property.getName() + SerializationConstants.REFERENCE_ID;

        this.printIndent(indent);
        this.printXmlOpen(propertyName, false, null);

        Long id = property.getReferenceId();
        if (id != null) {
            this.xml.append(String.valueOf(id));
        } else {
            this.xml.append(SerializationConstants.NULL_VALUE);
        }

        this.printXmlClose(propertyName);
        this.printNewLine();
    }

    /**
     * Prints an empty value. in case of an unset or <code>null</code> value.
     * 
     * @param name
     *            the property name.
     * @throws IOException
     *             if an I/O error occurs
     * 
     */
    private void printXmlNullValue(String name) throws IOException {
        this.xml.append(TAG_START);
        this.xml.append(name);
        this.xml.append(TAG_SLASH);
        this.xml.append(TAG_END);
    }

    /**
     * Prints the opening XML tag.
     * 
     * @param name
     *            the tag name
     * @param close
     *            whether the tag should already been closed
     * @param attributes
     *            the tag attributes
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    private void printXmlOpen(String name, boolean close, Map<String, String> attributes) throws IOException {
        this.xml.append(TAG_START);
        this.xml.append(name);

        if (attributes != null) {
            for (Entry<String, String> attribute : attributes.entrySet()) {
                this.xml.append(' ');
                this.xml.append(attribute.getKey());
                this.xml.append('=');
                this.xml.append('"');
                this.xml.append(attribute.getValue());
                this.xml.append('"');
            }
        }

        if (close) {
            this.xml.append(' ');
            this.xml.append('/');
        }

        this.xml.append(TAG_END);
    }

    /**
     * Prints the closing XML tag.
     * 
     * @param name
     *            the tag name
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    private void printXmlClose(String name) throws IOException {
        this.xml.append(TAG_START);
        this.xml.append(TAG_SLASH);
        this.xml.append(name);
        this.xml.append(TAG_END);
    }

    /**
     * Create the closing XML tag.
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    private void printNewLine() throws IOException {
        this.xml.append('\r');
        this.xml.append('\n');
    }

    /**
     * Prints the indent.
     * 
     * @param indent
     *            the indent size
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    private void printIndent(int indent) throws IOException {
        for (int i = 0; i < indent + this.indent; i++) {
            this.xml.append(' ');
        }
    }

    /**
     * Finish the serialization process and returns the serialization result.
     * 
     * @return the serialization result
     * 
     * @throws SerializationException
     *             when the serialization failed.
     */
    public SerializationResult finish() throws SerializationException {
        String xml = this.finishXml();

        this.result.setContent(xml);

        if (this.includeResources) {
            try {
                String name = SerializationConstants.RESOURCE_CONTENT;
                byte[] content = xml.getBytes(SerializationConstants.CHARSET);
                ResourceType type = ResourceType.XML;
                Resource resource = new Resource(name, content, type);
                resource.setComment(SerializationConstants.RESOURCE_COMMENT);

                this.result.getResourceContainer().addResource(resource);

                this.result.getResourceContainer().flush();
                this.result.getResourceContainer().close();
            } catch (IOException e) {
                throw new SerializationException("Error creating resource archive.", e);
            }
        }

        return this.result;
    }

    /**
     * Finish the XML content.
     * 
     * @return the final XML string
     * 
     * @throws SerializationException
     */
    private String finishXml() throws SerializationException {
        if (!this.isFinished) {
            try {
                this.printNewLine();
                this.printIndent(INDENT);
                this.printXmlOpen(SerializationConstants.TAG_LINKS, false, null);
                this.printNewLine();

                for (XmlLink link : this.linkList) {
                    Map<String, String> attributes = new LinkedHashMap<String, String>();
                    attributes.put(SerializationConstants.ATTRIBUTE_NAME, link.getName());
                    attributes.put(SerializationConstants.ATTRIBUTE_SOURCE, link.getSource());
                    attributes.put(SerializationConstants.ATTRIBUTE_TARGET, link.getTarget());

                    this.printIndent(INDENT + INDENT);
                    this.printXmlOpen(SerializationConstants.TAG_LINK, true, attributes);
                    this.printNewLine();
                }

                this.printIndent(INDENT);
                this.printXmlClose(SerializationConstants.TAG_LINKS);

            } catch (IOException e) {
                throw new SerializationException("Unable to serialize datatype links.", e);
            }
        }

        StringBuilder prefix = new StringBuilder();
        prefix.append('<');
        prefix.append(this.name);
        prefix.append('>');
        prefix.append('\r');
        prefix.append('\n');

        StringBuilder suffix = new StringBuilder();
        suffix.append('\r');
        suffix.append('\n');
        suffix.append('<');
        suffix.append('/');
        suffix.append(this.name);
        suffix.append('>');

        StringBuilder result = new StringBuilder();
        result.append(prefix);
        result.append(this.xml);
        result.append(suffix);

        return result.toString();
    }

    @Override
    public String toString() {
        return this.xml.toString();
    }

}
