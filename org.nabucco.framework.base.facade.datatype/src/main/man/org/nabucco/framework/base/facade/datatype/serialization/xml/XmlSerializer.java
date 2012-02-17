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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.serialization.DeserializationData;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationResult;
import org.nabucco.framework.base.facade.datatype.serialization.Serializer;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * XmlSerializer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class XmlSerializer implements Serializer {

    /** The default indent. */
    public static final int DEFAULT_INDENT = 0;

    /** The default resource inclusion. */
    public static final boolean DEFAULT_INCLUDE_RESOURCE = false;

    @Override
    public SerializationResult serialize(Datatype datatype) throws SerializationException {
        return this.serialize(datatype, DEFAULT_INDENT, DEFAULT_INCLUDE_RESOURCE);
    }

    @Override
    public SerializationResult serialize(List<? extends Datatype> datatypeList) throws SerializationException {
        return this.serialize(datatypeList, DEFAULT_INDENT, DEFAULT_INCLUDE_RESOURCE);
    }

    /**
     * Serializes a given datatype to XML with a given indent.
     * 
     * @param datatype
     *            the datatype to serialize
     * @param indent
     *            the XML indent
     * @param includeResources
     *            include external resources like external XML or binary files
     * 
     * @return the serialization result as string
     * 
     * @throws SerializationException
     *             when the datatype cannot be serialized
     */
    public SerializationResult serialize(Datatype datatype, int indent, boolean includeResources)
            throws SerializationException {
        if (datatype == null) {
            throw new IllegalArgumentException("Unable to serialize datatype [null].");
        }

        XmlSerializationVisitor printer = new XmlSerializationVisitor(indent, includeResources);

        try {
            datatype.accept(printer);
        } catch (VisitorException e) {
            throw new SerializationException("Unable to serialize datatype [" + datatype + "].", e);
        }

        return printer.finish();
    }

    /**
     * Serializes a list of datatype to XML with a given indent.
     * 
     * @param datatypeList
     *            the list of datatypes to serialize
     * @param indent
     *            the XML indent
     * @param includeResources
     *            include external resources like external XML or binary files
     * 
     * @return the serialization result as string
     * 
     * @throws SerializationException
     *             when the datatype list cannot be serialized
     */
    public SerializationResult serialize(List<? extends Datatype> datatypeList, int indent, boolean includeResources)
            throws SerializationException {
        if (datatypeList == null) {
            throw new IllegalArgumentException("Unable to serialize datatype list [null].");
        }

        XmlSerializationVisitor printer = new XmlSerializationVisitor(indent, includeResources);

        for (Datatype datatype : datatypeList) {
            if (datatype == null) {
                throw new IllegalArgumentException("Unable to serialize datatype [null].");
            }

            try {
                datatype.accept(printer);
            } catch (VisitorException e) {
                throw new SerializationException("Unable to serialize datatype [" + datatype + "].", e);
            }
        }

        return printer.finish();
    }

    @Override
    public List<Datatype> deserialize(DeserializationData data) throws SerializationException {
        if (data == null) {
            throw new IllegalArgumentException("Unable to deserialize XML [null].");
        }

        XmlDeserializationParser parser = new XmlDeserializationParser();
        return parser.parseXml(data.getContent(), data.getResourceData());
    }

}
