/*
 * Copyright 2010 PRODYNA AG
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
package org.nabucco.framework.base.facade.exception;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.exception.NabuccoException;

import org.nabucco.framework.base.facade.datatype.message.MessageMappingBase;
import org.nabucco.framework.base.facade.datatype.message.MessageProperty;

/**
 * Default implementation for mapping an exception to a messageMapper.
 * 
 * @author Michael Krausse, PRODYNA AG
 */
public class NabuccoExceptionMapper {

    /**
     * Delimits created path.
     */
    public final static String DELIMITER = "/";

    /**
     * Maps an exception to a message.
     * 
     * Uses basepath of an empty string.
     * 
     * @param exception
     *            should be mapped
     * @return mapped exception
     */
    public static MessageMappingBase mapException(final NabuccoException exception) {
        return mapException(exception, "");
    }

    /**
     * Maps exception to a message.
     * 
     * @param exception
     *            value
     * @param basePath
     *            suffix of the path where local pathElements are added
     * @return Mapping for exception (and all children of the exeption)
     */
    public static MessageMappingBase mapException(final NabuccoException exception,
            final String basePath) {

        MessageMappingBase result = null;

        if (exception != null) {
            MessageMappingBase lastMessage = null;
            StringBuilder path = new StringBuilder(basePath);

            // FIXME: NBC-307!!! If https://secure.prodyna.de/jira/browse/NBC-307 has not been
            // closed the following produces an error, because needed method getPathElements() are
            // not generated.

            // for (String pathElement : exception.getPathElements()) {
            for (String pathElement : new String[0]) {
                path.append(NabuccoExceptionMapper.DELIMITER).append(pathElement);
                MessageMappingBase mapping = createMessageMapping(exception, path);
                if (result == null) {
                    result = mapping;
                } else {
                    if (lastMessage != null) {
                        lastMessage.getChildren().add(mapping);
                    }
                }
                lastMessage = mapping;
            }
            getMappingsFromCause(exception, lastMessage, path);
        }

        return result;
    }

    /**
     * Creates a mapping for an exception (without children)
     * 
     * @param exception
     *            to be mapped
     * @param path
     *            path this exception is mapped to
     * 
     * @return created mapping base.
     */
    private static MessageMappingBase createMessageMapping(final NabuccoException exception,
            StringBuilder path) {
        MessageMappingBase result = new MessageMappingBase();
        result.setCodeNumber(path.toString());
        result.getMessagePropertiyList().addAll(createMessagePropertyList(exception));
        return result;
    }

    /**
     * Create Mappings from Cause (if cause is a nabucco exception)
     * 
     * @param exception
     *            the root exception
     * @param parent
     *            the parent mapping
     * @param path
     *            the path
     */
    private static void getMappingsFromCause(final NabuccoException exception,
            final MessageMappingBase parent, final StringBuilder path) {
        if (parent != null) {

            Throwable cause = exception.getCause();
            if (cause instanceof NabuccoException) {
                NabuccoException nabuccoCause = (NabuccoException) cause;

                // FIXME: NBC-307!!! If https://secure.prodyna.de/jira/browse/NBC-307 has not been
                // closed the following produces an error, because needed methods are not
                // generated.

                // MessageMappingBase mappingChildren = cause.getMapping(path.toString());
                MessageMappingBase mappingChildren = null;

                if (mappingChildren != null) {
                    parent.getChildren().add(mappingChildren);
                }
            }
        }
    }

    private static List<MessageProperty> createMessagePropertyList(NabuccoException exception) {
        List<MessageProperty> result = new ArrayList<MessageProperty>();
        for (String key : exception.getParameters().keySet()) {
            String value = exception.getParameters().get(key);
            MessageProperty messageProperty = new MessageProperty();
            messageProperty.setKey(key);
            messageProperty.setValue(value);
            result.add(messageProperty);
        }
        return result;
    }

}
