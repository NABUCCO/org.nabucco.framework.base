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
package org.nabucco.framework.base.facade.datatype.serialization;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;

/**
 * Serializer
 * <p/>
 * Interface for {@link Datatype} serializer and deserialization purpose. See {@link XmlSerializer}
 * for concrete implementations.
 * 
 * @see XmlSerializer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Serializer {

    /**
     * Serializes a given datatype to the specific format.
     * 
     * @param datatype
     *            the datatype to serialize
     * 
     * @return the serialization result as string
     * 
     * @throws SerializationException
     *             when the datatype cannot be serialized
     */
    SerializationResult serialize(Datatype datatype) throws SerializationException;

    /**
     * Serializes a list of datatype to the specific format.
     * 
     * @param datatypeList
     *            the list of datatypes to serialize
     * 
     * @return the serialization result as string
     * 
     * @throws SerializationException
     *             when the datatype list cannot be serialized
     */
    SerializationResult serialize(List<? extends Datatype> datatypeList) throws SerializationException;

    /**
     * De-serializes a list of datatypes from a specific format.
     * 
     * @param data
     *            the serialized data in a specific format (e.g. XML).
     * 
     * @return the deserialized datatypes
     * 
     * @throws SerializationException
     *             when the datatype cannot be de-serialized
     */
    List<Datatype> deserialize(DeserializationData data) throws SerializationException;

}
