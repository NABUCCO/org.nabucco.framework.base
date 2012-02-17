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

import java.text.MessageFormat;

/**
 * SerializationConstants
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface SerializationConstants {

    /**
     * The XML Attribute "id".
     */
    final String ATTRIBUTE_ID = "id";

    /**
     * The XML Attribute "type".
     */
    final String ATTRIBUTE_TYPE = "type";

    /**
     * The XML Attribute "name".
     */
    final String ATTRIBUTE_NAME = "name";

    /**
     * The XML Attribute "sourceId".
     */
    final String ATTRIBUTE_SOURCE = "sourceId";

    /**
     * The XML Attribute "targetId".
     */
    final String ATTRIBUTE_TARGET = "targetId";

    /**
     * The XML Attribute "rootId".
     */
    final String ATTRIBUTE_ROOT = "rootId";

    /**
     * The XML Tag "datatypeState".
     */
    final String TAG_STATE = "datatypeState";

    /**
     * The XML Tag "links".
     */
    final String TAG_LINKS = "links";

    /**
     * The XML Tag "link".
     */
    final String TAG_LINK = "link";

    /**
     * Reference ID.
     */
    final String REFERENCE_ID = "RefId";

    /**
     * String for null.
     */
    final String NULL_VALUE = "null";

    /**
     * The default charset.
     */
    final String CHARSET = "ISO-8859-1";

    /**
     * The resource comment.
     */
    final String RESOURCE_COMMENT = "The Serialized NABUCCO Export.";

    /**
     * The resource content.
     */
    final String RESOURCE_CONTENT = "content";

    /**
     * The resource prefix.
     */
    final String RESOURCE_PREFIX = "res";

    /**
     * The resource suffix.
     */
    final String RESOURCE_SUFFIX = "res";

    /**
     * Constant for character data message formatting.
     */
    final MessageFormat CDATA_FORMAT = new MessageFormat("<![CDATA[{0}]]>");
}
