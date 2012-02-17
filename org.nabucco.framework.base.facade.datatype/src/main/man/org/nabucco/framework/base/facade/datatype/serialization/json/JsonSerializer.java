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
package org.nabucco.framework.base.facade.datatype.serialization.json;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.facade.datatype.serialization.DeserializationData;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationResult;
import org.nabucco.framework.base.facade.datatype.serialization.Serializer;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * JsonSerializer
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public final class JsonSerializer implements Serializer {

    private static JsonSerializer instance;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(JsonSerializer.class);

    /**
     * Creates a new {@link JsonSerializer} instance.
     */
    private JsonSerializer() {
    }

    /**
     * Getter for the singleton instance.
     * 
     * @return the singleton instance
     */
    public static synchronized JsonSerializer getInstance() {
        if (instance == null) {
            instance = new JsonSerializer();
        }
        return instance;
    }

    @Override
    public SerializationResult serialize(List<? extends Datatype> datatypeList) throws SerializationException {
        StringBuffer sb = new StringBuffer();
        SerializationResult serializationResult = new SerializationResult();
        for (Datatype current : datatypeList) {
            sb.append(serializeDatatype(current));
        }
        serializationResult.setContent(sb.toString());
        return serializationResult;
    }

    @Override
    public SerializationResult serialize(Datatype datatype) throws SerializationException {
        SerializationResult serializationResult = new SerializationResult();
        serializationResult.setContent(this.serializeDatatype(datatype));
        return serializationResult;
    }

    /**
     * Serialize the given datatype.
     * 
     * @param datatype
     *            the datatype to serialize
     * 
     * @return the serialized datatype
     * 
     * @throws SerializationException
     *             when the serialization fails
     */
    private String serializeDatatype(Datatype datatype) throws SerializationException {
        JsonSerializationVisitor jsonVisitor = new JsonSerializationVisitor();
        try {
            datatype.accept(jsonVisitor);
        } catch (VisitorException ve) {
            logger.error(ve, "Error visiting datatype '", datatype.getClass().getCanonicalName(), "'.");
            throw new SerializationException("Error visiting datatype '"
                    + datatype.getClass().getCanonicalName() + "'.", ve);
        }
        return jsonVisitor.getBufferContent();
    }

    /**
     * Serializes a given visitable to the specific format.
     * 
     * @param datatype
     *            the visitable to serialize
     * 
     * @return the serialization result as string
     * 
     * @throws SerializationException
     *             when the visitable cannot be serialized
     */
    public SerializationResult serialize(PropertyOwner visitable) throws SerializationException {
        SerializationResult serializationResult = new SerializationResult();
        serializationResult.setContent(this.serializePropertyOwner(visitable));
        return serializationResult;
    }

    /**
     * Serialize the given datatype.
     * 
     * @param datatype
     *            the datatype to serialize
     * 
     * @return the serialized datatype
     * 
     * @throws SerializationException
     *             when the serialization fails
     */
    private String serializePropertyOwner(PropertyOwner propertyOwner) throws SerializationException {
        JsonSerializationVisitor jsonVisitor = new JsonSerializationVisitor();
        try {
            propertyOwner.accept(jsonVisitor);
        } catch (VisitorException ve) {
            logger.error(ve, "Error visiting type '", propertyOwner.getClass().getCanonicalName(), "'.");
            throw new SerializationException("Error visiting type '"
                    + propertyOwner.getClass().getCanonicalName() + "'.", ve);
        }
        return jsonVisitor.getBufferContent();
    }

    @Override
    public List<Datatype> deserialize(DeserializationData data) throws SerializationException {
        throw new SerializationException("deserialize not yet supported");
    }

}
